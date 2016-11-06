package com.example.assistantapp.chapter2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.assistantapp.base.DbHelper;

/**
 * Created by ouyangym on 2016/10/28.
 */
public class MyContentProvider extends ContentProvider {
    private static final String TAG = MyContentProvider.class.toString();
    private static final String AUTHORITY = "com.example.assistantapp.provider";

    private static final Uri URI_BOOK = Uri.parse("content://" + AUTHORITY + "/book");
    private static final Uri URI_USER = Uri.parse("content://" + AUTHORITY + "/user");

    private static final int CODE_BOOK = 0;
    private static final int CODE_USER = 1;

    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private SQLiteDatabase mDb;

    static {
        mUriMatcher.addURI(AUTHORITY, "book", CODE_BOOK);
        mUriMatcher.addURI(AUTHORITY, "user", CODE_USER);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate()");
        initProviderData();

        return true;
    }

    /*
    initialize the database
     */
    private void initProviderData() {
        mDb = new DbHelper(this.getContext()).getWritableDatabase();
        mDb.execSQL("delete from " + DbHelper.TABLE_NAME_BOOK);
        mDb.execSQL("delete from " + DbHelper.TABLE_NAME_USER);

        mDb.execSQL("insert into " + DbHelper.TABLE_NAME_BOOK + " values(1,'Life is challenge');");
        mDb.execSQL("insert into " + DbHelper.TABLE_NAME_BOOK + " values(2,'Android OS');");
        mDb.execSQL("insert into " + DbHelper.TABLE_NAME_USER + " values(1,'Judith');");
        mDb.execSQL("insert into " + DbHelper.TABLE_NAME_USER + " values(2,'Laden');");
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query()");

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }

        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType()");

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert()");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }

        mDb.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete()");

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }

        int count = mDb.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update()");

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }

        int count = mDb.update(tableName, values, selection, selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    /*
        Match the uri with the UriMatcher,and return the table name by the result code.
     */
    private String getTableName(Uri uri) {
        int code = mUriMatcher.match(uri);
        switch (code) {
            case CODE_BOOK:
                return DbHelper.TABLE_NAME_BOOK;
            case CODE_USER:
                return DbHelper.TABLE_NAME_USER;
            default:
                return null;
        }
    }
}
