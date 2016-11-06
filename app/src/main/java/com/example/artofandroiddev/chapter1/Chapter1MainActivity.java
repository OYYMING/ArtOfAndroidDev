package com.example.artofandroiddev.chapter1;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.artofandroiddev.R;

public class Chapter1MainActivity extends AppCompatActivity {

    private static final String TAG = Chapter1MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter1_main);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause is triggered");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop is triggered");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState is triggered");
    }

    protected void showTransparentActivity(View view) {
        Intent intent = new Intent(this,TransparentActivity.class);
        this.startActivity(intent);
    }


    protected void showSerializeActivity(View view) {
        Intent intent = new Intent(this,SerializeActivity.class);
        this.startActivity(intent);
    }
}
