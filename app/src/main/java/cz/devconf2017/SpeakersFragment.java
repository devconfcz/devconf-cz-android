package cz.devconf2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpeakersFragment extends Fragment {
    @BindView(R.id.enter_info)
    TextView signUpText;
    @BindView(R.id.loading_label)
    TextView loadingLabel;
    @BindView(R.id.loading_bar)
    ProgressBar progressBar;

    View view;
    static LinearLayout loading;
    static RecyclerView recycler;
    FirebaseRecyclerAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_speakers, container, false);

        ButterKnife.bind(view);

        loading = (LinearLayout) view.findViewById(R.id.loading_box);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new FirebaseRecyclerAdapter<Speaker, SpeakerViewHolder>(Speaker.class, R.layout.row_speakers, SpeakerViewHolder.class, MainActivity.SPEAKERS.myRef) {
            @Override
            public void populateViewHolder(SpeakerViewHolder speakerHolder, Speaker item, int position) {
                if(MainActivity.SPEAKERS.getSpeakers().size() > 0) {
                    speakerHolder.setSpeaker(MainActivity.SPEAKERS.getSpeakers().get(position));
                }
            }
        };
        mAdapter.notifyDataSetChanged();
        recycler.setAdapter(mAdapter);

        if(MainActivity.SPEAKERS.getSpeakers().size() > 0){
            setLoadingBox();
        }
        return view;
    }

    public void setLoadingBox(){
        loading.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }
}