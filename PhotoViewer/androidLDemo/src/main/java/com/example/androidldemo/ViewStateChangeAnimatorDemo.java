package com.example.androidldemo;

import android.app.Activity;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.androidlanimation.R;

public class ViewStateChangeAnimatorDemo extends Activity {
    private static final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};
    private static final int[] STATE_UNCHECKED = new int[]{};

    private ImageView mImageView;
    private boolean mIsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewstatechangeanimatordemo_layout);

        mImageView = (ImageView)findViewById(R.id.imageview_icon_id);
        Drawable drawable = getResources().getDrawable(R.drawable.add_schedule_fab_icon_anim);
        mImageView.setImageDrawable(drawable);
        mImageView.setImageState(STATE_UNCHECKED, false);
        mImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Drawable db = mImageView.getDrawable();
                if (db instanceof AnimatedStateListDrawable) {
                    mImageView.setImageState(mIsChecked ? STATE_UNCHECKED : STATE_CHECKED, false);
                    db.jumpToCurrentState();
                    mImageView.setImageState(mIsChecked ? STATE_CHECKED : STATE_UNCHECKED, false);
                    mIsChecked = !mIsChecked;
                }
            }
        });
    }
}
