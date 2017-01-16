package cz.devconf2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.fabric.sdk.android.Fabric.TAG;

/**
 * Created by jridky on 10.12.16.
 */

public class VotingDetail extends AppCompatActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    static LinearLayout loading;
    static RecyclerView recycler;
    FirebaseRecyclerAdapter mAdapter;

    List<Feedback> list;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        id = getIntent().getIntExtra("talk",0);

        Talk t = MainActivity.TALKS.findTalk(id);

        if(t == null)
                finish();

        list = new ArrayList<Feedback>();
        ButterKnife.bind(this);

        loading = (LinearLayout) findViewById(R.id.loading_box);
        recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference myRef = MainActivity.FBDB.getDatabase().getReference("votes");

        mAdapter = new FirebaseRecyclerAdapter<Feedback, VotingDetailViewHolder>(Feedback.class, R.layout.row_feedback, VotingDetailViewHolder.class, myRef) {
            @Override
            public void populateViewHolder(VotingDetailViewHolder votingHolder, Feedback item, int position) {
                if(list.size() > 0 ) {
                    votingHolder.setFeedback(list.get(position));
                }
            }

            @Override
            public int getItemCount() {
                Log.d("ITEMCOUNT", ""+list.size());
                return list.size();
            }

            @Override
            public Feedback getItem(int position){
                return list.get(position);
            }
        };

        recycler.setAdapter(mAdapter);


        myRef.child(String.valueOf(id)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(list.size() > 0){
                    list.clear();
                }
                for(DataSnapshot vote: dataSnapshot.getChildren()) {
                    Feedback f = vote.getValue(Feedback.class);
                    list.add(f);
                }
                Log.d("FEEDBACK", "" + list.size());
                setLoadingBox();
                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void setLoadingBox(){
        loading.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
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
