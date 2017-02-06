package co.jlabs.ordering.Classes;

import java.util.ArrayList;

/**
 * Created by Wadi on 05-11-2015.
 */


public class Order_PizzaBuilder {

        Order_Pizza pizza = new Order_Pizza();



    public Order_PizzaBuilder withType(int type) {
        pizza.setType(type);
        return this;
    }
    public Order_PizzaBuilder withItem(Order_Pizza.Item item,int Custom_Size) {
        pizza.setItem(item, Custom_Size);
        return this;
    }

    public Order_PizzaBuilder withSubcat(int id,Order_Pizza.Item item,int Custom_Size)
    {
        pizza.setSubcat(id);
        pizza.setItem(item, Custom_Size);
        return this;
    }

    public Order_PizzaBuilder withSize(Order_Pizza.Size size) {
        if(pizza.getSize()==null)
        {
            pizza.addToPrice(size.getPrice());
        }
        else
        {
            pizza.addToPrice(size.getPrice() - pizza.getSize().getPrice());
        }
        pizza.setSize(size);
        return this;
    }

 /*   public Order_PizzaBuilder withTopping(Order_Pizza.Topping topping) {
        pizza.setTopping(topping);
        pizza.addToPrice(topping.getCost());
        return this;
    }
    public Order_PizzaBuilder withCrust(Order_Pizza.Crust crust) {
        pizza.setCrust(crust);
        pizza.addToPrice(crust.getCost());
        return this;
    }

*/

    public Order_Pizza getCurrentPizza()
    {
        return pizza;
    }


    public Order_PizzaBuilder withCustom(int arr_pos, ArrayList<Integer> arr, float price) {
        pizza.setCustoms(arr_pos,arr);
        pizza.addToPrice(price);
        return this;
    }


    public Order_Pizza build() {
            return pizza;
        }

    public double calculatePrice() {
            return pizza.getTotalPrice();
        }

    }