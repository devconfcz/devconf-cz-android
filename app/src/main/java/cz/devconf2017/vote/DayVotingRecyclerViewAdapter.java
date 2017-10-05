package cz.devconf2017.vote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cz.devconf2017.R;

class DayVotingRecyclerViewAdapter extends FirebaseRecyclerAdapter<Votes, VoteHolder> {

    DayVotingRecyclerViewAdapter(FirebaseRecyclerOptions<Votes> options) {
        super(options);
    }

    @Override
    public VoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_voting, parent, false);
        return new VoteHolder(view);
    }

    @Override
    protected void onBindViewHolder(VoteHolder viewHolder, int position, Votes votes) {
        viewHolder.number.setText(votes.getScore());
        viewHolder.title.setText(votes.getTitle());
        viewHolder.speaker.setText(votes.getSpeaker());
//        viewHolder.talk = votes.getTalk().getId();
        viewHolder.talk = 123;
        viewHolder.statistic.setText(votes.getStatistic());
    }
}
