package com.sunvote.udptest;

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
            remoteUDPSocket = (MainActivity.port ==  0 ?
                    new DatagramSocket() : new DatagramSocket(MainActivity.port));//_Utils.LOCAL_UDP_SEND$LISTENING_PORT);
            remoteUDPSocket.setReuseAddress(true);
            return remoteUDPSocket;
        } catch (Exception e) {
            closeLocalUDPSocket();
            return null;
        }
    }

    private boolean isRemoteUDPSocketReady() {
        boolean ret = true;
        if(remoteUDPSocket != null) {
            return ret && !remoteUDPSocket.isClosed();
        }
        return false;
    }



    public void closeLocalUDPSocket() {
        try {
            if (remoteUDPSocket != null) {
                remoteUDPSocket.close();
                remoteUDPSocket = null;
            } else {
            }
        } catch (Exception e) {
        }
    }

    public DatagramSocket getRemoteDPSocket() {
        if (isRemoteUDPSocketReady()) {
            return remoteUDPSocket;
        } else {
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
                ds.connect(InetAddress.getByName(MainActivity.serverIp), MainActivity.port);
            } catch (Exception e) {
                return -1;
            }
        }
        return UDPUtils.send(ds, fullProtocalBytes, dataLen) ? 0 : -1;

    }


}
