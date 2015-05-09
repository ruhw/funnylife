package com.example.ruhaiwen.funnylife.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bmob.BmobProFile;
import com.example.ruhaiwen.funnylife.Config;
import com.example.ruhaiwen.funnylife.FunnyLifeApplication;
import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.entity.User;
import com.example.ruhaiwen.funnylife.ui.activity.MainActivity;
import com.example.ruhaiwen.funnylife.ui.activity.PersonalEditActivity;
import com.example.ruhaiwen.funnylife.ui.activity.PersonalActivity;
import com.example.ruhaiwen.funnylife.ui.activity.RegisterAndLoginActivity;
import com.example.ruhaiwen.funnylife.ui.activity.SettingsActivity;
import com.example.ruhaiwen.funnylife.ui.base.BaseActivity;
import com.example.ruhaiwen.funnylife.ui.base.BaseHomeFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import cn.bmob.v3.BmobUser;

/**
 * Created by ruhaiwen on 15-4-3.
 */
public class MyInfoFragment extends BaseHomeFragment implements View.OnClickListener{

    private RelativeLayout mInfoLayout;
    private ImageView mIconImageView;
    private TextView mNickNameTextView;
    private TextView mEditInfoTextView;
    private TextView mSettingTextView;
    private TextView mUpvoteTextView;
    private TextView mUploadTextView;
    private TextView mCommentTextView;
    private TextView mLogoutTextView;

    public static MyInfoFragment newInstance(){
        MyInfoFragment  fragment = new MyInfoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myinfo;
    }

    @Override
    protected void findViews(View view) {
        mInfoLayout = (RelativeLayout)view.findViewById(R.id.myInfo);
        mIconImageView = (ImageView)view.findViewById(R.id.myIcon);
        mNickNameTextView = (TextView)view.findViewById(R.id.myNickName);
        mEditInfoTextView = (TextView)view.findViewById(R.id.edit_myInfo);
        mSettingTextView = (TextView)view.findViewById(R.id.settings);
        mUpvoteTextView = (TextView)view.findViewById(R.id.upVotes);
        mUploadTextView = (TextView)view.findViewById(R.id.upLoads);
        mCommentTextView = (TextView)view.findViewById(R.id.comments);
        mLogoutTextView = (TextView)view.findViewById(R.id.logout);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        User currentUser = FunnyLifeApplication.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userIconUrl = BmobProFile.getInstance(mContext)
                    .signURL(currentUser.getUserIconName(), currentUser.getUserIconUrl(), Config.Access_KEY, 0, null);
            ImageLoader.getInstance()
                    .displayImage(userIconUrl, mIconImageView,
                            FunnyLifeApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
                            new SimpleImageLoadingListener(){

                                @Override
                                public void onLoadingComplete(String imageUri, View view,
                                                              Bitmap loadedImage) {
                                    // TODO Auto-generated method stub
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                }

                            });
            mNickNameTextView.setText(currentUser.getUsername());
        }
    }

    @Override
    protected void setListener() {
        mInfoLayout.setOnClickListener(this);
        mEditInfoTextView.setOnClickListener(this);
        mSettingTextView.setOnClickListener(this);
        mUpvoteTextView.setOnClickListener(this);
        mUploadTextView.setOnClickListener(this);
        mCommentTextView.setOnClickListener(this);
        mLogoutTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BmobUser currentUser = FunnyLifeApplication.getInstance().getCurrentUser();
        if(currentUser == null) {
            ((MainActivity) getActivity()).redictToActivity(mContext, RegisterAndLoginActivity.class, null);
        }else{
            Bundle bundle = new Bundle();
            switch (v.getId()){
                case R.id.myInfo:
                    bundle.putInt(PersonalActivity.TPYE, PersonalActivity.ALL);
                    ((BaseActivity)getActivity()).redictToActivity(mContext, PersonalActivity.class, bundle);
                    break;
                case R.id.edit_myInfo:
                    ((BaseActivity)getActivity()).redictToActivity(mContext,PersonalEditActivity.class,null);
                    break;
                case R.id.settings:
                    ((BaseActivity)getActivity()).redictToActivity(mContext, SettingsActivity.class,null);
                    break;
                case R.id.upVotes:
                    bundle.putInt(PersonalActivity.TPYE,PersonalActivity.UPVOTES);
                    ((BaseActivity)getActivity()).redictToActivity(mContext, PersonalActivity.class,bundle);
                    break;
                case R.id.upLoads:
                    bundle.putInt(PersonalActivity.TPYE,PersonalActivity.UPLOADS);
                    ((BaseActivity)getActivity()).redictToActivity(mContext,PersonalActivity.class,bundle);
                    break;
                case R.id.comments:
                    bundle.putInt(PersonalActivity.TPYE,PersonalActivity.COMMENTS);
                    ((BaseActivity)getActivity()).redictToActivity(mContext,PersonalActivity.class,bundle);
                    break;
                case R.id.logout:
                    BmobUser.logOut(mContext);
                    ((BaseActivity) getActivity()).redictToActivity(mContext, RegisterAndLoginActivity.class, null);
                    break;
            }
        }

    }

}
