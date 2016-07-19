package com.example.salehe.kigamboni;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Salehe on 7/19/2016.
 */
public class Registration extends Fragment implements View.OnClickListener{
    Button btnReg;
    EditText edtFirst,edtLast, edtUser, edtPass, edtConfPass, edtEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_fragment,container,false);

        edtFirst=(EditText)view.findViewById(R.id.edtfirstname);
        edtLast=(EditText)view.findViewById(R.id.edtlastname);
        edtUser=(EditText)view.findViewById(R.id.edtUsername);
        edtPass=(EditText)view.findViewById(R.id.edtPass);
        edtConfPass=(EditText)view.findViewById(R.id.edtConfirmPass);
        edtEmail=(EditText)view.findViewById(R.id.edtEmail);
        //Initialization of Register Button
        btnReg=(Button)view.findViewById(R.id.button1);
        btnReg.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
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
                if(edtPass.getText().toString().length()<8){
                    edtPass.setError("Password should be atleast of 8 charactors");
                edtPass.requestFocus();
                }
                else {
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}
