package com.seproject.crowdfunder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seproject.crowdfunder.fragment.EndingSoonFragment;
import com.seproject.crowdfunder.fragment.NearYouFragment;
import com.seproject.crowdfunder.fragment.TrendingFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new TrendingFragment();
        }
        else if (position == 1)
        {
            fragment = new NearYouFragment();
        }
        else if (position == 2)
        {
            fragment = new EndingSoonFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "TRENDING";
        }
        else if (position == 1)
        {
            title = "NEAR YOU";
        }
        else if (position == 2)
        {
            title = "ENDING SOON";
        }
        return title;
    }
}