package cz.devconf2017.vote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cz.devconf2017.R;
import cz.devconf2017.Talk;

class DayVotingRecyclerViewAdapter extends FirebaseRecyclerAdapter<Talk, VoteHolder> {

    DayVotingRecyclerViewAdapter(FirebaseRecyclerOptions<Talk> options) {
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
    protected void onBindViewHolder(VoteHolder viewHolder, int position, Talk talk) {
        final TalkBusiness tb = new TalkBusiness(talk);

        viewHolder.number.setText(tb.printScore());
        viewHolder.title.setText(tb.printTitle());
        viewHolder.speaker.setText(tb.printSpeakers(null));
        viewHolder.talk = talk.getId();
        viewHolder.statistic.setText(tb.printStatistics());
    }
}
