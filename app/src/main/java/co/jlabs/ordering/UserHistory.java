package co.jlabs.ordering;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class UserHistory  extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://lannister-api.elasticbeanstalk.com/tyrion/history?email=";
    private static String url1 = "&vendor_id=1";

    // JSON Node names
    private static final String TAG_DATA = "data";
    private static final String TAG_STATUS = "status";
    private static final String TAG_NAME = "name";
    private static final String TAG_Total="total";
    private static final String TAG_Order_Num = "order_number";
    private static final String TAG_ORDER = "order";

    String email;
    Context context;
    String status;
    // contacts JSONArray
    LinearLayout parent, no_net_;
    JSONArray data = null;
    JSONArray order = null;
    private static String names="";
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        context=this;
        parent = (LinearLayout) findViewById(R.id.parent);
        no_net_ = (LinearLayout) findViewById(R.id.no_net_);
        contactList = new ArrayList<HashMap<String, String>>();
        email=Static_Catelog.getStringProperty(context,"email");
//        ListView lv = getListView();
//
//        // Listview on item click listener
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // getting values from selected ListItem
//                String name = ((TextView) view.findViewById(R.id.name))
//                        .getText().toString();
//                String cost = ((TextView) view.findViewById(R.id.email))
//                        .getText().toString();
//                String description = ((TextView) view.findViewById(R.id.mobile))
//                        .getText().toString();
//
//                // Starting single contact activity
//                Intent in = new Intent(getApplicationContext(),
//                        SingleOrderActivity.class);
//                in.putExtra(TAG_NAME, name);
//                in.putExtra(TAG_Total, cost);
//                in.putExtra(TAG_Order_Num, description);
//                startActivity(in);
//
//            }
//        });

        // Calling async task to get json
        if (JSONfunctions.isNetworkAvailable(context)) {
            new GetContacts().execute();
        } else {
            parent.setVisibility(View.GONE);
            no_net_.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(UserHistory.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url+email+url1, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    data = jsonObj.getJSONArray(TAG_DATA);
                    Log.i("dats",""+data);
                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);

                        String status = "Order Status: "+c.getString(TAG_STATUS);

                        String total ="₹"+ c.getString(TAG_Total);
                        String order_number = "Order Number: "+c.getString(TAG_Order_Num);

//						String gender = c.getString(TAG_GENDER);

                        // Phone node is JSON Object

                        //JSONObject jsonObj1 = jsonObj.getJSONObject("order");
                        try {
                            order = c.getJSONArray(TAG_ORDER);
                            String name[]=new String[order.length()];
                            for (int j = 0; j < order.length(); j++) {
                                JSONObject c1 = order.getJSONObject(j);

                                name[j] = c1.getString(TAG_NAME);
                                Log.i("nasm", "" + name[j]);
                                names+=name[j]+"\n";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Static_Catelog.setStringProperty(context, "status", status);
                        //JSONObject order = c.getJSONObject(TAG_ORDER);

//						String mobile = order.getString(TAG_PHONE_MOBILE);
//						String home = phone.getString(TAG_PHONE_HOME);
//						String office = phone.getString(TAG_PHONE_OFFICE);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put(TAG_STATUS, status);
                        contact.put(TAG_NAME,names );
                        contact.put(TAG_Total, total);
                        contact.put(TAG_Order_Num, order_number);

                        // adding contact to contact list
                        contactList.add(contact);
                        names="";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    UserHistory.this, contactList,
                    R.layout.list_item, new String[] {TAG_STATUS,TAG_NAME,  TAG_Total,
                    TAG_Order_Num }, new int[] { R.id.status,R.id.name,
                    R.id.email, R.id.mobile });

            setListAdapter(adapter);
        }

    }

}