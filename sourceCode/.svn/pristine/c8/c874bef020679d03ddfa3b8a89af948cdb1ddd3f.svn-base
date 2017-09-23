package com.sunvote.udptransfer.work;

import com.sunvote.protocal.Protocol;
import com.sunvote.udptransfer.UDPModule;
import com.sunvote.udptransfer.stream.SunVoteInputStream;
import com.sunvote.util.LogUtil;

import java.io.ByteArrayOutputStream;

/**
 * Created by Elvis on 2017/8/14.
 * Email:Eluis@psunsky.com
 * Description:
 * SDK接收处理器
 */

public class SDKProcessWork {

    public static final int TYPE_SDKPROCESSWORK = 2 ;

    public final static String NAME = SDKProcessWork.class.getSimpleName();
    private static SDKProcessWork instance = new SDKProcessWork();
    private boolean isRun = false;
    private WorkThread workThread;
    private SunVoteInputStream inputStream;


    public void setInputStream(SunVoteInputStream inputStream) {
        this.inputStream = inputStream;
    }

    private SDKProcessWork(){
    }

    public static SDKProcessWork getInstance(){
        return instance;
    }

    public WorkThread getWorkThread() {
        return workThread;
    }

    /**
     * 判断SDK接收处理器是否在工作
     * @return
     */
    public boolean isRunning() {
        return isRun;
    }


    public void start(){
        LogUtil.d(UDPModule.TAG, "SDK接收处理器被调用开启工作");
        if(!isRunning()){
            LogUtil.d(UDPModule.TAG, "SDK接收处理器开始工作");
            workThread = new WorkThread(NAME);
            isRun = true;
        }
    }

    public void stop(){
        LogUtil.d(UDPModule.TAG, "SDK接收处理器被调用停止工作");
        if(isRunning()){
            LogUtil.d(UDPModule.TAG, "SDK接收处理器停止工作");
            workThread.destroyObject();
            isRun = false;
        }
    }

    public void execute(byte[] datas,int length){
        LogUtil.i(UDPModule.TAG,"SDKProcessWork(SDK -> Module):",datas);
        WorkThread.MessageBean messageBean = ProtocalFactory.execute(datas,length,TYPE_SDKPROCESSWORK);
        if(messageBean.executeMethod == null){
            messageBean.executeMethod = new WorkThread.ExecuteMethod() {
                @Override
                public void execute(WorkThread.MessageBean messageBean) {
                    try {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        outputStream.write(messageBean.datas, 0, 4);
                        outputStream.write(BaseStationProcessWork.getInstance().getBaseStationInfo().getMatchCode());
                        outputStream.write(messageBean.datas,4,messageBean.datas.length-4);
                        BaseStationProcessWork.getInstance().postData(outputStream.toByteArray());
                        outputStream.close();
                    }catch (Exception ex){
                        LogUtil.e(UDPModule.TAG,ex);
                    }
                }
            };
        }
        workThread.sendMessage(messageBean);
    }

    public void postData(byte[] datas,int length){
        LogUtil.i(UDPModule.TAG,"SDKProcessWork(Module -> SDK):",datas);
        if(inputStream != null){
            inputStream.pushDatas(datas,length);
        }
    }

    public void postData(byte[] datas){
        postData(datas,datas.length);
    }

    public void sendProtocol(Protocol protocol){
        if(protocol != null){
            protocol.setEnableMatchCode(false);
            postData(protocol.toBytes());
        }
    }

}
