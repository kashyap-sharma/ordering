package co.jlabs.ordering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.jlabs.ordering.Classes.MyPizza;

/**
 * Created by Kashyap on 10/6/2015.
 */
public class PopUpCustomization extends Activity {

    MyPizza myPizza;
    int id;
    int array_pos;
    OrderApplication app;
    Context context;
    Button confirmed;
    ArrayList<Boolean> array;
    int numselected;
    int min,max,soft;
    TextView txt_min, txt_max, txt_selected, softs, veg, nveg;
    float price;
    int pizza_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custom);
        context=this;
        app = (OrderApplication) getApplicationContext();
        confirmed = (Button)findViewById(R.id.confirmed);
        myPizza=app.getMyPizza();
        pizza_id=app.pizza_id;
        array_pos=getIntent().getIntExtra("array_pos", 0);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        id=app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId();
        min=myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).min;
        max=myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).max;
        soft=myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).soft;

        txt_min= (TextView) findViewById(R.id.min);
        softs = (TextView) findViewById(R.id.soft);
        txt_max= (TextView) findViewById(R.id.max);
        txt_selected= (TextView) findViewById(R.id.selected);
        veg= (TextView) findViewById(R.id.veg);
        nveg= (TextView) findViewById(R.id.nveg);
        veg.setVisibility(View.GONE);
        nveg.setVisibility(View.GONE);
        txt_min.setText("" + min);
        txt_max.setText(""+max);
        softs.setText("" + soft);

        LinearLayout lv1=(LinearLayout)findViewById(R.id.lv1);
        LinearLayout lv2=(LinearLayout)findViewById(R.id.lv2);
        array=new ArrayList<>();
        View vi;
        numselected=0;
        price=0;
        ((TextView)findViewById(R.id.title)).setText(myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).name);

        for(int k=0;k<myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).options.size();k++)
        {
            if(myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).options.get(k).tags.contains("nveg"))
            {
                veg.setVisibility(View.VISIBLE);
                nveg.setVisibility(View.VISIBLE);
                vi = inflater.inflate(R.layout.internal_item, null);
                TextView tv = (TextView)vi.findViewById(R.id.item);
                TextView b = (TextView) vi.findViewById(R.id.add_des1);
                tv.setText(myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).options.get(k).name);
                b.setTag(myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).options.get(k).id);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(max==0) {
                            FlipView(v, !array.get((int) v.getTag()));
                            array.set((int) v.getTag(), !array.get((int) v.getTag()));
                        }
                        else
                        {
                            if(max<=numselected)
                            {
                                Toast.makeText(context,"Sorry!!! you can select only "+max+" with this customization.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                FlipView(v, !array.get((int) v.getTag()));
                                array.set((int) v.getTag(), !array.get((int) v.getTag()));
                            }
                        }
                    }
                });
                lv2.addView(vi);
            }
            else
            {
                vi = inflater.inflate(R.layout.internal_item, null);
                TextView tv = (TextView)vi.findViewById(R.id.item);
                TextView b = (TextView) vi.findViewById(R.id.add_des1);
                tv.setText(myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).options.get(k).name);
                b.setTag(myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).options.get(k).id);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (max == 0) {
                            FlipView(v, !array.get((int) v.getTag()));
                            array.set((int) v.getTag(), !array.get((int) v.getTag()));
                        }
                        else
                        {
                            if (max <= numselected) {
                                Toast.makeText(context, "Sorry Max No. of Toppings Selected", Toast.LENGTH_SHORT).show();
                            } else {
                                FlipView(v, !array.get((int) v.getTag()));
                                array.set((int) v.getTag(), !array.get((int) v.getTag()));
                            }
                        }
                    }
                });
                lv1.addView(vi);
            }
            array.add(false);
        }
        Button close=(Button)findViewById(R.id.cancel);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(min!=0)
                {
                    int numselected=0;
                    for(int i=0;i<array.size();i++)
                    {
                        if(array.get(i))
                        {
                            numselected++;
                        }
                    }
                    if(numselected<min)
                    {
                        Toast.makeText(context,"Please Select min no. of toppings", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        app.getCurrentPizzaBuilder().withCustom(array_pos, print_selected(), price);
                        Intent i = new Intent(PopUpCustomization.this, PopUp1st.class);
                        i.putExtra("Price", price);
                        //setIntent(i);
                        setResult(100 + array_pos, i);
                        finish();
                    }
                }
                else
                {
                    app.getCurrentPizzaBuilder().withCustom(array_pos, print_selected(), price);

                    Intent i = new Intent(PopUpCustomization.this, PopUp1st.class);
                    i.putExtra("Price", price);
                    //  setIntent(i);
                    setResult(100 + array_pos, i);
                    finish();
                }

            }
        });
    }
    public void FlipView(View view, boolean b)
    {
        TextView v= (TextView) view;
        if(b)
        {
            v.setText("Added");
            numselected++;
        }
        else
        {
            v.setText("Add");
            numselected--;
        }
        txt_selected.setText(""+numselected);
        if (numselected > max) {

        }
    }

    public ArrayList<Integer> print_selected()
    {
        int x = numselected - soft;

        ArrayList<Integer> arr=new ArrayList<>();
        for (int i=0;i<array.size();i++)
        {
            if(array.get(i))
            {
                arr.add(i);
                if (x > 0) {
                    price = price + myPizza.type_of_pizza.get(pizza_id).items.get(id).custom.get(array_pos).options.get(i).price;
                    x--;
                }
            }

        }
        return arr;
    }
}
