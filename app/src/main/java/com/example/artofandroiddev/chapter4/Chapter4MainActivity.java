package com.example.artofandroiddev.chapter4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter4.circularimageview.CircularImageViewActivity;

public class Chapter4MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4_main);
    }


    protected void showCircularImageViewActivity(View view) {
        Intent intent = new Intent(this, CircularImageViewActivity.class);
        this.startActivity(intent);
    }
}
