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
package cn.xphsc.web.common.lang.mutable;

import cn.xphsc.web.common.lang.type.ConvertTypes;
import cn.xphsc.web.utils.ByteUtils;
import cn.xphsc.web.utils.ObjectUtils;
import cn.xphsc.web.utils.StringUtils;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: variable String
 * @since 1.1.6
 */
public class MutableString implements  CharSequence, Serializable {

    private static final long serialVersionUID = 8675244107435484L;

    private StringBuilder builder;

    public MutableString() {
        this.builder = new StringBuilder();
    }

    public MutableString(String s) {
        this.builder = new StringBuilder(s);
    }

    public MutableString(StringBuilder builder) {
        this.builder = builder;
    }

    public MutableString(Object o) {
        this.builder = new StringBuilder(ObjectUtils.toString(o));
    }

    public MutableString of(String character) {
        return new MutableString(character);
    }


    public String get() {
        return builder.toString();
    }

    public void set(String s) {
        this.builder = new StringBuilder(s);
    }

    /**
     * 是否为空白
     * @return true 空白
     */
    public boolean isBlank() {
        return StringUtils.isBlank(builder.toString());
    }

    /**
     * 是否不为空白
     * @return false 空白
     */
    public boolean isNotBlank() {
        return StringUtils.isNotBlank(builder.toString());
    }

    /**
     * 是否为空
     * @return true 空
     */
    public boolean isEmpty() {
        return StringUtils.isEmpty(builder.toString());
    }

    /**
     * 是否不为空
     * @return false 空
     */
    public boolean isNotEmpty() {
        return StringUtils.isNotEmpty(builder.toString());
    }

    /**
     * 去除首尾空格
     * @return this
     */
    public MutableString trim() {
        this.builder = new StringBuilder(builder.toString().trim());
        return this;
    }

    /**
     * 去除特殊符号
     * @return this
     */
    public MutableString trimPunct() {
        String trim=builder.toString().replaceAll("[\\pP\\p{Punct}]", StringUtils.EMPTY).replaceAll("￥", StringUtils.EMPTY);
        this.builder = new StringBuilder(trim);
        return this;
    }

    /**
     * 连接字符
     * @param concat 字符
     * @return this
     */
    public MutableString concat(String... concat) {
        for (String s : concat) {
            builder.append(s);
        }
        return this;
    }

    /**
     * 连接字符
     * @param characters 字符
     * @return this
     */
    public MutableString concatBefore(String... characters) {
        StringBuilder sb = new StringBuilder();
        for (String character : characters) {
            sb.append(character);
        }
        this.builder = sb.append(builder);
        return this;
    }

    /**
     * 格式化字符串
     * @param os 参数
     * @return this
     */
    public MutableString format(Object... os) {
        this.builder = new StringBuilder(StringUtils.format(builder.toString(), os));
        return this;
    }

    /**
     * 翻转字符串
     *
     * @return this
     */
    public MutableString reverse() {
        builder.reverse();
        return this;
    }

    /**
     * 截取字符串
     * @param begin 开始下标
     * @param end   结束下标
     * @return this
     */
    public MutableString substring(int begin, int end) {
        this.builder = new StringBuilder(builder.substring(begin, end));
        return this;
    }

    /**
     * 截取字符串
     * @param begin 开始下标
     * @return this
     */
    public MutableString substring(int begin) {
        this.builder = new StringBuilder(builder.substring(begin));
        return this;
    }

    /**
     * 是否包含字符串
     * @param s 字符串
     * @return true包含
     */
    public boolean contains(String s) {
        return builder.indexOf(s) > -1;
    }

    /**
     * 转为 byte
     * @return byte
     */
    public Byte toByte() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toByte(builder.toString());
    }

    /**
     * 转为 short
     * @return s
     */
    public Short toShort() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toShort(builder.toString());
    }

    /**
     * 转为 integer
     * @return Integer
     */
    public Integer toInteger() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toInt(builder.toString());
    }

    /**
     * 转为 long
     * @return long
     */
    public Long toLong() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toLong(builder.toString());
    }

    /**
     * 转为 float
     * @return float
     */
    public Float toFloat() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toFloat(builder.toString());
    }

    /**
     * 转为 double
     * @return double
     */
    public Double toDouble() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toDouble(builder.toString());
    }

    /**
     * 转为 boolean
     * @return boolean
     */
    public Boolean toBoolean() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toBoolean(builder.toString());
    }

    /**
     * 转为 char
     * @return char
     */
    public Character toCharacter() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toChar(builder.toString());
    }

    /**
     * 转为 date
     * @return date
     */
    public Date toDate() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toDate(builder.toString());
    }

    /**
     * 转为 localDateTime
     * @return LocalDateTime
     */
    public LocalDateTime toLocalDateTime() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toLocalDateTime(builder.toString());
    }

    /**
     * 转为 localDate
     * @return date
     */
    public LocalDate toLocalDate() {
        if (this.isEmpty()) {
            return null;
        }
        return ConvertTypes.toLocalDate(builder.toString());
    }

    /**
     * 转为 stringBuilder
     * @return stringBuilder
     */
    public StringBuilder toStringBuilder() {
        return new StringBuilder(builder);
    }

    /**
     * 转为 stringBuffer
     * @return stringBuffer
     */
    public StringBuffer toStringBuffer() {
        return new StringBuffer(builder);
    }

    /**
     * 转为 byte[]
     * @return byte[]
     */
    public byte[] toBytes() {
        return ByteUtils.toBytes(builder.toString());
    }


    /**
     * 转为 char[]
     *
     * @return char[]
     */
    public char[] toChars() {
        return builder.toString().toCharArray();
    }

    /**
     * 转为小写String
     * @return string
     */
    public String toLowerString() {
        return builder.toString().toLowerCase();
    }

    /**
     * 转为大写String
     *
     * @return string
     */
    public String toUpperString() {
        return builder.toString().toUpperCase();
    }



    @Override
    public int length() {
        return builder.length();
    }

    @Override
    public char charAt(int index) {
        return builder.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return builder.subSequence(start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        MutableString that = (MutableString) o;
        return Objects.equals(builder, that.builder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(builder);
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
