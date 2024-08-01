package com.example.codeseasy.com.customerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RatingDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rating.db";
    private static final int DATABASE_VERSION = 2; // Incrementar la versión de la base de datos

    public RatingDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ratings (order_id TEXT PRIMARY KEY, rating REAL, comment TEXT, comment_status INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE ratings ADD COLUMN comment TEXT");
            db.execSQL("ALTER TABLE ratings ADD COLUMN comment_status INTEGER DEFAULT 0");
        }
    }
}