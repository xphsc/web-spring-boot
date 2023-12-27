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

import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.common.lang.constant.Constants;
import java.util.Date;
import java.util.TimeZone;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.6
 */
final class GmtTimeZone extends TimeZone {

    private static final int MILLISECONDS_PER_MINUTE = Constants.MS_S_60;

    private static final int MINUTES_PER_HOUR = 60;

    private static final int HOURS_PER_DAY = 24;

    private static final long serialVersionUID = 1L;

    private final int offset;

    private final String zoneId;

    GmtTimeZone(boolean negate, int hours, int minutes) {
        if (hours >= HOURS_PER_DAY) {
            throw Exceptions.argument(hours + " hours out of range");
        }
        if (minutes >= MINUTES_PER_HOUR) {
            throw Exceptions.argument(minutes + " minutes out of range");
        }
        int milliseconds = (minutes + (hours * MINUTES_PER_HOUR)) * MILLISECONDS_PER_MINUTE;
        this.offset = negate ? -milliseconds : milliseconds;
        this.zoneId = twoDigits(
                twoDigits(new StringBuilder(9).append("GMT").append(negate ? '-' : '+'), hours)
                        .append(':'), minutes).toString();

    }

    private static StringBuilder twoDigits(StringBuilder sb, int n) {
        return sb.append((char) ('0' + (n / 10))).append((char) ('0' + (n % 10)));
    }

    @Override
    public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) {
        return offset;
    }

    @Override
    public void setRawOffset(int offsetMillis) {
        throw Exceptions.unsupported();
    }

    @Override
    public int getRawOffset() {
        return offset;
    }

    @Override
    public String getID() {
        return zoneId;
    }

    @Override
    public boolean useDaylightTime() {
        return false;
    }

    @Override
    public boolean inDaylightTime(Date date) {
        return false;
    }

    @Override
    public String toString() {
        return "id=\"" + zoneId + "\", offset=" + offset;
    }

    @Override
    public int hashCode() {
        return offset;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GmtTimeZone)) {
            return false;
        }
        return zoneId.equals(((GmtTimeZone) other).zoneId);
    }

}
