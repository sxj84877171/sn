package com.sunvote.udptransfer;

import android.content.Context;

import com.sunvote.udptransfer.stream.SunVoteInputStream;
import com.sunvote.udptransfer.stream.SunVoteOutputStream;
import com.sunvote.udptransfer.work.BaseStationProcessWork;
import com.sunvote.udptransfer.work.RepeatMessageManager;
import com.sunvote.udptransfer.work.SDKProcessWork;
import com.sunvote.util.ByteUtils;
import com.sunvote.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Elvis on 2017/8/8.
 * Email:Eluis@psunsky.com
 * Description:Client 配置使用
 * 1.首先开启wifi UDP的监听。
 * 2.开启SDK写入数据的监听
 * <p>
 * 外面调用只需要调用这个类，所有的开放的东西都从这个接口调用
 * <p>
 * 模块主要功能：
 * SDK -> UDPModule -> BaseStation
 * BaseStation -> UDPModule -> SDK
 * SDK->UDPModule->SDK
 * BaseStation->UDPModule -> BaseStation
 * UDPModule -> SDK
 */
public class UDPModule {

    public static final String TAG = UDPModule.class.getSimpleName();
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * 由外界全部配置
     *
     * @param serverIp   服务器IP地址
     * @param serverPort 服务器端口
     * @param localPort  本地端口
     */
    public UDPModule(String serverIp, int serverPort, int localPort) {
        Config.getInstance().serverIP = serverIp;
        Config.getInstance().localUDPPort = localPort;
        Config.getInstance().serverUDPPort = serverPort;
        init();
    }

    private void init() {
        SunVoteInputStream udpInputStream = new SunVoteInputStream();
        SunVoteOutputStream udpOutputStream = new SunVoteOutputStream();
        SDKProcessWork.getInstance().setInputStream(udpInputStream);
        BaseStationProcessWork.getInstance().start();
        RepeatMessageManager.getInstance().start();
        SDKProcessWork.getInstance().start();
        inputStream = udpInputStream;
        outputStream = udpOutputStream;
    }

    /**
     * 自动查找服务端IP地址
     *
     * @param serverPort 服务器端口
     * @param localPort  本地端口
     */
    public UDPModule(int serverPort, int localPort) {
        Config.getInstance().localUDPPort = localPort;
        Config.getInstance().serverUDPPort = serverPort;
        init();
    }

    /**
     * 自动查找服务端地址，并且本地和远程端口一致
     *
     * @param port 远程和与本地使用同一个端口
     */
    public UDPModule(int port) {
        Config.getInstance().localUDPPort = port;
        Config.getInstance().serverUDPPort = port;
        init();
    }

    /**
     * 无需额外配置，使用默认配置
     */
    public UDPModule() {
        init();
    }

    /**
     * 获取输入流
     * 适配前期APP使用
     * 数据转发出口
     * 后期开启直接监听接收数据包
     * @return
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * 获取输出流
     *适配前期APP使用
     * 数据入口
     * 后期修改直接发命令包
     * @return
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * 释放资源
     * 最后使用完成，记得释放资源
     */
    public void release() {
        BaseStationProcessWork.getInstance().stop();
        SDKProcessWork.getInstance().stop();
        RepeatMessageManager.getInstance().stop();

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启日志输出到文件
     * 可以开启线上跟踪
     */
    public void enablelogtoFile() {
        LogUtil.enableLogToFile();
    }

    /**
     * 关闭日志输出到文件
     * 关闭线上耿总
     */
    public void disableLogtoFile() {
        LogUtil.disabelLogToFile();
    }

    /**
     * 开启module中日志Studio 监视器输出
     */
    public void enableModuleLog() {
        LogUtil.enableLog();
    }

    /**
     * 关闭module中日志输出
     */
    public void disableModuleLog() {
        LogUtil.disableLog();
    }

    /**
     * 设置日志级别
     * V级别 所有日志都输出
     * D级别，debug信息输出
     * I级别，信息级别输出
     * W警告级别日志输出
     * E级别，错误级别日志输出
     *
     * @param lever
     */
    public void setDebugLever(int lever) {
        LogUtil.lever = lever;
    }


    /**
     * 设置在线检测时间
     * 重启模块后生效
     *
     * @param time
     */
    public void setOnLineCheckTime(long time) {
        if (time > 0) {
            Config.getInstance().CHECK_ON_LINE_TIME = time;
        } else {
            LogUtil.w(UDPModule.TAG, "setOnLineCheckTime time is not right.time =" + time);
        }
    }

    /**
     * 消息包未接收，重复发送的间隔时间设置
     */
    public void setRepeatCheckTime(long time) {
        if (time > 0) {
            Config.getInstance().REPEAT_INVERVAL_TIME = time;
        } else {
            LogUtil.w(UDPModule.TAG, "setRepeatCheckTime time is not right.time =" + time);
        }
    }

    public int getUdpModuleNO(){
        return ByteUtils.bytes2Int(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
    }

    public void searchServarIp(){
        Config.getInstance().setCanChangerServer(true);
    }

    public void setContext(Context context){
        if(context != null){
            BaseStationProcessWork.getInstance().setContext(context);
        }
    }

}
