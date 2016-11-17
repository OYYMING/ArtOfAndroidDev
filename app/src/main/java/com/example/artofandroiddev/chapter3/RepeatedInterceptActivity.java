package com.example.artofandroiddev.chapter3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SimpleAdapter;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.DummyContent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepeatedInterceptActivity extends AppCompatActivity {

    @BindView(R.id.sv_repeated_intercept)
    public ScrollViewEx mScrollViewEx;

    @BindView(R.id.lv_repeated_intercept)
    public ListViewEx mListView;

    private boolean shouldInterceptedByChild = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeated_intercept);

        ButterKnife.bind(this);

        initViews();
    }

    protected void initViews() {
        initListView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        shouldInterceptedByChild = shouldInterceptedByChild == false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        mListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (shouldInterceptedByChild) {
//                    return false;
//                }
//                return true;
//            }
//        });
//
//        mScrollViewEx.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (!shouldInterceptedByChild) {
//                    return false;
//                }
//                return true;
//            }
//        });
        mListView.setOnInterceptTouchEventListener(new ListViewEx.OnInterceptTouchEventListener() {
            @Override
            public boolean onTouchEvent(MotionEvent ev) {
                Log.d("repeatedIntercept","shouldInterceptedByChild:" + shouldInterceptedByChild);
                if (!shouldInterceptedByChild && mScrollViewEx.onTouchEvent(ev)) {
                    return true;
                }
                return false;
            }
        });
    }

    private void initListView() {
        SimpleAdapter adapter = new SimpleAdapter(this, DummyContent.ITEM_MAP, android.R.layout.simple_list_item_1, new String[]{"KEY"}, new int[]{android.R.id.text1});
        mListView.setAdapter(adapter);
    }
}
