package com.example.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver.model.CasesondrivinglicModel;
import com.example.driver.model.CityModel;
import com.example.driver.model.Datum;
import com.example.driver.model.DistrictModel;
import com.example.driver.model.DrivenToModel;
import com.example.driver.model.DrivenfromModel;
import com.example.driver.model.LicensetypeModel;
import com.example.driver.model.LocalCityModel;
import com.example.driver.model.QualificationModel;
import com.example.driver.model.SalaryModel;
import com.example.driver.model.StateModel;
import com.example.driver.service.ApiConstant;
import com.example.driver.service.AppController;
import com.example.driver.service.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.sax2.Driver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Insert_Driver extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    @Override
    public void onBackPressed() {
/*
        MainActivity.li_insertdriver.setBackgroundDrawable(getResources().getDrawable(R.drawable.corner_radius_dashbord));
        MainActivity.li_updateprofile.setBackgroundDrawable(getResources().getDrawable(R.drawable.corner_radius_dashbord));
        MainActivity.rel_driverReport.setBackgroundDrawable(getResources().getDrawable(R.drawable.corner_radius_dashbord));*/


    }

    private TextView tvDisplayDate;

    private Button btnChangeDate;

    private int myear;
    private int mmonth;
    private int mday;

    static final int DATE_DIALOG_ID = 999;

    KProgressHUD dialog;
    TextView tv_license_expiry_date;
    ImageView iv_insertdriver;
    ImageView img_licenseimg, img_Adhar_pan, img_ref_address_proof1, img_ref_address_proof2;
    Spinner spinner_personal_Education, spinner_salary, spinner_personal_driven_to, spinner_license_type, spinner_casesonDrvinglicense;
    Spinner spinner_personal_city, spinner_personal_statee, spinner_personal_District, spinner_personal_driven_from;
    int var = 0;
    LinearLayout li_allcheckbox;
    SessionManager sessionManager;
    CircleImageView img_profile;
    AlertDialog dialogLoyalty;
    ArrayList<CityModel> categoryModelArrayList = new ArrayList<>();
    LinearLayout li_spinner_routes_driven_to, li_spinner_routes_driven_from, li_spinner_localcity;
    private ArrayList<String> cityName = new ArrayList<String>();
    private ArrayList<String> cityId = new ArrayList<String>();


    private ArrayList<String> salaryName = new ArrayList<>();
    private ArrayList<String> salaryId = new ArrayList<>();

    private ArrayList<String> qulificationName = new ArrayList<>();
    private ArrayList<String> qulificationId = new ArrayList<>();


    private ArrayList<String> license_typeName = new ArrayList<>();
    private ArrayList<String> license_typeId = new ArrayList<>();


    private ArrayList<String> cases_on_driving_licenseName = new ArrayList<>();
    private ArrayList<String> cases_on_driving_licenseId = new ArrayList<>();


    private ArrayList<String> localcityName = new ArrayList<String>();
    private ArrayList<String> localcityId = new ArrayList<String>();

    private ArrayList<String> stateName = new ArrayList<String>();
    private ArrayList<String> stateId = new ArrayList<String>();

    private ArrayList<String> DistrictName = new ArrayList<String>();
    private ArrayList<String> DistrictId = new ArrayList<String>();


    private ArrayList<String> Driventocity = new ArrayList<String>();
    private ArrayList<String> Driventoid = new ArrayList<String>();
    private ArrayList<String> Drivenfromcity = new ArrayList<String>();
    private ArrayList<String> Drivenfromid = new ArrayList<String>();

    String role = "";
    String str_title, str_location, str_startdate, str_enddate, str_links, str_tags,
            category_id = "", topic_id = "", subtopic_id = "";
    private String imgPath = null;
    int PERMISSION_ALL = 1;
    private InputStream inputStreamImg;
    public static int CAMERA1 = 101;
    Uri selectedImage;
    private File destination = null;
    Bitmap bitmap = null;
    String get_imagee1 = "", get_imagee2 = "", get_imagee3 = "", get_imagee4 = "", get_imagee5 = "", get_imagee6 = "", get_imagee7 = "";
    String uid = "", imagee = "", typee = "";
    String uid1 = "", imagee1 = "", typee1 = "";
    String uid2 = "", imagee2 = "", typee2 = "";
    String uid3 = "", imagee3 = "", typee3 = "";
    String uid4 = "", imagee4 = "", typee4 = "";
    String uid5 = "", imagee5 = "", typee5 = "";
    String uid6 = "", imagee6 = "", typee6 = "";
    String encodedImage;
    ArrayList<String> arrayList;
    ArrayList<SalaryModel> arrayList_salary;
    ArrayList<QualificationModel> arrayList_qulification;
    ArrayList<Datum> arrayList_licensetype;
    ArrayList<CasesondrivinglicModel> arrayList_casesondrivinglicense;

    private final int PICK_IMAGE_CAMERA = 3, PICK_IMAGE_GALLERY = 2;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,};
    ArrayList<CityModel> cityModelArrayList;
    ArrayList<String> cityNameList;
    ArrayList<StateModel> stateModels;
    ArrayList<LocalCityModel> localCityModels;
    ArrayList<DistrictModel> districtModels;
    ArrayList<LocalCityModel> drivenToModels;
    ArrayList<LocalCityModel> drivenfromModel;
    // ArrayList<Driven> districtModels = new ArrayList<>();
    String city;
    // ArrayList arrayList_licensetype;
    ArrayList arrayListcasesonDrvinglicense;
    DatePickerDialog picker;
    EditText et_doucument_adhar_pan;
    ArrayList arrayList_doucument_adhar_pan, arrayLists_Routes_Driven;
    Spinner spinner_doucument_adhar_pan, spinner_localcity;
    Button btn_submit;

    TextView tvSelectCity;
    LinearLayout docs_All_adhar, docs_All_pan;
    CheckBox checkbox_tata_ace, checkbox_tanker, checkbox_trailer, checkbox_mahindra, checkbox_407, checkbox_1109, checkbox_10tyre, checkbox_12tyre, checkbox_t14tyre, checkbox_32feetsxl, checkbox_32feetmxl;
    String Checkboxdata;
    TextView tv_adhar_pan, tv_pan;
    EditText etFname, etphone1;
    EditText etphone2, etTotalworkexp, etaddress, etstreet, etdlnumber, et_pan, et_adhar_pan, etdriving_license_no, et_ref_name, et_ref_address, et_ref_contactno, et_ref_relation;
    HashMap<String, String> hashMap;
    String id;
    String checkboxdisplay = "";
    String str_checkbox_tata_ace, str_checkbox_mahindra;
    ImageView img_pan_card, img_adhar_front, img_adhar_back;
    EditText etlistcity,et_adhar;
    ArrayList<String> itmsssss = new ArrayList<>();

    Spinner spinner_Routes_Driven;
    ArrayList<String> checkBoxArray;

    boolean spinnerItem;

TextView tv_insertdriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert__driver);
        //  li_allcheckbox=findViewById(R.id.li_allcheckbox);
        // onBackPressed();
        initiateIds();
        init();
       // tv_insertdriver=findViewById(R.id.tv_insertdriver);
        arrayList_licensetype = new ArrayList<>();
        checkBoxArray = new ArrayList<>();
        arrayList_casesondrivinglicense = new ArrayList<>();
        cityModelArrayList = new ArrayList<>();
        arrayList_salary = new ArrayList<>();
        arrayList_qulification = new ArrayList<>();
        cityNameList = new ArrayList<>();
        sessionManager = new SessionManager(Insert_Driver.this);
        if (sessionManager.isLoggedIn()) {
            hashMap = sessionManager.getUSerdetails();
            id = hashMap.get(SessionManager.userId);
        }


    }

    private void initiateIds() {

        dialog = KProgressHUD.create(Insert_Driver.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.3f);
        final Dialog dialog = new Dialog(Insert_Driver.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.setContentView(R.layout.custome_dialog_sponsership_pckg);
        dialog.getWindow().setDimAmount((float) 0.6);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        stateModels = new ArrayList<>();
        districtModels = new ArrayList<>();
        drivenToModels = new ArrayList<>();
        drivenfromModel = new ArrayList<>();
        localCityModels = new ArrayList<>();
        // ArrayList<DistrictModel> districtModels = new ArrayList<>();
        img_profile = findViewById(R.id.img_profile);
        tv_adhar_pan = findViewById(R.id.tv_adhar_pan);
        tv_pan = findViewById(R.id.tv_pan);
        etFname = findViewById(R.id.etFname);
        etphone1 = findViewById(R.id.etphone1);
        et_adhar = findViewById(R.id.et_adhar);
        etphone2 = findViewById(R.id.etphone2);
        tvSelectCity = findViewById(R.id.tvSelectCity);
        etTotalworkexp = findViewById(R.id.etTotalworkexp);
        li_spinner_routes_driven_to = findViewById(R.id.li_spinner_routes_driven_to);
        li_spinner_routes_driven_from = findViewById(R.id.li_spinner_routes_driven_from);
        li_spinner_localcity = findViewById(R.id.li_spinner_localcity);
        etaddress = findViewById(R.id.etaddress);
        // etlistcity = findViewById(R.id.etlistcity);
        etstreet = findViewById(R.id.etstreet);
        etdlnumber = findViewById(R.id.etdlnumber);
        et_adhar_pan = findViewById(R.id.et_adhar);
        et_pan = findViewById(R.id.et_pan);
        etdriving_license_no = findViewById(R.id.etdriving_license_no);
        et_ref_name = findViewById(R.id.et_ref_name);
        et_ref_address = findViewById(R.id.et_ref_address);
        et_ref_contactno = findViewById(R.id.et_ref_contactno);
        et_ref_relation = findViewById(R.id.et_ref_relation);
        docs_All_pan = findViewById(R.id.docs_All_pan);
        docs_All_adhar = findViewById(R.id.docs_All_adhar);
        btn_submit = findViewById(R.id.btn_submit);
        tv_license_expiry_date = findViewById(R.id.tv_license_expiry_date);
        spinner_personal_Education = findViewById(R.id.spinner_personal_Education);
        spinner_license_type = findViewById(R.id.spinner_license_type);
        spinner_Routes_Driven = findViewById(R.id.spinner_Routes_Driven);
        spinner_casesonDrvinglicense = findViewById(R.id.spinner_casesonDrvinglicense);
        spinner_personal_driven_from = findViewById(R.id.spinner_personal_driven_from);
        spinner_personal_District = findViewById(R.id.spinner_personal_District);
        spinner_personal_driven_to = findViewById(R.id.spinner_personal_driven_to);
        spinner_doucument_adhar_pan = findViewById(R.id.spinner_doucument_adhar_pan);
        spinner_personal_city = findViewById(R.id.spinner_personal_city);
        spinner_personal_statee = findViewById(R.id.spinner_personal_statee);
        spinner_localcity = findViewById(R.id.spinner_localcity);
        img_licenseimg = findViewById(R.id.img_licenseimg);
        img_pan_card = findViewById(R.id.img_pan_card);
        img_adhar_front = findViewById(R.id.img_adhar_front);
        img_adhar_back = findViewById(R.id.img_adhar_back);
        iv_insertdriver = findViewById(R.id.iv_insertdriver);
        img_ref_address_proof1 = findViewById(R.id.img_ref_address_proof1);
        img_ref_address_proof2 = findViewById(R.id.img_ref_address_proof2);
        spinner_salary = findViewById(R.id.spinner_salary);

        checkbox_tata_ace = findViewById(R.id.checkbox_tata_ace);
        checkbox_trailer = findViewById(R.id.checkbox_trailer);
        checkbox_mahindra = findViewById(R.id.checkbox_mahindra);
        checkbox_407 = findViewById(R.id.checkbox_407);
        checkbox_1109 = findViewById(R.id.checkbox_1109);
        checkbox_10tyre = findViewById(R.id.checkbox_10tyre);
        checkbox_12tyre = findViewById(R.id.checkbox_12tyre);
        checkbox_t14tyre = findViewById(R.id.checkbox_t14tyre);
        checkbox_32feetsxl = findViewById(R.id.checkbox_32feetsxl);
        checkbox_32feetmxl = findViewById(R.id.checkbox_32feetmxl);
        checkbox_tanker = findViewById(R.id.checkbox_tanker);

        checkbox_tata_ace.setOnCheckedChangeListener(this);
        checkbox_trailer.setOnCheckedChangeListener(this);
        checkbox_mahindra.setOnCheckedChangeListener(this);
        checkbox_407.setOnCheckedChangeListener(this);
        checkbox_1109.setOnCheckedChangeListener(this);
        checkbox_10tyre.setOnCheckedChangeListener(this);
        checkbox_12tyre.setOnCheckedChangeListener(this);
        checkbox_t14tyre.setOnCheckedChangeListener(this);
        checkbox_32feetsxl.setOnCheckedChangeListener(this);
        checkbox_32feetmxl.setOnCheckedChangeListener(this);
        checkbox_tanker.setOnCheckedChangeListener(this);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApiConstant.isConnected(getApplicationContext())) {
                    if (etFname.getText().toString().equals("")) {
                        etFname.setError("Please enter User Name");
                        Toast.makeText(Insert_Driver.this, "Please enter user name...", Toast.LENGTH_SHORT).show();
                    } else if (etphone1.getText().toString().length() != 10) {
                        etphone1.setError("Please enter phone Number");
                        Toast.makeText(Insert_Driver.this, "Please enter phone number...", Toast.LENGTH_SHORT).show();
                    } else if (spinner_personal_Education.getSelectedItem().toString().trim().equals("--select education qualification")) {
                        Toast.makeText(Insert_Driver.this, "Please enter education qualification...", Toast.LENGTH_SHORT).show();
                    } else if (etTotalworkexp.getText().toString().equals("")) {
                        etTotalworkexp.setError("Please enter Total work experiance");
                        Toast.makeText(Insert_Driver.this, "Please enter Total work experiance...", Toast.LENGTH_SHORT).show();
                    } else if (spinner_salary.getSelectedItem().toString().trim().equals("--Select Salary Range--")) {
                        Toast.makeText(Insert_Driver.this, "Please enter Salary Expectation...", Toast.LENGTH_SHORT).show();
                    } else if (etaddress.getText().toString().equals("")) {
                        etaddress.setError("Please enter Address");
                        Toast.makeText(Insert_Driver.this, "Please enter Address...", Toast.LENGTH_SHORT).show();
                    } else if (spinner_personal_city.getSelectedItem().toString().trim().equals("---Please Select City---")) {
                        Toast.makeText(Insert_Driver.this, "Please Select City...", Toast.LENGTH_SHORT).show();
                    } else if (spinner_personal_District.getSelectedItem().toString().trim().equals("---Please Select District---")) {
                        Toast.makeText(Insert_Driver.this, "Please Select district...", Toast.LENGTH_SHORT).show();
                    } else if (spinner_personal_statee.getSelectedItem().toString().trim().equals("---Please Select State---")) {
                        Toast.makeText(Insert_Driver.this, "Please Select state...", Toast.LENGTH_SHORT).show();
                    }
                    else if (checkBoxArray.size() == 0) {
                        Toast.makeText(Insert_Driver.this, "Please select atleast one checkbox", Toast.LENGTH_SHORT).show();
                    }

                    else if (spinner_Routes_Driven.getSelectedItem().toString().trim().equals("--Please Select driven Route--")) {
                        Toast.makeText(Insert_Driver.this, "--Please Select driven Route--", Toast.LENGTH_SHORT).show();
                    }
                    else if (spinner_Routes_Driven.getSelectedItem().toString().trim().equals("Local")
                            && spinner_localcity.getSelectedItem().equals("---Please Select City---")) {
                        Toast.makeText(Insert_Driver.this, "Please Select Local City", Toast.LENGTH_SHORT).show();
                    }

                    else if (spinner_Routes_Driven.getSelectedItem().toString().trim().equals("Outstation")
                            && spinner_personal_driven_from.getSelectedItem().equals("---Please Select City---")) {
                        Toast.makeText(Insert_Driver.this, "Please Select driven from city", Toast.LENGTH_SHORT).show();
                    } else if (spinner_Routes_Driven.getSelectedItem().toString().trim().equals("Outstation")
                            && spinner_personal_driven_to.getSelectedItem().equals("---Please Select City---")) {
                        Toast.makeText(Insert_Driver.this, "Please Select driven to city", Toast.LENGTH_SHORT).show();
                    }
                    else if (imagee1.equals("")) {
                        Toast.makeText(Insert_Driver.this, "Please upload License image...", Toast.LENGTH_SHORT).show();
                    }
                    else if (etdriving_license_no.getText().toString().equals("")) {
                        etdriving_license_no.setError("Please enter Driving license no");
                        Toast.makeText(Insert_Driver.this, "Please enter Driving license no...", Toast.LENGTH_SHORT).show();
                    } else if (tv_license_expiry_date.getText().toString().equals("")) {
                        Toast.makeText(Insert_Driver.this, "Please enter license expiry date.....", Toast.LENGTH_SHORT).show();
                    } else if (spinner_license_type.getSelectedItem().toString().trim().equals("---select license type---")) {
                        Toast.makeText(Insert_Driver.this, "Please enter Driving license type...", Toast.LENGTH_SHORT).show();
                    } else if (spinner_casesonDrvinglicense.getSelectedItem().toString().trim().equals("---select cases on license---")) {
                        Toast.makeText(Insert_Driver.this, "Please select cases on license...", Toast.LENGTH_SHORT).show();
                    }
                    else if (spinner_doucument_adhar_pan.getSelectedItem().toString().trim().equals("--Please Select Document--")){
                        Toast.makeText(Insert_Driver.this, "Please select document...", Toast.LENGTH_SHORT).show();
                    }
                    else if (spinner_doucument_adhar_pan.getSelectedItem().toString().trim().equals("Adhar Card") && imagee3.equals("") ){

                        Toast.makeText(Insert_Driver.this, "Please Upload adhar Card image...", Toast.LENGTH_SHORT).show();
                    }


                    else if (spinner_doucument_adhar_pan.getSelectedItem().toString().trim().equals("Adhar Card") && imagee4.equals("") ){

                        Toast.makeText(Insert_Driver.this, "Please Upload adhar Card image...", Toast.LENGTH_SHORT).show();
                    }

                    else if (spinner_doucument_adhar_pan.getSelectedItem().toString().trim().equals("Adhar Card") && et_adhar.getText().toString().length()<2){

                        Toast.makeText(Insert_Driver.this, "Please enter Adhar Card Number...", Toast.LENGTH_SHORT).show();
                    }
                    else if (spinner_doucument_adhar_pan.getSelectedItem().toString().trim().equals("Pan Card") && imagee2.equals("")){

                        Toast.makeText(Insert_Driver.this, "Please Upload Pan Card image...", Toast.LENGTH_SHORT).show();
                    }else if (spinner_doucument_adhar_pan.getSelectedItem().toString().trim().equals("Pan Card") && et_pan.getText().toString().length()<2){

                        Toast.makeText(Insert_Driver.this, "Please enter Pan Card Number..", Toast.LENGTH_SHORT).show();
                    }
                    else if (et_ref_name.getText().toString().equals("")) {
                        et_ref_name.setError("Please enter reference name");
                        Toast.makeText(Insert_Driver.this, "Please enter reference name...", Toast.LENGTH_SHORT).show();
                    } else if (et_ref_contactno.getText().toString().length() != 10) {
                        et_ref_contactno.setError("Please enter Reference Contact No");
                        Toast.makeText(Insert_Driver.this, "Please enter Contact No...", Toast.LENGTH_SHORT).show();
                    } else if (et_ref_relation.getText().toString().equals("")) {
                        et_ref_relation.setError("Please enter Reference relation");
                        Toast.makeText(Insert_Driver.this, "Please enter Reference relation...", Toast.LENGTH_SHORT).show();
                    } else if (imagee.equals("")) {

                        Toast.makeText(Insert_Driver.this, "Please upload profile image...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                      //  getselectedcheckbox();
                        getinsertdriverapi(dialog);

                    }

                } else {
                    Toast.makeText(Insert_Driver.this, "Please Check internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etdriving_license_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String etdata = charSequence.toString();
                //2 mis
                //  boolean isValid = true;
                if (i2 <= 8) {


                } else {

                }

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000; // milliseconds

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("outer", editable.toString());
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions (e.g. manipulating views)
                                String data = editable.toString();
                                Log.d("inner", data);

                              /* runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       getlicincevalidity();
                                   }
                               });*/
                                getlicincevalidity();
                            }
                        },
                        DELAY
                );


            }
        });
    }

    private void getselectedcheckbox() {
        if (checkbox_tata_ace.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_tata_ace.getText() + ",";
        }
        if (checkbox_mahindra.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_mahindra.getText() + ",";
        }
        if (checkbox_407.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_407.getText() + ",";
        }
        if (checkbox_1109.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_1109.getText() + ",";
        }
        if (checkbox_10tyre.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_10tyre.getText() + ",";
        }
        if (checkbox_12tyre.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_12tyre.getText() + ",";
        }
        if (checkbox_t14tyre.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_t14tyre.getText() + ",";
        }
        if (checkbox_32feetsxl.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_32feetsxl.getText() + ",";
        }
        if (checkbox_32feetmxl.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_32feetmxl.getText() + ",";
        }
        if (checkbox_trailer.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_trailer.getText() + ",";
        }
        if (checkbox_tanker.isChecked()) {
            checkboxdisplay = checkboxdisplay + checkbox_trailer.getText() + ",";
        }
    }

    private void init() {

        getCityList();
        new asynkdistrict().execute();

        getLocalCityList();
        getStateList();
        //getDrivento();
        getsalary();
        getqulification();
        getlicense_type();
        getcasesondrivinglicese();




        //  getDrivenfrom();
        // setspinnerDrivenfrom();

        /*getDistrict();
        setSpinnerDriver();*/

        // new asynkgetdrivenfrom().execute();
//new asynkdrivenfrom().e
        /*getDrivenfrom();
        setspinnerDrivenfrom();*/




        arrayLists_Routes_Driven = new ArrayList();
        arrayLists_Routes_Driven.add("--Please Select driven Route--");
        arrayLists_Routes_Driven.add("Local");
        arrayLists_Routes_Driven.add("Outstation");
        setRole(arrayLists_Routes_Driven, spinner_Routes_Driven);

        arrayList_doucument_adhar_pan = new ArrayList();
        arrayList_doucument_adhar_pan.add("--Please Select Document--");
        arrayList_doucument_adhar_pan.add("Adhar Card");
        arrayList_doucument_adhar_pan.add("Pan Card");
        setRole(arrayList_doucument_adhar_pan, spinner_doucument_adhar_pan);


       /* arrayList = new ArrayList();
        arrayList.add("--select education qualification");
        arrayList.add("10th");
        arrayList.add("12th");
        arrayList.add("Graduation");
        setRole(arrayList, spinner_personal_Education);*/


      /*  arrayList_salary = new ArrayList<>();
        arrayList_salary.add("--select Salary Range--");
        arrayList_salary.add("15k to 20k");
        arrayList_salary.add("20k to 25k");
        arrayList_salary.add("25k to 30k");
        arrayList_salary.add("30k to 35k");
        arrayList_salary.add("35k to 40k");
        arrayList_salary.add("40k to 45k");
        arrayList_salary.add("45k to 50k");
        setRole(arrayList_salary, spinner_salary);*/

/*
        arrayList_licensetype = new ArrayList();
        arrayList_licensetype.add("---select license type---");
        arrayList_licensetype.add("Light Commercial Vehicle");
        arrayList_licensetype.add("Heavy motor Vehicle");
        arrayList_licensetype.add("Trailer license");
        setRole(arrayList_licensetype, spinner_license_type);*/

/*        arrayListcasesonDrvinglicense = new ArrayList();
        arrayListcasesonDrvinglicense.add("---select cases on license---");
        arrayListcasesonDrvinglicense.add("0");
        arrayListcasesonDrvinglicense.add("1");
        arrayListcasesonDrvinglicense.add("2");
        arrayListcasesonDrvinglicense.add("3");
        arrayListcasesonDrvinglicense.add("4");
        setRole(arrayListcasesonDrvinglicense, spinner_casesonDrvinglicense);*/


        iv_insertdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Insert_Driver.this, MainActivity.class);
                startActivity(intent);
            }
        });


        tv_license_expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Insert_Driver.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_license_expiry_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                               /* tv_license_expiry_date.setText(new StringBuilder().append(month)
                                        .append("-").append(day).append("-").append(year)
                                        .append(" "));*/
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                picker.setTitle(getResources().getString(
                        R.string.alert_date_select));
                picker.show();
                tv_license_expiry_date.setTextColor(Color.DKGRAY);


                /*final Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(
                        Insert_Driver.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker,
                                          int selectedyear, int selectedmonth,
                                          int selectedday) {

                        mcurrentDate.set(Calendar.YEAR, selectedyear);
                        mcurrentDate.set(Calendar.MONTH, selectedmonth);
                        mcurrentDate.set(Calendar.DAY_OF_MONTH,
                                selectedday);
*//*                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf;
                        sdf = new SimpleDateFormat(mDay - mMonth - mYear);*//*

                     //   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd").parse();
                        tv_license_expiry_date.setText(new StringBuilder().append(mMonth)
                                .append("-").append(mday).append("-").append(mYear)
                                .append(" "));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.setTitle(getResources().getString(
                        R.string.alert_date_select));
                mDatePicker.show();*/
            }
        });




        img_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(Insert_Driver.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Insert_Driver.this, new String[]{Manifest.permission.CAMERA}, CAMERA1);
                } else {
                    var = 1;

                    checkPermission1();


                }
            }
        });


        img_licenseimg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Insert_Driver.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Insert_Driver.this, new String[]{Manifest.permission.CAMERA}, CAMERA1);
                } else {
                    var = 2;

                    checkPermission1();


                }
            }
        });

        img_pan_card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Insert_Driver.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Insert_Driver.this, new String[]{Manifest.permission.CAMERA}, CAMERA1);
                } else {
                    var = 3;

                    checkPermission1();

                }
            }
        });

        img_adhar_front.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Insert_Driver.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Insert_Driver.this, new String[]{Manifest.permission.CAMERA}, CAMERA1);
                } else {
                    var = 4;

                    checkPermission1();

                }
            }
        });


        img_adhar_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Insert_Driver.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Insert_Driver.this, new String[]{Manifest.permission.CAMERA}, CAMERA1);
                } else {
                    var = 5;

                    checkPermission1();
                }

            }
        });


        img_ref_address_proof1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Insert_Driver.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Insert_Driver.this, new String[]{Manifest.permission.CAMERA}, CAMERA1);
                } else {
                    var = 6;

                    checkPermission1();

                }
            }
        });


        img_ref_address_proof2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Insert_Driver.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Insert_Driver.this, new String[]{Manifest.permission.CAMERA}, CAMERA1);
                } else {
                    var = 7;

                    checkPermission1();

                }
            }
        });

    }




    @SuppressLint("NewApi")
    private void checkPermission1() {

        if (ContextCompat.checkSelfPermission(Insert_Driver.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(Insert_Driver.this,
                Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(Insert_Driver.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (Insert_Driver.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (Insert_Driver.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale
                    (Insert_Driver.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(Insert_Driver.this.findViewById(android.R.id.content),
                        "Please Grant Permissions for read external storage",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{Manifest.permission
                                                .READ_EXTERNAL_STORAGE, Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSIONS_MULTIPLE_REQUEST);
                            }
                        }).show();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            if (var == 1) {
                // img_profile.setImageDrawable(getResources().getDrawable(R.drawable.user_profile));
                selectImage();
            } else if (var == 2) {
                //   img_licenseimg.setImageDrawable(getResources().getDrawable(R.drawable.imgaadhar));
                selectImage();
            } else if (var == 3) {
                //  img_pan_card.setImageDrawable(getResources().getDrawable(R.drawable.imgaadhar));
                selectImage();
            } else if (var == 4) {
                //  img_ref_address_proof1.setImageDrawable(getResources().getDrawable(R.drawable.imgaddressproof));
                selectImage();
            } else if (var == 5) {
                //  img_ref_address_proof1.setImageDrawable(getResources().getDrawable(R.drawable.imgaddressproof));
                selectImage();
            } else if (var == 6) {
                //   img_ref_address_proof1.setImageDrawable(getResources().getDrawable(R.drawable.imgaddressproof));
                selectImage();
            } else if (var == 7) {
                //  img_ref_address_proof1.setImageDrawable(getResources().getDrawable(R.drawable.imgaddressproof));
                selectImage();
            }
        }
    }

    private void selectImage() {
        try {
            PackageManager pm = Insert_Driver.this.getPackageManager();

            if (1 == 1) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Insert_Driver.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        }
                    }
                });
                builder.show();
            } else
                ActivityCompat.requestPermissions(Insert_Driver.this, PERMISSIONS, PERMISSION_ALL);
        } catch (Exception e) {
            Toast.makeText(Insert_Driver.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            if (data == null) {
            } else {
                selectedImage = data.getData();
                try {

                    Uri selectedImage = data.getData();
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());


                    destination = new File(Environment.getExternalStorageDirectory() + "/" +
                            getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                    FileOutputStream fo;
                    File storageDir = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES);


                    File image = File.createTempFile(
                            timeStamp,  // prefix
                            ".jpg",         // suffix
                            storageDir);
                    imgPath = "file:" + image.getAbsolutePath();

                    try {

                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imgPath = destination.getAbsolutePath();
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imgPath = destination.getAbsolutePath();
                    if (var == 1) {
                        img_profile.setImageBitmap(bitmap);
                        byte[] imageBytes = bytes.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee1 = encodedImage;
                        imagee = get_imagee1;
                        typee = "driver1";
                    } else if (var == 2) {
                        img_licenseimg.setImageBitmap(bitmap);
                        byte[] imageBytes = bytes.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee2 = encodedImage;
                        imagee1 = get_imagee2;
                        typee = "driver2";
                    } else if (var == 3) {
                        img_pan_card.setImageBitmap(bitmap);
                        byte[] imageBytes = bytes.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee3 = encodedImage;
                        imagee2 = get_imagee3;
                        typee = "driver3";
                    } else if (var == 4) {
                        img_adhar_front.setImageBitmap(bitmap);
                        byte[] imageBytes = bytes.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee4 = encodedImage;
                        imagee3 = get_imagee4;
                        typee = "driver4";
                    } else if (var == 5) {
                        img_adhar_back.setImageBitmap(bitmap);
                        byte[] imageBytes = bytes.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee5 = encodedImage;
                        imagee4 = get_imagee5;
                        typee = "driver4";
                    } else if (var == 6) {
                        img_ref_address_proof1.setImageBitmap(bitmap);
                        byte[] imageBytes = bytes.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee6 = encodedImage;
                        imagee5 = get_imagee6;
                        typee = "driver4";
                    } else if (var == 7) {
                        img_ref_address_proof2.setImageBitmap(bitmap);
                        byte[] imageBytes = bytes.toByteArray();
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee7 = encodedImage;
                        imagee6 = get_imagee7;
                        typee = "driver4";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == PICK_IMAGE_GALLERY) {

            if (data == null) {

            } else {
                selectedImage = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(Insert_Driver.this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    imgPath = getRealPathFromURI(selectedImage);
                    destination = new File(imgPath.toString());

                    byte[] imageBytes = bytes.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    imgPath = destination.getAbsolutePath();
                    if (var == 1) {
                        img_profile.setImageBitmap(bitmap);
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee1 = encodedImage;
                        imagee = get_imagee1;
                        typee = "driver";
                    } else if (var == 2) {
                        img_licenseimg.setImageBitmap(bitmap);
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee2 = encodedImage;
                        imagee1 = get_imagee2;
                        typee = "driver";
                    } else if (var == 3) {
                        img_pan_card.setImageBitmap(bitmap);
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee3 = encodedImage;
                        imagee2 = get_imagee3;
                        typee = "driver";
                    } else if (var == 4) {
                        img_adhar_front.setImageBitmap(bitmap);
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee4 = encodedImage;
                        imagee3 = get_imagee4;
                        typee = "driver";
                    } else if (var == 5) {
                        img_adhar_back.setImageBitmap(bitmap);
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee5 = encodedImage;
                        imagee4 = get_imagee5;
                        typee = "driver";
                    } else if (var == 6) {
                        img_ref_address_proof1.setImageBitmap(bitmap);
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee6 = encodedImage;
                        imagee5 = get_imagee6;
                        typee = "driver";
                    } else if (var == 7) {
                        img_ref_address_proof2.setImageBitmap(bitmap);
                        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        get_imagee7 = encodedImage;
                        imagee6 = get_imagee7;
                        typee = "driver";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = Insert_Driver.this.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        role = arrayList.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getCityList() {
//        dialog.show();
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.CITY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    new Thread(() -> {
                       // localCityModels = getJsonLocalcity(response);
                        cityModelArrayList = getJson(response);

                        Log.d("city_list", String.valueOf(cityNameList.size()));

                        cityId.clear();
                        cityName.clear();
                        for (int i = 0; i < cityModelArrayList.size(); i++) {
                            cityId.add(cityModelArrayList.get(i).getId());
                            cityName.add(cityModelArrayList.get(i).getCity());
                        }

                        cityId.add(0, "0");
                        cityName.add(0, "---Please Select City---");

                        runOnUiThread(() -> {
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, cityName);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_personal_city.setAdapter(spinnerArrayAdapter);
                            spinner_personal_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // sent_amount_Id = amountIdList.get(position);
                                   /* String selectedCityName = cityName.get(position);
                                    String selectedCityId = cityId.get(position);*/
                                    //  spinner_personal_city.setPositiveButton("OK");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });

                            // setCity(cityModelArrayList, spinner_personal_city);
                            dismissDialog();
                        });
                    }).start();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialog();
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(120 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            dismissDialog();
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void getsalary() {

        dialog.show();
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.salary_expectation, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //cityModelArrayList = getJson(response);
                    new Thread(() -> {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            boolean error = jsonObject1.getBoolean("error");
                            if (error == false) {
                                String message = jsonObject1.getString("message");
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                                    String id = jsonObject11.getString("id");
                                    String salary_expectation = jsonObject11.getString("salary_expectation");
                                    SalaryModel salaryModel = new SalaryModel(id, salary_expectation);
                                    salaryModel.setId(id);
                                    salaryModel.setId(salary_expectation);
                                    arrayList_salary.add(salaryModel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //  Log.d("city_list", String.valueOf(cityNameList.size()));
                        // print kar na  cityNameList.size()

                        salaryId.clear();
                        salaryName.clear();

                        for (int i = 0; i < arrayList_salary.size(); i++) {

                            salaryId.add(arrayList_salary.get(i).getId());
                            salaryName.add(arrayList_salary.get(i).getSalary_expectation().toString());


                        }

                        salaryId.add(0, "0");
                        salaryName.add(0, "--Select Salary Range--");
                        runOnUiThread(() -> {
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, salaryName);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_salary.setAdapter(spinnerArrayAdapter);
                            spinner_salary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // sent_amount_Id = amountIdList.get(position);

                                  /*  String selectedsalName = salaryName.get(position);
                                    String selectedsalId = salaryId.get(position);*/
                                    //  spinner_personal_city.setPositiveButton("OK");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }
                            });

                            // setCity(cityModelArrayList, spinner_personal_city);
                            dialog.dismiss();
                        });
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(120 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    public void getqulification() {

        dialog.show();
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.qualification, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //cityModelArrayList = getJson(response);
                    new Thread(() -> {


                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            boolean error = jsonObject1.getBoolean("error");
                            if (error == false) {
                                String message = jsonObject1.getString("message");
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                                    String id = jsonObject11.getString("id");
                                    String Qualification = jsonObject11.getString("Qualification");
                                    QualificationModel qualificationModel = new QualificationModel(id, Qualification);
                                    qualificationModel.setId(id);
                                    qualificationModel.setId(Qualification);
                                    arrayList_qulification.add(qualificationModel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //  Log.d("city_list", String.valueOf(cityNameList.size()));
                        // print kar na  cityNameList.size()

                        qulificationId.clear();
                        qulificationName.clear();

                        for (int i = 0; i < arrayList_qulification.size(); i++) {

                            qulificationId.add(arrayList_qulification.get(i).getId().toString());
                            qulificationName.add(arrayList_qulification.get(i).getQualification().toString());


                        }

                        qulificationId.add(0, "0");
                        qulificationName.add(0, "--select education qualification");
                        runOnUiThread(() -> {
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, qulificationName);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_personal_Education.setAdapter(spinnerArrayAdapter);
                            spinner_personal_Education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // sent_amount_Id = amountIdList.get(position);

                                   /* String selectedsalNamee = qulificationName.get(position);
                                    String selectedsalIdd = qulificationId.get(position);*/
                                    //  spinner_personal_city.setPositiveButton("OK");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }
                            });

                            // setCity(cityModelArrayList, spinner_personal_city);
                            dismissDialog();

                        });
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialog();
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(120 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            dismissDialog();
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    public void getlicense_type() {

        dialog.show();
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.license_type, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //cityModelArrayList = getJson(response);
                    new Thread(() -> {
                        try {


                            LicensetypeModel ob = new Gson().fromJson(response, LicensetypeModel.class);
                            Log.e("Response", response);
                            if (!ob.getError()) {


                                arrayList_licensetype = new ArrayList<>();
                                arrayList_licensetype = ob.getData();
                            }
//                        JSONObject jsonObject1 = new JSONObject(response);
//                        boolean error = jsonObject1.getBoolean("error");
//                        if (error == false) {
//                            String message = jsonObject1.getString("message");
//                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
//                            for (int i = 0; i <= jsonArray.length(); i++) {
//
//                                JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//                                String id = jsonObject11.getString("id");
//                                String type = jsonObject11.getString("type");
//                                LicensetypeModel licensetypeModel = new LicensetypeModel(id, type);
//                                licensetypeModel.setId(id);
//                                licensetypeModel.setId(type);
//                                arrayList_licensetype.add(licensetypeModel);
//                            }
//                        }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        //  Log.d("city_list", String.valueOf(cityNameList.size()));
                        // print kar na  cityNameList.size()

                        license_typeId.clear();
                        license_typeName.clear();

                        for (int i = 0; i < arrayList_licensetype.size(); i++) {

                            license_typeId.add("" + arrayList_licensetype.get(i).getId());
                            license_typeName.add("" + arrayList_licensetype.get(i).getType());


                        }

                        license_typeId.add(0, "0");
                        license_typeName.add(0, "---select license type---");
                        runOnUiThread(() -> {
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, license_typeName);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_license_type.setAdapter(spinnerArrayAdapter);
                            spinner_license_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // sent_amount_Id = amountIdList.get(position);

                                   /* String selectedsalNameea = license_typeName.get(position);
                                    String selectedsalIdda = license_typeId.get(position);*/
                                    //  spinner_personal_city.setPositiveButton("OK");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }
                            });

                            // setCity(cityModelArrayList, spinner_personal_city);
                            dismissDialog();


                        });
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialog();
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(120 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            dismissDialog();
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    public void getcasesondrivinglicese() {

        dialog.show();
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.cases_on_driving_license, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //cityModelArrayList = getJson(response);
                    new Thread(() -> {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            boolean error = jsonObject1.getBoolean("error");
                            if (error == false) {
                                String message = jsonObject1.getString("message");
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                                    String id = jsonObject11.getString("id");
                                    String Cases_On_Driving_License = jsonObject11.getString("Cases_On_Driving_License");
                                    CasesondrivinglicModel casesondrivinglicModel = new CasesondrivinglicModel(id, Cases_On_Driving_License);
                                    casesondrivinglicModel.setId(id);
                                    casesondrivinglicModel.setCases_On_Driving_License(Cases_On_Driving_License);
                                    arrayList_casesondrivinglicense.add(casesondrivinglicModel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //  Log.d("city_list", String.valueOf(cityNameList.size()));
                        // print kar na  cityNameList.size()

                        cases_on_driving_licenseId.clear();
                        cases_on_driving_licenseName.clear();

                        for (int i = 0; i < arrayList_casesondrivinglicense.size(); i++) {

                            cases_on_driving_licenseId.add(arrayList_casesondrivinglicense.get(i).getId().toString());
                            cases_on_driving_licenseName.add(arrayList_casesondrivinglicense.get(i).getCases_On_Driving_License().toString());


                        }

                        cases_on_driving_licenseId.add(0, "0");
                        cases_on_driving_licenseName.add(0, "---select cases on license---");
                        runOnUiThread(() -> {
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, cases_on_driving_licenseName);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_casesonDrvinglicense.setAdapter(spinnerArrayAdapter);
                            spinner_casesonDrvinglicense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // sent_amount_Id = amountIdList.get(position);

                                    /*String selectedsalNameeas = cases_on_driving_licenseName.get(position);
                                    String selectedsalIdsda = cases_on_driving_licenseId.get(position);*/
                                    //  spinner_personal_city.setPositiveButton("OK");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }
                            });

                            // setCity(cityModelArrayList, spinner_personal_city);
                            dismissDialog();


                        });
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialog();
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(120 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            dismissDialog();
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    ArrayList<StateModel> getJsonState(String response) {
        ArrayList<StateModel> array = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean error = jsonObject.getBoolean("error");
            String message = jsonObject.getString("message");
            if (error == false) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id");
                    String state = jsonObject1.getString("state");
                    StateModel stateModel = new StateModel(id, state);
                    stateModel.setId(id);
                    stateModel.setState(state);
                    array.add(stateModel);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    ArrayList<LocalCityModel> getJsonLocalcity(String response) {
        ArrayList<LocalCityModel> array = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(response);
            boolean error = jsonObject.getBoolean("error");
            String message = jsonObject.getString("message");
            if (error == false) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id");
                    String city = jsonObject1.getString("city");
                    String state_id = jsonObject1.getString("state_id");
                    LocalCityModel localCityModell = new LocalCityModel(id, city, state_id);
                    localCityModell.setId(id);
                    localCityModell.setCity(city);
                    localCityModell.setState_id(state_id);

                    array.add(localCityModell);
                    localcityId.add(id);
                    localcityName.add(city);
                }
                Log.e("dataresponse", "" + new Gson().toJson(localCityModels));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONException", "getJsonLocalcity: " + e.toString());
        }
        return array;
    }

    public void getStateList() {
        dialog.show();
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.STATE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new Thread(() -> {

                        stateModels = getJsonState(response);
                        stateId.clear();
                        stateName.clear();

                        for (int i = 0; i < stateModels.size(); i++) {

                            stateId.add(stateModels.get(i).getId().toString());
                            stateName.add(stateModels.get(i).getState().toString());


                        }

                        stateId.add(0, "0");
                        stateName.add(0, "---Please Select State---");
                        runOnUiThread(() -> {
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, stateName);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_personal_statee.setAdapter(spinnerArrayAdapter);
                            spinner_personal_statee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // sent_amount_Id = amountIdList.get(position);

                                    /*String selectedstateName = stateName.get(position);
                                    String selectedstateId = stateId.get(position);*/
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }
                            });

                            // setCity(cityModelArrayList, spinner_personal_city);

                            dialog.dismiss();
                        });
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(120 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }

    }


    public void getLocalCityList() {
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.LOcal_City, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new Thread(() -> {
                        localCityModels = getJsonLocalcity(response);

                        //localcityId.clear();
                        //localcityName.clear();
                 /*   for (int i = 0; i < localCityModels.size(); i++) {
                        localcityId.add(localCityModels.get(i).getId());
                        localcityName.add(localCityModels.get(i).getCity());
                    }*/

                        runOnUiThread(() -> {


                            localcityId.add(0, "0");
                            localcityName.add(0, "---Please Select City---");
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, localcityName);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_localcity.setAdapter(spinnerArrayAdapter);
                            spinner_localcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // sent_amount_Id = amountIdList.get(position);

                                   /* String slectlocalcityname = localcityName.get(position);
                                    String slectlocalcityid = localcityId.get(position);*/
                                    spinnerItem = true;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    //   Toast.makeText(Insert_Driver.this, "Please select city", Toast.LENGTH_SHORT).show();
                                    spinnerItem = false;
                                }

                            });
                            final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, localcityName);
                            spinnerArrayAdapter1.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_personal_driven_to.setAdapter(spinnerArrayAdapter);
                            spinner_personal_driven_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                    // sent_amount_Id = amountIdList.get(position);

                                   /* String slectlocalcityname = localcityName.get(position);
                                    String slectlocalcityid = localcityId.get(position);*/
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

//                                    Toast.makeText(Insert_Driver.this, "Please select driven to city", Toast.LENGTH_SHORT).show();
                                }

                            });


                            final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, localcityName);
                            spinnerArrayAdapter2.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_personal_driven_from.setAdapter(spinnerArrayAdapter);
                            spinner_personal_driven_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // sent_amount_Id = amountIdList.get(position);

                                   /* String slectlocalcityname = localcityName.get(position);
                                    String slectlocalcityid = localcityId.get(position);*/
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

//                                    Toast.makeText(Insert_Driver.this, "Please select driven from city", Toast.LENGTH_SHORT).show();
                                }

                            });


                        });
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();


                    Log.e("error", error.toString());
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder al = new AlertDialog.Builder(Insert_Driver.this);
                    String mesaage = null;
                    if (error instanceof NetworkError) {
                        mesaage = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof ServerError) {
                        mesaage = "The server could not be found. Please try again after some time!!";
                    } else if (error instanceof AuthFailureError) {
                        mesaage = "Cannot connect to Internet...Authfailure!";
                    } else if (error instanceof NoConnectionError) {
                        mesaage = "Cannot connect to Internet...NoConnectionError!";
                    } else if (error instanceof TimeoutError) {
                        mesaage = "Connection TimeOut! TimeoutError.";
                    }
                    al.setTitle("Service Response....");
                    al.setMessage(mesaage);
                    al.show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(180 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void getDistrict() {
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.LOcal_City, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new Thread(() -> {
                        localCityModels = getJsonLocalcity(response);
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new

                    DefaultRetryPolicy(150 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Please Check internet Connection...", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinnerDriver() {


        runOnUiThread(() -> {
            DistrictId.add(0, "0");
            DistrictName.add(0, "---Please Select District---");
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, DistrictName);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
            spinner_personal_District.setAdapter(spinnerArrayAdapter);
            spinner_personal_District.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // sent_amount_Id = amountIdList.get(position);
/*
                                                String districtnameee = DistrictName.get(position);
                                                String districtidd = DistrictId.get(position);*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });

        });
    }
    /*public void setspinnerDrivenfrom(){
        runOnUiThread(() -> {
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, Driventocity);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
            spinner_personal_driven_from.setAdapter(spinnerArrayAdapter);
            spinner_personal_driven_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // sent_amount_Id = amountIdList.get(position);

                                               *//* String selectedstateName = Driventocity.get(position);
                                                String selectedstateId = Driventoid.get(position);*//*
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
        });

    }*/

    public void getDrivento() {
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.LOcal_City, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new Thread(() -> {
                        localCityModels = getJsonLocalcity(response);

                        //Driventoid.add(0, "0");
                        //Driventocity.add(0, "");
                        runOnUiThread(() -> {
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, Driventocity);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
                            spinner_personal_driven_to.setAdapter(spinnerArrayAdapter);
                            spinner_personal_driven_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    // sent_amount_Id = amountIdList.get(position);

                                                /*String selectedstateName = Driventocity.get(position);
                                                String selectedstateId = Driventoid.get(position);*/
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }

                            });


                        });
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(120 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Please Check internet Connection...", Toast.LENGTH_SHORT).show();
        }
    }

    public void getDrivenfrom() {
        if (ApiConstant.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstant.DRIVEN_TO, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new Thread(() -> {
                        localCityModels = getJsonLocalcity(response);

                        Driventoid.clear();
                        Driventocity.clear();
                        for (int i = 0; i < drivenToModels.size(); i++) {
                            Driventoid.add(drivenToModels.get(i).getId().toString());
                            Driventocity.add(drivenToModels.get(i).getCity().toString());
                        }


                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(120 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Please Check internet Connection...", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<CityModel> getJson(String response) {
        ArrayList<CityModel> arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean error = jsonObject.getBoolean("error");
            String message = jsonObject.getString("message");
            if (error == false) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id");
                    String city = jsonObject1.getString("city");
                    String state_id = jsonObject1.getString("state_id");
                    CityModel cityModel = new CityModel(id, city, state_id);
                    cityModel.setId(id);
                    cityModel.setCity(city);
                    cityModel.setState_id(state_id);
                    arrayList.add(cityModel);
                    //cityNameList.add(city);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    public void setRole(final ArrayList<String> interestModelArrayList, Spinner spinner) {

        ArrayAdapter<String> modelArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.spinner_item, interestModelArrayList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    Context mContext = this.getContext();
                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.spinner_item, null);
                }
                TextView tv = (TextView) v.findViewById(R.id.txt_interest);
                tv.setText(interestModelArrayList.get(position));
                if (tv.getText().equals("Adhar Card")) {
                    docs_All_adhar.setVisibility(View.VISIBLE);
                    docs_All_pan.setVisibility(View.GONE);
                    tv_adhar_pan.setVisibility(View.VISIBLE);
                    tv_pan.setVisibility(View.GONE);
                    et_adhar_pan.setVisibility(View.VISIBLE);
                    et_pan.setVisibility(View.GONE);
                   /* if (img_adhar_front.equals("")) {
                        Toast.makeText(Insert_Driver.this, "Please upload adhar front image", Toast.LENGTH_SHORT).show();
                    } else if (img_adhar_back.equals("")) {
                        Toast.makeText(Insert_Driver.this, "Please upload adhar back image", Toast.LENGTH_SHORT).show();

                    }*/


                } else if (tv.getText().equals("Pan Card")) {
                    docs_All_adhar.setVisibility(View.GONE);
                    docs_All_pan.setVisibility(View.VISIBLE);
                    tv_adhar_pan.setVisibility(View.GONE);
                    tv_pan.setVisibility(View.VISIBLE);
                    et_pan.setVisibility(View.VISIBLE);
                    et_adhar_pan.setVisibility(View.GONE);
                    /*if (img_pan_card.equals("")) {
                        Toast.makeText(Insert_Driver.this, "Please upload pan image", Toast.LENGTH_SHORT).show();
                    }*/

                } else if (tv.getText().equals("Local")) {

                    li_spinner_localcity.setVisibility(View.VISIBLE);
                    li_spinner_routes_driven_from.setVisibility(View.GONE);
                    li_spinner_routes_driven_to.setVisibility(View.GONE);

                   /* if (spinner_localcity.getSelectedItem().toString().trim().equals("---Please Select City---")) {
                        Toast.makeText(Insert_Driver.this, "Please select local city", Toast.LENGTH_SHORT).show();
                    } else {

                    }*/

                } else if (tv.getText().equals("Outstation")) {
                    li_spinner_localcity.setVisibility(View.GONE);
                    li_spinner_routes_driven_from.setVisibility(View.VISIBLE);
                    li_spinner_routes_driven_to.setVisibility(View.VISIBLE);
                    /*if (spinner_personal_driven_from.getSelectedItem().toString().trim().equals("")) {
                        Toast.makeText(Insert_Driver.this, "Please select city", Toast.LENGTH_SHORT).show();
                    } else if (spinner_personal_driven_to.getSelectedItem().toString().trim().equals("")) {
                        Toast.makeText(Insert_Driver.this, "Please select city", Toast.LENGTH_SHORT).show();
                    } else {

                    }*/
                }

                switch (position) {
                    case 0:
                        tv.setTextColor(Color.GRAY);
                        break;
                    default:
                        tv.setTextColor(Color.GRAY);
                        break;
                }
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    Context mContext = this.getContext();
                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.spinner_item, null);
                }

                TextView tv = (TextView) v.findViewById(R.id.txt_interest);

                tv.setText(interestModelArrayList.get(position));

                switch (position) {
                    case 0:
                        tv.setTextColor(Color.GRAY);
                        break;
                    default:
                        tv.setTextColor(Color.GRAY);
                        break;
                }
                return v;
            }
        };
        spinner.setAdapter(modelArrayAdapter);
        modelArrayAdapter.notifyDataSetChanged();

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (compoundButton.getId() == R.id.checkbox_tata_ace) {
            if (checkbox_tata_ace.isChecked())
                checkBoxArray.add(checkbox_tata_ace.getText().toString().trim());
            else
                checkBoxArray.remove(checkbox_tata_ace.getText().toString().trim());}
            if (compoundButton.getId() == R.id.checkbox_mahindra) {
                if (checkbox_mahindra.isChecked())
                    checkBoxArray.add("Mahindra Pick-Up");
                else
                    checkBoxArray.remove("Mahindra Pick-Up");
            }

            if (compoundButton.getId() == R.id.checkbox_407) {
                if (checkbox_407.isChecked())
                    checkBoxArray.add(checkbox_407.getText().toString().trim());
                else
                    checkBoxArray.remove(checkbox_407.getText().toString().trim());
            }

            if (compoundButton.getId() == R.id.checkbox_1109) {
                if (checkbox_1109.isChecked())
                    checkBoxArray.add(checkbox_1109.getText().toString().trim());
                else
                    checkBoxArray.remove(checkbox_1109.getText().toString().trim());
            }
        if (compoundButton.getId() == R.id.checkbox_10tyre) {
            if (checkbox_10tyre.isChecked()) {
                checkBoxArray.add(checkbox_10tyre.getText().toString().trim());
              //  Toast.makeText(this, ""+checkBoxArray.size(), Toast.LENGTH_SHORT).show();
            }
            else{
                checkBoxArray.remove(checkbox_10tyre.getText().toString().trim());
               // Toast.makeText(this, ""+checkBoxArray.size(), Toast.LENGTH_SHORT).show();
            }
        }
        if (compoundButton.getId() == R.id.checkbox_12tyre) {
            if (checkbox_12tyre.isChecked()) {
                checkBoxArray.add(checkbox_12tyre.getText().toString().trim());
              //  Toast.makeText(this, ""+checkBoxArray.size(), Toast.LENGTH_SHORT).show();
            }
            else{
                checkBoxArray.remove(checkbox_12tyre.getText().toString().trim());
             //   Toast.makeText(this, ""+checkBoxArray.size(), Toast.LENGTH_SHORT).show();

            }

        }
        if (compoundButton.getId() == R.id.checkbox_t14tyre) {
            if (checkbox_t14tyre.isChecked())
                checkBoxArray.add(checkbox_t14tyre.getText().toString().trim());
            else
                checkBoxArray.remove(checkbox_t14tyre.getText().toString().trim());
        }
        if (compoundButton.getId() == R.id.checkbox_32feetsxl) {
            if (checkbox_32feetsxl.isChecked())
                checkBoxArray.add(checkbox_32feetsxl.getText().toString().trim());
            else
                checkBoxArray.remove(checkbox_32feetsxl.getText().toString().trim());
        }
        if (compoundButton.getId() == R.id.checkbox_32feetmxl) {
            if (checkbox_32feetmxl.isChecked())
                checkBoxArray.add(checkbox_32feetmxl.getText().toString().trim());
            else
                checkBoxArray.remove(checkbox_32feetmxl.getText().toString().trim());
        }
        if (compoundButton.getId() == R.id.checkbox_trailer) {
            if (checkbox_trailer.isChecked())
                checkBoxArray.add(checkbox_trailer.getText().toString().trim());
            else
                checkBoxArray.remove(checkbox_trailer.getText().toString().trim());
        }
        if (compoundButton.getId() == R.id.checkbox_tanker) {
            if (checkbox_tanker.isChecked())
                checkBoxArray.add(checkbox_tanker.getText().toString().trim());
            else
                checkBoxArray.remove(checkbox_tanker.getText().toString().trim());
        }


    }


    private class asynkdistrict extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {
            OkHttpClient client = new OkHttpClient();
            try {
                RequestBody formBody = new FormBody.Builder()
                       /* .add("mobile_no", mobilefp)
                        .add("password", confirmp)*/
                        .build();

                okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
                builder.url(ApiConstant.DISTRICT);
                builder.get();
                okhttp3.Request request = builder.build();
                okhttp3.Response response = client.newCall(request).execute();

                return response.body().string();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }


        //run on main thread
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            try {
                JSONObject jsn = null;
                if (!TextUtils.isEmpty(result)) {
                    jsn = new JSONObject(result);
                    boolean error=jsn.getBoolean("error");
                    String message=jsn.getString("message");
                    JSONArray jsonArray=jsn.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String id=jsonObject1.getString("id");
                        String district=jsonObject1.getString("district");
                        DistrictModel districtModel=new DistrictModel(id,district);
                        districtModel.setId(id);
                        districtModel.setDistrict(district);
                        districtModels.add(districtModel);
                        DistrictId.clear();
                        DistrictName.clear();
                        for (int j = 0; j < districtModels.size(); j++) {
                            DistrictId.add(districtModels.get(j).getId().toString());
                            DistrictName.add(districtModels.get(j).getDistrict().toString());
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DistrictId.add(0, "0");
            DistrictName.add(0, "---Please Select District---");
           final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, DistrictName);
             spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
            spinner_personal_District.setAdapter(spinnerArrayAdapter);
            spinner_personal_District.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // sent_amount_Id = amountIdList.get(position);

                                                String districtnameee = DistrictName.get(position);
                                                String districtidd = DistrictId.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });

        }
    }

    /*private class asynkgetdrivenfrom extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoyalty = new ProgressDialog(Insert_Driver.this);
            dialogLoyalty.setMessage("Please wait...");
            dialogLoyalty.setCancelable(false);
            dialogLoyalty.show();

        }

        @Override
        protected String doInBackground(String... arg0) {
            OkHttpClient client = new OkHttpClient();
            try {
                RequestBody formBody = new FormBody.Builder()
                        *//* .add("mobile_no", mobilefp)
                         .add("password", confirmp)*//*
                        .build();

                okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
                builder.url(ApiConstant.DRIVEN_FROM);
                builder.get();
                okhttp3.Request request = builder.build();
                okhttp3.Response response = client.newCall(request).execute();

                return response.body().string();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (dialogLoyalty.isShowing())
                dialogLoyalty.dismiss();
            try {
                JSONObject jsn = null;
                if (!TextUtils.isEmpty(result)) {
                    jsn = new JSONObject(result);
                    boolean error=jsn.getBoolean("error");
                    String message=jsn.getString("message");
                    JSONArray jsonArray=jsn.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String id=jsonObject1.getString("id");
                        String city=jsonObject1.getString("city");
                        String state_id=jsonObject1.getString("state_id");
                        DrivenfromModel drivenfromModel1=new DrivenfromModel(id,city,state_id);

                        drivenfromModel.add(drivenfromModel1);

                        Drivenfromid.clear();
                        Drivenfromcity.clear();
                        for (int j = 0; j < drivenfromModel.size(); j++) {
                            Drivenfromid.add(drivenfromModel.get(j).getId().toString());
                            Drivenfromcity.add(drivenfromModel.get(j).getCity().toString());
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Drivenfromid.add(0, "0");
            Drivenfromcity.add(0, "");
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Insert_Driver.this, R.layout.cutom_spiner, DistrictName);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.cutom_spiner); // The drop down view
            spinner_personal_driven_from.setAdapter(spinnerArrayAdapter);
            spinner_personal_driven_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // sent_amount_Id = amountIdList.get(position);

                  *//*  String districtnameee = DistrictName.get(position);
                    String districtidd = DistrictId.get(position);*//*
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });

        }
    }*/

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public void getlicincevalidity() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConstant.Licnce_validty, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    String message = jsonObject.getString("message");
                    String data = jsonObject.getString("data");

                    if (!error) {
                        Toast.makeText(Insert_Driver.this, message, Toast.LENGTH_SHORT).show();
                        getalertfailurelicense();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   progressBar.setVisibility(View.GONE);
                Log.e("error", error.toString());
                Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder al = new AlertDialog.Builder(Insert_Driver.this);
                String mesaage = null;
                if (error instanceof NetworkError) {
                    mesaage = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    mesaage = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    mesaage = "Cannot connect to Internet...Authfailure!";
                } else if (error instanceof NoConnectionError) {
                    mesaage = "Cannot connect to Internet...NoConnectionError!";
                } else if (error instanceof TimeoutError) {
                    mesaage = "Connection TimeOut! TimeoutError.";
                }
                al.setTitle("Service Response....");
                al.setMessage(mesaage);
                al.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driver_dlno", etdriving_license_no.getText().toString());
                return params;
            }

            @Override
            public RetryPolicy getRetryPolicy() {
                RetryPolicy retryPolicy = new DefaultRetryPolicy(60 * 1000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                return retryPolicy;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
        //progressBar.setVisibility(View.VISIBLE);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getalertfailurelicense() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Insert_Driver.this);
        LayoutInflater inflater = getLayoutInflater();
        View loyaltyView = inflater.inflate(R.layout.alert_exit_ui, null);

        builder.setView(loyaltyView);

        TextView textView = (TextView) loyaltyView.findViewById(R.id.alert_textview);
        TextView textView1 = (TextView) loyaltyView.findViewById(R.id.alert_textview1);

        textView.setText("License Number already Exists");
        textView.setGravity(Gravity.CENTER);
        textView1.setText("Oops!");
        textView1.setGravity(Gravity.CENTER);
        RelativeLayout OkayButton = loyaltyView.findViewById(R.id.btn_positive_alert);
        RelativeLayout cancleButton = loyaltyView.findViewById(R.id.btn_negative_alert);
        cancleButton.setVisibility(View.GONE);
        dialogLoyalty = builder.create();
        OkayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etdriving_license_no.setText("");
                dialogLoyalty.dismiss();
            }
        });
        Objects.requireNonNull(dialogLoyalty.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if (!this.isFinishing()) {
            dialogLoyalty.setCancelable(false);
            dialogLoyalty.show();
        }

    }


    public void getinsertdriverapi(final Dialog dialog11) {
        dialog.show();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConstant.INSERT_DRIVER, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Boolean error = jsonObject.getBoolean("error");
                    String message = jsonObject.getString("message");
                    String data = jsonObject.getString("data");
                    if (error == true) {
                        showLogoutDialog();
                        Toast.makeText(Insert_Driver.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Insert_Driver.this, message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    dismissDialog();
                    e.printStackTrace();

                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
                Log.e("error", error.toString());
                Toast.makeText(Insert_Driver.this, error.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder al = new AlertDialog.Builder(Insert_Driver.this);
                String mesaage = null;
                if (error instanceof NetworkError) {
                    mesaage = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    mesaage = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    mesaage = "Cannot connect to Internet...Authfailure!";
                } else if (error instanceof NoConnectionError) {
                    mesaage = "Cannot connect to Internet...NoConnectionError!";
                } else if (error instanceof TimeoutError) {
                    mesaage = "Connection TimeOut! TimeoutError.";
                }
                al.setTitle("Service Response....");
                al.setMessage(mesaage);
                al.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("vehincles_checkbox", String.valueOf(checkBoxArray).replace("[","").replace("]",""));

                params.put("driver_name", etFname.getText().toString());
                params.put("driver_number1", etphone1.getText().toString());
                params.put("driver_number2", etphone2.getText().toString());
                params.put("driver_edu", spinner_personal_Education.getSelectedItem().toString());
                params.put("driver_workexp", etTotalworkexp.getText().toString());
                params.put("salary_expectation", spinner_salary.getSelectedItem().toString());
                params.put("driver_address", etaddress.getText().toString());
                params.put("driver_city", spinner_personal_city.getSelectedItem().toString());
                params.put("driver_state", spinner_personal_statee.getSelectedItem().toString());
                params.put("driver_district", spinner_personal_District.getSelectedItem().toString());
                params.put("driver_street", etstreet.getText().toString());

                params.put("driver_source", spinner_personal_driven_from.getSelectedItem().toString());
                params.put("driver_dest", spinner_personal_driven_to.getSelectedItem().toString());
                params.put("driver_dlno", etdriving_license_no.getText().toString());
                params.put("license_expiry", tv_license_expiry_date.getText().toString());
                params.put("license_type", spinner_license_type.getSelectedItem().toString());
                params.put("license_case", spinner_casesonDrvinglicense.getSelectedItem().toString());
                params.put("driver_adharno", et_adhar_pan.getText().toString());
                params.put("driver_panno", et_pan.getText().toString());
                params.put("ref_name", et_ref_name.getText().toString());
                params.put("ref_number1", et_ref_contactno.getText().toString());
                params.put("ref_relation", et_ref_relation.getText().toString());
                params.put("ref_address", et_ref_address.getText().toString());
                params.put("Userid", id);
                params.put("localcity", spinner_localcity.getSelectedItem().toString());

                params.put("driver_img", imagee);
                params.put("license_img", imagee1);
                params.put("pan_img", imagee2);
                params.put("adhar_img", imagee3);
                params.put("Adhar_back_img", imagee4);

                params.put("address_proof", imagee5);
                params.put("Ref_address_proof2", imagee6);

                return params;
            }

            @Override
            public RetryPolicy getRetryPolicy() {
                RetryPolicy retryPolicy = new DefaultRetryPolicy(10 * 60 * 1000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                return retryPolicy;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
        dialog.show();
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10 * 60 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        /*stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showLogoutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View loyaltyView = inflater.inflate(R.layout.alert_exit_ui, null);

        builder.setView(loyaltyView);

        TextView textView = (TextView) loyaltyView.findViewById(R.id.alert_textview);
        TextView textView1 = (TextView) loyaltyView.findViewById(R.id.alert_textview1);

        textView.setText("Data Inserted Successfully...");
        textView.setGravity(Gravity.CENTER);
        textView1.setText("Thank You");
        textView1.setGravity(Gravity.CENTER);
        RelativeLayout OkayButton = loyaltyView.findViewById(R.id.btn_positive_alert);
        RelativeLayout cancleButton = loyaltyView.findViewById(R.id.btn_negative_alert);
        cancleButton.setVisibility(View.GONE);
        dialogLoyalty = builder.create();
        OkayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                Insert_Driver.this.finish();
//                finish();

            }
        });
        Objects.requireNonNull(dialogLoyalty.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if (!this.isFinishing()) {
            dialogLoyalty.setCancelable(false);
            dialogLoyalty.show();
        }


    }


}