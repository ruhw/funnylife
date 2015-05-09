package com.example.ruhaiwen.funnylife.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by ruhaiwen on 15-4-17.
 */
public class PublicationHolder extends ViewHolder{

    private User user;
    private Publication entity;

    public ImageView userLogo;
    public TextView userName;
    public TextView contentText;
    public ImageView contentImage;
    public ImageView collectMark;
    public TextView love;
    public TextView hate;
    public TextView share;
    public TextView comment;


    public PublicationHolder(Publication entity){
        this.entity = entity;
        user = FunnyLifeApplication.getInstance().getCurrentUser();
    }
    @Override
    public void findView(View convertView) {
        userName = (TextView)convertView.findViewById(R.id.user_name);
        userLogo = (ImageView)convertView.findViewById(R.id.user_logo);
        collectMark = (ImageView)convertView.findViewById(R.id.item_action_fav);
        contentText = (TextView)convertView.findViewById(R.id.content_text);
        contentImage = (ImageView)convertView.findViewById(R.id.content_image);
        love = (TextView)convertView.findViewById(R.id.item_action_love);
        hate = (TextView)convertView.findViewById(R.id.item_action_hate);
        share = (TextView)convertView.findViewById(R.id.item_action_share);
        comment = (TextView)convertView.findViewById(R.id.item_action_comment);

    }

    @Override
    public void setupView(Context context) {
        ImageLoader.getInstance()
                .displayImage(entity.getAuthor().getUserImageUrl(), userLogo,
                        FunnyLifeApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
                        new SimpleImageLoadingListener(){

                            @Override
                            public void onLoadingComplete(String imageUri, View view,
                                                          Bitmap loadedImage) {
                                // TODO Auto-generated method stub
                                super.onLoadingComplete(imageUri, view, loadedImage);
                            }

                        });

        userName.setText(entity.getAuthor().getUsername());
        contentText.setText(entity.getContent());
        if(null == entity.getPictureUrl()){
            contentImage.setVisibility(View.GONE);
        }else{
            String URL = BmobProFile.getInstance(context)
                    .signURL(entity.getPictureName(),entity.getPictureUrl(), Config.Access_KEY, 0, null);
            contentImage.setVisibility(View.VISIBLE);
            ImageLoader.getInstance()
                    .displayImage(URL==null?"":URL, contentImage,
                            FunnyLifeApplication.getInstance().getOptions(R.drawable.bg_pic_loading),
                            new SimpleImageLoadingListener(){

                                @Override
                                public void onLoadingComplete(String imageUri, View view,
                                                              Bitmap loadedImage) {
                                    // TODO Auto-generated method stub
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    float[] cons= ActivityUtil.getBitmapConfiguration(loadedImage, contentImage, 1.0f);
                                    RelativeLayout.LayoutParams layoutParams=
                                            new RelativeLayout.LayoutParams((int)cons[0], (int)cons[1]);
                                    layoutParams.addRule(RelativeLayout.BELOW,R.id.content_text);
                                    contentImage.setLayoutParams(layoutParams);
                                }

                            });
        }

        resetMylove(entity);
        setMyloveView(love,entity,false);
        resetMyCollection(entity);
        setCollectionView(collectMark,entity);

    }

    @Override
    public void setListener(final Context context) {
        userLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(user != null){//已登录
                    redictToActivity(context, PersonalActivity.class, null);
                }else{//未登录
                    redictToActivity(context, RegisterAndLoginActivity.class, null);
                }
            }
        });
        love.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickLove(context,love,entity);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //share to sociaty
                //final TencentShare tencentShare=new TencentShare(MyApplication.getInstance().getTopActivity(), getQQShareEntity(entity));
                //tencentShare.shareToQQ();
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //评论
                if(user == null){
                    redictToActivity(context,RegisterAndLoginActivity.class,null);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(CommentActivity.PUBLICATION,entity);
                    redictToActivity(context, CommentActivity.class, bundle);
                }
            }
        });

        collectMark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //收藏
                onClickCollect(context,v, entity);
            }
        });
    }

    /**
     * 初始化收藏状态
     * @param entity 需要重设收藏状态的条目
     * @return true表示当前用户已收藏
     */
    private boolean resetMyCollection(Publication entity) {
        boolean isMyCollection = false;
        if(user != null && user.getCollections() != null){
            for(BmobPointer bmobPointer : user.getCollections().getObjects()){
                if(bmobPointer.getObjectId().equals(entity.getObjectId())){
                    isMyCollection = true;
                    entity.setMyCollection(isMyCollection);
                    break;
                }
            }
        }
        return isMyCollection;
    }

    /**
     * 初始化点赞状态
     * @param entity 需要重设点赞状态的条目
     * @return true表示当前用户已点赞
     */
    private boolean resetMylove(Publication entity) {
        boolean isMylove = false;
        if(user != null && user.getMyLoves() != null){//已登录
            for(BmobPointer bmobPointer : user.getMyLoves().getObjects()){
                if(bmobPointer.getObjectId().equals(entity.getObjectId())){
                    isMylove = true;
                    entity.setMylove(isMylove);
                    break;
                }
            }
        }
        return isMylove;
    }

    /**
     * 点击点赞按钮处理事件
     * @param love 点赞成功更新iew
     * @param entity 需要更新的条目
     */
    private void onClickLove(final Context context,final TextView love,final Publication entity) {
        if(user != null && user.getSessionToken()!=null){
            if(entity.isMylove()){
                entity.increment("loveNum",-1);
                //myLoves.remove(entity);
            }else {
                entity.increment("loveNum",1);
                //myLoves.add(entity);
            }
            entity.update(context, new UpdateListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    //LogUtils.i(TAG, "点赞成功~");
                    entity.setMylove(!entity.isMylove());
                    setMyloveView(love, entity,true);
                    addToMyLoves(context,entity);
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    // TODO Auto-generated method stub
                }
            });
        }else {
            redictToActivity(context, RegisterAndLoginActivity.class, null);
        }
    }

    /**
     * 根据点赞状态设置view的状态
     * @param love
     */
    private void setMyloveView(TextView love,Publication entity,boolean isFromUpdate) {
        boolean isMylove = entity.isMylove();
        if(isFromUpdate && isMylove){
            love.setTextColor(Color.parseColor("#D95555"));
            entity.setLoveNum(entity.getLoveNum() + 1);
        }else if(isFromUpdate && !isMylove){
            love.setTextColor(Color.parseColor("#000000"));
            entity.setLoveNum(entity.getLoveNum() - 1);
        }else if(!isFromUpdate && isMylove){
            love.setTextColor(Color.parseColor("#D95555"));
        }else {
            love.setTextColor(Color.parseColor("#000000"));
        }
        love.setText(entity.getLoveNum() + "");
    }

    /**
     * 根据用户收藏状态设置view的显示
     * @param v
     */
    private void setCollectionView(View v,Publication entity) {
        if(entity.isMyCollection()){
            ((ImageView)v).setImageResource(R.drawable.ic_action_fav_choose);
        }else{
            ((ImageView)v).setImageResource(R.drawable.ic_action_fav_normal);
        }
    }

    /**
     * 点击Collect按钮处理事件
     * @param v
     * @param publication
     */
    private void onClickCollect(Context context,final View v, final Publication publication) {
        // TODO Auto-generated method stub
        if(user != null && user.getSessionToken()!=null){
            BmobRelation collectRelation = new BmobRelation();
            if(publication.isMyCollection()){
                collectRelation.remove(publication);
            }else{
                collectRelation.add(publication);
            }
            user.setCollections(collectRelation);
            user.update(context, new UpdateListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    //LogUtils.i(TAG, "收藏成功。");
                    publication.setMyCollection(!publication.isMyCollection());
                    setCollectionView(v, publication);
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    // TODO Auto-generated method stub
                    //LogUtils.i(TAG, "收藏失败。请检查网络~");
                }
            });
        }else{
            //前往登录注册界面
            redictToActivity(context, RegisterAndLoginActivity.class, null);
        }
    }

    /**
     * 将发布的条目添加到用户的信息中
     * @param publication
     */
    private void addToMyLoves(final Context context,Publication publication){

        BmobRelation myLoves = new BmobRelation();
        if(publication.isMylove()){
            myLoves.add(publication);
        }else{
            myLoves.remove(publication);
        }
        user.setMyLoves(myLoves);
        user.update(context, new UpdateListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ToastFactory.getToast(context, "已成功添加到用户的已发布条目信息中");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                ToastFactory.getToast(context, "很遗憾，用户的已发布条目信息添加失败");
            }
        });
    }
}
