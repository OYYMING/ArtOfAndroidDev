package com.example.artofandroiddev.chapter2.Messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.Constant;

public class MessengerClientActivity extends AppCompatActivity {

    private static final String TAG = MessengerClientActivity.class.toString();
    private Messenger mService;
    private Messenger mClient;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);

            Message request = Message.obtain(null,Constant.MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.MSG_KEY_REQUEST,"Hello,this is client");
            request.setData(bundle);
            request.replyTo = mClient;

            try {
                mService.send(request);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);

        Log.d(TAG, "The PID is : " + Process.myPid());

        mClient = new Messenger(new MessengerHandler());
        startService();

        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.unbindService(mConnection);
    }

    private void startService ()
    {
        Intent intent = new Intent(this,MessengerService.class);
        this.bindService(intent,mConnection,BIND_AUTO_CREATE);
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_SERVER:
                    String reply = msg.getData().getString(Constant.MSG_KEY_REPLY);
                    Log.d(TAG, reply);
                default:
                    super.handleMessage(msg);

            }
        }
    }
}
