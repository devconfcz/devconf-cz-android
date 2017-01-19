package cz.devconf2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
                return list.size();
            }

            @Override
            public Feedback getItem(int position){
                return list.get(position);
            }
        };

        recycler.setAdapter(mAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(list.size() > 0){
                    list.clear();
                }

                for(DataSnapshot vote: dataSnapshot.getChildren()) {
                    Iterator talk = vote.getChildren().iterator();
                    while (talk.hasNext()) {
                        DataSnapshot ds = (DataSnapshot) talk.next();
                        int talkId = Integer.parseInt(ds.getKey());
                        if (id == talkId){
                            Feedback f = ds.getValue(Feedback.class);
                            list.add(f);
                        }
                    }
                }

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
