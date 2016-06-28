package co.jlabs.ordering;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import co.jlabs.ordering.Classes.Order_Pizza;
import co.jlabs.ordering.Interface.PriceTotal;
import co.jlabs.ordering.photoview.MaterialFontIcons;
import co.jlabs.ordering.photoview.MyTextView;

public class CheckOut extends Activity {
    OrderApplication app;
    Context context;
    ArrayList<Order_Pizza> pizzas;
    LazyAdapter adapter;
    String email;
    MyTextView tv, total_grand;
    RelativeLayout pi;
    MaterialFontIcons icon_back;
    PriceTotal PriceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        app = (OrderApplication) context.getApplicationContext();
        setContentView(R.layout.activity_check_out);
        pizzas = new ArrayList<>();
        email=Static_Catelog.getStringProperty(context,"email");
        ListView list = (ListView) findViewById(R.id.list_check);
        tv = (MyTextView) findViewById(R.id.login);
        total_grand = (MyTextView) findViewById(R.id.total_grand);
        icon_back = (MaterialFontIcons) findViewById(R.id.icon_back);
        pi=(RelativeLayout) findViewById(R.id.pi);
        pizzas = app.getAllOrdersPizzas();

        PriceInterface = new PriceTotal() {
            @Override
            public void onPriceChange(int position, float value) {
                if ((Float.parseFloat(total_grand.getText().toString()) + value) > 0) {
                    total_grand.setText("" + (Float.parseFloat(total_grand.getText().toString()) + value));
                } else total_grand.setText("Tech.Error");
            }
        };

        adapter = new LazyAdapter(this, pizzas, app.getMyPizza(), PriceInterface);
        list.setAdapter(adapter);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CheckOut.this, MainActivity.class);
                startActivity(intent1);
            }
        });



        if(email==null){

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Float.parseFloat(total_grand.getText().toString()) > 300) {
                        Pizza_Objecto pra_data = new Pizza_Objecto();
                        pra_data.obj = make_jsonobj(adapter).toString();
                        Intent emailIntent;
                        Static_Catelog.setStringProperty(context,"tota",total_grand.getText().toString());
                        emailIntent = new Intent(CheckOut.this, LoginActivity.class);

                        emailIntent.putExtra("pradata", pra_data);
                        startActivity(emailIntent);
                        finish();
                    } else
                        Toast.makeText(context, "Please make it more than ₹300", Toast.LENGTH_LONG).show();


                }
            });

        }
        else{

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Float.parseFloat(total_grand.getText().toString()) > 300) {
                        Pizza_Objecto pra_data = new Pizza_Objecto();
                        Static_Catelog.setStringProperty(context,"tota",total_grand.getText().toString());
                        pra_data.obj = make_jsonobj(adapter).toString();
                        Intent emailIntent;

                        emailIntent = new Intent(CheckOut.this, AddressFiller.class);

                        emailIntent.putExtra("pradata", pra_data);
                        startActivity(emailIntent);
                        finish();
                    } else
                        Toast.makeText(context, "Please make it more than ₹300", Toast.LENGTH_LONG).show();

                }
            });
        }

        total_grand.setText("" + app.totalprice() * 1.125);

        Log.i("Tracki", "T" + app.totalprice());
        Log.i("Trackin", "T" + app.totalprice() * 1.125);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public JSONObject make_jsonobj(LazyAdapter adapter)
    {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray =new JSONArray();
        ArrayList<JSONObject> obj= adapter.return_json();
        ArrayList<Integer> int_i= adapter.return_int();

        for(int i=0;i<obj.size();i++)
        {
            JSONObject obj1 = new JSONObject();
            try {
                obj1.put("quantity", int_i.get(i));
                obj1.put("pizza",obj.get(i));
                jsonArray.put(obj1);
            }
            catch (Exception e)
            {
            }
        }
        try {
            jsonObject.put("order", jsonArray);
            Log.i("jsonArray", "jsonArray" + jsonArray);
        }
        catch (Exception e)
        {

        }
        return jsonObject;
    }
}
