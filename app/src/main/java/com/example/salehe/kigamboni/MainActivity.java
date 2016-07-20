package com.example.salehe.kigamboni;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btnhome,btnsituation,btnnews,btnpayment;
    TextView home,payment,situation,newsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       /* *//*refresh code*//*
        handler = new Handler();

        handler.postDelayed(m_Runnable, 5000);
        *//*end refresh code*//*
*/

        home = (TextView) findViewById(R.id.home);
        payment = (TextView) findViewById(R.id.paymentText);
        situation = (TextView) findViewById(R.id.situation);
        newsText = (TextView) findViewById(R.id.newsText);

        home.setTextColor(Color.WHITE);
        btnhome = (LinearLayout) findViewById(R.id.btnHome);
        btnsituation = (LinearLayout) findViewById(R.id.btnSituation);
        btnnews = (LinearLayout) findViewById(R.id.btnNews);
        btnpayment = (LinearLayout) findViewById(R.id.btnPayment);

        btnhome.setOnClickListener(this);
        btnsituation.setOnClickListener(this);
        btnnews.setOnClickListener(this);
        btnpayment.setOnClickListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.main_container,homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHome:
                FragmentManager fragmentManager1 = getFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction1.replace(R.id.main_container,homeFragment);
                home.setTextColor(Color.WHITE);
                payment.setTextColor(Color.BLACK);
                newsText.setTextColor(Color.BLACK);
                situation.setTextColor(Color.BLACK);
                fragmentTransaction1.commit();
                break;

            case R.id.btnPayment:
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                PackagePaymentFragment payment1 = new PackagePaymentFragment();
                fragmentTransaction2.replace(R.id.main_container, payment1);
                home.setTextColor(Color.BLACK);
                payment.setTextColor(Color.WHITE);
                newsText.setTextColor(Color.BLACK);
                situation.setTextColor(Color.BLACK);
                fragmentTransaction2.commit();
                break;

            case R.id.btnSituation:
                FragmentManager fragmentManager3 = getFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                BridgeSituationFragment bridgeSituation = new BridgeSituationFragment();
                fragmentTransaction3.replace(R.id.main_container,bridgeSituation);
                home.setTextColor(Color.BLACK);
                payment.setTextColor(Color.BLACK);
                newsText.setTextColor(Color.BLACK);
                situation.setTextColor(Color.WHITE);
                fragmentTransaction3.commit();
                break;

            case R.id.btnNews:
                FragmentManager fragmentManager4 = getFragmentManager();
                FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                TrafficNewsFragment news = new TrafficNewsFragment();
                fragmentTransaction4.replace(R.id.main_container,news);
                home.setTextColor(Color.BLACK);
                payment.setTextColor(Color.BLACK);
                newsText.setTextColor(Color.WHITE);
                situation.setTextColor(Color.BLACK);
                fragmentTransaction4.commit();
                break;
        }
    }

}
