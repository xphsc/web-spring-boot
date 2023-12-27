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
package cn.xphsc.web.common.lang.type;


import cn.xphsc.web.common.lang.function.ConvertFunction;
import cn.xphsc.web.common.lang.date.Dates8;
import cn.xphsc.web.common.lang.math.BigDecimals;
import cn.xphsc.web.common.lang.math.BigIntegers;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 基本类型转换映射 提供者
 * @since 1.1.6
 */
public class BasicTypeStoreProvider implements Serializable {

    private static final long serialVersionUID = -938129593771195L;

    private final ObjectTypeStore store;

    public BasicTypeStoreProvider() {
        this(ObjectTypeStore.STORE);
    }

    public BasicTypeStoreProvider(ObjectTypeStore store) {
        this.store = store;
        this.loadNumber();
        this.loadNumberExt();
        this.loadBoolean();
        this.loadChar();
        this.loadString();
        this.loadByteArray();
        this.loadShortArray();
        this.loadIntArray();
        this.loadLongArray();
        this.loadFloatArray();
        this.loadDoubleArray();
        this.loadBooleanArray();
        this.loadCharArray();
        this.loadStringArray();
    }

    // -------------------- base --------------------
    private final static ConvertFunction TO_BYTE = ConvertTypes::toByte;
    private final static ConvertFunction TO_SHORT = ConvertTypes::toShort;
    private final static ConvertFunction TO_INT = ConvertTypes::toInt;
    private final static ConvertFunction TO_LONG = ConvertTypes::toLong;
    private final static ConvertFunction TO_FLOAT = ConvertTypes::toFloat;
    private final static ConvertFunction TO_DOUBLE = ConvertTypes::toDouble;
    private final static ConvertFunction TO_BOOLEAN = ConvertTypes::toBoolean;
    private final static ConvertFunction TO_CHAR = ConvertTypes::toChar;
    // -------------------- array --------------------
    private final static ConvertFunction TO_BYTE_ARRAY = ConvertTypes::toBytes;
    private final static ConvertFunction TO_SHORT_ARRAY = ConvertTypes::toShorts;
    private final static ConvertFunction TO_INT_ARRAY = ConvertTypes::toInts;
    private final static ConvertFunction TO_LONG_ARRAY = ConvertTypes::toLongs;
    private final static ConvertFunction TO_FLOAT_ARRAY = ConvertTypes::toFloats;
    private final static ConvertFunction TO_DOUBLE_ARRAY = ConvertTypes::toDoubles;
    private final static ConvertFunction TO_BOOLEAN_ARRAY = ConvertTypes::toBooleans;
    private final static ConvertFunction TO_CHAR_ARRAY = ConvertTypes::toChars;
    private final static ConvertFunction TO_STRING_ARRAY = ConvertTypes::toStrings;
    // -------------------- usual --------------------
    private final static ConvertFunction TO_BIG_DECIMAL = BigDecimals::toBigDecimal;
    private final static ConvertFunction TO_BIG_INTEGER = BigIntegers::toBigInteger;
    private final static ConvertFunction TO_STRING = ConvertTypes::toString;
    private final static ConvertFunction TO_DATE = ConvertTypes::toDate;
    private final static ConvertFunction TO_LOCAL_DATE_TIME = Dates8::localDateTime;
    private final static ConvertFunction TO_LOCAL_DATE = Dates8::localDate;

    private void loadNumber() {
        store.register(Number.class, Byte.class, TO_BYTE);
        store.register(Number.class, Short.class, TO_SHORT);
        store.register(Number.class, Integer.class, TO_INT);
        store.register(Number.class, Long.class, TO_LONG);
        store.register(Number.class, Float.class, TO_FLOAT);
        store.register(Number.class, Double.class, TO_DOUBLE);
        store.register(Number.class, Boolean.class, TO_BOOLEAN);
        store.register(Number.class, Character.class, TO_CHAR);
        store.register(Number.class, BigDecimal.class, TO_BIG_DECIMAL);
        store.register(Number.class, BigInteger.class, TO_BIG_INTEGER);
        store.register(Number.class, String.class, TO_STRING);
    }

    private void loadNumberExt() {
        store.register(Short.class, byte[].class, TO_BYTE_ARRAY);
        store.register(Integer.class, byte[].class, TO_BYTE_ARRAY);
        store.register(Long.class, Date.class, TO_DATE);
        store.register(Long.class, LocalDateTime.class, TO_LOCAL_DATE_TIME);
        store.register(Long.class, LocalDate.class, TO_LOCAL_DATE);
        store.register(Long.class, byte[].class, TO_BYTE_ARRAY);
    }

    private void loadBoolean() {
        store.register(Boolean.class, Byte.class, TO_BYTE);
        store.register(Boolean.class, Short.class, TO_SHORT);
        store.register(Boolean.class, Integer.class, TO_INT);
        store.register(Boolean.class, Long.class, TO_LONG);
        store.register(Boolean.class, Float.class, TO_FLOAT);
        store.register(Boolean.class, Double.class, TO_DOUBLE);
        store.register(Boolean.class, Character.class, TO_CHAR);
        store.register(Boolean.class, BigDecimal.class, TO_BIG_DECIMAL);
        store.register(Boolean.class, BigInteger.class, TO_BIG_INTEGER);
        store.register(Boolean.class, String.class, TO_STRING);
    }

    private void loadChar() {
        store.register(Character.class, Byte.class, TO_BYTE);
        store.register(Character.class, Short.class, TO_SHORT);
        store.register(Character.class, Integer.class, TO_INT);
        store.register(Character.class, Long.class, TO_LONG);
        store.register(Character.class, Float.class, TO_FLOAT);
        store.register(Character.class, Double.class, TO_DOUBLE);
        store.register(Character.class, Boolean.class, TO_BOOLEAN);
        store.register(Character.class, BigDecimal.class, TO_BIG_DECIMAL);
        store.register(Character.class, BigInteger.class, TO_BIG_INTEGER);
        store.register(Character.class, String.class, TO_STRING);
    }

    private void loadString() {
        store.register(String.class, Byte.class, TO_BYTE);
        store.register(String.class, Short.class, TO_SHORT);
        store.register(String.class, Integer.class, TO_INT);
        store.register(String.class, Long.class, TO_LONG);
        store.register(String.class, Float.class, TO_FLOAT);
        store.register(String.class, Double.class, TO_DOUBLE);
        store.register(String.class, Boolean.class, TO_BOOLEAN);
        store.register(String.class, Character.class, TO_CHAR);
        store.register(String.class, BigDecimal.class, TO_BIG_DECIMAL);
        store.register(String.class, BigInteger.class, TO_BIG_INTEGER);
        store.register(String.class, Date.class, TO_DATE);
        store.register(String.class, LocalDateTime.class, TO_LOCAL_DATE_TIME);
        store.register(String.class, LocalDate.class, TO_LOCAL_DATE);
    }

    private void loadByteArray() {
        store.register(byte[].class, short[].class, TO_SHORT_ARRAY);
        store.register(byte[].class, int[].class, TO_INT_ARRAY);
        store.register(byte[].class, long[].class, TO_LONG_ARRAY);
        store.register(byte[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(byte[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(byte[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(byte[].class, char[].class, TO_CHAR_ARRAY);
        store.register(byte[].class, String[].class, TO_STRING_ARRAY);
        store.register(Byte[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(Byte[].class, short[].class, TO_SHORT_ARRAY);
        store.register(Byte[].class, int[].class, TO_INT_ARRAY);
        store.register(Byte[].class, long[].class, TO_LONG_ARRAY);
        store.register(Byte[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(Byte[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(Byte[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(Byte[].class, char[].class, TO_CHAR_ARRAY);
        store.register(Byte[].class, String[].class, TO_STRING_ARRAY);
    }

    private void loadShortArray() {
        store.register(short[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(short[].class, int[].class, TO_INT_ARRAY);
        store.register(short[].class, long[].class, TO_LONG_ARRAY);
        store.register(short[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(short[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(short[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(short[].class, char[].class, TO_CHAR_ARRAY);
        store.register(short[].class, String[].class, TO_STRING_ARRAY);
        store.register(Short[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(Short[].class, short[].class, TO_SHORT_ARRAY);
        store.register(Short[].class, int[].class, TO_INT_ARRAY);
        store.register(Short[].class, long[].class, TO_LONG_ARRAY);
        store.register(Short[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(Short[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(Short[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(Short[].class, char[].class, TO_CHAR_ARRAY);
        store.register(Short[].class, String[].class, TO_STRING_ARRAY);
    }

    private void loadIntArray() {
        store.register(int[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(int[].class, short[].class, TO_SHORT_ARRAY);
        store.register(int[].class, long[].class, TO_LONG_ARRAY);
        store.register(int[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(int[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(int[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(int[].class, char[].class, TO_CHAR_ARRAY);
        store.register(int[].class, String[].class, TO_STRING_ARRAY);
        store.register(Integer[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(Integer[].class, short[].class, TO_SHORT_ARRAY);
        store.register(Integer[].class, int[].class, TO_INT_ARRAY);
        store.register(Integer[].class, long[].class, TO_LONG_ARRAY);
        store.register(Integer[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(Integer[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(Integer[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(Integer[].class, char[].class, TO_CHAR_ARRAY);
        store.register(Integer[].class, String[].class, TO_STRING_ARRAY);

    }

    private void loadLongArray() {
        store.register(long[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(long[].class, short[].class, TO_SHORT_ARRAY);
        store.register(long[].class, int[].class, TO_INT_ARRAY);
        store.register(long[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(long[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(long[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(long[].class, char[].class, TO_CHAR_ARRAY);
        store.register(long[].class, String[].class, TO_STRING_ARRAY);
        store.register(Long[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(Long[].class, short[].class, TO_SHORT_ARRAY);
        store.register(Long[].class, int[].class, TO_INT_ARRAY);
        store.register(Long[].class, long[].class, TO_LONG_ARRAY);
        store.register(Long[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(Long[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(Long[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(Long[].class, char[].class, TO_CHAR_ARRAY);
        store.register(Long[].class, String[].class, TO_STRING_ARRAY);
    }

    private void loadFloatArray() {
        store.register(float[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(float[].class, short[].class, TO_SHORT_ARRAY);
        store.register(float[].class, int[].class, TO_INT_ARRAY);
        store.register(float[].class, long[].class, TO_LONG_ARRAY);
        store.register(float[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(float[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(float[].class, char[].class, TO_CHAR_ARRAY);
        store.register(float[].class, String[].class, TO_STRING_ARRAY);
        store.register(Float[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(Float[].class, short[].class, TO_SHORT_ARRAY);
        store.register(Float[].class, int[].class, TO_INT_ARRAY);
        store.register(Float[].class, long[].class, TO_LONG_ARRAY);
        store.register(Float[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(Float[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(Float[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(Float[].class, char[].class, TO_CHAR_ARRAY);
        store.register(Float[].class, String[].class, TO_STRING_ARRAY);
    }

    private void loadDoubleArray() {
        store.register(double[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(double[].class, short[].class, TO_SHORT_ARRAY);
        store.register(double[].class, int[].class, TO_INT_ARRAY);
        store.register(double[].class, long[].class, TO_LONG_ARRAY);
        store.register(double[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(double[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(double[].class, char[].class, TO_CHAR_ARRAY);
        store.register(double[].class, String[].class, TO_STRING_ARRAY);
        store.register(Double[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(Double[].class, short[].class, TO_SHORT_ARRAY);
        store.register(Double[].class, int[].class, TO_INT_ARRAY);
        store.register(Double[].class, long[].class, TO_LONG_ARRAY);
        store.register(Double[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(Double[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(Double[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(Double[].class, char[].class, TO_CHAR_ARRAY);
        store.register(Double[].class, String[].class, TO_STRING_ARRAY);
    }

    private void loadBooleanArray() {
        store.register(boolean[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(boolean[].class, short[].class, TO_SHORT_ARRAY);
        store.register(boolean[].class, int[].class, TO_INT_ARRAY);
        store.register(boolean[].class, long[].class, TO_LONG_ARRAY);
        store.register(boolean[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(boolean[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(boolean[].class, char[].class, TO_CHAR_ARRAY);
        store.register(boolean[].class, String[].class, TO_STRING_ARRAY);
        store.register(Boolean[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(Boolean[].class, short[].class, TO_SHORT_ARRAY);
        store.register(Boolean[].class, int[].class, TO_INT_ARRAY);
        store.register(Boolean[].class, long[].class, TO_LONG_ARRAY);
        store.register(Boolean[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(Boolean[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(Boolean[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(Boolean[].class, char[].class, TO_CHAR_ARRAY);
        store.register(Boolean[].class, String[].class, TO_STRING_ARRAY);
    }

    private void loadCharArray() {
        store.register(char[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(char[].class, short[].class, TO_SHORT_ARRAY);
        store.register(char[].class, int[].class, TO_INT_ARRAY);
        store.register(char[].class, long[].class, TO_LONG_ARRAY);
        store.register(char[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(char[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(char[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(char[].class, String[].class, TO_STRING_ARRAY);
        store.register(Character[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(Character[].class, short[].class, TO_SHORT_ARRAY);
        store.register(Character[].class, int[].class, TO_INT_ARRAY);
        store.register(Character[].class, long[].class, TO_LONG_ARRAY);
        store.register(Character[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(Character[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(Character[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(Character[].class, char[].class, TO_CHAR_ARRAY);
        store.register(Character[].class, String[].class, TO_STRING_ARRAY);
    }

    private void loadStringArray() {
        store.register(String[].class, byte[].class, TO_BYTE_ARRAY);
        store.register(String[].class, short[].class, TO_SHORT_ARRAY);
        store.register(String[].class, int[].class, TO_INT_ARRAY);
        store.register(String[].class, long[].class, TO_LONG_ARRAY);
        store.register(String[].class, float[].class, TO_FLOAT_ARRAY);
        store.register(String[].class, double[].class, TO_DOUBLE_ARRAY);
        store.register(String[].class, boolean[].class, TO_BOOLEAN_ARRAY);
        store.register(String[].class, char[].class, TO_CHAR_ARRAY);
    }

}
