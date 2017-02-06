package co.jlabs.ordering.Classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kashyap on 11/3/2015.
 */
public class MyPizza {
    public int vendor_id;
    public int version;



    public ArrayList<Menu_Signature> type_of_pizza;

    public MyPizza(JSONObject jsonObject)
    {
        type_of_pizza=new ArrayList<>();
        try{
            vendor_id = jsonObject.getInt("vendor_id");
            version= jsonObject.getInt("version");
        }
        catch (Exception e)
        {
            Log.i("Myapp", "Mypizza 1");
        }
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray=jsonObject.getJSONArray("menu");

        } catch (JSONException e) {
            Log.i("Myapp", "Mypizza 2");
        }

        Menu_Signature menu_signature;

        for (int i=0;i<jsonArray.length();i++)
        {
            try {
                menu_signature = new Menu_Signature((JSONObject) jsonArray.get(i));
                type_of_pizza.add(menu_signature);
            }catch (Exception e)
            {
                Log.i("Myapp","PraError"+i);
            }
            Log.e("Myapp","----------------------*"+i+"*----------------------");
        }
        {

        }


    }
    public MyPizza()
    {

    }

}
