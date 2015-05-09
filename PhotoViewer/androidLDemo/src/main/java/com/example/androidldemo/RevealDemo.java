package com.example.androidldemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.androidlanimation.R;

public class RevealDemo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revealdemo_layout);

        final FrameLayout infoContainer = (FrameLayout)findViewById(R.id.framelayout_id);

        Button button = (Button)findViewById(R.id.button_reveal_id);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                int visible = infoContainer.getVisibility();

                int cx = view.getLeft();
                int cy = view.getTop();
                float radius = Math.max(infoContainer.getWidth(), infoContainer.getHeight()) * 2.0f;

                Animator reveal;
                if (visible == View.INVISIBLE) {
                    infoContainer.setVisibility(View.VISIBLE);
                    reveal = ViewAnimationUtils.createCircularReveal(
                            infoContainer, cx, cy, 0, radius);
                    reveal.setInterpolator(new AccelerateInterpolator(2.0f));
                } else {
                    reveal = ViewAnimationUtils.createCircularReveal(
                            infoContainer, cx, cy, radius, 0);
                    reveal.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            infoContainer.setVisibility(View.INVISIBLE);
                        }
                    });
                    reveal.setInterpolator(new DecelerateInterpolator(2.0f));
                }
                reveal.setDuration(600);
                reveal.start();
            }
        });

        Button button2 = (Button)findViewById(R.id.button_reveal_next_id);
        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RevealDemo.this, SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
