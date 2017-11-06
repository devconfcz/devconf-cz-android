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

    @BindView(R.id.session_start)
    public TextView start;

    @BindView(R.id.session_title)
    public TextView title;

    @BindView(R.id.session_speakers)
    public TextView speaker;

    @BindView(R.id.session_track)
    public TextView track;

    @BindView(R.id.session_duration)
    public TextView duration;

    @BindView(R.id.session_container)
    public LinearLayout background;

    @BindView(R.id.session_running)
    public TextView running;

    @BindView(R.id.session_day)
    public TextView day;

    @BindView(R.id.session_room)
    public TextView room;

    public String sessionDay;
    public String sessionId;

    public TalkViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.session_container)
    public void onClick(View view) {
        Intent sInfo = new Intent(view.getContext(), TalkDetail.class);
        sInfo.putExtra("day", sessionDay);
        sInfo.putExtra("id", sessionId);
        view.getContext().startActivity(sInfo);
    }
}