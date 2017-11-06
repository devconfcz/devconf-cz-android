package cz.devconf2017.voting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.devconf2017.R;
import cz.devconf2017.Speaker;
import cz.devconf2017.Talk;
import cz.devconf2017.TalkBusiness;

class DayVotingRecyclerViewAdapter extends FirebaseRecyclerAdapter<Talk, DayVotingHolder> {

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
    public DayVotingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.holder_day_voting, parent, false);
        return new DayVotingHolder(view);
    }

    @Override
    protected void onBindViewHolder(final DayVotingHolder viewHolder, int position, final Talk talk) {
        final TalkBusiness tb = new TalkBusiness(talk);

        viewHolder.rating.setText(tb.printScore());
        viewHolder.title.setText(tb.printTitle());
        viewHolder.talk = talk.getId();
        viewHolder.statistic.setText(tb.printStatistics());

        viewHolder.speaker.setText("...");
        tb.getPrintedSpeakers(new TalkBusiness.GetPrintedSpeakersListener() {
            @Override
            public void onGetPrintedSpeakers(CharSequence speakers) {
                viewHolder.speaker.setText(speakers);
            }
        });
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
