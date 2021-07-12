package com.example.vgamovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePass extends AppCompatActivity {

    ImageButton ibtnNotification,ibtnHome ;
    ImageButton ibtnBack;
    TextView txtLoRe,txtLogout;
    EditText edit_password,edtNewPass,edtConfirmNewPass;
    ImageView show_pass_btn,imgShowPass,imgConfirmShowPass;
    Button btnChangePass;
    String oldPass,newPass,confirmPass;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        Email = SaveSharedPreference.getData(getApplicationContext(),"Email");

        ibtnBack = findViewById(R.id.ibtnBack);
        edit_password = findViewById(R.id.edit_password);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmNewPass = findViewById(R.id.edtConfirmNewPass);
        show_pass_btn = findViewById(R.id.show_pass_btn);
        imgShowPass = findViewById(R.id.imgShowPass);
        imgConfirmShowPass = findViewById(R.id.imgConfirmShowPass);
        btnChangePass = findViewById(R.id.btnChangePass);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer4);
        ImageButton menuRight = (ImageButton) findViewById(R.id.ibtnExtend);

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass = edit_password.getText().toString().trim();
                newPass = edtNewPass.getText().toString().trim();
                confirmPass = edtConfirmNewPass.getText().toString().trim();

                if(oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()){
                    Toast.makeText(ChangePass.this,"Vui lòng nhập thông tin mật khẩu",Toast.LENGTH_SHORT).show();
                }
                else {
                    ChangePass(Config.urlUpdatePass);
                }
            }
        });


        @SuppressLint("WrongViewCast") NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            navigationView.removeHeaderView(navigationView.getHeaderView(0));
            View header = LayoutInflater.from(this).inflate(R.layout.header1, null);
            navigationView.addHeaderView(header);
            ibtnNotification = (ImageButton) header.findViewById(R.id.ibtnNotification);
            ibtnHome = (ImageButton) header.findViewById(R.id.ibtnHome);
            txtLogout = (TextView)  header.findViewById(R.id.txtLogout);

            ibtnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ChangePass.this, "OK", Toast.LENGTH_SHORT).show();
                }
            });


            ibtnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.END);
                }
            });

            txtLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                    drawer.closeDrawer(GravityCompat.END);
                }
            });
        }
        else{
            navigationView.removeHeaderView(navigationView.getHeaderView(1));
            View header = navigationView.getHeaderView(0);
            ibtnNotification = (ImageButton) header.findViewById(R.id.ibtnNotification);
            txtLoRe = (TextView) header.findViewById(R.id.txtLoRe);
            ibtnHome = (ImageButton) header.findViewById(R.id.ibtnHome);

            ibtnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ChangePass.this, "OK", Toast.LENGTH_SHORT).show();
                }
            });

            txtLoRe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChangePass.this,Advertisement.class );
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.END);
                }
            });

            ibtnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.END);
                }
            });
        }


    }

    private void ChangePass(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(ChangePass.this,"Mật khẩu đã được thay đổi",Toast.LENGTH_SHORT).show();
                            finish();
                        }else if(response.trim().equals("Password are not same")){
                            Toast.makeText(ChangePass.this,"Mật khẩu chưa chính xác",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ChangePass.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangePass.this,"Lỗi server",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("Oldpass",oldPass);
                params.put("Newpass",newPass);
                params.put("Confirmpass",confirmPass);
                params.put("Email",Email);



                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void ShowHidePass(View view) {

        if(view.getId()==R.id.show_pass_btn){
            if(edit_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hidepass);
                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.unhide);
                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}
