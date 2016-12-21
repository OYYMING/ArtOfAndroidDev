package com.example.artofandroiddev.chapter7.eleme;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artofandroiddev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 本篇用来演示饿了么的食物点击后画出抛物线加入购物车的小球动画
 */
public class ElemeActivity extends AppCompatActivity {

    private FrameLayout mDecor;
    private BallPool mBallPool;
    private int mCount = 0;

    @BindView(R.id.shoppingCar)
    public TextView mShoppingCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleme);

        ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBallPool.clear();
    }

    private void init() {
        mDecor = (FrameLayout) this.getWindow().getDecorView();
        mBallPool = new BallPool(this);
    }

    /**
     * 绑定到Button上的方法
     * 当点击Button时，从当前Button的位置飞出一个小球，并抛到购物车中
     * @param view
     */
    public void throwBalls(View view) {
        ImageView ball = mBallPool.getBall();
        int[] start = new int[2];
        view.getLocationInWindow(start);

        mDecor.addView(ball);
        ball.setX(start[0]);
        ball.setY(start[1]);

        animateToShoppingCar(ball);
    }

    /**
     * 对指定小球实施抛物线动画
     * @param view
     */
    private void animateToShoppingCar(final View view) {
        int[] start = new int[2];
        int[] end = new int[2];
        view.getLocationInWindow(start);
        mShoppingCar.getLocationInWindow(end);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator xAnim = ObjectAnimator.ofFloat(view, "x", end[0]);
        xAnim.setDuration(500);
        xAnim.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator yAnim = ObjectAnimator.ofFloat(view, "y", end[1]);
        yAnim.setDuration(500);
        //越接近购物车的按钮，AnticipateInterpolator的Tension越大
        yAnim.setInterpolator(new AnticipateInterpolator((float)(Math.pow(start[1] / 100,3) / 130 + 2)));
        set.playTogether(xAnim, yAnim);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                mDecor.removeView(view);
                mBallPool.recycle((ImageView)view);
                updateShoppingCar(1);
            }
        });
    }

    /**
     * 小球抛物线动画结束后更新购物车数量
     * @param increment 购物车商品数量的增量，可以为负数
     */
    private void updateShoppingCar(int increment) {
        mCount += increment;
        mShoppingCar.setText("" + mCount);
    }
}
