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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.loading_label)
    TextView loadingLabel;
    @BindView(R.id.loading_bar)
    ProgressBar progressBar;

    View view;
    static LinearLayout loading;
    static TextView confEnd;
    static RecyclerView recycler;
    public HomeRecycleViewAdapter mAdapter;
    static Date dayFour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        try {
            dayFour = sdf.parse("31/1/2017");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ButterKnife.bind(view);
        loading = (LinearLayout) view.findViewById(R.id.loading_box);
        confEnd = (TextView) view.findViewById(R.id.confernceend);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new HomeRecycleViewAdapter();
        if(new Date().compareTo(dayFour) >= 0){
            loading.setVisibility(View.GONE);
            recycler.setVisibility(View.GONE);
            confEnd.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            confEnd.setVisibility(View.GONE);
        }

        MainActivity.ALLLOADED.fragment = this;
        recycler.setAdapter(mAdapter);
        if (MainActivity.TALKS.getActualTalks().size() > 0) {
            mAdapter.updateData(MainActivity.TALKS.getActualTalks());
            setLoadingBox();
        }

        return view;
    }


    public void setLoadingBox(){
        loading.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }
}