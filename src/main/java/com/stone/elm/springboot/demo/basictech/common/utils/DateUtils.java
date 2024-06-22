package com.stone.elm.springboot.demo.basictech.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    private static final ThreadLocal<SimpleDateFormat> STAND_FORMAT_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final ThreadLocal<SimpleDateFormat> FORMAT_EIGHT_DAY = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));
    private static final ThreadLocal<SimpleDateFormat> FORMAT_SIX_DAY = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyMMdd"));

    private static final int NUM_ZERO = 0;
    private static final int NUM_ONE = 1;
    private static final int NUM_TWO = 2;
    private static final int NUM_SEVEN = 7;
    private static final int MILLIS = 1000;

    private DateUtils() {
    }

    /**
     * 当前天 00:00:00
     * @return
     */
    public static Calendar getDayStartCalender() {
        return getDayStartCalender(NUM_ZERO);
    }

    public static Long getDayStartSecond() {
        return getDayStartCalender(NUM_ZERO).getTimeInMillis() / MILLIS;
    }

    public static String getDayStartFormat() {
        SimpleDateFormat simpleDateFormat = STAND_FORMAT_LOCAL.get();
        return simpleDateFormat.format(getDayStartCalender(NUM_ZERO).getTime());
    }

    /**
     * N天后 00:00:00
     * @param n
     * @return
     */
    public static Calendar getDayStartCalender(int n) {
        Calendar calendar = Calendar.getInstance();

        // 获取偏移量
        int offset = calendar.get(Calendar.DATE) + n;
        // 设置偏移量
        calendar.set(Calendar.DATE, offset);
        // 当前时分秒归零
        setStartCalendar(calendar);

        return calendar;
    }

    public static Long getDayStartSecond(int n) {
        return getDayStartCalender(n).getTimeInMillis() / MILLIS;
    }

    public static String getDayStartFormat(int n) {
        SimpleDateFormat simpleDateFormat = STAND_FORMAT_LOCAL.get();
        return simpleDateFormat.format(getDayStartCalender(n).getTime());
    }

    /**
     * 当前周 周一 00:00:00
     * @return
     */
    public static Calendar getWeekStartCalender() {
        return getWeekStartCalender(NUM_ZERO);
    }

    public static Long getWeekStartSecond() {
        return getWeekStartCalender(NUM_ZERO).getTimeInMillis() / MILLIS;
    }

    public static String getWeekStartFormat() {
        SimpleDateFormat simpleDateFormat = STAND_FORMAT_LOCAL.get();
        return simpleDateFormat.format(getWeekStartCalender(NUM_ZERO).getTime());
    }

    /**
     * N周后 周一 00:00:00
     * @param n
     * @return
     */
    public static Calendar getWeekStartCalender(int n) {
        Calendar calendar = Calendar.getInstance();

        // 当前时分秒归零
        setStartCalendar(calendar);
        // 当前是本周第几天
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // 获取本周周一的时间
        int weekOffset = dayOfWeek == NUM_ONE ? NUM_ONE - Calendar.DAY_OF_WEEK : NUM_TWO - dayOfWeek;
        calendar.add(Calendar.DAY_OF_MONTH, weekOffset);
        // 获取偏移量
        int offset = calendar.get(Calendar.DATE) + (NUM_SEVEN * n);
        // 设置偏移量
        calendar.set(Calendar.DATE, offset);

        return calendar;
    }

    public static Long getWeekStartSecond(int n) {
        return getWeekStartCalender(n).getTimeInMillis() / MILLIS;
    }

    public static String getWeekStartFormat(int n) {
        SimpleDateFormat simpleDateFormat = STAND_FORMAT_LOCAL.get();
        return simpleDateFormat.format(getWeekStartCalender(n).getTime());
    }

    /**
     * 当前月 月初 00:00:00
     * @return
     */
    public static Calendar getMonthStartCalender() {
        return getMonthStartCalender(NUM_ZERO);
    }

    public static Long getMonthStartSecond() {
        return getMonthStartCalender(NUM_ZERO).getTimeInMillis() / MILLIS;
    }

    public static String getMonthStartFormat() {
        SimpleDateFormat simpleDateFormat = STAND_FORMAT_LOCAL.get();
        return simpleDateFormat.format(getMonthStartCalender(NUM_ZERO).getTime());
    }

    /**
     * N月后 月初 00:00:00
     * @param n
     * @return
     */
    public static Calendar getMonthStartCalender(int n) {
        Calendar calendar = Calendar.getInstance();

        // 当前时分秒归零
        setStartCalendar(calendar);
        // 时间设置到月初
        calendar.set(Calendar.DAY_OF_MONTH, NUM_ONE);
        // 获取偏移量
        int offset = calendar.get(Calendar.MONTH) + n;
        // 设置偏移量
        calendar.set(Calendar.MONTH, offset);

        return calendar;
    }

    public static Long getMonthStartSecond(int n) {
        return getMonthStartCalender(n).getTimeInMillis() / MILLIS;
    }

    public static String getMonthStartFormat(int n) {
        SimpleDateFormat simpleDateFormat = STAND_FORMAT_LOCAL.get();
        return simpleDateFormat.format(getMonthStartCalender(n).getTime());
    }

    /**
     * 时分秒归零
     * @param calendar
     */
    private static void setStartCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, NUM_ZERO);
        calendar.set(Calendar.MINUTE, NUM_ZERO);
        calendar.set(Calendar.SECOND, NUM_ZERO);
    }


    public static String getCurrentSixDigitDay() {
        SimpleDateFormat simpleDateFormat = FORMAT_SIX_DAY.get();
        return simpleDateFormat.format(getDayStartCalender().getTime());
    }

    public static String getCurrentEightDigitDay() {
        SimpleDateFormat simpleDateFormat = FORMAT_EIGHT_DAY.get();
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    public static Long getCurrentTimeStamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String getCurrentFormat() {
        SimpleDateFormat simpleDateFormat = STAND_FORMAT_LOCAL.get();
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }
}
