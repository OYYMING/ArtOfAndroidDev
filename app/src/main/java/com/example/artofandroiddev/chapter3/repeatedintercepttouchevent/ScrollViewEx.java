package com.example.artofandroiddev.chapter3.repeatedintercepttouchevent;

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

    /**
     * 当转换到ScrollView来接管滑动事件时，
     * 通过该flag来判断是否重置mStartScrollY和mDownY
     */
    public static final int FLAG_RESET_DOWNY = 1;

    /**
     * 按下屏幕时的Y坐标。
     * 每次切换到ScrollView消耗事件的时候会以当前点坐标重置该值
     */
    private float mDownY;

    /**
     * 按下屏幕时ScrollView在Y坐标上的滑动距离
     * 每次切换到ScrollView消耗事件的时候会重新通过getScrollY()重置该值
     */
    private float mStartScrollY;

    private int mFlags = 0;

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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "ScrollViewEx_dispatchTouchEvent_______________ev.X:" + ev.getRawX() + "   ev.Y:" + ev.getRawY());

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getRawY();
                mStartScrollY = getScrollY();
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "ScrollViewEx_onTouchEvent has been invoked");

        float x = ev.getRawX();
        float y = ev.getRawY();

        //为了确保ScrollView从当前位置继续滑动，重新设置mStartScrollY和mDownY
        if ((mFlags & FLAG_RESET_DOWNY) == FLAG_RESET_DOWNY) {
            mDownY = y;
            mStartScrollY = getScrollY();
            mFlags &= ~FLAG_RESET_DOWNY;
        }

        switch (ev.getAction()) {
            //直接调用ScrollView自身的onTouchEvent会出现很离谱的幻影，所以由我们自己来处理滑动
            case MotionEvent.ACTION_MOVE:
                float dy = y - mDownY;
                scrollTo(0,(int)(mStartScrollY - dy));
                break;
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //始终不拦截TouchEvents
        return false;
    }

    public void setFlags(int flags) {
        mFlags |= flags;
    }
}
