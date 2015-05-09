package com.example.androidldemo;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class BezierView extends View {
    private float mX;
    private float mY;
    private Paint mPaint;
    private Path mPath;
    private float mMultipliter = 100.0f;
    private Camera mCamera = new Camera();
    private Matrix matrix = new Matrix();
    private float mControlX1 = -1f;
    private float mControlY1 = -1f;
    private float mControlX2 = -1f;
    private float mControlY2 = -1f;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPath = new Path();
    }

    public void setPath() {
        mPath.moveTo(0, 0);
        mPath.lineTo(mMultipliter, mMultipliter);
        mControlX1 = -1f;
        mControlY1 = -1f;
        mControlX2 = -1f;
        mControlY2 = -1f;
    }

    public void setPath(float controlX, float controlY) {
        mPath.moveTo(0, 0);
        mControlX1 = controlX * mMultipliter;
        mControlY1 = controlY * mMultipliter;
        mControlX2 = -1f;
        mControlY2 = -1f;
        mPath.quadTo(mControlX1, mControlY1, mMultipliter, mMultipliter);
    }

    public void setPath(float controlX1, float controlY1, float controlX2, float controlY2) {
        mPath.moveTo(0, 0);
        mControlX1 = controlX1 * mMultipliter;
        mControlY1 = controlY1 * mMultipliter;
        mControlX2 = controlX2 * mMultipliter;
        mControlY2 = controlY2 * mMultipliter;
        mPath.cubicTo(mControlX1, mControlY1, mControlX2, mControlY2, mMultipliter, mMultipliter);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mMultipliter = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.save();

        matrix.reset();
        mCamera.save();
        mCamera.rotateX(180);
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(-getWidth()/ 2, -getHeight() / 2);
        matrix.postTranslate(getWidth()/ 2, getHeight() / 2);
        canvas.concat(matrix);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(10);
        if (mControlX1 > 0) {
            canvas.drawPoint(mControlX1, mControlY1, mPaint);
        }
        if (mControlX2 > 0) {
            canvas.drawPoint(mControlX2, mControlY2, mPaint);
        }
        canvas.restore();
    }

    public void setMX(float mX) {
        this.mX = mX;
        invalidate();
    }

    public float getMX() {
        return mX;
    }

    public void setMY(float mY) {
        this.mY = mY;
        invalidate();
    }

    public float getMY() {
        return mY;
    }
}
