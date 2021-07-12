package com.example.vgamovie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class BookTicket extends AppCompatActivity {

    String urlGetData = "http://192.168.43.44:8080/vgamovie/getdataposter3.php";
    ImageButton ibtnBack;
    String MovieID,dateMat,CinemaID,date,TimeNow,DateNow,ShowID,ShowIDinShowS;
    ImageButton ibtnNotification,ibtnHome ;
    TextView txtLoRe,txtLogout;
    TextView txtTittleMovieBook;
    int ShowIDArray[] = new int[100];
    int CinemaHallIDArray[] = new int[100];
    String CinemaNameArray[] = new String[100];
    int CinemaSeatID_SS_Array[] = new int[100];
    int Status_SS_Array[] = new int[100];


    ListView lvMovieTime;
    ArrayList<MovieTime> arrayMovieTime;
    MovieTimeAdapter adapter;

    SimpleDateFormat format1;
    HorizontalCalendar horizontalCalendar;
    Date currentTime,currentDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        lvMovieTime = (ListView) findViewById(R.id.listMovieTime);
        arrayMovieTime = new ArrayList<>();

        adapter = new MovieTimeAdapter(this,R.layout.layout_movietime,arrayMovieTime);
        lvMovieTime.setAdapter(adapter);


        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        Bundle bundle = getIntent().getExtras();
        MovieID = bundle.getString("MovieID");



        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .build();


        ibtnBack = findViewById(R.id.ibtnBack);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer3);
        ImageButton menuRight = (ImageButton) findViewById(R.id.ibtnExtend);
        txtTittleMovieBook = findViewById(R.id.txtTittleMovieBook);



        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvMovieTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookTicket.this,HallSeatBook.class);
                intent.putExtra("SeatIDArray",CinemaSeatID_SS_Array );
                intent.putExtra("StatusArray",Status_SS_Array );
                intent.putExtra("ShowIDinShowS", ShowIDinShowS);
                intent.putExtra("MovieID", MovieID);
                startActivity(intent);
                if(position==0){
                    intent.putExtra("ShowID", ShowIDArray[0]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[0]);
                    intent.putExtra("NameTheater", CinemaNameArray[0]);

                }
                if(position==1){
                    intent.putExtra("ShowID", ShowIDArray[1]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[1]);
                    intent.putExtra("NameTheater", CinemaNameArray[1]);
//
                }
                if(position==2){
                    intent.putExtra("ShowID", ShowIDArray[2]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[2]);
                    intent.putExtra("NameTheater", CinemaNameArray[2]);
//
                }
                if(position==3){
                    intent.putExtra("ShowID", ShowIDArray[3]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[3]);
                    intent.putExtra("NameTheater", CinemaNameArray[3]);

                }
                if(position==4){
                    intent.putExtra("ShowID", ShowIDArray[4]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[4]);
                    intent.putExtra("NameTheater", CinemaNameArray[4]);

                }
                if(position==5){
                    intent.putExtra("ShowID", ShowIDArray[5]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[5]);
                    intent.putExtra("NameTheater", CinemaNameArray[5]);

                }
                if(position==6){
                    intent.putExtra("ShowID", ShowIDArray[6]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[6]);
                    intent.putExtra("NameTheater", CinemaNameArray[6]);

                }
                if(position==7){
                    intent.putExtra("ShowID", ShowIDArray[7]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[7]);
                    intent.putExtra("NameTheater", CinemaNameArray[7]);
//                    intent.putExtra("CinemaSeatID_SS", CinemaSeatID_SS_Array[7]);
                    intent.putExtra("Status_SS", Status_SS_Array[7]);
                }
                if(position==8){
                    intent.putExtra("ShowID", ShowIDArray[8]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[8]);
                    intent.putExtra("NameTheater", CinemaNameArray[8]);

                }
                if(position==9){
                    intent.putExtra("ShowID", ShowIDArray[9]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[9]);
                    intent.putExtra("NameTheater", CinemaNameArray[9]);

                }
                if(position==10){
                    intent.putExtra("ShowID", ShowIDArray[10]);
                    intent.putExtra("CinemaHallID", CinemaHallIDArray[10]);
                    intent.putExtra("NameTheater", CinemaNameArray[10]);

                }
                startActivity(intent);
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
                    Toast.makeText(BookTicket.this, "OK", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BookTicket.this, "OK", Toast.LENGTH_SHORT).show();
                }
            });

            txtLoRe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookTicket.this,Advertisement.class );
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



        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                dateMat = format1.format(date);
                currentTime = Calendar.getInstance().getTime();
                currentDate = Calendar.getInstance().getTime();
                DateNow = format1.format(currentDate);
                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
                TimeNow = format2.format(currentTime);
                adapter.clearData();
                new BookTicket.ReadJSONDataMovie().execute(Config.urlGetData2);

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
                JSONArray arrayNameID = object.getJSONArray("Movie").getJSONObject(0).getJSONArray(MovieID);
                JSONArray arrayMovie = object.getJSONArray("Show");
                JSONArray arrayCinemaHall = object.getJSONArray("CinemaHall");
                JSONArray arrayShow_Seat = object.getJSONArray("ShowSeat");


                for(int i=0; i<arrayNameID.length(); i++){
                    JSONObject objectData = arrayNameID.getJSONObject(i);
//                    String a = objectData.getString("Title");
//                    Toast.makeText(Movie.this,a , Toast.LENGTH_SHORT).show();
//                    Picasso.get()
//                            .load(objectData.getString("CoverImageUrl"))
//                            .into(backgroundMovie);
                    txtTittleMovieBook.setText(objectData.getString("Title"));
                }
                int count=0;
                int count2 = 0;
                for(int i=0; i<arrayMovie.length(); i++){
                    JSONObject objectData = arrayMovie.getJSONObject(i);
                    date = objectData.getString("Date");
                    String movieIDinShow = objectData.getString("MoivieID");
                    String StartTime = objectData.getString("StartTime");
                    String CinemaHallID = objectData.getString("CinemaHallID");

                    CinemaID = objectData.getString("CinemaID");
                    if(movieIDinShow.equals(MovieID)){
                        JSONArray arrayCinema = object.getJSONArray("Cinema");
                        for(int j=0;j<arrayCinema.length();j++){
                        JSONObject objectData1 = arrayCinema.getJSONObject(j);
                            String cinemaIDList = objectData1.getString("CinemaID");
                            String NameCinema = objectData1.getString("Name");
                            if (date.equals(dateMat) && cinemaIDList.equals(CinemaID) ) {
                                if (date.compareTo(DateNow)==0) {
                                    if(TimeNow.compareTo(StartTime)<0) {
                                        count++;
                                        arrayMovieTime.add(new MovieTime(NameCinema, StartTime));
                                        String TG = objectData.getString("ShowID");
                                        String NameCinemaSent = objectData1.getString("Name");
                                        int SID = Integer.parseInt(TG);
                                        int CineHID = Integer.parseInt(CinemaHallID);
                                        ShowIDArray[count-1] = SID;
                                        for(int k=0;k<arrayShow_Seat.length();k++){
                                            JSONObject objectDataShowSeat = arrayShow_Seat.getJSONObject(k);
                                            ShowIDinShowS = objectDataShowSeat.getString("ShowID");
                                            String CinemaH_ID =  objectDataShowSeat.getString("CinemaHallID");
                                            int iCinemaH_ID = Integer.parseInt(CinemaH_ID);
                                            if(TG.equals(ShowIDinShowS)  ){
                                                count2++;
                                                String SeatID_SS = objectDataShowSeat.getString("CinemaSeatID");
                                                String Status_SS = objectDataShowSeat.getString("Status");

                                                int TGSeatID = Integer.parseInt(SeatID_SS);
                                                int TGStatus = Integer.parseInt(Status_SS);

                                                CinemaSeatID_SS_Array[count2-1]= TGSeatID;
                                                Status_SS_Array[count2-1]=TGStatus ;

                                                }
                                        }
                                        CinemaHallIDArray[count-1]=CineHID;
                                        CinemaNameArray[count-1]=NameCinemaSent;
                                    }
                                }
                                else {
                                    arrayMovieTime.add(new MovieTime(NameCinema, StartTime));
                                    ShowID = objectData.getString("ShowID");
                                }
                            }
                        }
                    }

                }


                adapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(BookTicket.this,"LỖI" , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
