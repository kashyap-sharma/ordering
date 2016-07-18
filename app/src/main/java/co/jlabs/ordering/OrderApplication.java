package co.jlabs.ordering;

import android.app.Application;
import android.content.Context;

import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.jlabs.ordering.Classes.MyPizza;
import co.jlabs.ordering.Classes.Order_Pizza;
import co.jlabs.ordering.Classes.Order_PizzaBuilder;

public class OrderApplication extends android.app.Application {
    public int pizza_id;
    private ArrayList<Order_Pizza> pizzas;
    private Order_PizzaBuilder new_pizza;

    public static final String TAG = OrderApplication.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private static OrderApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized OrderApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public OrderApplication()
    {
        pizzas=new ArrayList<>();
    }


    public Order_PizzaBuilder getCurrentPizzaBuilder()
    {
        return new_pizza;
    }
    public void start_new_pizzaBuilder(Order_PizzaBuilder pizza)
    {
        this.new_pizza = pizza;
    }

    public void order_current_pizza()
    {
        pizzas.add(new_pizza.build());
    }

    public ArrayList<Order_Pizza> getAllOrdersPizzas()
    {
        return pizzas;
    }

    @Nullable
    public Order_Pizza getPizzaByidOrType(int id){
        for( int i=0;i<pizzas.size();i++)
            if(pizzas.get(i).getItem().getId()==id)
                return pizzas.get(i);
        return null;
    }

    public float totalprice(){
        float total_price=0;
        for(int i=0;i<pizzas.size();i++)
        {
            total_price=total_price+pizzas.get(i).getTotalPrice();
        }
        return total_price;
    }
    MyPizza pizza;

    public MyPizza getMyPizza()
    {
        if(pizza!=null)
        {
            return pizza;
        }
        else
        {
            JSONObject jsonObject;
            MyPizza myPizza;

            try {
                jsonObject=new JSONObject(Static_Catelog.getStringProperty(this,"jsonData"));
                jsonObject=jsonObject.getJSONObject("data");
                myPizza = new MyPizza(jsonObject);

            } catch (JSONException e) {
                myPizza = new MyPizza();
            }
            pizza=myPizza;
            return myPizza;
        }
    }


}