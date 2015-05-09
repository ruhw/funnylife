package com.example.ruhaiwen.funnylife.ui.base;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.adapter.BaseContentAdapter;
import com.example.ruhaiwen.funnylife.adapter.PublicationAdapter;
import com.example.ruhaiwen.funnylife.entity.Publication;
import com.example.ruhaiwen.funnylife.library.PullToRefreshBase;
import com.example.ruhaiwen.funnylife.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public abstract class BaseContentFragment extends BaseFragment{

    protected int pageNum;
    protected String lastItemTime;

    protected View contentView;
    protected ArrayList<Publication> mListItems = new ArrayList<Publication>();
    protected PullToRefreshListView mPullRefreshListView;
    protected BaseContentAdapter<Publication> mAdapter;
    protected ListView actualListView;

    protected TextView networkTips;
    protected ProgressBar progressbar;
    protected boolean pullFromUser;
    public enum RefreshType{
        REFRESH,LOAD_MORE
    }
    protected RefreshType mRefreshType = RefreshType.LOAD_MORE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        pageNum = 0;
        lastItemTime = getCurrentTime();
    }

    private String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times = formatter.format(new Date(System.currentTimeMillis()));
        return times;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_qiangcontent,container,false);
        findViews(contentView);
        setupViews(savedInstanceState);
        setListener();
        fetchData();
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mListItems.size() == 0){
            fetchData();
        }
    }

    private void findViews(View contentView) {
        mPullRefreshListView = (PullToRefreshListView)contentView
                .findViewById(R.id.pull_refresh_list);
        networkTips = (TextView)contentView.findViewById(R.id.networkTips);
        progressbar = (ProgressBar)contentView.findViewById(R.id.progressBar);
    }

    private void setupViews(Bundle savedInstanceState) {
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        actualListView = mPullRefreshListView.getRefreshableView();
        mAdapter = new PublicationAdapter(mContext.getApplicationContext(),mListItems);
        actualListView.setAdapter(mAdapter);
    }

    private void setListener() {
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                pullFromUser = true;
                mRefreshType = RefreshType.REFRESH;
                pageNum = 0;
                lastItemTime = getCurrentTime();
                fetchData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                mRefreshType = RefreshType.LOAD_MORE;
                fetchData();
            }
        });
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                // TODO Auto-generated method stub

            }
        });
        actualListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
            }
        });

    }

    public abstract void fetchData();

    protected static final int LOADING = 1;
    protected static final int LOADING_COMPLETED = 2;
    protected static final int LOADING_FAILED =3;
    protected static final int NORMAL = 4;
    protected void setState(int state){
        switch (state) {
            case LOADING:
                if(mListItems.size() == 0){
                    mPullRefreshListView.setVisibility(View.GONE);
                    progressbar.setVisibility(View.VISIBLE);
                }
                networkTips.setVisibility(View.GONE);

                break;
            case LOADING_COMPLETED:
                networkTips.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);

                mPullRefreshListView.setVisibility(View.VISIBLE);
                mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);


                break;
            case LOADING_FAILED:
                if(mListItems.size()==0){
                    mPullRefreshListView.setVisibility(View.VISIBLE);
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    networkTips.setVisibility(View.VISIBLE);
                }
                progressbar.setVisibility(View.GONE);
                break;
            case NORMAL:

                break;
            default:
                break;
        }
    }
}
