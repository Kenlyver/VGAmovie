package com.example.vgamovie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Movie extends AppCompatActivity {

    ImageButton ibtnBack;
    String urlGetData = "http://192.168.43.44:8080/vgamovie/getdataposter2.php";
    TextView txtTittleMovie, txtDescriptionMovie, txtContentCensorship,txtContentReleaseDate,
            txtContentGenre,txtContentDirectors, txtContentCast, txtContentTime, txtContentLanguage;
    String MovieID;
    ImageView backgroundMovie;
    Button btnBookTicket;
    ImageButton ibtnNotification,ibtnHome ;
    TextView txtLoRe,txtLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ibtnBack = findViewById(R.id.ibtnBack);
        txtTittleMovie = findViewById(R.id.txtTittleMovie);
        txtDescriptionMovie = findViewById(R.id.txtDescriptionMovie);
        txtContentCensorship = findViewById(R.id.txtContentCensorship);
        txtContentReleaseDate = findViewById(R.id.txtContentReleaseDate);
        txtContentGenre = findViewById(R.id.txtContentGenre);
        txtContentDirectors = findViewById(R.id.txtContentDirectors);
        txtContentCast = findViewById(R.id.txtContentCast);
        txtContentTime = findViewById(R.id.txtContentTime);
        txtContentLanguage = findViewById(R.id.txtContentLanguage);
        backgroundMovie = findViewById(R.id.backgroundMovie);
        btnBookTicket = findViewById(R.id.btnBookTicket);

        txtDescriptionMovie.setMovementMethod(new ScrollingMovementMethod());


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer2);
        ImageButton menuRight = (ImageButton) findViewById(R.id.ibtnExtend);

        Bundle bundle = getIntent().getExtras();
        int value = bundle.getInt("MovieID");
        MovieID = String.valueOf(value);


        btnBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
                    Intent intent = new Intent(Movie.this,BookTicket.class);
                    intent.putExtra("MovieID", MovieID);
                    startActivity(intent);
                } else {
                    showAlertDialog(Movie.this);
                }



            }
        });

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

//        getDataDetailMovie(urlGetData);

        new ReadJSON().execute(Config.urlGetData1);

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
                    Toast.makeText(Movie.this, "OK", Toast.LENGTH_SHORT).show();
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
                    finish();
                }
            });
        }
        else{
            navigationView.removeHeaderView(navigationView.getHeaderView(1));
            View header = LayoutInflater.from(this).inflate(R.layout.header, null);
            navigationView.addHeaderView(header);
            ibtnNotification = (ImageButton) header.findViewById(R.id.ibtnNotification);
            txtLoRe = (TextView) header.findViewById(R.id.txtLoRe);
            ibtnHome = (ImageButton) header.findViewById(R.id.ibtnHome);

            ibtnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Movie.this, "OK", Toast.LENGTH_SHORT).show();
                }
            });

            txtLoRe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Movie.this,Advertisement.class );
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

    private class ReadJSON extends AsyncTask<String, Void, String>{
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
                JSONArray arrayID = object.getJSONArray(MovieID);

                for(int i=0; i<arrayID.length(); i++){
                    JSONObject objectData = arrayID.getJSONObject(i);
//                    String a = objectData.getString("Title");
//                    Toast.makeText(Movie.this,a , Toast.LENGTH_SHORT).show();
                    Picasso.get()
                            .load(objectData.getString("CoverImageUrl"))
                            .into(backgroundMovie);
                    txtTittleMovie.setText(objectData.getString("Title"));
                    txtDescriptionMovie.setText(objectData.getString("Description"));
                    txtContentCensorship.setText(objectData.getString("Censorship"));
                    txtContentReleaseDate.setText(objectData.getString("ReleaseDate"));
                    txtContentGenre.setText(objectData.getString("Genre"));
                    txtContentDirectors.setText(objectData.getString("Directors"));
                    txtContentCast.setText(objectData.getString("Cast"));
                    txtContentTime.setText(objectData.getString("Duration"));
                    txtContentLanguage.setText(objectData.getString("Language"));
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Movie.this,"LỖI" , Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void showAlertDialog(Context context)  {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Vui lòng đăng nhập để tiếp tục");

        //
        builder.setCancelable(true);

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Movie.this,Login.class);
                startActivity(intent);
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


//    private void getDataDetailMovie(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i=0; i< response.length();i++){
//                    try {
//                        JSONObject object = response.getJSONObject(i);
//                        object.getJSONArray("0");
////                        JSONArray arrayID = object.getJSONArray("0");
////                        Toast.makeText(Movie.this,arrayID.length(),Toast.LENGTH_SHORT).show();
////                        txtTittleMovie.setText(object.getString("Title"));
////                        txtDescriptionMovie.setText(object.getString("Description"));
//                        txtContentCensorship.setText(object.getString("Censorship"));
//                        txtContentReleaseDate.setText(object.getString("ReleaseDate"));
//                        txtContentGenre.setText(object.getString("Genre"));
//                        txtContentDirectors.setText(object.getString("Directors"));
//                        txtContentCast.setText(object.getString("Cast"));
//                        txtContentTime.setText(object.getString("Duration"));
//                        txtContentLanguage.setText(object.getString("Language"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Movie.this,error.toString(),Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        requestQueue.add(jsonArrayRequest);
//    }
}
