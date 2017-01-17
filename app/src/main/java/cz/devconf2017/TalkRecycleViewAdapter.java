package cz.devconf2017;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TalkRecycleViewAdapter extends RecyclerView.Adapter<TalkViewHolder> {

    private int day, room;
    private List<Talk> itemList;

    public TalkRecycleViewAdapter(int day, int room) {
        this.day = day;
        this.room = room;
        this.itemList = MainActivity.TALKS.getTalks(day, room);
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(itemList.size() > 0) {
            view = LayoutInflater.from(parent.getContext()).inflate((R.layout.row_talks), null);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate((R.layout.row_talk_nothing), null);
        }

        return new TalkViewHolder(view, itemList.size());
    }

    @Override
    public void onBindViewHolder(TalkViewHolder holder, int position) {
        if(itemList.size() < 1){
            holder.setTitle("No sessions are planned here today.");
        }else{
            holder.setTalk(itemList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(this.itemList.size() < 1){
            this.itemList = MainActivity.TALKS.getTalks(day, room);
        }
        return (this.itemList.size() == 0? 1 : this.itemList.size());
    }
}