package com.sunvote.txpad;

import android.app.Application;

import com.sunvote.util.LogUtil;

/**
 * Created by Elvis on 2017/9/6.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class TXadApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationData.getInstance();
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                LogUtil.e("TXadApp","carsh(thread id:" + thread.getId() + ",thread name:" + thread.getName() + ")");
                LogUtil.e("TXadApp",ex);
                if(uncaughtExceptionHandler != null){
                    uncaughtExceptionHandler.uncaughtException(thread,ex);
                }
                System.exit(0);
            }
        });
    }
}
