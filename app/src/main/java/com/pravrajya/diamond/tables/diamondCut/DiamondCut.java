package com.pravrajya.diamond.tables.diamondCut;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DiamondCut extends RealmObject {

    @PrimaryKey
    private String cut_type;
    private String cut_url;

    public DiamondCut() {
    }

    public DiamondCut(String cut_type, String cut_url) {
        this.cut_type = cut_type;
        this.cut_url = cut_url;
    }

    public String getCut_type() {
        return cut_type;
    }

    public void setCut_type(String cut_type) {
        this.cut_type = cut_type;
    }

    public String getCut_url() {
        return cut_url;
    }

    public void setCut_url(String cut_url) {
        this.cut_url = cut_url;
    }
}
