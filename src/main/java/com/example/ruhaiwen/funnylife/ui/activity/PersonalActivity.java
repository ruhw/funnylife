package com.example.ruhaiwen.funnylife.ui.activity;


import android.content.Intent;
import android.os.Bundle;

import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.ui.base.BaseFragment;
import com.example.ruhaiwen.funnylife.ui.base.BaseHomeActivity;
import com.example.ruhaiwen.funnylife.ui.fragment.CommentsFragment;
import com.example.ruhaiwen.funnylife.ui.fragment.MyRelatedFragment;
import com.example.ruhaiwen.funnylife.ui.fragment.UpLoadsFragment;
import com.example.ruhaiwen.funnylife.ui.fragment.UpVotesFragment;

/**
 * Created by ruhaiwen on 15-4-3.
 */
public class PersonalActivity extends BaseHomeActivity {
    public static final String TPYE = "type";
    public static final int ALL = 0;
    public static final int UPVOTES = 1;
    public static final int UPLOADS = 2;
    public static final int COMMENTS = 3;

    @Override
    protected String getActionBarTitle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int type = bundle.getInt(PersonalActivity.TPYE);
        switch (type){
            case ALL:
                return getResources().getString(R.string.related);
            case UPVOTES:
                return getResources().getString(R.string.upVotes);
            case UPLOADS:
                return getResources().getString(R.string.upLoads);
            case COMMENTS:
                return getResources().getString(R.string.comments);
        }
        return getResources().getString(R.string.related);
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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int type = bundle.getInt(PersonalActivity.TPYE);
        switch (type){
            case ALL:
                return MyRelatedFragment.newInstance();
            case UPVOTES:
                return UpVotesFragment.newInstance();
            case UPLOADS:
                return UpLoadsFragment.newInstance();
            case COMMENTS:
                return CommentsFragment.newInstance();
        }
        return MyRelatedFragment.newInstance();
    }

    @Override
    protected void addActions() {

    }
}
