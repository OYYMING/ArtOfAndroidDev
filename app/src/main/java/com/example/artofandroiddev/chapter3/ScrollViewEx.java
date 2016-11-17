package com.example.artofandroiddev.chapter3;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by ouyangym on 2016/11/11.
 */
public class ScrollViewEx extends ScrollView {
    private static final String TAG = ScrollViewEx.class.toString();

    private OnInterceptTouchEventListener mOnInterceptTouchEventListener;

    public ScrollViewEx(Context context) {
        super(context);
    }

    public ScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "ScrollViewEx_onTouchEvent has been called");
        Log.d(TAG, "ev.X:" + ev.getRawX() + "   ev.Y:" + ev.getY());

        boolean result = super.onTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }



    public OnInterceptTouchEventListener getOnInterceptTouchEventListener() {
        return mOnInterceptTouchEventListener;
    }

    public void setOnInterceptTouchEventListener(OnInterceptTouchEventListener mOnInterceptTouchEventListener) {
        this.mOnInterceptTouchEventListener = mOnInterceptTouchEventListener;
    }

    public  interface OnInterceptTouchEventListener {
        boolean onTouchEvent(MotionEvent ev);
    }

}
