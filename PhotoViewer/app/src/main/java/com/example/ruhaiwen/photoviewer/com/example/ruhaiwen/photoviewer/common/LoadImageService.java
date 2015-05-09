package com.example.ruhaiwen.photoviewer.com.example.ruhaiwen.photoviewer.common;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import com.example.ruhaiwen.photoviewer.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class LoadImageService extends Service {

    private MyBinder mBinder = new MyBinder();

    private static final int FLOOR = 5;
    public LoadImageService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder{
        public void searchImages(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<String> mList = new ArrayList<String>();

                    FileUtils.searchPhotos(getApplicationContext(),FLOOR);
                }
            }).start();
        }
    }
}
