package com.example.ruhaiwen.funnylife.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ruhaiwen.funnylife.FunnyLifeApplication;
import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.adapter.CommentAdapter;
import com.example.ruhaiwen.funnylife.adapter.PublicationHolder;
import com.example.ruhaiwen.funnylife.entity.Comment;
import com.example.ruhaiwen.funnylife.entity.Publication;
import com.example.ruhaiwen.funnylife.entity.User;
import com.example.ruhaiwen.funnylife.ui.base.BasePageActivity;
import com.example.ruhaiwen.funnylife.utils.ActivityUtil;
import com.example.ruhaiwen.funnylife.utils.Constant;
import com.example.ruhaiwen.funnylife.utils.LogUtils;
import com.example.ruhaiwen.funnylife.view.ActionBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-4-2
 * TODO
 */

public class CommentActivity extends BasePageActivity implements OnClickListener{

    public static final String PUBLICATION = "publication";
	private ActionBar actionbar;
	private ListView commentList;
	private TextView footer;
	
	private EditText commentContent ;
	private Button commentCommit;


    private View commentItem;
	private PublicationHolder publicationHolder;
	private Publication publication;
	private String commentEdit = "";
	
	private CommentAdapter mAdapter;
	
	private List<Comment> comments = new ArrayList<Comment>();
	
	private int pageNum;
	
	@Override
	protected void setLayoutView() {
		// TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		actionbar = (ActionBar)findViewById(R.id.actionbar_comment);
        commentItem = findViewById(R.id.comment_item);
		commentList = (ListView)findViewById(R.id.comment_list);
		footer = (TextView)findViewById(R.id.loadmore);
		
		commentContent = (EditText)findViewById(R.id.comment_content);
		commentCommit = (Button)findViewById(R.id.comment_commit);
	}

	@Override
	protected void setupViews(Bundle bundle) {
		// TODO Auto-generated method stub
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initActionBar();
        initCommentItem();
        initCommentList();
	}


    /**
     * 初始化ActionBar
     */
    private void initActionBar() {
        actionbar.setTitle("发表评论");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAction(new ActionBar.Action() {

            @Override
            public void performAction(View view) {
                // TODO Auto-generated method stub
                finish();
            }

            @Override
            public int getDrawable() {
                // TODO Auto-generated method stub
                return R.drawable.icon_app;
            }
        });
    }

    /**
     * 初始化评论所属条目
     */
    private void initCommentItem() {
        Intent intent = getIntent();
        Bundle publicationBundle = intent.getExtras();
        publication = (Publication)publicationBundle.getSerializable(CommentActivity.PUBLICATION);
        publicationHolder = new PublicationHolder(publication);
        publicationHolder.findView(commentItem);
        publicationHolder.setupView(mContext);
        publicationHolder.setListener(mContext);
    }

    /**
     * 初始化评论列表
     */
    private void initCommentList() {
        commentList.setCacheColorHint(0);
        commentList.setScrollingCacheEnabled(false);
        commentList.setScrollContainer(false);
        commentList.setFastScrollEnabled(true);
        commentList.setSmoothScrollbarEnabled(true);
        mAdapter = new CommentAdapter(CommentActivity.this, comments);
        commentList.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(commentList);
    }

    @Override
	protected void setListener() {
		// TODO Auto-generated method stub

		footer.setOnClickListener(this);
		commentCommit.setOnClickListener(this);
        publicationHolder.comment.setOnClickListener(this);
	}

	@Override
	protected void fetchData() {
		// TODO Auto-generated method stub
		fetchComment();
	}

    /**
     * 从服务器拉取评论数据
     */
	private void fetchComment(){
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		query.addWhereRelatedTo("comment", new BmobPointer(publication));
		query.include("commentator");
		query.order("createdAt");
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.findObjects(this, new FindListener<Comment>() {
			
			@Override
			public void onSuccess(List<Comment> data) {
				// TODO Auto-generated method stub
				LogUtils.i(TAG, "get comment success!" + data.size());
				if(data.size()!=0 && data.get(data.size()-1)!=null){
					
					if(data.size()< Constant.NUMBERS_PER_PAGE){
						//ActivityUtil.show(mContext, "已加载完所有评论~");
						footer.setText("暂无更多评论~");
					}
					
					mAdapter.getDataList().addAll(data);
					mAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(commentList);
					LogUtils.i(TAG,"refresh");
				}else{
					//ActivityUtil.show(mContext, "暂无更多评论~");
					footer.setText("暂无更多评论~");
					pageNum--;
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ActivityUtil.show(CommentActivity.this, "获取评论失败。请检查网络~");
				pageNum--;
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_logo:
			onClickUserLogo();
			break;
		case R.id.loadmore:
			onClickLoadMore();
			break;
		case R.id.comment_commit:
			onClickCommit();
			break;
		case R.id.item_action_comment:
			onClickComment();
			break;
		default:
			break;
		}
	}

    /**
     * 点击跳转到用户信息页面
     */
	private void onClickUserLogo() {
		// TODO Auto-generated method stub
		//跳转到个人信息界面
		User currentUser = BmobUser.getCurrentUser(this,User.class);
		if(currentUser != null){//已登录
			Intent intent = new Intent();
			intent.setClass(FunnyLifeApplication.getInstance().getTopActivity(), PersonalActivity.class);
			mContext.startActivity(intent);
		}else{//未登录
			ActivityUtil.show(this, "请先登录。");
			Intent intent = new Intent();
			intent.setClass(this, RegisterAndLoginActivity.class);
			startActivityForResult(intent, Constant.GO_SETTINGS);
		}
	}

    /**
     * 点击加载更多评论
     */
	private void onClickLoadMore() {
		// TODO Auto-generated method stub
		fetchData();
	}

    /**
     * 点击提交评论到服务器
     */
	private void onClickCommit() {
		// TODO Auto-generated method stub
		User currentUser = BmobUser.getCurrentUser(this,User.class);
		if(currentUser != null){//已登录
			commentEdit = commentContent.getText().toString().trim();
			if(TextUtils.isEmpty(commentEdit)){
				ActivityUtil.show(this, "评论内容不能为空。");
				return;
			}
			//comment now
			publishComment(currentUser,commentEdit);
		}else{//未登录
			ActivityUtil.show(this, "发表评论前请先登录。");
			Intent intent = new Intent();
			intent.setClass(this, RegisterAndLoginActivity.class);
			startActivityForResult(intent, Constant.PUBLISH_COMMENT);
		}
		
	}

    /**
     * 上传评论到服务器的具体方法
     * @param user 评论人
     * @param content 评论内容
     */
	private void publishComment(User user,String content){
		
		final Comment comment = new Comment();
		comment.setCommentator(user);
        comment.setPublication(publication);
		comment.setCommentContent(content);
		comment.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ActivityUtil.show(CommentActivity.this, "评论成功。");
				if(mAdapter.getDataList().size()<Constant.NUMBERS_PER_PAGE){
					mAdapter.getDataList().add(comment);
					mAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(commentList);
				}
				commentContent.setText("");
				hideSoftInput();
				
				//将该评论与发布目录绑定到一起
				BmobRelation relation = new BmobRelation();
				relation.add(comment);
				publication.setComment(relation);
				publication.update(mContext, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        LogUtils.i(TAG, "更新评论成功。");
//						fetchData();
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        LogUtils.i(TAG, "更新评论失败。" + arg1);
                    }
                });
				
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ActivityUtil.show(CommentActivity.this, "评论失败。请检查网络~");
			}
		});
	}

    /**
     * 点击评论按钮，弹出软键盘
     */
	private void onClickComment() {
		// TODO Auto-generated method stub
		commentContent.requestFocus();

		InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);  

		imm.showSoftInput(commentContent, 0);  
	}

    /**
     * 隐藏软键盘
     */
	private void hideSoftInput(){
		InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);  

		imm.hideSoftInputFromWindow(commentContent.getWindowToken(), 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case Constant.PUBLISH_COMMENT:
				//登录完成
				commentCommit.performClick();
				break;
			case Constant.GET_FAVOURITE:
				
				break;
			case Constant.GO_SETTINGS:
				//userLogo.performClick();
				break;
			default:
				break;
			}
		}
		
	}
	
	
	/*** 
     * 动态设置listview的高度 
     *  item 总布局必须是linearLayout
     * @param listView 
     */  
    public void setListViewHeightBasedOnChildren(ListView listView) {  
        ListAdapter listAdapter = listView.getAdapter();  
        if (listAdapter == null) {  
            return;  
        }  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight  
                + (listView.getDividerHeight() * (listAdapter.getCount()-1))  
                +15;  
        listView.setLayoutParams(params);  
    }
}
