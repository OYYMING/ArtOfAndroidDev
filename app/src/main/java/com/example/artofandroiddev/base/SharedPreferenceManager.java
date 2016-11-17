package com.example.artofandroiddev.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

/**
 * Created by ouyangym on 2016/11/06.
 */
public class SharedPreferenceManager {
    public static final String PREFERENCE_NAME = "MySharedPreference";

    protected static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0);
    }

    public static String getString(Context context, String key, @Nullable String defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
}
