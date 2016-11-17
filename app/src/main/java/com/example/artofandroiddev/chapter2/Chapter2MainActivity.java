package com.example.artofandroiddev.chapter2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.chapter2.BinderPool.BinderPoolActivity;
import com.example.artofandroiddev.chapter2.Messenger.MessengerClientActivity;
import com.example.artofandroiddev.chapter2.SharedPreference.SharedPreferenceActivity;

public class Chapter2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter2_main);
    }

    protected void showAIDLClientActivity(View view) {
        Intent intent = new Intent(this,AIDLClientActivity.class);
        this.startActivity(intent);
    }

    protected void showMessengerClientActivity(View view) {
        Intent intent = new Intent(this,MessengerClientActivity.class);
        this.startActivity(intent);
    }

    protected void showProviderActivity(View view) {
        Intent intent = new Intent(this,ProviderActivity.class);
        this.startActivity(intent);
    }

    protected void showBinderPoolActivity(View view) {
        Intent intent = new Intent(this,BinderPoolActivity.class);
        this.startActivity(intent);
    }

    protected void showSharedPreferenceActivity(View view) {
        Intent intent = new Intent(this,SharedPreferenceActivity.class);
        this.startActivity(intent);
    }
}
