package com.example.artofandroiddev.chapter4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter4.circularimageview.CircularImageViewActivity;
import com.example.artofandroiddev.chapter4.flowlayout.FlowLayoutActivity;
import com.example.artofandroiddev.chapter4.focuschange.FocusChangedActivity;
import com.example.artofandroiddev.chapter4.testadjustviewbounds.AdjustViewBoundsActivity;

public class Chapter4MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_main);
        Log.d("WindowFocusChanged", "Chapter4MainActivity _ onCreate");
    }


    public void showCircularImageViewActivity(View view) {
        Intent intent = new Intent(this, CircularImageViewActivity.class);
        this.startActivity(intent);
    }

    public void showFlowLayoutActivity(View view) {
        Intent intent = new Intent(this, FlowLayoutActivity.class);
        this.startActivity(intent);
    }

    public void showAdjustViewBoundsActivity(View view) {
        Intent intent = new Intent(this, AdjustViewBoundsActivity.class);
        this.startActivity(intent);
    }

    public void showFocusChangedActivity(View view) {
        Intent intent = new Intent(this, FocusChangedActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("WindowFocusChanged", "Chapter4MainActivity _ onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("WindowFocusChanged", "Chapter4MainActivity _ onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("WindowFocusChanged", "Chapter4MainActivity _ onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WindowFocusChanged", "Chapter4MainActivity _ onResume");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d("WindowFocusChanged", "Chapter4MainActivity _ onWindowFocusChanged:" + hasFocus);
        super.onWindowFocusChanged(hasFocus);
    }
}
