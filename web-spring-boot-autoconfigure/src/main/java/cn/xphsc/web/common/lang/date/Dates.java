/*
 * Copyright (c) 2022 huipei.x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xphsc.web.common.lang.date;




import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.common.lang.date.format.DatesFormat;
import cn.xphsc.web.common.lang.date.format.FastDateFormat;
import cn.xphsc.web.common.lang.regexp.Matches;
import cn.xphsc.web.common.lang.type.ConvertTypes;
import cn.xphsc.web.utils.StringUtils;
import static cn.xphsc.web.common.lang.date.format.DatesFormat.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  日期时间工具类
 * @since 1.1.6
 */
public class Dates  {

    public Dates() {
    }

    public static Date date() {
        return new Date();
    }

    public static Date date(int ms) {
        return new Date(ms * SECOND_STAMP);
    }

    public static Date date(long ms) {
        return new Date(ms);
    }

    /**
     * 将对象转换为时间
     * LocalTime 不支持转化
     *
     * @param o 对象
     * @return 时间
     */
    public static Date date(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Integer) {
            return date((int) o * SECOND_STAMP);
        } else if (o instanceof Long) {
            long l = (long) o;
            if (isMilli(l)) {
                return date(l);
            } else {
                return date(l * SECOND_STAMP);
            }
        } else if (o instanceof byte[] || o instanceof Byte[] || o instanceof short[] || o instanceof Short[]
                || o instanceof int[] || o instanceof Integer[] || o instanceof long[] || o instanceof Long[]
                || o instanceof float[] || o instanceof Float[] || o instanceof double[] || o instanceof Double[]
                || o instanceof char[] || o instanceof Character[] || o instanceof String[]) {
            try {
                int[] analysis = ConvertTypes.toInts(o);
                if (analysis.length == 3) {
                    return build(analysis[0], analysis[1], analysis[2]);
                } else if (analysis.length == 6) {
                    return build(analysis[0], analysis[1], analysis[2], analysis[3], analysis[4], analysis[5]);
                } else if (analysis.length == 7) {
                    return build(analysis[0], analysis[1], analysis[2], analysis[3], analysis[4], analysis[5], analysis[6]);
                }
            } catch (Exception e) {
                return null;
            }
        } else if (o instanceof Date) {
            return (Date) o;
        } else if (o instanceof Calendar) {
            return ((Calendar) o).getTime();
        } else if (o instanceof String) {
            return parse((String) o);
        } else if (o instanceof LocalDate) {
            return Dates8.date((LocalDate) o);
        } else if (o instanceof LocalDateTime) {
            return Dates8.date((LocalDateTime) o);
        } else if (o instanceof Instant) {
            return Dates8.date((Instant) o);
        }
        return null;
    }

    public static Calendar calendar() {
        return Calendar.getInstance();
    }

    public static Calendar calendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static Calendar calendar(long milliSecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliSecond);
        return c;
    }

    /**
     * 获取日历
     * @param o o
     * @return 日历
     */
    public static Calendar calendar(Object o) {
        Date date = date(o);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 解构时间
     * @param d 时间
     * @return [y, M, d, H, m, s, S]
     */
    public static int[] analysis(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return new int[]{
                c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE), c.get(Calendar.SECOND), c.get(Calendar.MILLISECOND)
        };
    }

    public static Date build(int year, int month, int day) {
        return build(year, month, day, 0, 0, 0, 0);
    }

    public static Date build(int year, int month, int day, int h, int m, int s) {
        return build(year, month, day, h, m, s, 0);
    }

    /**
     * 构建时间
     * @param year  年
     * @param month 月
     * @param day   日
     * @param h     时
     * @param m     分
     * @param s     秒
     * @param ms    毫秒
     * @return 时间
     */
    public static Date build(int year, int month, int day, int h, int m, int s, int ms) {
        Calendar c = calendar();
        c.set(year, month - 1, day, h, m, s);
        if (ms != 0) {
            c.set(Calendar.MILLISECOND, ms);
        }
        return c.getTime();
    }

    public static String current() {
        return FastDateFormat.getInstance(YMD_HMS).format(new Date());
    }

    /**
     * 获取当前时间
     * @param pattern 格式
     * @return 当前时间
     */
    public static String current(String pattern) {
        return FastDateFormat.getInstance(pattern).format(new Date());
    }

    public static Date clearHms() {
        return clearHms(calendar());
    }

    public static Date clearHms(Date d) {
        return clearHms(calendar(d));
    }

    /**
     * 日期的 00:00:00
     * 清除时分秒
     * @param c 时间
     * @return 清除后的时间
     */
    public static Date clearHms(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date dayEnd() {
        return dayEnd(calendar());
    }

    public static Date dayEnd(Date d) {
        return dayEnd(calendar(d));
    }

    /**
     * 日期的 23:59:59
     * @param c 时间
     * @return 23:59:59
     */
    public static Date dayEnd(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static Date monthFirstDay() {
        return monthFirstDay(calendar(), false);
    }

    public static Date monthFirstDay(Date d) {
        return monthFirstDay(calendar(d), false);
    }

    /**
     * 设置日期为1号
     * @param c 时间
     * @return 时间
     */
    public static Date monthFirstDay(Calendar c) {
        return monthFirstDay(c, false);
    }

    public static Date monthFirstDayHms() {
        return monthFirstDay(calendar(), true);
    }

    public static Date monthFirstDayHms(Date d) {
        return monthFirstDay(calendar(d), true);
    }

    /**
     * 设置日期为1号, 清除HMS
     *
     * @param c 时间
     * @return 时间
     */
    public static Date monthFirstDayHms(Calendar c) {
        return monthFirstDay(c, true);
    }

    public static Date monthFirstDay(Date d, boolean clearHms) {
        return monthFirstDay(calendar(d), clearHms);
    }

    /**
     * 设置日期为当月1号
     * @param c        时间
     * @param clearHms 是否清除HMS
     * @return 时间
     */
    public static Date monthFirstDay(Calendar c, boolean clearHms) {
        c.set(Calendar.DAY_OF_MONTH, 1);
        if (clearHms) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        }
        return c.getTime();
    }

    public static Date monthLastDay() {
        return monthLastDay(new Date(), false);
    }

    public static Date monthLastDay(Date d) {
        return monthLastDay(d, false);
    }

    /**
     * 设置日期为月份的最后一天
     * @param c 时间
     * @return 时间
     */
    public static Date monthLastDay(Calendar c) {
        return monthLastDay(c, false);
    }

    public static Date monthLastDayHms() {
        return monthLastDay(new Date(), true);
    }

    public static Date monthLastDayHms(Date d) {
        return monthLastDay(d, true);
    }

    /**
     * 设置日期为月份的最后一天 时间的最后一秒
     * @param c 时间
     * @return 时间
     */
    public static Date monthLastDayHms(Calendar c) {
        return monthLastDay(c, true);
    }

    public static Date monthLastDay(Date d, boolean dayEnd) {
        return monthLastDay(calendar(d), dayEnd);
    }

    /**
     * 月份的最后一天
     * @param c      时间
     * @param dayEnd 是否将时间 转化为 23:59:59
     * @return 时间
     */
    public static Date monthLastDay(Calendar c, boolean dayEnd) {
        c.set(Calendar.DAY_OF_MONTH, DatesFormat.getMonthLastDay(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1));
        if (dayEnd) {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
        }
        return c.getTime();
    }

    public static Date[] getIncrementDayDates(int incr, int times) {
        return getIncrementDates(null, Calendar.DAY_OF_MONTH, incr, times);
    }

    public static Date plusMonths( Date date, int months) {
        if (months == 0) {
            return date;
        } else if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(2, months);
            return cal.getTime();
        }
    }

    public static Date plusDays(Date date, int days) {
        if (days == 0) {
            return date;
        } else if (date == null) {
            return null;
        } else {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);
            cal.add(5, days);
            return cal.getTime();
        }
    }

    public static Date plusMins(Date date, int mins) {
        if (mins == 0) {
            return date;
        } else if (date == null) {
            return null;
        } else {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);
            cal.add(12, mins);
            return cal.getTime();
        }
    }



    /**
     * 获取自增时间
     * @param d     开始时间
     * @param incr  自增时间(+ -)
     * @param times 自增次数
     * @return 时间
     */
    public static Date[] getIncrementDayDates(Date d, int incr, int times) {
        return getIncrementDates(d, Calendar.DAY_OF_MONTH, incr, times);
    }

    public static Date[] getIncrementHourDates(int incr, int times) {
        return getIncrementDates(null, Calendar.HOUR_OF_DAY, incr, times);
    }

    /**
     * 获取自增时间
     * @param d     开始时间
     * @param incr  自增时间(+ -)
     * @param times 自增次数
     * @return 时间
     */
    public static Date[] getIncrementHourDates(Date d, int incr, int times) {
        return getIncrementDates(d, Calendar.HOUR_OF_DAY, incr, times);
    }

    public static Date[] getIncrementDates(int field, int incr, int times) {
        return getIncrementDates(null, field, incr, times);
    }

    /**
     * 获取自增时间
     * @param d     开始时间
     * @param field 时间字段
     * @param incr  自增时间(+ -)
     * @param times 自增次数
     * @return 时间
     */
    public static Date[] getIncrementDates(Date d, int field, int incr, int times) {
        Date[] dates = new Date[times];
        Calendar c = calendar();
        if (d != null) {
            c.setTime(d);
        }
        dates[0] = c.getTime();
        for (int i = 0; i < times - 1; i++) {
            c.add(field, incr);
            dates[i + 1] = c.getTime();
        }
        return dates;
    }

    public static String format(Date d) {
        return FastDateFormat.getInstance(YMD_HMS).format(d);
    }

    public static String format(Date d, String pattern) {
        return d == null ? null : FastDateFormat.getInstance(pattern).format(d);
    }

    public static String format(Date d, Locale locale) {
        return FastDateFormat.getInstance(YMD_HMS, locale).format(d);
    }

    public static String format(Date d, String pattern, Locale locale) {
        return d == null ? null : FastDateFormat.getInstance(pattern, locale).format(d);
    }

    public static String format(Date d, TimeZone timeZone) {
        return FastDateFormat.getInstance(YMD_HMS, timeZone).format(d);
    }

    public static String format(Date d, String pattern, TimeZone timeZone) {
        return d == null ? null : FastDateFormat.getInstance(pattern, timeZone).format(d);
    }

    public static String format(Date d, TimeZone timeZone, Locale locale) {
        return FastDateFormat.getInstance(YMD_HMS, timeZone, locale).format(d);
    }

    /**
     * 格式化
     * @param d        时间
     * @param pattern  格式
     * @param timeZone 时区
     * @param locale   地区
     * @return ignore
     */
    public static String format(Date d, String pattern, TimeZone timeZone, Locale locale) {
        return d == null ? null : FastDateFormat.getInstance(pattern, timeZone, locale).format(d);
    }

    public static Date parse(String d) {
        if (StringUtils.isBlank(d)) {
            return null;
        }
        String pattern = null;
        if (d.contains("-")) {
            for (String s : PARSE_PATTERN_GROUP1) {
                if (d.length() == s.length()) {
                    pattern = s;
                    break;
                }
            }
            if (pattern != null) {
                return parse(d, pattern);
            }
        } else if (d.contains("/")) {
            for (String s : PARSE_PATTERN_GROUP2) {
                if (d.length() == s.length()) {
                    pattern = s;
                    break;
                }
            }
            if (pattern != null) {
                return parse(d, pattern);
            }
        } else if (Matches.isNumber(d)) {
            for (String s : PARSE_PATTERN_GROUP3) {
                if (d.length() == s.length()) {
                    pattern = s;
                    break;
                }
            }
            if (pattern != null) {
                return parse(d, pattern);
            }
        } else if (d.split(Constants.SPACE).length == 6) {
            return parse(d, WORLD, Locale.US);
        }
        return null;
    }

    public static Date parse(String d, String pattern) {
        try {
            return FastDateFormat.getInstance(pattern).parse(d);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parse(String d, String... patterns) {
        for (String pattern : patterns) {
            Date parse = parse(d, pattern);
            if (parse != null) {
                return parse;
            }
        }
        return null;
    }

    public static Date parse(String d, String pattern, Locale locale) {
        try {
            return FastDateFormat.getInstance(pattern, locale).parse(d);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parse(String d, Locale locale, String... patterns) {
        for (String pattern : patterns) {
            Date parse = parse(d, pattern, locale);
            if (parse != null) {
                return parse;
            }
        }
        return null;
    }

    public static Date parse(String d, String pattern, TimeZone timeZone) {
        try {
            return FastDateFormat.getInstance(pattern, timeZone).parse(d);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parse(String d, TimeZone timeZone, String... patterns) {
        for (String pattern : patterns) {
            Date parse = parse(d, pattern, timeZone);
            if (parse != null) {
                return parse;
            }
        }
        return null;
    }

    public static Date parse(String d, String pattern, TimeZone timeZone, Locale locale) {
        try {
            return FastDateFormat.getInstance(pattern, timeZone, locale).parse(d);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期转化
     * @param d        日期
     * @param timeZone 时区
     * @param locale   地区
     * @param patterns 格式
     * @return 日期
     */
    public static Date parse(String d, TimeZone timeZone, Locale locale, String... patterns) {
        for (String pattern : patterns) {
            Date parse = parse(d, pattern, timeZone, locale);
            if (parse != null) {
                return parse;
            }
        }
        return null;
    }

    /**
     * 将GMT时间转为date
     * @param s GMT时间
     * @return date
     */
    public static Date world(String s) {
        return parse(s, WORLD, Locale.US);
    }

    public static String hourType() {
        return hourType(calendar());
    }

    /**
     * 时间段
     * @param d d
     * @return 时间段
     */
    public static String hourType(Date d) {
        return hourType(calendar(d));
    }

    /**
     * 时间段
     * @param c c
     * @return 时间段
     */
    public static String hourType(Calendar c) {
        return DatesFormat.hourType(c.get(Calendar.HOUR_OF_DAY));
    }





    /**
     * 时差毫秒
     * @param date1 时间1
     * @param date2 时间2
     * @return 时差
     */
    public static long intervalMs(Date date1, Date date2) {
        return Math.abs(date1.getTime() - date2.getTime());
    }



    /**
     * 时差解构
     * @param date1 时间1
     * @param date2 时间2
     * @return 时差 [天,时,分,秒]
     */
    public static long[] intervalAnalysis(Date date1, Date date2) {
        return DatesFormat.intervalAnalysis(intervalMs(date1, date2));
    }

    /**
     * 转换格式
     * <p>
     * 使用格式推断
     * @param d 时间
     * @param n 新格式
     * @return 日期
     */
    public static String convert(String d, String n) {
        Date parse = parse(d);
        if (parse == null) {
            return Constants.EMPTY;
        }
        return format(parse, n);
    }

    /**
     * 转换格式
     * @param d      时间
     * @param before 原格式
     * @param after  新格式
     * @return 日期
     */
    public static String convert(String d, String before, String after) {
        return format(parse(d, before), after);
    }

    public static boolean isLeapYear() {
        return isLeapYear(calendar());
    }

    public static boolean isLeapYear(Date d) {
        return isLeapYear(calendar(d));
    }

    /**
     * 是否为闰年
     * @param c calendar
     * @return true 闰年
     */
    public static boolean isLeapYear(Calendar c) {
        return DatesFormat.isLeapYear(c.get(Calendar.YEAR));
    }

    public static int getMonthLastDay() {
        return getMonthLastDay(calendar());
    }

    public static int getMonthLastDay(Date d) {
        return getMonthLastDay(calendar(d));
    }

    /**
     * 获得月份的最后一天
     * @param c calendar
     * @return 最后一天
     */
    public static int getMonthLastDay(Calendar c) {
        return DatesFormat.getMonthLastDay(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    public static int getQuarter() {
        return getQuarter(calendar());
    }

    public static int getQuarter(Date d) {
        return getQuarter(calendar(d));
    }

    /**
     * 获取季度
     * @param c calendar
     * @return 季度
     */
    public static int getQuarter(Calendar c) {
        return DatesFormat.getQuarter(c.get(Calendar.MONTH) + 1);
    }

    public static boolean isAm() {
        return isAm(calendar());
    }

    public static boolean isAm(Date d) {
        return isAm(calendar(d));
    }

    /**
     * 是否为上午
     *
     * @param c c
     * @return ignore
     */
    public static boolean isAm(Calendar c) {
        return Calendar.AM == c.get(Calendar.AM_PM);
    }

    public static boolean isPm() {
        return isPm(calendar());
    }

    public static boolean isPm(Date d) {
        return isPm(calendar(d));
    }

    /**
     * 是否为下午
     *
     * @param c c
     * @return ignore
     */
    public static boolean isPm(Calendar c) {
        return Calendar.PM == c.get(Calendar.AM_PM);
    }




}
