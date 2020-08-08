package com.example.driver;


import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.driver.service.SessionManager;

public class SplashScreen extends RuntimePermissionClass {
    private static int SPLASH_SCREEN_TIME_OUT = 4000;
    SessionManager sessionManager;

    private static final int REQUEST_PERMISSIONS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_splash_screen);
        sessionManager = new SessionManager(SplashScreen.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SplashScreen.super.requestAppPermissions(new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CALL_PHONE,
                    }, R.string.runtime_permissions_txt
                    , REQUEST_PERMISSIONS);

        }else{
            splashtime();
        }



    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        splashtime();
    }

    public void splashtime() {
        Thread timethread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    sessionManager = new SessionManager(getApplicationContext());
                    if (sessionManager.isLoggedIn()) {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreen.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        timethread.start();
    }
}