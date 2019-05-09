/**
 *
 * Copyright © 2012-2020 [Shailesh Mishra](https://github.com/mshaileshr/). All Rights Reserved
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pravrajya.diamond.tables.product;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductTable extends RealmObject {

    @PrimaryKey
    private String id;
    private String color;
    private String stockId;
    private String productWeight;
    private String lowHigh;
    private String price;
    private String shape;
    private String shade;
    private String size;
    private String clarity;
    private String cut;
    private String polish;
    private String symmetry;
    private String culet;
    private String fluorescence;
    private String licence;
    private String vedioLink;


    public ProductTable() { }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getLowHigh() {
        return lowHigh;
    }

    public void setLowHigh(String lowHigh) {
        this.lowHigh = lowHigh;
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

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getCulet() {
        return culet;
    }

    public void setCulet(String culet) {
        this.culet = culet;
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

    public String getVedioLink() {
        return vedioLink;
    }

    public void setVedioLink(String vedioLink) {
        this.vedioLink = vedioLink;
    }


    @Override
    public String toString() {
        return "ProductTable{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", stockId='" + stockId + '\'' +
                ", productWeight='" + productWeight + '\'' +
                ", lowHigh='" + lowHigh + '\'' +
                ", price='" + price + '\'' +
                ", shape='" + shape + '\'' +
                ", shade='" + shade + '\'' +
                ", size='" + size + '\'' +
                ", clarity='" + clarity + '\'' +
                ", cut='" + cut + '\'' +
                ", polish='" + polish + '\'' +
                ", symmetry='" + symmetry + '\'' +
                ", culet='" + culet + '\'' +
                ", fluorescence='" + fluorescence + '\'' +
                ", licence='" + licence + '\'' +
                ", vedioLink='" + vedioLink + '\'' +
                '}';
    }
}
