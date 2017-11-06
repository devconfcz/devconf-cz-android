package cz.devconf2017.offline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cz.devconf2017.R;
import cz.devconf2017.base.BaseFragment;
import cz.devconf2017.util.CustomImageGetter;
import cz.devconf2017.util.StringUtils;

public class VenueFragment extends BaseFragment {

    @BindView(R.id.data)
    TextView data;

    @BindView(R.id.title)
    TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_offline;
    }

    public static VenueFragment newInstance() {
        return new VenueFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title.setText(R.string.venue);
        data.setText(StringUtils.fromHtml(getString(R.string.venueData), new CustomImageGetter(getActivity())));
        data.setMovementMethod(LinkMovementMethod.getInstance());
    }

}