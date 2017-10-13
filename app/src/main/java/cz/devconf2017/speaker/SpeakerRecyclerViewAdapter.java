package cz.devconf2017.speaker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cz.devconf2017.R;
import cz.devconf2017.Speaker;

class SpeakerRecyclerViewAdapter extends FirebaseRecyclerAdapter<Speaker, SpeakerHolder> {

    private static final int LAYOUT_ID = R.layout.holder_speaker;
    private static final String TAG = SpeakerRecyclerViewAdapter.class.getName();

    SpeakerRecyclerViewAdapter(FirebaseRecyclerOptions<Speaker> options) {
        super(options);
    }

    @Override
    public SpeakerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(LAYOUT_ID, parent, false);

        return new SpeakerHolder(view);
    }

    @Override
    protected void onBindViewHolder(SpeakerHolder holder, int position, Speaker speaker) {
        holder.setSpeaker(speaker);
    }
}
