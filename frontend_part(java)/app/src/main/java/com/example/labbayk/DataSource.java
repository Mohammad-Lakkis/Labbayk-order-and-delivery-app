package com.example.labbayk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DataSource {
    SQLiteDatabase database;
    DBHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertUser(User u) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("username", u.getUserName());
            initialValues.put("useremail", u.getUserEmail());
            if (u.getUserPhoto() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                u.getUserPhoto().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photo = baos.toByteArray();
                initialValues.put("userphoto", photo);
            }
            didSucceed = database.insert("user", null, initialValues) > 0;
        } catch (Exception e) {
            Log.d("My Database", "Something went wrong!");
        }
        return didSucceed;
    }

    public boolean updateUser(User u) {
        boolean didSucceed = false;
        try {
            long rowID = u.getUserID();
            ContentValues updatedValues = new ContentValues();
            updatedValues.put("username", u.getUserName());
            updatedValues.put("useremail", u.getUserEmail());
            if (u.getUserPhoto() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                u.getUserPhoto().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photo = baos.toByteArray();
                updatedValues.put("userphoto", photo);
            }
            didSucceed = database.update("user", updatedValues, "_id = " + rowID, null) > 0;
        } catch (Exception ignored) {
        }
        return didSucceed;
    }

    public int getLastUser() {
        int lastId;
        try {
            String query = "Select MAX(_id) from user";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }


    public User getUserFromEmail(String email){
        User u = new User();
        String query = "SELECT * FROM user WHERE useremail = ?";
        Cursor cursor = database.rawQuery(query,new String[]{email});
        if (cursor.moveToFirst()) {
            u.setUserID(cursor.getInt(0));
            u.setUserName(cursor.getString(1));
            u.setUserEmail(cursor.getString(2));
            byte[] photo = cursor.getBlob(3);
            if (photo != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(photo);
                Bitmap userPhoto = BitmapFactory.decodeStream(bais);
                u.setUserPhoto(userPhoto);
            }
        }
        cursor.close();
        return u;
    }


}
