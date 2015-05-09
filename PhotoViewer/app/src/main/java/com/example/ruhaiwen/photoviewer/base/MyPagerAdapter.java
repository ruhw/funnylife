package com.example.ruhaiwen.photoviewer.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ruhaiwen on 14-12-22.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private final String[] titles = { "全部", "收藏" };

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PhotoListFragment mPhotoListFragment = PhotoListFragment.newInstance();
                return mPhotoListFragment;
            case 1:
                FavoritesPhotoListFragment mFavoritesPhotoListFragment = FavoritesPhotoListFragment.newInstance();
                return mFavoritesPhotoListFragment;

            default:
                return null;
        }
    }



}
