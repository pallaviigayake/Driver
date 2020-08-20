package com.example.driver.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Pattern;

public class ApiConstant {

    public final static Pattern PHONE_NUMBER = Pattern.compile("^[789]\\d{9}$");
    private static final String BASE_URL = "http://eleganttransport.co.in/api/";
    public static final String LOGIN = BASE_URL + "/User/login";
    public static final String CITY = BASE_URL +"/Driver/city";
    public static final String STATE = BASE_URL +"/Driver/states";
    public static final String DISTRICT = BASE_URL +"/Driver/district";
    public static final String DRIVEN_TO = BASE_URL +"/Driver/Routes_Driven_to";
    public static final String DRIVEN_FROM = BASE_URL +"/Driver/Routes_Driven_from";
    public static final String RESET_PASSWORD = BASE_URL +"/Driver/resetPasswordUser";
    public static final String PROFILE_UPDATE = BASE_URL +"/User/profileUpdate";
    public static final String INSERT_DRIVER = BASE_URL +"/Driver/insert_driver";
    public static final String DRIVER_REPORT = BASE_URL +"/Driver/Driver_report";
    public static final String LOcal_City = BASE_URL +"/Driver/Local_city";


    public static final String type_of_vehicles = BASE_URL +"/Driver/type_of_vehicles";
    public static final String cases_on_driving_license = BASE_URL +"/Driver/cases_on_driving_license";
    public static final String license_type = BASE_URL +"/Driver/license_type";
    public static final String qualification = BASE_URL +"/Driver/qualification";
    public static final String salary_expectation = BASE_URL +"/Driver/salary_expectation";
    public static final String Licnce_validty = BASE_URL +"/Driver/licensenoExists";





    public static boolean isConnected(Context context){
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork= cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
