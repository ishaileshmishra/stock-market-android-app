package com.pravrajya.diamond.views.users.fragments.offers;

public class AddModelInCart {

    String uid;
    String title;

    public AddModelInCart() {
    }

    public AddModelInCart(String uid, String title) {
        this.uid = uid;
        this.title = title;
    }

    @Override
    public String toString() {
        return "AddModelInCart{" +
                "uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
