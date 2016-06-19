package co.jlabs.ordering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import co.jlabs.ordering.photoview.MaterialFontIcons;


public class AboutUs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        MaterialFontIcons back = (MaterialFontIcons) findViewById(R.id.back);
        WebView view = (WebView)findViewById(R.id.inset_web_view);
        String aboutus1;
        aboutus1 = "<html><body><p align=\"justify\" style=\"font-size:15;\">";
        aboutus1+= "We are a woodfired PIZZA delivery service that makes your PIZZA the way you like it! (CYOP) You could either choose from our Signature section or whip up a \"bad ass\" PIZZA for yourself from the Invent section. You get to choose your own base, the sauce and then just go crazy with the toppings. If you ask us, that's how a PIZZA is had!!\n" +
                "\n" +
                "We also have a range of extremely gratifying sliders- so tender and juicy, that every bite is a mouthful!\n" +
                "\n" +
                "From the ICEBOX, you can go for the Nutella Cream Cheese shake, or try the house favourite soda, Meth Me Up!\n" +
                "\n" +
                "So go ahead and call us or visit us at www.bakingbad.in to have the greatest PIZZAS & SLIDERS ever made!!!";
        aboutus1+= "</p></body></html>";
        view.loadData(aboutus1, "text/html", "utf-8");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutUs.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
