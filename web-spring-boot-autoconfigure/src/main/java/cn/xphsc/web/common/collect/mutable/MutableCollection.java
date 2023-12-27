/*
 * Copyright (c) 2023 huipei.x
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
import java.util.Collection;
import java.util.Date;
import java.util.function.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 可转换的 collection
 * @since 1.1.7
 */
public interface MutableCollection<E> extends Collection<E> {

    /**
     * get
     * @param index index
     * @return value
     */
    E get(int index);

    default Byte getByte(int i) {
        return this.getByte(i, Suppliers.nullSupplier());
    }

    default Byte getByte(int i, Byte def) {
        return this.getByte(i, () -> def);
    }

    default Byte getByte(int i, Supplier<Byte> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toByte(e);
    }

    default byte getByteValue(int i) {
        return this.getByteValue(i, Suppliers.BYTE_SUPPLIER);
    }

    default byte getByteValue(int i, byte def) {
        return this.getByteValue(i, () -> def);
    }

    default byte getByteValue(int i, ByteSupplier supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.getAsByte();
        }
        return ConvertTypes.toByte(e);
    }

    default Short getShort(int i) {
        return this.getShort(i, Suppliers.nullSupplier());
    }

    default Short getShort(int i, Short def) {
        return this.getShort(i, () -> def);
    }

    default Short getShort(int i, Supplier<Short> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toShort(e);
    }

    default short getShortValue(int i) {
        return this.getShortValue(i, Suppliers.SHORT_SUPPLIER);
    }

    default short getShortValue(int i, short def) {
        return this.getShortValue(i, () -> def);
    }

    default short getShortValue(int i, ShortSupplier supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.getAsShort();
        }
        return ConvertTypes.toShort(e);
    }

    default Integer getInteger(int i) {
        return this.getInteger(i, Suppliers.nullSupplier());
    }

    default Integer getInteger(int i, Integer def) {
        return this.getInteger(i, () -> def);
    }

    default Integer getInteger(int i, Supplier<Integer> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toInt(e);
    }

    default int getIntValue(int i) {
        return this.getIntValue(i, Suppliers.INT_SUPPLIER);
    }

    default int getIntValue(int i, int def) {
        return this.getIntValue(i, () -> def);
    }

    default int getIntValue(int i, IntSupplier supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.getAsInt();
        }
        return ConvertTypes.toInt(e);
    }

    default Long getLong(int i) {
        return this.getLong(i, Suppliers.nullSupplier());
    }

    default Long getLong(int i, Long def) {
        return this.getLong(i, () -> def);
    }

    default Long getLong(int i, Supplier<Long> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toLong(e);
    }

    default long getLongValue(int i) {
        return this.getLongValue(i, Suppliers.LONG_SUPPLIER);
    }

    default long getLongValue(int i, long def) {
        return this.getLongValue(i, () -> def);
    }

    default long getLongValue(int i, LongSupplier supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.getAsLong();
        }
        return ConvertTypes.toLong(e);
    }

    default Float getFloat(int i) {
        return this.getFloat(i, Suppliers.nullSupplier());
    }

    default Float getFloat(int i, Float def) {
        return this.getFloat(i, () -> def);
    }

    default Float getFloat(int i, Supplier<Float> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toFloat(e);
    }

    default float getFloatValue(int i) {
        return this.getFloatValue(i, Suppliers.FLOAT_SUPPLIER);
    }

    default float getFloatValue(int i, float def) {
        return this.getFloatValue(i, () -> def);
    }

    default float getFloatValue(int i, FloatSupplier supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.getAsFloat();
        }
        return ConvertTypes.toFloat(e);
    }

    default Double getDouble(int i) {
        return this.getDouble(i, Suppliers.nullSupplier());
    }

    default Double getDouble(int i, Double def) {
        return this.getDouble(i, () -> def);
    }

    default Double getDouble(int i, Supplier<Double> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toDouble(e);
    }

    default double getDoubleValue(int i) {
        return this.getDoubleValue(i, Suppliers.DOUBLE_SUPPLIER);
    }

    default double getDoubleValue(int i, double def) {
        return this.getDoubleValue(i, () -> def);
    }

    default double getDoubleValue(int i, DoubleSupplier supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.getAsDouble();
        }
        return ConvertTypes.toDouble(e);
    }

    default Boolean getBoolean(int i) {
        return this.getBoolean(i, Suppliers.nullSupplier());
    }

    default Boolean getBoolean(int i, Boolean def) {
        return this.getBoolean(i, () -> def);
    }

    default Boolean getBoolean(int i, Supplier<Boolean> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toBoolean(e);
    }

    default boolean getBooleanValue(int i) {
        return this.getBooleanValue(i, Suppliers.BOOLEAN_SUPPLIER);
    }

    default boolean getBooleanValue(int i, boolean def) {
        return this.getBooleanValue(i, () -> def);
    }

    default boolean getBooleanValue(int i, BooleanSupplier supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.getAsBoolean();
        }
        return ConvertTypes.toBoolean(e);
    }

    default Character getCharacter(int i) {
        return this.getCharacter(i, Suppliers.nullSupplier());
    }

    default Character getCharacter(int i, Character def) {
        return this.getCharacter(i, () -> def);
    }

    default Character getCharacter(int i, Supplier<Character> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toChar(e);
    }

    default char getCharValue(int i) {
        return this.getCharValue(i, Suppliers.CHAR_SUPPLIER);
    }

    default char getCharValue(int i, char def) {
        return this.getCharValue(i, () -> def);
    }

    default char getCharValue(int i, CharSupplier supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.getAsChar();
        }
        return ConvertTypes.toChar(e);
    }

    default String getString(int i) {
        return this.getString(i, Suppliers.nullSupplier());
    }

    default String getString(int i, String def) {
        return this.getString(i, () -> def);
    }

    default String getString(int i, Supplier<String> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toString(e);
    }

    default Date getDate(int i) {
        return this.getDate(i, Suppliers.nullSupplier());
    }

    default Date getDate(int i, Date def) {
        return this.getDate(i, () -> def);
    }

    default Date getDate(int i, Supplier<Date> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toDate(e);
    }

    default LocalDateTime getLocalDateTime(int i) {
        return this.getLocalDateTime(i, Suppliers.nullSupplier());
    }

    default LocalDateTime getLocalDateTime(int i, LocalDateTime def) {
        return this.getLocalDateTime(i, () -> def);
    }

    default LocalDateTime getLocalDateTime(int i, Supplier<LocalDateTime> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toLocalDateTime(e);
    }

    default LocalDate getLocalDate(int i) {
        return this.getLocalDate(i, Suppliers.nullSupplier());
    }

    default LocalDate getLocalDate(int i, LocalDate def) {
        return this.getLocalDate(i, () -> def);
    }

    default LocalDate getLocalDate(int i, Supplier<LocalDate> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return ConvertTypes.toLocalDate(e);
    }

    default BigDecimal getBigDecimal(int i) {
        return this.getBigDecimal(i, Suppliers.nullSupplier());
    }

    default BigDecimal getBigDecimal(int i, BigDecimal def) {
        return this.getBigDecimal(i, () -> def);
    }

    default BigDecimal getBigDecimal(int i, Supplier<BigDecimal> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return BigDecimals.toBigDecimal(e);
    }

    default BigInteger getBigInteger(int i) {
        return this.getBigInteger(i, Suppliers.nullSupplier());
    }

    default BigInteger getBigInteger(int i, BigInteger def) {
        return this.getBigInteger(i, () -> def);
    }

    default BigInteger getBigInteger(int i, Supplier<BigInteger> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return BigIntegers.toBigInteger(e);
    }

    default <V> V getObject(int i) {
        return this.getObject(i, Suppliers.nullSupplier());
    }

    default <V> V getObject(int i, V def) {
        return this.getObject(i, () -> def);
    }

    @SuppressWarnings("unchecked")
    default <V> V getObject(int i, Supplier<V> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return (V) e;
    }

    default E get(int i, E def) {
        return this.get(i, () -> def);
    }

    default E get(int i, Supplier<E> supplier) {
        Asserts.notNull(supplier);
        E e = get(i);
        if (e == null) {
            return supplier.get();
        }
        return e;
    }

}
