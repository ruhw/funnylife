package com.example.ruhaiwen.funnylife.ui.activity;


import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.ui.base.BaseFragment;
import com.example.ruhaiwen.funnylife.ui.base.BaseHomeActivity;
import com.example.ruhaiwen.funnylife.ui.fragment.SettingsFragment;

/**
 * Created by ruhaiwen on 15-4-3.
 */
public class SettingsActivity extends BaseHomeActivity {
    @Override
    protected String getActionBarTitle() {
        return getResources().getString(R.string.settings);
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onHomeActionClick() {
        this.finish();
    }

    @Override
    protected BaseFragment getFragment() {
        return SettingsFragment.newInstance();
    }

    @Override
    protected void addActions() {

    }
}
