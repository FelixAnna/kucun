package com.xiangyong.manager.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    /**
     * 解析UTC时间格式的字符串为Date
     * @param utcTime  格式"2017-09-07T04:35:53Z"
     * @return
     */
    public Date parseUTC(String utcTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.parse(utcTime);
    }

    public static String formatDate(Date date,String pattern){
        return new SimpleDateFormat().format(date);
    }

}
