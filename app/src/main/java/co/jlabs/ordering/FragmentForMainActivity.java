package co.jlabs.ordering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desmond.parallaxviewpager.NotifyingScrollView;
import com.desmond.parallaxviewpager.ScrollViewFragment;

import java.util.List;

import co.jlabs.ordering.Classes.Menu_Signature;
import co.jlabs.ordering.Classes.MyPizza;
import co.jlabs.ordering.Classes.Order_Pizza;
import co.jlabs.ordering.Classes.Order_PizzaBuilder;


/**
 * Created by JussConnect on 6/23/2015.
 */
public class FragmentForMainActivity extends ScrollViewFragment {


    private static final String ARG_POSITION = "position";
    LayoutInflater inflater;
    MyPizza myPizza;
    Activity context;
    OrderApplication app;

    Context mContext;
    private  TextView add_des1;

    private CardView veg,nonveg ,ord_heise;

    public static Fragment newInstance(int position) {
        FragmentForMainActivity f = new FragmentForMainActivity();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View page=inflater.inflate(R.layout.pra_lay_adapter, container, false);
        mPosition = getArguments().getInt(ARG_POSITION);

        mScrollView = (NotifyingScrollView) page.findViewById(R.id.scrollview);
        setScrollViewOnScrollListener();

        LinearLayout lv1=(LinearLayout)page.findViewById(R.id.lv1);
        LinearLayout lv2=(LinearLayout)page.findViewById(R.id.lv2);
        TextView textveg=(TextView)page.findViewById(R.id.veg_text);
        View vi;
        if(myPizza.type_of_pizza.get(mPosition).subcats!=null&&myPizza.type_of_pizza.get(mPosition).subcats.size()>0)
        {
            for(Menu_Signature.Subcat subcat : myPizza.type_of_pizza.get(mPosition).subcats)
            for(int i=0;i<subcat.items.size();i++)
            {
                if(subcat.items.get(i).tags.contains("nveg"))
                {

                    lv2.setVisibility(View.VISIBLE);

                    vi = inflater.inflate(R.layout.internal_item, null);
                    TextView tv = (TextView)vi.findViewById(R.id.item);

                    final  TextView add_des1=(TextView)vi.findViewById(R.id.add_des1);

                    tv.setText(subcat.items.get(i).name);
                    TextView base = (TextView) vi.findViewById(R.id.base_price);
                    int item_price = subcat.items.get(i).price;
                    int size_price = 0;
                    try {
                        size_price = subcat.items.get(i).size.get(0).price;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (item_price > size_price) {
                        base.setText("Base Price: ₹" + item_price);
                    } else base.setText("Base Price: ₹" + size_price);

                    tv.setTag(mPosition);
                    vi.setTag(subcat.items.get(i).id);
                    vi.findViewById(R.id.subcat).setTag(subcat.id);
                    vi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order_Pizza.Item item = new Order_Pizza().new Item();
                            item.setItem((int) v.getTag(), 0);
                            int idss =(int)(v.findViewById(R.id.item).getTag());
                            app.pizza_id=idss;

                            app.start_new_pizzaBuilder(new Order_PizzaBuilder().withType(idss));
                            app.getCurrentPizzaBuilder().withSubcat((Integer) v.findViewById(R.id.subcat).getTag(),item,myPizza.type_of_pizza.get(idss).subcats.get((Integer) v.findViewById(R.id.subcat).getTag()).items.get((int) v.getTag()).custom.size());
                            if(myPizza.type_of_pizza.get(idss).subcats.get((Integer) v.findViewById(R.id.subcat).getTag()).items.get((int) v.getTag()).simple)
                            {
                                if ( add_des1.getText().equals("Add")) {
                                    app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).subcats.get((Integer) v.findViewById(R.id.subcat).getTag()).items.get((int) v.getTag()).price);
                                    app.order_current_pizza();
                                    add_des1.setBackgroundColor(Color.parseColor("#f26522"));
                                    add_des1.setText("Added");
//                                add_des1.setEnabled(false);
                                    add_des1.setTextColor(Color.parseColor("#ffffff"));
                                    ((MainActivity) context).updateUIforOrders();
                                }
                            }
                            else
                            {

                                app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).subcats.get((Integer) v.findViewById(R.id.subcat).getTag()).items.get((int) v.getTag()).price);
                                Log.i("Myapp","Hello+ "+app.getCurrentPizzaBuilder().getCurrentPizza().getTotalPrice());
                                Intent intent = new Intent(context, PopUp1st.class);
                                context.startActivityForResult(intent, 0);

                            }
                        }
                    });
                    lv2.addView(vi);
                }
                else
                {
                    lv1.setVisibility(View.VISIBLE);
                    if(mPosition==0){
                        vi = inflater.inflate(R.layout.internal_craft, null);
                        textveg.setVisibility(View.GONE);

                    }


                    else{
                        vi = inflater.inflate(R.layout.internal_item, null);
                        if (mPosition==3||mPosition==5||mPosition==6){
                            textveg.setVisibility(View.GONE);


                        }
                        TextView base = (TextView) vi.findViewById(R.id.base_price);

                        int item_price = subcat.items.get(i).price;
                        int size_price = 0;
                        try {
                            size_price = subcat.items.get(i).size.get(0).price;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (item_price > size_price) {
                            base.setText("Base Price: ₹" + item_price);
                        } else base.setText("Base Price: ₹" + size_price);

                    }
                    TextView tv = (TextView)vi.findViewById(R.id.item);
                    final  TextView add_des1=(TextView)vi.findViewById(R.id.add_des1);
                    tv.setText(subcat.items.get(i).name);
                    tv.setTag(mPosition);
                    vi.setTag(subcat.items.get(i).id);
                    vi.findViewById(R.id.subcat).setTag(subcat.id);
                    vi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int idss = (int) (v.findViewById(R.id.item).getTag());
                            app.pizza_id = idss;

                            Order_Pizza.Item item = new Order_Pizza().new Item();
                            item.setItem((int) v.getTag(), 0);
                            app.start_new_pizzaBuilder(new Order_PizzaBuilder().withType(idss));
                            app.getCurrentPizzaBuilder().withSubcat((Integer) v.findViewById(R.id.subcat).getTag(),item,myPizza.type_of_pizza.get(idss).subcats.get((Integer) v.findViewById(R.id.subcat).getTag()).items.get((int) v.getTag()).custom.size());
                            if (myPizza.type_of_pizza.get(idss).subcats.get((Integer) v.findViewById(R.id.subcat).getTag()).items.get((int) v.getTag()).simple) {
                                if (add_des1.getText().equals("Add")) {
                                    app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).subcats.get((Integer) v.findViewById(R.id.subcat).getTag()).items.get((int) v.getTag()).price);
                                    app.order_current_pizza();
                                    add_des1.setBackgroundColor(Color.parseColor("#f26522"));

                                    add_des1.setText("Added");

//                                add_des1.setEnabled(false);
                                    add_des1.setTextColor(Color.parseColor("#ffffff"));
                                    ((MainActivity) context).updateUIforOrders();
                                }

                            } else {
                                app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).subcats.get((Integer) v.findViewById(R.id.subcat).getTag()).items.get((int) v.getTag()).price);
                                Log.i("Myapp", "Hello+ " + app.getCurrentPizzaBuilder().getCurrentPizza().getTotalPrice());
                                Intent intent = new Intent(context, PopUp1st.class);
                                context.startActivityForResult(intent, 0);

                            }
                        }
                    });
                    lv1.addView(vi);
                }
            }
        }
        else if(myPizza.type_of_pizza.get(mPosition).items!=null&&myPizza.type_of_pizza.get(mPosition).items.size()>0)
        for(int i=0;i<myPizza.type_of_pizza.get(mPosition).items.size();i++)
        {
            if(myPizza.type_of_pizza.get(mPosition).items.get(i).tags.contains("nveg"))
            {

                lv2.setVisibility(View.VISIBLE);

                vi = inflater.inflate(R.layout.internal_item, null);
                TextView tv = (TextView)vi.findViewById(R.id.item);

                final  TextView add_des1=(TextView)vi.findViewById(R.id.add_des1);

                tv.setText(myPizza.type_of_pizza.get(mPosition).items.get(i).name);
                TextView base = (TextView) vi.findViewById(R.id.base_price);
                int item_price = myPizza.type_of_pizza.get(mPosition).items.get(i).price;
                int size_price = 0;
                try {
                    size_price = myPizza.type_of_pizza.get(mPosition).items.get(i).size.get(0).price;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (item_price > size_price) {
                    base.setText("Base Price: ₹" + item_price);
                } else base.setText("Base Price: ₹" + size_price);

                tv.setTag(mPosition);
                vi.setTag(myPizza.type_of_pizza.get(mPosition).items.get(i).id);
                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Order_Pizza.Item item = new Order_Pizza().new Item();
                        item.setItem((int) v.getTag(), 0);
                        int idss =(int)(v.findViewById(R.id.item).getTag());
                        app.pizza_id=idss;

                        app.start_new_pizzaBuilder(new Order_PizzaBuilder().withType(idss));
                        app.getCurrentPizzaBuilder().withItem(item,myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).custom.size());
                        if(myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).simple)
                        {
                            if ( add_des1.getText().equals("Add")) {
                                app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).price);
                                app.order_current_pizza();
                                add_des1.setBackgroundColor(Color.parseColor("#f26522"));
                                add_des1.setText("Added");
//                                add_des1.setEnabled(false);
                                add_des1.setTextColor(Color.parseColor("#ffffff"));
                                ((MainActivity) context).updateUIforOrders();
                            }
                        }
                        else
                        {

                            app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).price);
                            Log.i("Myapp","Hello+ "+app.getCurrentPizzaBuilder().getCurrentPizza().getTotalPrice());
                            Intent intent = new Intent(context, PopUp1st.class);
                            context.startActivityForResult(intent, 0);

                        }
                    }
                });
                lv2.addView(vi);
            }
            else
            {
                lv1.setVisibility(View.VISIBLE);
                if(mPosition==0){
                    vi = inflater.inflate(R.layout.internal_craft, null);
                    textveg.setVisibility(View.GONE);

                }


                else{
                    vi = inflater.inflate(R.layout.internal_item, null);
                    if (mPosition==3||mPosition==5||mPosition==6){
                        textveg.setVisibility(View.GONE);


                    }
                    TextView base = (TextView) vi.findViewById(R.id.base_price);

                    int item_price = myPizza.type_of_pizza.get(mPosition).items.get(i).price;
                    int size_price = 0;
                    try {
                        size_price = myPizza.type_of_pizza.get(mPosition).items.get(i).size.get(0).price;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (item_price > size_price) {
                        base.setText("Base Price: ₹" + item_price);
                    } else base.setText("Base Price: ₹" + size_price);

                }
                TextView tv = (TextView)vi.findViewById(R.id.item);
                final  TextView add_des1=(TextView)vi.findViewById(R.id.add_des1);
                tv.setText(myPizza.type_of_pizza.get(mPosition).items.get(i).name);
                tv.setTag(mPosition);
                vi.setTag(myPizza.type_of_pizza.get(mPosition).items.get(i).id);
                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int idss = (int) (v.findViewById(R.id.item).getTag());
                        app.pizza_id = idss;

                        Order_Pizza.Item item = new Order_Pizza().new Item();
                        item.setItem((int) v.getTag(), 0);
                        app.start_new_pizzaBuilder(new Order_PizzaBuilder().withType(idss));
                        app.getCurrentPizzaBuilder().withItem(item, myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).custom.size());
                        if (myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).simple) {
                             if (add_des1.getText().equals("Add")) {
                                 app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).price);
                                 app.order_current_pizza();
                                 add_des1.setBackgroundColor(Color.parseColor("#f26522"));

                                 add_des1.setText("Added");

//                                add_des1.setEnabled(false);
                                 add_des1.setTextColor(Color.parseColor("#ffffff"));
                                 ((MainActivity) context).updateUIforOrders();
                             }

                        } else {
                            app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).price);
                            Log.i("Myapp", "Hello+ " + app.getCurrentPizzaBuilder().getCurrentPizza().getTotalPrice());
                            Intent intent = new Intent(context, PopUp1st.class);
                            context.startActivityForResult(intent, 0);

                        }
                    }
                });
                lv1.addView(vi);
            }
        }

        return page;
    }
//
//    @Override
//    public void adjustScroll(int scrollHeight, int headerTranslationY)
//    {
//        mScrollView.setScrollY(headerTranslationY - scrollHeight);
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
        context=activity;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        app = (OrderApplication) mContext.getApplicationContext();
        myPizza=app.getMyPizza();
    }

}


