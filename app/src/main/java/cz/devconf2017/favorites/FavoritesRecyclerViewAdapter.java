package cz.devconf2017.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cz.devconf2017.R;
import cz.devconf2017.Talk;
import cz.devconf2017.TalkBusiness;

class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesHolder> {

    private static final int LAYOUT_ID = R.layout.holder_session;

    private final List<Talk> favoritesSessions;

    FavoritesRecyclerViewAdapter(List<Talk> favoritesSessions) {
        this.favoritesSessions = favoritesSessions;
    }

    @Override
    public FavoritesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(LAYOUT_ID, parent, false);

        return new FavoritesHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoritesHolder holder, int position) {
        Talk talk = favoritesSessions.get(position);
        TalkBusiness tb = new TalkBusiness(talk);

        holder.start.setText(tb.printStart());
        holder.running.setVisibility(tb.isRunning() ? View.VISIBLE : View.INVISIBLE);
        holder.title.setText(tb.printTitle());
        holder.track.setText(tb.printTrack());
        holder.duration.setText(tb.printDuration());
        holder.day.setText(tb.printDay(holder.itemView.getContext()));
        holder.room.setText(tb.printRoom());

        holder.container.setBackgroundColor(TalkBusiness.GetTrackColorListener.DEFAULT_COLOR);
        tb.getTrackColor(new TalkBusiness.GetTrackColorListener() {
            @Override
            public void onGetColor(int trackColor) {
                holder.container.setBackgroundColor(trackColor);
            }
        });

        holder.speakers.setText(TalkBusiness.GetPrintedSpeakersListener.DEFAULT_SPEAKERS);
        tb.getPrintedSpeakers(new TalkBusiness.GetPrintedSpeakersListener() {
            @Override
            public void onGetPrintedSpeakers(CharSequence speakers) {
                holder.speakers.setText(speakers);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesSessions.size();
    }
}