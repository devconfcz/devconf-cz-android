package cz.devconf2017;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jridky on 9.12.16.
 */

public class VotingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView number, title, speaker, statistic;
    private int talk;

    public VotingViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        number = (TextView) itemView.findViewById(R.id.number);
        title = (TextView) itemView.findViewById(R.id.title);
        speaker = (TextView) itemView.findViewById(R.id.speaker);
        statistic = (TextView) itemView.findViewById(R.id.statistic);
    }

    public void setVote(Voting v){
        number.setText(v.getScore());
        title.setText(v.getTitle());
        speaker.setText(v.getSpeaker());
        talk = v.talk.getId();
        statistic.setText(v.getStatistic());
    }

    @Override
    public void onClick(View v){
        Intent sInfo = new Intent(v.getContext(), VotingDetail.class);
        sInfo.putExtra("talk", talk);
        v.getContext().startActivity(sInfo);
    }
}