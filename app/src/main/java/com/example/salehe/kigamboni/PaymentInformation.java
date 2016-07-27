package com.example.salehe.kigamboni;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
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
public class PaymentInformation extends Fragment implements View.OnClickListener {

    Button btnMpesa,btnTigo;
    TextView mpNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_information_fragment,container,false);
        btnMpesa = (Button) view.findViewById(R.id.btnMpesa);
        btnTigo = (Button) view.findViewById(R.id.btnTigo);
        mpNo = (TextView)view.findViewById(R.id.mpesaNo);
        btnMpesa.setOnClickListener(this);
        btnTigo.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMpesa:
                performDialVoda();
                break;

            case R.id.btnTigo:
                performDialTigo();
                break;
        }
    }

    private void performDialVoda() {
        try {
            Uri number = Uri.parse("tel:*150*00#");
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performDialTigo() {
        try {
            Uri number = Uri.parse("tel:*150*01#");
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
