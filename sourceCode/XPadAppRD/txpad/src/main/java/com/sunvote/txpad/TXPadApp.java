package com.sunvote.txpad;

import android.app.Application;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.txpad.cache.SpCache;
import com.sunvote.util.LogUtil;

/**
 * Created by Elvis on 2017/9/6.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class TXPadApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationData.getInstance();
        SpCache.getInstance().setContext(this);
        LogUtil.enableLogToFile();
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                LogUtil.e("TXPadApp","carsh(thread id:" + thread.getId() + ",thread name:" + thread.getName() + ")");
                LogUtil.e("TXPadApp",ex);
                if(uncaughtExceptionHandler != null){
                    uncaughtExceptionHandler.uncaughtException(thread,ex);
                }
                System.exit(0);
            }
        });
        BaseStationManager.getInstance().init();
    }
}
