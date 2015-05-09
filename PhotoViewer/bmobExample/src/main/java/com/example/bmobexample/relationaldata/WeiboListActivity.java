package com.example.bmobexample.relationaldata;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.MyUser;

public class WeiboListActivity extends BaseActivity {
	
	ListView listView;
	EditText et_content;
	Button btn_publish;
	
	static List<Weibo> weibos = new ArrayList<Weibo>();
	MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weibo);
		setTitle("微博列表");
		
		adapter = new MyAdapter(this);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_publish = (Button) findViewById(R.id.btn_publish);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
		
		btn_publish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				publishWeibo(et_content.getText().toString());
			}
		});
		
		findWeibos();
	}
	
	/**
	 * 查询微博
	 */
	private void findWeibos(){
		BmobQuery<Weibo> query = new BmobQuery<Weibo>();
		query.order("-updatedAt");
		MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
		query.addWhereEqualTo("author", user);	// 查询当前用户的所有微博
		query.include("author"); 	// 希望在查询微博信息的同时也把发布人的信息查询出来，可以使用include方法
		query.findObjects(this, new FindListener<Weibo>() {
			@Override
			public void onSuccess(List<Weibo> object) {
				// TODO Auto-generated method stub
				weibos = object;
				adapter.notifyDataSetChanged();
				et_content.setText("");
			}
			
			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				toast("查询失败:"+msg);
			}
		});
	}
	
	/**
	 * 发布微博，发表微博时关联了用户类型，是一对一的体现
	 */
	private void publishWeibo(String content){
		
		MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
		if(user == null){
			toast("发布微博前请先登陆");
			return;
		}else if(TextUtils.isEmpty(content)){
			toast("发布内容不能为空");
			return;
		}
		
		// 创建微博信息
		Weibo weibo = new Weibo();
		weibo.setContent(content);
		weibo.setAuthor(user);
		weibo.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("发布成功");
				findWeibos();
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("发表失败");
			}
		});
	}
	
	private static class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		
		private Context mContext;

		public MyAdapter(Context context) {
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		static class ViewHolder {
			TextView tv_content;
			TextView tv_author;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
//			return bmobObjects.size();
			return weibos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_weibo, null);

				holder = new ViewHolder();
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			final Weibo weibo = weibos.get(position);
			
			holder.tv_author.setText("发布人："+weibo.getAuthor().getUsername());
			
			final String str = weibo.getContent();

			holder.tv_content.setText(str);

			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, CommentListActivity.class);
					intent.putExtra("objectId", weibo.getObjectId());
					
					mContext.startActivity(intent);
				}
			});

			return convertView;
		}
	}
	
}
