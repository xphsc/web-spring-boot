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
package cn.xphsc.web.common.lang.function;

import java.util.function.*;


/**
 * {@link if}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 提供者工具
 * @since 1.1.7
 */
@SuppressWarnings("unchecked")
public class Suppliers {

    private Suppliers() {
    }

    /**
     * null 提供者
     */
    public static final Supplier<?> NULL_SUPPLER = () -> null;

    public static final ByteSupplier BYTE_SUPPLIER = () -> (byte) 0;

    public static final ShortSupplier SHORT_SUPPLIER = () -> (short) 0;

    public static final IntSupplier INT_SUPPLIER = () -> 0;

    public static final LongSupplier LONG_SUPPLIER = () -> 0L;

    public static final FloatSupplier FLOAT_SUPPLIER = () -> 0F;

    public static final DoubleSupplier DOUBLE_SUPPLIER = () -> 0D;

    public static final BooleanSupplier BOOLEAN_SUPPLIER = () -> false;

    public static final CharSupplier CHAR_SUPPLIER = () -> (char) 0;

    // -------------------- getter --------------------

    public static <T> Supplier<T> nullSupplier() {
        return (Supplier<T>) NULL_SUPPLER;
    }

}
