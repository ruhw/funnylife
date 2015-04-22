package com.example.ruhaiwen.funnylife.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bmob.BmobProFile;
import com.example.ruhaiwen.funnylife.Config;
import com.example.ruhaiwen.funnylife.FunnyLifeApplication;
import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.entity.Publication;
import com.example.ruhaiwen.funnylife.entity.User;
import com.example.ruhaiwen.funnylife.ui.activity.CommentActivity;
import com.example.ruhaiwen.funnylife.ui.activity.PersonalActivity;
import com.example.ruhaiwen.funnylife.ui.activity.RegisterAndLoginActivity;
import com.example.ruhaiwen.funnylife.utils.ActivityUtil;
import com.example.ruhaiwen.funnylife.utils.LogUtils;
import com.example.ruhaiwen.funnylife.utils.ToastFactory;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;


import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by ruhaiwen on 15-4-8.
 */
public class PublicationAdapter extends BaseContentAdapter {
    public static final String TAG = "PublicationAdapter";

    public PublicationAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getConvertView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ai_item, null);
            viewHolder = new PublicationHolder((Publication)dataList.get(position));
            viewHolder.findView(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.setupView(mContext);
        viewHolder.setListener(mContext);

        return convertView;
    }

}
