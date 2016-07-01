package co.jlabs.ordering;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import co.jlabs.ordering.LikeAnimation.LikeButtonView;
import co.jlabs.ordering.photoview.MaterialFontIcons;


public class OrderStatusLast extends Activity {
    private static String url = "http://lannister-api.elasticbeanstalk.com/tyrion/details?vendor_id=1&order_number=";
    TextView status_current;
    MaterialFontIcons placed, ready_dispatch, on_way, delivered;
    String order_number;
    String status;

    Context context;
    LinearLayout parent,  hide;
    JSONObject data = null;
    private static final String TAG_DATA = "data";
    private static final String TAG_STATUS = "status";
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status);
        context=this;
        placed = (MaterialFontIcons) findViewById(R.id.placed);
        parent=(LinearLayout)findViewById(R.id.parent);

        hide = (LinearLayout) findViewById(R.id.hide);
        ready_dispatch = (MaterialFontIcons) findViewById(R.id.ready_dispatch);
        on_way = (MaterialFontIcons) findViewById(R.id.on_way);
        delivered = (MaterialFontIcons) findViewById(R.id.delivered);
        status_current=(TextView)findViewById(R.id.status_current);
        Log.i("hipo", "65" + order_number);
        if (JSONfunctions.isNetworkAvailable(context)) {
            new GetStatus().execute();
            status = Static_Catelog.getStringProperty(context, "status_current");
            Log.i("stamo", "stat" + status);
            final int k = 1;

            final Handler ha = new Handler();
            ha.postDelayed(new Runnable() {

                @Override
                public void run() {

                    status = Static_Catelog.getStringProperty(context, "status_current");

                    new GetStatus().execute();
                    Log.i("stamo111", "stat" + status);
                    if (status != null) {

                        if (status.equals("placed")) {
                            status_current.setText("Your order has been placed.");
                            placed.setTextColor(Color.parseColor("#f26522"));
                            on_way.setTextColor(Color.parseColor("#4F4F4F"));
                            ready_dispatch.setTextColor(Color.parseColor("#4F4F4F"));
                            delivered.setTextColor(Color.parseColor("#4F4F4F"));
                        } else if (status.equals("accepted") || status.equals("delayed")) {
                            placed.setTextColor(Color.parseColor("#4F4F4F"));
                            on_way.setTextColor(Color.parseColor("#4F4F4F"));
                            ready_dispatch.setTextColor(Color.parseColor("#f26522"));
                            delivered.setTextColor(Color.parseColor("#4F4F4F"));
                            status_current.setText("Your order has been accepted.");
                        } else if (status.equals("ready")) {

                            placed.setTextColor(Color.parseColor("#4F4F4F"));
                            on_way.setTextColor(Color.parseColor("#f26522"));
                            ready_dispatch.setTextColor(Color.parseColor("#4F4F4F"));
                            delivered.setTextColor(Color.parseColor("#4F4F4F"));
                            status_current.setText("Your order is out for delivery.");
                        } else if (status.equals("delivered")) {
                            placed.setTextColor(Color.parseColor("#4F4F4F"));
                            on_way.setTextColor(Color.parseColor("#4F4F4F"));
                            ready_dispatch.setTextColor(Color.parseColor("#4F4F4F"));
                            delivered.setTextColor(Color.parseColor("#f26522"));
                            hide.setVisibility(View.GONE);
                            status_current.setText("Your order is delivered to you successfully.");
                        } else {
                            parent.setVisibility(View.GONE);
                            status_current.setText("Some issue in fetching data...!!!");
                        }


                    }
                    ha.postDelayed(this, 1000);
                }
            }, 500);
        } else {
            parent.setVisibility(View.GONE);
            status_current.setVisibility(View.GONE);

        }

    }


    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(OrderStatusLast.this, MainActivity.class);

        startActivity(setIntent);
        finish();

    }


    private class GetStatus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
          /*  pDialog = new ProgressDialog(OrderStatusLast.this);
          //  pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
*/
        }



        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            order_number=Static_Catelog.getStringProperty(context,"order_number");
            Log.d("Response1: ", "> " + order_number);
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url+order_number, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    data = jsonObj.getJSONObject(TAG_DATA);
                    Log.i("datqs","124"+data);

//                    // looping through All Contacts
//
//
                        String status = data.getString(TAG_STATUS);
                        Log.i("hipo","hipo1"+status);
                        Static_Catelog.setStringProperty(context, "status_current",status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    }

             else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }



    }

}
