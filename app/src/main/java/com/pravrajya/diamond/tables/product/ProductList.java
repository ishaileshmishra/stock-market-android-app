package com.pravrajya.diamond.tables.product;

import io.realm.RealmObject;

public class ProductList extends RealmObject {

    private String product;
    private String productWeight;
    private String high;
    private String low;
    private String price;

    public ProductList() { }

    public ProductList(String product, String productWeight, String high, String low, String price) {
        this.product = product;
        this.productWeight = productWeight;
        this.high = high;
        this.low = low;
        this.price = price;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "ProductList{" +
                "product='" + product + '\'' +
                ", productWeight='" + productWeight + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
