package com.example.artofandroiddev.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter1.Chapter1MainActivity;
import com.example.artofandroiddev.chapter2.Chapter2MainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void showChapter1(View view) {
        this.startActivity(new Intent(this, Chapter1MainActivity.class));
    }

    protected void showChapter2(View view) {
        this.startActivity(new Intent(this, Chapter2MainActivity.class));
    }
}
