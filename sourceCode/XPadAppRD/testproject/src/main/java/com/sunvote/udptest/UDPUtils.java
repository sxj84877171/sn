package com.sunvote.udptest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Elvis on 2017/8/8.
 * Email:Eluis@psunsky.com
 * Description: UDP发送工具类
 */

public class UDPUtils {

    private final static String TAG = UDPUtils.class.getSimpleName();

    public static boolean send(DatagramSocket skt, byte[] d, int dataLen) {
        if (skt != null && d != null) {
            try {
                return send(skt, new DatagramPacket(d, dataLen));
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public synchronized static boolean send(DatagramSocket skt, DatagramPacket p) {
        boolean sendSucess = true;
        if (skt != null && p != null) {
                        if (skt.isConnected()) {
                            try {
                                skt.send(p);
                            } catch (Exception e) {
                                try{
                                    skt.close();
                                }catch (Exception ex){}
                    sendSucess = false;
                }
            }
        } else {
        }

        return sendSucess;
    }
}
