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

import cn.xphsc.web.common.exception.Exceptions;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Predicate;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class ArrayUtils {

    /**
     * An empty immutable {@code String} array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final int INITIAL_HASH = 7;
    private static final int MULTIPLIER = 31;
    private static final Object[] EMPTY_OBJECT_ARR = {};
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(long[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(int[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(short[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(char[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(byte[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(double[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(float[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(boolean[] array) {
        return array != null && array.length != 0;
    }

    public static Integer[] wrap(int... values) {
        int length = values.length;
        Integer[] array = new Integer[length];

        for(int i = 0; i < length; ++i) {
            array[i] = Integer.valueOf(values[i]);
        }

        return array;
    }

    public static int[] unWrap(Integer... values) {
        int length = values.length;
        int[] array = new int[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i].intValue();
        }

        return array;
    }

    public static Long[] wrap(long... values) {
        int length = values.length;
        Long[] array = new Long[length];

        for(int i = 0; i < length; ++i) {
            array[i] = Long.valueOf(values[i]);
        }

        return array;
    }

    public static long[] unWrap(Long... values) {
        int length = values.length;
        long[] array = new long[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i].longValue();
        }

        return array;
    }

    public static Character[] wrap(char... values) {
        int length = values.length;
        Character[] array = new Character[length];

        for(int i = 0; i < length; ++i) {
            array[i] = Character.valueOf(values[i]);
        }

        return array;
    }

    public static char[] unWrap(Character... values) {
        int length = values.length;
        char[] array = new char[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i].charValue();
        }

        return array;
    }

    public static Byte[] wrap(byte... values) {
        int length = values.length;
        Byte[] array = new Byte[length];
        for(int i = 0; i < length; ++i) {
            array[i] = Byte.valueOf(values[i]);
        }

        return array;
    }

    public static byte[] unWrap(Byte... values) {
        int length = values.length;
        byte[] array = new byte[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i].byteValue();
        }

        return array;
    }

    public static Short[] wrap(short... values) {
        int length = values.length;
        Short[] array = new Short[length];

        for(int i = 0; i < length; ++i) {
            array[i] = Short.valueOf(values[i]);
        }

        return array;
    }

    public static short[] unWrap(Short... values) {
        int length = values.length;
        short[] array = new short[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i].shortValue();
        }

        return array;
    }

    public static Float[] wrap(float... values) {
        int length = values.length;
        Float[] array = new Float[length];

        for(int i = 0; i < length; ++i) {
            array[i] = Float.valueOf(values[i]);
        }

        return array;
    }

    public static float[] unWrap(Float... values) {
        int length = values.length;
        float[] array = new float[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i].floatValue();
        }

        return array;
    }

    public static Double[] wrap(double... values) {
        int length = values.length;
        Double[] array = new Double[length];

        for(int i = 0; i < length; ++i) {
            array[i] = Double.valueOf(values[i]);
        }

        return array;
    }

    public static double[] unWrap(Double... values) {
        int length = values.length;
        double[] array = new double[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i].doubleValue();
        }

        return array;
    }

    public static Boolean[] wrap(boolean... values) {
        int length = values.length;
        Boolean[] array = new Boolean[length];

        for(int i = 0; i < length; ++i) {
            array[i] = Boolean.valueOf(values[i]);
        }

        return array;
    }

    public static boolean[] unWrap(Boolean... values) {
        int length = values.length;
        boolean[] array = new boolean[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i].booleanValue();
        }

        return array;
    }


    //--------

    public static void shuffle(int[] arr) {
        int size = arr.length;
        SecureRandom rnd = new SecureRandom();
        // Shuffle array
        for (int i=size; i>1; i--) {
            swap(arr, i - 1, rnd.nextInt(i));
        }
    }

    /**
     * 交换数组中的位置
     * @param arr 数组
     * @param len 数组长度
     * @return 处理空值的次数
     */
    private static int compactSwap(Object[] arr, int len) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (arr[i] != null) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // -------------------- min --------------------

    /**
     * 最小值
     * @param arr 数组
     * @return 最小值
     */
    public static byte min(byte[] arr) {
        int len = length(arr);
        if (len == 0) {
            return 0;
        }
        byte min = arr[0];
        for (int i = 1; i < len; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    public static short min(short[] arr) {
        int len = length(arr);
        if (len == 0) {
            return 0;
        }
        short min = arr[0];
        for (int i = 1; i < len; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    public static int min(int[] arr) {
        int len = length(arr);
        if (len == 0) {
            return 0;
        }
        int min = arr[0];
        for (int i = 1; i < len; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    public static long min(long[] arr) {
        int len = length(arr);
        if (len == 0) {
            return 0;
        }
        long min = arr[0];
        for (int i = 1; i < len; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    public static float min(float[] arr) {
        int len = length(arr);
        if (len == 0) {
            return 0;
        }
        float min = arr[0];
        for (int i = 1; i < len; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    public static double min(double[] arr) {
        int len = length(arr);
        if (len == 0) {
            return 0;
        }
        double min = arr[0];
        for (int i = 1; i < len; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    public static char min(char[] arr) {
        int len = length(arr);
        if (len == 0) {
            return 0;
        }
        char min = arr[0];
        for (int i = 1; i < len; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    public static <T extends Comparable<T>> T min(T[] arr) {
        int len = length(arr);
        if (len == 0) {
            return null;
        }
        T min = arr[0];
        int offset = 1;
        if (min == null) {
            for (int blen = arr.length; offset < blen; offset++) {
                T bi = arr[offset];
                if (bi != null) {
                    min = bi;
                    offset++;
                    break;
                }
            }
            if (min == null) {
                return null;
            }
        }
        for (int i = offset; i < len; i++) {
            if (arr[i] != null && min.compareTo(arr[i]) > 0) {
                min = arr[i];
            }
        }
        return min;
    }

    public static <T> T min(T[] arr, Comparator<T> c) {
        int len = length(arr);
        if (len == 0) {
            return null;
        }
        T min = arr[0];
        int offset = 1;
        if (min == null) {
            for (int blen = arr.length; offset < blen; offset++) {
                T bi = arr[offset];
                if (bi != null) {
                    min = bi;
                    offset++;
                    break;
                }
            }
            if (min == null) {
                return null;
            }
        }
        for (int i = offset; i < len; i++) {
            if (arr[i] != null && c.compare(min, arr[i]) > 0) {
                min = arr[i];
            }
        }
        return min;
    }


      /*
        * 排除数组中指定的值
        * @param arr 数组
       * @param es  排除值
       * @return 处理后的数组, 如果数组没有包含的值则直接返回
       * */
    public static byte[] exclude(byte[] arr, byte... es) {
        int len = length(arr);
        if (len == 0) {
            return new byte[0];
        }
        if (isEmpty(es)) {
            return arr;
        }
        int num = excludeSwap(arr, len, i -> {
            for (byte e : es) {
                if (i == e) {
                    return true;
                }
            }
            return false;
        });
        byte[] na = new byte[len - num];
        System.arraycopy(arr, 0, na, 0, len - num);
        return na;
    }
    // -------------------- length --------------------

    public static <T> int length(T[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(byte[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(short[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(int[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(long[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(float[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(double[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(char[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(boolean[] arr) {
        return arr == null ? 0 : arr.length;
    }

// -------------------- first --------------------

    /**
     * 获取数组第一个元素
     *
     * @param arr array
     * @return 第一个元素 长度为0则抛出异常
     */
    public static <T> T first(T[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[0];
    }

    public static byte first(byte[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[0];
    }

    public static short first(short[] array) {
        int length = length(array);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return array[0];
    }

    public static int first(int[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[0];
    }

    public static long first(long[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[0];
    }

    public static float first(float[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[0];
    }

    public static double first(double[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[0];
    }

    public static boolean first(boolean[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[0];
    }

    public static char first(char[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[0];
    }

    /**
     * 获取数组第一个元素
     *
     * @param arr array
     * @param def 默认值
     * @return 第一个元素
     */
    public static <T> T first(T[] arr, T def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    public static byte first(byte[] arr, byte def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    public static short first(short[] arr, short def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    public static int first(int[] arr, int def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    public static long first(long[] arr, long def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    public static float first(float[] arr, float def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    public static double first(double[] arr, double def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    public static boolean first(boolean[] arr, boolean def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    public static char first(char[] arr, char def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[0];
    }

    // -------------------- last --------------------

    /**
     * 获取数组最后一个元素
     *
     * @param arr array
     * @return 第一个元素  长度为0则抛出异常
     */
    public static <T> T last(T[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    public static byte last(byte[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    public static short last(short[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    public static int last(int[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    public static long last(long[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    public static float last(float[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    public static double last(double[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    public static boolean last(boolean[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    public static char last(char[] arr) {
        int length = length(arr);
        if (length == 0) {
            throw Exceptions.arrayIndex("array is empty");
        }
        return arr[length - 1];
    }

    /**
     * 获取数组最后一个元素
     *
     * @param arr array
     * @param def 默认值
     * @return 第一个元素
     */
    public static <T> T last(T[] arr, T def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    public static byte last(byte[] arr, byte def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    public static short last(short[] arr, short def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    public static int last(int[] arr, int def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    public static long last(long[] arr, long def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    public static float last(float[] arr, float def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    public static double last(double[] arr, double def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    public static boolean last(boolean[] arr, boolean def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    public static char last(char[] arr, char def) {
        int length = length(arr);
        if (length == 0) {
            return def;
        }
        return arr[length - 1];
    }

    /**
     * 交换数组中的位置
     * @param arr 数组
     * @param len 数组长度
     * @return 处理空值的次数
     */
    private static int excludeSwap(byte[] arr, int len, Predicate<Byte> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static int excludeSwap(short[] arr, int len, Predicate<Short> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static int excludeSwap(int[] arr, int len, Predicate<Integer> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static int excludeSwap(long[] arr, int len, Predicate<Long> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static int excludeSwap(float[] arr, int len, Predicate<Float> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static int excludeSwap(double[] arr, int len, Predicate<Double> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static int excludeSwap(boolean[] arr, int len, Predicate<Boolean> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static int excludeSwap(char[] arr, int len, Predicate<Character> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static int excludeSwap(Object[] arr, int len, Predicate<Object> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    private static <T> int excludeSwaps(T[] arr, int len, Predicate<T> p) {
        int num = 0;
        for (int i = 0; i < len; i++) {
            if (!p.test(arr[i])) {
                continue;
            }
            if (i == len - num + 1) {
                break;
            }
            num++;
            if (i != len - num) {
                System.arraycopy(arr, i + 1, arr, i, len - i - 1);
                i--;
            }
        }
        return num;
    }

    public static boolean equals(Object o1, Object o2) {
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

    public static Object[] wrap(Object obj) {
        if (null == obj) {
            return null;
        }
        if (isArray(obj)) {
            try {
                return (Object[]) obj;
            } catch (Exception e) {
                final String className = obj.getClass().getComponentType().getName();
                switch (className) {
                    case "long":
                        return wrap((long[]) obj);
                    case "int":
                        return wrap((int[]) obj);
                    case "short":
                        return wrap((short[]) obj);
                    case "char":
                        return wrap((char[]) obj);
                    case "byte":
                        return wrap((byte[]) obj);
                    case "boolean":
                        return wrap((boolean[]) obj);
                    case "float":
                        return wrap((float[]) obj);
                    case "double":
                        return wrap((double[]) obj);
                    default:
                        throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException(StringUtils.format("[{}] is not Array!", new Object[]{obj.getClass()}));
    }


    public static String join(Object array, CharSequence conjunction) {
        if(null == array){
            throw new NullPointerException("Array must be not null!");
        }
        if (false == isArray(array)) {
            throw new IllegalArgumentException(StringUtils.format("[{}] is not a Array!", array.getClass()));
        }

        final Class<?> componentType = array.getClass().getComponentType();
        if (componentType.isPrimitive()) {
            final String componentTypeName = componentType.getName();
            switch (componentTypeName) {
                case "long":
                    return join((long[]) array, conjunction);
                case "int":
                    return join((int[]) array, conjunction);
                case "short":
                    return join((short[]) array, conjunction);
                case "char":
                    return join((char[]) array, conjunction);
                case "byte":
                    return join((byte[]) array, conjunction);
                case "boolean":
                    return join((boolean[]) array, conjunction);
                case "float":
                    return join((float[]) array, conjunction);
                case "double":
                    return join((double[]) array, conjunction);
                    default:
            }
        } else {
            return join((Object[]) array, conjunction);
        }
        return null;
    }

    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else if (ArrayUtils.isArray(obj)) {
            // 对象数组
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception ignore) {
                //ignore
            }
        }

        return obj.toString();
    }
    public static boolean isArray(Object obj) {
        if(null == obj) {
            throw new NullPointerException("Object check for isArray is null");
        } else {
            return obj.getClass().isArray();
        }
    }

    // -------------------- indexOf --------------------

    /**
     * 查找第一个查询到的元素的位置
     *
     * @param arr   数组
     * @param s     元素
     * @param start 开始向后查找的下标
     * @return 位置
     */
    public static int indexOf(byte[] arr, byte s, int start) {
        return indexOfs(arr, s, start);
    }

    public static int indexOf(short[] arr, short s, int start) {
        return indexOfs(arr, s, start);
    }

    public static int indexOf(int[] arr, int s, int start) {
        return indexOfs(arr, s, start);
    }

    public static int indexOf(long[] arr, long s, int start) {
        return indexOfs(arr, s, start);
    }

    public static int indexOf(float[] arr, float s, int start) {
        return indexOfs(arr, s, start);
    }

    public static int indexOf(double[] arr, double s, int start) {
        return indexOfs(arr, s, start);
    }

    public static int indexOf(char[] arr, char s, int start) {
        return indexOfs(arr, s, start);
    }

    public static int indexOf(boolean[] arr, boolean s, int start) {
        return indexOfs(arr, s, start);
    }

    public static <T> int indexOf(T[] arr, T s, int start) {
        return indexOfs(arr, s, start);
    }

    public static int indexOf(byte[] arr, byte s) {
        return indexOfs(arr, s, 0);
    }

    public static int indexOf(short[] arr, short s) {
        return indexOfs(arr, s, 0);
    }

    public static int indexOf(int[] arr, int s) {
        return indexOfs(arr, s, 0);
    }

    public static int indexOf(long[] arr, long s) {
        return indexOfs(arr, s, 0);
    }

    public static int indexOf(float[] arr, float s) {
        return indexOfs(arr, s, 0);
    }

    public static int indexOf(double[] arr, double s) {
        return indexOfs(arr, s, 0);
    }

    public static int indexOf(char[] arr, char s) {
        return indexOfs(arr, s, 0);
    }

    public static int indexOf(boolean[] arr, boolean s) {
        return indexOfs(arr, s, 0);
    }

    public static <T> int indexOf(T[] arr, T s) {
        return indexOfs(arr, s, 0);
    }

    private static <T> int indexOfs(T arr, T s, int start) {
        Object[] array = ofs(arr);
        int length = array.length;
        if (start < 0) {
            return -1;
        } else if (start >= length) {
            return -1;
        }
        for (int i = start; i < length; i++) {
            if (ObjectUtils.nullSafeEquals(array[i], s)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 可接收基本类型的数组, 返回Object[]
     *
     * @param arr arr
     * @return array
     */
    public static Object[] ofs(Object... arr) {
        int lengths = lengths(arr);
        if (lengths == 0) {
            return EMPTY_OBJECT_ARR;
        }
        Object[] r = new Object[lengths];
        int len = arr.length;
        if (len != lengths || (len == 1 && isArray(arr[0]))) {
            for (int j = 0; j < lengths; j++) {
                r[j] = Array.get(arr[0], j);
            }
        } else {
            return arr;
        }
        return r;
    }
    // -------------------- length --------------------

    /**
     * 获取数组长度
     *
     * @param arr 数组 如果传参是一个基本类型数组, 返回值也是基本类型数组的长度
     * @return 长度
     * <p>
     * 在默认情况下, 如果可变参数o, 传参是基本类型的数组, 使用length属性判断, o.length = 1
     */
    public static int lengths(Object... arr) {
        if (arr == null) {
            return 0;
        }
        if (arr.length == 1) {
            Object t = arr[0];
            if (t instanceof byte[]) {
                return ((byte[]) t).length;
            } else if (t instanceof short[]) {
                return ((short[]) t).length;
            } else if (t instanceof int[]) {
                return ((int[]) t).length;
            } else if (t instanceof long[]) {
                return ((long[]) t).length;
            } else if (t instanceof float[]) {
                return ((float[]) t).length;
            } else if (t instanceof double[]) {
                return ((double[]) t).length;
            } else if (t instanceof char[]) {
                return ((char[]) t).length;
            } else if (t instanceof boolean[]) {
                return ((boolean[]) t).length;
            } else if (t instanceof Object[]) {
                return ((Object[]) t).length;
            }
        }
        return arr.length;
    }

    // -------------------- contains --------------------

    /**
     * 判断数组是否包含某个元素
     *
     * @param arr 数组
     * @param s   元素
     * @return true包含
     */
    public static boolean contains(byte[] arr, byte s) {
        return indexOf(arr, s) != -1;
    }

    public static boolean contains(short[] arr, short s) {
        return indexOf(arr, s) != -1;
    }

    public static boolean contains(int[] arr, int s) {
        return indexOf(arr, s) != -1;
    }

    public static boolean contains(long[] arr, long s) {
        return indexOf(arr, s) != -1;
    }

    public static boolean contains(float[] arr, float s) {
        return indexOf(arr, s) != -1;
    }

    public static boolean contains(double[] arr, double s) {
        return indexOf(arr, s) != -1;
    }

    public static boolean contains(char[] arr, char s) {
        return indexOf(arr, s) != -1;
    }

    public static boolean contains(boolean[] arr, boolean s) {
        return indexOf(arr, s) != -1;
    }

    public static <T> boolean contains(T[] arr, T s) {
        return indexOf(arr, s) != -1;
    }

    // -------------------- hashcode --------------------

    /**
     * 获取数组的hashCode
     *
     * @param arr arr
     * @return hashCode
     */
    public static int hashCode(Object[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (Object element : arr) {
            hash = MULTIPLIER * hash + ObjectUtils.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(byte[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (byte element : arr) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int hashCode(short[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (short element : arr) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int hashCode(int[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (int element : arr) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static int hashCode(long[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (long element : arr) {
            hash = MULTIPLIER * hash + Long.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(float[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (float element : arr) {
            hash = MULTIPLIER * hash + Float.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(double[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (double element : arr) {
            hash = MULTIPLIER * hash + Double.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(boolean[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (boolean element : arr) {
            hash = MULTIPLIER * hash + Boolean.hashCode(element);
        }
        return hash;
    }

    public static int hashCode(char[] arr) {
        if (arr == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (char element : arr) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }
    public static byte[] resize(byte[] arr, int newSize) {
        if (newSize <= 0) {
            return new byte[0];
        }
        if (arr.length < newSize) {
            byte[] nbs = new byte[newSize];
            System.arraycopy(arr, 0, nbs, 0, arr.length);
            return nbs;
        } else if (arr.length > newSize) {
            byte[] nbs = new byte[newSize];
            System.arraycopy(arr, 0, nbs, 0, newSize);
            return nbs;
        }
        return arr;
    }
}
