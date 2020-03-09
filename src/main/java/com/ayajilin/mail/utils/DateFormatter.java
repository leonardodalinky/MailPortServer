package com.ayajilin.mail.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String toLogTime(Date dt, String strLog) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd Z HH:mm:ss  ");
        return sf.format(dt) + strLog;
    }
}
