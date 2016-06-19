package co.jlabs.ordering;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import co.jlabs.ordering.Classes.Order_Pizza;


public class LastPage extends ListActivity {


    private static String address_url = "http://lannister-api.elasticbeanstalk.com/tyrion/address?vendor_id=1&email=";
    public static String email;
    public static String firstname;
    public static String lastname;
    private static final String TAG_DATA = "data";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_PIN = "pincode";
    private static final String TAG_AREA = "area";
    private static final String TAG_order_num = "order_number";
    ArrayList<HashMap<String, String>> address_lists;

    TextView wname;
    TextView adding_new_add;
    String numb,pini,adrsi,lndmrki;
    ScrollView prev_add;
    Button bt;
    OrderApplication app;
    Pizza_Objecto pra_test;
    CardView ord;
    EditText pincode,address,landmark,contact;
    TextInputLayout input_pincode,input_address,input_landmark,input_contact;
    LinearLayout det, adding_address;
    Context context;
    private ProgressDialog pDialog;
    JSONArray data = null;
    public static My_info myinfo;


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(android.R.style.Theme_Material_Light);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.address);
        prev_add = (ScrollView) findViewById(R.id.prev_add);
        ord = (CardView) findViewById(R.id.ord);
        address_lists = new ArrayList<HashMap<String, String>>();
        det=(LinearLayout)findViewById(R.id.det);
        adding_address = (LinearLayout) findViewById(R.id.adding_address);
//        savedadd_lin=(LinearLayout)findViewById(R.id.savedadd_lin);
//        address_list= (ListView) findViewById(R.id.address_list);


        pincode=(EditText)findViewById(R.id.pincode);
        address=(EditText)findViewById(R.id.address);
        landmark=(EditText)findViewById(R.id.landmark);
        contact=(EditText)findViewById(R.id.contact);

        input_pincode=(TextInputLayout)findViewById(R.id.input_pincode);
        input_address=(TextInputLayout)findViewById(R.id.input_address);
        input_landmark=(TextInputLayout)findViewById(R.id.input_landmark);
        input_contact=(TextInputLayout)findViewById(R.id.input_contact);
        wname=(TextView)findViewById(R.id.name);
        adding_new_add=(TextView)findViewById(R.id.adding_new_add);
        ord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adding_address.setVisibility(View.GONE);
                prev_add.setVisibility(View.VISIBLE);
            }
        });

        pra_test = new Pizza_Objecto();
        pra_test.obj= ((Pizza_Objecto) getIntent().getSerializableExtra("pradata")).obj;
        bt=(Button)findViewById(R.id.btn_signup);
        new GetAddress().execute();
        context = this;
        app = (OrderApplication) context.getApplicationContext();
        email=Static_Catelog.getStringProperty(context,"email");

        ArrayList<String> arr = new ArrayList<>();
        ArrayList<JSONObject> jsonObjects = Static_Catelog.loadJson(context);
        for(int i=0;i<jsonObjects.size();i++)
        {
            arr.add(" Hello "+jsonObjects.get(i).toString());
        }
//        address_list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arr));

        if(email==null)
        {
            Intent intent = getIntent();
            myinfo = new My_info();
            email = intent.getStringExtra("email");
            firstname = intent.getStringExtra("fname");
            lastname = intent.getStringExtra("lname");
            Static_Catelog.setStringProperty(context,"email",email);
            Static_Catelog.setStringProperty(context,"fname",firstname);
            Static_Catelog.setStringProperty(context,"lname",lastname);

        }
        else
        {
            email=  Static_Catelog.getStringProperty(context, "email");
            firstname=  Static_Catelog.getStringProperty(context, "fname");
            lastname= Static_Catelog.getStringProperty(context, "lname");


        }


        wname.setText("Welcome " + firstname + " " + lastname);


        contact.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        pincode.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        contact.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(contact.getWindowToken(), 0);
                }
                return false;
            }
        });


        //LISTVIEW LISTENER

        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pin2 = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();
                String phone2 = ((TextView) view.findViewById(R.id.status))
                        .getText().toString();
                String add2 = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String area2 = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();

                Static_Catelog.setStringProperty(context, "pin2", pin2);
                Static_Catelog.setStringProperty(context, "phone2", phone2);
                Static_Catelog.setStringProperty(context, "add2", add2);
                Static_Catelog.setStringProperty(context, "area2", area2);

                // Starting single contact activity
//                Intent in = new Intent(getApplicationContext(),
//                        SingleOrderActivity.class);
//                in.putExtra(TAG_NAME, name);
//                in.putExtra(TAG_Total, cost);
//                in.putExtra(TAG_Order_Num, description);
//                startActivity(in);


            }
        });



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phones = Static_Catelog.getStringProperty(context, "pin2");

                if ((validatecontact() && validatePin() && validateaddress() && validateArea()) || phones != "null") {
                    if (phones != "null") {


                        numb = Static_Catelog.getStringProperty(context, "phone2");
                        pini = Static_Catelog.getStringProperty(context, "pin2");
                        adrsi = Static_Catelog.getStringProperty(context, "add2");
                        lndmrki = Static_Catelog.getStringProperty(context, "area2");


                    } else {

                        numb = contact.getText().toString();
                        pini = pincode.getText().toString();
                        adrsi = address.getText().toString();
                        lndmrki = landmark.getText().toString();
                        Static_Catelog.setStringProperty(context, "pin2", "");
                        phones = Static_Catelog.getStringProperty(context, "pin2");
                    }
                }

                JSONObject tempjson = new JSONObject();
                try {
                    tempjson.put("pincode", pini);
                    tempjson.put("address", adrsi);
                    tempjson.put("area", lndmrki);
                    tempjson.put("phone", numb);
                    Static_Catelog.saveJson(context, tempjson);
                } catch (JSONException e) {

                }

                    ArrayList<String> arr = new ArrayList<>();
                    ArrayList<JSONObject> jsonObjects = Static_Catelog.loadJson(context);
                    for(int i=0;i<jsonObjects.size();i++)
                    {
                        arr.add(" " + jsonObjects.get(i).toString());
                    }
//                    address_list.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, android.R.id.text1,arr));

                    ArrayList<Order_Pizza> pizzas = app.getAllOrdersPizzas();
                    String s = "";
                    for (int i = 0; i < pizzas.size(); i++) {
                        Log.i("Myapp Order Pizza", "Pizza " + i + " " + pizzas.get(i).toJSON());
                        s = s + pizzas.get(i).toJSON();
                    }
                    JSONArray arr1 = new JSONArray();

                    for (int i = 0; i < pizzas.size(); i++) {
                        arr1.put(pizzas.get(i).toJSON());
                    }
                    JSONObject finalJson = new JSONObject();
                    try {
                        finalJson.put("order", arr1);
                        Log.i("Orderaa", "or" + arr1);
                        finalJson.put("vendor_id", 1);
                        finalJson.put("email", email);
                        finalJson.put("phone", numb);
                        finalJson.put("name", firstname+" "+lastname);
                        finalJson.put("area", lndmrki);
                        finalJson.put("address", adrsi+" "+pini);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                JSONArray addr = new JSONArray();
                JSONObject jsons = new JSONObject();
                try {
                    jsons.put("phone", numb);
                    jsons.put("area", lndmrki);
                    jsons.put("address", adrsi);
                    jsons.put("pincode", pini);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("address", "numb" + numb);

                addr.put(jsons);

                JSONObject addressJson = new JSONObject();
                try {
                    addressJson.put("vendor_id", 1);
                    addressJson.put("email", email);
                    addressJson.put("address", addr);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("address", "address" + addr);
//                if (phones.equals("null")) {
                    new AddressPost(addressJson).execute();


//                }

                    new OrderPizza(finalJson).execute();

                    Intent emailIntent;

                    emailIntent = new Intent(LastPage.this, OrderStatusLast.class);


                    startActivity(emailIntent);
                    finish();


            }
        });

    }

    private class GetAddress extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LastPage.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(address_url + email, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    data = jsonObj.getJSONArray(TAG_DATA);
                    Log.i("dats", "" + data);
                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        JSONArray c = data.getJSONArray(i);
                        for (int k = 0; k < c.length(); k++) {
                            JSONObject ads = c.getJSONObject(k);
                            String phn1 = ads.getString("phone");
                            String add1 = ads.getString("address");
                            String area1 = ads.getString("area");
                            String pin1 = ads.getString("pincode");
                            Static_Catelog.setStringProperty(context, "phone", phn1);

                            HashMap<String, String> address = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            address.put(TAG_ADDRESS, add1);
                            address.put(TAG_AREA, area1);
                            address.put(TAG_PHONE, phn1);
                            address.put(TAG_PIN, pin1);
                            address_lists.add(address);
                        }
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
                    LastPage.this, address_lists,
                    R.layout.list_item, new String[]{TAG_PHONE, TAG_ADDRESS, TAG_AREA,
                    TAG_PIN}, new int[]{R.id.status, R.id.name,
                    R.id.email, R.id.mobile});

            setListAdapter(adapter);
        }

    }

    private class AddressPost extends AsyncTask<String, Void, Void> {
        JSONObject object1;

        AddressPost(JSONObject obj) {
            object1 = obj;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONfunctions.makenewHttpRequest(context, "http://lannister-api.elasticbeanstalk.com/tyrion/address", object1);
            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            Toast.makeText(context, "address posted successfully", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onBackPressed() {
        backButtonHandler();
    }
    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                LastPage.this);
        // Setting Dialog Title
        alertDialog.setTitle("Leave application?");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to leave the application?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }


    private class OrderPizza extends AsyncTask<String,Void,Void>
    {
        JSONObject object;
        OrderPizza(JSONObject obj)
        {
            object=obj;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONObject obj=JSONfunctions.makenewHttpRequest(context, "http://lannister-api.elasticbeanstalk.com/tyrion/order", object);




            try {
                JSONObject c = obj.getJSONObject("data");
                Log.i("hipo","hipo"+c);
                String order_number = c.getString(TAG_order_num);
                Log.i("hipo","hipo1"+order_number);
                Static_Catelog.setStringProperty(context, "order_number",order_number);
            } catch (JSONException e) {
                e.printStackTrace();
            }








            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            Toast.makeText(context, "Pizza Ordered Successfully", Toast.LENGTH_LONG).show();
            app.getAllOrdersPizzas().clear();
        }
    }

    private boolean validatecontact() {
        String email = contact.getText().toString().trim();

        if (email.isEmpty() || !isValidNumber(email)) {
            input_contact.setError("Please enter Valid Contact Number");
            requestFocus(contact);
            return false;
        } else {
            input_contact.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateaddress() {
        String email = address.getText().toString().trim();

        if (email.isEmpty() ) {
            input_address.setError("Please enter Valid Address");
            requestFocus(address);
            return false;
        } else {
            input_address.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateArea() {
        String email = landmark.getText().toString().trim();

        if (email.isEmpty()) {
            input_landmark.setError("Please enter Valid Area");
            requestFocus(landmark);
            return false;
        } else {
            input_landmark.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePin() {
        String email = pincode.getText().toString().trim();

        if (email.isEmpty() || !isValidNumber(email)) {
            input_pincode.setError("Please enter valid Pin Code");
            requestFocus(pincode);
            return false;
        } else {
            input_pincode.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidNumber(String email) {
        return !TextUtils.isEmpty(email) && Patterns.PHONE.matcher(email).matches();
    }





}

