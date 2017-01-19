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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jridky on 13.1.17.
 */
public class FavoritesFragment extends Fragment {
    @BindView(R.id.loading_label)
    TextView loadingLabel;
    @BindView(R.id.loading_bar)
    ProgressBar progressBar;

    View view;
    static LinearLayout loading;
    static TextView confEnd, title;
    static RecyclerView recycler;
    public HomeRecycleViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(view);
        title = (TextView) view.findViewById(R.id.title);
        title.setText(R.string.favorites);

        loading = (LinearLayout) view.findViewById(R.id.loading_box);
        confEnd = (TextView) view.findViewById(R.id.confernceend);
        confEnd.setText(R.string.nofavorites);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new HomeRecycleViewAdapter();
        loading.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
        confEnd.setVisibility(View.GONE);

        MainActivity.FAVORITES.adapter = mAdapter;
        mAdapter.fragment = this;
        recycler.setAdapter(mAdapter);
        if (MainActivity.FAVORITES.getTalks().size() > 0) {
            mAdapter.updateData(MainActivity.FAVORITES.getTalks());
            setLoadingBox();
        }

        return view;
    }


    public void setLoadingBox() {
        loading.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
        confEnd.setVisibility(View.GONE);
    }

    public void noFavorites() {
        loading.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
        confEnd.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume(){
        mAdapter.updateData(MainActivity.FAVORITES.getTalks());
        if(mAdapter.getItemCount() == 0){
            noFavorites();
        }else{
            setLoadingBox();
        }
        super.onResume();
    }
}