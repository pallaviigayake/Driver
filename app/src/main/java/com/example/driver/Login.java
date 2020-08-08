package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
EditText etUsername_Login,etPassword_Login;
Button btLogin_Login;
ProgressBar progressbar_Login;
SessionManager sessionManager;
String name,password;
TextView tv_forgotpassword_Login;
    private boolean isConnected;
    private void checkConnection() {
        isConnected = ConnectivityReceiver.isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername_Login = findViewById(R.id.etUsername_Login);
        tv_forgotpassword_Login = findViewById(R.id.tv_forgotpassword_Login);
        etPassword_Login = findViewById(R.id.etPassword_Login);
        btLogin_Login = findViewById(R.id.btLogin_Login);
        progressbar_Login = findViewById(R.id.progressbar_Login);
        sessionManager = new SessionManager(Login.this);

        tv_forgotpassword_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        btLogin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUsername_Login.getText().toString() != null && etUsername_Login.getText().toString().equals("")) {
                    etUsername_Login.setError("Please enter User Name");
                } else if (etPassword_Login.getText().toString() != null && etPassword_Login.getText().toString().equals("")) {
                    etPassword_Login.setError("Please enter password");
                    //showToast("Please enter password");
                } else {

                    checkConnection();
                    if (isConnected) {

                        /*InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);*/

                        getlogin();
                    } else {
                        Toast.makeText(getApplicationContext(), "Check Internet connection", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });

    }
    public void getlogin(){
        name=etUsername_Login.getText().toString().trim();
        password=etPassword_Login.getText().toString().trim();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ApiConstant.LOGIN, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean error=jsonObject.getBoolean("error");
                    String message=jsonObject.getString("message");
                    if (error==true){
                        Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_LONG).show();
                        progressbar_Login.setVisibility(View.GONE);

                        Log.e("Error", response);
                    }else{
                        progressbar_Login.setVisibility(View.GONE);
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");

                            String userId=jsonObject1.getString("userId");
                            String role=jsonObject1.getString("role");
                            String roleText=jsonObject1.getString("roleText");
                            String name=jsonObject1.getString("name");
                            String email=jsonObject1.getString("email");
                            String image=jsonObject1.getString("image");
                            String mobile=jsonObject1.getString("mobile");
                            String lastLogin=jsonObject1.getString("lastLogin");
                            boolean isLoggedIn=jsonObject1.getBoolean("isLoggedIn");

                            sessionManager.createLoginSession(userId, role, roleText, name, email,image,mobile,lastLogin);

                        Intent intent = new Intent(Login.this, MainActivity.class);
                     //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar_Login.setVisibility(View.GONE);
                Log.e("error",error.toString());
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder al = new AlertDialog.Builder(Login.this);
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
                params.put("username", name);
                params.put("password", password);

                return params;
            }
            @Override
            public RetryPolicy getRetryPolicy() {
                RetryPolicy retryPolicy = new DefaultRetryPolicy(5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                return retryPolicy;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
        progressbar_Login.setVisibility(View.VISIBLE);

    }
}