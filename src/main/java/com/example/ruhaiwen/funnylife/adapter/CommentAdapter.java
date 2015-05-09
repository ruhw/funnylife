package com.example.ruhaiwen.funnylife.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ruhaiwen.funnylife.R;
import com.example.ruhaiwen.funnylife.entity.Comment;
import com.example.ruhaiwen.funnylife.utils.LogUtils;

import java.util.List;

public class CommentAdapter extends BaseContentAdapter<Comment>{

	public CommentAdapter(Context context, List<Comment> list) {
		super(context, list);
	}

	@Override
	public View getConvertView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
            convertView = mInflater.inflate(R.layout.comment_item, null);
            viewHolder = new CommentHolder(dataList.get(position));
			viewHolder.findView(convertView);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}

        viewHolder.setupView(mContext);
        viewHolder.setListener(mContext);

		return convertView;
	}

}
