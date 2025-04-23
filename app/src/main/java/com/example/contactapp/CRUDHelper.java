package com.example.contactapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class CRUDHelper {
    private final DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public CRUDHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertOrUpdate(String name, String email, String newPhone, String oldPhone) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.UserTable.COLUMN_NAME_NAME, name);
        cv.put(DatabaseContract.UserTable.COLUMN_NAME_EMAIL, email);
        cv.put(DatabaseContract.UserTable.COLUMN_NAME_PHONE, newPhone);

        if (oldPhone != null && !oldPhone.equals(newPhone)) {
            int updated = database.update(
                    DatabaseContract.UserTable.TABLE_NAME,
                    cv,
                    DatabaseContract.UserTable.COLUMN_NAME_PHONE + " = ?",
                    new String[]{ oldPhone }
            );
            if (updated <= 0) {
                database.insert(
                        DatabaseContract.UserTable.TABLE_NAME,
                        null,
                        cv
                );
            }
        } else {
            database.insertWithOnConflict(
                    DatabaseContract.UserTable.TABLE_NAME,
                    null,
                    cv,
                    SQLiteDatabase.CONFLICT_REPLACE
            );
        }
    }

    public void delete(String phone) {
        database.delete(
                DatabaseContract.UserTable.TABLE_NAME,
                DatabaseContract.UserTable.COLUMN_NAME_PHONE + " = ?",
                new String[]{phone}
        );
    }

    public ArrayList<Contact> fetchAllUsers() {
        ArrayList<Contact> contacts = new ArrayList<>();
        String[] columns = {
                DatabaseContract.UserTable.COLUMN_NAME_NAME,
                DatabaseContract.UserTable.COLUMN_NAME_PHONE,
                DatabaseContract.UserTable.COLUMN_NAME_EMAIL
        };
        try (Cursor cursor = database.query(
                DatabaseContract.UserTable.TABLE_NAME,
                columns,
                null, null, null, null,
                DatabaseContract.UserTable.COLUMN_NAME_NAME + " ASC"
        )) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseContract.UserTable.COLUMN_NAME_NAME));
                String phone = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseContract.UserTable.COLUMN_NAME_PHONE));
                String email = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseContract.UserTable.COLUMN_NAME_EMAIL));
                contacts.add(new Contact(name, phone, email));
            }
        }
        return contacts;
    }
}