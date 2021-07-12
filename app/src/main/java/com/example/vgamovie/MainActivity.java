package com.example.vgamovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    Animation in_right,out_left,out_right,in_left;
    private float initialX;
    ImageButton ibtnAds, ibtnExtend, ibtnLogin, ibtnNotification,ibtnHome ;
    ImageView imagePoster;
    TextView txtLoRe,txtLogout;
    DrawerLayout mDrawerLayout;
    GestureDetector mGestureDetector;
    private ViewPager2 viewPager2;
    String urlGetData = "http://192.168.43.44:8080/vgamovie/getdataposter.php";
    PosterAdapter adapter;
    List<CustomPoster> posterImage = new ArrayList<>();
    String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipperAds);
        ibtnAds = (ImageButton) findViewById(R.id.ibtnAds);
        ibtnExtend = (ImageButton) findViewById(R.id.ibtnExtend);
        ibtnLogin = (ImageButton) findViewById(R.id.ibtnLogin);
        imagePoster = (ImageView) findViewById(R.id.imageViewPoster);


        //initData();
        getDataPoster(Config.urlGetData);

        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.horizontal_cycle);
        adapter = new PosterAdapter(posterImage,getBaseContext());
        pager.setAdapter(adapter);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ImageButton menuRight = (ImageButton) findViewById(R.id.ibtnExtend);


        in_left = AnimationUtils.loadAnimation(this,R.anim.in_left);
        out_right = AnimationUtils.loadAnimation(this,R.anim.out_right);
        in_right = AnimationUtils.loadAnimation(this,R.anim.in_right);
        out_left = AnimationUtils.loadAnimation(this,R.anim.out_left);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(this, customGestureDetector);


        ibtnAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
                Intent intent= new Intent(MainActivity.this,UserInfo.class);
                startActivity(intent);
                }
                else {
                    Intent intent= new Intent(MainActivity.this,Advertisement.class);
                    startActivity(intent);
                }
            }
        });

        ibtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
                    Toast.makeText(MainActivity.this, "Bạn đã đăng nhập",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
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
                    Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
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
//            View header = navigationView.getHeaderView(0);
            View header = LayoutInflater.from(this).inflate(R.layout.header, null);
            navigationView.addHeaderView(header);
            ibtnNotification = (ImageButton) header.findViewById(R.id.ibtnNotification);
            txtLoRe = (TextView) header.findViewById(R.id.txtLoRe);
            ibtnHome = (ImageButton) header.findViewById(R.id.ibtnHome);

            ibtnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }
            });

            txtLoRe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,Advertisement.class );
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

    private void getDataPoster(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i< response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        posterImage.add(new CustomPoster(
                                object.getString("MarkUrl"),
                                object.getString("ImageUrl"),
                                object.getString("Title"),
                                object.getString("Duration")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    private void initData()
    {
        posterImage.add(new CustomPoster("https://i.ibb.co/qL8BvYf/c13.png","https://i.ibb.co/mtLk4cS/poster-ww84.jpg","WONDER WOMAN 1984","2 giờ 31 phút"));
        posterImage.add(new CustomPoster("https://i.ibb.co/qL8BvYf/c13.png","https://i.ibb.co/YZJ6mc1/poster-hunter.jpg","THỢ SĂN QUÁI VẬT","1 giờ 44 phút"));
        posterImage.add(new CustomPoster("https://i.ibb.co/SPVK20X/cp.png","https://i.ibb.co/L8cXHxR/poster-doraemon.jpg","NOBITA VÀ BẠN KHỦNG LONG","1 giờ 50 phút"));
        posterImage.add(new CustomPoster("https://i.ibb.co/MnkYc1C/c16.png","https://i.ibb.co/jLGhbLC/poster-ncqpn.jpg","NGƯỜI CẦN QUÊN PHẢI NHỚ","1 giờ 50 phút"));
        posterImage.add(new CustomPoster("https://i.ibb.co/SPVK20X/cp.png","https://i.ibb.co/X2J36Qr/poster-soul.jpg","CUỘC SỐNG NHIỆM MÀU","1 giờ 47 phút"));
        posterImage.add(new CustomPoster("https://i.ibb.co/hyXrNSv/c18.png","https://i.ibb.co/ZNccv8b/poster-thutrang.png","CHỊ MƯỜI BA: 3 NGÀY SINH TỪ","1 giờ 35 phút"));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() > e2.getX()) {
                if(viewFlipper.isAutoStart()) {
                    viewFlipper.stopFlipping();
                    viewFlipper.setInAnimation(MainActivity.this, R.anim.in_right);
                    viewFlipper.setOutAnimation(MainActivity.this, R.anim.out_left);
                    viewFlipper.showNext();
                    viewFlipper.startFlipping();
                    viewFlipper.setAutoStart(true);
                }
            }

            if (e1.getX() < e2.getX()) {
                if(viewFlipper.isAutoStart()) {
                    viewFlipper.stopFlipping();
                    viewFlipper.setInAnimation(MainActivity.this, R.anim.in_left);
                    viewFlipper.setOutAnimation(MainActivity.this, R.anim.out_right);
                    viewFlipper.showPrevious();
                    viewFlipper.startFlipping();
                    viewFlipper.setAutoStart(true);
                }
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("Tap", "Double tap");
            return super.onDoubleTap(e);
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            return false;
        }
    }

}
