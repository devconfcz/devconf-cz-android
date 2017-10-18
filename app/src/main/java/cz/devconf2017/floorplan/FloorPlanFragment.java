package cz.devconf2017.floorplan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import butterknife.BindView;
import cz.devconf2017.R;
import cz.devconf2017.base.BaseFragment;

public class FloorPlanFragment extends BaseFragment {

    @BindView(R.id.floor_plan_image)
    protected SubsamplingScaleImageView image;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_floorplan;
    }

    public static FloorPlanFragment newInstance() {
        return new FloorPlanFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image.setImage(ImageSource.resource(R.drawable.floorplan));
    }
}
