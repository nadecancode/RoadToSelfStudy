package me.allen.rtss.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static String formatDueDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        return simpleDateFormat.format(date);
    }

}
