package com.example.salehe.kigamboni;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salehe on 7/24/2016.
 */
public class PaymentForm extends Fragment implements View.OnClickListener{
    Button btnpay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_form_fragment,container,false);

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

                break;
        }
    }
}
