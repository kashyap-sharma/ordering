package co.jlabs.ordering;

import android.app.Activity;
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

import com.andreabaccega.widget.FormEditText;

import org.json.JSONObject;

import co.jlabs.ordering.fragmentsInitialiser.Initialiser;

/**
 * Created by JLabs on 06/28/16.
 */
public class NewAddress  extends Fragment{
    FormEditText name, address, landmark, contact;
    Initialiser init;
//    Pizza_Objecto pra_test;
//    Context context;
//    OrderApplication app;
    String n,a,l,c;
    int check=0;
    public NewAddress() {
        // Required empty public constructor
    }
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         final View v=  inflater.inflate(R.layout.new_last_page_content, container, false);
         name=(FormEditText)v.findViewById(R.id.name);
         address=(FormEditText)v.findViewById(R.id.address);
         landmark=(FormEditText)v.findViewById(R.id.landmark);
         contact=(FormEditText)v.findViewById(R.id.contact);

         contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length()>9&&s.length()<14) {
                     check=123;
                   n=name.getText().toString();
                   a=address.getText().toString();
                   l=landmark.getText().toString();
                   c=contact.getText().toString();
                    onClickNext(v);

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

    public void onButtonPressed(int ch,String na,String aa,String la,String ca ) {
        if (mListener != null) {
            mListener.onFragmentInteraction(ch,na, aa, la,ca);
        }
    }

    public void onClickNext(View v) {
        FormEditText[] allFields    = { name, address, contact, landmark };


        boolean allValid = true;
        for (FormEditText field: allFields) {
            allValid = field.testValidity() && allValid;
        }

        if (allValid) {
            onButtonPressed(check,n, a, l,c);

        } else {
            // EditText are going to appear with an exclamation mark and an explicative message.
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int ch, String names, String add,String la, String land);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
