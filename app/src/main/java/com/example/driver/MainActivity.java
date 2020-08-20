package com.example.driver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.driver.service.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    androidx.appcompat.app.ActionBarDrawerToggle t;
    private NavigationView navigationView;
    AlertDialog dialogLoyalty;
    RelativeLayout relative_UserProfile, relative_insertdriver, relative_TotaldriverReport, relative_DriversMonthlyReport;
    Toolbar toolbar;
    public RelativeLayout rel_driverReport, li_insertdriver, li_updateprofile;
    RelativeLayout logout;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    SessionManager sessionManager;
    HashMap<String, String> hashMap;
    CircleImageView img_profile;
    String name, image;
    TextView tvname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_profile = findViewById(R.id.img_profile);
        li_insertdriver = findViewById(R.id.li_insertdriver);
        logout = findViewById(R.id.logout);
        rel_driverReport = findViewById(R.id.rel_driverReport);
        li_updateprofile = findViewById(R.id.li_updateprofile);
        tvname = findViewById(R.id.tvname);
        sessionManager = new SessionManager(MainActivity.this);
        if (sessionManager.isLoggedIn()) {
            hashMap = sessionManager.getUSerdetails();
            name = hashMap.get(SessionManager.name);
            image = hashMap.get(SessionManager.image);
        }
        tvname.setText(name);
        try {
            Glide.with(getApplicationContext()).load(image).into(img_profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
        li_insertdriver.setOnClickListener(view -> {
            // li_insertdriver.setBackgroundResource(R.drawable.btn_red);

            Intent intent = new Intent(MainActivity.this, Insert_Driver.class);
            startActivityForResult(intent, 123);
        });
        li_updateprofile.setOnClickListener(view -> {

            //li_updateprofile.setBackgroundResource(R.drawable.btn_red);
            Intent intent = new Intent(MainActivity.this, UserProfile.class);
            startActivity(intent);
        });
        rel_driverReport.setOnClickListener(view -> {
            //rel_driverReport.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_red));
            Intent intent = new Intent(MainActivity.this, Driver_Report.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "ff", Toast.LENGTH_SHORT).show();
            //  li_insertdriver.setBackgroundResource(R.drawable.corner_radius_dashbord);
            //li_updateprofile.setBackgroundResource(R.drawable.corner_radius_dashbord);
            //rel_driverReport.setBackgroundResource(R.drawable.corner_radius_dashbord);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showLogoutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View loyaltyView = inflater.inflate(R.layout.alert_exit_ui, null);

        builder.setView(loyaltyView);

        TextView textView = (TextView) loyaltyView.findViewById(R.id.alert_textview);
        textView.setGravity(Gravity.CENTER);
        textView.setText("Sure You want to Logout ?");

        RelativeLayout OkayButton = loyaltyView.findViewById(R.id.btn_positive_alert);
        RelativeLayout cancleButton = loyaltyView.findViewById(R.id.btn_negative_alert);
        dialogLoyalty = builder.create();
        OkayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

//                finish();

            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoyalty.dismiss();
            }
        });

        dialogLoyalty.setCancelable(false);
        dialogLoyalty.show();
        Objects.requireNonNull(dialogLoyalty.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}
