package co.jlabs.ordering.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.jlabs.ordering.Static_Catelog;


/**
 * Created by Wadi on 05-11-2015.
 */
public class Order_Pizza {

    private float totalPrice = 0;

    private Size size;
    private int quantity=1;
    private Item item;
    private int type;

    ArrayList<ArrayList<Integer>> customs;


    public class Size{
        private int id;
        private float price;
        public void setsize(int id,float price)
        {
            this.id=id;
            this.price=price;
        }
        public int getid()
        {
            return id;
        }
        public float getPrice(){

            return price;
        }
    }

    public class Item{
        private int id;
        private float price;
        public void setItem(int id,float price)
        {
            this.id=id;
            this.price=price;
        }
        public int getId()
        {
            return id;
        }
        public float getPrice(){

            return price;
        }
    }


    public Size getSize() {

        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    public float getTotalPrice() {
        return totalPrice;
    }

    public void addToPrice(float price) {
        this.totalPrice = totalPrice + price;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setItem(Item item,int Custom_Size) {
        this.item = item;
        customs = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> nodeList;
        for(int i=0;i<Custom_Size;i++)
        {
            nodeList = new ArrayList<Integer>();
            customs.add(nodeList);
        }

    }

    public Item getItem() {
        return item;
    }


    public void setQuantity(int q) {
        this.quantity = q;
    }
    public int getQuantity() {
        return quantity;
    }

    public JSONObject toJSON()
    {
        JSONObject jsonObject = new JSONObject();

        try {
            try {
                jsonObject.put("size", getSize().getid());
            }
            catch (Exception e)
            {

            }
            jsonObject.put("Price",totalPrice);
            try {
                jsonObject.put("category",getType());
            } catch (JSONException e) {

            }
            jsonObject.put("item",getItem().getId());
            jsonObject.put("qty",quantity);
            JSONArray jsonArray_out =new JSONArray();
            JSONArray jsonArray_in;
          //  String s="";
            for(int i=0;i<customs.size();i++)
            {
                jsonArray_in=new JSONArray();
                for(int k=0;k<customs.get(i).size();k++)
                {
                        jsonArray_in.put(Integer.valueOf(customs.get(i).get(k)));
                }
                try {
                    Static_Catelog.log("Out " + jsonArray_in);
                    jsonArray_out.put(jsonArray_in);
                } catch (Exception e) {

                }
            }
            jsonObject.put("custom",jsonArray_out);

        } catch (JSONException e) {

        }
        return jsonObject;
    }


    public void setCustoms(int arr_pos,ArrayList<Integer> arr) {
        customs.get(arr_pos).clear();
        customs.set(arr_pos,arr);
    }
}