package com.example.artofandroiddev.chapter3.sameorientationconflict;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.DummyContent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 拥有两个子View:TextView作为Header，ListView作为Header下面的Content
 * 滑动规则为
 * (1)当Header可见时，由StickyLayout处理滑动;
 * (2)当ListView已经滑动到顶部，且当前仍在向下滑动且时，由StickyLayout处理滑动。
 * (3)其他情况由ListView处理滑动
 * <p/>
 * 使用了作者所指的外部拦截法
 */
public class StickyLayout extends ScrollView {
    private float mDownX;
    private float mDownY;
    private float mLastX;
    private float mLastY;
    private float mStartScrollY;

    public TextView mHeader;
    public ListView mContent;

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        float x = ev.getRawX();
        float y = ev.getRawY();
        int scrollY = getScrollY();
        boolean intercepted = false;
        isContentAtTop();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                mStartScrollY = scrollY;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (scrollY < getHeaderHeight()) {
                    intercepted = true;
                } else if (!hasScrollToBottom() && dy < 0) {
                    intercepted = true;
                } else if (isContentAtTop() && dy > 0) {
                    mDownY = y;
                    mStartScrollY = scrollY;
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        mLastX = x;
        mLastY = y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        float y = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mDownY;
                scrollTo(0,(int)(mStartScrollY - dy));
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(ev);
    }

    /**
     * 判断StickyLayout是否已经滚动到底部
     *
     * @return true if this view has scrolled to its bottom.
     */
    private boolean hasScrollToBottom() {
        boolean atBottom = false;
        View child = this.getChildAt(0);
        if (child != null) {
            int scrollHeight = child.getMeasuredHeight();
            if (scrollHeight <= this.getHeight() + this.getScrollY())
                atBottom = true;
        }

        return atBottom;
    }

    /**
     * 判断作为content的ListView是否滚动到了顶部
     *
     * @return true if the content has touched its top
     */
    private boolean isContentAtTop() {
        boolean atTop = false;
        if (mContent != null && mContent.getFirstVisiblePosition() == 0) {
            View child = mContent.getChildAt(0);
            if (child != null && child.getTop() == 0)
                atTop = true;
        }

        return atTop;
    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float deltaY = y - mLastY;
//                scrollBy(0, -(int)deltaY);
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//
//        mLastX = x;
//        mLastY = y;
//
//        return true;
//    }

    public void setHeader(TextView view) {
        mHeader = view;
    }

    public void setContent(ListView view) {
        mContent = view;

        SimpleAdapter adapter = new SimpleAdapter(getContext(), DummyContent.ITEM_MAP, android.R.layout.simple_list_item_1, new String[]{"KEY"}, new int[]{android.R.id.text1});
        mContent.setAdapter(adapter);
    }

    public int getHeaderHeight() {
        return mHeader.getHeight();
    }

    public static String getMotionName(MotionEvent ev) {
        String name = "";
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                name = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                name = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                name = "ACTION_UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                name = "ACTION_CANCEL";
                break;
        }

        return name;
    }
}
