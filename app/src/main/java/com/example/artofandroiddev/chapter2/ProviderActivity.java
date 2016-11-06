package com.example.artofandroiddev.chapter2;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.Constant;
import com.example.artofandroiddev.base.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ProviderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    private static final String TAG = ProviderActivity.class.toString();

    /*
    The ID of different Loader
     */
    private static final int LOADER_ID_USER = 0;
    private static final int LOADER_ID_BOOK = 1;

    private static final Uri URI_BOOK = Uri.parse(Constant.BOOK_PROVIDER_URISTR);
    private static final Uri URI_USER = Uri.parse(Constant.USER_PROVIDER_URISTR);

    private static final String[] PROJECTION = new String[]{"_id", "name"};

    @BindView(R.id.rb_book)
    public AppCompatRadioButton mRadioButtonBook;
    @BindView(R.id.rb_user)
    public AppCompatRadioButton mRadioButtonUser;
    @BindView(R.id.et_id)
    public AppCompatEditText mId;
    @BindView(R.id.et_name)
    public AppCompatEditText mName;
    @BindView(R.id.lv_result)
    public ListViewCompat mListView;

    private SimpleCursorAdapter mAdapter;

    private LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        ButterKnife.bind(this);

        mLoaderManager = getSupportLoaderManager();

        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, PROJECTION, new int[]{android.R.id.text1, android.R.id.text2});
        mListView.setAdapter(mAdapter);
    }

    /*
    应该使用异步操作因为provider可能很耗时
     */
    protected void insert(View view) {
        Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                ContentValues values = wrapContentValues();
                if (values == null)
                    return;

                Uri uri = getLoaderUri();
                try {
                    Uri resUri = getContentResolver().insert(uri, values);

                    subscriber.onNext(resUri);
                    subscriber.onCompleted();
                } catch (IllegalArgumentException e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        initOrRestartLoader();
                    }
                })
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {

                    }
                });


    }

    protected void delete(View view) {
        Uri uri = getLoaderUri();
        getContentResolver().delete(uri, "_id=? and name=?", new String[]{mId.getText().toString(), mName.getText().toString()});
        initOrRestartLoader();
    }

    protected void update(View view) {
        ContentValues values = wrapContentValues();
        if (values == null)
            return;

        Uri uri = getLoaderUri();
        getContentResolver().update(uri, values, "_id=?", new String[]{mId.getText().toString()});
        initOrRestartLoader();
    }

    protected void retrieve(View view) {
        Uri uri = getLoaderUri();
        getContentResolver().query(uri, PROJECTION, null, null, null);
        initOrRestartLoader();
    }

    private ContentValues wrapContentValues() {
        ContentValues values = new ContentValues();
        int id = 0;
        try {
            id = Integer.parseInt(mId.getText() != null ? mId.getText().toString() : "0");
        } catch (Exception e) {
            Util.alert(this, "_id cannot be parsed as an integer value");
            return null;
        }

        values.put("_id", id);
        values.put("name", mName.getText() != null ? mName.getText().toString() : "");

        return values;
    }

    private void initOrRestartLoader() {
        if (mLoaderManager == null)
            return;

        int id = getLoaderId();
        if (mLoaderManager.getLoader(id) != null) {
            mLoaderManager.restartLoader(id, null, this);
        } else {
            mLoaderManager.initLoader(id, null, this);
        }
    }

    private Uri getLoaderUri() {
        return mRadioButtonBook.isChecked() ? URI_BOOK : URI_USER;
    }

    private int getLoaderId() {
        return mRadioButtonBook.isChecked() ? LOADER_ID_BOOK : LOADER_ID_USER;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        switch (id) {
            case LOADER_ID_BOOK:
                loader = new CursorLoader(this, URI_BOOK, PROJECTION, null, null, null);
                break;
            case LOADER_ID_USER:
                loader = new CursorLoader(this, URI_USER, PROJECTION, null, null, null);
                break;
            default:
                break;
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mAdapter.swapCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }

    private static class Content {
        private int _id;
        private String name;
    }

    //应该用静态内部类来避免内存泄漏吧
//    private static class ContentLoaderListener implements LoaderManager.LoaderCallbacks {
//        private WeakReference<Activity> activityWeakReference;
//
//        public ContentLoaderListener(Activity activity) {
//            activityWeakReference = new WeakReference<Activity>(activity);
//        }
//
//        @Override
//        public Loader onCreateLoader(int id, Bundle args) {
//            Context context = activityWeakReference.get();
//            if (context == null) {
//                Log.e(TAG, "The activity held by LoaderListener is empty");
//                return null;
//            }
//
//            Loader loader = null;
//            switch (id) {
//                case LOADER_ID_BOOK:
//                    loader = new CursorLoader(context, URI_BOOK, PROJECTION, null, null, null);
//                    break;
//                case LOADER_ID_USER:
//                    loader = new CursorLoader(context, URI_USER, PROJECTION, null, null, null);
//                    break;
//                default:
//                    break;
//            }
//
//            return loader;
//        }
//
//        @Override
//        public void onLoadFinished(Loader loader, Object data) {
//
//        }
//
//        @Override
//        public void onLoaderReset(Loader loader) {
//
//        }
//    }
}
