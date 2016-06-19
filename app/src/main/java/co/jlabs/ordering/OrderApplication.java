package co.jlabs.ordering;

import android.app.Application;

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