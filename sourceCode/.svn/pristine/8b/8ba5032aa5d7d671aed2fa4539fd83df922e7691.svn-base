package com.sunvote.xpadapp;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;

import com.sunvote.udptransfer.UDPModule;
import com.sunvote.util.LogUtil;
import com.sunvote.xpadcomm.XPadApi;

import java.util.List;

public class App extends Application {
	public static XPadApi XPad;
	
	@Override
	public void onCreate() {
		 XPad = XPadApi.getInstance();
		XPad.getClient().enablelogtoFile();
		XPad.getClient().setContext(this);
		final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				LogUtil.e(UDPModule.TAG,"carsh(thread id:" + thread.getId() + ",thread name:" + thread.getName() + ")");
				LogUtil.e(UDPModule.TAG,ex);
				if(uncaughtExceptionHandler != null){
					uncaughtExceptionHandler.uncaughtException(thread,ex);
				}
				System.exit(0);
			}
		});
		super.onCreate();
	}
	
	public static boolean isBackground(Context context) {
	    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
	    for (RunningAppProcessInfo appProcess : appProcesses) {
	         if (appProcess.processName.equals(context.getPackageName())) {
	                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
	                         // Log.i("��̨", appProcess.processName);
	                          return true;
	                }else{
	                         // Log.i("ǰ̨", appProcess.processName);
	                          return false;
	                }
	           }
	    }
	    return false;
	}
}
