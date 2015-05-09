package com.example.androidldemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.androidlanimation.R;

public class RippleDemo extends Activity {
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rippledemo_layout);

        mButton = (Button)findViewById(R.id.button_id);
        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mButton.getBackground().setHotspot(0, 30);
            }
        });
    }
}
