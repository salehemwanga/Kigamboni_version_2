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
import android.widget.Toast;

/**
 * Created by Salehe on 7/19/2016.
 */
public class PackagePaymentFragment extends Fragment implements View.OnClickListener{
    Button btnLogin,btnInformation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.package_payment_fragment,container,false);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnInformation = (Button) view.findViewById(R.id.btnInformation);
        btnInformation.setBackgroundColor(Color.parseColor("#64b5f6"));
        btnLogin.setOnClickListener(this);
        btnInformation.setOnClickListener(this);
        FragmentManager fragmentManager =getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PaymentInformation info =  new PaymentInformation();
        fragmentTransaction.add(R.id.registration_container,info);
        fragmentTransaction.commit();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Registration registration = new Registration();
                fragmentTransaction.replace(R.id.registration_container, registration);
                fragmentTransaction.commit();
                btnLogin.setBackgroundColor(Color.parseColor("#64b5f6"));
                btnInformation.setBackgroundColor(Color.parseColor("#dcedc8"));
                Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnInformation:
                FragmentManager fragmentManager1 = getFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                PaymentInformation info = new PaymentInformation();
                fragmentTransaction1.replace(R.id.registration_container, info);
                fragmentTransaction1.commit();
                btnInformation.setBackgroundColor(Color.parseColor("#64b5f6"));
                btnLogin.setBackgroundColor(Color.parseColor("#dcedc8"));
                Toast.makeText(getActivity(),"information",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
