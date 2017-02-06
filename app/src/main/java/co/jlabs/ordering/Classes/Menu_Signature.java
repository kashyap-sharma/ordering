package co.jlabs.ordering.Classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kashyap on 11/3/2015.
 */
public class Menu_Signature {
    public List<Items> items;
    public int id;
    public List<Subcat> subcats;
    public String category;

    public class Subcat
    {
        public List<Items> items;
        public int id;
        public String subcat;
        Subcat(JSONObject jsonObject)
        {
            try {
                id=jsonObject.getInt("id");
                subcat=jsonObject.getString("subcat");
                items = new ArrayList<>();
                JSONArray jsonArray = new JSONArray();
                jsonArray=jsonObject.getJSONArray("items");
                Items item;
                for(int i=0;i<jsonArray.length();i++)
                {
                    try {
                        item=new Items((JSONObject)jsonArray.get(i));
                        items.add(item);
                    } catch (Exception e) {
                        Log.i("Myapp", "Menu_signature Inside");
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class Option
    {
        public int price;
        public List<String> tags;
        public String name;
        public int id;
        Option(JSONObject jsonObject)
        {
            tags=new ArrayList<>();
            try {
                id = jsonObject.getInt("id");
                name = jsonObject.getString("name");
                price = jsonObject.getInt("price");
                try {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = jsonObject.getJSONArray("tags");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            tags.add(jsonArray.get(i).toString());
                        } catch (JSONException e) {
                            Log.i("Myapp", "Menu_signature Tags inside");
                        }
                    }
                }
                catch (Exception e)
                {
                    Log.i("Myapp", "Menu_signature TAGS oUTSIDE");
                }
            }
            catch (Exception e)
            {
                Log.i("Myapp", "Menu_signature oPTIONS id name price " + jsonObject.toString());

            }
        }
    }

    public class Custom
    {
        public int max;
        public int min;
        public List<Option> options;
        public String name;
        public int soft;
        Custom(JSONObject jsonObject)
        {
            options=new ArrayList<>();
            try {
                min = jsonObject.getInt("min");
                max = jsonObject.getInt("max");
                soft = jsonObject.getInt("soft");
                name = jsonObject.getString("name");

                JSONArray arr_options =new JSONArray();
                try {
                    arr_options=jsonObject.getJSONArray("options");
                    Option temp_option;
                    JSONObject temp_json_object =new JSONObject();
                    for(int k =0;k<arr_options.length();k++)
                    {
                        try {
                            temp_json_object=(JSONObject)arr_options.get(k);
                            temp_option=new Option(temp_json_object);
                            options.add(temp_option);
                        } catch (JSONException e) {
                            Log.i("Myapp", "Menu_signature Options inside");
                        }

                    }
                } catch (JSONException e) {
                    Log.i("Myapp", "Menu_signature Options oUTSIDE");
                }

            } catch (JSONException e) {
                Log.i("Myapp", "Menu_signature Custom min max soft name");
            }

        }
    }

    public class Size
    {
        public int price;
        public String name;
        public int id;
        Size(JSONObject jsonObject)
        {
            try {
                id = jsonObject.getInt("id");
                name = jsonObject.getString("name");
                price = jsonObject.getInt("price");
            }
            catch (Exception e)
            {
                Log.i("Myapp", "Menu_signature Size id name price");
            }
        }
    }

    public class Items
    {
        public List<String> tags;
        public int id;
        public List<Custom> custom;
        public boolean simple;
        public String desc;
        public String name;
        public List<Size> size;
        public int price;
        public boolean size_is_required=true;
        Items(JSONObject jsonObject)
        {
            tags=new ArrayList<>();
            custom=new ArrayList<>();
            size=new ArrayList<>();
            try {
                id=jsonObject.getInt("id");
                simple=jsonObject.getBoolean("simple");
                try {
                    desc = jsonObject.getString("desc");
                }
                catch (Exception e)
                {
                    //desc = "****No Description Provided****";
                    Log.i("Myapp", "Menu_signature Item Desc");
                }
                try {
                    price = jsonObject.getInt("price");
                }
                catch (Exception e)
                {
                    price = 0;
                    Log.i("Myapp", "Menu_signature Item Price");
                }
                name=jsonObject.getString("name");
                JSONArray tag =new JSONArray();
                try {
                    tag=jsonObject.getJSONArray("tags");
                    for(int k =0;k<tag.length();k++)
                    {
                        tags.add(tag.getString(k));
                    }
                } catch (JSONException e) {
                    tags.add("any");
                    Log.i("Myapp", "Menu_signature Item Tags");
                }
                JSONArray sizes =new JSONArray();
                try {
                    sizes=jsonObject.getJSONArray("size");
                    Size temp_size;
                    JSONObject temp_json_object =new JSONObject();
                    for(int k =0;k<sizes.length();k++)
                    {

                        try {
                            temp_json_object=(JSONObject)sizes.get(k);
                            temp_size=new Size(temp_json_object);
                            size.add(temp_size);
                        } catch (JSONException e) {
                            Log.i("Myapp", "Menu_signature Item Size Inside");
                        }

                    }
                } catch (JSONException e) {
                    size_is_required=false;
                    Log.i("Myapp", "Menu_signature Item Size outside");
                }

                JSONArray arr_custom =new JSONArray();
                try {
                    arr_custom=jsonObject.getJSONArray("custom");
                    Custom temp_custom;
                    JSONObject temp_json_object =new JSONObject();
                    for(int k =0;k<arr_custom.length();k++)
                    {
                        if(!arr_custom.get(k).toString().equals("null"))
                        try {
                            temp_json_object=(JSONObject)arr_custom.get(k);
                            temp_custom=new Custom(temp_json_object);
                            custom.add(temp_custom);
                        } catch (JSONException e) {
                            Log.i("Myapp", "Menu_signature Item Custom inside");
                        }

                    }
                } catch (JSONException e) {
                    Log.i("Myapp", "Menu_signature Item Custom Outside");
                }



            } catch (JSONException e) {
                Log.i("Myapp", "Menu_signature Custom id simple name");
            }
        }
        public Size getSizeChildId(int id)
        {
            for(int i=0;i<size.size();i++)
            {
                if(size.get(i).id==id)
                {
                    return size.get(i);
                }
            }
            return null;
        }
    }
    Menu_Signature(JSONObject jsonObject)
    {
        if(jsonObject.has("subcats"))
        {
            try {
                subcats = new ArrayList<>();
                id = jsonObject.getInt("id");
                category= jsonObject.getString("category");
                JSONArray jsonArray = new JSONArray();
                jsonArray=jsonObject.getJSONArray("subcats");
                Subcat subcat;
                for(int i=0;i<jsonArray.length();i++)
                {
                    try {
                        subcat = new Subcat((JSONObject) jsonArray.get(i));
                        subcats.add(subcat);
                    } catch (Exception e) {
                        Log.i("Myapp", "Menu_signature Inside");
                        e.printStackTrace();
                    }
                }
            }
            catch (JSONException e)
            {

            }
        }
        else
        {
            initializeItems(jsonObject);
        }

    }

    private void initializeItems(JSONObject jsonObject)
    {
        items = new ArrayList<>();
        try{
            id = jsonObject.getInt("id");
            category= jsonObject.getString("category");
            JSONArray jsonArray = new JSONArray();
            jsonArray=jsonObject.getJSONArray("items");
            Items item;
            for(int i=0;i<jsonArray.length();i++)
            {
                try {
                    item=new Items((JSONObject)jsonArray.get(i));
                    items.add(item);
                } catch (Exception e) {
                    Log.i("Myapp", "Menu_signature Inside");
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            Log.i("Myapp", "Menu_signature Outside");

        }
    }

}
