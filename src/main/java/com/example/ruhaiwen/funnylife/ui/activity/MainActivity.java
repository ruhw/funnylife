package com.example.ruhaiwen.funnylife.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;


import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.adapter.MyPagerAdapter;
import com.example.ruhaiwen.funnylife.ui.base.BasePageActivity;
import com.example.ruhaiwen.funnylife.view.PagerSlidingTabStrip;

/**
 * Created by ruhaiwen on 15-4-3.
 */
public class MainActivity extends BasePageActivity{

    private PagerSlidingTabStrip mTabs;
    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;

    private ViewPager mViewPager;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViews() {
        dm = getResources().getDisplayMetrics();
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        mTabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mTabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mTabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        mTabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置Tab标题文字的大小
        mTabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        mTabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        mTabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        mTabs.setTabBackground(0);
    }


    @Override
    protected void setupViews(Bundle bundle) {
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        mTabs.setViewPager(mViewPager);
        setTabsValue();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void fetchData() {

    }
}
