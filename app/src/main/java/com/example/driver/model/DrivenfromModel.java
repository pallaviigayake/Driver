package com.example.driver.model;

public class DrivenfromModel {
    String id;
    String city;
    String state_id;

    public DrivenfromModel(String id, String city, String state_id) {
        this.id = id;
        this.city = city;
        this.state_id = state_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }
}
