package com.example.artofandroiddev.chapter3;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by ouyangym on 2016/11/11.
 */
public class ListViewEx extends ListView {
    private static final String TAG = ListViewEx.class.toString();
    
    private OnInterceptTouchEventListener mOnInterceptTouchEventListener;

    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "ListViewEx_onTouchEvent has been called");
        if (mOnInterceptTouchEventListener != null) {
            if (mOnInterceptTouchEventListener.onTouchEvent(ev)) {
                return true;
            }
        }
        boolean result = super.onTouchEvent(ev);
        return true;
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
