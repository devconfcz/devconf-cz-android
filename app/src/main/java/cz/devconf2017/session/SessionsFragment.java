package cz.devconf2017.session;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cz.devconf2017.MainActivity;
import cz.devconf2017.R;
import cz.devconf2017.Room;
import cz.devconf2017.base.BaseFragment;

import static cz.devconf2017.MainActivity.ALLLOADED.rooms;

public class SessionsFragment extends BaseFragment implements ValueEventListener {

    public static final String ARG_DAY = "ARG_DAY";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMM d", Locale.US);

    @BindView(R.id.title)
    protected TextView title;

    @BindView(R.id.tabs)
    protected TabLayout tabs;

    @BindView(R.id.view_pager)
    protected ViewPager viewPager;

    private int day;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sessions;
    }

    public static SessionsFragment newInstance(int day) {
        SessionsFragment fragment = new SessionsFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_DAY, day);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = getArguments().getInt(ARG_DAY, 1);

        showLoading();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title.setText("Day " + day + ", " + sdf.format(MainActivity.TALKS.getDay(day)));

        FirebaseDatabase.getInstance()
                .getReference("rooms")
                .addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Room> rooms = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            rooms.add(snapshot.getValue(Room.class));
        }

        SessionsPagerAdapter pagerAdapter = new SessionsPagerAdapter(getFragmentManager(), day, rooms);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabs.setupWithViewPager(viewPager);

        hideLoading();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        hideLoading();
        showToast(R.string.fui_general_error);
    }
}