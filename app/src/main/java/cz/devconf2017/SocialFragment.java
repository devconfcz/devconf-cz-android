package cz.devconf2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jridky on 5.1.17.
 */
public class SocialFragment extends Fragment {
        TextView data, title;

        View view;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_offline, container, false);

            title = (TextView) view.findViewById(R.id.title);
            data = (TextView) view.findViewById(R.id.data);

            title.setText(R.string.social_event);
            data.setText(Html.fromHtml(getString(R.string.socialData)));
            data.setMovementMethod(LinkMovementMethod.getInstance());
            return view;
        }
}
