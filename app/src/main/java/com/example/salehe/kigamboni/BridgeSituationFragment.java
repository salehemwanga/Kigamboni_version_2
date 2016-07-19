package com.example.salehe.kigamboni;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Salehe on 7/19/2016.
 */
public class BridgeSituationFragment extends Fragment {

    Config conf = new Config();
    public  String value;
    public  String value2;

    public class Wrapper {
        public String JSON_STRING;
        public String JSON_STRING1;
    }

    Wrapper w = new Wrapper();

    private ListView listView, listView1;
    public TextView name, cost, status;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bridge_situation_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.listView);
        listView1 = (ListView) view.findViewById(R.id.listView1);
        getJSON();
        return view;
    }


    private void showBridgeSituation() {

        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(w.JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String time = jo.getString(conf.TAG_TIME);
                String situation = jo.getString(conf.TAG_STATUS);
                String id = jo.getString(conf.STATUS_ID);

                HashMap<String, String> employees = new HashMap<>();
                employees.put(conf.TAG_TIME, time);
                employees.put(conf.STATUS_ID, id);
                employees.put(conf.TAG_STATUS, situation);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
             this.getActivity(), list, R.layout.list_view_situation,
//                new String[]{Config.TAG_TIME, Config.TAG_STATUS, Config.STATUS_ID},
                new String[]{conf.TAG_TIME, conf.TAG_STATUS},
                new int[]{R.id.tvTime, R.id.tvStatus});
        listView.setAdapter(adapter);
    }




    private void showCost(){
        Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(w.JSON_STRING1);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString(Config.TAG_VEHICLE_TYPE);
                String price = jo.getString(Config.TAG_COST);
                String status = jo.getString(Config.TAG_STATUS_COST);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_VEHICLE_TYPE,name);
                employees.put(Config.TAG_STATUS_COST,status);
                employees.put(Config.TAG_COST,price);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.list_view_cost,
                new String[]{Config.TAG_VEHICLE_TYPE,Config.TAG_STATUS_COST,Config.TAG_COST},
                new int[]{R.id.tvVehicleType,R.id.tvStatusCost, R.id.tvCost});
        listView1.setAdapter(adapter);
    }


    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, Wrapper> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(Wrapper s) {

                super.onPostExecute(s);
                loading.dismiss();
                w.JSON_STRING = s.JSON_STRING;
                showBridgeSituation();
                showCost();
            }

            @Override
            protected Wrapper doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                w.JSON_STRING = rh.sendGetRequest(Config.URL_GET_SITUATION);
                w.JSON_STRING1  = rh.sendGetRequestParam(Config.URL_GET_COST,value);
                return w;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
