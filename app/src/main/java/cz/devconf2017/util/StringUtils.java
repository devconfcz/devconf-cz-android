package cz.devconf2017.util;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Html;
import android.text.Spanned;

public class StringUtils {

    public static Spanned fromHtml(String html) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    public static Spanned fromHtml(String html, Html.ImageGetter imageGetter) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
        } else {
            return Html.fromHtml(html, imageGetter, null);
        }
    }

}
