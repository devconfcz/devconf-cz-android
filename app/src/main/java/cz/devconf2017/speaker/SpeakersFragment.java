package cz.devconf2017.speaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import cz.devconf2017.R;
import cz.devconf2017.Speaker;
import cz.devconf2017.base.BaseFragment;
import cz.devconf2017.util.view.EmptyRecyclerView;

public class SpeakersFragment extends BaseFragment {

    private static final String TAG = SpeakersFragment.class.getName();

    @BindView(R.id.speakers_recycler_view)
    EmptyRecyclerView recycler;

    @BindView(R.id.empty_view)
    TextView emptyView;

    SpeakerRecyclerViewAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_speakers;
    }

    public static SpeakersFragment newInstance() {
        SpeakersFragment fragment = new SpeakersFragment();

        return fragment;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showLoading();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("speakers");
        FirebaseRecyclerOptions<Speaker> options = new FirebaseRecyclerOptions.Builder<Speaker>()
                .setQuery(myRef, Speaker.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new SpeakerRecyclerViewAdapter(options) {
            @Override
            public void onDataChanged() {
                hideLoading();
            }

            @Override
            public void onError(DatabaseError error) {
                super.onError(error);
                hideLoading();
                showToast(R.string.fui_general_error);
            }
        };

        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setEmptyView(emptyView);
    }
}