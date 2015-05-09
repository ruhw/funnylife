package com.example.ruhaiwen.jobqueueottodemo.events;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ruhaiwen on 15-4-2.
 */
public class LoadImageSucceedEvent {

    public ImageView mImageView;
    public Bitmap mBitmap;
    public LoadImageSucceedEvent(ImageView imageView,Bitmap bitmap){
        mImageView = imageView;
        mBitmap = bitmap;
    }
}
