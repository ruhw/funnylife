package com.example.ruhaiwen.jobqueueottodemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.ruhaiwen.jobqueueottodemo.jobs.FetchImageJob;
import com.example.ruhaiwen.jobqueueottodemo.otto.BusProvider;
import com.path.android.jobqueue.JobManager;

/**
 * Created by ruhaiwen on 15-4-2.
 */
public class JobqueueAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private String[] mImagesUrl;

    public JobqueueAdapter(LayoutInflater layoutInflater,String[] imagesUrl) {
        mLayoutInflater = layoutInflater;
        mImagesUrl = imagesUrl;
    }

    @Override
    public int getCount() {
        return mImagesUrl.length;
    }

    @Override
    public Object getItem(int position) {
        return mImagesUrl[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.imageview, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = ViewHolder.getFromView(convertView);
        }
        holder.render((String)getItem(position));
        return convertView;
    }

    private static class ViewHolder {

        ImageView mImageView;
        public ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.jobqueueImageView);
            view.setTag(this);
        }

        public static ViewHolder getFromView(View view) {
            Object tag = view.getTag();
            if(tag instanceof ViewHolder) {
                return (ViewHolder) tag;
            } else {
                return new ViewHolder(view);
            }
        }

        public void render(String imageUrl) {
//          mImageView.setText(imageUrl);
            DemoApplication.getInstance().getJobManager()
                    .addJobInBackground(new FetchImageJob(imageUrl,mImageView));
        }
    }
}
