package com.pravrajya.diamond.views.users.login;

public class UserProfile {


    private String userId;
    private String email;
    private String name;
    private String profileImage;


    public UserProfile(String userId, String email, String name, String profileImage) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
