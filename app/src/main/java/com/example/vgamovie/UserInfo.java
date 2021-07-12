package com.example.vgamovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class UserInfo extends AppCompatActivity {

    ImageButton ibtnBack;
    TextView txtNameUser,txtIdUser;
    ImageButton ibtnNotification,ibtnHome ;
    TextView txtLoRe,txtLogout,txtChangeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ibtnBack = findViewById(R.id.ibtnBack);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer3);
        ImageButton menuRight = (ImageButton) findViewById(R.id.ibtnExtend);
        txtNameUser = findViewById(R.id.txtNameUser);
        txtIdUser = findViewById(R.id.txtIdUser);
        txtChangeInfo = findViewById(R.id.txtChangeInfo);

        txtChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfo.this, ChangePass.class);
                startActivity(intent);
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
                    Toast.makeText(UserInfo.this, "OK", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UserInfo.this, "OK", Toast.LENGTH_SHORT).show();
                }
            });

            txtLoRe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserInfo.this,Advertisement.class );
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
}
