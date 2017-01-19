package cz.devconf2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by jridky on 10.12.16.
 */

public class SpeakerDetail extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.avatar)
    ImageView vAvatar;
    @BindView(R.id.name)
    TextView vName;
    @BindView(R.id.bio)
    TextView vBio;
    @BindView(R.id.twitter)
    TextView vTwitter;
    @BindView(R.id.talks)
    TextView vTalks;

    String name, avatar, bio, country, twitter, org, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        name = getIntent().getStringExtra("name");
        avatar = getIntent().getStringExtra("avatar");
        bio = getIntent().getStringExtra("bio");
        country = getIntent().getStringExtra("country");
        twitter = getIntent().getStringExtra("twitter");
        org = getIntent().getStringExtra("organization");
        id = getIntent().getStringExtra("id");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference sr = storage.getReferenceFromUrl("gs://" + FirebaseApp.getInstance().getOptions().getStorageBucket());
        Glide.with(this).using(new FirebaseImageLoader()).load(sr.child("speakers/" + avatar.toLowerCase() + ".jpg"))
                .fitCenter().placeholder(R.drawable.default_avatar)
                .into(vAvatar);

        vName.setText(name + " " + " (" + country + ", " + org + ")");
        vBio.setText(bio);

        if(!twitter.equalsIgnoreCase("")){
            vTwitter.setText(Html.fromHtml("Twitter: <a href='https://twitter.com/" + twitter + "'>@" + twitter + "</a>"));
            vTwitter.setMovementMethod(LinkMovementMethod.getInstance());
        }

        vTalks.setText("Talks: " + MainActivity.TALKS.getSpeakerTalks(id));
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
}
