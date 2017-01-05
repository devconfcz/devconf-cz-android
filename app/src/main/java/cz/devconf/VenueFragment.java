package cz.devconf;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by jridky on 11.12.16.
 */

public class VenueFragment extends Fragment {
    TextView data, title;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offline, container, false);

        data = (TextView) view.findViewById(R.id.data);
        title = (TextView) view.findViewById(R.id.title);

        title.setText(R.string.venue);
        data.setText(Html.fromHtml(getString(R.string.venueData), new Image(), null));
        data.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }


    public class Image implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int idd;

            idd = getResources().getIdentifier(source, "drawable", getActivity().getPackageName());
            Display display = getActivity().getWindowManager().getDefaultDisplay();

            Drawable d = getResources().getDrawable(idd);

            float mult = 0.8f * display.getWidth() / d.getIntrinsicWidth();

            if (mult > 1.0f) {
                mult = 1.0f;
            }

            d.setBounds(0, 0, (int) (d.getIntrinsicWidth() * mult), (int) (d.getIntrinsicHeight() * mult));

            return d;

        }

    }
}