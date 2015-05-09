package com.example.ruhaiwen.funnylife.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.ruhaiwen.funnylife.Config;
import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.ui.base.BaseActivity;

import cn.bmob.v3.Bmob;

/**
 * 软件引导界面
 */
public class SplashActivity extends BaseActivity {

	private static final long DELAY_TIME = 2000L;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
        //Bmob SDK初始化--只需要这一段代码即可完成初始化
        //请到Bmob官网(http://www.bmob.cn/)申请ApplicationId,具体地址:http://docs.bmob.cn/android/faststart/index.html?menukey=fast_start&key=start_android
        Bmob.initialize(this, Config.APPLICATION_ID);
        redirectByTime();
	}
	
	/**
	 * 根据时间进行页面跳转
	 */
	private void redirectByTime() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				redictToActivity(SplashActivity.this, MainActivity.class, null);
				finish();
			}
		}, DELAY_TIME);
	}

}
