package com.example.driver.model;

public class CasesondrivinglicModel {
String id;
String Cases_On_Driving_License;

    public CasesondrivinglicModel(String id, String cases_On_Driving_License) {
        this.id = id;
        Cases_On_Driving_License = cases_On_Driving_License;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCases_On_Driving_License() {
        return Cases_On_Driving_License;
    }

    public void setCases_On_Driving_License(String cases_On_Driving_License) {
        Cases_On_Driving_License = cases_On_Driving_License;
    }
}
