package com.pravrajya.diamond.tables.diamondClarity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DiamondClarity extends RealmObject {

    @PrimaryKey
    private String clarity;
    private String clarityCode;

    public DiamondClarity() {
    }


    public DiamondClarity(String clarity, String clarityCode) {
        this.clarity = clarity;
        this.clarityCode = clarityCode;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getClarityCode() {
        return clarityCode;
    }

    public void setClarityCode(String clarityCode) {
        this.clarityCode = clarityCode;
    }
}
