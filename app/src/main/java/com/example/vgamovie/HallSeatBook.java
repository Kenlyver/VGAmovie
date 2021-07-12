package com.example.vgamovie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class HallSeatBook extends AppCompatActivity implements View.OnClickListener {

    ViewGroup layout;
    String urlGetData = "http://192.168.43.44:8080/vgamovie/getdataposter3.php";
    String urlInsert = "http://192.168.43.44:8080/vgamovie/insert.php";

    public static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    String seats = "_________________/"
            + "_________________/"
            + "AA__AAAAAAA__AA/"
            + "AA__AAAAAAA__AA/"
            + "AA__AAAAAAA__AA/"
            + "AA__AAAAAAA__AA/"
            + "AA__AAAAAAA__AA/"
            + "AA__AAAAAAA__AA/"
            + "AA__AAAAAAA__AA/"
            + "AA__AAAAAAA__AA/"
            + "AA__AAAAAAA__AA/"
            + "_________________/";


    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";
    String NameSeat ="A" ;
    int countMoney = 0,countSeat=0;
    int iShowID,iCinemaHallID;
    String ShowID,MovieID,CinemaHallID,NameTheater,ShowIDinShowS,IDinCinema,S_countSeat,Date,StartTime,CinemaSeatID;
    TextView txtNameTheater,txtCinemaHall,txtDateShow,txtTimeStart,txtTimeEnd;
    ImageButton ibtnBack;
    int CinemaSeatID_SS_Array[] = new int [100];
    int Status_SS_Array[] = new int[100];
    String SaveCinemaID[] = new String[100];
    TextView txtTitle,txtMoney;
    String amount="";
    String amUSD ="";;
    Button btnBookTicket;
    ArrayList<String> seatIDList = new ArrayList<>();


    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_seat_book);

        Bundle bundle = getIntent().getExtras();
        iShowID = bundle.getInt("ShowID");
        ShowID = String.valueOf(iShowID);
        MovieID = bundle.getString("MovieID");
        iCinemaHallID = bundle.getInt("CinemaHallID");
        NameTheater =bundle.getString("NameTheater");
        ShowIDinShowS =bundle.getString("ShowIDinShowS");
        CinemaSeatID_SS_Array= getIntent().getIntArrayExtra("SeatIDArray");
        Status_SS_Array= getIntent().getIntArrayExtra("StatusArray");
        CinemaHallID = String.valueOf(iCinemaHallID);
        StringBuilder newSeat = null;
        new HallSeatBook.ReadJSONDataMovie().execute(Config.urlGetData2);

        ibtnBack = findViewById(R.id.ibtnBack);

        txtNameTheater = findViewById(R.id.txtNameTheater);
        txtCinemaHall = findViewById(R.id.txtCinemaHall);
        txtDateShow = findViewById(R.id.txtDateShow);
        txtTimeStart = findViewById(R.id.txtTimeStart);
        txtTimeEnd = findViewById(R.id.txtTimeEnd);

        txtTitle = findViewById(R.id.txtTitle);
        txtMoney = findViewById(R.id.txtMoney);
        btnBookTicket= findViewById(R.id.btnBook);

        txtNameTheater.setText(NameTheater);

        layout = findViewById(R.id.layoutSeat);

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);




        btnBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                processPayment();
//                addSeat(urlInsert);
            }
        });

        seats = "/" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;
        int countRow = 0;
        int countSeat=0;

        newSeat = new StringBuilder(seats);
        for (int index = 0; index < seats.length(); index++) {
            if (seats.charAt(index) == 'U') {

            }
            else if (seats.charAt(index) == 'A') {
                if(ShowID.equals(ShowIDinShowS)){
                for(int i=0;i<CinemaSeatID_SS_Array.length;i++) {
                    if(Status_SS_Array[i]==1) {
                        if (CinemaSeatID_SS_Array[i] < 3) {
                            newSeat.setCharAt(35 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 3 && CinemaSeatID_SS_Array[i] < 10) {
                                newSeat.setCharAt(38 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 10 && CinemaSeatID_SS_Array[i] < 13) {
                                newSeat.setCharAt(40 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] < 14) {
                                newSeat.setCharAt(41 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 14 && CinemaSeatID_SS_Array[i] < 21) {
                                newSeat.setCharAt(43 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 21 && CinemaSeatID_SS_Array[i] < 24) {
                                newSeat.setCharAt(45 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 23 && CinemaSeatID_SS_Array[i] < 25) {
                                newSeat.setCharAt(46 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 25 && CinemaSeatID_SS_Array[i] < 32) {
                                newSeat.setCharAt(48 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 32 && CinemaSeatID_SS_Array[i] < 35) {
                                newSeat.setCharAt(50 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 34 && CinemaSeatID_SS_Array[i] < 36) {
                                newSeat.setCharAt(51 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 36 && CinemaSeatID_SS_Array[i] < 43) {
                                newSeat.setCharAt(53 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 43 && CinemaSeatID_SS_Array[i] < 45) {
                                newSeat.setCharAt(55 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 45 && CinemaSeatID_SS_Array[i] < 47) {
                                newSeat.setCharAt(56 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 47 && CinemaSeatID_SS_Array[i] < 54) {
                                newSeat.setCharAt(58 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 54 && CinemaSeatID_SS_Array[i] < 56) {
                                newSeat.setCharAt(60 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 56 && CinemaSeatID_SS_Array[i] < 58) {
                                newSeat.setCharAt(61 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 58 && CinemaSeatID_SS_Array[i] < 65) {
                                newSeat.setCharAt(63 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 65 && CinemaSeatID_SS_Array[i] < 67) {
                                newSeat.setCharAt(65 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 67 && CinemaSeatID_SS_Array[i] < 69) {
                                newSeat.setCharAt(66 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 69 && CinemaSeatID_SS_Array[i] < 76) {
                                newSeat.setCharAt(68 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 76 && CinemaSeatID_SS_Array[i] < 78) {
                                newSeat.setCharAt(70 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 78 && CinemaSeatID_SS_Array[i] < 80) {
                                newSeat.setCharAt(71 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 80 && CinemaSeatID_SS_Array[i] < 87) {
                                newSeat.setCharAt(73 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 87 && CinemaSeatID_SS_Array[i] < 89) {
                                newSeat.setCharAt(75 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 89 && CinemaSeatID_SS_Array[i] < 91) {
                                newSeat.setCharAt(76 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 91 && CinemaSeatID_SS_Array[i] < 98) {
                                newSeat.setCharAt(78 + CinemaSeatID_SS_Array[i], 'U');
                        } else if (CinemaSeatID_SS_Array[i] >= 98 && CinemaSeatID_SS_Array[i] < 100) {
                                newSeat.setCharAt(80 + CinemaSeatID_SS_Array[i], 'U');
                        }
                    }
                    }
                }}
            else if (seats.charAt(index) == 'R') {
                countSeat++;
            }
        }

        for (int index = 0; index < newSeat.length(); index++) {
            if (newSeat.charAt(index) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (newSeat.charAt(index) == 'U') {
                count++;
                countRow++;
                if(countRow==12){
                    countRow =1;
                }
                NameSeatInHall(count);
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(NameSeat+countRow + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);

            } else if (newSeat.charAt(index) == 'A') {
                count++;
                countRow++;
                countSeat++;
                if(countRow==12){
                    countRow =1;
                }

                NameSeatInHall(count);
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(NameSeat +countRow + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (newSeat.charAt(index) == 'R') {
                count++;
                countRow++;
                if(countRow==12){
                    countRow =1;
                }
                NameSeatInHall(count);
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_reserved);
                view.setText(NameSeat +count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_RESERVED);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (newSeat.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }
    }

    private void processPayment() {

        amount = txtMoney.getText().toString();
        int iamount = Integer.parseInt(amount);
        float amountUSD=0;
        amountUSD = (float)iamount/22000;
        amUSD = String.valueOf(amountUSD);

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amUSD)),"USD",
                "Thanh toán tiền vé",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }



    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId() + ",")) {
                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);
                countMoney =countMoney-45000;
                String Money = String.valueOf(countMoney);
                txtMoney.setText(Money);
                countSeat--;
                int IDSeat=0 ;
                IDSeat = view.getId();
                CinemaSeatID = String.valueOf(IDSeat);
                removeElements(SaveCinemaID,CinemaSeatID);
                S_countSeat =String.valueOf(countSeat);
            } else {
                countMoney = countMoney + 45000;
                countSeat++;
                S_countSeat =String.valueOf(countSeat);
                String Money = String.valueOf(countMoney);
                txtMoney.setText(Money);
                int IDSeat=0 ;
                IDSeat = view.getId();
                CinemaSeatID = String.valueOf(IDSeat);
                SaveCinemaID[countSeat-1]= CinemaSeatID;
                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_selected);
            }
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Ghế này đã được đặt", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Ghế này đang được giữ", Toast.LENGTH_SHORT).show();
        }
    }

    private class ReadJSONDataMovie extends AsyncTask<String, Void, String> {
        StringBuilder content = new StringBuilder();
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line="";

                while ((line= bufferedReader.readLine()) != null){
                    content.append(line);
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);
                JSONArray arrayShow = object.getJSONArray("Show");
                JSONArray arrayCinemaHall = object.getJSONArray("CinemaHall");
                JSONArray arrayShow_Seat = object.getJSONArray("ShowSeat");
                JSONArray arrayNameID = object.getJSONArray("Movie").getJSONObject(0).getJSONArray(MovieID);

                for(int i=0; i<arrayNameID.length(); i++){
                    JSONObject objectData = arrayNameID.getJSONObject(i);
                    txtTitle.setText(objectData.getString("Title"));
                }

                int count=0;
                for(int i=0; i<arrayShow.length(); i++){
                    JSONObject objectData = arrayShow.getJSONObject(i);
                    String ShowIDinShow = objectData.getString("ShowID");
                    if(ShowID.equals(ShowIDinShow)){
                        txtDateShow.setText(objectData.getString("Date"));
                        Date = objectData.getString("Date");
                        txtTimeStart.setText(objectData.getString("StartTime"));
                        StartTime = objectData.getString("StartTime");
                        txtTimeEnd.setText(objectData.getString("EndTime"));
                        for(int j=0;j<arrayCinemaHall.length();j++){
                            JSONObject objectData2 = arrayCinemaHall.getJSONObject(j);
                            IDinCinema = objectData2.getString("CinemaHallD");
                            if(CinemaHallID.equals(IDinCinema)){
                                txtCinemaHall.setText(objectData2.getString("Name"));
                            }
                        }

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(HallSeatBook.this,"LỖI" , Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, Payment.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amUSD)
                                .putExtra("S_countSeat",S_countSeat)
                                .putExtra("Starttime",StartTime)
                                .putExtra("DateShow",txtDateShow.getText().toString().trim())
                                .putExtra("ShowID",ShowID)
                                .putExtra("CinemaHallID",CinemaHallID)
                                .putExtra("SaveCinemaID",SaveCinemaID)
                        );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invailid", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void addSeat(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(HallSeatBook.this,"success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HallSeatBook.this, MainActivity.class));
                        }else{
                            Toast.makeText(HallSeatBook.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HallSeatBook.this,"Lỗi server",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("numberofSeats",S_countSeat);
                params.put("time",StartTime);
                params.put("date",txtDateShow.getText().toString().trim());
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



    public static String[] removeElements(String[] input, String deleteMe) {
        List result = new LinkedList();

        for(String item : input)
            if(!deleteMe.equals(item))
                result.add(item);

        return (String[]) result.toArray(input);
    }



    public void NameSeatInHall(int i)
    {
        if(i ==12){
            NameSeat="B";
        }
        else if(i ==23){
            NameSeat="C";
        }
        else if(i ==34){
            NameSeat="D";
        }
        else if(i ==45){
            NameSeat="E";
        }
        else if(i ==56){
            NameSeat="F";
        }
        else if(i ==67){
            NameSeat="G";
        }
        else if(i ==78){
            NameSeat="H";
        }
        else if(i ==89){
            NameSeat="J";
        }
        else if(i ==100){
            NameSeat="K";
        }
    }
}
