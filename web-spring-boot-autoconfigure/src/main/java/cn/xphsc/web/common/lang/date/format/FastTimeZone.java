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

import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.6
 */
public class FastTimeZone {

    private FastTimeZone() {
    }

    private static final Pattern GMT_PATTERN = Pattern.compile("^(?:(?i)GMT)?([+-])?(\\d\\d?)?(:?(\\d\\d?))?$");

    private static final TimeZone GREENWICH = new GmtTimeZone(false, 0, 0);

    public static TimeZone getGmtTimeZone() {
        return GREENWICH;
    }

    public static TimeZone getGmtTimeZone(String pattern) {
        if ("Z".equals(pattern) || "UTC".equals(pattern)) {
            return GREENWICH;
        }

        Matcher m = GMT_PATTERN.matcher(pattern);
        if (m.matches()) {
            int hours = parseInt(m.group(2));
            int minutes = parseInt(m.group(4));
            if (hours == 0 && minutes == 0) {
                return GREENWICH;
            }
            return new GmtTimeZone(parseSign(m.group(1)), hours, minutes);
        }
        return null;
    }

    public static TimeZone getTimeZone(String id) {
        TimeZone tz = getGmtTimeZone(id);
        if (tz != null) {
            return tz;
        }
        return TimeZone.getTimeZone(id);
    }

    private static int parseInt(String group) {
        return group != null ? Integer.parseInt(group) : 0;
    }

    private static boolean parseSign(String group) {
        return group != null && group.charAt(0) == '-';
    }

}
