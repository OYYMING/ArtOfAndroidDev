package com.example.artofandroiddev.util;

import android.app.Activity;
import android.content.Context;
import android.text.BoringLayout;
import android.util.DisplayMetrics;

/**
 * Created by ouyangym on 2016/11/14.
 */
public class MyViewUtils {

    /**
     * Return the current display metrics that are in effect for this resource
     * object.  The returned object should be treated as read-only.
     * @param context
     * @return The resource's current display metrics.
     */
    public static DisplayMetrics getScreenMetrics(Context context){
        return context.getResources().getDisplayMetrics();
    }
}
