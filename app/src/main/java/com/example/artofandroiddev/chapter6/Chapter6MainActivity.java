package com.example.artofandroiddev.chapter6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter6.drawablestate.MySpinnerActivity;

public class Chapter6MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter6_main);
    }

    public void showMySpinnerActivity(View view) {
        Intent intent = new Intent(this, MySpinnerActivity.class);
        this.startActivity(intent);
    }
}
