package com.example.artofandroiddev.chapter6.drawablestate;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Spinner;

import com.example.artofandroiddev.R;


/**
 * 可以为Spinner在展开和折叠时切换不同的背景
 */
public class MySpinner extends Spinner {
    /**
     * Spinner展开时的状态值
     */
    public static final int[] STATE_EXPANDED = new int[] {R.attr.state_expanded};

    /**
     * 当前Spinner是否是展开的状态
     */
    private boolean mExpanded;

    public MySpinner(Context context) {
        super(context);
    }

    public MySpinner(Context context, int mode) {
        super(context, mode);
    }

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (mExpanded) {
            int[] state = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(state, STATE_EXPANDED);
            return state;
        }
        return super.onCreateDrawableState(extraSpace);
    }

    /**
     * 当前Spinner是否是展开状态
     * @return true 如果当前Spinner是展开状态
     */
    public boolean isExpanded() {
        return mExpanded;
    }

    /**
     * 设置Spinner是否是展开的状态
     * @param expanded
     */
    protected void setExpanded(boolean expanded) {
        if (this.mExpanded == expanded)
            return;

        this.mExpanded = expanded;
        refreshDrawableState();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (mExpanded && hasWindowFocus)
            setExpanded(false);
        Log.d("MySpinnerActivity", "MySpinner_onWindowFocusChanged:" + hasWindowFocus);
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    public boolean performClick() {
        if (!mExpanded) {
            setExpanded(true);
        }

        return super.performClick();
    }
}
