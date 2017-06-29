package com.bahisadam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.bahisadam.fragment.PredictionLeagueLastWeekFragment;
import com.bahisadam.fragment.PredictionLeagueCurrentWeekFragment;

/**
 * @author GorkemKarayel on 27.04.2017.
 */

public class PredictionFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTittle;

    public PredictionFragmentPagerAdapter(FragmentManager fm , String[] mTabTittle){
        super(fm);
        this.mTabTittle = mTabTittle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
              return new PredictionLeagueCurrentWeekFragment();
            case 1 :
                return new PredictionLeagueLastWeekFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTabTittle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTabTittle[position];
    }
}
