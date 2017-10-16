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
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (list.size() > 0) {
//                    list.clear();
//                }
//
//                for (DataSnapshot vote : dataSnapshot.getChildren()) {
//                    Iterator talks = vote.getChildren().iterator();
//                    while (talks.hasNext()) {
//                        DataSnapshot ds = (DataSnapshot) talks.next();
//                        try {
//                            int talkId = Integer.parseInt(ds.getKey());
//                            int index = getVote(talkId);
//                            Feedback fb = ds.getValue(Feedback.class);
//                            if (index < 0) {
//                                Votes voting = new Votes(talkId);
//                                if (voting.talk.getDay() == day) {
//                                    voting.add(Integer.parseInt(fb.rating));
//                                    list.add(voting);
//                                }
//                            } else {
//                                list.get(index).add(Integer.parseInt(fb.rating));
//                            }
//                        } catch (NumberFormatException e) {
//                            Log.e("NFE", e.getMessage());
//                        }
//                    }
//                }
//
//                countVotes();
//                hideLoading();
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

//    public int getVote(int talkId) {
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).talk.getId() == talkId) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    public void countVotes() {
//        Collections.sort(list, sortByVotes);
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).addScore(i + 1);
//        }
//
//        Collections.sort(list, sortByAvg);
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).addScore(i + 1);
//        }
//
//        Collections.sort(list, sortByScore);
//
//    }
//
//    public static Comparator<Votes> sortByVotes = new Comparator<Votes>() {
//        @Override
//        public int compare(Votes talk, Votes t1) {
//            int vote = talk.numVotes;
//            int vote2 = t1.numVotes;
//            int dateComparision = vote - vote2;
//            if (dateComparision > 0) {
//                return 1;
//            }
//
//            if (dateComparision < 0) {
//                return -1;
//            }
//
//            dateComparision = Float.compare(talk.getAvgVotes(), t1.getAvgVotes());
//
//            if (dateComparision > 0) {
//                return 1;
//            }
//
//            if (dateComparision < 0) {
//                return -1;
//            }
//
//            return 0;
//        }
//    };
//
//    public static Comparator<Votes> sortByAvg = new Comparator<Votes>() {
//        @Override
//        public int compare(Votes talk, Votes t1) {
//            float vote = talk.getAvgVotes();
//            float vote2 = t1.getAvgVotes();
//            int dateComparision = Float.compare(vote, vote2);
//
//            if (dateComparision > 0) {
//                return 1;
//            }
//
//            if (dateComparision < 0) {
//                return -1;
//            }
//
//            return 0;
//        }
//    };
//
//    public static Comparator<Votes> sortByScore = new Comparator<Votes>() {
//        @Override
//        public int compare(Votes talk, Votes t1) {
//            int vote = talk.score;
//            int vote2 = t1.score;
//            int dateComparision = vote2 - vote;
//
//            if (dateComparision > 0) {
//                return 1;
//            }
//
//            if (dateComparision < 0) {
//                return -1;
//            }
//
//            dateComparision = t1.votes - talk.votes;
//
//            if (dateComparision > 0) {
//                return 1;
//            }
//
//            if (dateComparision < 0) {
//                return -1;
//            }
//
//            return 0;
//
//        }
//    };

}
