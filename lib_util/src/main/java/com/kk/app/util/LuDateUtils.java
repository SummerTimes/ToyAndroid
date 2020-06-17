package com.kk.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期相关
 */
public class LuDateUtils {

    public static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat COMPLETE_FORMAT_ONE = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat COMPLETE_FORMAT_TWO = new SimpleDateFormat(
            "yyyy.MM.dd HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat COMPLETE_FORMAT_THREE = new SimpleDateFormat(
            "yyyyMMdd-HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat COMPLETE_FORMAT_FOUR = new SimpleDateFormat(
            "yyyyMMddHHmmss", Locale.CHINA);
    public static final SimpleDateFormat COMPLETE_FORMAT_FIVE = new SimpleDateFormat(
            "yyMMddHHmmss", Locale.CHINA);
    public static final SimpleDateFormat COMPLETE_FORMAT_SIX = new SimpleDateFormat(
            "yyyyMMdd HH:mm:ss", Locale.CHINA);

    public static final SimpleDateFormat YMD_FORMAT_ONE = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CHINA);
    public static final SimpleDateFormat YMD_FORMAT_TWO = new SimpleDateFormat(
            "yyyy/MM/dd", Locale.CHINA);
    public static final SimpleDateFormat YMD_FORMAT_THREE = new SimpleDateFormat(
            "yyyy.MM.dd", Locale.CHINA);
    public static final SimpleDateFormat YMD_FORMAT_FOUR = new SimpleDateFormat(
            "yy-MM-dd", Locale.CHINA);
    public static final SimpleDateFormat YMD_FORMAT_FIVE = new SimpleDateFormat(
            "yy/MM/dd", Locale.CHINA);
    public static final SimpleDateFormat YMD_FORMAT_SIX = new SimpleDateFormat(
            "yy.MM.dd", Locale.CHINA);
    public static final SimpleDateFormat YMD_FORMAT_SEVEN = new SimpleDateFormat(
            "yyyyMMdd", Locale.CHINA);
    public static final SimpleDateFormat HMS_FORMAT_ONE = new SimpleDateFormat(
            "HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat HMS_FORMAT_TWO = new SimpleDateFormat(
            "HHmmss", Locale.CHINA);
    public static final SimpleDateFormat HH_FORMAT = new SimpleDateFormat("HH", Locale.CHINA);

    /**
     * long时间转换为String
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTimeStr(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long时间转换为String, 格式为 {@link #DEFAULT_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTimeStr(long timeInMillis) {
        return getTimeStr(timeInMillis, DEFAULT_FORMAT);
    }

    /**
     * 获得当前时间(毫秒)
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间,格式为{@link #DEFAULT_FORMAT}
     *
     * @return String
     */
    public static String getCurrentTimeInString() {
        return getTimeStr(getCurrentTimeInLong());
    }

    /**
     * 以指定格式获取当前时间
     *
     * @param dateFormat
     * @return String
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTimeStr(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * 判断某一时间与当前时间戳的间隔是否在某一时间段之内
     *
     * @param beforeTimeLong 与当前时间比较的某一时间戳
     * @param periodLong     某一时间段
     * @return 如果当前时间与某一时间在这一时间段内，返回true，否则返回false
     */
    public static boolean isInDeltaTime(long beforeTimeLong, long periodLong) {
        return System.currentTimeMillis() - beforeTimeLong < periodLong;
    }

    /**
     * 将秒转成时、分
     *
     * @param second
     * @return String
     */
    public static String secondToHourMinute(int second) {
        String result = "";
        if (second < 60 * 60) {
            result = second / 60 + "分钟";
        } else {
            result = second / (60 * 60) + "小时" + (second % (60 * 60) / 60)
                    + "分";
        }
        return result;
    }

    /**
     * 将毫秒秒转成秒、分
     *
     * @param millisecond
     * @return String
     */
    public static String millisecondToMinutesecond(long millisecond) {
        String result = "";
        millisecond = millisecond / 1000;
        if (millisecond < 60) {
            result = millisecond / 60 + "秒";
        } else {
            result = (millisecond / 60) + "分" + (millisecond % 60) + "秒";
        }
        return result;
    }

    /**
     * 传入两个日期数组,计算两个日期相隔天数
     *
     * @param startDate 例:[2013,5,12]
     * @param endDate   例:[2014,11,2]
     * @return
     */
    public static String getDaysInterval(int[] startDate, int[] endDate) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(startDate[0], startDate[1] - 1, startDate[2]); // 将日历翻到2013年六月十二日，5表示六月
        long timeOne = calendar.getTimeInMillis();
        calendar.set(endDate[0], endDate[1] - 1, endDate[2]); // 将日历翻到2014年十一月二日,2表示三月
        long timeTwo = calendar.getTimeInMillis();
        long headway = (timeTwo - timeOne) / (1000 * 60 * 60 * 24);
        return String.valueOf(headway);
    }

    /**
     * 根据输入的年和月份,获取当月天数
     *
     * @param dyear
     * @param dmonth
     * @return int 当月天数
     */
    public static int getDaysByYearAndMonth(String dyear, String dmonth) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM",
                Locale.getDefault());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(simpleDate.parse(dyear + "/" + dmonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);// 根据年月 获取月份天数
    }

    /**
     * 比较两个日期
     *
     * @param firstDate
     * @param secondDate
     * @return boolean secondDate 在 firstDate之后返回true
     */
    public static boolean compareDate(int[] firstDate, int[] secondDate) {
        Calendar oneCalendar = Calendar.getInstance();
        Calendar twoCalendar = Calendar.getInstance();
        oneCalendar.set(firstDate[0], firstDate[1] - 1, firstDate[2]);
        twoCalendar.set(secondDate[0], secondDate[1] - 1, secondDate[2]);
        return twoCalendar.after(oneCalendar);
    }

    /**
     * 字符串转换为Date
     *
     * @param sdate 字符串
     * @param sdf   字符串的格式
     * @return 成功返回Date 失败返回null
     */
    public static Date stringToDate(String sdate, SimpleDateFormat sdf) {
        try {
            return sdf.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Date转换为字符串
     *
     * @param date
     * @param outSdf 转换后的格式
     * @return String
     */
    public static String datetoString(Date date, SimpleDateFormat outSdf) {
        return outSdf.format(date);
    }

    /**
     * 格式转换
     *
     * @param sdate  字符串
     * @param inSdf  格式
     * @param outSdf 转换后的格式
     * @return null: 转换失败 String 转换后的字符串
     */
    public static String changeDateTimeFormat(String sdate,
                                              SimpleDateFormat inSdf, SimpleDateFormat outSdf) {
        Date date = stringToDate(sdate, inSdf);
        if (date != null) {
            return datetoString(date, outSdf);
        }
        return null;
    }

    /**
     * 判断当前月份是不是传入的月份
     *
     * @param month
     * @return
     */
    public static boolean isCurrentMonth(int month) {
        Calendar now = Calendar.getInstance();
        int curMonth = now.get(Calendar.MONTH) + 1;

        return curMonth == month;
    }

}
