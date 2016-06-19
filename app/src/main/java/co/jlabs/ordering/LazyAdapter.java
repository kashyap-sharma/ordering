package co.jlabs.ordering;

/**
 * Created by Kashyap on 9/30/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.jlabs.ordering.Classes.MyPizza;
import co.jlabs.ordering.Classes.Order_Pizza;
import co.jlabs.ordering.Interface.PriceTotal;
import co.jlabs.ordering.photoview.MaterialFontIcons;

public class LazyAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<Order_Pizza> data;
    private static LayoutInflater inflater=null;
    ArrayList<Integer> info_i;
    Context c;
    OrderApplication app;
    MyPizza m;
    ArrayList<JSONObject> obj;
    MyPizza myPizza;
    LazyAdapter adapter;
    PriceTotal PriceInterface;

    public LazyAdapter(Context a, ArrayList<Order_Pizza> d, MyPizza myPizza, PriceTotal myInterface) {
        activity = a;
        data=d;
        m=myPizza;
        this.c=a;
        obj=new ArrayList<>();
        this.PriceInterface = myInterface;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        info_i=new ArrayList<>();
        for(int i=0;i<d.size();i++)
        {
            info_i.add(1);
        }
        adapter=this;
      //  imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.check_listview, null);
        Log.i("Tracks", "T" + position);
        app = (OrderApplication) c.getApplicationContext();
        TextView orderedItem = (TextView)vi.findViewById(R.id.orderedItem);
        TextView itemDetail = (TextView)vi.findViewById(R.id.itemDetail);
        TextView itemDetail1 = (TextView) vi.findViewById(R.id.itemDetail1);
        final TextView itemDetail2 = (TextView) vi.findViewById(R.id.itemDetail2);
        final TextView tax = (TextView) vi.findViewById(R.id.mone1);
        final TextView item_total = (TextView) vi.findViewById(R.id.mone2);
        MaterialFontIcons rem_des1 = (MaterialFontIcons)vi.findViewById(R.id.rem_des1);
       final TextView totaldes1 = (TextView)vi.findViewById(R.id.totaldes1);
        MaterialFontIcons add_des1=(MaterialFontIcons)vi.findViewById(R.id.add_des1); //
        // Setting all values in listview
        int type=data.get(position).getType();

        int subtype;

        // itemDetail2.setText("₹ " + money*data.get(position).getQuantity() + "+Tax");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
        }
        catch (Exception e)
        {

        }
        String q = "";
        String q1 = "";

        JSONObject jsonObj1 = data.get(position).toJSON();

        try {
            JSONArray custom = jsonObj1.getJSONArray("custom");
            for (int i = 0; i < custom.length(); i++) {
                JSONArray custom_inside_array = (JSONArray) custom.get(i);

                for (int k = 0; k < custom_inside_array.length(); k++) {
                    String s = m.type_of_pizza.get(data.get(position).getType()).items.get(data.get(position).getItem().getId()).custom.get(i).options.get(k).name;
                    itemDetail1.setText("" + " " + s + "\n");
                }

            }
        } catch (JSONException e) {

        }

        try {
            final int money = jsonObj1.getInt("Price");
            // itemDetail2.setText("₹ " + money * data.get(position).getQuantity() + "+Tax");
            itemDetail2.setText("" + money * data.get(position).getQuantity());
            tax.setText("" + (money * data.get(position).getQuantity()) * 0.125);
            item_total.setText("" + (money * data.get(position).getQuantity()) * 1.125);
//            int p=m.type_of_pizza.get(data.get(position).)
            add_des1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //info_i.set(position,info_i.get(position)+1);
                    // String s = String.valueOf(i);
                    // totaldes1.setText("" + info_i.get(position));
                    data.get(position).setQuantity(data.get(position).getQuantity() + 1);
                    totaldes1.setText("" + data.get(position).getQuantity());
                    itemDetail2.setText("" + money * data.get(position).getQuantity());
                    int m = money * data.get(position).getQuantity();
                    tax.setText("" + (money * data.get(position).getQuantity()) * 0.125);
                    double t = (money * data.get(position).getQuantity()) * 0.125;
                    item_total.setText("" + (m + t));
//                    itemDetail2.setText("₹ " + money * data.get(position).getQuantity() + "+Tax");
                    PriceInterface.onPriceChange(position, money * 1.125f);
                }
            });
            rem_des1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.get(position).setQuantity(data.get(position).getQuantity() - 1);
                    if (data.get(position).getQuantity() > 0) {
                        totaldes1.setText("" + data.get(position).getQuantity());
                        itemDetail2.setText("" + money * data.get(position).getQuantity());
                        int m = money * data.get(position).getQuantity();
                        tax.setText("" + (money * data.get(position).getQuantity()) * 0.125);
                        double t = (money * data.get(position).getQuantity()) * 0.125;
                        item_total.setText("" + (m + t));

//                        itemDetail2.setText("₹ " + money * data.get(position).getQuantity() + "+Tax");
                    } else {
                        data.remove(position);
                        adapter.notifyDataSetChanged();
                        if (data.size() == 0) {
                            c.startActivity(new Intent(c, MainActivity.class));
                        }

                    }
                    PriceInterface.onPriceChange(position, -money * 1.125f);

                }
            });


        } catch (JSONException e) {

        }
//
//        try {
//            String quant = jsonObj1.getString("qty");
//            String category = jsonObj1.getString("category");
//            int price = jsonObj1.getInt("price");
//
//            try {
//                int size = jsonObj1.getInt("size");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } catch (JSONException e) {
//
//        }


        String datum = Static_Catelog.getStringProperty(c, "order_details");
        try {
            JSONObject jsonObj = new JSONObject(datum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //myPizza.type_of_pizza.get(position).category
           String s=m.type_of_pizza.get(data.get(position).getType()).items.get(data.get(position).getItem().getId()).name;
        String s1 = m.type_of_pizza.get(data.get(position).getType()).items.get(data.get(position).getItem().getId()).name;
            Log.i("log_pos",""+position);
        String d= null;
        try {
            d = m.type_of_pizza.get(data.get(position).getType()).items.get(data.get(position).getItem().getId()).desc;
        } catch (Exception e) {

        }

        JSONObject obj1 = new JSONObject();
            try {
                obj1.put("name", s);
                obj1.put("size", data.get(position).getSize());



                jsonObject.put("data",obj1);
            }
            catch (Exception e)
            {

            }


        orderedItem.setText(s);
        // itemDetail.setText(d);
        obj.add(jsonObject);


        return vi;
    }

    public ArrayList<JSONObject> return_json()
    {

        return obj;
    }
    public ArrayList<Integer> return_int()
    {
        return info_i;
    }


}