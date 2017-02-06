package com.desmond.parallaxviewpager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

/**
 * Created by desmond on 1/6/15.
 */
public abstract class ParallaxFragmentPagerAdapter extends FragmentPagerAdapter {

    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;

    public ParallaxFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);

        mScrollTabHolders.put(position, (ScrollTabHolder) object);

        return object;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}
