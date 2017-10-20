package cz.devconf2017.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormatUtils {

    public static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("HH:mm", Locale.US);
    public static final SimpleDateFormat DATE_FORMAT_TIME_INPUT = new SimpleDateFormat("HH:mm", Locale.US);
    public static final SimpleDateFormat DATE_FORMAT_DURATION = new SimpleDateFormat("H:mm", Locale.US);
    public static final SimpleDateFormat DATE_FORMAT_DURATION_INPUT = new SimpleDateFormat("H:mm:ss", Locale.US);
}
