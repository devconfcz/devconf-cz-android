package cz.devconf2017.vote;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cz.devconf2017.R;

class VotingResultsPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TABS_COUNT = 3;

    private final Context context;

    VotingResultsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return DayVotingFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.day_format, position + 1);
    }
}