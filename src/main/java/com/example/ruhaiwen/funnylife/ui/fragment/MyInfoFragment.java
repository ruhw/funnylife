package com.example.ruhaiwen.funnylife.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.ui.base.BaseHomeFragment;

/**
 * Created by ruhaiwen on 15-4-3.
 */
public class MyInfoFragment extends BaseHomeFragment {

    public static MyInfoFragment newInstance(){
        MyInfoFragment  fragment = new MyInfoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void findViews(View view) {

    }

    @Override
    protected void setupViews(Bundle bundle) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void fetchData() {

    }
}
