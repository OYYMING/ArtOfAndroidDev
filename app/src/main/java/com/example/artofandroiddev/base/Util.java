package com.example.artofandroiddev.base;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ouyangym on 2016/10/27.
 */
public class Util {
    public static void alert (Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
