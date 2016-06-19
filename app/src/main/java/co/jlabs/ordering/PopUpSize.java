package co.jlabs.ordering;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import co.jlabs.ordering.Classes.MyPizza;
import co.jlabs.ordering.Classes.Order_Pizza;

/**
 * Created by Kashyap on 10/6/2015.
 */
public class PopUpSize extends Activity {

    MyPizza myPizza;
    int id;
    int size_id;
    OrderApplication app;
    int pizza_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_size);

        app = (OrderApplication) getApplicationContext();
        myPizza=app.getMyPizza();
        pizza_id=app.pizza_id;
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        id=app.getCurrentPizzaBuilder().getCurrentPizza().getItem().getId();
        RelativeLayout rl;
        final LinearLayout lv= (LinearLayout) findViewById(R.id.size);
        for(int i=0;i<myPizza.type_of_pizza.get(pizza_id).items.get(id).size.size();i++)
        {
            rl = (RelativeLayout) layoutInflater.inflate(R.layout.size_popup,null, false);
            ((TextView)rl.findViewById(R.id.show_text)).setText(myPizza.type_of_pizza.get(pizza_id).items.get(id).size.get(i).name);
            ((TextView)rl.findViewById(R.id.price_text)).setText(""+myPizza.type_of_pizza.get(pizza_id).items.get(id).size.get(i).price);
            rl.setTag(myPizza.type_of_pizza.get(pizza_id).items.get(id).size.get(i).id);
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<View> views= new ArrayList<>();
                    lv.findViewsWithText(views, "hello", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                    for(int i=0;i<views.size();i++)
                    {
                        views.get(i).setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                    v.setBackgroundColor(Color.parseColor("#dddddd"));
                    size_id=(int)v.getTag();
                }
            });
            lv.addView(rl);

        }

        Button close=(Button)findViewById(R.id.cancel);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button confirm=(Button)findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Order_Pizza.Size size = new Order_Pizza().new Size();
                size.setsize(size_id,myPizza.type_of_pizza.get(pizza_id).items.get(id).getSizeChildId(size_id).price);
                app.getCurrentPizzaBuilder().withSize(size);
                setResult(1);
                finish();
            }
        });

    }

}
