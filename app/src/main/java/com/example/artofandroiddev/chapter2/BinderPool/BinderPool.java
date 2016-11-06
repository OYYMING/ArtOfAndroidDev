package com.example.artofandroiddev.chapter2.BinderPool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.assistantapp.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ouyangym on 2016/11/03.
 */
public class BinderPool {
    private static final String TAG = BinderPool.class.toString();

    private static BinderPool mInstance;

    private final Context mContext;
    private IBinderPool mBinderPool;
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Service is connected");
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service is disconnected");
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBinderPool != null) {
                Log.d(TAG, "Binder died");
                mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);

                mBinderPool = null;

                //重新建立连接
                ConnectToService();
            }
        }
    };

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        ConnectToService();
    }

    public static BinderPool getBinderPool(Context context) {
        if (mInstance == null) {
            synchronized (BinderPool.class) {
                if (mInstance == null) {
                    mInstance = new BinderPool(context);
                }
            }
        }

        return mInstance;
    }

    private synchronized void ConnectToService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.assistantapp", "com.example.assistantapp.chapter2.BinderPoolService"));
        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        try {
            //Wait until the service is bound.Thus we should not query the BinderPool in main thread.
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int code) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(code);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return binder;
    }
}
