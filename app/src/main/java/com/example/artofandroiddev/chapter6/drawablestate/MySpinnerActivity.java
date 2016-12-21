package com.example.artofandroiddev.chapter6.drawablestate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.DummyContent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用来演示自定义DrawableState的MySpinner
 * 当Spinner展开和折叠时会应用不同的背景
 */
public class MySpinnerActivity extends AppCompatActivity {

    @BindView(R.id.sp_custom_state)
    public MySpinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spinner);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DummyContent.ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("MySpinnerActivity", "onWindowFocusChanged:" + hasFocus);
    }
}
