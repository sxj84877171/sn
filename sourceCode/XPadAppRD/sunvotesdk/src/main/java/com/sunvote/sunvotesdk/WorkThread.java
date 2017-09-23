package com.sunvote.sunvotesdk;

import android.os.Handler;
import android.os.HandlerThread;

import com.sunvote.util.LogUtil;

/**
 * Created by Elvis on 2017/8/14.
 * Email:Eluis@psunsky.com
 * Description:
 * 工作模块封装，提供给与基站通信 和 与SDK通信模块的循坏工作队列。
 */

public class WorkThread extends HandlerThread {

    private boolean isAlive = false;

    private Handler handler;

    /**
     * 给工作命名
     * 方便debug调试 发现处理逻辑执行在哪个线程上面。
     * @param name 工作名
     */
    public WorkThread(String name) {
        super(name);
        start();
        isAlive = true;
        handler = new Handler(getLooper());
    }

    /**
     * 发送消息(任务）处理
     * @param messageBean 消息（任务）
     */
    public void sendMessage(final MessageBean messageBean) {
        if (isAlive) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (messageBean != null && messageBean.executeMethod != null) {
                        try {
                            messageBean.executeMethod.execute(messageBean);
                        }catch (Exception ex){
                            LogUtil.e(BaseStationManager.TAG,ex);
                        }
                        if (messageBean.callback != null) {
                            try {
                                messageBean.callback.onMsgHasSend(messageBean);
                            }catch (Exception ex){
                                LogUtil.e(BaseStationManager.TAG,ex);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 定时发送一个消息（任务）
     * @param messageBean 定时消息
     * @param interval 间隔时间
     */
    public void sendIntervalMessage(final MessageBean messageBean, final long interval) {
        if (isAlive) {
            sendMessage(messageBean);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendIntervalMessage(messageBean,interval);
                }
            }, interval);
        }
    }

    /**
     * 延迟发送一个消息
     * @param messageBean 消息
     * @param last 延迟时间 毫秒为单位
     */
    public void sendMessageDelayed(final MessageBean messageBean,long last){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage(messageBean);
            }
        },last);
    }

    /**
     *延迟消息检测与移除
     * @param runnable
     * @param last
     */
    public void postDelayed(Runnable runnable, long last){
        handler.postDelayed(runnable,last);
    }

    /**
     * 移除消息，不执行
     * @param runnable
     */
    public void removeCallbacks(Runnable runnable){
        handler.removeCallbacks(runnable);
    }


    /**
     * 工作模块退出
     * 回收垃圾对象
     */
    public void destroyObject() {
        isAlive = !quit();
    }

    /**
     * 防止调用者没有调用
     *
     * @throws Throwable
     * @see #destroyObject()
     * 垃圾无法回收对象
     */
    @Override
    protected void finalize() throws Throwable {
        if (isAlive) {
            destroyObject();
        }
        super.finalize();
    }

    /**
     * 发送完消息的处理
     */
    public interface Callback {
        void onMsgHasSend(MessageBean messageBean);
    }

    /**
     * 对应的协议的处理操作
     */
    public interface ExecuteMethod {
        void execute(MessageBean messageBean);
    }

    public static class MessageBean {

        /**
         * 协议处理完成后的操作
         */
        public Callback callback;
        /**
         * 该协议的处理方式
         */
        public ExecuteMethod executeMethod;

        /**
         * 由调用者来确定。
         */
        public Object  object;

        public int what;

    }


}
