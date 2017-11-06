package cz.devconf2017.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Display;

public class CustomImageGetter implements Html.ImageGetter {

    private final Activity activity;

    public CustomImageGetter(Activity activity) {
        this.activity = activity;
    }

    public Drawable getDrawable(String source) {
        int idd;

        idd = activity.getResources().getIdentifier(source, "drawable", activity.getPackageName());
        Display display = activity.getWindowManager().getDefaultDisplay();

        Drawable d = activity.getResources().getDrawable(idd);

        float mult = 0.8f * display.getWidth() / d.getIntrinsicWidth();

        if (mult > 1.0f) {
            mult = 1.0f;
        }

        d.setBounds(0, 0, (int) (d.getIntrinsicWidth() * mult), (int) (d.getIntrinsicHeight() * mult));

        return d;
    }

}
