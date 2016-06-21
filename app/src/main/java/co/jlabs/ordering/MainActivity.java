package co.jlabs.ordering;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.nineoldandroids.view.ViewHelper;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import co.jlabs.ordering.Classes.MyPizza;
import co.jlabs.ordering.Classes.Order_Pizza;
import co.jlabs.ordering.fab.FloatingActionButton;

//import app.test.com.myapplication.AlphaForegroundColorSpan;


public class MainActivity extends FragmentActivity implements ScrollTabHolder, ViewPager.OnPageChangeListener {

    private static AccelerateDecelerateInterpolator sSmoothInterpolator = new AccelerateDecelerateInterpolator();

    //
    private View mHeader;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;


    private ImageView icon;
    private static final int EMAIL_ACTIVITY_REQUEST = 1;
    private int mActionBarHeight;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private ImageView mHeaderLogo;
    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    private TypedValue mTypedValue = new TypedValue();
    int[] photos={R.drawable.photo1, R.drawable.phpto2,R.drawable.photo3,R.drawable.photo3};
    KenBurnsView imageView;





    Context context;
    LayoutInflater layoutInflater;
    OrderApplication app;
    MyPizza myPizza;
    AdapterPizza _adapter;
    ViewPager _mViewPager;
    private SmartTabLayout mPagerSlidingTabStrip;
    TextView current_orders;
    LinearLayout lay_orders,toolbar;
    int allorders;

    TextView checkout,money_foot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context=this;
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + getActionBarHeight();
        setContentView(R.layout.activity_main);
        imageView =(KenBurnsView) findViewById(R.id.header_picture);
        icon = (ImageView) findViewById(R.id.icon);
        mHeaderLogo = (ImageView) findViewById(R.id.header_thumbnail);
        mHeader = findViewById(R.id.header);
        mPagerSlidingTabStrip = (SmartTabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        toolbar= (LinearLayout) findViewById(R.id.toolbar);

        FloatingActionButton history=(FloatingActionButton)findViewById(R.id.history);
        FloatingActionButton rateus=(FloatingActionButton)findViewById(R.id.rate);
        FloatingActionButton aboutus=(FloatingActionButton)findViewById(R.id.aboutus);
        FloatingActionButton feedback=(FloatingActionButton)findViewById(R.id.feedback);
        FloatingActionButton addresses=(FloatingActionButton)findViewById(R.id.addresses);
        FloatingActionButton current=(FloatingActionButton)findViewById(R.id.current);

        layoutInflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        app = (OrderApplication) context.getApplicationContext();
        current_orders= (TextView) findViewById(R.id.orders);
        myPizza = app.getMyPizza();

        mViewPager.setOffscreenPageLimit(myPizza.type_of_pizza.size());
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);
        String email = Static_Catelog.getStringProperty(context, "email");
        String order_number = Static_Catelog.getStringProperty(context, "order_number");
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(this);
        mPagerAdapter.setTabHolderScrollingContent(this);
        _mViewPager= (ViewPager) findViewById(R.id.view_pager);
        lay_orders= (LinearLayout) findViewById(R.id.orders_layout);
        checkout= (TextView) findViewById(R.id.checkout);
        money_foot= (TextView) findViewById(R.id.money_foot);

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
                    finish();


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

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // nothing
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // nothing
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
      //  currentHolder.adjustScroll((int) (mHeader.getHeight() + ViewHelper.getTranslationY(mHeader)),(mHeaderHeight));

    }

    @Override
    public void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition)
    {
        if (mViewPager.getCurrentItem() == pagePosition)
        {
            mHeader.setTranslationY(Math.max(-view.getScrollY(), mMinHeaderTranslation));
            float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
            interpolate(mHeaderLogo, icon, sSmoothInterpolator.getInterpolation(ratio));
            toolbar.setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
        }
    }

    @Override
    public void adjustScroll(int scrollHeight,int headerTranslationY) {
        // nothing
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

    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
            getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        }else{
            getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
        }

        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());

        return mActionBarHeight;
    }



    public class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private ScrollTabHolder mListener;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return myPizza.type_of_pizza.get(position).category;
        }

        @Override
        public int getCount() {
            return myPizza.type_of_pizza.size();
        }

        @Override
        public Fragment getItem(int position) {
            ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) FragmentForMainActivity.newInstance(position);
            mScrollTabHolders.put(position, fragment);
            if (mListener != null) {
                fragment.setScrollTabHolder(mListener);
            }
            return fragment;
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
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


}