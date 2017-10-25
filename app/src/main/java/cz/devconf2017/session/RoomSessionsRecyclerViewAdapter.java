package cz.devconf2017.session;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cz.devconf2017.R;
import cz.devconf2017.Talk;
import cz.devconf2017.TalkBusiness;

class RoomSessionsRecyclerViewAdapter extends FirebaseRecyclerAdapter<Talk, TalkViewHolder> {

    private static final int LAYOUT_ID = R.layout.row_talks;

    RoomSessionsRecyclerViewAdapter(FirebaseRecyclerOptions<Talk> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(final TalkViewHolder holder, int position, Talk session) {
        TalkBusiness tb = new TalkBusiness(session);

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
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(LAYOUT_ID, parent, false);

        return new TalkViewHolder(view);
    }
}
