package com.example.artofandroiddev.chapter4.focuschange;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.artofandroiddev.R;

public class FocusChangedActivity extends AppCompatActivity {
    private static final String TAG = FocusChangedActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_changed);
        Log.d("WindowFocusChanged", "FocusChangedActivity _ onCreate");
    }

    public void isFocused(View view)
    {
        Log.d(TAG, "FocusChangedActivity_" + view.toString() + "::focusable::" + view.isFocused());
    }

    public void alert(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        dialog.setMessage("jsklfjewjr");
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("WindowFocusChanged", "FocusChangedActivity _ onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("WindowFocusChanged", "FocusChangedActivity _ onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("WindowFocusChanged", "FocusChangedActivity _ onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WindowFocusChanged", "FocusChangedActivity _ onResume");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d("WindowFocusChanged", "FocusChangedActivity _ onWindowFocusChanged:" + hasFocus);
        super.onWindowFocusChanged(hasFocus);
    }
}
