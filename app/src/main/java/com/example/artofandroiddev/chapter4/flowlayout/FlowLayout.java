package com.example.artofandroiddev.chapter4.flowlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ouyangym on 2016/11/24.
 * 一个沿着水平方向放置子View的FlowLayout，
 * 当一行中View的累计宽度超过FlowLayout的宽度时，将从下一行开始布局当前的子View
 * 可以设置固定宽度，match_parent和wrap_content。wrap_content的情况将以累计宽度的最大的一行的宽度作为FrameLayout的宽度
 * 支持Padding和子View的margin
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHorizontal(widthMeasureSpec, heightMeasureSpec); //方向暂时只支持水平排序
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    private void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        final int horizontalPadding = getPaddingLeft() + getPaddingRight();
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        final int count = getChildCount();

        int totalHeight = 0;
        int totalWidth = 0;
        int lineHeight = 0;
        int lineWidth = horizontalPadding;
        int childState = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            measureChildWithMargins(child, widthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin, heightMeasureSpec, 0);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int childTotalWidth = childWidth + lp.leftMargin + lp.rightMargin;
            int childTotalHeight = childHeight + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childTotalWidth > width) {
                totalHeight += lineHeight;
                totalWidth = Math.max(totalWidth, lineWidth);
                lineHeight = childTotalHeight;
                lineWidth = horizontalPadding + childTotalWidth;
            } else {
                lineHeight = Math.max(lineHeight, childTotalHeight);
                lineWidth += childTotalWidth;
            }

            combineMeasuredStates(childState, child.getMeasuredState());
        }

        //累加最后一行的宽度和高度
        totalWidth = Math.max(totalWidth, lineWidth);
        totalHeight += lineHeight;

        totalHeight += getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? width : resolveSizeAndState(totalWidth, widthMeasureSpec, 0),
                heightMode == MeasureSpec.EXACTLY ? height : resolveSizeAndState(totalHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth() - getPaddingRight();
        final int height = getMeasuredHeight();
        //子View各行累计的高度
        int totalHeight = getPaddingTop();
        //当前行View累计已用宽度
        int lineWidth = getPaddingLeft();
        //当前行View的最大高度
        int lineHeight = 0;

        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int childTotalWidth = childWidth + lp.leftMargin + lp.rightMargin;
            int childTotalHeight = childHeight + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childTotalWidth > width) {
                totalHeight += lineHeight;
                //当前行已满时，从下一行开始布局
                child.layout(getPaddingLeft() + lp.leftMargin, totalHeight + lp.topMargin, getPaddingLeft() + lp.leftMargin + childWidth, totalHeight + lp.topMargin + childHeight);
                lineHeight = childTotalHeight;
                lineWidth = getPaddingLeft() + childTotalWidth;
            } else {
                //当前行未满时，继续在行末布局
                child.layout(lineWidth + lp.leftMargin, totalHeight + lp.topMargin, lineWidth + lp.leftMargin + childWidth, totalHeight + lp.topMargin + childHeight);
                lineHeight = Math.max(lineHeight, childTotalHeight);
                lineWidth += childTotalWidth;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
