package com.psk.mvvmdemo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

public class MVVMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // Helper for running tasks on the main UI thread
    private static final Handler mHandler = new Handler();

    public static void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

}
