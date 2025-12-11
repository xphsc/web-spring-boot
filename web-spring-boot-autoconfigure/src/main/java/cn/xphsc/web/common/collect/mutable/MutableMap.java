/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.common.collect.mutable;

import cn.xphsc.web.common.lang.function.*;
import cn.xphsc.web.common.lang.math.BigDecimals;
import cn.xphsc.web.common.lang.math.BigIntegers;
import cn.xphsc.web.common.lang.type.ConvertTypes;
import cn.xphsc.web.utils.Asserts;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.function.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 可转换的 map
 * @since 2.0.2
 */
public interface MutableMap<K, V> extends Map<K, V> {

    default Byte getByte(K k) {
        return this.getByte(k, Suppliers.nullSupplier());
    }

    default Byte getByte(K k, Byte def) {
        return this.getByte(k, () -> def);
    }

    default Byte getByte(K k, Supplier<Byte> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toByte(v);
    }

    default byte getByteValue(K k) {
        return this.getByteValue(k, Suppliers.BYTE_SUPPLIER);
    }

    default byte getByteValue(K k, byte def) {
        return this.getByteValue(k, () -> def);
    }

    default byte getByteValue(K k, ByteSupplier supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.getAsByte();
        }
        return ConvertTypes.toByte(v);
    }

    default Short getShort(K k) {
        return this.getShort(k, Suppliers.nullSupplier());
    }

    default Short getShort(K k, Short def) {
        return this.getShort(k, () -> def);
    }

    default Short getShort(K k, Supplier<Short> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toShort(k);
    }

    default short getShortValue(K k) {
        return this.getShortValue(k, Suppliers.SHORT_SUPPLIER);
    }

    default short getShortValue(K k, short def) {
        return this.getShortValue(k, () -> def);
    }

    default short getShortValue(K k, ShortSupplier supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.getAsShort();
        }
        return ConvertTypes.toShort(k);
    }

    default Integer getInteger(K k) {
        return this.getInteger(k, Suppliers.nullSupplier());
    }

    default Integer getInteger(K k, Integer def) {
        return this.getInteger(k, () -> def);
    }

    default Integer getInteger(K k, Supplier<Integer> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toInt(v);
    }

    default int getIntValue(K k) {
        return this.getIntValue(k, Suppliers.INT_SUPPLIER);
    }

    default int getIntValue(K k, int def) {
        return this.getIntValue(k, () -> def);
    }

    default int getIntValue(K k, IntSupplier supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.getAsInt();
        }
        return ConvertTypes.toInt(v);
    }

    default Long getLong(K k) {
        return this.getLong(k, Suppliers.nullSupplier());
    }

    default Long getLong(K k, Long def) {
        return this.getLong(k, () -> def);
    }

    default Long getLong(K k, Supplier<Long> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toLong(v);
    }

    default long getLongValue(K k) {
        return this.getLongValue(k, Suppliers.LONG_SUPPLIER);
    }

    default long getLongValue(K k, long def) {
        return this.getLongValue(k, () -> def);
    }

    default long getLongValue(K k, LongSupplier supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.getAsLong();
        }
        return ConvertTypes.toLong(v);
    }

    default Float getFloat(K k) {
        return this.getFloat(k, Suppliers.nullSupplier());
    }

    default Float getFloat(K k, Float def) {
        return this.getFloat(k, () -> def);
    }

    default Float getFloat(K k, Supplier<Float> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toFloat(v);
    }

    default float getFloatValue(K k) {
        return this.getFloatValue(k, Suppliers.FLOAT_SUPPLIER);
    }

    default float getFloatValue(K k, float def) {
        return this.getFloatValue(k, () -> def);
    }

    default float getFloatValue(K k, FloatSupplier supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.getAsFloat();
        }
        return ConvertTypes.toFloat(v);
    }

    default Double getDouble(K k) {
        return this.getDouble(k, Suppliers.nullSupplier());
    }

    default Double getDouble(K k, Double def) {
        return this.getDouble(k, () -> def);
    }

    default Double getDouble(K k, Supplier<Double> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toDouble(v);
    }

    default double getDoubleValue(K k) {
        return this.getDoubleValue(k, Suppliers.DOUBLE_SUPPLIER);
    }

    default double getDoubleValue(K k, double def) {
        return this.getDoubleValue(k, () -> def);
    }

    default double getDoubleValue(K k, DoubleSupplier supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.getAsDouble();
        }
        return ConvertTypes.toDouble(v);
    }

    default Boolean getBoolean(K k) {
        return this.getBoolean(k, Suppliers.nullSupplier());
    }

    default Boolean getBoolean(K k, Boolean def) {
        return this.getBoolean(k, () -> def);
    }

    default Boolean getBoolean(K k, Supplier<Boolean> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toBoolean(v);
    }

    default boolean getBooleanValue(K k) {
        return this.getBooleanValue(k, Suppliers.BOOLEAN_SUPPLIER);
    }

    default boolean getBooleanValue(K k, boolean def) {
        return this.getBooleanValue(k, () -> def);
    }

    default boolean getBooleanValue(K k, BooleanSupplier supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.getAsBoolean();
        }
        return ConvertTypes.toBoolean(v);
    }

    default Character getCharacter(K k) {
        return this.getCharacter(k, Suppliers.nullSupplier());
    }

    default Character getCharacter(K k, Character def) {
        return this.getCharacter(k, () -> def);
    }

    default Character getCharacter(K k, Supplier<Character> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toChar(v);
    }

    default char getCharValue(K k) {
        return this.getCharValue(k, Suppliers.CHAR_SUPPLIER);
    }

    default char getCharValue(K k, char def) {
        return this.getCharValue(k, () -> def);
    }

    default char getCharValue(K k, CharSupplier supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.getAsChar();
        }
        return ConvertTypes.toChar(v);
    }

    default String getString(K k) {
        return this.getString(k, Suppliers.nullSupplier());
    }

    default String getString(K k, String def) {
        return this.getString(k, () -> def);
    }

    default String getString(K k, Supplier<String> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toString(v);
    }

    default Date getDate(K k) {
        return this.getDate(k, Suppliers.nullSupplier());
    }

    default Date getDate(K k, Date def) {
        return this.getDate(k, () -> def);
    }

    default Date getDate(K k, Supplier<Date> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toDate(v);
    }

    default LocalDateTime getLocalDateTime(K k) {
        return this.getLocalDateTime(k, Suppliers.nullSupplier());
    }

    default LocalDateTime getLocalDateTime(K k, LocalDateTime def) {
        return this.getLocalDateTime(k, () -> def);
    }

    default LocalDateTime getLocalDateTime(K k, Supplier<LocalDateTime> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toLocalDateTime(v);
    }

    default LocalDate getLocalDate(K k) {
        return this.getLocalDate(k, Suppliers.nullSupplier());
    }

    default LocalDate getLocalDate(K k, LocalDate def) {
        return this.getLocalDate(k, () -> def);
    }

    default LocalDate getLocalDate(K k, Supplier<LocalDate> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return ConvertTypes.toLocalDate(v);
    }

    default BigDecimal getBigDecimal(K k) {
        return this.getBigDecimal(k, Suppliers.nullSupplier());
    }

    default BigDecimal getBigDecimal(K k, BigDecimal def) {
        return this.getBigDecimal(k, () -> def);
    }

    default BigDecimal getBigDecimal(K k, Supplier<BigDecimal> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return BigDecimals.toBigDecimal(v);
    }

    default BigInteger getBigInteger(K k) {
        return this.getBigInteger(k, Suppliers.nullSupplier());
    }

    default BigInteger getBigInteger(K k, BigInteger def) {
        return this.getBigInteger(k, () -> def);
    }

    default BigInteger getBigInteger(K k, Supplier<BigInteger> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return BigIntegers.toBigInteger(v);
    }

    default <E> E getObject(K k) {
        return this.getObject(k, Suppliers.nullSupplier());
    }

    default <E> E getObject(K k, E def) {
        return this.getObject(k, () -> def);
    }

    @SuppressWarnings("unchecked")
    default <E> E getObject(K k, Supplier<E> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return (E) v;
    }

    default V get(K k, V def) {
        return this.get(k, () -> def);
    }

    default V get(K k, Supplier<V> supplier) {
        Asserts.notNull(supplier);
        V v = this.get(k);
        if (v == null) {
            return supplier.get();
        }
        return v;
    }

}

