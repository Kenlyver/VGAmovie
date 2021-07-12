package com.example.vgamovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;


public class Payment extends AppCompatActivity {

    TextView txtId,txtAmount,txtStatus;
    String S_countSeat,StartTime,DateShow,ShowID,CinemaHallID;
    String SaveCinemaID[] = new String[100];
    String urlInsert = "http://192.168.1.8:8080/vgamovie/insert.php";
    Button btnAgree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);
        btnAgree = findViewById(R.id.btnAgree);

        Bundle bundle = getIntent().getExtras();
        S_countSeat = bundle.getString("S_countSeat");
        StartTime = bundle.getString("Starttime");
        DateShow = bundle.getString("DateShow");
        ShowID = bundle.getString("ShowID");
        CinemaHallID = bundle.getString("CinemaHallID");
        SaveCinemaID = getIntent().getStringArrayExtra("SaveCinemaID");

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Payment.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();



        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));

            Log.d("Payment",jsonObject.getJSONObject("response").toString());

            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

            if(txtStatus.getText().toString().equals("approved")){
                addSeat(Config.urlInsert);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void addSeat(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(Payment.this,"success",Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(HallSeatBook.this, MainActivity.class));
                        }else{
                            Toast.makeText(Payment.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment.this,"Lỗi server",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("numberofSeats",S_countSeat);
                params.put("time",StartTime);
                params.put("date",DateShow);
                params.put("showID",ShowID);

                JSONObject jsonObject=new JSONObject();
                JSONArray jsonArray = new JSONArray();

                for(int i=0;i<SaveCinemaID.length;i++)
                {
                    try {
                        jsonObject.put("cinemaSeatID_"+i,SaveCinemaID[i]);
                        jsonArray.put(jsonObject.get("cinemaSeatID_"+String.valueOf(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    params.put("cinemaSeatID",CinemaSeatID);
                }
                Log.d("cinemaSeatID",jsonObject.toString());
                Log.d("Array",jsonArray.toString());
                params.put("cinemaSeatID",jsonArray.toString());
                params.put("cinemaHallID",CinemaHallID);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showDetails(JSONObject response, String paymentAmount) {

        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText("$"+paymentAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
