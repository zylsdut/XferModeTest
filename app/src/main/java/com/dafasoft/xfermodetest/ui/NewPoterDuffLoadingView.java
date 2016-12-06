package com.dafasoft.xfermodetest.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.dafasoft.xfermodetest.R;

/**
 * Created by zhangyulong on 2016/12/6.
 */

public class NewPoterDuffLoadingView extends View {
    private Bitmap mBitmap;
    private Rect mLocationRect;
    private Rect mBitmapRect;
    private Paint mPaint;
    private int mProgressStart;
    private PorterDuffXfermode mMode;

    public NewPoterDuffLoadingView(Context context) {
        super(context);
        init();
    }

    public NewPoterDuffLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewPoterDuffLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        int rectLeft = (w - width) /2;
        int rectTop = (h - height) / 2;
        int rectRight = rectLeft + width;
        int rectBottom = rectTop + height;
        mLocationRect = new Rect(rectLeft , rectTop , rectRight , rectBottom);
        mBitmapRect = new Rect(0 , 0 , width , height);
        mProgressStart = rectBottom;
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources() , R.drawable.ga_studio);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveLayerCount = canvas.saveLayer(0, 0, canvas.getWidth() , canvas.getHeight(), mPaint,
                Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmap , mBitmapRect , mLocationRect , mPaint);
        mPaint.setXfermode(mMode);
        canvas.drawRect(mLocationRect.left , mProgressStart , mLocationRect.right , mLocationRect.bottom , mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayerCount);
        mProgressStart -= 2;
        if (mProgressStart <= mLocationRect.top) {
            mProgressStart = mLocationRect.bottom;
        }
        invalidate();
    }
}
