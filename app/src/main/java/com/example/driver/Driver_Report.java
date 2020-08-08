package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.driver.service.ApiConstant;
import com.example.driver.service.AppController;
import com.example.driver.service.ConnectivityReceiver;
import com.example.driver.service.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Driver_Report extends AppCompatActivity {

    ImageView iv_Back_report;
    EditText et_totaldriver, et_currentmonth;
    boolean isConnected;
    ProgressBar progressBar;
    SessionManager sessionManager;
    HashMap<String, String> hashMap;
    String id;

    private void checkConnection() {
        isConnected = ConnectivityReceiver.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__report);
       // onBackPressed();
        et_currentmonth = findViewById(R.id.et_currentmonth);
        progressBar = findViewById(R.id.Progress);
        et_totaldriver = findViewById(R.id.et_totaldriver);
        iv_Back_report = findViewById(R.id.iv_Back_report);
        sessionManager = new SessionManager(Driver_Report.this);
        if (sessionManager.isLoggedIn()) {
            hashMap = sessionManager.getUSerdetails();
            id = hashMap.get(SessionManager.userId);
        }
        iv_Back_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Driver_Report.this, MainActivity.class);
                startActivity(intent);
            }
        });
        getdata();
    }

    public void getdata() {
        checkConnection();
        if (isConnected) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConstant.DRIVER_REPORT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean error = jsonObject.getBoolean("error");
                        String message = jsonObject.getString("message");

                        // JSONArray jsonArray=jsonObject.getJSONArray("data");
                       /* for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String countid=jsonObject1.getString("countid");
                            et_totaldriver.setText(countid);
                        }
                        JSONObject jsonObject2=*/
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = jsonObject1.getJSONArray("Total_Driver");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            String countid = jsonObject11.getString("countid");
                            et_totaldriver.setText(countid);
                        }
                        String Percentage_Driver = jsonObject1.getString("Percentage_Driver");
                        et_currentmonth.setText(Percentage_Driver + "" + "%");
                        /*int maxLength = 5;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        et_currentmonth.setFilters(fArray);*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Log.e("error", error.toString());
                    Toast.makeText(Driver_Report.this, error.toString(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder al = new AlertDialog.Builder(Driver_Report.this);
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
                    params.put("id", id);
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
            progressBar.setVisibility(View.VISIBLE);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } else {
            Toast.makeText(Driver_Report.this, "Please Check Internet Connection...", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();
        Intent intent=new Intent(Driver_Report.this,MainActivity.class);
        startActivity(intent);*/
         }
}