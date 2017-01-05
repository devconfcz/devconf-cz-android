package cz.devconf2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    int day, id;
    Talk t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        speaker.setOnClickListener(this);



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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view){
        Speaker s = t.getSpeaker();

        if(s != null) {

            Intent sInfo = new Intent(view.getContext(), SpeakerDetail.class);
            sInfo.putExtra("name", s.getName());
            sInfo.putExtra("bio", s.getBio());
            sInfo.putExtra("avatar", s.getAvatar());
            sInfo.putExtra("country", s.getCountry());
            sInfo.putExtra("twitter", s.getTwitter());
            sInfo.putExtra("organization", s.getOrganization());
            view.getContext().startActivity(sInfo);
        }
    }
}
