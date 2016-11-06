package com.example.artofandroiddev.chapter1;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.artofandroiddev.R;

public class TransparentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
    }

    protected void showReparentableActivity(View view)
    {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.assistantapp","com.example.assistantapp.chapter1.ReparentableActivity"));

        this.startActivity(intent);
    }
}
