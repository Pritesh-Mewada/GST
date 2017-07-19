package com.graphysis.gst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by pritesh on 11/7/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GoodsRate();
            case 1:
                return new ServicesRate();
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Goods";
            case 1:
                return "Services";
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
}
