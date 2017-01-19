package cz.devconf2017;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by jridky on 11.1.17.
 */
public class Favorites {
    public List<String> talks;

    public Favorites(){
        // for firebase purposes
    }

    public void save(String user){

        DatabaseReference dr = MainActivity.FBDB.getDatabase().getReference();
        dr.child("favorites").child(user).setValue(this);
    }


}
