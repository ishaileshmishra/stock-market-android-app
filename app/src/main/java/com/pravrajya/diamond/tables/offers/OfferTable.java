package com.pravrajya.diamond.tables.offers;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class OfferTable extends RealmObject {

    @PrimaryKey
    private String uid;

    private String date;

    private String title;

    private String price;

    private String carat;

    public OfferTable() { }


    public OfferTable(String uid, String date, String title, String price, String carat) {
        this.uid = uid;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
