package com.example.artofandroiddev.chapter3.sameorientationconflict;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.artofandroiddev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用于演示同方向均有滑动事件所造成的滑动冲突的解决方法
 * 使用了作者所说的外部拦截法
 * 但是由于存在被内部的ListView请求不拦截TouchEvent的现象，
 * 所以当ListView滑动到顶部时外层的ScrollView无法接管滑动事件
 * 我通过给ListView添加OnTouchListener解决了这个问题
 */
public class StickyLayoutActivity extends AppCompatActivity {

    @BindView(R.id.sl_scrollable_wrapper)
    public StickyLayout mWrapper;
    @BindView(R.id.tv_sticky_layout_header)
    public TextView mHeader;
    @BindView(R.id.lv_sticky_layout_content)
    public ListView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mWrapper.setHeader(mHeader);
        mWrapper.setContent(mContent);
        // 由于存在被内部的ListView请求不拦截TouchEvent的现象，
        // 可以用setOnTouchListener的方法来消除内部类偷偷调用requestDisallowInterceptTouchEvent(true)的影响
        mContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
    }

}
