package cz.devconf2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by jridky on 5.1.17.
 */
public class FloorPlanFragment extends Fragment {
    SubsamplingScaleImageView image;

        View view;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_floorplan, container, false);

            image = (SubsamplingScaleImageView) view.findViewById(R.id.image);
            image.setImage(ImageSource.resource(R.drawable.floorplan));

            return view;
        }
}
