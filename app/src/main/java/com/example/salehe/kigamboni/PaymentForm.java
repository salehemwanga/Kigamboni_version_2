package com.example.salehe.kigamboni;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salehe on 7/24/2016.
 */
public class PaymentForm extends Fragment implements View.OnClickListener{
    /*payment info*/
    String edtPlateNo,edtAccountNo,edtCardNo;

    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    /*end...*/

    Button btnpay;
    EditText tvPlateNo,tvAccountNo,tvCardNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_form_fragment,container,false);

        /*payment form field*/
        tvPlateNo = (EditText) view.findViewById(R.id.edtPlatenumber);
        tvAccountNo = (EditText) view.findViewById(R.id.edtAccountnumber);
        tvCardNo = (EditText) view.findViewById(R.id.edtCardnumber);
        /*end..*/

        btnpay = (Button)view.findViewById(R.id.btnPay);
        btnpay.setOnClickListener(this);
        /*drop down  list*/
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Package Type");
        categories.add("Week");
        categories.add("3 Months");
        categories.add("6 Months");
        categories.add("1 Year");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(dataAdapter);
        /*end dropdown list*/

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btnPay:
                invokePayment();
                break;
        }
    }

    public void invokePayment(){
        edtPlateNo = tvPlateNo.getText().toString();
        edtAccountNo = tvAccountNo.getText().toString();
        edtCardNo = tvCardNo.getText().toString();
        payment(edtPlateNo, edtAccountNo, edtCardNo);
    }

    private void payment(final String edtPlateNo, String edtAccountNo, String edtCardNo) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String plateNumber = params[0];
                String accountNumber = params[1];
                String cardNumber = params[2];

                //Toast.makeText(getActivity(),"this is from activity",Toast.LENGTH_SHORT).show();

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("plate_number", plateNumber));
                nameValuePairs.add(new BasicNameValuePair("account_number", accountNumber));
                nameValuePairs.add(new BasicNameValuePair("card_number", cardNumber));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.43.20/android/payment.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    FragmentManager abc = getFragmentManager();
                    FragmentTransaction abcTrans = abc.beginTransaction();
                    HomeFragment homeFragment = new HomeFragment();
                    abcTrans.replace(R.id.main_container, homeFragment);
                    abcTrans.commit();
                }else {
                    Toast.makeText(getActivity(), "Payment Fail!", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
//        Toast.makeText(getActivity(), edtPlateNo, Toast.LENGTH_SHORT).show();
        la.execute(edtPlateNo, edtAccountNo, edtCardNo);

    }
}
