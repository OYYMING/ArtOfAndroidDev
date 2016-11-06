package com.example.artofandroiddev.chapter2.Messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.example.artofandroiddev.base.Constant;

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.toString();

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    public MessengerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "The PID is : " + Process.myPid());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_CLIENT:
                    Log.d(TAG, msg.getData().getString(Constant.MSG_KEY_REQUEST));

                    Messenger client = msg.replyTo;
                    Message reply = Message.obtain(null,Constant.MSG_FROM_SERVER);
                    Bundle replyData = new Bundle();
                    replyData.putString(Constant.MSG_KEY_REPLY,"Hello there.This is message from Mars!!!");
                    reply.setData(replyData);

                    try {
                        client.send(reply);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                default:
                    super.handleMessage(msg);

            }
        }
    }
}
