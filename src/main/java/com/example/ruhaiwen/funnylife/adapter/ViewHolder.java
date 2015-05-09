package com.example.ruhaiwen.funnylife.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.ruhaiwen.funnylife.FunnyLifeApplication;

/**
 * Created by ruhaiwen on 15-4-21.
 */
public abstract class ViewHolder {

    /**
     *
     * @param convertView 需要显示的view
     * @return 返回给adapter显示
     */
    public abstract void findView(View convertView);

    /**
     * 初始化view的状态
     */
    public abstract void setupView(Context context);

    /**
     * 设置view的监听器
     */
    public abstract void setListener(Context context);

    /**
     * Activity跳转
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void redictToActivity(Context context,Class<?> targetActivity,Bundle bundle){
        Intent intent = new Intent(context, targetActivity);
        if(null != bundle){
            intent.putExtras(bundle);
        }
        FunnyLifeApplication.getInstance().getTopActivity().startActivity(intent);
    }
}
