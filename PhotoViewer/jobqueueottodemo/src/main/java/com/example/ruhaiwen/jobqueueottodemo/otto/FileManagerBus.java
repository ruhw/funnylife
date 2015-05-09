package com.example.ruhaiwen.jobqueueottodemo.otto;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by ruhaiwen on 15-4-2.
 */
public class FileManagerBus extends Bus {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    FileManagerBus.super.post(event);
                }
            });
        }
    }
}
