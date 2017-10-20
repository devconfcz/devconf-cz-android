package cz.devconf2017.session;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cz.devconf2017.MainActivity;
import cz.devconf2017.R;
import cz.devconf2017.Talk;
import cz.devconf2017.TalkBusiness;

class RoomSessionsRecyclerViewAdapter extends FirebaseRecyclerAdapter<Talk, TalkViewHolder> {

    private static final int LAYOUT_ID = R.layout.row_talks;

    RoomSessionsRecyclerViewAdapter(FirebaseRecyclerOptions<Talk> options) {
        super(options);
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(LAYOUT_ID, parent, false);

        return new TalkViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(TalkViewHolder holder, int position, Talk talk) {
        holder.sessionDay = talk.getDay();
        holder.sessionId = talk.getId();

        TalkBusiness tb = new TalkBusiness(talk);
        holder.track.setText(talk.getTrack());
        holder.duration.setText(tb.printDuration());
        holder.speaker.setText(tb.printSpeakers(null));
        holder.day.setText(tb.printDay(holder.itemView.getContext()));
        holder.room.setText(tb.printRoom());
        holder.title.setText(talk.getTitle());

        holder.start.setText(tb.printStart());
        holder.start2.setText(tb.printStart());

//        if (talk.last) {
//            holder.running.setText(R.string.lastofday);
//            talk.setRunning();
//        } else {
//            holder.running.setText(R.string.running);
//        }
//
//        if (talk.getRunning()) {
//            holder.start.setVisibility(View.GONE);
//            holder.start2.setVisibility(View.VISIBLE);
//            holder.running.setVisibility(View.VISIBLE);
//        } else {
//            holder.start.setVisibility(View.VISIBLE);
//            holder.start2.setVisibility(View.GONE);
//            holder.running.setVisibility(View.GONE);
//        }

        holder.background.setBackgroundColor(Color.parseColor(MainActivity.TRACKS.findColor(talk.getTrack())));
    }
}
