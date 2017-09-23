package com.sunvote.udptransfer.core;

import android.text.TextUtils;
import android.util.Log;

import com.sunvote.udptransfer.Config;
import com.sunvote.udptransfer.UDPModule;
import com.sunvote.util.LogUtil;
import com.sunvote.util.UDPUtils;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Elvis on 2017/8/8.
 * Email:Eluis@psunsky.com
 * Description:
 */

public class LocalUDPDataSender {

    private final static String TAG = LocalUDPDataSender.class.getSimpleName();

    private static LocalUDPDataSender instance = null;

    private DatagramSocket remoteUDPSocket = null;


    public static LocalUDPDataSender getInstance() {
        if (instance == null)
            instance = new LocalUDPDataSender();
        return instance;
    }

    private LocalUDPDataSender() {

    }

    public DatagramSocket resetRemoteUDPSocket() {
        try {
            closeLocalUDPSocket();
            LogUtil.d(UDPModule.TAG, "new DatagramSocket()中...");
            remoteUDPSocket = (Config.getInstance().serverUDPPort == 0 ?
                    new DatagramSocket() : new DatagramSocket(Config.getInstance().serverUDPPort));//_Utils.LOCAL_UDP_SEND$LISTENING_PORT);
            remoteUDPSocket.setReuseAddress(true);
            LogUtil.d(UDPModule.TAG, "new DatagramSocket()已成功完成.");
            return remoteUDPSocket;
        } catch (Exception e) {
            LogUtil.w(UDPModule.TAG, "localUDPSocket创建时出错，原因是：" + e.getMessage(), e);
            closeLocalUDPSocket();
            return null;
        }
    }

    private boolean isRemoteUDPSocketReady() {
        boolean ret = true;
        if(remoteUDPSocket != null && isSameIp(remoteUDPSocket)) {
            return ret && !remoteUDPSocket.isClosed();
        }
        return false;
    }

    public boolean isSameIp(DatagramSocket socket){
        if(socket == null){
            return  false;
        }

        if(socket.getRemoteSocketAddress() == null){
            return false;
        }

        String ip = socket.getRemoteSocketAddress().toString();
        if(TextUtils.isEmpty(Config.getInstance().serverIP)){
            return true;
        }else{
            return ip.contains(Config.getInstance().serverIP);
        }
    }

    public void closeLocalUDPSocket() {
        try {
            LogUtil.d(UDPModule.TAG, "正在closeLocalUDPSocket()...");
            if (remoteUDPSocket != null) {
                remoteUDPSocket.close();
                remoteUDPSocket = null;
            } else {
                LogUtil.d(UDPModule.TAG, "Socket处于未初化状态（可能是您还未登陆），无需关闭。");
            }
        } catch (Exception e) {
            LogUtil.w(UDPModule.TAG, "closeLocalUDPSocket时出错，原因是：" + e.getMessage(), e);
        }
    }

    public DatagramSocket getRemoteDPSocket() {
        if (isRemoteUDPSocketReady()) {
            return remoteUDPSocket;
        } else {
            LogUtil.d(UDPModule.TAG, "isRemoteUDPSocketReady()==false，需要先resetLocalUDPSocket()...");
            return resetRemoteUDPSocket();
        }
    }



    /**
     * 需要重载一个函数
     * 如果没有指定服务器IP地址，则只能广播出去，让服务器接收处理
     * @param fullProtocalBytes
     * @param dataLen
     * @return
     */
    public int send(byte[] fullProtocalBytes, int dataLen) {
        DatagramSocket ds = getRemoteDPSocket();
        if (ds != null && !ds.isConnected()) {
            try {
                if("".equals(Config.getInstance().serverIP)){
                    LogUtil.d(UDPModule.TAG,"user the ip: localhost");
                    ds.connect(InetAddress.getByName("localhost"), Config.getInstance().serverUDPPort);
                }else{
                    LogUtil.d(UDPModule.TAG,"user the ip:" + Config.getInstance().serverIP);
                    ds.connect(InetAddress.getByName(Config.getInstance().serverIP), Config.getInstance().serverUDPPort);
                }
            } catch (Exception e) {
                Log.w(UDPModule.TAG, "send时出错，原因是：" + e.getMessage(), e);
                return -1;
            }
        }
        return UDPUtils.send(ds, fullProtocalBytes, dataLen) ? 0 : -1;

    }


}
