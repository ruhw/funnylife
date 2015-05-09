package com.example.ruhaiwen.funnylife.ui.activity;


import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.ui.base.BaseFragment;
import com.example.ruhaiwen.funnylife.ui.base.BaseHomeActivity;
import com.example.ruhaiwen.funnylife.ui.fragment.AboutFragment;


/**
 * Created by ruhaiwen on 15-4-3.
 */
public class AboutActivity extends BaseHomeActivity {

    @Override
    protected String getActionBarTitle() {
        // TODO Auto-generated method stub
        return getString(R.string.about_title);
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void onHomeActionClick() {
        // TODO Auto-generated method stub
        this.finish();
    }

    @Override
    protected BaseFragment getFragment() {
        // TODO Auto-generated method stub
        return AboutFragment.newInstance();
    }

    @Override
    protected void addActions() {
        // TODO Auto-generated method stub

    }

}
