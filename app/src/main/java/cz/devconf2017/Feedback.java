package cz.devconf2017;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jridky on 11.1.17.
 */
public class Feedback {
    public String rating;
    public String feedback;
    public String user;

    public Feedback(){
        // for firebase purposes
    }

    public Feedback(float rating, String text, String user){
        this.rating = String.valueOf((int) rating);
        this.feedback = text;
        this.user = user;
    }

    public void save(int talk, String user){
        DatabaseReference dr = MainActivity.FBDB.getDatabase().getReference();
        dr.child("votes").child(String.valueOf(talk)).child(user).setValue(this);
    }
}
