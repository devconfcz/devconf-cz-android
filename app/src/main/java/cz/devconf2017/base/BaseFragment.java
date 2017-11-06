package cz.devconf2017.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Base class for all fragments, containing common logic. Child fragments should implement
 * onViewCreated in order to configure views.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected void showLoading() {
        ((BaseActivity) getActivity()).showLoading();
    }

    protected void hideLoading() {
        ((BaseActivity) getActivity()).hideLoading();
    }

    protected void showToast(int stringRes) {
        ((BaseActivity) getActivity()).showToast(stringRes);
    }
}
