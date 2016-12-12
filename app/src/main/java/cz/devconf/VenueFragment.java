package cz.devconf;

import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.fabric.sdk.android.Fabric.TAG;

/**
 * Created by jridky on 11.12.16.
 */

public class VenueFragment extends Fragment {
    TextView data;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_venue, container, false);

        data = (TextView) view.findViewById(R.id.data);

        data.setText(Html.fromHtml(getString(R.string.venueData), new Image(), null));
        data.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }


    public class Image implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int idd;

            idd = getResources().getIdentifier(source, "drawable", getActivity().getPackageName());
            Display display = getActivity().getWindowManager().getDefaultDisplay();

            Log.d(TAG, source);
            Drawable d = getResources().getDrawable(idd);

            float mult = 0.8f * display.getWidth() / d.getIntrinsicWidth();

            if (mult > 1.0f) {
                mult = 1.0f;
            }

            d.setBounds(0, 0, (int) (d.getIntrinsicWidth() * mult), (int) (d.getIntrinsicHeight() * mult));
            Log.d(TAG, "Width " + (d.getIntrinsicWidth() * mult) + " Height " + (d.getIntrinsicHeight() * mult) + " image " + source);

            return d;

        }

    }
}