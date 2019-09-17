package com.torinosrc.commons.utils;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获得本周一与当前日期相差的天数
     *
     * @return
     */
    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    public static Long getCurrentDayStartTime() {
        long current = System.currentTimeMillis();
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        return zero;

    }

    public static Long getCurrentDayEndTime() {
        long current = System.currentTimeMillis();
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        long twelve = zero + 24 * 60 * 60 * 1000;
//        long twelve = zero + 24*60*60*1000 - 1;
        return twelve;
    }

    /**
     * 获得当前周- 周一的日期
     *
     * @return
     */
    public static Long getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        //将时、分、秒、毫秒 清零
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        Date monday = currentDate.getTime();
        Long preMonday = monday.getTime();

        return preMonday;
    }


    /**
     * 获得当前周- 周日  的日期
     *
     * @return
     */
    public static Long getCurrentSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        //将时、分、秒、毫秒 清零
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        Date sunday = currentDate.getTime();
        Long preSunday = sunday.getTime();
        return preSunday;
    }

    /**
     * 获得当前月--开始日期
     *
     * @return
     */
    public static Long getCurrentMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将时、分、秒、毫秒 清零
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().getTime();
    }

    /**
     * 获得当前月--结束日期
     *
     * @return
     */
    public static Long getCurrentMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        //将时、分、秒、毫秒 清零
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().getTime();
    }

    // 日期转字符串格式
    public static String timeStamp2Date(String seconds, String format) {
        if (StringUtils.isEmpty(seconds) || StringUtils.isEmpty(format)) {
            throw new TorinoSrcServiceException("参数都不能为空");
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    // 字符串转日期格式
    public static Date StrToDate(String str, String formatString) {

        if (formatString == null || formatString.isEmpty()) {
            formatString = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatString);

        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new TorinoSrcServiceException("字符串转日期出现异常");
        }
        return date;
    }

    /**
     * 获取给定时间 当天0点的时间戳
     *
     * @param time
     * @return
     */
    public static Long getDayStartTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));

        //将时、分、秒、毫秒 清零
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().getTime();
    }

    /**
     * 获取给定时间 当天24点的时间戳
     *
     * @param time
     * @return
     */
    public static Long getDayEndTime(Long time) {
        Long startTime = getDayStartTime(time);
        Long oneDayTimeMillis = 60000 * 60 * 24L;
        return startTime + oneDayTimeMillis;
    }
}
