package com.example.artofandroiddev.base;

/**
 * Created by ouyangym on 2016/10/26.
 */
public class Constant {
    public static final int MSG_FROM_CLIENT = 10001;

    public static final int MSG_FROM_SERVER = 10002;

    public static final String MSG_KEY_REQUEST = "MSG_KEY_REQUEST";

    public static final String MSG_KEY_REPLY = "MSG_KEY_REPLY";

    public static final int MSG_NEW_USER_ADDED = 10003;

    public static final String BOOK_PROVIDER_URISTR = "content://com.example.assistantapp.provider/book";

    public static final String USER_PROVIDER_URISTR = "content://com.example.assistantapp.provider/user";

    public static class SharedPreference {
        public static final int MSG_GET_VALUE = 10004;

        public static final int MSG_VALUE_RESULT = 10005;

        public static final String MSG_KEY_REQUEST = "MSG_KEY_REQUEST";

        public static final String MSG_KEY_REPLY = "MSG_KEY_REPLY";
    }
}
