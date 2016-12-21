package com.example.artofandroiddev.chapter7.eleme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;

/**
 * Created by ouyangym on 2016/12/07.
 */
public class BallPool {
    /**
     * 初始化时创建小球的数量
     */
    private static final int INITIAL_BALL_COUNT = 5;
    /**
     * 当需要扩充{@link #mBalls}时，添加小球的增量
     */
    private static final int INCREMENT_BALL_COUNT = 3;

    private Context mContext;

    /**
     * 放置小球的缓冲池
     * 当数量不足时会扩充
     * 由于没有设置扩充的下限，所以当数量为0时开始扩充
     */
    private LinkedList<ImageView> mBalls;

    /**
     * 小球背景
     */
    private Bitmap mBallBackground;

    public BallPool(Context context) {
        mContext = context;

        initPool();
    }

    /**
     * 初始化
     * 先创建5个球
     */
    private void initPool() {
        mBalls = new LinkedList<>();
        for (int i = 0; i < INITIAL_BALL_COUNT; i++) {
            ImageView imageView = createBall();
            mBalls.addLast(imageView);
        }
    }

    /**
     * 返回一个小球
     * 如果{@link #mBalls}为null则新建{@link #mBalls}
     * 如果{@link #mBalls}的size为0，则添加由{@link #INCREMENT_BALL_COUNT}指定数量的小球
     * @return
     */
    public  ImageView getBall() {
        if (mBalls == null) {
            initPool();
        } else if (mBalls.size() <= 0) {
            for (int i = 0; i < INCREMENT_BALL_COUNT; i++) {
                ImageView imageView = createBall();
                mBalls.add(imageView);
            }
        }

        return mBalls.removeLast();
    }

    /**
     * 创建一个小球
     * @return 新创建的小球
     */
    private ImageView createBall() {
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(100, 100);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(getBallBackground());

        return imageView;
    }

    /**
     * 当小球动画结束后可以将小球返回
     * @param ball
     */
    public synchronized void recycle(ImageView ball) {
        ball.setVisibility(View.VISIBLE);
        mBalls.add(ball);
    }

    /**
     * Clear the pool when the activity no longer exists.
     */
    public void clear() {
        if (mBalls != null && mBalls.size() > 0) {
            mBalls.clear();
            mBalls = null;
        }
    }

    /**
     * 获得小球背景
     * 如果{@link #mBallBackground}为空则通过{@link #createBackgroundFromCanvas}创建
     * @return
     */
    public Bitmap getBallBackground() {
        if (mBallBackground == null)
            mBallBackground = createBackgroundFromCanvas();

        return mBallBackground;
    }

    /**
     * 通过Canvas绘制的方法绘制小球的背景
     * @return  小球的背景
     */
    private Bitmap createBackgroundFromCanvas() {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);

        canvas.drawCircle(50, 50, 50, paint);

        return bitmap;
    }
}
