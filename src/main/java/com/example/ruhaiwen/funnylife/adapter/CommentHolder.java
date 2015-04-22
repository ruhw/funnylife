package com.example.ruhaiwen.funnylife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.ruhaiwen.funnylife.FunnyLifeApplication;
import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.entity.Comment;
import com.example.ruhaiwen.funnylife.entity.User;
import com.example.ruhaiwen.funnylife.utils.LogUtils;

/**
 * Created by ruhaiwen on 15-4-21.
 */
public class CommentHolder extends ViewHolder {

    private User user;
    private Comment comment;

    public TextView userName;
    public TextView commentContent;
    public TextView time;

    public CommentHolder(Comment comment) {
        this.comment = comment;
        user = FunnyLifeApplication.getInstance().getCurrentUser();
    }

    @Override
    public void findView(View convertView) {
        userName = (TextView)convertView.findViewById(R.id.userName_comment);
        commentContent = (TextView)convertView.findViewById(R.id.content_comment);
        time = (TextView)convertView.findViewById(R.id.time_comment);
    }

    @Override
    public void setupView(Context context) {
        if(comment.getCommentator()!=null){
            userName.setText(comment.getCommentator().getUsername());
        }
        time.setText(comment.getCreatedAt());
        commentContent.setText(comment.getCommentContent());
    }

    @Override
    public void setListener(Context context) {

    }
}
