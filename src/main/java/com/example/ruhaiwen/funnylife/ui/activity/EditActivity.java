package com.example.ruhaiwen.funnylife.ui.activity;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.LocalThumbnailListener;
import com.bmob.btp.callback.UploadListener;
import com.bmob.utils.BmobLog;
import com.example.ruhaiwen.funnylife.FunnyLifeApplication;
import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.entity.Publication;
import com.example.ruhaiwen.funnylife.entity.User;
import com.example.ruhaiwen.funnylife.ui.base.BasePageActivity;
import com.example.ruhaiwen.funnylife.utils.ActivityUtil;
import com.example.ruhaiwen.funnylife.utils.Constant;
import com.example.ruhaiwen.funnylife.utils.LogUtils;
import com.example.ruhaiwen.funnylife.utils.ToastFactory;
import com.example.ruhaiwen.funnylife.view.ActionBar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by ruhaiwen on 15-4-3.
 */
public class EditActivity extends BasePageActivity {

    private ActionBar mActionBar;
    private ImageView mImageView;
    private EditText mEditText;
    private TextView mCountTextView;
    private String imageUrl;
    private String thumbPath;
    private User user = FunnyLifeApplication.getInstance().getCurrentUser();
    private String commitContent;
    private final int mEditTextMaxCount = 140;

    @Override
    protected void setLayoutView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit);
    }

    @Override
    protected void findViews() {
        mActionBar = (ActionBar)findViewById(R.id.actionbar_edit);
        mImageView = (ImageView)findViewById(R.id.editImageView);
        mEditText = (EditText)findViewById(R.id.edit_content);
        mCountTextView = (TextView)findViewById(R.id.showNumTextView);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initActionBar();
        imageUrl = getImageUrl();
        compressImage(imageUrl, 6, 90, 90, 100);
        mCountTextView.setText(0 + "/" + mEditTextMaxCount);
    }

    private void initActionBar() {
        mActionBar.setTitle("发表作品");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAction(new ActionBar.Action() {

            @Override
            public void performAction(View view) {
                // TODO Auto-generated method stub
                finish();
            }

            @Override
            public int getDrawable() {
                // TODO Auto-generated method stub
                return R.drawable.icon_app;
            }
        });

        mActionBar.addAction(new ActionBar.Action() {

            @Override
            public void performAction(View view) {
                // TODO Auto-generated method stub
                commitContent = mEditText.getText().toString().trim();
                if (TextUtils.isEmpty(commitContent)) {
                    ActivityUtil.show(EditActivity.this, "内容不能为空");
                    return;
                }
                compressImage(imageUrl, 1, 400, 400, 100);
            }

            @Override
            public int getDrawable() {
                // TODO Auto-generated method stub
                return R.drawable.btn_comment_publish;
            }
        });
    }

    /**
     * 发布条目
     * @param publication
     */
    private void publish(final Publication publication) {

        publication.save(mContext, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ActivityUtil.show(EditActivity.this, "发表成功！");
                LogUtils.i(TAG, "创建成功。");
                setResult(RESULT_OK);
                addPublicationToUser(publication);
                finish();
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                ActivityUtil.show(EditActivity.this, "发表失败！yg" + arg1);
                LogUtils.i(TAG, "创建失败。" + arg1);
            }
        });
    }

    /**
     * 将发布的条目添加到用户的信息中
     * @param publication
     */
    private void addPublicationToUser(Publication publication){
        if(TextUtils.isEmpty(user.getObjectId()) ||
                TextUtils.isEmpty(publication.getObjectId())){
            ToastFactory.getToast(mContext, "当前用户或者当前Publication对象的object为空");
            return;
        }

        BmobRelation publications = new BmobRelation();
        publications.add(publication);
        user.setPublications(publications);
        user.update(this, new UpdateListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ToastFactory.getToast(mContext, "已成功添加到用户的已发布条目信息中");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                ToastFactory.getToast(mContext, "很遗憾，用户的已发布条目信息添加失败");
            }
        });
    }

    /**
     * 压缩图片
     * @param imageUrl
     * @param modeId 显示模式,1表示指定宽，高自适应，等比例缩放，其他值参考bmob官方文档
     *               6--固定宽高，居中裁剪
     */
    private void compressImage(String imageUrl,int modeId,int width,int height,int quality) {
        BmobProFile.getInstance(mContext).getLocalThumbnail(imageUrl, modeId, width, height, quality,new LocalThumbnailListener() {

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                ToastFactory.getToast(mContext,"本地缩略图创建失败 :"+statuscode+","+errormsg);
            }

            @Override
            public void onSuccess(String thumbnailPath) {
                // TODO Auto-generated method stub
                ToastFactory.getToast(mContext,"本地缩略图创建成功  :"+thumbnailPath);
                if(thumbPath == null){
                    thumbPath = thumbnailPath;
                    mImageView.setImageBitmap(BitmapFactory.decodeFile(thumbPath));
                }else {
                    thumbPath = thumbnailPath;
                    uploadImageAndPublish(thumbPath, commitContent);
                }
            }
        });

    }

    /**
     * 上传图片并发布条目
     * @param imageUrl
     */
    private void uploadImageAndPublish(String imageUrl, final String commitContent) {
        BTPFileResponse response = BmobProFile.getInstance(mContext).upload(imageUrl, new UploadListener() {

            @Override
            public void onSuccess(String fileName,String url) {
                // TODO Auto-generated method stub
                //dialog.dismiss();
                ToastFactory.getToast(mContext,"文件已上传成功：" + fileName);
                Publication publication = initPublication(commitContent,fileName,url);
                publish(publication);
            }

            @Override
            public void onProgress(int ratio) {
                // TODO Auto-generated method stub
                BmobLog.i("MainActivity -onProgress :" + ratio);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                ToastFactory.getToast(mContext,"上传出错："+errormsg);
            }
        });
    }

    /**
     * 初始化publication,用于发布
     * @param commitContent
     * @param fileName
     * @param url
     */
    private Publication initPublication(String commitContent, String fileName, String url) {
        Publication publication = new Publication();
        publication.setAuthor(user);
        publication.setPictureName(fileName);
        publication.setPictureUrl(url);
        publication.setContent(commitContent);
        return publication;
    }

    /**
     * 获取原图的
     * @return
     */
    private String getImageUrl() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String imageUrl = bundle.getString(Constant.IMAGE_URL);
        return imageUrl;
    }

    @Override
    protected void setListener() {

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() <= mEditTextMaxCount){
                    mCountTextView.setText(s.length() + "/" + mEditTextMaxCount);
                }
            }
        });
    }

    @Override
    protected void fetchData() {

    }
}
