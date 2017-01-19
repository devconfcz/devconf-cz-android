package cz.devconf2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.fabric.sdk.android.Fabric.TAG;

/**
 * Created by jridky on 10.12.16.
 */

public class TalkDetail extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.background)
    DrawerLayout background;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.track)
    TextView track;
    @BindView(R.id.duration)
    TextView duration;
    @BindView(R.id.room)
    TextView room;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.speaker)
    TextView speaker;
    @BindView(R.id.datetime)
    TextView datetime;
    @BindView(R.id.difficulty)
    TextView difficulty;
    @BindView(R.id.feedback)
    RatingBar rating;
    @BindView(R.id.feedbackText)
    EditText feedbackText;
    @BindView(R.id.sendFeedback)
    Button submit;
    @BindView(R.id.resetFeedback)
    Button reset;



    int day, id;
    Talk t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        day = getIntent().getIntExtra("day",1);
        id = getIntent().getIntExtra("id",0);

        t = MainActivity.TALKS.findTalk(day,id);

        if(t == null)
                finish();

        title.setText(t.getTitle());
        track.setText(t.getTrack());
        duration.setText(t.getFormatedDuration());
        room.setText(t.getRoom().toUpperCase());
        description.setText(t.getDescription());
        background.setBackgroundColor(Color.parseColor(MainActivity.TRACKS.findColor(t.getTrack())));
        speaker.setText(t.getSpeakerCompleteInfo());
        datetime.setText("Day " + t.getDay() + " at " + t.getFormatedStart());
        difficulty.setText(t.getDifficulty());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            hideFeedback(true);
        }else{
            hideFeedback(false);
            DatabaseReference mref = MainActivity.FBDB.getDatabase().getReference("votes").child(user.getUid());
            reset.setVisibility(View.GONE);
            mref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for(DataSnapshot vote: dataSnapshot.getChildren()) {
                        if(vote.getKey().equals(t.id)){
                            Feedback f = vote.getValue(Feedback.class);
                            if(Integer.parseInt(f.rating) > 0){
                                reset.setVisibility(View.VISIBLE);
                                feedbackText.setText(f.feedback);
                                rating.setRating(Float.valueOf(f.rating));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }

        speaker.setOnClickListener(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                float rtg = rating.getRating();
                if(rtg < 1.0f){
                    rtg = 1.0f;
                    rating.setRating(rtg);
                }
                Feedback feedback = new Feedback(rtg, feedbackText.getText().toString());
                feedback.save(t.getId(), user.getUid());
                reset.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(),R.string.feedbackSent,Toast.LENGTH_LONG).show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference dr = MainActivity.FBDB.getDatabase().getReference();
                rating.setRating(0.0f);
                feedbackText.setText("");
                reset.setVisibility(View.GONE);
                dr.child("votes").child(user.getUid()).child(String.valueOf(t.getId())).removeValue();
                Toast.makeText(getBaseContext(),R.string.feedbackReset,Toast.LENGTH_LONG).show();
            }
        });

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar bar, float rating, boolean bool){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Feedback feedback = new Feedback(rating, feedbackText.getText().toString());
                feedback.save(t.getId(), user.getUid());
            }


        });



    }

    public void hideFeedback(boolean hide){
        if(hide){
            rating.setVisibility(View.GONE);
            feedbackText.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }else{
            rating.setVisibility(View.VISIBLE);
            feedbackText.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                if(MainActivity.FAVORITES.isFavorite(this.id)) {
                    getMenuInflater().inflate(R.menu.menu_favorites_on, menu);
                }else{
                    getMenuInflater().inflate(R.menu.menu_favorites_off, menu);
                }
            }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int Id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (Id == android.R.id.home) {
            finish();
        }

        if (Id == R.id.action_favorite){
            if(MainActivity.FAVORITES.isFavorite(id)){
                item.setIcon(R.drawable.ic_favorite_border);
                MainActivity.FAVORITES.remove(id);
            }else{
                item.setIcon(R.drawable.ic_favorite);
                MainActivity.FAVORITES.add(id);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view){
        Speaker s = t.getSpeaker();

        if(s != null) {

            Intent sInfo = new Intent(view.getContext(), SpeakerDetail.class);
            sInfo.putExtra("id", s.getId());
            sInfo.putExtra("name", s.getName());
            sInfo.putExtra("bio", s.getBio());
            sInfo.putExtra("avatar", s.getEmail());
            sInfo.putExtra("country", s.getCountry());
            sInfo.putExtra("twitter", s.getTwitter());
            sInfo.putExtra("organization", s.getOrganization());
            view.getContext().startActivity(sInfo);
        }
    }
}
