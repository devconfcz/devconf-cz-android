package cz.devconf2017;

import com.google.firebase.database.DatabaseReference;

public class Feedback {

    public String feedback;
    public int rating;

    public Feedback() {
        // For Firebase purposes
    }

    public Feedback(String feedback, int rating) {
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // TODO extract from here
    public void save(int talk, String user) {
        DatabaseReference dr = MainActivity.FBDB.getDatabase().getReference();
        dr.child("votes").child(user).child(String.valueOf(talk)).setValue(this);
    }
}
