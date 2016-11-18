package com.example.artofandroiddev.chapter2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.Constant;
import com.example.artofandroiddev.utils.ToastUtils;
import com.example.assistantapp.IOnUserAddedListener;
import com.example.assistantapp.IUserManager;
import com.example.assistantapp.User;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AIDLClientActivity extends AppCompatActivity {

    private static final String TAG = AIDLClientActivity.class.toString();

    private AppCompatEditText mAcet_name;
    private AppCompatEditText mAcet_age;

    private IUserManager mUserManager;

    private UserAddedHandler mHandler = new UserAddedHandler(this);

    private boolean mIsBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIsBound = true;
            mUserManager = IUserManager.Stub.asInterface(iBinder);
            try {
                iBinder.linkToDeath(new IBinder.DeathRecipient() {
                    //在Binder线程池被回调
                    @Override
                    public void binderDied() {
                        mIsBound = false;
                        Log.d(TAG, "binder is died");

                        if (mUserManager == null)
                            return;

                        mUserManager.asBinder().unlinkToDeath(this, 0);
                        mUserManager = null;

                        Log.d(TAG, "Detect the binder is dead.Try to bind again");
                        bindService();
                    }
                }, 0);

                Log.d(TAG, "Try to register the listener into RemoteService");
                mUserManager.registerListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "service binding succeed");

            getUserList();

            ToastUtils.alert(AIDLClientActivity.this, "service binding succeed");
        }

        /*
        在UI线程被回调
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIsBound = false;
            mUserManager = null;

            Log.d(TAG, "service disconnected");
        }
    };

    private IOnUserAddedListener.Stub mListener = new IOnUserAddedListener.Stub() {
        @Override
        public void onUserAdded(User user) throws RemoteException {
            mHandler.obtainMessage(Constant.MSG_NEW_USER_ADDED, user).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidlclient);

        initViews();
    }

    private void initViews() {
        mAcet_name = (AppCompatEditText) findViewById(R.id.acet_name);
        mAcet_age = (AppCompatEditText) findViewById(R.id.acet_age);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!mIsBound)
            return;

        this.unbindService(mConnection);

        if (mUserManager != null && mUserManager.asBinder().isBinderAlive()) {
            try {
                Log.d(TAG, "Try to unregister the listener from RemoteService");
                mUserManager.unregisterListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    protected void bindService(View view) {
        bindService();
    }

    private void bindService() {
        mIsBound = true;

        if (mUserManager != null && mUserManager.asBinder().isBinderAlive()) {
            ToastUtils.alert(this, "You have bound the service yet");
            return;
        }

        Intent intent = new Intent();
        intent.setAction("com.example.aidl");
        intent.setPackage("com.example.assistantapp");

        Log.d(TAG, "service binding started");
        this.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    protected void addUser(View view) {
        int age = 0;
        try {
            age = Integer.parseInt(mAcet_age.getText().toString());
        } catch (NumberFormatException e) {
            ToastUtils.alert(this, "Age can not be empty");
            return;
        }

        this.addUser(new User(mAcet_name.getText().toString(), age));
    }

    protected void addUserOut(View view) {
        int age = 0;
        try {
            age = Integer.parseInt(mAcet_age.getText().toString());
        } catch (NumberFormatException e) {
            ToastUtils.alert(this, "Age can not be empty");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("NAME", mAcet_name.getText().toString());
        bundle.putInt("AGE", age);
        this.addUserOut(bundle);
    }

    private void getUserList() {
        if (mUserManager != null && mUserManager.asBinder().isBinderAlive()) {
            Observable.create(new Observable.OnSubscribe<List<User>>() {
                @Override
                public void call(Subscriber<? super List<User>> subscriber) {
                    try {
                        List<User> users = mUserManager.getUserList();

                        subscriber.onNext(users);
                        subscriber.onCompleted();
                    } catch (RemoteException e) {
                        e.printStackTrace();

                        subscriber.onError(e);
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1<List<User>, Observable<User>>() {
                        @Override
                        public Observable<User> call(List<User> users) {
                            return Observable.from(users);
                        }
                    })
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.d(TAG, "User list from service is like below:\n");
                        }
                    })
                    .subscribe(new Action1<User>() {
                        @Override
                        public void call(User user) {
                            Log.d(TAG, user.toString() + "\n");
                        }
                    });
        }
    }

    private void addUser(final User user) {
        if (mUserManager != null && mUserManager.asBinder().isBinderAlive()) {
            Observable.create(new Observable.OnSubscribe<User>() {
                @Override
                public void call(Subscriber<? super User> subscriber) {
                    try {
                        mUserManager.addUser(user);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            getUserList();
                        }
                    })
                    .subscribe(new Action1<User>() {
                        @Override
                        public void call(User user) {
                            Log.d(TAG, user.toString() + "\n");
                        }
                    });
        } else {
            ToastUtils.alert(this, "Service has not been bound yet.Please bind first");
        }
    }

    private void addUserOut(final Bundle user) {
        if (mUserManager != null && mUserManager.asBinder().isBinderAlive()) {
            Observable.create(new Observable.OnSubscribe<Bundle>() {
                @Override
                public void call(Subscriber<? super Bundle> subscriber) {
                    try {
                        mUserManager.addUserInOut(user);

                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.d(TAG, "Original Bundle : \n");
                            Log.d(TAG, "Name : " + user.getString("NAME") + "      Age : " + user.getInt("AGE"));
                        }
                    })
                    .subscribe(new Action1<Bundle>() {
                        @Override
                        public void call(Bundle bundle) {
                            Log.d(TAG, "Modified Bundle : \n");
                            Log.d(TAG, "Name : " + user.getString("NAME") + "      Age : " + user.getInt("AGE") + "      Echo : " + user.getString("ECHO"));
                        }
                    });
        } else {
            ToastUtils.alert(this, "Service has not been bound yet.Please bind first");
        }
    }

    protected void registerListener(View view) {
        if (mUserManager != null && mUserManager.asBinder().isBinderAlive()) {
            try {
                mUserManager.registerListener(mListener);
                Log.d(TAG, "Listener has been registered successfully");

                ToastUtils.alert(AIDLClientActivity.this, "Listener has been registered successfully");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.alert(this, "Service has not been bound yet.Please bind first");
        }
    }

    protected void unregisterListener(View view) {
        if (mUserManager != null && mUserManager.asBinder().isBinderAlive()) {
            try {
                mUserManager.unregisterListener(mListener);
                Log.d(TAG, "Listener has been unregistered successfully");

                ToastUtils.alert(AIDLClientActivity.this, "Listener has been unregistered successfully");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.alert(this, "Service has not been bound yet.Please bind first");
        }
    }


    private static class UserAddedHandler extends Handler {
        private WeakReference<Activity> activityWeakReference;

        public UserAddedHandler(Activity activity) {
            activityWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_NEW_USER_ADDED:
                    if (msg != null && msg.obj instanceof User) {
                        Log.d(TAG, "new user is added : " + msg.obj.toString());
                    }
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
