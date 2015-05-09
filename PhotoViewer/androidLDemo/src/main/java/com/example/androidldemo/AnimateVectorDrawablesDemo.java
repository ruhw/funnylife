package com.example.androidldemo;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.androidlanimation.R;

public class AnimateVectorDrawablesDemo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.animatevectordrawablesdemo_layout);

        final ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Drawable drawable = imageView.getDrawable();
              if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
              }
          }
        });
    }
}
