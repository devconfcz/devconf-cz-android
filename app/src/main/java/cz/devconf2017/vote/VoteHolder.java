package cz.devconf2017.vote;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.devconf2017.R;

class VoteHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.vote_holder_number)
    TextView number;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.speaker)
    TextView speaker;

    @BindView(R.id.statistic)
    TextView statistic;

    public String talk;

    VoteHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.vote_holder_container)
    public void onVoteClick(View v) {
        v.getContext().startActivity(new Intent(v.getContext(), VotingDetail.class)
                .putExtra("talk", talk)
        );
    }
}
