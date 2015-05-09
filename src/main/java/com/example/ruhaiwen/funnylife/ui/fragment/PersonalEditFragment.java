package com.example.ruhaiwen.funnylife.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bmob.utils.BmobLog;
import com.example.ruhaiwen.funnylife.Config;
import com.example.ruhaiwen.funnylife.FunnyLifeApplication;
import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.entity.User;
import com.example.ruhaiwen.funnylife.ui.base.BaseHomeActivity;
import com.example.ruhaiwen.funnylife.ui.base.BaseHomeFragment;
import com.example.ruhaiwen.funnylife.utils.CacheUtils;
import com.example.ruhaiwen.funnylife.utils.LogUtils;
import com.example.ruhaiwen.funnylife.utils.ToastFactory;
import com.example.ruhaiwen.funnylife.view.ActionBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by ruhaiwen on 15-5-2.
 */
public class PersonalEditFragment extends BaseHomeFragment implements View.OnClickListener{

    public static final int RESULT_FROM_ALBUM = 1;
    public static final int RESULT_FRMO_CAMERA = 2;


    RelativeLayout mUserIconLayout;
    ImageView mUserIconImageView;
    EditText mNickNameEditText;
    CheckBox mSexSelectCheckBox;
    EditText mSignatureEditText;
    TextView mEmailResetTextView;
    TextView mChangePasswordTextView;

    User user ;
    String mIconUrl;//如果更换了头像，就不为空，用于上传头像

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((BaseHomeActivity)getActivity()).actionBar;
        actionBar.addAction(new com.example.ruhaiwen.funnylife.view.ActionBar.Action() {
            @Override
            public int getDrawable() {
                return R.drawable.btn_comment_publish;
            }

            @Override
            public void performAction(View view) {
                if(mIconUrl == null){
                    publish(user);
                }else {
                    uploadIconAndSave(mIconUrl);
                }
            }
        });
    }

    public static PersonalEditFragment newInstance(){
        PersonalEditFragment fragment = new PersonalEditFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_edit;
    }

    @Override
    protected void findViews(View view) {
        mUserIconLayout = (RelativeLayout)view.findViewById(R.id.userIconLayout);
        mUserIconImageView = (ImageView)view.findViewById(R.id.userIcon);
        mNickNameEditText = (EditText)view.findViewById(R.id.nickNameEditText);
        mSexSelectCheckBox = (CheckBox)view.findViewById(R.id.sexSelectCheckBox);
        mSignatureEditText = (EditText)view.findViewById(R.id.signatureEditText);
        mEmailResetTextView = (TextView)view.findViewById(R.id.emailResetTextView);
        mChangePasswordTextView = (TextView)view.findViewById(R.id.changePasswordTextView);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        user = FunnyLifeApplication.getInstance().getCurrentUser();
        initUserIcon();
        mNickNameEditText.setText(user.getNickname());
        initUserSex();
        initSignature();
    }

    /**
     * 初始化签名
     */
    private void initSignature() {
        if(!TextUtils.isEmpty(user.getSignature())){
            mSignatureEditText.setText(user.getSignature());
        }
    }

    /**
     * 初始化用户性别
     */
    private void initUserSex() {
        if(user.getSex().equals("male")){
            mSexSelectCheckBox.setChecked(false);
        }
    }

    /**
     * 初始化用户头像
     */
    private void initUserIcon() {
        if(!TextUtils.isEmpty(user.getUserIconName())){
            String userIconUrl = BmobProFile.getInstance(mContext)
                    .signURL(user.getUserIconName(), user.getUserIconUrl(), Config.Access_KEY, 0, null);
            ImageLoader.getInstance()
                    .displayImage(userIconUrl, mUserIconImageView,
                            FunnyLifeApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
                            new SimpleImageLoadingListener(){

                                @Override
                                public void onLoadingComplete(String imageUri, View view,
                                                              Bitmap loadedImage) {
                                    // TODO Auto-generated method stub
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                }
                            });
        }
    }

    @Override
    protected void setListener() {
        mUserIconLayout.setOnClickListener(this);
        mEmailResetTextView.setOnClickListener(this);
        mChangePasswordTextView.setOnClickListener(this);
        mSexSelectCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    user.setSex("female");
                }else {
                    user.setSex("male");
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userIconLayout:
                showAlbumDialog();
                break;
            case R.id.emailResetTextView:
        }
    }

    String dateTime;
    AlertDialog albumDialog;
    public void showAlbumDialog(){
        albumDialog = new AlertDialog.Builder(mContext).create();
        albumDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_usericon, null);
        albumDialog.show();
        albumDialog.setContentView(v);
        albumDialog.getWindow().setGravity(Gravity.CENTER);


        TextView albumPic = (TextView)v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView)v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                albumDialog.dismiss();
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                getIconFromAlbum();
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                albumDialog.dismiss();
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";
                getIconFromCamera();
            }
        });
    }



    private void getIconFromCamera(){
        File f = new File(CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Log.e("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, RESULT_FROM_ALBUM);
    }

    private void getIconFromAlbum(){
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("image/*");
        startActivityForResult(intent2, RESULT_FRMO_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case 1:
                    String files =CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime;
                    File file = new File(files);
                    if(file.exists()&&file.length() > 0){
                        Uri uri = Uri.fromFile(file);
                        startPhotoZoom(uri);
                    }else{

                    }
                    break;
                case 2:
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            //  保存图片
                            mIconUrl = saveToSdCard(bitmap);
                            mUserIconImageView.setImageBitmap(bitmap);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);

    }

    public String saveToSdCard(Bitmap bitmap){
        File file = new File(Environment.getExternalStorageDirectory() + "/funnyLife/" + dateTime + ".png");
        try {
            file.createNewFile();
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LogUtils.i(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**
     * 上传图片并发布条目
     * @param imageUrl
     */
    private void uploadIconAndSave(String imageUrl) {
        BmobProFile.getInstance(mContext).upload(imageUrl, new UploadListener() {

            @Override
            public void onSuccess(String fileName,String url) {
                // TODO Auto-generated method stub
                //dialog.dismiss();
                ToastFactory.getToast(mContext, "文件已上传成功：" + fileName);
                user.setUserIconName(fileName);
                user.setUserIconUrl(url);
                publish(user);
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
     * 发布条目
     * @param user
     */
    private void publish(final User user) {
        updateUser();
        user.update(mContext,new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastFactory.getToast(mContext, "保存成功");
                getActivity().finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastFactory.getToast(mContext, "保存失败");
            }
        });
    }

    /**
     * 更新user信息
     */
    private void updateUser() {
        user.setNickname(mNickNameEditText.getText().toString());
        user.setSignature(mSignatureEditText.getText().toString());
    }

}
