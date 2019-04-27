package com.pravrajya.diamond.views.users.fragments.cart;

public class CartModel {

    private String uid;
    private String title;
    private String price;
    private String carat;

    public CartModel(String uid, String title, String price, String carat) {
        this.uid = uid;
        this.title = title;
        this.price = price;
        this.carat = carat;
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

    public String getCarat() {
        return carat;
    }

    public void setCarat(String carat) {
        this.carat = carat;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", carat='" + carat + '\'' +
                '}';
    }
}
