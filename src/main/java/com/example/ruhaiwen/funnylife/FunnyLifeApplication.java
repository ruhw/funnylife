package com.example.ruhaiwen.funnylife;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.ruhaiwen.funnylife.entity.User;
import com.example.ruhaiwen.funnylife.utils.ActivityManagerUtils;
import com.example.ruhaiwen.funnylife.volley.VolleyImageCache;

import cn.bmob.v3.BmobUser;

/**
 * Created by ruhaiwen on 15-4-2.
 */
public class FunnyLifeApplication extends Application {

    public static FunnyLifeApplication sFunnyLifeApplication = null;

    public FunnyLifeApplication(){
        sFunnyLifeApplication = this;
    }

    //存放volley请求的队列
    public static RequestQueue mRequestQueue;
    //请求和缓存图片的ImageLoader
    public static ImageLoader mImageLoader;
    public static User user;
    @Override
    public void onCreate() {
        super.onCreate();
        initVolley();
    }

    private void initVolley() {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, VolleyImageCache.getInstance(getApplicationContext()));
    }

    public User getCurrentUser() {
        if(user ==null){
            return BmobUser.getCurrentUser(this, User.class);
        }
        return user;
    }
    public static FunnyLifeApplication getInstance(){
        return sFunnyLifeApplication;
    }

    public void addActivity(Activity ac){
        ActivityManagerUtils.getInstance().addActivity(ac);
    }

    public void exit(){
        ActivityManagerUtils.getInstance().removeAllActivity();
    }

    public Activity getTopActivity(){
        return ActivityManagerUtils.getInstance().getTopActivity();
    }




}
