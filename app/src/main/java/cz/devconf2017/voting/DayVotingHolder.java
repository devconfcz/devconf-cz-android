package cz.devconf2017.voting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.devconf2017.R;

class DayVotingHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.holder_day_voting_rating)
    TextView rating;

    @BindView(R.id.holder_day_voting_title)
    TextView title;

    @BindView(R.id.holder_day_voting_speakers)
    TextView speaker;

    @BindView(R.id.holder_day_voting_statistic)
    TextView statistic;

    public String talk;

    DayVotingHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.holder_day_voting_container)
    public void onVoteClick(View v) {
        Toast.makeText(v.getContext(), "TODO", Toast.LENGTH_SHORT).show();
//        v.getContext().startActivity(new Intent(v.getContext(), VotingDetail.class)
//                .putExtra("talk", talk)
//        );
    }
}
