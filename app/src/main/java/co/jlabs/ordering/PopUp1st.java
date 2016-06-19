package co.jlabs.ordering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.jlabs.ordering.Classes.Menu_Signature;
import co.jlabs.ordering.Classes.MyPizza;

/**
 * Created by Kashyap on 10/6/2015.
 */
public class PopUp1st extends Activity {

    Button chngSize,confirmed;
    TextView txt_money,txt_size;
    OrderApplication app;
    Boolean size_selected;
    Boolean msel = false;

    Context context;
    MyPizza myPizza;
    Menu_Signature.Items item;
    LinearLayout custom_layout;
    ArrayList<TextView> buttons;
    TextView price_text;
    LinearLayout lay_price;
    int pizza_id;
    CardView sizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signature_inside);
        app = (OrderApplication) getApplicationContext();
        confirmed = (Button)findViewById(R.id.confirmed);
        price_text= (TextView) findViewById(R.id.price_text);
        lay_price= (LinearLayout) findViewById(R.id.lay_price);
        pizza_id=app.pizza_id;
        size_selected=false;

        buttons=new ArrayList<>();
        context=this;
        TextView title=(TextView)findViewById(R.id.title);
        myPizza = app.getMyPizza();
        item=myPizza.type_of_pizza.get(pizza_id).items.get(app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId());

        title.setText(myPizza.type_of_pizza.get(pizza_id).items.get(app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId()).name);

        chngSize = (Button)findViewById(R.id.changesize);
        sizer=(CardView)findViewById(R.id.sizer);
        txt_money=(TextView)findViewById(R.id.money);
        txt_size=(TextView)findViewById(R.id.regu);
        custom_layout= (LinearLayout) findViewById(R.id.custom_layout);

        if (myPizza.type_of_pizza.get(pizza_id).items.get(app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId()).size.size()>0) {
            chngSize.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent i=new Intent(PopUp1st.this,PopUpSize.class);
                    startActivityForResult(i,1);
                }
            });
        }
        else sizer.setVisibility(View.GONE);
        setcontent(this,item);
        confirmed.setEnabled(false);
        confirmed.setTextColor(Color.parseColor("#000000"));
        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size_selected || myPizza.type_of_pizza.get(pizza_id).items.get(app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId()).size.size() == 0 || !msel) {
                    app.order_current_pizza();

                    setResult(0);
                    finish();

                } else {
                    Toast.makeText(context, "Please Select Required Categories first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button close=(Button)findViewById(R.id.cancel);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1)
        {
            size_selected=true;
            setSizeLayout(app.getCurrentPizzaBuilder().getCurrentPizza().getSize().getid());
            lay_price.setVisibility(View.VISIBLE);
            price_text.setText("Rs. " + app.getCurrentPizzaBuilder().getCurrentPizza().getTotalPrice() + "/-");
        }
        if(resultCode>=100)
        {
            float prev_price = data.getFloatExtra("Price", 0);
            buttons.get(resultCode-100).setText("Replace");
            Log.i("qwerty", "" + (resultCode - 100));
            lay_price.setVisibility(View.VISIBLE);
            price_text.setText("Rs. " + app.getCurrentPizzaBuilder().getCurrentPizza().getTotalPrice() + "/-");
            buttons.get(resultCode - 100).setTag(R.string.booleans, true);
            buttons.get(resultCode - 100).setTag(R.string.floats, prev_price);
            if (checkallfortrue()) {
                confirmed.setEnabled(true);
                confirmed.setTextColor(Color.parseColor("#A92323"));
            }


        }

    }

    public void setSizeLayout(int size)
    {
        txt_money.setText("" + myPizza.type_of_pizza.get(pizza_id).items.get(app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId()).getSizeChildId(size).price);
        txt_size.setText(myPizza.type_of_pizza.get(pizza_id).items.get(app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId()).getSizeChildId(size).name);
    }

    private void setcontent(Context context, Menu_Signature.Items item){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout;
        TextView custom_text;
        Button custom_button;

        for(int i=0;i<item.custom.size();i++) {
            layout = (RelativeLayout) (inflater).inflate(R.layout.adap_custom, null);
            custom_text = (TextView) layout.findViewById(R.id.Custom_title);
            custom_button = (Button) layout.findViewById(R.id.AddButton);
            if (myPizza.type_of_pizza.get(pizza_id).items.get(app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId()).custom.get(i).min > 0) {
                custom_button.setBackgroundColor(Color.parseColor("#000000"));
                custom_button.setText("Options");
                custom_button.setTag(R.string.booleans, false);
            } else {
                custom_button.setTag(R.string.booleans, true);
            }
            custom_text.setText(item.custom.get(i).name);
            custom_button.setTag(R.string.integers, i);
            String mn = custom_button.getText().toString();
            Log.i("colors", "colors" + mn);
            if (mn.equals("Options")) {
                msel = true;

            }
            custom_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(PopUp1st.this, PopUpCustomization.class);
                    i.putExtra("array_pos", (int) v.getTag(R.string.integers));
                    if (v.getTag() != null)
                        app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(-Float.parseFloat(v.getTag().toString()));
                    startActivityForResult(i, 100 + (int) v.getTag(R.string.integers));
                }
            });
            buttons.add(custom_button);
            custom_layout.addView(layout);

        }
    }

    public boolean checkallfortrue() {
        for (int i = 0; i < buttons.size(); i++) {
            if (!((Boolean) buttons.get(i).getTag(R.string.booleans))) {
                return false;
            }
        }
        return true;
    }

}
