package cz.devconf2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by jridky on 13.1.17.
 */
public class FavoritesFragment extends Fragment {
    TextView title;
    View view;
    FavoritesPagerAdapter pAdapter;
    TabLayout tabs;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_talks, container, false);

        ButterKnife.bind(view);

        title = (TextView) view.findViewById(R.id.title);
        title.setText(R.string.favorites);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        pAdapter = new FavoritesPagerAdapter(getFragmentManager());
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        viewPager.setAdapter(pAdapter);
        tabs.setTabsFromPagerAdapter(pAdapter);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        return view;
    }
}

class FavoritesPagerAdapter extends FragmentStatePagerAdapter {
    Fragment fragment = null;

    public FavoritesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //Based upon the position you can call the fragment you need here
        //here i have called the same fragment for all the instances
        fragment = new RoomTalkFragment();

        Bundle args = new Bundle();
        args.putInt("index", position);
        args.putBoolean("favorites", true);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public int getCount() {
        // Returns the number of tabs (If you need 4 tabs change it to 4)
        int tabs = MainActivity.ROOMDB.getRooms().size();
        return tabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return MainActivity.ROOMDB.getRoomName(position);
    }
}
