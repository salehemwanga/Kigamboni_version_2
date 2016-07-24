package com.example.salehe.kigamboni;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public class Wrapper {
        public String JSON_STRING;
    }
    Wrapper w = new Wrapper();
    TextView tvmain;

    LinearLayout btnhome,btnsituation,btnnews,btnpayment;
    TextView home,payment,situation,newsText;
    ImageView imageView;

    /*refresh*/
    Handler handler = new Handler();
    Runnable refresh;
    /**/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*custom action bar*/
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        imageView = (ImageView) findViewById(R.id.btnSetting);
        imageView.setOnClickListener(this);
        /*end*/

        /**/
        tvmain = (TextView) findViewById(R.id.tvmain);
        /**/
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

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final HomeFragment homeFragment = new HomeFragment();
        final BridgeSituationFragment bridgeSituation = new BridgeSituationFragment();
        fragmentTransaction.add(R.id.main_container, homeFragment);
        fragmentTransaction.commit();


        /*refresh code*/
        refresh = new Runnable() {
            public void run() {
                getJSON();
                handler.postDelayed(refresh, 5000);
            }
        };
        handler.post(refresh);

        /*end refresh*/

        getJSON();
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

            case R.id.btnSetting:
                LayoutInflater layout =LayoutInflater.from(this);
                View promptView = layout.inflate(R.layout.notification_layout,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Long alertTime = new GregorianCalendar().getTimeInMillis()+10000;
                                Intent alertIntent =  new Intent(MainActivity.this,AlertReceiver.class);

                                AlarmManager alarmNanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                alarmNanager.set(AlarmManager.RTC_WAKEUP,alertTime, PendingIntent.getBroadcast(MainActivity.this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                                Toast.makeText(MainActivity.this, "Notification set", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"canceled", Toast.LENGTH_SHORT).show();
                            }
                        });

                alertDialogBuilder.create().show();

                break;
        }
    }

    private void showBridgeSituation() {

//        Wrapper w = new Wrapper();
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(w.JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
//                String time = jo.getString(Config.TAG_TIME);
                String situationid = jo.getString(Config.TAG_STATUS_ID);

                HashMap<String, String> employees = new HashMap<String,String>();
                employees.put(Config.TAG_STATUS_ID, situationid);
                employees.put("1",Config.TAG_STATUS_ID);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvmain.setText(list.get(0).get(Config.TAG_STATUS_ID));

    }
    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, Wrapper> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(Wrapper s) {

                super.onPostExecute(s);
                loading.dismiss();
                w.JSON_STRING = s.JSON_STRING;
//               w.JSON_STRING1 = s.JSON_STRING1;
                showBridgeSituation();
//                showCost();
            }

            @Override
            protected Wrapper doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                w.JSON_STRING = rh.sendGetRequest(Config.URL_GET_SITUATION);
//                w.JSON_STRING1  = rh.sendGetRequestParam(Config.URL_GET_COST, "1");
                return w;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Appliation")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
