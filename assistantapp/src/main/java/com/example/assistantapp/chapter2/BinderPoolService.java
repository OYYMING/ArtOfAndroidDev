package com.example.assistantapp.chapter2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.assistantapp.AIDLConstant;
import com.example.assistantapp.IBinderPool;

public class BinderPoolService extends Service {
    private static final String TAG = BinderPoolService.class.toString();

    private final IBinderPool.Stub mBinderPoolService = new IBinderPool.Stub() {
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            Binder binder = null;
            switch (binderCode) {
                case AIDLConstant.BINDERCODE_SECURITYCENTER:
                    binder = new SecurityCenter();
                    break;
                default:
                    Log.d(TAG, "The BinderCode:" + binderCode + " is not found");
                    break;
            }
            return binder;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mBinderPoolService;
    }
}
