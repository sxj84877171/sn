package com.sunvote.udptransfer.work;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import com.sunvote.cmd.state.GetPkgStateRequest;
import com.sunvote.cmd.upload.NumberingModeResult;
import com.sunvote.protocal.Protocol;
import com.sunvote.udptransfer.Config;
import com.sunvote.udptransfer.UDPModule;
import com.sunvote.udptransfer.core.LocalUDPDataSender;
import com.sunvote.udptransfer.core.LocalUDPSocketProvider;
import com.sunvote.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elvis on 2017/8/14.
 * Email:Eluis@psunsky.com
 * Description:
 * 基站处理器
 * 负责接收基站的信息，然后负责初步处理，并建立消息通道到基站，发送信息到基站。
 */

public class BaseStationProcessWork {

    public static final int TYPE_BASESTATIONPROCESSWORK =  1;

    /**
     * 工作线程名字
     */
    public static final String NAME = BaseStationProcessWork.class.getSimpleName();

    /**
     * 处理 从基站回来的消息处理器
     */
    private WorkThread workThread;
    /**
     * 接收 从基站回来的消息处理器
     */
    private WorkThread receiverThread;
    /**
     * 是否在工作
     */
    private boolean isRun = false;

    /**
     * 维护基站信息
     */
    private BaseStationInfo baseStationInfo = new BaseStationInfo();

    /**
     * 读取基站信息
     * @return
     */
    public BaseStationInfo getBaseStationInfo() {
        return baseStationInfo;
    }

    /**
     * 工作线程，主要是提供重发机制模块工作
     * @return
     */
    public WorkThread getWorkThread() {
        return workThread;
    }

    /**
     * 单例模式
     */
    private static BaseStationProcessWork instance;

    public static BaseStationProcessWork getInstance() {
        if (instance == null) {
            instance = new BaseStationProcessWork();
        }
        return instance;
    }

    private BaseStationProcessWork() {

    }

    /**
     * SDK发送出去的信息的包，基站回来的包进行临时保存处理的数据包
     */
    private NumberingModeResult numberingModeResult;

    public void setNumberingModeResult(NumberingModeResult numberingModeResult) {
        this.numberingModeResult = numberingModeResult;
    }

    public NumberingModeResult getNumberingModeResult() {
        return numberingModeResult;
    }

    /**
     * 信标值
     */
    private GetPkgStateRequest oldPkgState ;

    /**
     * 保存旧的信标值
     * @param oldPkgState
     */
    public void setOldPkgState(GetPkgStateRequest oldPkgState) {
        this.oldPkgState = oldPkgState;
    }

    public GetPkgStateRequest getOldPkgState() {
        return oldPkgState;
    }

    /**
     * 信标是否有变化，是否需要更新
     * @param request
     * @return
     */
    public boolean needUpdateState(GetPkgStateRequest request){
        if(oldPkgState == null){
            return true;
        }

        if(request == null){
            return false;
        }

        boolean ret =!(oldPkgState.getMode() == request.getMode() && oldPkgState.getDataPos() == request.getDataPos());

        if(!ret){
            byte[] bytes = oldPkgState.getModes();
            byte[] newBytes = request.getModes();
            if(bytes != null && newBytes != null){
                for(int i = 0 ; i < bytes.length ; i++){
                    ret = !(bytes[i] == newBytes[i]);
                    if(ret){
                        break;
                    }
                }
            }
        }

        return ret ;
    }

    /**
     * 开启基站接收处理器工作
     * 如果已经开启工作，则直接跳过，不处理
     */
    public void start() {
        LogUtil.d(UDPModule.TAG, "基站接收处理器被调用开启工作");
        if (!isRunning()) {
            LogUtil.d(UDPModule.TAG, "基站接收处理器开始工作");
            restore();
            workThread = new WorkThread(NAME);
            receiverThread = new WorkThread(NAME + "-receiver");
            WorkThread.MessageBean messageBean = new WorkThread.MessageBean();
            messageBean.executeMethod = executeMethod;
            receiverThread.sendMessage(messageBean);
            delayOnlineCheck();
            isRun = true;
        }
    }

    /**
     * 判断基站接收处理器是否在工作
     *
     * @return
     */
    public boolean isRunning() {
        return isRun;
    }

    /**
     * 停止基站接收处理器工作
     * 如果已停止，则直接不处理
     */
    public void stop() {
        LogUtil.d(UDPModule.TAG, "基站接收处理器被调用停止工作");
        if (isRunning()) {
            LogUtil.d(UDPModule.TAG, "基站接收处理器停止工作");
            workThread.destroyObject();
            receiverThread.destroyObject();
            LocalUDPSocketProvider.getInstance().closeLocalUDPSocket();
            isRun = false;
        }
    }

    /**
     * 基站接收数据到模块
     */
    private WorkThread.ExecuteMethod executeMethod = new WorkThread.ExecuteMethod() {
        @Override
        public void execute(WorkThread.MessageBean messageBean) {
            DatagramSocket localUDPSocket = LocalUDPSocketProvider.getInstance().getLocalUDPSocket();
            while (isRunning()) {
                if (localUDPSocket != null && !localUDPSocket.isClosed()) {
                    try {
                        byte[] data = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(data, data.length);
                        localUDPSocket.receive(packet);
                        LogUtil.v(UDPModule.TAG,"BaseStationProcessWork(BaseStation -> Module):",packet.getData(),packet.getLength());
                        byte[] result = getBytes(data, packet);
                        //初始化基站IP地址
                        if(!initServerIp(packet, result)){
                            continue;
                        }
                        //设置module为在线状态
                        getBaseStationInfo().setOnline();
                        // 延迟module检测在线状态
                        delayOnlineCheck();
                        //扔到处理器去处理，然后等待接收基站指令。
                        WorkThread.MessageBean bean = ProtocalFactory.execute(result, result.length,TYPE_BASESTATIONPROCESSWORK);
                        //如果命令没有解析，没有指定对应的处理方法，添加一个默认的处理方法，即转发
                        isNeddAddDefaultMethod(bean);
                        workThread.sendMessage(bean);
                    } catch (Exception ex) {
                        LogUtil.e(UDPModule.TAG, ex);
                        localUDPSocket.close();
                    }
                } else {
                    localUDPSocket = LocalUDPSocketProvider.getInstance().getLocalUDPSocket();
                }
            }
        }
    };

    /**
     * 移除从基站过来的信息的配对码
     * @param data
     * @param packet
     * @return
     */
    private byte[] getBytes(byte[] data, DatagramPacket packet) {
        if(packet.getLength() > 8) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if(packet.getLength() > 8) {
                outputStream.write(packet.getData(), 0, 3);
                outputStream.write(packet.getData()[3] - 4);//去掉配对码的长度，再进行处理
                outputStream.write(packet.getData(), 8, packet.getLength() - 8);
            }
            return outputStream.toByteArray();
        }
        return data;
    }

    /**
     * 添加缺省的处理方式，直接转发SDK。
     * @param bean
     */
    private void isNeddAddDefaultMethod(WorkThread.MessageBean bean) {
        if(bean.executeMethod == null){
            bean.executeMethod = new WorkThread.ExecuteMethod() {
                @Override
                public void execute(WorkThread.MessageBean messageBean) {
                    // 需要去掉配对码
                    SDKProcessWork.getInstance().postData(messageBean.datas);
                }
            };
        }
    }

    /**
     * 找到服务器地址，给服务器地址初始化
     * @param packet
     * @param result
     */
    private boolean initServerIp(DatagramPacket packet, byte[] result) {
        String remoteAddress = "";
        if(packet.getAddress() != null){
            remoteAddress = packet.getAddress().toString();
            if(remoteAddress.length() > 1){
                if(!Config.getInstance().serverIP.equals(remoteAddress.substring(1))){
                    if("".equals(Config.getInstance().serverIP) || Config.getInstance().isCanChangerServer()) {
                        LogUtil.v(UDPModule.TAG,"old ip:" + Config.getInstance().serverIP + ",new ip:"+ remoteAddress);
                        Config.getInstance().serverIP = remoteAddress.substring(1);
                        Config.getInstance().setCanChangerServer(false);
                        return true;
                    }
                    return false;
                }
                return true;
            }
        }
        return false;
//        debug code
//        String key = ByteUtils.bytesToHexString(result);
//        if(map.get(key) != null){
//            int count = map.get(key);
//            if(count < 10){
//                LogUtil.v(UDPModule.TAG,remoteAddress + ",BaseStationProcessWork(BaseStation -> Module):",result);
//            }
//            map.put(key,++count);
//        }else{
//            map.put(key,1);
//            LogUtil.v(UDPModule.TAG,remoteAddress + ",BaseStationProcessWork(BaseStation -> Module):",result);
//        }
    }

    // debug code
    private Map<String,Integer> map = new HashMap();


    public void sendProtocol(Protocol protocol){
        if(protocol != null){
            protocol.setEnableMatchCode(true);
            postData(protocol.toBytes());
        }
    }

    /**
     * 模块发送数据到基站
     * @param datas
     */
    public void postData(final byte[] datas){
        WorkThread.MessageBean messageBean = new WorkThread.MessageBean();
        messageBean.datas = datas ;
        messageBean.executeMethod = new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.v(UDPModule.TAG,"BaseStationProcessWork(Module -> BaseStation):",datas);

                try{
                   int ret = LocalUDPDataSender.getInstance().send(datas,datas.length);
                    if(ret < 0){
                        LogUtil.v(UDPModule.TAG,"BaseStationProcessWork(Module -> BaseStation)-fail.",datas);
                    }
                }catch (Exception ex){
                    LogUtil.e(UDPModule.TAG,ex);
                }
            }
        };
        workThread.sendMessage(messageBean);
    }


    /**
     * 基站无活动，需要修改当前为离线状态
     */
    private Runnable checkOnlineTask = new Runnable() {
        @Override
        public void run() {
            WorkThread.MessageBean messageBean = new WorkThread.MessageBean();
            messageBean.executeMethod = new WorkThread.ExecuteMethod() {
                @Override
                public void execute(WorkThread.MessageBean messageBean) {
                    getBaseStationInfo().setOffline();
                }
            };
            workThread.sendMessage(messageBean);
        }
    };


    /**
     * 当前还在线
     * 延迟检测离线状态
     */
    public void delayOnlineCheck(){
        workThread.removeCallbacks(checkOnlineTask);
        workThread.postDelayed(checkOnlineTask,Config.getInstance().CHECK_ON_LINE_TIME);
    }

    /**
     * 延迟任务，重发机制使用，每隔固定时间重复发送消息
     * @param task
     */
    public void postDelayed(Runnable task){
        workThread.postDelayed(task,Config.getInstance().REPEAT_INVERVAL_TIME);
    }


    /**
     * 移除延迟任务，重发机制使用，每隔固定时间重复发送消息
     * @param task
     */
    public void removeCallbacks(Runnable task){
        workThread.removeCallbacks(task);
    }


    public void commit() {
        File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/sunvote/");
        if(!folder.exists()){
            folder.mkdirs();
        }
        File file =new File(Environment.getExternalStorageDirectory().getPath() + "/sunvote/sunvote.dat");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                LogUtil.e(UDPModule.TAG,e);
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            KeyId keyId = new KeyId();
            keyId.setKeyId(getBaseStationInfo().getKeyId());
            objOut.writeObject(keyId);
            objOut.flush();
            LogUtil.i(UDPModule.TAG,"save info success");
        } catch (IOException e) {
            LogUtil.e(UDPModule.TAG,"save info fail",e);
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    LogUtil.e(UDPModule.TAG,e);
                }
            }
        }
    }

    static class KeyId implements Serializable{

        private byte[] keyId;

        public void setKeyId(byte[] keyId) {
            this.keyId = keyId;
        }

        public byte[] getKeyId() {
            return keyId;
        }
    }
    public void restore(){
        File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/sunvote/");
        if(!folder.exists()){
            return;
        }
        File file =new File(Environment.getExternalStorageDirectory().getPath() + "/sunvote/sunvote.dat");
        if(!file.exists()){
            return;
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            KeyId keyId = (KeyId) objIn.readObject();
            baseStationInfo.setKeyId(keyId.getKeyId());
            LogUtil.i(UDPModule.TAG,"save info success");
        } catch (Exception e) {
//            baseStationInfo.setKeyId(new byte[]{0x00,0x01});
            LogUtil.e(UDPModule.TAG,"save info fail",e);
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    LogUtil.e(UDPModule.TAG,e);
                }
            }
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    private Context mContext ;

    public byte getRx(){
        byte ret = 0 ;
        if(mContext != null) {
            WifiManager wifi_service = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifi_service.getConnectionInfo();
            int rssi = wifiInfo.getRssi();
            if(rssi < 0 && rssi > -100){
                rssi = -rssi;
            }
            ret = (byte)rssi;
        }
        return ret;
    }

    public byte getLinkSpeed(){
        byte ret = 0 ;
        if(mContext != null) {
            WifiManager wifi_service = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifi_service.getConnectionInfo();
            int linkspeed = wifiInfo.getLinkSpeed();
            return (byte) linkspeed;
        }
        return ret;
    }

}
