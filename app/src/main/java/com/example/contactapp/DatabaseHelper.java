package com.example.contactapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContract.UserTable.TABLE_NAME + " (" +
                    DatabaseContract.UserTable.COLUMN_NAME_NAME  + " TEXT, " +
                    DatabaseContract.UserTable.COLUMN_NAME_EMAIL + " TEXT, " +
                    DatabaseContract.UserTable.COLUMN_NAME_PHONE + " TEXT PRIMARY KEY)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.UserTable.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context,
                DatabaseContract.DATABASE_NAME,
                null,
                DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
