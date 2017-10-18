package cz.devconf2017.vote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import cz.devconf2017.R;
import cz.devconf2017.Talk;
import cz.devconf2017.base.BaseFragment;
import cz.devconf2017.util.view.EmptyRecyclerView;

public class DayVotingFragment extends BaseFragment {

    private static final String TAG = DayVotingFragment.class.getName();
    private static final String ARG_DAY = "ARG_DAY";

    @BindView(R.id.voting_day_recycler_view)
    EmptyRecyclerView recycler;

    @BindView(R.id.empty_view)
    TextView emptyView;

    DayVotingRecyclerViewAdapter adapter;
    int day;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voting_day;
    }

    public static DayVotingFragment newInstance(int day) {
        DayVotingFragment fragment = new DayVotingFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_DAY, day);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = getArguments().getInt(ARG_DAY, 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showLoading();

        Query query = FirebaseDatabase.getInstance()
                .getReference("sessions")
                .orderByChild("day")
                .equalTo(String.valueOf(day));

        FirebaseRecyclerOptions<Talk> options = new FirebaseRecyclerOptions.Builder<Talk>()
                .setQuery(query, Talk.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new DayVotingRecyclerViewAdapter(options) {
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                hideLoading();
            }

            @Override
            public void onError(DatabaseError error) {
                super.onError(error);
                hideLoading();
                showToast(R.string.fui_general_error);
            }
        };

        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setEmptyView(emptyView);
    }

}
