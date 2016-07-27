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
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Salehe on 7/19/2016.
 */
public class Registration extends Fragment implements View.OnClickListener{
    /*rgistration*/
    String username,password,phoneNumber,firstName,lastName;

    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
   /* private static final String LOGIN_URL ="http://192.168.43.20/android/login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";*/

    /*end...*/
    Button btnReg;
    TextView logiLink;
    EditText edtFirst,edtLast, edtUser, edtPass, edtConfPass, edtPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_fragment,container,false);

        edtFirst=(EditText)view.findViewById(R.id.edtfirstname);
        edtLast=(EditText)view.findViewById(R.id.edtlastname);
        edtUser=(EditText)view.findViewById(R.id.edtUsername);
        edtPass=(EditText)view.findViewById(R.id.edtPass);
        edtConfPass=(EditText)view.findViewById(R.id.edtConfirmPass);
        edtPhone=(EditText)view.findViewById(R.id.edtPhonenumber);
        //Initialization of Register Button
        btnReg=(Button)view.findViewById(R.id.btnRegister);
        logiLink = (TextView)view.findViewById(R.id.loginLink);

        logiLink.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                // TODO Auto-generated method stub
                if(edtFirst.getText().toString().length()==0){
                    edtFirst.setError("First name not entered");
                    edtFirst.requestFocus();
                }
                if(edtLast.getText().toString().length()==0){
                    edtLast.setError("Last name not entered");
                    edtLast.requestFocus();
                }

                if(edtUser.getText().toString().length()==0){
                    edtUser.setError("Username is Required");
                    edtUser.requestFocus();
                }
                if(edtPass.getText().toString().length()==0){
                    edtPass.setError("Password not entered");
                    edtPass.requestFocus();
                }
                if(edtConfPass.getText().toString().length()==0){
                    edtConfPass.setError("Please confirm password");
                    edtLast.requestFocus();
                }
                if(!edtPass.getText().toString().equals(edtConfPass.getText().toString())){
                    edtConfPass.setError("Password Not matched");
                    edtConfPass.requestFocus();
                }
                if(edtPass.getText().toString().length()<3){
                    edtPass.setError("Password should be atleast of 8 charactors");
                edtPass.requestFocus();
                }
                else {
                    invokeRegistration();
//                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.loginLink:
                FragmentManager fragmentManager1 = getFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                Login login = new Login();
                fragmentTransaction1.replace(R.id.registration_container, login);
                fragmentTransaction1.commit();
//                Toast.makeText(getActivity(),"login",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void invokeRegistration(){
        username = edtUser.getText().toString();
        password = edtPass.getText().toString();
        firstName = edtFirst.getText().toString();
        lastName = edtLast.getText().toString();
        phoneNumber = edtPhone.getText().toString();
        registration(firstName, lastName, username, password, phoneNumber);
    }

    private void registration(final String username, String password, String firstName, String lastName, String phoneNumber) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];
                String fname = params[2];
                String lname = params[3];
                String pnumber = params[4];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                nameValuePairs.add(new BasicNameValuePair("first_name", fname));
                nameValuePairs.add(new BasicNameValuePair("last_name", lname));
                nameValuePairs.add(new BasicNameValuePair("phone_number", pnumber));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.43.20/android/registration.php");
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
                    Login login = new Login();
                    abcTrans.replace(R.id.registration_container, login);
                    abcTrans.commit();
                }else {
                    Toast.makeText(getActivity(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(username, password,firstName,lastName,phoneNumber);

    }
}
