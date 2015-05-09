package com.example.ruhaiwen.photoviewer.base;


import android.os.Bundle;

import com.example.ruhaiwen.photoviewer.model.Photo;


/**
 * create an instance of this fragment.
 */
public class FavoritesPhotoListFragment extends BaseListFragment {

    private static FavoritesPhotoListFragment mFavoritesPhotoListFragment;

    public static FavoritesPhotoListFragment newInstance() {
        if(mFavoritesPhotoListFragment == null){
            mFavoritesPhotoListFragment = new FavoritesPhotoListFragment();
        }
        return mFavoritesPhotoListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Photo mPhoto3 = new Photo("path3","name3","suffix3","3kb");
        Photo mPhoto4 = new Photo("path4","name4","suffix4","4kb");
        Photo mPhoto5 = new Photo("path5","name5","suffix5","5kb");
        Photo mPhoto6 = new Photo("path6","name6","suffix4","6kb");
        mPhotos.add(mPhoto3);
        mPhotos.add(mPhoto4);
        mPhotos.add(mPhoto5);
        mPhotos.add(mPhoto6);
    }
}
