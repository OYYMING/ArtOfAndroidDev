package com.example.artofandroiddev.chapter2.SharedPreference;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.Constant;
import com.example.artofandroiddev.base.SharedPreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
此包内的Activity和Service用于验证SharedPreference在多进程通信中的不可靠性，
即在一个进程中用SP修改的指无法立刻反映到另一个进程的SP中，取出的指仍为旧值
 */
public class SharedPreferenceActivity extends AppCompatActivity {

    @BindView(R.id.tv_shared_preference_value)
    public TextView mTextView;

    @BindView(R.id.tv_shared_preference_value2)
    public TextView mServiceTextView;

    @BindView(R.id.et_shared_preference_value)
    public TextView mEditText;

    private Messenger mMessenger;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SharedPreference.MSG_VALUE_RESULT:
                    String compositeStr = String.format(SharedPreferenceActivity.this.getString(R.string.shared_preference_service_value),msg.getData().getString(Constant.SharedPreference.MSG_KEY_REPLY));
                    mServiceTextView.setText(compositeStr);
                    break;
                default:
                    break;
            }
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SharedPreference", "onServiceConnected");
            mMessenger = new Messenger(service);
            getValueFromService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        ButterKnife.bind(this);

        String compositeStr = String.format(this.getString(R.string.shared_preference_value), SharedPreferenceManager.getString(this, Constant.SharedPreference.MSG_KEY_REQUEST, null));
        mTextView.setText(compositeStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceConnection != null) {
            this.unbindService(mServiceConnection);
        }
    }

    protected void updateValueLocally(View view) {
        Log.d("SharedPreference", "SharedPreferenceActivity_Thread: " + Process.myPid());
        SharedPreferenceManager.setString(this, Constant.SharedPreference.MSG_KEY_REQUEST, mEditText.getText().toString());
        String compositeStr = String.format(this.getString(R.string.shared_preference_value), SharedPreferenceManager.getString(this, Constant.SharedPreference.MSG_KEY_REQUEST, null));
        mTextView.setText(compositeStr);
    }

    protected void getValueFromService(View view) {
        if (mMessenger == null) {
            Intent intent = new Intent(this, SharedPreferenceService.class);
            this.bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        } else {
            getValueFromService();
        }
    }

    private void getValueFromService ()
    {
        if (mMessenger == null)
            return ;

        Message message = Message.obtain(null, Constant.SharedPreference.MSG_GET_VALUE);
        message.getData().putString(Constant.SharedPreference.MSG_KEY_REQUEST,Constant.SharedPreference.MSG_KEY_REQUEST);
        message.replyTo = new Messenger(mHandler);

        try {
            mMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
