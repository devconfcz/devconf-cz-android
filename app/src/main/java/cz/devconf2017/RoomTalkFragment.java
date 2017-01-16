package cz.devconf2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by jridky on 3.1.17.
 */
public class RoomTalkFragment extends Fragment {
    @BindView(R.id.loading_label)
    TextView loadingLabel;
    @BindView(R.id.loading_bar)
    ProgressBar progressBar;

    View view;
    static RecyclerView recycler;
    TalkRecycleViewAdapter mAdapter;
    int page, day;
    boolean favorites;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_talk, container, false);

        ButterKnife.bind(view);
        page = getArguments().getInt("index",0);
        day = getArguments().getInt("day",1);
        favorites = getArguments().getBoolean("favorites",false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new TalkRecycleViewAdapter(day, page, favorites);
        recycler.setAdapter(mAdapter);
        return view;
    }

}
