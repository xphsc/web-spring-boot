/*
 * Copyright (c) 2021 huipei.x
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

import cn.xphsc.web.common.lang.hash.Hashes;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class ObjectUtils {


    public static final String EMPTY_STRING = "";


    public static boolean isEmpty(Object o){
        return !isNotEmpty(o);

    }

    public static boolean isNotEmpty(Object object){
     return Optional.ofNullable(object).isPresent();
    }

    /**
     * 默认值
     * @param o   对象
     * @param def 默认值
     * @return 如果对象为空返回默认值
     */
    public static <T> T def(T o, T def) {
        if (isEmpty(o)) {
            return def;
        }
        return o;
    }

    /**
     * 默认值
     * @param o        对象
     * @param supplier 默认值提供者
     * @return 如果对象为空返回默认值
     */
    public static <T> T def(T o, Supplier<T> supplier) {
        if (isEmpty(o)) {
            return supplier.get();
        }
        return o;
    }


    public static String toString(Object object) {
        return toString(object, EMPTY_STRING);
    }


    public static String toString(Object object, String nullStr) {
        if (object == null) {
            return nullStr;
        } else if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        } else if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        } else if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        } else if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        } else if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        } else if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        } else if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        } else if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        } else if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        } else {
            return object.toString();
        }
    }

    public static <T> Object clone(T obj) {
        if(!(obj instanceof Cloneable)) {
            return null;
        } else {
            Object result;
            if(obj.getClass().isArray()) {
                Class checked = obj.getClass().getComponentType();
                if(!checked.isPrimitive()) {
                    result = ((Object[])((Object[])obj)).clone();
                } else {
                    int length = Array.getLength(obj);
                    result = Array.newInstance(checked, length);

                    while(length-- > 0) {
                        Array.set(result, length, Array.get(obj, length));
                    }
                }
            } else {
                try {
                    Method method = obj.getClass().getMethod("clone", new Class[0]);
                    result = method.invoke(obj, new Object[0]);
                } catch (NoSuchMethodException var4) {
                    throw new RuntimeException("Cloneable type " + obj.getClass().getName() + " has no clone method", var4);
                } catch (IllegalAccessException var5) {
                    throw new RuntimeException("Cannot clone Cloneable type " + obj.getClass().getName(), var5);
                } catch (InvocationTargetException var6) {
                    throw new RuntimeException("Exception cloning Cloneable type " + obj.getClass().getName(), var6.getCause());
                }
            }

            return result;
        }
    }

    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            return arrayEquals(o1, o2);
        }
        return false;
    }

    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[]) o1, (Object[]) o2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        return false;
    }
    public static int hashCode(Object obj) {
        return Hashes.hashCode(obj);
    }
}
