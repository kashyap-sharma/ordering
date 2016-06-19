package co.jlabs.ordering;

import java.io.Serializable;

/**
 * Created by Kashyap on 10/8/2015.
 */

public class My_info implements Serializable {
    public String email;
    public String name;
    public String contact;
    My_info(){
        email = new String();
        name= new String();
        contact = new String();
    }
}

