package com.pravrajya.diamond.views.admin.views.products;

import com.pravrajya.diamond.tables.product.ProductList;

import io.realm.annotations.PrimaryKey;

public class Model {

    @PrimaryKey
    private String id;
    private Integer type;
    private String diamondColor;
    private ProductList productLists;

    public Model(String diamondColor, Integer type) {
        this.diamondColor = diamondColor;
        this.type = type;
    }

    public Model(String id, Integer type, String diamondColor, ProductList productLists) {
        this.id = id;
        this.type = type;
        this.diamondColor = diamondColor;
        this.productLists = productLists;
    }

    @Override
    public String toString() {
        return "Model{" +
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
