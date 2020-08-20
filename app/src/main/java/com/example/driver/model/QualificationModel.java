package com.example.driver.model;

public class QualificationModel {
    String id;
    String Qualification;

    public QualificationModel(String id, String qualification) {
        this.id = id;
        Qualification = qualification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }
}