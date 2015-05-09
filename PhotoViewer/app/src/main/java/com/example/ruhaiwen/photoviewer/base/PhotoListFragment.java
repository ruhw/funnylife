package com.example.ruhaiwen.photoviewer.base;


import android.os.Bundle;

import com.example.ruhaiwen.photoviewer.model.Photo;

/**
 * create an instance of this fragment.
 */
public class PhotoListFragment extends BaseListFragment {

   private static PhotoListFragment mPhotoListFragment;

    public static PhotoListFragment newInstance() {
        if(mPhotoListFragment == null){
            mPhotoListFragment = new PhotoListFragment();
        }
        return mPhotoListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Photo mPhoto1 = new Photo("path","name","suffix","1kb");
        Photo mPhoto2 = new Photo("path2","name2","suffix2","2kb");
        mPhotos.add(mPhoto1);
        mPhotos.add(mPhoto2);
    }
}
