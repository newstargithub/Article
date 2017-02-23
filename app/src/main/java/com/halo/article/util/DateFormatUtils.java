package com.halo.article.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author: zx
 * Date: 2017/2/9
 * Description:
 */

public class DateFormatUtils {
    /**
     * 将long类date转换为String类型
     * @param date date
     * @return String date
     */
    public static String getZhihuDailyDateFormat(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        sDate = format.format(d);
        return sDate;
    }
}
