package com.example.artofandroiddev.chapter7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter7.eleme.ElemeActivity;
import com.example.artofandroiddev.chapter7.viewanim.ViewAnimActivity;

/**
 * 本章用于演示动画
 */
public class Chapter7MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7_main);
    }

    public void showViewAnimActivity(View view) {
        Intent intent = new Intent(this, ViewAnimActivity.class);
        this.startActivity(intent);
    }

    public void showElemeActivity(View view) {
        Intent intent = new Intent(this, ElemeActivity.class);
        this.startActivity(intent);
    }
}
