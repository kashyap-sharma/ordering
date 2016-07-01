package co.jlabs.ordering;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import co.jlabs.ordering.photoview.MyIconFonts;
import co.jlabs.ordering.photoview.MyTextView;

public class BillActivity extends AppCompatActivity {

    MyTextView pay_mode,order_id,name,address,contact;
    MyIconFonts total,back;
    Button track;
    String mode,orderID,cus_name,add,num;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        context=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pay_mode=(MyTextView)findViewById(R.id.pay_mode);
        order_id=(MyTextView)findViewById(R.id.order_id);
        name=(MyTextView)findViewById(R.id.name);
        address=(MyTextView)findViewById(R.id.address);
        contact=(MyTextView)findViewById(R.id.contact);
        total=(MyIconFonts)findViewById(R.id.total);
        back=(MyIconFonts)findViewById(R.id.back);

        track=(Button)findViewById(R.id.track);
        Intent intent = getIntent();
        mode=intent.getStringExtra("mode");
        //orderID=intent.getStringExtra("mode");
        //cus_name=intent.getStringExtra("mode");
        add=intent.getStringExtra("faddress");
        num=intent.getStringExtra("contact");
        if (mode.equals("cod")){
            pay_mode.setText("COD");
        }else {
            pay_mode.setText("Prepaid");
            pay_mode.setTextColor(Color.parseColor("#ffffff"));
            pay_mode.setBackgroundColor(Color.parseColor("#079450"));
        }
        order_id.setText(Static_Catelog.getStringProperty(context,"order_number"));
        name.setText(Static_Catelog.getStringProperty(context,"fname")+" "+Static_Catelog.getStringProperty(context,"fname"));
        address.setText(add);
        contact.setText(num);
        total.setText(context.getResources().getString(R.string.rupee)+" "+Static_Catelog.getStringProperty(context,"tota"));

    }

}