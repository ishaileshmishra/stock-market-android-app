package com.pravrajya.diamond.tables.cartKlc;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CartKlcModel extends RealmObject {

    @PrimaryKey
    private String uid;
    private String title;
    private double price;
    private double weight;
    private double klcPrice;
    private int qty;

    public CartKlcModel() { }

    public CartKlcModel(String uid, String title, double price, double weight, double klcPrice, int qty) {
        this.uid = uid;
        this.title = title;
        this.price = price;
        this.weight = weight;
        this.klcPrice = klcPrice;
        this.qty = qty;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getKlcPrice() {
        return klcPrice;
    }

    public void setKlcPrice(double klcPrice) {
        this.klcPrice = klcPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
