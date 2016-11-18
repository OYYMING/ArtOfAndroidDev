package com.example.artofandroiddev.chapter3.repeatedintercepttouchevent;

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

    private OnRepeatedInterceptTouchEventListener mOnRepeatedInterceptTouchEventListener;

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
        Log.d(TAG, "ListViewEx_onTouchEvent has been invoked");
        if (mOnRepeatedInterceptTouchEventListener != null) {
            if (mOnRepeatedInterceptTouchEventListener.onTouchEvent(ev)) {
                return true;
            }
        }
        super.onTouchEvent(ev);
        return true;
    }


    public OnRepeatedInterceptTouchEventListener getOnInterceptTouchEventListener() {
        return mOnRepeatedInterceptTouchEventListener;
    }

    public void setOnInterceptTouchEventListener(OnRepeatedInterceptTouchEventListener mOnRepeatedInterceptTouchEventListener) {
        this.mOnRepeatedInterceptTouchEventListener = mOnRepeatedInterceptTouchEventListener;
    }


    /**
     * 想要和ListView反复共享滑动事件的话，可以使用这个接口
     * 在onTouchEvent时会首先由这个接口来处理TouchEvent。
     */
    public interface OnRepeatedInterceptTouchEventListener {
        boolean onTouchEvent(MotionEvent ev);
    }

}
