package cz.devconf2017.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.devconf2017.R;

public class FavoritesHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.session_container)
    ViewGroup container;

    @BindView(R.id.session_start)
    TextView start;

    @BindView(R.id.session_running)
    View running;

    @BindView(R.id.session_title)
    TextView title;

    @BindView(R.id.session_speakers)
    TextView speakers;

    @BindView(R.id.session_track)
    TextView track;

    @BindView(R.id.session_duration)
    TextView duration;

    @BindView(R.id.session_day)
    TextView day;

    @BindView(R.id.session_room)
    TextView room;

    public FavoritesHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
