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
package cn.xphsc.web.common.lang.date.format;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.6
 */
public class FastDateFormat extends Format implements DateParser, DatePrinter {

    private static final long serialVersionUID = 2L;

    private static final FormatCache<FastDateFormat> CACHE = new FormatCache<FastDateFormat>() {
        @Override
        protected FastDateFormat createInstance(String pattern, TimeZone timeZone, Locale locale) {
            return new FastDateFormat(pattern, timeZone, locale);
        }
    };

    private final FastDatePrinter printer;

    private final FastDateParser parser;

    protected FastDateFormat(String pattern, TimeZone timeZone, Locale locale) {
        this(pattern, timeZone, locale, null);
    }

    protected FastDateFormat(String pattern, TimeZone timeZone, Locale locale, Date centuryStart) {
        this.printer = new FastDatePrinter(pattern, timeZone, locale);
        this.parser = new FastDateParser(pattern, timeZone, locale, centuryStart);
    }

    public static FastDateFormat getInstance() {
        return CACHE.getInstance();
    }

    public static FastDateFormat getInstance(String pattern) {
        return CACHE.getInstance(pattern, null, null);
    }

    public static FastDateFormat getInstance(String pattern, TimeZone timeZone) {
        return CACHE.getInstance(pattern, timeZone, null);
    }

    public static FastDateFormat getInstance(String pattern, Locale locale) {
        return CACHE.getInstance(pattern, null, locale);
    }

    /**
     * 获取实例
     *
     * @param pattern  pattern
     * @param timeZone timeZone
     * @param locale   locale
     * @return this
     */
    public static FastDateFormat getInstance(String pattern, TimeZone timeZone, Locale locale) {
        return CACHE.getInstance(pattern, timeZone, locale);
    }

    public static FastDateFormat getDateInstance(int style) {
        return CACHE.getDateInstance(style, null, null);
    }

    public static FastDateFormat getDateInstance(int style, Locale locale) {
        return CACHE.getDateInstance(style, null, locale);
    }

    public static FastDateFormat getDateInstance(int style, TimeZone timeZone) {
        return CACHE.getDateInstance(style, timeZone, null);
    }

    /**
     * 获取日期实例
     *
     * @param style    style
     * @param timeZone timeZone
     * @param locale   locale
     * @return this
     */
    public static FastDateFormat getDateInstance(int style, TimeZone timeZone, Locale locale) {
        return CACHE.getDateInstance(style, timeZone, locale);
    }

    public static FastDateFormat getTimeInstance(int style) {
        return CACHE.getTimeInstance(style, null, null);
    }

    public static FastDateFormat getTimeInstance(int style, Locale locale) {
        return CACHE.getTimeInstance(style, null, locale);
    }

    public static FastDateFormat getTimeInstance(int style, TimeZone timeZone) {
        return CACHE.getTimeInstance(style, timeZone, null);
    }

    /**
     * 获取时间实例
     *
     * @param style    style
     * @param timeZone timeZone
     * @param locale   locale
     * @return this
     */
    public static FastDateFormat getTimeInstance(int style, TimeZone timeZone, Locale locale) {
        return CACHE.getTimeInstance(style, timeZone, locale);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
        return CACHE.getDateTimeInstance(dateStyle, timeStyle, null, null);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale) {
        return CACHE.getDateTimeInstance(dateStyle, timeStyle, null, locale);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone) {
        return getDateTimeInstance(dateStyle, timeStyle, timeZone, null);
    }

    /**
     * 获取日期时间实例
     *
     * @param dateStyle dateStyle
     * @param timeStyle timeStyle
     * @param timeZone  timeZone
     * @param locale    locale
     * @return this
     */
    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone, Locale locale) {
        return CACHE.getDateTimeInstance(dateStyle, timeStyle, timeZone, locale);
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        return toAppendTo.append(printer.format(obj));
    }

    @Override
    public String format(long millis) {
        return printer.format(millis);
    }

    @Override
    public String format(Date date) {
        return printer.format(date);
    }

    @Override
    public String format(Calendar calendar) {
        return printer.format(calendar);
    }

    @Override
    public Date parse(String source) {
        return parser.parse(source);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        return parser.parse(source, pos);
    }

    @Override
    public boolean parse(String source, ParsePosition pos, Calendar calendar) {
        return parser.parse(source, pos, calendar);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parser.parse(source, pos);
    }

    @Override
    public String getPattern() {
        return printer.getPattern();
    }

    @Override
    public TimeZone getTimeZone() {
        return printer.getTimeZone();
    }

    @Override
    public Locale getLocale() {
        return printer.getLocale();
    }

    /**
     * 预估最大长度
     */
    public int getMaxLengthEstimate() {
        return printer.getMaxLengthEstimate();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FastDateFormat)) {
            return false;
        }
        FastDateFormat other = (FastDateFormat) obj;
        return printer.equals(other.printer);
    }

    @Override
    public int hashCode() {
        return printer.hashCode();
    }

    @Override
    public String toString() {
        return printer.getPattern() + ", " + printer.getLocale() + ", " + printer.getTimeZone().getID();
    }

}
