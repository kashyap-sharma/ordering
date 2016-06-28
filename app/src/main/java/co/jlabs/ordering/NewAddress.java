package co.jlabs.ordering;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import co.jlabs.ordering.fragmentsInitialiser.Initialiser;

/**
 * Created by JLabs on 06/28/16.
 */
public class NewAddress  extends Fragment{
     EditText name, address, landmark, contact;
    Initialiser init;
//    Pizza_Objecto pra_test;
//    Context context;
//    OrderApplication app;
    String n,a,l,c;
    public NewAddress() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v=  inflater.inflate(R.layout.new_last_page_content, container, false);
         name=(EditText)v.findViewById(R.id.name);
         address=(EditText)v.findViewById(R.id.address);
         landmark=(EditText)v.findViewById(R.id.landmark);
         contact=(EditText)v.findViewById(R.id.contact);
         contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length()>9&&s.length()<14) {
                   n=name.getText().toString();
                   a=address.getText().toString();
                   l=landmark.getText().toString();
                   c=contact.getText().toString();
                    SendFbData();
                } else {

                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {


                // TODO Auto-generated method stub
            }
        });

         return v;
    }

    private void SendFbData() {

            init.updateName(7,n,a,c,l);
    }



}
