package com.example.artofandroiddev.chapter2.SharedPreference;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.Constant;
import com.example.artofandroiddev.base.SharedPreferenceManager;

public class SharedPreferenceService extends Service {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SharedPreference.MSG_GET_VALUE:
                    String key = msg.getData().getString(Constant.SharedPreference.MSG_KEY_REQUEST);
                    Message message = Message.obtain(null,Constant.SharedPreference.MSG_VALUE_RESULT);
                    message.getData().putString(Constant.SharedPreference.MSG_KEY_REPLY,getValue(key,null));
                    try {
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public SharedPreferenceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new Messenger(mHandler).getBinder();
    }

    private String getValue(String key, String defValue) {
        Log.d("SharedPreference", "SharedPreferenceService_Thread: " + Process.myPid());
        Log.d("SharedPreference", "The search key is: " + key);
        return SharedPreferenceManager.getString(this, key, defValue);
    }

}
