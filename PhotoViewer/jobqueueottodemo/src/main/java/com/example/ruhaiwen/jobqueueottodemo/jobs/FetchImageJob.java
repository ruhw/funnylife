package com.example.ruhaiwen.jobqueueottodemo.jobs;


import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.ruhaiwen.jobqueueottodemo.MainActivity;
import com.example.ruhaiwen.jobqueueottodemo.events.LoadImageSucceedEvent;
import com.example.ruhaiwen.jobqueueottodemo.otto.BusProvider;
import com.example.ruhaiwen.jobqueueottodemo.utils.NetUtils;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ruhaiwen on 15-4-2.
 */
public class FetchImageJob extends Job {

    private String mImageUrl;
    private ImageView mTargetView;
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;
    public FetchImageJob(String imageUrl,ImageView targetView) {
        super(new Params(Priority.MID).requireNetwork().groupBy("fetch-images"));
        id = jobCounter.incrementAndGet();
        mImageUrl = imageUrl;
        mTargetView = targetView;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Bitmap bitmap = NetUtils.loadImage(mImageUrl);
        if(bitmap != null){
            BusProvider.getInstance().post(new LoadImageSucceedEvent(mTargetView,bitmap));
        }
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return true;
    }
}
