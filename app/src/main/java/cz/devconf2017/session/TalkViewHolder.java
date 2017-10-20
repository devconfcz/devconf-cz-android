package cz.devconf2017.session;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.devconf2017.R;
import cz.devconf2017.TalkDetail;

public class TalkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.start)
    TextView start;

    @BindView(R.id.start2)
    TextView start2;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.speaker)
    TextView speaker;

    @BindView(R.id.track)
    TextView track;

    @BindView(R.id.duration)
    TextView duration;

    @BindView(R.id.background)
    LinearLayout background;

    @BindView(R.id.running)
    TextView running;

    @BindView(R.id.day)
    TextView day;

    @BindView(R.id.room)
    TextView room;

    String sessionDay;
    String sessionId;

    public TalkViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.background)
    public void onClick(View view) {
        Intent sInfo = new Intent(view.getContext(), TalkDetail.class);
        sInfo.putExtra("day", sessionDay);
        sInfo.putExtra("id", sessionId);
        view.getContext().startActivity(sInfo);
    }
}