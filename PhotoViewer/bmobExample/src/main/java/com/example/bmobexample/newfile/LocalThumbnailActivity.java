package com.example.bmobexample.newfile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.LocalThumbnailListener;
import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;

/**
 * 本地生成缩略图
 */
public class LocalThumbnailActivity extends BaseActivity{

	EditText edit_ratio,edit_width,edit_height,edit_quality;
	Button btn_thumbnail,btn_thumbnail1,btn_thumbnail2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_thumbnail);
		btn_thumbnail = (Button)findViewById(R.id.btn_thumbnail);
		btn_thumbnail1 = (Button)findViewById(R.id.btn_thumbnail1);
		btn_thumbnail2 = (Button)findViewById(R.id.btn_thumbnail2);

		edit_ratio = (EditText)findViewById(R.id.edit_ratio);
		edit_width = (EditText)findViewById(R.id.edit_width);
		edit_height = (EditText)findViewById(R.id.edit_height);
		edit_quality = (EditText)findViewById(R.id.edit_quality);
		btn_thumbnail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLocalThumbnailById();
			}
		});

		btn_thumbnail1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLocalThumbnail();
			}
		});
		btn_thumbnail2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLocalThumbnailByQuality();
			}
		});
		
	}
	
	String localPath = "sdcard/png0.png";
	
	private void getLocalThumbnailById(){
		final String id = edit_ratio.getText().toString();
		if(TextUtils.isEmpty(id)){
			return;
		}
		int modeId =Integer.parseInt(id);

		BmobProFile.getInstance(LocalThumbnailActivity.this).getLocalThumbnail(localPath, modeId, new LocalThumbnailListener() {

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("本地缩略图创建失败 :"+statuscode+","+errormsg);
			}

			@Override
			public void onSuccess(String thumbnailPath) {
				// TODO Auto-generated method stub
				showToast("本地缩略图创建成功  :"+thumbnailPath);
			}
		});
	}
	
	private void getLocalThumbnail(){
		String id = edit_ratio.getText().toString();
		String w = edit_width.getText().toString();
		String h = edit_height.getText().toString();
		if(TextUtils.isEmpty(id)){
			return;
		}
		if(TextUtils.isEmpty(w)){
			return;
		}
		if(TextUtils.isEmpty(h)){
			return;
		}
		int modeId =Integer.parseInt(id);
		int width =Integer.parseInt(w);
		int height =Integer.parseInt(h);
		BmobProFile.getInstance(LocalThumbnailActivity.this).getLocalThumbnail(localPath, modeId, width, height, new LocalThumbnailListener() {

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("本地缩略图创建失败 :"+statuscode+","+errormsg);
			}

			@Override
			public void onSuccess(String thumbnailPath) {
				// TODO Auto-generated method stub
				showToast("本地缩略图创建成功  :"+thumbnailPath);
			}
		});
	}
	
	private void getLocalThumbnailByQuality(){
		String id = edit_ratio.getText().toString();
		String w = edit_width.getText().toString();
		String h = edit_height.getText().toString();
		String q = edit_quality.getText().toString();
		if(TextUtils.isEmpty(id)){
			return;
		}
		if(TextUtils.isEmpty(w)){
			return;
		}
		if(TextUtils.isEmpty(h)){
			return;
		}
		if(TextUtils.isEmpty(q)){
			return;
		}
		int modeId =Integer.parseInt(id);
		int width =Integer.parseInt(w);
		int height =Integer.parseInt(h);
		//此为新增的图片压缩质量：0-100之间，方便开发者
		int quality =Integer.parseInt(q);
		
		BmobProFile.getInstance(LocalThumbnailActivity.this).getLocalThumbnail(localPath, modeId, width, height, quality,new LocalThumbnailListener() {
			
			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("本地缩略图创建失败 :"+statuscode+","+errormsg);
			}
			
			@Override
			public void onSuccess(String thumbnailPath) {
				// TODO Auto-generated method stub
				showToast("本地缩略图创建成功  :"+thumbnailPath);
			}
		});
	}
	

}
