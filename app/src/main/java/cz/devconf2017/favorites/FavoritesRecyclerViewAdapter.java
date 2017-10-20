package cz.devconf2017.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cz.devconf2017.R;
import cz.devconf2017.Talk;
import cz.devconf2017.session.SessionHolder;

class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<SessionHolder> {

    private final List<Talk> favoritesSessions;

    FavoritesRecyclerViewAdapter(List<Talk> favoritesSessions) {
        this.favoritesSessions = favoritesSessions;
    }

    @Override
    public SessionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate((R.layout.holder_session), null);

        return new SessionHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionHolder holder, int position) {
        Talk talk = favoritesSessions.get(position);
    }

    @Override
    public int getItemCount() {
        return favoritesSessions.size();
    }
}