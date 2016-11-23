package com.example.artofandroiddev.chapter4.circularimageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.artofandroiddev.R;

/**
 * Created by ouyangym on 2016/11/22.
 * CircularImageView是一个圆角ImageView
 * 可以选择是否为圆形，并可设置padding和边框
 */
public class CircularImageView extends ImageView {

    /**
     * 通过app:isCircle设置当前ImageView是否为圆形
     */
    private boolean mIsCircle;

    /**
     * 记录mIsCircle==true时，圆在四个方向上的padding。计算方法为：
     * mCirclePadding = Math.min(getPaddingLeft() + getPaddingRight(), getPaddingTop() + getPaddingBottom()) / 2
     */
    private int mCirclePadding;

    /**
     * 通过app:radius设置的圆角矩形的弧。
     * 只有当mIsCircle==false时，这个值才有效。
     */
    private float mRadius;

    /**
     * 是否为CircularImageView添加边框
     * 默认为false
     */
    private boolean mBorderEnabled = false;
    /**
     * 通过app:border_width设置的边框宽度。
     */
    private float mBorderWidth;

    /**
     * 通过app:border_width设置的边框颜色。
     */
    private int mBorderColor;

    /**
     * the Paint for painting bitmap
     */
    private Paint mPaint;

    /**
     * the Paint for painting border
     */
    private Paint mBorderPaint;

    /**
     * 以{@link #getDrawable()}获得的图片作为Shader，需要用{@link #mMatrix}进行变换.
     */
    private BitmapShader mShader;

    /**
     * BitmapShader的缩放矩阵
     */
    private Matrix mMatrix;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs, 0, 0);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyleAttr, defStyleRes);
        mIsCircle = array.getBoolean(R.styleable.CircularImageView_isCircle, false);
        mRadius = array.getDimensionPixelSize(R.styleable.CircularImageView_radius, 5);
        mBorderEnabled = array.getBoolean(R.styleable.CircularImageView_borderEnabled, false);
        mBorderWidth = array.getDimensionPixelSize(R.styleable.CircularImageView_borderWidth, 1);
        mBorderColor = array.getColor(R.styleable.CircularImageView_borderColor, Color.GRAY);
        array.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
        mBorderPaint.setStrokeJoin(Paint.Join.ROUND);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setUpShader();

        //因为边框线是有宽度的，所以绘制边框时，rectF的位置需要向内部偏移，不然会造成边框粗细不均的情况
        final float borderOffset = mBorderWidth;

        if (mIsCircle) {
            float radius = getMeasuredWidth() / 2;
            //draw the border
            if (mBorderEnabled)
                canvas.drawCircle(radius, radius, radius - borderOffset, mBorderPaint);

            canvas.drawCircle(radius, radius, radius - mCirclePadding, mPaint);
        } else {
            //draw the border
            if (mBorderEnabled) {
                RectF borderRect = new RectF(borderOffset, borderOffset, getMeasuredWidth() - borderOffset, getMeasuredHeight() - borderOffset);
                canvas.drawRoundRect(borderRect, mRadius, mRadius, mBorderPaint);
            }

            float paddingLeft = getPaddingLeft();
            float paddingRight = getPaddingRight();
            float paddingTop = getPaddingTop();
            float paddingBottom = getPaddingBottom();
            RectF rect = new RectF(paddingLeft, paddingTop, getMeasuredWidth() - paddingRight, getMeasuredHeight() - paddingBottom);
            canvas.drawRoundRect(rect, mRadius, mRadius, mPaint);
        }

    }

    /**
     * set up the BitmapShader we paint with
     * we will apply a matrix transformation to the shader because we want the Bitmap fill the CircularImageView's size better
     */
    private void setUpShader() {
        final Drawable drawable = getDrawable();
        if (drawable == null)
            return;

        Bitmap bmp = drawable2Bitmap(drawable);

        final float width = getMeasuredWidth();
        final float height = getMeasuredHeight();

        float drawableWidth = bmp.getWidth();
        float drawableHeight = bmp.getHeight();

        float scale;
        if (mIsCircle) {
            scale = width / Math.min(drawableWidth, drawableHeight);
        } else {
            scale = Math.max(width / drawableWidth, height / drawableHeight);
        }
        mMatrix.setScale(scale, scale);

        mShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mShader.setLocalMatrix(mMatrix);

        mPaint.setShader(mShader);
    }

    /**
     * Convert the drawable to bitmap,cause we will need bitmap as BitmapShader
     * @param drawable 通过{@link #getDrawable()}获得的drawable
     * @return 通过drawable获取的Bitmap
     */
    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        drawable.draw(canvas);

        return bmp;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mIsCircle) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            mCirclePadding = Math.min(getPaddingLeft() + getPaddingRight(), getPaddingTop() + getPaddingBottom()) / 2;
            int size = Math.min(width, height);
            setMeasuredDimension(size, size);
        }
    }
}
