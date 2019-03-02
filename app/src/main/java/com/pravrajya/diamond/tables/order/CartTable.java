package com.pravrajya.diamond.tables.order;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class CartTable extends RealmObject {

    @PrimaryKey
    private String uid;

    public CartTable() { }

    public CartTable(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
