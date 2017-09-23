package com.sunvote.udptransfer.core;

import com.sunvote.udptransfer.Config;
import com.sunvote.udptransfer.UDPModule;
import com.sunvote.util.LogUtil;

import java.net.DatagramSocket;
import java.net.SocketAddress;

/**
 * Created by Elvis on 2017/8/8.
 * Email:Eluis@psunsky.com
 * Description:
 * 本地UDP提供类
 */

public class LocalUDPSocketProvider {
    private final static String TAG = LocalUDPSocketProvider.class.getSimpleName();

    private static LocalUDPSocketProvider instance = null;

    private DatagramSocket localUDPSocket = null;

    public static LocalUDPSocketProvider getInstance() {
        if (instance == null)
            instance = new LocalUDPSocketProvider();
        return instance;
    }

    private LocalUDPSocketProvider() {
        //
    }

    public DatagramSocket resetLocalUDPSocket() {
        try {
            closeLocalUDPSocket();
            LogUtil.d(UDPModule.TAG, "【IMCORE】new DatagramSocket()中...");
            localUDPSocket = (Config.getInstance().localUDPPort == 0 ?
                    new DatagramSocket() : new DatagramSocket(Config.getInstance().localUDPPort));//_Utils.LOCAL_UDP_SEND$LISTENING_PORT);
            //Config.getInstance().localUDPPort
            localUDPSocket.setReuseAddress(true);
            localUDPSocket.setSoTimeout(60 * 10000);
            LogUtil.d(UDPModule.TAG, "【IMCORE】new DatagramSocket()已成功完成.");
            return localUDPSocket;
        } catch (Exception e) {
            LogUtil.w(UDPModule.TAG, "【IMCORE】localUDPSocket创建时出错，原因是：" + e.getMessage(), e);
            closeLocalUDPSocket();
            return null;
        }
    }

    private boolean isLocalUDPSocketReady() {
        boolean ret = true;
        if(localUDPSocket != null) {
            SocketAddress socketAddress = localUDPSocket.getRemoteSocketAddress();
            if (socketAddress != null) {
                if(!"".equals(Config.getInstance().serverIP)){
                    ret = socketAddress.toString().contains(Config.getInstance().serverIP);
                }
            }
            return ret && !localUDPSocket.isClosed();
        }
        return false;
    }

    public DatagramSocket getLocalUDPSocket() {
        if (isLocalUDPSocketReady()) {
            LogUtil.d(UDPModule.TAG, "【IMCORE】isLocalUDPSocketReady()==true，直接返回本地socket引用哦。");
            return localUDPSocket;
        } else {
            LogUtil.d(UDPModule.TAG, "【IMCORE】isLocalUDPSocketReady()==false，需要先resetLocalUDPSocket()...");
            return resetLocalUDPSocket();
        }
    }

    public void closeLocalUDPSocket() {
        try {
            LogUtil.d(UDPModule.TAG, "【IMCORE】正在closeLocalUDPSocket()...");
            if (localUDPSocket != null) {
                localUDPSocket.close();
                localUDPSocket = null;
            } else {
                LogUtil.d(UDPModule.TAG, "【IMCORE】Socket处于未初化状态（可能是您还未登陆），无需关闭。");
            }
        } catch (Exception e) {
            LogUtil.w(UDPModule.TAG, "【IMCORE】lcloseLocalUDPSocket时出错，原因是：" + e.getMessage(), e);
        }
    }
}
