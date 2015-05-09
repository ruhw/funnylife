package com.example.ruhaiwen.jobqueueottodemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ruhaiwen.jobqueueottodemo.events.LoadImageSucceedEvent;
import com.example.ruhaiwen.jobqueueottodemo.otto.BusProvider;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;


public class MainActivity extends Activity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView)findViewById(R.id.jobqueueListView);
        JobqueueAdapter adapter = new JobqueueAdapter(this.getLayoutInflater(),Images.imageUrls);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    /*@Produce
    public LoadImageSucceedEvent produceLoadImageEvent(ImageView imageView,Bitmap bitmap){
        return new LoadImageSucceedEvent(imageView,bitmap);
    }*/

    @Subscribe
    public void onLoadImageSucceed(LoadImageSucceedEvent event){
        event.mImageView.setImageBitmap(event.mBitmap);
    }
}
