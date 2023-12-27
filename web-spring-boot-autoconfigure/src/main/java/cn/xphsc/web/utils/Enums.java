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
package cn.xphsc.web.utils;

import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.common.lang.reflect.Fields;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 枚举工具类
 * @since 1.1.7
 */
public class Enums {

    private Enums() {
    }

    /**
     * 是否为Enum类
     * @param clazz 类
     * @return true enum
     */
    public static boolean isEnum(Class<?> clazz) {
        return clazz.isEnum();
    }

    /**
     * 是否为Enum类
     *
     * @param obj 类
     * @return true enum
     */
    public static boolean isEnum(Object obj) {
        return obj.getClass().isEnum();
    }

    /**
     * Enum转String
     * @param e Enum
     * @return name值
     */
    public static String toString(Enum<?> e) {
        return e != null ? e.name() : null;
    }

    /**
     * 字符串转枚举
     *
     * @param enumClass 枚举类
     * @param index     枚举索引
     * @return 枚举值 null表示无此对应枚举
     */
    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, int index) {
        E[] enumConstants = enumClass.getEnumConstants();
        return index < enumConstants.length ? enumConstants[index] : null;
    }

    /**
     * 字符串转枚举
     * @param enumClass 枚举类
     * @param index     枚举索引
     * @param def       默认值
     * @return 枚举值 null表示无此对应枚举
     */
    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, int index, E def) {
        E[] enumConstants = enumClass.getEnumConstants();
        return index < enumConstants.length ? enumConstants[index] : def;
    }

    /**
     * 字符串转枚举
     * @param enumClass 枚举类
     * @param value     值
     * @return 枚举值
     */
    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String value) {
        return Enum.valueOf(enumClass, value);
    }

    /**
     * 字符串转枚举
     *
     * @param enumClass 枚举类
     * @param value     值
     * @param def       默认值
     * @return 枚举值
     */
    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String value, E def) {
        if (enumClass == null || StringUtils.isBlank(value)) {
            return def;
        }
        try {
            return Enum.valueOf(enumClass, value);
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * 值映射为枚举
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param method    取值方法
     * @param <E>       对应枚举
     * @return ignore
     */
    public static <E extends Enum<?>> E valueOf(Class<E> enumClass, Object value, Method method) {
        E[] es = enumClass.getEnumConstants();
        for (E e : es) {
            Object v;
            try {
                method.setAccessible(true);
                v = method.invoke(e);
            } catch (IllegalAccessException | InvocationTargetException e1) {
                throw Exceptions.runtime("no such method");
            }
            if (value instanceof Number && v instanceof Number && new BigDecimal(String.valueOf(value)).compareTo(new BigDecimal(String.valueOf(v))) == 0) {
                return e;
            }
            if (Objects.equals(v, value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 枚举类name列表
     * @param clazz 枚举类
     * @return names
     */
    public static List<String> getNames(Class<? extends Enum<?>> clazz) {
        Enum<?>[] enums = clazz.getEnumConstants();
        if (enums == null) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>(enums.length);
        for (Enum<?> e : enums) {
            list.add(e.name());
        }
        return list;
    }

    /**
     * 获取枚举字段和对象的映射
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @return LinkedHashMap
     */
    public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> enumClass) {
        Map<String, E> map = new LinkedHashMap<>();
        for (E e : enumClass.getEnumConstants()) {
            map.put(e.name(), e);
        }
        return map;
    }

    /**
     * 判断值是存在枚举中
     * @param enumClass 枚举类
     * @param val       val
     * @return ignore
     */
    public static <E extends Enum<E>> boolean contains(Class<E> enumClass, String val) {
        return getEnumMap(enumClass).containsKey(val);
    }

    /**
     * 检查枚举值是否匹配
     * @param e   枚举
     * @param val val
     * @return ignore
     */
    public static boolean equalsIgnoreCase(Enum<?> e, String val) {
        return StringUtils.ignoreEquals(toString(e), val);
    }

    /**
     * 检查枚举值是否匹配
     * @param e   枚举
     * @param val val
     * @return ignore
     */
    public static boolean equals(Enum<?> e, String val) {
        return StringUtils.defaultEquals(toString(e), val);
    }

    /**
     * 获得枚举类中所有的字段名
     * @param clazz 枚举类
     * @return 字段名
     */
    public static List<String> getFields(Class<? extends Enum<?>> clazz) {
        return Fields.getFields(clazz)
                .stream()
                .filter(field -> !field.getType().isEnum())
                .filter(field -> !field.getName().contains("$VALUES"))
                .filter(field -> !"ordinal".equals(field.getName()))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    /**
     * 获得枚举名字段值的Map
     * @param clazz     枚举类
     * @param fieldName 字段名
     * @return ignore
     */
    public static Map<String, Object> getFieldValues(Class<? extends Enum<?>> clazz, String fieldName) {
        Enum<?>[] enums = clazz.getEnumConstants();
        if (enums == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(enums.length);
        for (Enum<?> e : enums) {
            map.put(e.name(), Fields.getFieldValue(e, fieldName));
        }
        return map;
    }

}
