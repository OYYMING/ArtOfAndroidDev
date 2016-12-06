package com.example.artofandroiddev.chapter4.focuschange;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by ouyangym on 2016/11/29.
 */
public class FocusChangedView extends Button {
    private static final String TAG = FocusChangedView.class.toString();

    public FocusChangedView(Context context) {
        super(context);
    }

    public FocusChangedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusChangedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("WindowFocusChanged", "FocusChangedView _ onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("WindowFocusChanged", "FocusChangedView _ onDraw");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("WindowFocusChanged", "FocusChangedView _ onLayout");
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        Log.d("WindowFocusChanged", "FocusChangedView_onFocusChanged:" + focused);
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.d("WindowFocusChanged", "FocusChangedView_onWindowFocusChanged:" + hasWindowFocus);
        super.onWindowFocusChanged(hasWindowFocus);
    }
}
