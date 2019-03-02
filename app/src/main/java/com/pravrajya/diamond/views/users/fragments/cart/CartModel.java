package com.pravrajya.diamond.views.users.fragments.cart;

public class CartModel {

    private String uid;
    private String title;
    private String price;

    public CartModel(String uid, String title, String price) {
        this.uid = uid;
        this.title = title;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
