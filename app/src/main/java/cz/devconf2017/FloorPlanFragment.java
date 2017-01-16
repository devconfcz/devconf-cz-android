package cz.devconf2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.IPhotoView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by jridky on 5.1.17.
 */
public class FloorPlanFragment extends Fragment {
        PhotoView image;

        View view;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_floorplan, container, false);

            image = (PhotoView) view.findViewById(R.id.image);
            Glide.with(this).load(R.drawable.floorplan).into(image);

            return view;
        }
}
