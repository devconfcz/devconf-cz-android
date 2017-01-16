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

    String name, avatar, bio, country, twitter, org;

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

        Glide.with(this).load(avatar).fitCenter().placeholder(R.drawable.default_avatar).into(vAvatar);

        vName.setText(name + " " + " (" + country + ", " + org + ")");
        vBio.setText(bio);

        if(!twitter.equalsIgnoreCase("")){
            vTwitter.setText(Html.fromHtml("Twitter: <a href='https://twitter.com/" + twitter + "'>@" + twitter + "</a>"));
            vTwitter.setMovementMethod(LinkMovementMethod.getInstance());
        }
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
