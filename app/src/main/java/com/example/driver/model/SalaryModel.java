package com.example.driver.model;

public class SalaryModel {
    String id;
    String salary_expectation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalary_expectation() {
        return salary_expectation;
    }

    public void setSalary_expectation(String salary_expectation) {
        this.salary_expectation = salary_expectation;
    }

    public SalaryModel(String id, String salary_expectation) {
        this.id = id;
        this.salary_expectation = salary_expectation;
    }


}
