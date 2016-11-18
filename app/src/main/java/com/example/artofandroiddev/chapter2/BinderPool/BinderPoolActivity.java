package com.example.artofandroiddev.chapter2.BinderPool;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.utils.ToastUtils;
import com.example.assistantapp.AIDLConstant;
import com.example.assistantapp.ISecurityCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = BinderPoolActivity.class.toString();

    @BindView(R.id.btn_security)
    public Button mSecurityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);

        ButterKnife.bind(this);
    }

    protected void querySecurityBinder(View view) {
        doAfterInitializeBinderPool(new Action1<BinderPool>() {
            @Override
            public void call(BinderPool binderPool) {
                IBinder binder = binderPool.queryBinder(AIDLConstant.BINDERCODE_SECURITYCENTER);
                if (binder != null) {
                    ISecurityCenter securityCenter = ISecurityCenter.Stub.asInterface(binder);
                    Log.d(TAG, "SecurityBinder query succeeded");
                    try {
                        final String encryptedStr = "「" + securityCenter.encrypt("Original String ") + "」";
                        Log.d(TAG, "The encrypted String is " + encryptedStr);
                        BinderPoolActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.alert(BinderPoolActivity.this.getApplicationContext(), "The encrypted String is \n" + encryptedStr);
                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void doAfterInitializeBinderPool(Action1<BinderPool> action1) {
        launchBinderPool(action1);
    }

    private void launchBinderPool(Action1<BinderPool> action1) {
        Observable.create(new Observable.OnSubscribe<BinderPool>() {
            @Override
            public void call(Subscriber<? super BinderPool> subscriber) {
                BinderPool binderPool = BinderPool.getBinderPool(BinderPoolActivity.this);
                try {
                    subscriber.onNext(binderPool);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<BinderPool>() {
                    @Override
                    public void call(BinderPool binderPool) {
                        //ToastUtils.alert(BinderPoolActivity.this,"BinderPool installation succeeded");
                    }
                })
                .subscribe(action1);
    }
}
