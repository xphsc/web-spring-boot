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



import java.text.DecimalFormat;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.6
 */
public class NumberUtils {

    private NumberUtils() {
    }



    // -------------------- zero --------------------

    /**
     * 是否为0
     *
     * @param x ignore
     * @return true 0
     */
    public static boolean isZero(byte x) {
        return x == 0;
    }

    public static boolean isZero(short x) {
        return x == 0;
    }

    public static boolean isZero(int x) {
        return x == 0;
    }

    public static boolean isZero(long x) {
        return x == 0;
    }

    public static boolean isZero(float x) {
        return x == 0;
    }

    public static boolean isZero(double x) {
        return x == 0;
    }

    /**
     * 是否不为0
     *
     * @param x ignore
     * @return true 非0
     */
    public static boolean isNotZero(byte x) {
        return x != 0;
    }

    public static boolean isNotZero(short x) {
        return x != 0;
    }

    public static boolean isNotZero(int x) {
        return x != 0;
    }

    public static boolean isNotZero(long x) {
        return x != 0;
    }

    public static boolean isNotZero(float x) {
        return x != 0;
    }

    public static boolean isNotZero(double x) {
        return x != 0;
    }

    /**
     * 是否小于0
     *
     * @param x x
     * @return true 小于0
     */
    public static boolean ltZero(byte x) {
        return x < 0;
    }

    public static boolean ltZero(short x) {
        return x < 0;
    }

    public static boolean ltZero(int x) {
        return x < 0;
    }

    public static boolean ltZero(long x) {
        return x < 0;
    }

    public static boolean ltZero(float x) {
        return x < 0;
    }

    public static boolean ltZero(double x) {
        return x < 0;
    }

    /**
     * 是否小于等于0
     *
     * @param x x
     * @return true 小于等于0
     */
    public static boolean lteZero(byte x) {
        return x <= 0;
    }

    public static boolean lteZero(short x) {
        return x <= 0;
    }

    public static boolean lteZero(int x) {
        return x <= 0;
    }

    public static boolean lteZero(long x) {
        return x <= 0;
    }

    public static boolean lteZero(float x) {
        return x <= 0;
    }

    public static boolean lteZero(double x) {
        return x <= 0;
    }

    /**
     * 是否大于0
     *
     * @param x x
     * @return true 大于0
     */
    public static boolean gtZero(byte x) {
        return x > 0;
    }

    public static boolean gtZero(short x) {
        return x > 0;
    }

    public static boolean gtZero(int x) {
        return x > 0;
    }

    public static boolean gtZero(long x) {
        return x > 0;
    }

    public static boolean gtZero(float x) {
        return x > 0;
    }

    public static boolean gtZero(double x) {
        return x > 0;
    }

    /**
     * 是否大于等于0
     *
     * @param x x
     * @return true 大于等于0
     */
    public static boolean gteZero(byte x) {
        return x >= 0;
    }

    public static boolean gteZero(short x) {
        return x >= 0;
    }

    public static boolean gteZero(int x) {
        return x >= 0;
    }

    public static boolean gteZero(long x) {
        return x >= 0;
    }

    public static boolean gteZero(float x) {
        return x >= 0;
    }

    public static boolean gteZero(double x) {
        return x >= 0;
    }

    /**
     * 是否全部为0
     *
     * @param array array
     * @return true 全为0
     */
    public static boolean isAllZero(byte... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (byte b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllZero(short... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (short b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllZero(int... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (int b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllZero(long... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (long b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllZero(float... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (float b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllZero(double... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (double b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全部不为0
     *
     * @param array array
     * @return true 全不为0
     */
    public static boolean isNoneZero(byte... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (byte b : array) {
            if (b == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneZero(short... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (short b : array) {
            if (b == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneZero(int... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (int b : array) {
            if (b == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneZero(long... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (long b : array) {
            if (b == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneZero(float... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (float b : array) {
            if (b == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneZero(double... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (double b : array) {
            if (b == 0) {
                return false;
            }
        }
        return true;
    }

    // -------------------- isNegative --------------------

    /**
     * 是否为负数
     *
     * @param x ignore
     * @return true 负数
     */
    public static boolean isNegative(byte x) {
        return x < 0;
    }

    public static boolean isNegative(short x) {
        return x < 0;
    }

    public static boolean isNegative(int x) {
        return x < 0;
    }

    public static boolean isNegative(long x) {
        return x < 0;
    }

    public static boolean isNegative(float x) {
        return x < 0;
    }

    public static boolean isNegative(double x) {
        return x < 0;
    }

    /**
     * 是否不为负数
     *
     * @param x ignore
     * @return true 非负数
     */
    public static boolean isNotNegative(byte x) {
        return x >= 0;
    }

    public static boolean isNotNegative(short x) {
        return x >= 0;
    }

    public static boolean isNotNegative(int x) {
        return x >= 0;
    }

    public static boolean isNotNegative(long x) {
        return x >= 0;
    }

    public static boolean isNotNegative(float x) {
        return x >= 0;
    }

    public static boolean isNotNegative(double x) {
        return x >= 0;
    }

    /**
     * 是否全为负数
     *
     * @param array array
     * @return true 全为负数
     */
    public static boolean isAllNegative(byte... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (byte b : array) {
            if (b >= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNegative(short... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (short b : array) {
            if (b >= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNegative(int... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (int b : array) {
            if (b >= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNegative(long... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (long b : array) {
            if (b >= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNegative(float... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (float b : array) {
            if (b >= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNegative(double... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (double b : array) {
            if (b >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全不为负数
     *
     * @param array array
     * @return true 全不为负数
     */
    public static boolean isNoneNegative(byte... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (byte b : array) {
            if (b < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneNegative(short... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (short b : array) {
            if (b < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneNegative(int... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (int b : array) {
            if (b < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneNegative(long... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (long b : array) {
            if (b < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneNegative(float... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (float b : array) {
            if (b < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneNegative(double... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (double b : array) {
            if (b < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将给定的数除余为指定区间中的数
     *
     * @param num        num
     * @param startRange 区间开始
     * @param endRange   区间结束
     * @return %
     */
    public static int getRangeNum(int num, int startRange, int endRange) {
        return startRange + (num % (endRange - startRange));
    }


    // -------------------- isNaN --------------------

    /**
     * 是否为NaN
     *
     * @param x ignore
     * @return true 为NaN
     */
    public static boolean isNaN(Float x) {
        return x.isNaN();
    }

    public static boolean isNaN(Double x) {
        return x.isNaN();
    }

    /**
     * 是否不为NaN
     *
     * @param x ignore
     * @return true 不为NaN
     */
    public static boolean isNotNaN(Float x) {
        return !x.isNaN();
    }

    public static boolean isNotNaN(Double x) {
        return !x.isNaN();
    }

    /**
     * 是否全为NaN
     *
     * @param array array
     * @return true 全为NaN
     */
    public static boolean isAllNaN(float... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (float b : array) {
            if (!Float.isNaN(b)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNaN(double... array) {
        if (ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (double b : array) {
            if (!Double.isNaN(b)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全不为NaN
     *
     * @param array array
     * @return true 全不为NaN
     */
    public static boolean isNoneNaN(float... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (float b : array) {
            if (Float.isNaN(b)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneNaN(double... array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        for (double b : array) {
            if (Double.isNaN(b)) {
                return false;
            }
        }
        return true;
    }

    // -------------------- compare --------------------

    /**
     * 比较接口
     *
     * @param x x
     * @param y y
     * @return -1 0 1
     */
    public static int compare(byte x, byte y) {
        return Byte.compare(x, y);
    }

    public static int compare(short x, short y) {
        return Short.compare(x, y);
    }

    public static int compare(int x, int y) {
        return Integer.compare(x, y);
    }

    public static int compare(long x, long y) {
        return Long.compare(x, y);
    }

    public static int compare(float x, float y) {
        return Float.compare(x, y);
    }

    public static int compare(double x, double y) {
        return Double.compare(x, y);
    }

    // -------------------- min --------------------

    /**
     * 最小值
     *
     * @param array array
     * @return 最小值
     */
    public static byte min(byte... array) {
        byte min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static short min(short... array) {
        short min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static int min(int... array) {
        int min = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] < min) {
                min = array[j];
            }
        }
        return min;
    }

    public static long min(long... array) {
        long min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static float min(float... array) {
        float min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Float.isNaN(array[i])) {
                return Float.NaN;
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static double min(double... array) {
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Double.isNaN(array[i])) {
                return Double.NaN;
            }
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    // -------------------- max --------------------

    /**
     * 最大值
     *
     * @param array array
     * @return 最大值
     */
    public static byte max(byte... array) {
        byte max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static short max(short... array) {
        short max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static int max(int... array) {
        int max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] > max) {
                max = array[j];
            }
        }
        return max;
    }

    public static long max(long... array) {
        long max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] > max) {
                max = array[j];
            }
        }
        return max;
    }

    public static float max(float... array) {
        float max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (Float.isNaN(array[j])) {
                return Float.NaN;
            }
            if (array[j] > max) {
                max = array[j];
            }
        }
        return max;
    }

    public static double max(double... array) {
        double max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (Double.isNaN(array[j])) {
                return Double.NaN;
            }
            if (array[j] > max) {
                max = array[j];
            }
        }
        return max;
    }

    // -------------------- sum --------------------

    public static long sum(byte... array) {
        long max = 0;
        for (int j = 0; j < array.length; j++) {
            max += array[j];
        }
        return max;
    }

    public static long sum(short... array) {
        long max = 0;
        for (int j = 0; j < array.length; j++) {
            max += array[j];
        }
        return max;
    }

    public static long sum(int... array) {
        long max = 0;
        for (int j = 0; j < array.length; j++) {
            max += array[j];
        }
        return max;
    }

    public static long sum(long... array) {
        long max = 0;
        for (int j = 0; j < array.length; j++) {
            max += array[j];
        }
        return max;
    }

    public static double sum(float... array) {
        double max = 0;
        for (int j = 0; j < array.length; j++) {
            if (!Float.isNaN(array[j])) {
                max += array[j];
            }
        }
        return max;
    }

    public static double sum(double... array) {
        double max = 0;
        for (int j = 0; j < array.length; j++) {
            if (!Double.isNaN(array[j])) {
                max += array[j];
            }
        }
        return max;
    }

    // -------------------- avg --------------------

    public static byte avg(byte... array) {
        int len = ArrayUtils.length(array);
        if (len == 0) {
            return 0;
        }
        return (byte) (sum(array) / len);
    }

    public static short avg(short... array) {
        int len = ArrayUtils.length(array);
        if (len == 0) {
            return 0;
        }
        return (short) (sum(array) / len);
    }

    public static int avg(int... array) {
        int len = ArrayUtils.length(array);
        if (len == 0) {
            return 0;
        }
        return (int) (sum(array) / len);
    }

    public static long avg(long... array) {
        int len = ArrayUtils.length(array);
        if (len == 0) {
            return 0;
        }
        return sum(array) / len;
    }

    public static float avg(float... array) {
        int len = ArrayUtils.length(array);
        if (len == 0) {
            return 0;
        }
        return (float) (sum(array) / len);
    }

    public static double avg(double... array) {
        int len = ArrayUtils.length(array);
        if (len == 0) {
            return 0;
        }
        return sum(array) / len;
    }






    // -------------------- scale --------------------
    /**
     * 设置小数位
     *
     * @param d          double
     * @param decimalLen 小数位
     * @return string
     */
    public static String setScale(double d, int decimalLen) {
        DecimalFormat format = new DecimalFormat("#." + StringUtils.repeat('#', decimalLen));
        return format.format(d);
    }

    /**
     * 设置小数位
     *
     * @param f          float
     * @param decimalLen 小数位
     * @return string
     */
    public static String setScale(float f, int decimalLen) {
        DecimalFormat format = new DecimalFormat("#." + StringUtils.repeat('#', decimalLen));
        return format.format(f);
    }

    /**
     * 清空小数
     *
     * @param f float
     * @return int
     */
    public static int cleanDecimal(float f) {
        return Integer.parseInt(new DecimalFormat("#").format(f));
    }

    /**
     * 清空小数
     *
     * @param d double
     * @return string
     */
    public static long cleanDecimal(double d) {
        return Long.parseLong(new DecimalFormat("#").format(d));
    }

    /**
     * 清空小数
     *
     * @param f float
     * @return string
     */
    public static String cleanFloatDecimal(float f) {
        return new DecimalFormat("#").format(f);
    }

    /**
     * 清空小数
     *
     * @param d double
     * @return string
     */
    public static String cleanDoubleDecimal(double d) {
        return new DecimalFormat("#").format(d);
    }


    /**
     * 是否有小数位
     *
     * @param f float
     * @return true有
     */
    public static boolean isDecimal(float f) {
        return Float.compare(f, ((float) (int) f)) != 0;
    }

    /**
     * 是否有小数位
     *
     * @param d double
     * @return true有
     */
    public static boolean isDecimal(double d) {
        return Double.compare(d, ((double) (long) d)) != 0;
    }

    /**
     * 获取最小二次幂
     *
     * @param c num
     * @return 最小二次幂 6 -> 8
     */
    public static int getMin2Power(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
    }

}
