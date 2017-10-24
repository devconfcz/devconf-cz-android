package cz.devconf2017.now;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import cz.devconf2017.HomeRecycleViewAdapter;
import cz.devconf2017.R;
import cz.devconf2017.base.BaseFragment;
import cz.devconf2017.util.Constants;
import cz.devconf2017.util.DateFormatUtils;
import cz.devconf2017.util.view.EmptyRecyclerView;

public class NowFragment extends BaseFragment {

    @BindView(R.id.now_conference_ended)
    protected TextView conferenceEndedLabel;

    @BindView(R.id.now_recycler_view)
    protected EmptyRecyclerView recycler;

    @BindView(R.id.now_empty_view)
    protected View emptyView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_now;
    }

    public static NowFragment newInstance() {
        return new NowFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isDevConfFinished()) {
            conferenceEndedLabel.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
        } else {
            conferenceEndedLabel.setVisibility(View.GONE);

            HomeRecycleViewAdapter adapter = new HomeRecycleViewAdapter();

            recycler.setAdapter(adapter);
            recycler.setEmptyView(emptyView);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private boolean isDevConfFinished() {
        try {
            Date now = new Date();
            Date lastDay = DateFormatUtils.DATE_FORMAT_DATE.parse(Constants.LAST_DAY_STR);
            return now.compareTo(lastDay) >= 0;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}