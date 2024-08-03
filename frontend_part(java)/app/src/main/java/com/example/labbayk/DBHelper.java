package com.example.labbayk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myusers.db";
    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_TABLE_USER =
            "create table user(" +
                    "_id integer primary key autoincrement," +
                    "username text," +
                    "useremail text primary key," +
                    "userphoto blob);";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("ALTER TABLE user ADD COLUMN userphoto blob");
        } catch (Exception ignored) {
        }
    }
}
