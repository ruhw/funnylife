package com.example.ruhaiwen.funnylife.ui.base;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.example.ruhaiwen.funnylife.utils.Constant;
import com.example.ruhaiwen.funnylife.utils.Sputil;


/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-2-23
 * TODO
 */

public abstract class BaseFragment extends Fragment {
	public static String TAG;
	protected Context mContext;
	protected Sputil sputil;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		mContext = getActivity();
		if(null == sputil){
			sputil = new Sputil(mContext, Constant.PRE_NAME);
		}
	}

}
