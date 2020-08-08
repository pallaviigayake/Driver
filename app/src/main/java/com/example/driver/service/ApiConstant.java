package com.example.driver.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Pattern;

public class ApiConstant {

    public final static Pattern PHONE_NUMBER = Pattern.compile("^[789]\\d{9}$");
    private static final String BASE_URL = "http://easycloud.net.in/driver_web/api/User";
    public static final String LOGIN = BASE_URL + "/login";
    public static final String CITY = "http://easycloud.net.in/driver_web/api/Driver/city";
    public static final String STATE = "http://easycloud.net.in/driver_web/api/Driver/states";
    public static final String DISTRICT = "http://easycloud.net.in/driver_web/api/Driver/district";
    public static final String DRIVEN_TO = "http://easycloud.net.in/driver_web/api/Driver/Routes_Driven_to";
    public static final String DRIVEN_FROM = "http://easycloud.net.in/driver_web/api/Driver/Routes_Driven_from";
    public static final String RESET_PASSWORD = "http://easycloud.net.in/driver_web/api/User/resetPasswordUser";
    public static final String PROFILE_UPDATE = "http://easycloud.net.in/driver_web/api/User/profileUpdate";
    public static final String INSERT_DRIVER = "http://easycloud.net.in/driver_web/api/Driver/insert_driver";
    public static final String DRIVER_REPORT = "http://easycloud.net.in/driver_web/api/Driver/Driver_report";
    public static final String LOcal_City = "http://easycloud.net.in/driver_web/api/Driver/Local_city";


    public static final String type_of_vehicles = "http://easycloud.net.in/driver_web/api/Driver/type_of_vehicles";
    public static final String cases_on_driving_license = "http://easycloud.net.in/driver_web/api/Driver/cases_on_driving_license";
    public static final String license_type = "http://easycloud.net.in/driver_web/api/Driver/license_type";
    public static final String qualification = "http://easycloud.net.in/driver_web/api/Driver/qualification";
    public static final String salary_expectation = "http://easycloud.net.in/driver_web/api/Driver/salary_expectation";





    public static boolean isConnected(Context context){
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork= cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
