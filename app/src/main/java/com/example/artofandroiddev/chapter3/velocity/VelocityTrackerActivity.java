package com.example.artofandroiddev.chapter3.velocity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.example.artofandroiddev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用于演示VelocityTracker的速度是瞬时速度
 * 可以通过拖动屏幕上的小球来查看当前滑动速度
 */
public class VelocityTrackerActivity extends AppCompatActivity {

    @BindView(R.id.tckv_velocity_test)
    public TrackerView mTrackerView;
    @BindView(R.id.tx_velocity_10)
    public TextView mVelocity_10;
    @BindView(R.id.tx_velocity_100)
    public TextView mVelocity_100;
    @BindView(R.id.tx_velocity_1000)
    public TextView mVelocity_1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_parameter);

        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mTrackerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVelocity_10.setText(formatVelocityStr(mTrackerView.getXVelocity(10), 10));
                mVelocity_100.setText(formatVelocityStr(mTrackerView.getXVelocity(100), 100));
                mVelocity_1000.setText(formatVelocityStr(mTrackerView.getXVelocity(1000), 1000));

                return false;
            }
        });
    }

    private String formatVelocityStr(float velocity, int units) {
        return this.getResources().getString(R.string.velocity_per_unit, velocity, units);
    }
}
