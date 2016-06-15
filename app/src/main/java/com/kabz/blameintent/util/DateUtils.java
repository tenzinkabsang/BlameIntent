package com.kabz.blameintent.util;

import android.text.format.DateFormat;

import java.util.Date;

public final class DateUtils {
    private DateUtils() {
    }

    public static String formatLong(final Date date) {
        return date == null ? "" : DateFormat.format("EE, MMMM d, yyyy", date).toString();
    }

    public static String formatShort(final Date date) {
        return date == null ? "" : DateFormat.format("EEE, MMM dd", date).toString();
    }
}
