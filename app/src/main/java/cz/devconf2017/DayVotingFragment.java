package cz.devconf2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
 * Created by jridky on 3.1.17.
 */
public class DayVotingFragment extends Fragment {
    @BindView(R.id.loading_label)
    TextView loadingLabel;
    @BindView(R.id.loading_bar)
    ProgressBar progressBar;

    View view;
    LinearLayout loading;
    RecyclerView recycler;
    FirebaseRecyclerAdapter mAdapter;
    int day;

    List<Voting> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_speakers, container, false);

        list = new ArrayList<Voting>();
        ButterKnife.bind(view);

        loading = (LinearLayout) view.findViewById(R.id.loading_box);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        day = getArguments().getInt("index") + 1;

        DatabaseReference myRef = MainActivity.FBDB.getDatabase().getReference("votes");

        mAdapter = new FirebaseRecyclerAdapter<Voting, VotingViewHolder>(Voting.class, R.layout.row_voting, VotingViewHolder.class, myRef) {
            @Override
            public void populateViewHolder(VotingViewHolder votingHolder, Voting item, int position) {
                if(list.size() > 0) {
                    votingHolder.setVote(list.get(position));
                }
            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            @Override
            public Voting getItem(int position){
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
                        try{
                            int talkId = Integer.parseInt(ds.getKey());
                            int index = getVote(talkId);
                            Feedback fb = ds.getValue(Feedback.class);
                            if(index < 0) {
                                Voting voting = new Voting(talkId);
                                if(voting.talk.getDay() == day) {
                                    voting.add(Integer.parseInt(fb.rating));
                                    list.add(voting);
                                }
                            }else{
                                list.get(index).add(Integer.parseInt(fb.rating));
                            }
                        }catch(NumberFormatException e){
                            Log.e("NFE",e.getMessage());
                        }
                    }
                }

                countVotes();
                setLoadingBox();
                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return view;
    }

    public int getVote(int talkId){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).talk.getId() == talkId){
                return i;
            }
        }
        return -1;
    }

    public void countVotes(){
        Collections.sort(list, sortByVotes);
        for(int i = 0; i < list.size(); i++){
            list.get(i).addScore(i+1);
        }

        Collections.sort(list, sortByAvg);
        for(int i = 0; i < list.size(); i++){
            list.get(i).addScore(i+1);
        }

        Collections.sort(list, sortByScore);

    }

    public static Comparator<Voting> sortByVotes = new Comparator<Voting>() {
        @Override
        public int compare(Voting talk, Voting t1) {
            int vote = talk.numVotes;
            int vote2 = t1.numVotes;
            int dateComparision = vote - vote2;
            if(dateComparision > 0){
                return 1;
            }

            if(dateComparision < 0){
                return -1;
            }

            dateComparision = Float.compare(talk.getAvgVotes(), t1.getAvgVotes());

            if(dateComparision > 0){
                return 1;
            }

            if(dateComparision < 0){
                return -1;
            }

            return 0;
        }
    };

    public static Comparator<Voting> sortByAvg = new Comparator<Voting>() {
        @Override
        public int compare(Voting talk, Voting t1) {
            float vote = talk.getAvgVotes();
            float vote2 = t1.getAvgVotes();
            int dateComparision = Float.compare(vote,vote2);

            if(dateComparision > 0){
                return 1;
            }

            if(dateComparision < 0){
                return -1;
            }

            return 0;
        }
    };

    public static Comparator<Voting> sortByScore = new Comparator<Voting>() {
        @Override
        public int compare(Voting talk, Voting t1) {
            int vote = talk.score;
            int vote2 = t1.score;
            int dateComparision = vote2 - vote;

            if(dateComparision > 0){
                return 1;
            }

            if(dateComparision < 0){
                return -1;
            }

            dateComparision = t1.votes - talk.votes;

            if(dateComparision > 0){
                return 1;
            }

            if(dateComparision < 0){
                return -1;
            }

            return 0;

        }
    };

    public void setLoadingBox(){
        loading.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }

}
