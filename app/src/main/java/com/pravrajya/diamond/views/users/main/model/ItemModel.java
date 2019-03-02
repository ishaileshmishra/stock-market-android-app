package com.pravrajya.diamond.views.users.main.model;

import java.util.ArrayList;


public class ItemModel {

    private String drink;
    private String high;
    private String low;
    private String price;


    public ItemModel(String drink, String high, String low, String price) {
        this.drink = drink;
        this.high = high;
        this.low = low;
        this.price = price;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
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

    public static ArrayList<ItemModel> getDataList(){

        ArrayList<ItemModel>  data_model = new ArrayList<>();
        data_model.add(new ItemModel("Breezer (0.10 gm)","135","130","130"));
        data_model.add(new ItemModel("Breezer bucket (0.10 gm)","500","400","490"));
        data_model.add(new ItemModel("Budweiser (0.10 gm)","530","400","400"));
        data_model.add(new ItemModel("Budweiser Magnimum (0.10 gm)","440","400","440"));
        data_model.add(new ItemModel("Carlsburg (0.10 gm)","450","400","420"));
        return  data_model;
    }


    public static ArrayList<ItemModel> getRefreshDataList(){

        ArrayList<ItemModel>  data_model = new ArrayList<>();
        data_model.add(new ItemModel("Breezer (0.10 gm)","135","130","133"));
        data_model.add(new ItemModel("Breezer bucket (0.10 gm)","500","400","430"));
        data_model.add(new ItemModel("Budweiser (0.10 gm)","530","400","500"));
        data_model.add(new ItemModel("Budweiser Magnimum (0.10 gm)","440","400","420"));
        data_model.add(new ItemModel("Carlsburg (0.10 gm)","450","400","430"));
        return  data_model;
    }

}
