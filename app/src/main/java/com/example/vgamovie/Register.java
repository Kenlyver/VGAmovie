package com.example.vgamovie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    ImageButton ibtnBack;
    TextView edit_password;
    EditText edtName,edtNumberPhone,edtEmail,edtPass;
    Button btnRigister;
    String emailFormat = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String Name, Email,Phone, Pass;
    String urlInsert = "http://192.168.43.44:8080/vgamovie/insert1.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ibtnBack = findViewById(R.id.ibtnBack);
        edit_password = findViewById(R.id.edit_password);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtNumberPhone = findViewById(R.id.edtNumberPhone);
        edtPass = findViewById(R.id.edit_password);
        btnRigister = findViewById(R.id.btnRigister);


        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(Register.this);
            }
        });

        btnRigister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=edtName.getText().toString();
                Email=edtEmail.getText().toString();
                Phone=edtNumberPhone.getText().toString();
                Pass=edtPass.getText().toString();
                if(Name.length()==0)
                {
                    edtName.requestFocus();
                    edtName.setError("'Họ Tên' không được để trống");
                }
                else if(!Name.matches("[a-zA-Z ]+"))
                {
                    edtName.requestFocus();
                    edtName.setError("Chỉ nhập các kí tự trong alphabet");
                }
                else if(Phone.length()==0)
                {
                    edtNumberPhone.requestFocus();
                    edtNumberPhone.setError("'Số điện thoại' không được để trống");
                }
                else if(!Email.trim().matches(emailFormat)){
                    edtEmail.requestFocus();
                    edtEmail.setError("'Email' không hợp lệ");
                }
                else if(Email.length()==0)
                {
                    edtEmail.requestFocus();
                    edtEmail.setError("'Email' không được để trống");
                }
                else if(Pass.length()==0)
                {
                    edtPass.requestFocus();
                    edtPass.setError("'Mật khẩu' không được để trống");
                }
                else if(Name.length()<=2 )
                {
                    edtName.requestFocus();
                    edtName.setError("'Họ Tên' phải lớn hơn 2");
                }
                else if(Pass.length()<6 )
                {
                    edtPass.requestFocus();
                    edtPass.setError("'Mật khẩu' phải có ít nhất 6 kí tự");
                }
                else {
                    RegisterAccount(Config.urlInsert1);

                }

            }
        });
    }


    private void RegisterAccount(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("success")){
                            Toast.makeText(Register.this,"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Register.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this,"Lỗi server",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("name",Name);
                params.put("email",Email);
                params.put("pass",Pass);
                params.put("phone",Phone);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

//    public static final String getmd5ofstring(final String s) {
//        final String MD5 = "MD5";
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = java.security.MessageDigest
//                    .getInstance(MD5);
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuilder hexString = new StringBuilder();
//            for (byte aMessageDigest : messageDigest) {
//                String h = Integer.toHexString(0xFF & aMessageDigest);
//                while (h.length() < 2)
//                    h = "0" + h;
//                hexString.append(h);
//            }
//            return hexString.toString(); // return md5
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }


    public void showAlertDialog(Context context)  {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Bạn muốn hủy bỏ việc đăng ký?");

        //
        builder.setCancelable(true);

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
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
