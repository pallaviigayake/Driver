package com.example.driver.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


public class SessionManager {
  SharedPreferences pref;
  SharedPreferences.Editor editor;
  Context context;
  int PRIVATE_MODE = 0;
  public static final String KEY_TOKEN = "token";
  public static final String KEY_FB_GOOGLE_ID = "User_FB_GOOGLE_ID";
  private static final String KEY_DOB = "DOB";
  private static final String PREF_NAME = "Lcn";
  private static final String IS_LOGIN = "IsLoggedIn";
  public static final String userId = "User_ID";
  public static final String role = "role";
  public static final String roleText = "roleText";
  public static final String name = "name";
  public static final String image = "image";
  public static final String email = "email";
  public static final String mobile = "mobile";
  public static final String lastLogin = "lastLogin";
  public static final String KEY_OTP = "OTP";

  public SessionManager(Context context) {
    this.context = context;
    pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = pref.edit();

  }

  public boolean isLoggedIn() {
    return pref.getBoolean(IS_LOGIN, false);
  }


  public HashMap<String, String> getUSerdetails() {
    HashMap<String, String> user = new HashMap<>();
    user.put(userId, pref.getString(userId, null));
    user.put(role, pref.getString(role, null));
    user.put(roleText, pref.getString(roleText, null));
    user.put(name, pref.getString(name, null));
    user.put(email, pref.getString(email, null));
    user.put(image, pref.getString(image, null));
    user.put(mobile, pref.getString(mobile, null));

    user.put(lastLogin, pref.getString(lastLogin, null));
    return user;
  }

  public void createLoginSession(String id, String rolee, String roleTextt, String namee, String emaill, String imagee, String mobilee,String lastLoginn) {
    editor.putBoolean(IS_LOGIN, true);
    editor.putString(userId, id);
    editor.putString(role, rolee);
    editor.putString(roleText, roleTextt);
    editor.putString(name, namee);
    editor.putString(email, emaill);
    editor.putString(image, imagee);
    editor.putString(mobile, mobilee);
    editor.putString(lastLogin, lastLoginn);
    this.editor.commit();

  }


}
