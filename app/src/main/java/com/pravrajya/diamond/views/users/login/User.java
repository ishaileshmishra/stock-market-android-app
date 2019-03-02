package com.pravrajya.diamond.views.users.login;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String userId;
    public String username;
    public String email;
    public Boolean emailVerified;
    public String photoUrl;

    public User(String userId, String username, String email, String photoUrl, Boolean emailVerified) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.photoUrl = photoUrl;
        this.emailVerified = emailVerified;
    }

}