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
import com.example.artofandroiddev.chapter4.Chapter4MainActivity;
import com.example.artofandroiddev.chapter6.Chapter6MainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("SharedUID", "APP_Process:" + Process.myPid());
    }

    public void showChapter1(View view) {
        this.startActivity(new Intent(this, Chapter1MainActivity.class));
    }

    public void showChapter2(View view) {
        this.startActivity(new Intent(this, Chapter2MainActivity.class));
    }

    public void showChapter3(View view) {
        this.startActivity(new Intent(this, Chapter3MainActivity.class));
    }

    public void showChapter4(View view) {
        this.startActivity(new Intent(this, Chapter4MainActivity.class));
    }

    public void showChapter6(View view) {
        this.startActivity(new Intent(this, Chapter6MainActivity.class));
    }
}
