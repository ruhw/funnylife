package com.example.ruhaiwen.funnylife.ui.fragment;

import com.example.ruhaiwen.funnylife.entity.Publication;
import com.example.ruhaiwen.funnylife.entity.User;
import com.example.ruhaiwen.funnylife.ui.base.BaseContentFragment;
import com.example.ruhaiwen.funnylife.utils.ActivityUtil;
import com.example.ruhaiwen.funnylife.utils.Constant;
import com.example.ruhaiwen.funnylife.utils.LogUtils;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by ruhaiwen on 15-5-2.
 */
public class UpLoadsFragment extends BaseContentFragment {
    public static UpLoadsFragment newInstance(){
        UpLoadsFragment upLoadsFragment = new UpLoadsFragment();
        return upLoadsFragment;
    }

    @Override
    public void fetchData() {
        setState(LOADING);
        User user = BmobUser.getCurrentUser(mContext, User.class);
        BmobQuery<Publication> query = new BmobQuery<Publication>();
        query.addWhereRelatedTo("publications", new BmobPointer(user));
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
                LogUtils.i(TAG, "find success." + list.size());
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
                    if(list.size() == 0 && mListItems.size() == 0){

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
}
