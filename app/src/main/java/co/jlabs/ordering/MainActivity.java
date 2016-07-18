package co.jlabs.ordering;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.desmond.parallaxviewpager.ParallaxFragmentPagerAdapter;
import com.desmond.parallaxviewpager.ParallaxViewPagerBaseActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.nineoldandroids.view.ViewHelper;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.wnafee.vector.MorphButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import co.jlabs.ordering.Classes.MyPizza;
import co.jlabs.ordering.Classes.Order_Pizza;
import co.jlabs.ordering.photoview.MyIconFonts;
import co.jlabs.ordering.photoview.MyIconFonts_FAB;
import co.jlabs.ordering.photoview.MyTextView;
import co.jlabs.ordering.revealtor.Revealator;


public class MainActivity extends ParallaxViewPagerBaseActivity {

    private View mTopImage;
    MyIconFonts drawerBtn;
    private SmartTabLayout mNavigBar;
    Context context;
    // private PagerAdapter mPagerAdapter;
    private static final int EMAIL_ACTIVITY_REQUEST = 1;
    int[] photos={R.drawable.photo1, R.drawable.phpto2,R.drawable.photo3,R.drawable.photo3};
    KenBurnsView imageView;
    public static final String STATEKEY_THE_AWESOME_VIEW_IS_VISIBLE = "the_awesome_view_is_visible";
    String order_number, email;
    OrderApplication app;
    MyPizza myPizza;
    int allorders;
    TextView current_orders;
    LinearLayout lay_orders;
    TextView money_foot;
    MyTextView checkout;
    int backpress=0;

    LinearLayout history,current,addres,feedback,rateus,aboutus;
    View cross;
    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    private ImageView icon,mHeaderLogo;
    private View toolbar;

    FloatingActionButton menu;
    private View theAwesomeView;
    private static AccelerateDecelerateInterpolator sSmoothInterpolator = new AccelerateDecelerateInterpolator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        context=this;

        drawerBtn=(MyIconFonts)findViewById(R.id.drawerBtn);
        final  View  maina=findViewById(R.id.maina);
        initValues();
        app = (OrderApplication) context.getApplicationContext();
        myPizza = app.getMyPizza();
        order_number = Static_Catelog.getStringProperty(context, "order_number");
        email = Static_Catelog.getStringProperty(context, "email");
        menu=(FloatingActionButton)findViewById(R.id.menu);
        theAwesomeView = findViewById(R.id.naviga);
        history=(LinearLayout)findViewById(R.id.history);
        current=(LinearLayout)findViewById(R.id.current_orders);
        addres=(LinearLayout)findViewById(R.id.addres);
        feedback=(LinearLayout)findViewById(R.id.feedback);
        rateus=(LinearLayout)findViewById(R.id.rateus);
        aboutus=(LinearLayout)findViewById(R.id.aboutus);
        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.performClick();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerBtn.setClickable(false);
                drawerBtn.setVisibility(View.GONE);
              //  maina.setEnabled(false);
                menu.setVisibility(View.GONE);
                Revealator.reveal(theAwesomeView)
                        .from(menu)
                        .withChildsAnimation()
                        //.withDelayBetweenChildAnimation(...)
                        //.withChildAnimationDuration(...)
                        //.withTranslateDuration(...)
                        .withRevealDuration(100)
                        //.withEndAction(...)
                        .start();
                maina.setClickable(false);
                backpress=0;
            }
        });
        cross=findViewById(R.id.cross);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menu.setVisibility(View.VISIBLE);
                Revealator.unreveal(theAwesomeView)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                               // menu.show();

                            }
                        })
                        .withDuration(200)
                        .start();
               // drawerBtn.setEndDrawable(R.drawable.ic_drawer_to_arrow);
                drawerBtn.setClickable(true);
                drawerBtn.setVisibility(View.VISIBLE);
                menu.setVisibility(View.GONE);
                maina.setClickable(true);

            }
        });

        if(email!=null){
            feedback.setVisibility(View.VISIBLE);
            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(MainActivity.this, EnvelopeActivity.class);
                    startActivityForResult(intent, EMAIL_ACTIVITY_REQUEST);

                }
            });
        }

        if(order_number!=null){

            current.setVisibility(View.VISIBLE);
            current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent;

                    emailIntent = new Intent(MainActivity.this, OrderStatusLast.class);


                    startActivity(emailIntent);
                }
            });
        }
        if(email!=null){
            history.setVisibility(View.VISIBLE);
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent;
                    emailIntent = new Intent(MainActivity.this, UserHistory.class);
                    startActivity(emailIntent);
                }
            });
        }
        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String appPackageName = "com.ea.gp.minions";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent;
                emailIntent = new Intent(MainActivity.this, AboutUs.class);


                startActivity(emailIntent);
                overridePendingTransition( R.anim.in_up, R.anim.out_up );

            }
        });

        if (savedInstanceState != null && savedInstanceState.getBoolean(STATEKEY_THE_AWESOME_VIEW_IS_VISIBLE)) {
            theAwesomeView.setVisibility(View.VISIBLE);
            menu.setVisibility(View.INVISIBLE);
        }




        icon = (ImageView) findViewById(R.id.icon);
        mHeaderLogo = (ImageView) findViewById(R.id.header_thumbnail);
        toolbar= findViewById(R.id.toolbar);


        mTopImage = findViewById(R.id.header_picture);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mNavigBar = (SmartTabLayout) findViewById(R.id.tabs);
        current_orders= (TextView) findViewById(R.id.orders);
        mHeader = findViewById(R.id.header);
        imageView = (KenBurnsView) findViewById(R.id.header_picture);
        lay_orders= (LinearLayout) findViewById(R.id.orders_layout);
        checkout= (MyTextView) findViewById(R.id.checkout);
        money_foot= (TextView) findViewById(R.id.money_foot);
        app = (OrderApplication) context.getApplicationContext();
        myPizza = app.getMyPizza();
        app = (OrderApplication) context.getApplicationContext();
        if (savedInstanceState != null) {
            mTopImage.setTranslationY(savedInstanceState.getFloat(IMAGE_TRANSLATION_Y));
            mHeader.setTranslationY(savedInstanceState.getFloat(HEADER_TRANSLATION_Y));
        }
        mViewPager.setOffscreenPageLimit(myPizza.type_of_pizza.size());

        String email = Static_Catelog.getStringProperty(context, "email");
        String order_number = Static_Catelog.getStringProperty(context, "order_number");





        setupAdapter();


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ArrayList<Order_Pizza> pizzas = app.getAllOrdersPizzas();
                String s = "";
                for (int i = 0; i < pizzas.size(); i++) {
//                    Log.i("Myapp Order Pizza", "Pizza " + i + " " + pizzas.get(i).toJSON());
                    s = s + pizzas.get(i).toJSON();
                    Log.i("Myapp Order Pizza", "Pizza " + i + " " + s);
                }
                Static_Catelog.setStringProperty(context, "order_details", s);
                String m = Static_Catelog.getStringProperty(context, "order_details");
                Log.i("Myapp Order Pizza", "Pizsafza " + 2 + " " + m);
                JSONArray arr = new JSONArray();

                for (int i = 0; i < pizzas.size(); i++) {

                    arr.put(pizzas.get(i).toJSON());
                }
                Intent emailIntent = new Intent(context, CheckOut.class);
                startActivity(emailIntent);


            }
        });

        updateUIforOrders();



        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                // change images randomly
                Random ran=new Random();
                int i=ran.nextInt(photos.length);
                //set image resources
                imageView.setImageResource(photos[i]);
                i++;
                if(i>photos.length-1)
                {
                    i=0;
                }
                handler.postDelayed(this, 5000);  //for interval...
            }
        };
        handler.postDelayed(runnable, 3000); //for initial delay..



    }


    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition)
    {
        if (mViewPager.getCurrentItem() == pagePosition)
        {
            mHeader.setTranslationY(Math.max(-view.getScrollY(), mMinHeaderTranslation));
            float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
            interpolate(mHeaderLogo, icon, sSmoothInterpolator.getInterpolation(ratio));
            toolbar.setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
        }
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    private void interpolate(View view1, View view2, float interpolation) {
        getOnScreenRect(mRect1, view1);
        getOnScreenRect(mRect2, view2);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        float translationX = 0.5F * (interpolation * (mRect2.left + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom));

        ViewHelper.setTranslationX(view1, translationX);
        ViewHelper.setTranslationY(view1, translationY - ViewHelper.getTranslationY(mHeader));
        //framelayout size reduction
        ViewHelper.setScaleX(view1, scaleX);
        ViewHelper.setScaleY(view1, scaleY);
    }

    private void getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    @Override
    protected void initValues() {
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + tabHeight;


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat(IMAGE_TRANSLATION_Y, mTopImage.getTranslationY());
        outState.putFloat(HEADER_TRANSLATION_Y, mHeader.getTranslationY());
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATEKEY_THE_AWESOME_VIEW_IS_VISIBLE, theAwesomeView.getVisibility() == View.VISIBLE);
    }

    @Override
    protected void setupAdapter() {
        if (mAdapter == null) {
            mAdapter = new PagerAdapter(getSupportFragmentManager());
        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mNavigBar.setOnPageChangeListener(getViewPagerChangeListener());
        mNavigBar.setViewPager(mViewPager);
    }

    @Override
    protected void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        mHeader.setTranslationY(translationY);
        mTopImage.setTranslationY(-translationY / 3);
    }

    private class PagerAdapter extends ParallaxFragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentForMainActivity.newInstance(position);
            return fragment;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return myPizza.type_of_pizza.get(position).category;
        }

        @Override
        public int getCount() {
            return myPizza.type_of_pizza.size();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateUIforOrders();
        if (requestCode == EMAIL_ACTIVITY_REQUEST && resultCode == RESULT_OK) {

            String[] to = data.getStringArrayExtra(Intent.EXTRA_EMAIL);
            String subject = data.getStringExtra(Intent.EXTRA_SUBJECT);
            String msg = data.getStringExtra(Intent.EXTRA_TEXT);
            String email1 = Static_Catelog.getStringProperty(context, "email");

            String numb = Static_Catelog.getStringProperty(context, "numb");

            //API Call wake

            JSONObject finalJson = new JSONObject();
            try {
                finalJson.put("email", email1);
                finalJson.put("vendor_id", 1);
                finalJson.put("phone", numb);
                finalJson.put("subject", subject);
                finalJson.put("body", msg);


            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("tracer", "" + finalJson);
            new Feedback(finalJson).execute();

        }
    }
    private class Feedback extends AsyncTask<String,Void,Void>
    {
        JSONObject object;
        Feedback(JSONObject obj)
        {
            object=obj;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONObject obj=JSONfunctions.makenewHttpRequest(context, "http://lannister-api.elasticbeanstalk.com/tyrion/feedback", object);

            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            Toast.makeText(context, "FeedBack Submitted", Toast.LENGTH_LONG).show();
            // finish();
        }
    }
    public void updateUIforOrders()
    {
        allorders=app.getAllOrdersPizzas().size();
        if(allorders>0) {
            current_orders.setText("" + allorders);
            lay_orders.setVisibility(View.VISIBLE);
            money_foot.setText("" + app.totalprice()*1.125);
        }
        else
            lay_orders.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(backpress==0){
            cross.performClick();
            backpress=1;
        }else {
            backButtonHandler();
            backpress=0;
        }

    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Leave application?");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to leave the application?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }
}
