package cz.devconf2017.dayvoting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.devconf2017.R;
import cz.devconf2017.Talk;

class DayVotingRecyclerViewAdapter extends FirebaseRecyclerAdapter<Talk, VoteHolder> {

    private static final Comparator<Talk> TALK_COMPARATOR_BY_RATING = new Comparator<Talk>() {
        @Override
        public int compare(Talk t1, Talk t2) {
            return Float.compare(t2.getAverageRating(), t1.getAverageRating());
        }
    };

    private final List<Talk> items;

    DayVotingRecyclerViewAdapter(FirebaseRecyclerOptions<Talk> options) {
        super(options);
        this.items = new ArrayList<>();
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

    @Override
    public void onChildChanged(ChangeEventType type, DataSnapshot session, int newIndex, int oldIndex) {
        super.onChildChanged(type, session, newIndex, oldIndex);
    }

    @Override
    public void onDataChanged() {
        // Every time data changes, calculate all ratings and sort the collection.
        // TODO a sortable FirebaseRecyclerView would improve this implementation
        items.clear();
        for (Talk talk : getSnapshots()) {
            float averageRating = new TalkBusiness(talk).calculateAverageRatingOfSession();
            talk.setAverageRating(averageRating);
            items.add(talk);
        }
        Collections.sort(items, TALK_COMPARATOR_BY_RATING);
        notifyDataSetChanged();
    }

    @Override
    public Talk getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
