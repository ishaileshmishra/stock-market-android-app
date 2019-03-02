package com.pravrajya.diamond.tables.diamondColor;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DiamondColor extends RealmObject {

    @PrimaryKey
    private String color;
    private String colorCode;

    public DiamondColor() {
    }

    public DiamondColor(String color, String colorCode) {
        this.color = color;
        this.colorCode = colorCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
