package com.cinnamon.moon.puzzle.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by moonp on 2017-01-12.
 */


public class SqlUtils extends SQLiteOpenHelper {
    public SqlUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE User" +
                "(id CHAR(10) PRIMARY KEY ," +
                " token TEXT," +
                " tokenSecret TEXT," +
                " conKey TEXT," +
                " conSecret TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(ContentValues conValues) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert("User", null, conValues);
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public String[] getData(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("User",
                new String[]{"id", "token", "tokenSecret", "conKey", "conSecret"},
                "id" + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String[] array = new String[]{cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)};

        return array;
    }
}
