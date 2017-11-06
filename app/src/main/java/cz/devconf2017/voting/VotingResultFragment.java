package cz.devconf2017.voting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.BindView;
import cz.devconf2017.R;
import cz.devconf2017.base.BaseFragment;

public class VotingResultFragment extends BaseFragment {

    private static final String ARG_SELECTED_TAB = "ARG_SELECTED_TAB";

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voting_result;
    }

    public static VotingResultFragment newInstance(int selectedTab) {
        VotingResultFragment fragment = new VotingResultFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SELECTED_TAB, selectedTab);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        VotingResultsPagerAdapter pagerAdapter = new VotingResultsPagerAdapter(
                getFragmentManager(),
                getContext()
        );
        viewPager.setAdapter(pagerAdapter);

        // tabs.setTabsFromPagerAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);

        // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }
}
