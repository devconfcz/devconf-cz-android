package cz.devconf2017.now;

import android.util.Log;
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
import java.util.Date;
import java.util.List;

import cz.devconf2017.R;
import cz.devconf2017.Talk;
import cz.devconf2017.TalkBusiness;
import cz.devconf2017.session.TalkViewHolder;
import cz.devconf2017.util.DateFormatUtils;

class NowSessionsRecyclerViewAdapter extends FirebaseRecyclerAdapter<Talk, TalkViewHolder> {

    private static final Comparator<Talk> TALK_COMPARATOR_BY_TIME = new Comparator<Talk>() {
        @Override
        public int compare(Talk t1, Talk t2) {
            TalkBusiness tb1 = new TalkBusiness(t1);
            TalkBusiness tb2 = new TalkBusiness(t2);
            return tb1.getStartDate().compareTo(tb2.getStartDate());
        }
    };
    private static final int LAYOUT_ID = R.layout.holder_session;

    private final List<Talk> items;

    NowSessionsRecyclerViewAdapter(FirebaseRecyclerOptions<Talk> options) {
        super(options);
        this.items = new ArrayList<>();
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(LAYOUT_ID, parent, false);
        return new TalkViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(final TalkViewHolder holder, int position, final Talk talk) {
        TalkBusiness tb = new TalkBusiness(talk);

        holder.start.setText(tb.printStart());
        holder.running.setVisibility(tb.isRunning() ? View.VISIBLE : View.INVISIBLE);
        holder.title.setText(tb.printTitle());
        holder.track.setText(tb.printTrack());
        holder.duration.setText(tb.printDuration());
        holder.day.setText(tb.printDay(holder.itemView.getContext()));
        holder.room.setText(tb.printRoom());

        holder.background.setBackgroundColor(TalkBusiness.GetTrackColorListener.DEFAULT_COLOR);
        tb.getTrackColor(new TalkBusiness.GetTrackColorListener() {
            @Override
            public void onGetColor(int trackColor) {
                holder.background.setBackgroundColor(trackColor);
            }
        });

        holder.speaker.setText(TalkBusiness.GetPrintedSpeakersListener.DEFAULT_SPEAKERS);
        tb.getPrintedSpeakers(new TalkBusiness.GetPrintedSpeakersListener() {
            @Override
            public void onGetPrintedSpeakers(CharSequence speakers) {
                holder.speaker.setText(speakers);
            }
        });
    }

    @Override
    public void onChildChanged(ChangeEventType type, DataSnapshot session, int newIndex, int oldIndex) {
        super.onChildChanged(type, session, newIndex, oldIndex);
    }

    @Override
    public void onDataChanged() {
        // Every time data changes, filter all sessions and sort by time.
        // TODO a sortable FirebaseRecyclerView would improve this implementation
        items.clear();
        for (Talk talk : getSnapshots()) {
            if (new TalkBusiness(talk).isRunningOrUpcoming()) {
                items.add(talk);
            }
        }
        Collections.sort(items, TALK_COMPARATOR_BY_TIME);
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
