package com.example.ruhaiwen.funnylife.ui.base;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.view.ActionBar;


public abstract class BaseHomeActivity extends BaseActivity{
	
	protected ActionBar actionBar;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base_home);
		
		initActionBar();
		
		initFragment();
	}

	private void initActionBar() {
		actionBar = (ActionBar)findViewById(R.id.actionbar_base);
		actionBar.setTitle(getActionBarTitle());
		actionBar.setDisplayHomeAsUpEnabled(isHomeAsUpEnabled()==true?true:false);
		actionBar.setHomeAction(new ActionBar.Action() {

			@Override
			public void performAction(View view) {
				// TODO Auto-generated method stub
				onHomeActionClick();
			}

			@Override
			public int getDrawable() {
				// TODO Auto-generated method stub
				return R.drawable.logo;
			}
		});
		addActions();
	}
	
	private void initFragment(){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.content_frame_base, getFragment())
				.commit();
	}
	
	protected abstract String getActionBarTitle();
	protected abstract boolean isHomeAsUpEnabled();
	protected abstract void onHomeActionClick();
	protected abstract BaseFragment getFragment();
	protected abstract void addActions();
	
	
}
