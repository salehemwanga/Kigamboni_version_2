package com.example.salehe.kigamboni;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btnhome,btnsituation,btnnews,btnpayment;
   /* public  FragmentManager fragmentManager = getFragmentManager();
    public  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                fragmentTransaction1.commit();
                break;

            case R.id.btnPayment:
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                PackagePaymentFragment payment = new PackagePaymentFragment();
                fragmentTransaction2.replace(R.id.main_container, payment);
                fragmentTransaction2.commit();
                break;

            case R.id.btnSituation:
                FragmentManager fragmentManager3 = getFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                BridgeSituationFragment bridgeSituation = new BridgeSituationFragment();
                fragmentTransaction3.replace(R.id.main_container,bridgeSituation);
                fragmentTransaction3.commit();
                break;

            case R.id.btnNews:
                FragmentManager fragmentManager4 = getFragmentManager();
                FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                TrafficNewsFragment news = new TrafficNewsFragment();
                fragmentTransaction4.replace(R.id.main_container,news);
                fragmentTransaction4.commit();
                break;
        }
    }
}
