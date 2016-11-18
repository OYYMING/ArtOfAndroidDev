package com.example.artofandroiddev.chapter3.repeatedintercepttouchevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.DummyContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 本Activity通过用ScrollView嵌套ListView，并每隔3秒切换滑动事件的消耗者
 * 演示如何在父ViewGroup和子View之间无缝转换事件的消耗者
 * 核心思想在于ScrollView始终不拦截TouchEvent，ListView的onTouchEvent始终返回true
 * 但是可能在ListView的onTouchEvent方法中实际上由Scrollview来消耗事件
 * ps:如果最开始的落点在ListView以外就无法切换TouchEvent的消耗者了，当然可能可以改就是了，不过不要太极端。。
 * PS2:这个可能并没有什么卵用，只是加深滑动冲突理解的小测试，纯属娱乐
 */
public class RepeatedInterceptActivity extends AppCompatActivity {
    private static final String TAG = RepeatedInterceptActivity.class.toString();

    /**
     * 滑动事件是否该由ListView来消耗
     * 每隔3秒切换
     */
    private boolean shouldManagedByChild = false;

    @BindView(R.id.sv_repeated_intercept)
    public ScrollViewEx mScrollViewEx;

    @BindView(R.id.lv_repeated_intercept)
    public ListViewEx mListView;

    @BindView(R.id.tv_touchevent_manager)
    public TextView mEventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeated_intercept);

        ButterKnife.bind(this);

        init();
    }

    protected void init() {
        initListView();

        //每隔3秒切换滑动事件的处理者
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                while (true) {
                    try {
                        subscriber.onNext("滑动事件当前由" + (shouldManagedByChild ? "ListView" : "ScrollView") + "处理");

                        Thread.sleep(3000);
                        shouldManagedByChild = shouldManagedByChild == false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mEventManager.setText(s);
                    }
                });
    }

    private void initListView() {
        SimpleAdapter adapter = new SimpleAdapter(this, DummyContent.ITEM_MAP, android.R.layout.simple_list_item_1, new String[]{"KEY"}, new int[]{android.R.id.text1});
        mListView.setAdapter(adapter);
        mListView.setOnInterceptTouchEventListener(new ListViewEx.OnRepeatedInterceptTouchEventListener() {
            @Override
            public boolean onTouchEvent(MotionEvent ev) {
                Log.d(TAG, "shouldManagedByChild:" + shouldManagedByChild);
                //如果不该由ListView消耗事件，则交由ScrollView来处理
                if (!shouldManagedByChild && mScrollViewEx.onTouchEvent(ev)) {
                    return true;
                } else {
                    //设置这个flag，以确保ScrollView在下次接管滑动事件的时候，会从当前位置继续滑动
                    mScrollViewEx.setFlags(ScrollViewEx.FLAG_RESET_DOWNY);
                }
                return false;
            }
        });

    }
}
