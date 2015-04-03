package com.example.ruhaiwen.funnylife.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.ruhaiwen.funnylife.FunnyLifeApplication;
import com.example.ruhaiwen.funnylife.utils.Constant;
import com.example.ruhaiwen.funnylife.utils.Sputil;

public class BaseActivity extends Activity implements OnSharedPreferenceChangeListener{
	
	protected static String TAG ;
	
	protected FunnyLifeApplication mApplication;
	protected Sputil sputil;
	protected Context mContext;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		initConfigure();
	}


	private void initConfigure() {
		mContext = this;
		if(null == mApplication){
			mApplication = FunnyLifeApplication.getInstance();
		}
		mApplication.addActivity(this);
		if(null == sputil){
			sputil = new Sputil(this, Constant.PRE_NAME);
		}
		sputil.getInstance().registerOnSharedPreferenceChangeListener(this);
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		//可用于监听设置参数，然后作出响应
	}

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
		startActivity(intent);
	}

}
