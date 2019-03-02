package com.pravrajya.diamond.tables.product;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductTable extends RealmObject {

    @PrimaryKey
    private String id;
    private Integer type;
    private String diamondColor;
    private ProductList productLists;

    public ProductTable() { }

    public ProductTable(String diamondColor, Integer type) {
        this.diamondColor = diamondColor;
        this.type = type;
    }

    public ProductTable(String id, Integer type, String diamondColor, ProductList productLists) {
        this.id = id;
        this.type = type;
        this.diamondColor = diamondColor;
        this.productLists = productLists;
    }

    @Override
    public String toString() {
        return "ProductTable{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", diamondColor='" + diamondColor + '\'' +
                ", productLists=" + productLists +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDiamondColor() {
        return diamondColor;
    }

    public void setDiamondColor(String diamondColor) {
        this.diamondColor = diamondColor;
    }

    public ProductList getProductLists() {
        return productLists;
    }

    public void setProductLists(ProductList productLists) {
        this.productLists = productLists;
    }
}
