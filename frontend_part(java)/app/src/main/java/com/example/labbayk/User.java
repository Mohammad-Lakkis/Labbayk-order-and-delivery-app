package com.example.labbayk;

import android.graphics.Bitmap;

public class User {
    private int userID;
    private String userName;
    private String userEmail;
    private Bitmap userPhoto;

    public User(){
        userID = -1;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Bitmap getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Bitmap userPhoto) {
        this.userPhoto = userPhoto;
    }
}
