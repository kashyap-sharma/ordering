package co.jlabs.ordering;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.jlabs.ordering.fragmentsInitialiser.Initialiser;
import co.jlabs.ordering.photoview.MyIconFonts;


public class AddressFiller extends AppCompatActivity  implements Initialiser {
    private ViewPager mViewPager;
    private String tota;
    String name,contact,landmark,address;
    Context context;
    MyIconFonts total;
    Button place_order;
    int s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_address_filler);
        total=(MyIconFonts)findViewById(R.id.total);
        place_order=(Button)findViewById(R.id.place_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tota=Static_Catelog.getStringProperty(context,"tota");
        float f = Float.parseFloat(tota);
        animateTextView(0,f,total);





    }

    public void animateTextView(float initialValue, float finalValue, final MyIconFonts  textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(1500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                textview.setText(valueAnimator.getAnimatedValue().toString());

            }
        });
        valueAnimator.start();

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewAddress(), "New Address");
        adapter.addFragment(new SavedAddress(), "Saved Address");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void updateName(int i,String name1,String address1,String contact1, String landmark1) {
        s=i;
        name=name1;
        address=address1;
        contact=contact1;
        landmark=landmark1;
        if (s>=7)
        {

            place_order.setBackgroundColor(Color.parseColor("#f26522"));
            place_order.setTextColor(Color.parseColor("#ffffff"));
            place_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(AddressFiller.this, OrderStatusLast.class);
                    startActivity(emailIntent);
                }
            });
        }
        else
        {
            place_order.setBackgroundColor(Color.parseColor("#DCDCDC"));
            place_order.setTextColor(Color.parseColor("#adadad"));
        }
    }

}
