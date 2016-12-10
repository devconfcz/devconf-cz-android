package cz.devconf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.fabric.sdk.android.Fabric.TAG;

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

    String name, avatar, bio, country, twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = getIntent().getStringExtra("name");
        avatar = getIntent().getStringExtra("avatar");
        bio = getIntent().getStringExtra("bio");
        country = getIntent().getStringExtra("country");
        twitter = getIntent().getStringExtra("twitter");

        Glide.with(this).load(avatar).placeholder(R.drawable.default_avatar).into(vAvatar);

        Log.d(TAG, "avatar: " + vAvatar.toString());
        vName.setText(name + " (" + country + ")");
        vBio.setText(bio);
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
