package cz.devconf2017;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by jridky on 11.1.17.
 */
public class Feedback {
    public String rating;
    public String feedback;

    public Feedback(){
        // for firebase purposes
    }

    public Feedback(float rating, String text){
        this.rating = String.valueOf((int) rating);
        this.feedback = text;
    }

    public void save(int talk, String user){
        DatabaseReference dr = MainActivity.FBDB.getDatabase().getReference();
        dr.child("votes").child(user).child(String.valueOf(talk)).setValue(this);
    }
}
