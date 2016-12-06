package com.example.artofandroiddev.chapter1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.artofandroiddev.R;
import com.example.assistantapp.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializeActivity extends AppCompatActivity {

    private static final String TAG = SerializeActivity.class.toString();

    private static User mSerializedUser;

    static {
        mSerializedUser = new User("Blook", 100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialize);
    }

    public void serializeUser(View view) {
        try {
            Log.d(TAG, "User waiting for serialization : " + mSerializedUser.toString() + "\n");

            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.getFilesDir().getPath().toString() + "/cache.txt"));
            stream.writeObject(mSerializedUser);
            stream.close();

            Toast.makeText(this, "serialization succeed", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeUser(View view) {
        try {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(this.getFilesDir().getPath().toString() + "/cache.txt"));
            User user = (User) stream.readObject();
            Log.d(TAG, "User serialized : " + user.toString() + "\n");
            stream.close();

            Toast.makeText(this, "deserializeUser succeed", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;

        private String mName;

        private int mAge;

        public User(String name, int age) {
            mName = name;
            mAge = age;
        }


        @Override
        public String toString() {
            return "User_name : " + mName + "   age : " + mAge;
        }
    }
}
