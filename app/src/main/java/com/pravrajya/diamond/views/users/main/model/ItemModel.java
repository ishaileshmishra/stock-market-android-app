package com.pravrajya.diamond.views.users.main.model;


public class ItemModel {

    private String clarity;
    private String weight;
    private String high;
    private String low;
    private String price;

    public ItemModel(String clarity, String weight, String high, String low, String price) {
        this.clarity = clarity;
        this.weight = weight;
        this.high = high;
        this.low = low;
        this.price = price;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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
        return "ItemModel{" +
                "clarity='" + clarity + '\'' +
                ", weight='" + weight + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
