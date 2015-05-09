package com.example.bmobexample.autoupdate;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;

public class ActAutoUpdate extends BaseActivity {
	
	String [] arr = {"自动更新","手动更新","静默下载更新","删除文件"};
	
	UpdateResponse ur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//在你需要调用自动更新功能之前先进行初始化建表操作
		BmobUpdateAgent.initAppVersion(this);
				
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.tv_item, arr);
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testAutoUpdate(position + 1);
			}
		});
		//利用如下方式计算apk的target_size大小：
//		Log.i("smile", "应用的target_size的大小 = "+new File("sdcard/xxx.apk").length());
		//允许在非wifi环境下检测应用更新
		BmobUpdateAgent.setUpdateOnlyWifi(false);
		//更新监听器
		BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
			
			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
				// TODO Auto-generated method stub
				if (updateStatus == UpdateStatus.Yes) {
					ur = updateInfo;
				}else if(updateStatus == UpdateStatus.No){
					Toast.makeText(ActAutoUpdate.this, "版本无更新", Toast.LENGTH_SHORT).show();
				}else if(updateStatus==UpdateStatus.EmptyField){//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
					Toast.makeText(ActAutoUpdate.this, "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
				}else if(updateStatus==UpdateStatus.IGNORED){
					Toast.makeText(ActAutoUpdate.this, "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
				}else if(updateStatus==UpdateStatus.ErrorSizeFormat){
					Toast.makeText(ActAutoUpdate.this, "请检查target_size填写的格式，请使用file.getLength()方法获取apk大小。", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	
	private void testAutoUpdate(int pos){
		switch (pos) {
		case 1:
			BmobUpdateAgent.update(this);
			break;
		case 2:
			BmobUpdateAgent.forceUpdate(this);
			break;
		case 3:
			BmobUpdateAgent.silentUpdate(this);
			break;
		case 4:
			if(ur != null){
				File file = new File(Environment.getExternalStorageDirectory(), ur.path_md5 + ".apk");
				if (file != null && file.exists()) {
					if (file.delete()) {
						Toast.makeText(ActAutoUpdate.this, "删除完成",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(ActAutoUpdate.this, "删除失败",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ActAutoUpdate.this, "删除完成", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(ActAutoUpdate.this, "删除失败", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
	
}
