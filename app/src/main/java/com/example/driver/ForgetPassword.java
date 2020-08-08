package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {
    ImageView iv_back_forgot;
    EditText et_forgotpassword;
    Button btn_send;
    SessionManager sessionManager;
    ProgressBar progressBar;
    String str_forgotpassword;
    String email;
    HashMap<String, String> hashMap;
    TextView textView_register;
    public boolean isConnected;
    Toolbar toolbar;
    String getemaill;
    ImageView iv_forgetpassword;

    private void checkConnection() {
        isConnected = ConnectivityReceiver.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        et_forgotpassword = findViewById(R.id.et_forgotpassword);
        btn_send = findViewById(R.id.btnForgotPassword);
        iv_forgetpassword = findViewById(R.id.iv_forgetpassword);

        sessionManager = new SessionManager(ForgetPassword.this);
        progressBar = findViewById(R.id.forgotPassProgress);
        if (sessionManager.isLoggedIn()) {
            hashMap = sessionManager.getUSerdetails();
            email = hashMap.get(SessionManager.email);
        }

        iv_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPassword.this, Login.class);
                startActivity(intent);
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_forgotpassword.getText().toString();
                if (email != null && email.length() == 0 && email.length() <= 8) {
                    et_forgotpassword.setError("Please enter valid email address...");

                } else {

                    checkConnection();
                    if (isConnected)
                    {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConstant.RESET_PASSWORD, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Boolean error = jsonObject.getBoolean("error");
                                    String message = jsonObject.getString("message");
                                    String data = jsonObject.getString("data");
                                    Toast.makeText(ForgetPassword.this, message.toString(), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.GONE);
                                Log.e("error", error.toString());
                                Toast.makeText(ForgetPassword.this, error.toString(), Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder al = new AlertDialog.Builder(ForgetPassword.this);
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
                                params.put("login_email", et_forgotpassword.getText().toString());
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
                        Toast.makeText(ForgetPassword.this, "Please Check Internet Connection...", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

   /* public void forgotpassword() {
        getemaill=et_forgotpassword.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConstant.RESET_PASSWORD, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {


                   JSONObject jsonObject = new JSONObject(response);
                    Boolean error = jsonObject.getBoolean("error");
                    String message = jsonObject.getString("message");
                    String data = jsonObject.getString("data");
                    Toast.makeText(ForgetPassword.this, message.toString(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login_email", et_forgotpassword.getText().toString());

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
        progressBar.setVisibility(View.VISIBLE);
    }*/

}
