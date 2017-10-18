package cz.devconf2017.dayvoting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.devconf2017.Feedback;
import cz.devconf2017.R;

class VotingDetailHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.feedback_content)
    TextView feedbackContent;

    @BindView(R.id.stars)
    RatingBar stars;

    public VotingDetailHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setFeedback(Feedback feedback) {
        feedbackContent.setText(feedback.feedback);
        stars.setRating(Float.valueOf(feedback.rating));
    }
}