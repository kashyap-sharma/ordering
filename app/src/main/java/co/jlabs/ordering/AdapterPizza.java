package co.jlabs.ordering;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.jlabs.ordering.Classes.MyPizza;
import co.jlabs.ordering.Classes.Order_Pizza;
import co.jlabs.ordering.Classes.Order_PizzaBuilder;

/**
 * Created by kashyap on 6/23/2015.
 */
public class AdapterPizza extends PagerAdapter {

    LayoutInflater inflater;
    MyPizza myPizza;
    Activity context;
    OrderApplication app;

    public AdapterPizza(Activity context, LayoutInflater inflater, OrderApplication app, MyPizza myPizza) {
        this.context=context;
        this.inflater=inflater;
        this.myPizza=myPizza;
        this.app=app;
}

    @Override
    public Object instantiateItem(ViewGroup container, int pizza_id) {
        View page=inflater.inflate(R.layout.pra_lay_adapter, container, false);
        LinearLayout lv1=(LinearLayout)page.findViewById(R.id.lv1);
        LinearLayout lv2=(LinearLayout)page.findViewById(R.id.lv2);
        View vi;
        for(int i=0;i<myPizza.type_of_pizza.get(pizza_id).items.size();i++)
        {
            if(myPizza.type_of_pizza.get(pizza_id).items.get(i).tags.contains("nveg"))
            {
                lv2.setVisibility(View.VISIBLE);
                vi = inflater.inflate(R.layout.internal_item, null);
                TextView tv = (TextView)vi.findViewById(R.id.item);
                tv.setText(myPizza.type_of_pizza.get(pizza_id).items.get(i).name);
                tv.setTag(pizza_id);
                vi.setTag(myPizza.type_of_pizza.get(pizza_id).items.get(i).id);
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
                            app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).price);
                            app.order_current_pizza();
                            ((MainActivity) context).updateUIforOrders();
                        }
                        else
                        {
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
                vi = inflater.inflate(R.layout.internal_item, null);
                TextView tv = (TextView)vi.findViewById(R.id.item);
                tv.setText(myPizza.type_of_pizza.get(pizza_id).items.get(i).name);
                tv.setTag(pizza_id);
                vi.setTag(myPizza.type_of_pizza.get(pizza_id).items.get(i).id);
                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int idss =(int)(v.findViewById(R.id.item).getTag());
                        app.pizza_id=idss;

                        Order_Pizza.Item item = new Order_Pizza().new Item();
                        item.setItem((int)v.getTag(),0);
                        app.start_new_pizzaBuilder(new Order_PizzaBuilder().withType(idss));
                        app.getCurrentPizzaBuilder().withItem(item,myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).custom.size());
                        if(myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).simple)
                        {
                            app.getCurrentPizzaBuilder().getCurrentPizza().addToPrice(myPizza.type_of_pizza.get(idss).items.get((int) v.getTag()).price);
                            app.order_current_pizza();

                            ((MainActivity) context).updateUIforOrders();
                        }
                        else
                        {
                            Intent intent = new Intent(context, PopUp1st.class);
                            context.startActivityForResult(intent, 0);
                        }
                    }
                });
                lv1.addView(vi);
            }
        }


        container.addView(page);
        return(page);
    }

    @Override
    public void destroyItem(ViewGroup container, int pizza_id,
                            Object object) {
        container.removeView((View) object);
    }
    @Override
             public CharSequence getPageTitle(int position) {
        return myPizza.type_of_pizza.get(position).category;
    }

    @Override
    public int getCount() {
        return myPizza.type_of_pizza.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return(view == object);
    }
}


