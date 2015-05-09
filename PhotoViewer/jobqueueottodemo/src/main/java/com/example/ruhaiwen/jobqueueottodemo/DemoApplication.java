package com.example.ruhaiwen.jobqueueottodemo;

import android.app.Application;

import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

/**
 * Created by ruhaiwen on 15-4-1.
 */
public class DemoApplication extends Application {

    private static DemoApplication sDemoApplictation;
    private JobManager mJobManager;

    public DemoApplication(){
        sDemoApplictation = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        configureJobManager();
    }

    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(3)
                .consumerKeepAlive(120)
                .build();
        mJobManager = new JobManager(this,configuration);
    }

    public static DemoApplication getInstance(){
        return sDemoApplictation;
    }

    public JobManager getJobManager(){
        return mJobManager;
    }

}
