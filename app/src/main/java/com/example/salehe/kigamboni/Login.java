package com.example.salehe.kigamboni;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Salehe on 7/19/2016.
 */
public class Login extends Fragment implements View.OnClickListener {

    TextView registerLink,edtUser,edtPass;
    Button btnlogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,container,false);

        registerLink =(TextView)view.findViewById(R.id.registerLink);
        btnlogin =(Button)view.findViewById(R.id.btnLogin);
        edtUser = (TextView)view.findViewById(R.id.edtUsername);
        edtPass = (TextView)view.findViewById(R.id.edtPass);

        registerLink.setOnClickListener(this);
        btnlogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerLink:
                FragmentManager fragmentManager1 = getFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                Registration reg = new Registration();
                fragmentTransaction1.replace(R.id.registration_container, reg);
                fragmentTransaction1.commit();
                break;

            case R.id.btnLogin:
                // TODO Auto-generated method stub

                if(edtUser.getText().toString().length()==0){
                    edtUser.setError("Username is Required");
                    edtUser.requestFocus();
                }
                if(edtPass.getText().toString().length()==0){
                    edtPass.setError("Password not entered");
                    edtPass.requestFocus();
                }
                else {
                    FragmentManager abc = getFragmentManager();
                    FragmentTransaction abcTrans = abc.beginTransaction();
                    PaymentForm Payform = new PaymentForm();
                    abcTrans.replace(R.id.registration_container, Payform);
                    abcTrans.commit();
                }
                break;
        }
    }
}
