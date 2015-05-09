package com.example.androidldemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;

import com.example.androidlanimation.R;

public class SecondActivity extends Activity {
    View mRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity_layout);

        mRoot = findViewById(R.id.root_view);
        ViewTreeObserver viewTreeObserver = mRoot.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                mRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewAnimationUtils.createCircularReveal(mRoot, mRoot.getWidth() / 2, mRoot.getHeight() / 2, 0, mRoot.getHeight() / 2).setDuration(1000).start();
                return true;
            }
        });
    }
}
