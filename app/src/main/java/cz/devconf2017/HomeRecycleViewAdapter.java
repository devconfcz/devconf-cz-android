package cz.devconf2017;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cz.devconf2017.favorites.FavoritesFragment;
import cz.devconf2017.session.TalkViewHolder;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<TalkViewHolder> {

    private List<Talk> itemList;
    public FavoritesFragment fragment;


    public HomeRecycleViewAdapter() {
        itemList = new ArrayList<Talk>();
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.row_talks), null);

        return new TalkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TalkViewHolder holder, int position) {
//        holder.setTalk(itemList.get(position));
    }

    public void updateData(List<Talk> newlist) {
        itemList.clear();
        itemList.addAll(newlist);
        if(fragment != null){
            if(itemList.size() < 1) {
//                fragment.noFavorites();
            }else{
//                fragment.setLoadingBox();
            }
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (this.itemList.size());
    }
}