package com.example.ruhaiwen.photoviewer.model;

import android.content.Context;
import android.util.Log;

import com.example.ruhaiwen.photoviewer.com.example.ruhaiwen.photoviewer.common.PhotoViewerJSONSerializer;

import java.util.ArrayList;

/**
 * Created by ruhaiwen on 14-10-30.
 */
public class PhotoLab {

    private static final String TAG = "PhotoLab";
    private static final String FILENAME ="photo.json";

    private ArrayList<Photo> mPhotos;
    private PhotoViewerJSONSerializer mSerializer;
    private static PhotoLab sPhotoLab;
    private Context mAppContext;

    private PhotoLab(Context appContext){
        mAppContext = appContext;
        //mCrimes = new ArrayList<Crime>();
        mSerializer = new PhotoViewerJSONSerializer(mAppContext, FILENAME);
        try{
            mPhotos = mSerializer.loadPhotos();
        }catch(Exception e){
            mPhotos = new ArrayList<Photo>();
            Log.e(TAG, "Error loading mPhotos: ", e);
        }
    }

    public static PhotoLab get(Context c){
        if(sPhotoLab == null){
            sPhotoLab = new PhotoLab(c.getApplicationContext());
        }
        return sPhotoLab;
    }

    public ArrayList<Photo> getPhotos(){
        return mPhotos;
    }

    public Photo getPhoto(String path){
        for(Photo p : mPhotos){
            if(p.getPath().equals(path)){
                return p;
            }
        }
        return null;
    }

    public boolean savePhotos(){
        try{
            mSerializer.savePhotos(mPhotos);
            //mSerializer.saveCrimesToExternal(mPhotos);
            Log.d(TAG, "mPhotos saved to file");
            return true;
        }catch(Exception e){
            Log.e(TAG, "Error saving mPhotos: ",e);
            return false;
        }
    }

    public void deletePhoto(Photo p){
        mPhotos.remove(p);
    }
}
