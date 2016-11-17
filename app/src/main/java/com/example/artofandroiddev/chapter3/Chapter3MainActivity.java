package com.example.artofandroiddev.chapter3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter3.diforientationconflict.HorizontalScrollViewActivity;
import com.example.artofandroiddev.chapter3.sameorientationconflict.StickyLayoutActivity;
import com.example.artofandroiddev.chapter3.velocity.VelocityTrackerActivity;

public class Chapter3MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter3_main);
    }

    protected void showRepeatedInterceptActivity(View view) {
        Intent intent = new Intent(this, RepeatedInterceptActivity.class);
        this.startActivity(intent);
    }

    protected void showVelocityTrackerActivity(View view) {
        Intent intent = new Intent(this, VelocityTrackerActivity.class);
        this.startActivity(intent);
    }

    protected void showAnimatorActivity(View view) {
        Intent intent = new Intent(this, AnimatorActivity.class);
        this.startActivity(intent);
    }

    protected void showHorizontalScrollViewActivity(View view) {
        Intent intent = new Intent(this, HorizontalScrollViewActivity.class);
        this.startActivity(intent);
    }

    protected void showStickyLayoutActivity(View view) {
        Intent intent = new Intent(this, StickyLayoutActivity.class);
        this.startActivity(intent);
    }
}
