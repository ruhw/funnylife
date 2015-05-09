package com.example.androidldemo;

import android.R.integer;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.androidlanimation.R;

public class CurvedMotionDemo extends Activity {
    private View mView;
    private View mView2;
    private View mView3;
    private View mView4;
    private Spinner mInterpolatorSpinner;
    private SeekBar mDurationSeekbar;
    private TextView mDurationLabel;

    private Interpolator mInterpolators[];
    private Path mPathIn;
    private Path mPathOut;

    private boolean mIsOut = false;

    private static final int INITIAL_DURATION_MS = 750;

    private BezierView mBezierView;
    private EditText mTextControlX1;
    private EditText mTextControlY1;
    private EditText mTextControlX2;
    private EditText mTextControlY2;
    private Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.curvedmotiondemo_layout);

        mBezierView = (BezierView)findViewById(R.id.bezier_id);
        Button button = (Button) findViewById(R.id.animateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = mInterpolatorSpinner.getSelectedItemPosition();
                Interpolator interpolator = mInterpolators[index];
                long duration = mDurationSeekbar.getProgress();
                Path path = mIsOut ? mPathIn : mPathOut;

                startAnimation(interpolator, duration, path);

                mIsOut = !mIsOut;

                switch (index) {
                    case 0:
                        mBezierView.setPath();
                        break;
                    case 1:
                        mBezierView.setPath(0.4f, 0f, 1f, 1f);
                        break;
                    case 2:
                        mBezierView.setPath(0.4f, 0f, 0.2f, 1f);
                        break;
                    case 3:
                        mBezierView.setPath(0f, 0f, 0.2f, 1f);
                        break;
                }
                mBezierView.invalidate();
            }
        });

        mDurationLabel = (TextView)findViewById(R.id.durationLabel);

        mInterpolators = new Interpolator[]{
                new AnimationUtils().loadInterpolator(this, android.R.interpolator.linear),
                new AnimationUtils().loadInterpolator(this, android.R.interpolator.fast_out_linear_in),
                new AnimationUtils().loadInterpolator(this, android.R.interpolator.fast_out_slow_in),
                new AnimationUtils().loadInterpolator(this, android.R.interpolator.linear_out_slow_in)
        };


        String[] interpolatorNames = getResources().getStringArray(R.array.interpolator_names);

        mInterpolatorSpinner = (Spinner)findViewById(R.id.interpolatorSpinner);
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, interpolatorNames);
        mInterpolatorSpinner.setAdapter(spinnerAdapter);

        mDurationSeekbar = (SeekBar)findViewById(R.id.durationSeek);

        mDurationSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mDurationLabel.setText(getResources().getString(R.string.animation_duration, i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mDurationSeekbar.setProgress(INITIAL_DURATION_MS);

        mView = findViewById(R.id.square);
        mView2 = findViewById(R.id.square2);
        mView3 = findViewById(R.id.square3);
        mView4 = findViewById(R.id.square4);
        mTextControlX1 = (EditText)findViewById(R.id.txt_controlx1);
        mTextControlY1 = (EditText)findViewById(R.id.txt_controly1);
        mTextControlX2 = (EditText)findViewById(R.id.txt_controlx2);
        mTextControlY2 = (EditText)findViewById(R.id.txt_controly2);
        mSwitch = (Switch)findViewById(R.id.switch_id);

        Button button2 = (Button) findViewById(R.id.btn_start_anim);
        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                long duration = mDurationSeekbar.getProgress();
                Path path = mIsOut ? mPathIn : mPathOut;
                startMyAnimator(duration, path);
                mIsOut = !mIsOut;
            }
        });

        // The following Path definitions are used by the ObjectAnimator to scale the view.

        // Path for 'in' animation: growing from 20% to 100%
        mPathIn = new Path();
        mPathIn.moveTo(0.2f, 0.4f);
        mPathIn.lineTo(1f, 1f);

        // Path for 'out' animation: shrinking from 100% to 20%
        mPathOut = new Path();
        mPathOut.moveTo(1f, 1f);
        mPathOut.lineTo(0.2f, 0.4f);

        mBezierView = (BezierView)findViewById(R.id.bezier_id);
    }

    /**
     * Start an animation on the sample view.
     * The view is animated using an {@link android.animation.ObjectAnimator} on the
     * {@link View#SCALE_X} and {@link View#SCALE_Y} properties, with its animation based on a path.
     * The only two paths defined here ({@link #mPathIn} and {@link #mPathOut}) scale the view
     * uniformly.
     *
     * @param interpolator The interpolator to use for the animation.
     * @param duration     Duration of the animation in ms.
     * @param path         Path of the animation
     * @return The ObjectAnimator used for this animation
     * @see android.animation.ObjectAnimator#ofFloat(Object, String, String, android.graphics.Path)
     */
    public ObjectAnimator startAnimation(Interpolator interpolator, long duration, Path path) {
        // This ObjectAnimator uses the path to change the x and y scale of the mView object.
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, View.SCALE_X, View.SCALE_Y, path);

        // Set the duration and interpolator for this animation
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);

        animator.start();

        ViewPropertyAnimator propertyAnimator = mView2.animate();
        propertyAnimator.translationY(mIsOut ? 0 : 400);
        propertyAnimator.setDuration(duration);
        propertyAnimator.setInterpolator(interpolator);
        propertyAnimator.start();

        ViewPropertyAnimator propertyAnimator3 = mView3.animate();
        propertyAnimator3.alpha(mIsOut ? 1.0f : 0);
        propertyAnimator3.setDuration(duration);
        propertyAnimator3.setInterpolator(interpolator);
        propertyAnimator3.start();

        ViewPropertyAnimator propertyAnimator4 = mView4.animate();
        propertyAnimator4.rotation(mIsOut ? 0 : 360);
        propertyAnimator4.setDuration(duration);
        propertyAnimator4.setInterpolator(interpolator);
        propertyAnimator4.start();

        return animator;
    }

    private void startMyAnimator(long duration, Path path) {
        float controlX1;
        float controlY1;
        float controlX2;
        float controlY2;
        PathInterpolator interpolator;
        if (mSwitch.isChecked()) {
            controlX1 = Float.valueOf(mTextControlX1.getText().toString());
            controlY1 = Float.valueOf(mTextControlY1.getText().toString());
            controlX2 = Float.valueOf(mTextControlX2.getText().toString());
            controlY2 = Float.valueOf(mTextControlY2.getText().toString());
            interpolator = new PathInterpolator(controlX1, controlY1, controlX2, controlY2);
            mBezierView.setPath(controlX1, controlY1, controlX2, controlY2);
        } else  {
            controlX1 = Float.valueOf(mTextControlX1.getText().toString());
            controlY1 = Float.valueOf(mTextControlY1.getText().toString());
            interpolator = new PathInterpolator(controlX1, controlY1);
            mBezierView.setPath(controlX1, controlY1);
        }
        mBezierView.invalidate();
        startAnimation(interpolator, duration, path);
    }
}
