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

import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  jdk8 时间工具类
 * @since 1.1.6
 */
public interface DateParser {

    /**
     * 格式化时间
     * @param source 时间
     * @return date
     */
    Date parse(String source);

    /**
     * 格式化时间
     * @param source 时间
     * @param pos    位置
     * @return date
     */
    Date parse(String source, ParsePosition pos);

    /**
     * 格式化时间 解析到日历
     *
     * @param source   时间
     * @param pos      位置
     * @param calendar 日历
     * @return 是否解析成功
     */
    boolean parse(String source, ParsePosition pos, Calendar calendar);

    /**
     * 获取格式
     * @return 格式
     */
    String getPattern();

    /**
     * 获取时区
     *
     * @return 时区
     */
    TimeZone getTimeZone();

    /**
     * 获取地区
     *
     * @return 地区
     */
    Locale getLocale();

}
