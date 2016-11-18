package com.example.artofandroiddev.chapter3.diforientationconflict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.artofandroiddev.utils.MyViewUtils;

/**
 * HorizontalScrollViewEx是作者所说的外部拦截法的示例
 *
 */
public class HorizontalScrollViewEx extends LinearLayout {
    private float mLastX;
    private float mLastY;
    private float mDownX;
    private float mDownY;

    private int mChildWidth;

    private boolean mFinished;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mChildWidth = MyViewUtils.getScreenMetrics(getContext()).widthPixels;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        boolean intercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - mDownX;
                float dy = x - mDownY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                if (!mFinished) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                scrollBy(-(int) deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                int scrollToIndex = scrollX / mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50) {
                    scrollToIndex = xVelocity > 0 ? scrollToIndex : scrollToIndex + 1;
                } else {
                    scrollToIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                scrollToIndex = Math.min(Math.max(scrollToIndex, 0), getChildCount() - 1);
                int dx = scrollToIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mVelocityTracker.clear();
        mVelocityTracker.recycle();
    }

    public void smoothScrollBy(int dx, int dy) {
        mFinished = true;
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
