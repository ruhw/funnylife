package com.example.ruhaiwen.funnylife.ui.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.ruhaiwen.funnylife.FunnyLifeApplication;
import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.entity.Publication;
import com.example.ruhaiwen.funnylife.ui.activity.PublicationEditActivity;
import com.example.ruhaiwen.funnylife.ui.activity.RegisterAndLoginActivity;
import com.example.ruhaiwen.funnylife.ui.base.BaseActivity;
import com.example.ruhaiwen.funnylife.ui.base.BaseContentFragment;
import com.example.ruhaiwen.funnylife.utils.ActivityUtil;
import com.example.ruhaiwen.funnylife.utils.Constant;
import com.example.ruhaiwen.funnylife.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by ruhaiwen on 15-4-3.
 */
public class MainFragment extends BaseContentFragment {

    private static final int REQUEST_CODE_ALBUM = 1;
    private static final int REQUEST_CODE_CAMERA = 2;
    PopupWindow mPopupWindow;
    private int xOffset;//偏移
    private int yOffset;
    private String dateTime;

    public static MainFragment newInstance(){
        MainFragment  fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void fetchData(){
        setState(LOADING);
        BmobQuery<Publication> query = new BmobQuery<Publication>();
        query.order("-createdAt");
        query.setLimit(Constant.NUMBERS_PER_PAGE);
        BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
        query.addWhereLessThan("createdAt", date);
        query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
        query.include("author");
        query.findObjects(getActivity(), new FindListener<Publication>() {

            @Override
            public void onSuccess(List<Publication> list) {
                // TODO Auto-generated method stub
                LogUtils.i(TAG,"find success."+list.size());
                if(list.size()!=0&&list.get(list.size()-1)!=null){
                    if(mRefreshType==RefreshType.REFRESH){
                        mListItems.clear();
                    }
                    if(list.size()<Constant.NUMBERS_PER_PAGE){
                        ActivityUtil.show(getActivity(), "已加载完所有数据~");
                    }
                    mListItems.addAll(list);
                    mAdapter.notifyDataSetChanged();

                    LogUtils.i(TAG,"DD"+(mListItems.get(mListItems.size()-1)==null));
                    setState(LOADING_COMPLETED);
                    mPullRefreshListView.onRefreshComplete();
                }else{
                    ActivityUtil.show(getActivity(), "暂无更多数据~");
                    if(list.size()==0&&mListItems.size()==0){

                        networkTips.setText("暂无数据。。。");
                        setState(LOADING_FAILED);
                        pageNum--;
                        mPullRefreshListView.onRefreshComplete();

                        LogUtils.i(TAG,"SIZE:"+list.size()+"ssssize"+mListItems.size());
                        return;
                    }
                    pageNum--;
                    setState(LOADING_COMPLETED);
                    mPullRefreshListView.onRefreshComplete();
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                LogUtils.i(TAG,"find failed."+arg1);
                pageNum--;
                setState(LOADING_FAILED);
                mPullRefreshListView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_main,menu);
        initPopupWindow();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_publish:
                checkLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    String[] mTypes = new String[]{"添加图片","拍照添加"};

    private void initPopupWindow() {
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //状态栏高度：frame.top
        xOffset = frame.top + getActivity().getActionBar().getHeight();//减去阴影宽度，适配UI.
        yOffset = ActivityUtil.dip2px(getActivity(), 10f); //设置x方向offset为5dp
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.popup,null);
        ListView popListView = (ListView)v.findViewById(R.id.popListView);
        //初始化popupWindow
        mPopupWindow = new PopupWindow(v, 600, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.background_color));
        //初始化listView的数据
        ArrayAdapter adapter = new ArrayAdapter(mContext,
                android.R.layout.simple_list_item_1,mTypes);
        popListView.setAdapter(adapter);
        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        initGetImageIntent();
                        break;
                    case 1:
                        initTakePhotoIntent();
                        break;
                    default:
                        break;
                }
                mPopupWindow.dismiss();
            }
        });
    }

    private void initTakePhotoIntent() {
        Date date = new Date(System.currentTimeMillis());
        dateTime = date.getTime() + "";
        //File f = new File(CacheUtils.getCacheDirectory(mContext, true, "pic/") + dateTime);
        File f = new File(Environment.getExternalStorageDirectory() + "/funnyLife/" + dateTime);
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
        startActivityForResult(camera, REQUEST_CODE_CAMERA);
    }

    private void initGetImageIntent() {
//        Date date1 = new Date(System.currentTimeMillis());
//        dateTime = date1.getTime() + "";
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    private void checkLogin() {
        BmobUser currentUser = FunnyLifeApplication.getInstance().getCurrentUser();
        if (currentUser != null) {
            // 允许用户使用应用,即有了用户的唯一标识符，可以作为发布内容的字段
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();// 关闭
            } else {
                mPopupWindow.showAtLocation(contentView, Gravity.RIGHT | Gravity.TOP,
                        yOffset, xOffset);// 显示
            }
        } else {
            // 缓存用户对象为空时， 可打开用户注册界面…
            Toast.makeText(getActivity(), "请先登录。",
                    Toast.LENGTH_SHORT).show();
            ((BaseActivity)getActivity()).redictToActivity(mContext, RegisterAndLoginActivity.class, null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            String filePath = null;
            switch (requestCode) {
                case REQUEST_CODE_ALBUM:
                    if(data!=null){
                        Uri originalUri = data.getData();
                        ContentResolver cr = mContext.getContentResolver();
                        Cursor cursor = cr.query(originalUri, null, null, null, null);
                        if(cursor.moveToFirst()){
                            do{
                                filePath = cursor.getString(cursor.getColumnIndex("_data"));
                                LogUtils.i(TAG,"get album:"+filePath);
                            }while (cursor.moveToNext());
                        }
                        jumpToActivity(filePath);
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    //filePath =CacheUtils.getCacheDirectory(mContext, true, "pic") + dateTime;
                    filePath = Environment.getExternalStorageDirectory() + "/funnyLife/" + dateTime;
                    jumpToActivity(filePath);
                    break;
                default:
                    break;
            }
        }
    }

    private void jumpToActivity(String filePath) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.IMAGE_URL,filePath);
        ((BaseActivity)getActivity()).redictToActivity(mContext, PublicationEditActivity.class,bundle);
    }


}
