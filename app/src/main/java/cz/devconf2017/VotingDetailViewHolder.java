package cz.devconf2017;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by jridky on 13.1.17.
 */
public class VotingDetailViewHolder extends RecyclerView.ViewHolder{

    public TextView user, feedback;
    public RatingBar stars;
    private int talk;

    public VotingDetailViewHolder(View itemView) {
        super(itemView);
        user = (TextView) itemView.findViewById(R.id.user);
        feedback = (TextView) itemView.findViewById(R.id.feedback_content);
        stars = (RatingBar) itemView.findViewById(R.id.stars);
    }

    public void setFeedback(Feedback f){
        user.setText(f.user);
        feedback.setText(f.feedback);
        stars.setRating(Float.valueOf(f.rating));
    }
}