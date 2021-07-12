package com.example.vgamovie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import java.util.Map;


public class Login extends AppCompatActivity {
    ImageButton ibtnBack;
    TextView edit_password,txtForgetPass;
    ImageView imgvShow;
    Button btnRigister,btnLogin;
    EditText edtEmail, edtPass;
    String urlLogin = "http://192.168.43.44:8080/vgamovie/login.php";
    String Email,Pass,EmailinUser,PasswordinUser;
    boolean checkLogin =false;
    SharedPreferences sp ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ibtnBack = findViewById(R.id.ibtnBack);
        imgvShow = findViewById(R.id.show_pass_btn);
        edit_password = findViewById(R.id.edit_password);
        txtForgetPass = findViewById(R.id.txtForgetPass);
        btnRigister = findViewById(R.id.btnRigister);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edit_password);
        sp = getSharedPreferences("login",MODE_PRIVATE);

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        txtForgetPass.getPaint().setUnderlineText(true);
        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,FogetPass.class);
                startActivity(intent);
            }
        });

        btnRigister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=edtEmail.getText().toString().trim();
//                Pass=getmd5ofstring(edtPass.getText().toString().trim());
                Pass=edtPass.getText().toString().trim();

                String emailFormat = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(!Email.trim().matches(emailFormat)){
                    edtEmail.requestFocus();
                    edtEmail.setError("'Email' không hợp lệ");
                }
                else if(Email.length()==0)
                {
                    edtEmail.requestFocus();
                    edtEmail.setError("Chưa nhập 'Email'");
                }
                else if(Pass.length()==0)
                {
                    edtPass.requestFocus();
                    edtPass.setError("Chưa nhập 'Mật khẩu'");
                }
                else {
                    loginAcc(Config.urlLogin);
                }
            }
        });


    }

    private void loginAcc(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equals("success")){
                    checkLogin =true;
                    Toast.makeText(Login.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                    SaveSharedPreference.saveData(getApplicationContext(),"Email",Email);
                    startActivity(new Intent(Login.this, MainActivity.class));
                }else{
                    Toast.makeText(Login.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("email",Email);
                params.put("pass",Pass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }



    public static final String getmd5ofstring(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString(); // return md5

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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
