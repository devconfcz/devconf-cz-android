package cz.devconf2017;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by jridky on 9.12.16.
 */

public class TalkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView start, start2, title, speaker, track, duration, running;
    public Context context;
    public Talk talk;
    public LinearLayout background;


    public void setTalk(Talk t){
        this.talk = t;
        setStart();
        setTitle();
        setSpeaker();
        setInfo();
        setColor();
    }

    private void setInfo() {
        this.track.setText(talk.getTrack());
        this.duration.setText(talk.getFormatedDuration());
    }

    private void setSpeaker() {
        this.speaker.setText(talk.getSpeakerInfo());
    }

    private void setTitle() {
        this.title.setText(talk.getTitle());
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    private void setStart() {

        this.start.setText(this.talk.getFormatedStart());
        this.start2.setText(this.talk.getFormatedStart());
        if(this.talk.last){
            this.running.setText(R.string.lastofday);
            this.talk.setRunning();
        }else{
            this.running.setText(R.string.running);
        }
        if(this.talk.getRunning()){
            this.start.setVisibility(View.GONE);
            this.start2.setVisibility(View.VISIBLE);
            this.running.setVisibility(View.VISIBLE);
        }else{
            this.start.setVisibility(View.VISIBLE);
            this.start2.setVisibility(View.GONE);
            this.running.setVisibility(View.GONE);
        }
    }

    private void setColor(){
        if (MainActivity.TRACKS.getTracks() == null){
            MainActivity.TRACKS.load();
        }
        background.setBackgroundColor(Color.parseColor(MainActivity.TRACKS.findColor(this.talk.getTrack())));
    }

    public TalkViewHolder(View itemView, int size) {
        super(itemView);

        if(size > 0) {
            //implementing onClickListener
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            start = (TextView) itemView.findViewById(R.id.start);
            start2 = (TextView) itemView.findViewById(R.id.start2);
            title = (TextView) itemView.findViewById(R.id.title);
            speaker = (TextView) itemView.findViewById(R.id.speaker);
            track = (TextView) itemView.findViewById(R.id.track);
            duration = (TextView) itemView.findViewById(R.id.duration);
            background = (LinearLayout) itemView.findViewById(R.id.background);
            running = (TextView) itemView.findViewById(R.id.running);
        }else{
            title = (TextView) itemView.findViewById(R.id.title);
        }

    }

    public TalkViewHolder(View itemView) {
        super(itemView);

        //implementing onClickListener
        itemView.setOnClickListener(this);
        context = itemView.getContext();
        start = (TextView) itemView.findViewById(R.id.start);
        title = (TextView) itemView.findViewById(R.id.title);
        speaker = (TextView) itemView.findViewById(R.id.speaker);
        track = (TextView) itemView.findViewById(R.id.track);
        duration = (TextView) itemView.findViewById(R.id.duration);
        background = (LinearLayout) itemView.findViewById(R.id.background);


    }

    @Override
    public void onClick(View view) {
        Intent sInfo = new Intent(view.getContext(), TalkDetail.class);
        sInfo.putExtra("day", talk.getDay());
        sInfo.putExtra("id", talk.getId());
        view.getContext().startActivity(sInfo);
    }
}