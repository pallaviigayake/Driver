package com.example.driver.model;

public class DistrictModel {
    String id;
    String district;

    public DistrictModel(String id, String district) {
        this.id = id;
        this.district = district;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
