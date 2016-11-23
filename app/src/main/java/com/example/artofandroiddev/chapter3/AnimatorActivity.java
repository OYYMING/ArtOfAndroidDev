package com.example.artofandroiddev.chapter3;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter3.velocity.TrackerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 本Activity演示利用ValueAnimator的AnimatorUpdateListener，同时对多个View的动画进行控制
 * 可以通过拖拽改变两个圆的初始位置
 */
public class AnimatorActivity extends AppCompatActivity {
    private static final String TAG = AnimatorActivity.class.toString();

    private ValueAnimator animator;
    private float mStartX;
    private float mStartY;
    private float mFinalX;
    private float mFinalY;

    @BindView(R.id.tckv_animator_1)
    public TrackerView mTrackerView1;
    @BindView(R.id.tckv_animator_2)
    public TrackerView mTrackerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        ButterKnife.bind(this);

        initAnimator();
    }

    /**
     * 初始化ValueAnimator
     */
    private void initAnimator() {
        animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTrackerView1.detour(mStartX, mStartY, mFinalX, mFinalY, animation.getAnimatedFraction());
                mTrackerView2.shortCut(mFinalX, mFinalY, mStartX, mStartY, animation.getAnimatedFraction());
            }
        });
    }

    /**
     * 交换两个圆的位置，一个圆沿着直线向对方移动，另一个圆沿着两点确认的圆的圆周向对方移动
     * @param view
     */
    protected void exchangePos(View view) {
        if (animator.isRunning())
            return;

        mStartX = mTrackerView1.getX();
        mStartY = mTrackerView1.getY();
        mFinalX = mTrackerView2.getX();
        mFinalY = mTrackerView2.getY();
        Log.d(TAG, "startX:" + mStartX + ",starY:" + mStartY + "     finalX:" + mFinalX + ",finalY:" + mFinalY);

        animator.start();
    }
}
