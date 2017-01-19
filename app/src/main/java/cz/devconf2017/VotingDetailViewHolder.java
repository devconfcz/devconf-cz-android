package cz.devconf2017;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by jridky on 13.1.17.
 */
public class VotingDetailViewHolder extends RecyclerView.ViewHolder{

    public TextView feedback;
    public RatingBar stars;

    public VotingDetailViewHolder(View itemView) {
        super(itemView);
        feedback = (TextView) itemView.findViewById(R.id.feedback_content);
        stars = (RatingBar) itemView.findViewById(R.id.stars);
    }

    public void setFeedback(Feedback f){
        feedback.setText(f.feedback);
        stars.setRating(Float.valueOf(f.rating));
    }
}