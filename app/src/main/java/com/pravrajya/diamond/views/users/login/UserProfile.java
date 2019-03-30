package com.pravrajya.diamond.views.users.login;

public class UserProfile {


    private String userId;
    private String email;
    private String name;
    private String location;
    private String mobile;
    private String loogedInBy;
    private String profileImage;


    public UserProfile(String userId, String email, String name, String location, String mobile, String loogedInBy, String profileImage) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.location = location;
        this.mobile = mobile;
        this.loogedInBy = loogedInBy;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoogedInBy() {
        return loogedInBy;
    }

    public void setLoogedInBy(String loogedInBy) {
        this.loogedInBy = loogedInBy;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
