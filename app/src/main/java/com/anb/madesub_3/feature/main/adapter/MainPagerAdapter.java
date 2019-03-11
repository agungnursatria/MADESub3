package com.anb.madesub_3.feature.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anb.madesub_3.feature.movie.MovieFragment;
import com.anb.madesub_3.feature.tvshow.TVShowFragment;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private AHBottomNavigation nav;
    public MainPagerAdapter(FragmentManager fm, AHBottomNavigation nav) {
        super(fm);
        this.nav = nav;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new MovieFragment();
            case 1:
                return new TVShowFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
