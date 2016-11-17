package com.example.artofandroiddev.base;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter1.Chapter1MainActivity;
import com.example.artofandroiddev.chapter2.Chapter2MainActivity;
import com.example.artofandroiddev.chapter3.Chapter3MainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("SharedUID", "APP_Process:" + Process.myPid());
    }

    protected void showChapter1(View view) {
        this.startActivity(new Intent(this, Chapter1MainActivity.class));
    }

    protected void showChapter2(View view) {
        this.startActivity(new Intent(this, Chapter2MainActivity.class));
    }

    protected void showChapter3(View view) {
        this.startActivity(new Intent(this, Chapter3MainActivity.class));
    }
}
