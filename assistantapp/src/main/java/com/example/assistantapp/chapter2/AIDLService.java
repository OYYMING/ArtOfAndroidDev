package com.example.assistantapp.chapter2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.example.assistantapp.IOnUserAddedListener;
import com.example.assistantapp.IUserManager;
import com.example.assistantapp.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AIDLService extends Service {
    private static final String TAG = AIDLService.class.toString();
    private static final String ACCESS_USER_SERVICE = "com.example.assistantapp.permission.ACCESS_USER_SERVICE";

    private AtomicBoolean mIsServiceAlive = new AtomicBoolean(true);

    private CopyOnWriteArrayList<User> mUserList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnUserAddedListener> mCallbackList = new RemoteCallbackList<>();

    private IUserManager.Stub mUserManager = new IUserManager.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (!checkPermission(ACCESS_USER_SERVICE)) {
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<User> getUserList() throws RemoteException {
//            if (!checkPermission(ACCESS_USER_SERVICE)) {
//                return null;
//            }

            synchronized (this) {
                if (mUserList == null || mUserList.size() == 0) {
                    mUserList.add(new User("jacob", 122));
                }

                return mUserList;
            }
        }

        @Override
        public void addUser(User user) throws RemoteException {
//            if (!checkPermission(ACCESS_USER_SERVICE)) {
//                return;
//            }

            synchronized (this) {
                if (mUserList == null) {
                    mUserList = new CopyOnWriteArrayList<>();
                }

                mUserList.add(user);

                SystemClock.sleep(5000);
            }
        }

        @Override
        public void addUserInOut(Bundle user) throws RemoteException {
//            if (!checkPermission(ACCESS_USER_SERVICE)) {
//                return;
//            }

            synchronized (this) {
                String name = user.getString("NAME");
                int age = user.getInt("AGE");
                addUser(new User(name, age));

//                user.putString("NAME", "NewMan");
                user.putString("ECHO", "This is echo from method addUserOut");
            }
        }

        @Override
        public void registerListener(IOnUserAddedListener listener) throws RemoteException {
            Log.d(TAG, "The client is not authorized");

//            if (!checkPermission(ACCESS_USER_SERVICE)) {
//                return;
//            }

            Log.d(TAG, "New listener is registered");
            mCallbackList.register(listener);
        }

        @Override
        public void unregisterListener(IOnUserAddedListener listener) throws RemoteException {
            Log.d(TAG, "The client is not authorized");

//            if (!checkPermission(ACCESS_USER_SERVICE)) {
//                return;
//            }

            Log.d(TAG, "Listener is unregistered");
            mCallbackList.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        mUserList.add(new User("adam", 1));

        Thread workerThread = new Thread(new WorkerThread());
        workerThread.setDaemon(true);
        workerThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mIsServiceAlive.set(false);
    }

    /*
        * This thread is used for adding new user into UserList,and notifying all the observers.
        * */
    private class WorkerThread implements Runnable {
        @Override
        public void run() {
            while (mIsServiceAlive.get()) {
                User user = new User("NewMan", mUserList.size() + 1);
                onUserAdded(user);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onUserAdded(User user) {
        mUserList.add(user);
        notifyAllListeners(user);

        Log.d(TAG, "new user is added : " + user.toString());
    }

    private void notifyAllListeners(User user) {
        int count = mCallbackList.beginBroadcast();
        for (int i = 0; i < count; i++) {
            IOnUserAddedListener l = mCallbackList.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.onUserAdded(user);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        mCallbackList.finishBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind____________CallingPID:" + Binder.getCallingPid() + "     ProcessID:" + Process.myPid());
        return mUserManager;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mIsServiceAlive.set(false);
        Log.d(TAG, "The service is unbound from client");
        return super.onUnbind(intent);
    }

    private boolean checkPermission(String permission) {
        Log.d(TAG, "checkPermission____________CallingPID:" + Binder.getCallingPid() + "     ProcessID:" + Process.myPid());
        if (Binder.getCallingPid() == Process.myPid()) {
            Log.d(TAG, "The calling process is the same as current process");
            return true;
        }

        if (checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "The client has been granted the permission");
            return true;
        }
        Log.d(TAG, "The client has not been granted the permission");
        return false;
    }
}
