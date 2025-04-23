package com.example.contactapp;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {}

    public static final String DATABASE_NAME = "contact.db";
    public static final int DATABASE_VERSION = 1;

    public static class UserTable implements BaseColumns {
        public static final String TABLE_NAME       = "contact";
        public static final String COLUMN_NAME_NAME  = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PHONE = "phone";
    }

}
