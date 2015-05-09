package com.example.bmobexample;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.EmailVerifyListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetServerTimeListener;
import cn.bmob.v3.listener.ResetPasswordListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;

import com.example.bmobexample.autoupdate.ActAutoUpdate;
import com.example.bmobexample.bean.MyUser;
import com.example.bmobexample.bean.Person;
import com.example.bmobexample.file.BmobFileActivity;
import com.example.bmobexample.newfile.NewBmobFileActivity;
import com.example.bmobexample.push.ActBmobPush;
import com.example.bmobexample.relationaldata.WeiboListActivity;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends BaseActivity {

	protected ListView mListview;
	protected BaseAdapter mAdapter;

	/**
	 * SDK初始化建议放在启动页
	 */
	public static String APPID = "1ed8c73811b59dd1029b2a20187e6727";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bmob.initialize(getApplicationContext(),APPID);
		

		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_test_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testBmob(position + 1);
			}
		});

		ChangeLogDialog changeLogDialog = new ChangeLogDialog(this);
		changeLogDialog.show();
	}

	private void testBmob(int pos) {
		switch (pos) {
		case 1:
			testinsertObject();
			break;
		case 2:
			testUpdateObjet();
			break;
		case 3:
			testDeleteObject();
			break;
		case 4:
			startActivity(new Intent(MainActivity.this, QueryActivity.class));
			break;
		case 5:
			testSignUp();
			break;
		case 6:
			testLogin();
			break;
		case 7:
			testGetCurrentUser();
			break;
		case 8:
			testLogOut();
			break;
		case 9:
			updateUser();
			break;
		case 10:
			testResetPasswrod();
			break;
		case 11:// 修改密码
			checkPassword();
			break;
		case 12:
			emailVerify();
			break;
		case 13:
			testFindBmobUser();
			break;
		case 14:
			getServerTime();
			break;
		case 15:
			startActivity(new Intent(this, BmobFileActivity.class));
			break;
		case 16:
			cloudCode();
			break;
		case 17:
			// 关联数据
			startActivity(new Intent(this, WeiboListActivity.class));
			break;
		case 18:
			// 批量操作
			startActivity(new Intent(this, BatchActionActivity.class));
			break;
		case 19:
			// 推送服务
			startActivity(new Intent(this, ActBmobPush.class));
			break;
		case 20://数据实时同步
			realTime();
			break;
		case 21:
			// 应用自动更新
			startActivity(new Intent(this, ActAutoUpdate.class));
			break;
		case 22:
			//新版文件服务
			startActivity(new Intent(this, NewBmobFileActivity.class));
			break;
		}
	}

	public static String objectId="";
	/**
	 * 插入对象
	 */
	private void testinsertObject() {
		final Person p2 = new Person();
		p2.setName("lucky");
		p2.setAddress("北京市海淀区");
		p2.setAge(25);
		p2.setGpsAdd(new BmobGeoPoint(112.934755, 24.52065));
		p2.setUploadTime(new BmobDate(new Date()));
		p2.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				objectId = p2.getObjectId();
				toast("创建数据成功：" + p2.getObjectId());
				Log.d("bmob", "objectId = " + p2.getObjectId());
				Log.d("bmob", "name =" + p2.getName());
				Log.d("bmob", "age =" + p2.getAge());
				Log.d("bmob", "address =" + p2.getAddress());
				Log.d("bmob", "gender =" + p2.isGender());
				Log.d("bmob", "createAt = " + p2.getCreatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("创建数据失败：" + msg);
			}
		});
	}

	/**
	 * 更新对象
	 */
	private void testUpdateObjet() {
		final Person p2 = new Person();
		p2.setAddress("北京市朝阳区12");
		p2.setAge(30);
		p2.update(this, objectId, new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("更新成功：" + p2.getUpdatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("更新失败：" + msg);
			}
		});

	}

	/**
	 * 删除对象
	 */
	private void testDeleteObject() {
		Person p2 = new Person();
		p2.setObjectId(objectId);
		p2.delete(this, new DeleteListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("删除成功");
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("删除失败：" + msg);
			}
		});
	}

	/**
	 * 注册用户
	 */
	private void testSignUp() {
		final MyUser myUser = new MyUser();
		myUser.setUsername("lucky");
		myUser.setPassword("123456");
		myUser.setAge(24);
		myUser.setGender(true);
		myUser.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("注册成功:" + myUser.getUsername() + "-"
						+ myUser.getObjectId() + "-" + myUser.getCreatedAt()
						+ "-" + myUser.getSessionToken()+",是否验证："+myUser.getEmailVerified());
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("注册失败:" + msg);
			}
		});
	}

	/**
	 * 登陆用户
	 */
	private void testLogin() {
		final BmobUser bu2 = new BmobUser();
		bu2.setUsername("lucky");
		bu2.setPassword("123456");
		bu2.login(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast(bu2.getUsername() + "登陆成功");
				testGetCurrentUser();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("登陆失败:" + msg);
			}
		});
	}

	/**
	 * 获取本地用户
	 */
	private void testGetCurrentUser() {
		MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
		if (myUser != null) {
			Log.i("life","本地用户信息:objectId = " + myUser.getObjectId() + ",name = " + myUser.getUsername()
					+ ",age = "+ myUser.getAge() + ",gender = " + myUser.getGender());
		} else {
			toast("本地用户为null,请登录。");
		}

	}

	/**
	 * 清除本地用户
	 */
	private void testLogOut() {
		BmobUser.logOut(this);
	}

	/**
	 * 更新用户
	 */
	private void updateUser() {
		final MyUser bmobUser = BmobUser.getCurrentUser(this, MyUser.class);
		if (bmobUser != null) {
			Log.d("bmob", "getObjectId = " + bmobUser.getObjectId());
			Log.d("bmob", "getUsername = " + bmobUser.getUsername());
			Log.d("bmob", "getPassword = " + bmobUser.getPassword());
			Log.d("bmob", "getEmail = "    + bmobUser.getEmail());
			Log.d("bmob", "getCreatedAt = " + bmobUser.getCreatedAt());
			Log.d("bmob", "getUpdatedAt = " + bmobUser.getUpdatedAt());
			MyUser newUser = new MyUser();
			newUser.setPassword("123456");
			newUser.setAge(2566);
			newUser.setGender(false);
			newUser.setObjectId(bmobUser.getObjectId());
			newUser.update(this,new UpdateListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					testGetCurrentUser();
				}

				@Override
				public void onFailure(int code, String msg) {
					// TODO Auto-generated method stub
					toast("更新用户信息失败:" + msg);
				}
			});
		} else {
			toast("本地用户为null,请登录。");
		}
	}

	/**
	 * 验证旧密码是否正确
	 * @Title: updatePassword
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void checkPassword() {
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		final MyUser bmobUser = BmobUser.getCurrentUser(this, MyUser.class);
		// 如果你传的密码是正确的，那么arg0.size()的大小是1，这个就代表你输入的旧密码是正确的，否则是失败的
		query.addWhereEqualTo("password", "123456");
		query.addWhereEqualTo("username", bmobUser.getUsername());
		query.findObjects(this, new FindListener<MyUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<MyUser> arg0) {
				// TODO Auto-generated method stub
				toast("查询密码成功:" + arg0.size());
			}
		});
	}

	/**
	 * 重置密码
	 */
	private void testResetPasswrod() {
		final String email = "469874851@qq.com";
		BmobUser.resetPassword(this, email, new ResetPasswordListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
			}

			@Override
			public void onFailure(int code, String e) {
				// TODO Auto-generated method stub
				toast("重置密码失败:" + e);
			}
		});
	}

	/**
	 * 查询用户
	 */
	private void testFindBmobUser() {
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.addWhereEqualTo("username", "lucky");
		query.findObjects(this, new FindListener<MyUser>() {

			@Override
			public void onSuccess(List<MyUser> object) {
				// TODO Auto-generated method stub
				toast("查询用户成功：" + object.size());

			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				toast("查询用户失败：" + msg);
			}
		});
	}

	/**
	 * 验证邮件
	 */
	private void emailVerify() {
		final String email = "75727433@qq.com";
		BmobUser.requestEmailVerify(this, email, new EmailVerifyListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("请求验证邮件成功，请到" + email + "邮箱中进行激活账户。");
			}

			@Override
			public void onFailure(int code, String e) {
				// TODO Auto-generated method stub
				toast("请求验证邮件失败:" + e);
			}
		});
	}

	/**
	 * 获取服务器时间
	 */
	private void getServerTime() {
		Bmob.getServerTime(MainActivity.this, new GetServerTimeListener() {

			@Override
			public void onSuccess(long time) {
				// TODO Auto-generated method stub
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				String times = formatter.format(new Date(time * 1000L));
				toast("当前服务器时间为:" + times);
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("获取服务器时间失败:" + msg);
			}
		});
	}

	/**
	 * 云端代码
	 */
	private void cloudCode() {
		//带请求参数
		AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//		JSONObject obj = new JSONObject();
//		try {
//			obj.put("mobile", "");
//		} catch (JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		ace.callEndpoint(this, "test", obj, new CloudCodeListener() {
//			
//			@Override
//			public void onSuccess(Object object) {
//				// TODO Auto-generated method stub
//				String result = object.toString();
//				toast("云端usertest方法返回:" + result);
//			}
//			
//			@Override
//			public void onFailure(int code, String msg) {
//				// TODO Auto-generated method stub
//				toast("访问云端usertest方法失败:" + msg);
//			}
//		});
		//不带请求的云端代码
		ace.callEndpoint(MainActivity.this, "testJSONObject", new CloudCodeListener() {

			@Override
			public void onSuccess(Object object) {
				toast("云端usertest方法返回:" + object.toString());
				String json = object.toString();
				try {
					JSONObject obj = new JSONObject(json);
					String ud = obj.getString("ud");
					toast("云端usertest方法返回ud:" + ud);
				} catch (Exception e) {
					// TODO: handle exception
					toast("云端usertest方法返回错误:" + e.getMessage());
				}
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("访问云端usertest方法失败:" + msg);
			}
		});
	}
	
	private void realTime(){
		final BmobRealTimeData rtd = new BmobRealTimeData();
		rtd.start(this, new ValueEventListener() {
			
			@Override
			public void onDataChange(JSONObject data) {
				// TODO Auto-generated method stub
				Log.i("life", "onDataChange：data = "+data);
			}
			
			@Override
			public void onConnectCompleted() {
				// TODO Auto-generated method stub
				Log.d("life", "连接成功:"+rtd.isConnected());
				if(rtd.isConnected()){
				    // 监听表更新
				    rtd.subTableUpdate("Person");
				}
			}
		});
	}

	public void onBackPressed() {
		finish();
	};
}
