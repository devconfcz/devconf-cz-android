package cz.devconf2017.session;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import cz.devconf2017.Room;
import cz.devconf2017.RoomTalkFragment;

class SessionsPagerAdapter extends FragmentStatePagerAdapter {

    private final int day;
    private final List<Room> rooms;

    SessionsPagerAdapter(FragmentManager fm, int day, List<Room> rooms) {
        super(fm);
        this.day = day;
        this.rooms = rooms;
    }

    @Override
    public Fragment getItem(int position) {
        // todo refactor with newInstance(position, day) method
        Fragment fragment = new RoomTalkFragment();

        Bundle args = new Bundle();
        args.putInt("index", position);
        args.putInt("day", day);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return rooms.get(position).getName();
    }
}
