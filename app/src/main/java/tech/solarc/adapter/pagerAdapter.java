package tech.solarc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sagar on 29/4/17.
 */

public class pagerAdapter extends FragmentPagerAdapter {


    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new appliances();
        }
        else {
            return new panels();
        }
    }
}
