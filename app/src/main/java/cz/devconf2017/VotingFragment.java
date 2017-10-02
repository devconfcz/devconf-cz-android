package cz.devconf2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jridky on 11.1.17.
 */
public class VotingFragment extends Fragment {
    TextView title;
    View view;
    VotingPagerAdapter pAdapter;
    TabLayout tabs;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_votings, container, false);

        ButterKnife.bind(view);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        pAdapter = new VotingPagerAdapter(getFragmentManager());
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        viewPager.setAdapter(pAdapter);
        tabs.setTabsFromPagerAdapter(pAdapter);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        return view;
    }
}

class VotingPagerAdapter extends FragmentStatePagerAdapter {
    Fragment fragment = null;

    public VotingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //Based upon the position you can call the fragment you need here
        //here i have called the same fragment for all the instances
        fragment = new DayVotingFragment();

        Bundle args = new Bundle();
        args.putInt("index", position);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public int getCount() {
        // Returns the number of tabs (If you need 4 tabs change it to 4)
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "Day " + (position+1);
    }
}
