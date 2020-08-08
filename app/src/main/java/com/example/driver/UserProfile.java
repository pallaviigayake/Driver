package com.example.driver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.example.driver.service.ApiConstant;
import com.example.driver.service.AppController;
import com.example.driver.service.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {


    TextInputEditText edt_username, edt_mobile, edt_email;
    Button btn_update;
    ProgressBar progressbar_Login;
    SessionManager sessionManager;
    HashMap<String, String> hashMap;
    String id, name, email, no, image;
    ImageView iv_userprofile;
    CircleImageView img_profile;


    int var = 0;
    String role = "";
    String str_title, str_location, str_startdate, str_enddate, str_links, str_tags,
            category_id = "", topic_id = "", subtopic_id = "";
    private String imgPath = null;
    int PERMISSION_ALL = 1;
    private InputStream inputStreamImg;
    public static int CAMERA1 = 100;
    Uri selectedImage;
    private File destination = null;
    Bitmap bitmap = null;
    String get_imagee1 = "", get_imagee2 = "", get_imagee3 = "", get_imagee4 = "";
    String uid = "", imagee = "", typee = "";
    String uid1 = "", imagee1 = "", typee1 = "";
    String uid2 = "", imagee2 = "", typee2 = "";
    String uid3 = "", imagee3 = "", typee3 = "";
    String encodedImage;
    ArrayList<String> arrayList;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2, ACTION_IMAGE_CAPTURE = 12;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,};

  /*  Handler handler = new Handler();
    Runnable timedTask = new Runnable() {

        @Override
        public void run() {
            profileupdate();
            handler.postDelayed(timedTask, 1000);
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //onBackPressed();

        sessionManager = new SessionManager(UserProfile.this);


        edt_username = findViewById(R.id.edt_username);
        img_profile = findViewById(R.id.img_profile);
        edt_mobile = findViewById(R.id.edt_mobile);
        progressbar_Login = findViewById(R.id.progressbar_Login);
        edt_email = findViewById(R.id.edt_email);
        btn_update = findViewById(R.id.btn_update);
        iv_userprofile = findViewById(R.id.iv_userprofile);
        iv_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        setdata();
        img_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(UserProfile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.CAMERA}, CAMERA1);
                } else {
                    var = 1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkPermission1();
                    }

                }
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileupdate();
                //  handler.post(timedTask);
            }
        });
        //  doTheAutoRefresh();
    }


    public void setdata() {
        if (sessionManager.isLoggedIn()) {
            hashMap = sessionManager.getUSerdetails();
            id = hashMap.get(SessionManager.userId);
            name = hashMap.get(SessionManager.name);
            email = hashMap.get(SessionManager.email);
            no = hashMap.get(SessionManager.mobile);
            image = hashMap.get(SessionManager.image);
        }

        edt_username.setText(name);
        edt_email.setText(email);
        edt_mobile.setText(no);

        try {
            Glide.with(getApplicationContext()).load(image).into(img_profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission1() {
        if (ContextCompat.checkSelfPermission(UserProfile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(UserProfile.this,
                Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(UserProfile.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (UserProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (UserProfile.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale
                    (UserProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(UserProfile.this.findViewById(android.R.id.content),
                        "Please Grant Permissions for read external storage",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
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
                //img_profile.setImageDrawable(Glide.with(getApplicationContext()).load(image).into(img_profile));
                selectImage();
            }
        }
    }

    private void selectImage() {
        try {
            PackageManager pm = UserProfile.this.getPackageManager();

            if (1 == 1) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
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
                ActivityCompat.requestPermissions(UserProfile.this, PERMISSIONS, PERMISSION_ALL);
        } catch (Exception e) {
            Toast.makeText(UserProfile.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
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
                    bitmap = MediaStore.Images.Media.getBitmap(UserProfile.this.getContentResolver(), selectedImage);
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
                        Log.d("imageeeeeeeeeee_first", get_imagee1);
                        imagee = get_imagee1;
                        Log.d("imageeeeeeeeeee", imagee);
                        typee = "driverhjbbbbbb";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = UserProfile.this.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void profileupdate() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConstant.PROFILE_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbar_Login.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean error = jsonObject.getBoolean("error");
                    String message = jsonObject.getString("message");
                    //   String data = jsonObject.getString("data");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String userId = jsonObject1.getString("userId");
                        String email = jsonObject1.getString("email");
                        String name = jsonObject1.getString("name");
                        String mobile = jsonObject1.getString("mobile");
                        String createdDtm = jsonObject1.getString("createdDtm");
                        String roleText = jsonObject1.getString("roleText");
                        String role = jsonObject1.getString("role");
                        String image = jsonObject1.getString("image");
                        Toast.makeText(UserProfile.this, message.toString(), Toast.LENGTH_SHORT).show();

                        sessionManager.createLoginSession(userId, role, roleText, edt_username.getText().toString(), edt_email.getText().toString(), image, edt_mobile.getText().toString(), createdDtm);
                        setdata();
                     //   UserProfile.this.notifyAll();
                    }

//                    notifyAll();
                    Toast.makeText(UserProfile.this, message.toString(), Toast.LENGTH_SHORT).show();

                    // setData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar_Login.setVisibility(View.GONE);
                Log.e("error", error.toString());
                Toast.makeText(UserProfile.this, error.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder al = new AlertDialog.Builder(UserProfile.this);
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
                params.put("fname", edt_username.getText().toString());
                params.put("mobile", edt_mobile.getText().toString());
                params.put("email", edt_email.getText().toString());
                params.put("userid", id);
                params.put("profile_img", imagee);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserProfile.this, MainActivity.class);
        startActivity(intent);
       /* MainActivity.li_insertdriver.setBackgroundDrawable(getResources().getDrawable(R.drawable.corner_radius_dashbord));
        MainActivity.li_updateprofile.setBackgroundDrawable(getResources().getDrawable(R.drawable.corner_radius_dashbord));
        MainActivity.rel_driverReport.setBackgroundDrawable(getResources().getDrawable(R.drawable.corner_radius_dashbord));
 */   }

  /*  private void doTheAutoRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                profileupdate();
                // Write code for your refresh logic
                doTheAutoRefresh();
            }
        }, 2000);
    }*/
}
