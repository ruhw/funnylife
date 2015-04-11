package com.example.ruhaiwen.funnylife.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.ruhaiwen.funnylife.ui.fragment.MainFragment;
import com.example.ruhaiwen.funnylife.ui.fragment.MyInfoFragment;
import com.example.ruhaiwen.funnylife.ui.fragment.MyRelatedFragment;
import com.example.ruhaiwen.funnylife.ui.fragment.MyloveFragment;

/**
 * Created by ruhaiwen on 14-12-22.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private final String[] titles = { "主页", "收藏","通知","个人" };

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
                return MainFragment.newInstance();
            case 1:
                return MyloveFragment.newInstance();
            case 2:
                return MyRelatedFragment.newInstance();
            case 3:
                return MyInfoFragment.newInstance();

            default:
                return null;
        }
    }



}
