package com.pravrajya.diamond.tables.diamondSize;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DiamondSize extends RealmObject {

    @PrimaryKey
    private String size;
    private String image;

    public DiamondSize() {
    }

    public DiamondSize(String size, String image) {
        this.size = size;
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
