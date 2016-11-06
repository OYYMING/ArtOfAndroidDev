package com.example.assistantapp.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ouyangym on 2016/10/28.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "book_provider.db";
    public static final String TABLE_NAME_USER = "User";
    public static final String TABLE_NAME_BOOK = "Book";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + "(_id INTEGER PRIMARY KEY," + "name VARCHAR(20))";
        String createTableBook = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BOOK + "(_id INTEGER PRIMARY KEY," + "name VARCHAR(20))";

        db.execSQL(createTableUser);
        db.execSQL(createTableBook);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
