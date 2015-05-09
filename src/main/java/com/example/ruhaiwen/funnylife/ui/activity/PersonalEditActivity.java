package com.example.ruhaiwen.funnylife.ui.activity;

 import android.view.View;

 import com.bmob.BTPFileResponse;
 import com.bmob.BmobProFile;
 import com.bmob.btp.callback.UploadListener;
 import com.bmob.utils.BmobLog;
 import com.example.ruhaiwen.funnylife.R;
 import com.example.ruhaiwen.funnylife.entity.User;
 import com.example.ruhaiwen.funnylife.ui.base.BaseFragment;
 import com.example.ruhaiwen.funnylife.ui.base.BaseHomeActivity;
 import com.example.ruhaiwen.funnylife.ui.fragment.PersonalEditFragment;
 import com.example.ruhaiwen.funnylife.utils.ToastFactory;
 import com.example.ruhaiwen.funnylife.view.ActionBar;

 import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by ruhaiwen on 15-5-1.
 */
public class PersonalEditActivity extends BaseHomeActivity {

    @Override
    protected String getActionBarTitle() {
        return getResources().getString(R.string.edit_myInfo);
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
        return new PersonalEditFragment();
    }

    @Override
    protected void addActions() {

    }

}
