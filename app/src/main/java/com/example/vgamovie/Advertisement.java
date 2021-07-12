package com.example.vgamovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class Advertisement extends AppCompatActivity {
    ViewPager viewPager;
    SpringDotsIndicator dot2;
    ViewAdapter viewAdapter;
    ImageButton ibtnBack;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dot2 = findViewById(R.id.dot2);
        ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRigister);

        viewAdapter = new ViewAdapter(this);
        viewPager.setAdapter(viewAdapter);
        dot2.setViewPager(viewPager);

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Advertisement.this,Login.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Advertisement.this,Register.class);
                startActivity(intent);
            }
        });

    }
}
