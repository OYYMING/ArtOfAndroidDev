package com.example.artofandroiddev.chapter4.testadjustviewbounds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.artofandroiddev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 本Activity是用来测试adjustVieBounds这个属性
 * 以及比较measure一个wrap_content的View得到的measuredWidth和实际的width的大小
 */
public class AdjustViewBoundsActivity extends AppCompatActivity {
    private static final String TAG = AdjustViewBoundsActivity.class.toString();
    
    @BindView(R.id.iv_doge)
    public ImageView mDoge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_view_bounds);

        ButterKnife.bind(this);
        measure(mDoge);

        mDoge.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "Width:" + mDoge.getWidth());
                Log.d(TAG, "Height:" + mDoge.getHeight());
            }
        });
    }

    private void measure(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        view.measure(widthMeasureSpec,heightMeasureSpec);

        Log.d(TAG, "MeasuredWidth:" + view.getMeasuredWidth());
        Log.d(TAG, "MeasuredHeight:" + view.getMeasuredHeight());
    }
}
