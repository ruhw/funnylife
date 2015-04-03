package com.example.ruhaiwen.funnylife.volley;

import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;


/**
 * Created by ruhaiwen on 15-2-4.
 * Volley加载图片时的Listener
 */
public class VolleyImageListener implements ImageLoader.ImageListener{

    ImageView mView;
    int mDefaultImageResId;
    int mErrorImageResId;
    int mPosition;

    public VolleyImageListener(ImageView view,
                               int defaultImageResId, int errorImageResId, int position){
        mView = view;
        mDefaultImageResId = defaultImageResId;
        mErrorImageResId = errorImageResId;
        mPosition = position;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
        if (response.getBitmap() != null) {
            if((Integer)mView.getTag() == mPosition)
                mView.setImageBitmap(response.getBitmap());
        } else if (mDefaultImageResId != 0) {
            mView.setImageResource(mDefaultImageResId);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mErrorImageResId != 0) {
            mView.setImageResource(mErrorImageResId);
        }
    }
}
