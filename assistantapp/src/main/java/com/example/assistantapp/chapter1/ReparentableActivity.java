package com.example.assistantapp.chapter1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.assistantapp.R;

public class ReparentableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reparentable);
    }
}
