package co.jlabs.ordering;

import org.json.JSONObject;

/**
 * Created by Kashyap on 10/30/2015.
 */
public class PizzaClass {

    class Craft_Pizza{
        class Size{

        }
        class Custom{
            class Crust{

            }
            class Sauce{

            }
            class Cheese{

            }
            class Toppings{
                class Signature{

                }
                class Gourmet{

                }

            }

        }


    }
    class Signature{
        int sig_p_id;
        String sig_category;
        class Sig_class{
            int id;
            boolean simple;
            String desc;

        }
        Signature()
        {
            sig_category="";
        }
        class Size{
            int size_id,price;
            String size_name;
            Size(){
                size_name = "";

            }


        }
        class Custom{
            int max,min,soft;
            String topping_type;
            Custom()
            {
                topping_type="";
            }
            class Items{
                boolean tag;
                int item_id;
                String item_name;
                int top_price;
            }


        }


    }
    class Gourmet{
        class Size{

        }
        class Custom{

        }
    }
    class Sliders{

    }
    class Small_Plates{

    }
    class IceBox{

    }
    class Desserts{

    }

    Signature signature;
    Gourmet gourmet;
    PizzaClass(){
        signature= new Signature();
        gourmet= new Gourmet();

    }


    public PizzaClass initialize(JSONObject jsonObject){

        PizzaClass pc=new PizzaClass();
                try{
                    pc.signature.sig_category=jsonObject.getString("category");
                    pc.signature.sig_p_id=jsonObject.getInt("id");
                }
                catch (Exception e){

                }
                return pc;
    }

}
