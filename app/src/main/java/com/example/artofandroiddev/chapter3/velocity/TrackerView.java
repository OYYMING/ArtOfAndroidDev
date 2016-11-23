package com.example.artofandroiddev.chapter3.velocity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.artofandroiddev.R;

/**
 * Created by ouyangym on 2016/11/11.
 */
public class TrackerView extends View {
    private static final String TAG = TrackerView.class.toString();

    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mLastX;
    private float mLastY;
    private float mStartX;
    private float mStartY;

    /**
     * the paint color for central circle
     */
    private int mCircleColor;

    public TrackerView(Context context) {
        super(context);
    }

    public TrackerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TrackerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrackerView,defStyleAttr,0);
        mCircleColor = array.getColor(R.styleable.TrackerView_circleColor,Color.GREEN);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(mCircleColor);
        int radius = getMeasuredWidth() < getMeasuredHeight() ? getMeasuredWidth() / 2 : getMeasuredHeight() / 2;
        canvas.drawCircle(radius, radius, radius, paint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.clear();
        mVelocityTracker.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        Log.d("Velocity", "onTouchEvent");
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = this.getX();
                mStartY = this.getY();
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mStartX += x - mLastX;
                mStartY += y - mLastY;

                this.setX(mStartX);
                this.setY(mStartY);
                requestLayout();
//                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mStartX = 0;
                mStartY = 0;
                mLastX = 0;
                mLastY = 0;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    public float getXVelocity(int units) {
        mVelocityTracker.computeCurrentVelocity(units);
        return mVelocityTracker.getXVelocity();
    }

    public float getYVelocity(int units) {
        mVelocityTracker.computeCurrentVelocity(units);
        return mVelocityTracker.getYVelocity();
    }

    /**
     * 以当前点和目标点为直径做圆，在圆周上沿着逆时针方向向目标点移动
     * 根据fraction计算当前点的坐标
     *
     * @param startX   The start position (X) in pixels
     * @param startY   The start position (Y) in pixels
     * @param finalX   The final position (X) in pixels
     * @param finalX   The final position (Y) in pixels
     * @param fraction Elapsed/interpolated fraction of the current ongoing animation
     */
    public void detour(float startX, float startY, float finalX, float finalY, float fraction) {
        final float midX = (startX + finalX) / 2;
        final float midY = (startY + finalY) / 2;
        final double radius = Math.sqrt((finalY - startY) * (finalY - startY) + (finalX - startX) * (finalX - startX)) / 2;

        double startAngle = resolveStartAngle(startX, startY, finalX, finalY);
        double curAngle = 180 * fraction + startAngle;

        double curX = midX + radius * Math.cos(curAngle * Math.PI / 180);
        double curY = midY + radius * Math.sin(curAngle * Math.PI / 180);
        this.setX((float) curX);
        this.setY((float) curY);
        Log.d(TAG, "curX:" + curX + ",curY" + curY);
    }

    private double resolveStartAngle(float startX, float startY, float finalX, float finalY) {
        float offsetX = (finalX - startX);
        float offsetY = (finalY - startY);
        double startAngle = Math.atan(offsetY / offsetX) / Math.PI * 180;
        if ((offsetX * offsetY > 0 && offsetX > 0) || (offsetX * offsetY < 0 && offsetX > 0)) {
            startAngle += 180;
        }
        return startAngle;
    }

    /**
     * 以两点确认一条线段，沿着线段向目标点移动。
     * 根据fraction计算当前点的坐标
     *
     * @param startX
     * @param startY
     * @param finalX
     * @param finalY
     * @param fraction
     */
    public void shortCut(float startX, float startY, float finalX, float finalY, float fraction) {
        final float dx = finalX - startX;
        final float dy = finalY - startY;
        double curX = startX + dx * fraction;
        double curY = startY + dy * fraction;

        this.setX((float) curX);
        this.setY((float) curY);
    }
}
