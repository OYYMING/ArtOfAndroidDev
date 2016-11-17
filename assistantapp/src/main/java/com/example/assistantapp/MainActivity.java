package com.example.assistantapp;

import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SharedUID", "assistantapp_Process:" + Process.myPid());
        setContentView(R.layout.activity_main);

        Log.d("ContentProvider","MainActivity_onCreate");
        Log.d("ContentProvider","MainActivity_PID:" + Process.myPid());
    }
}
