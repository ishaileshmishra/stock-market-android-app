package com.pravrajya.diamond.tables.product;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductTable extends RealmObject {

    @PrimaryKey
    private String id;
    private String color;

    private String stockId;
    private String productWeight;
    private String high;
    private String low;
    private String price;
    private String shape;
    private String size;
    private String clarity;
    private String cut;
    private String polish;
    private String symmetry;
    private String fluorescence;
    private String licence;



    public ProductTable() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
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

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getSymmetry() {
        return symmetry;
    }

    public void setSymmetry(String symmetry) {
        this.symmetry = symmetry;
    }

    public String getFluorescence() {
        return fluorescence;
    }

    public void setFluorescence(String fluorescence) {
        this.fluorescence = fluorescence;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }



    @Override
    public String toString() {
        return "ProductTable{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", stockId='" + stockId + '\'' +
                ", productWeight='" + productWeight + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", price='" + price + '\'' +
                ", shape='" + shape + '\'' +
                ", size='" + size + '\'' +
                ", clarity='" + clarity + '\'' +
                ", cut='" + cut + '\'' +
                ", polish='" + polish + '\'' +
                ", symmetry='" + symmetry + '\'' +
                ", fluorescence='" + fluorescence + '\'' +
                ", licence='" + licence + '\'' +
                '}';
    }
}
