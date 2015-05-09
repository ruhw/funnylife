package com.example.ruhaiwen.funnylife.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.ui.activity.AboutActivity;
import com.example.ruhaiwen.funnylife.ui.base.BaseActivity;
import com.example.ruhaiwen.funnylife.ui.base.BaseHomeFragment;

/**
 * Created by ruhaiwen on 15-5-3.
 */
public class SettingsFragment extends BaseHomeFragment implements View.OnClickListener{

    CheckBox mPushCheckBox;
    TextView mClearCacheTextView;
    TextView mCheckUpdateTextView;
    TextView mAboutAppTextView;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void findViews(View view) {
        mPushCheckBox = (CheckBox)view.findViewById(R.id.settings_push_switch);
        mClearCacheTextView = (TextView)view.findViewById(R.id.settings_cache_tips);
        mCheckUpdateTextView = (TextView)view.findViewById(R.id.settings_update_tips);
        mAboutAppTextView = (TextView)view.findViewById(R.id.about_app);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        mPushCheckBox.setChecked(sputil.getValue("isPushOn", true));
    }

    @Override
    protected void setListener() {
        mAboutAppTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_app:
                ((BaseActivity)getActivity()).redictToActivity(mContext, AboutActivity.class,null);
        }
    }
}
