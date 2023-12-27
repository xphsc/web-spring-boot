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
package cn.xphsc.web.common.lang.hash;
import cn.xphsc.web.utils.ArrayUtils;
import java.util.concurrent.ConcurrentMap;


/**
 * {@link ConcurrentMap}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: hash 工具类
 * @since 1.1.7
 */
public class Hashes {

    private Hashes() {
    }

    /**
     * 获取对象的hash值
     *
     * @param obj obj
     * @return hashCode
     */
    public static int hashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof Object[]) {
                return ArrayUtils.hashCode((Object[]) obj);
            } else if (obj instanceof byte[]) {
                return ArrayUtils.hashCode((byte[]) obj);
            } else if (obj instanceof short[]) {
                return ArrayUtils.hashCode((short[]) obj);
            } else if (obj instanceof int[]) {
                return ArrayUtils.hashCode((int[]) obj);
            } else if (obj instanceof long[]) {
                return ArrayUtils.hashCode((long[]) obj);
            } else if (obj instanceof float[]) {
                return ArrayUtils.hashCode((float[]) obj);
            } else if (obj instanceof double[]) {
                return ArrayUtils.hashCode((double[]) obj);
            } else if (obj instanceof boolean[]) {
                return ArrayUtils.hashCode((boolean[]) obj);
            } else if (obj instanceof char[]) {
                return ArrayUtils.hashCode((char[]) obj);
            }
        }
        return obj.hashCode();
    }

    /**
     * 根据对象的内存地址生成相应的Hash值
     *
     * @param obj 对象
     * @return hash值
     */
    public static int identityHashCode(Object obj) {
        return System.identityHashCode(obj);
    }

    /**
     * java 默认hash
     *
     * @param str 字符串
     * @return hash值
     */
    public static int hash(String str) {
        int h = 0;
        int off = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            h = 31 * h + str.charAt(off++);
        }
        return h;
    }

    /**
     * 加法hash
     * @param s     s
     * @param prime 质数
     * @return hash
     */
    public static int additiveHash(String s, int prime) {
        int hash, i;
        for (hash = s.length(), i = 0; i < s.length(); i++) {
            hash += s.charAt(i);
        }
        return hash % prime;
    }

    /**
     * 旋转hash
     * @param s     s
     * @param prime 质数
     * @return hash值
     */
    public static int rotatingHash(String s, int prime) {
        int hash, i;
        for (hash = s.length(), i = 0; i < s.length(); ++i) {
            hash = (hash << 4) ^ (hash >> 28) ^ s.charAt(i);
        }
        return hash % prime;
    }

    /**
     * 一次一个hash
     * @param s s
     * @return hash
     */
    public static int oneByOneHash(String s) {
        int hash, i;
        for (hash = 0, i = 0; i < s.length(); ++i) {
            hash += s.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        // return (hash & M_MASK);
        return hash;
    }

    /**
     * Bernstein's hash
     * @param s s
     * @return hash
     */
    public static int bernstein(String s) {
        int hash = 0;
        int i;
        for (i = 0; i < s.length(); ++i) {
            hash = 33 * hash + s.charAt(i);
        }
        return hash;
    }

    /**
     * Universal Hash
     * @param key  字节数组
     * @param mask 掩码
     * @param tab  tab
     * @return hash
     */
    public static int universal(char[] key, int mask, int[] tab) {
        int hash = key.length, i, len = key.length;
        for (i = 0; i < (len << 3); i += 8) {
            char k = key[i >> 3];
            if ((k & 0x01) == 0) {
                hash ^= tab[i];
            }
            if ((k & 0x02) == 0) {
                hash ^= tab[i + 1];
            }
            if ((k & 0x04) == 0) {
                hash ^= tab[i + 2];
            }
            if ((k & 0x08) == 0) {
                hash ^= tab[i + 3];
            }
            if ((k & 0x10) == 0) {
                hash ^= tab[i + 4];
            }
            if ((k & 0x20) == 0) {
                hash ^= tab[i + 5];
            }
            if ((k & 0x40) == 0) {
                hash ^= tab[i + 6];
            }
            if ((k & 0x80) == 0) {
                hash ^= tab[i + 7];
            }
        }
        return (hash & mask);
    }

    /**
     * Zobrist Hash
     * @param key  字节数组
     * @param mask 掩码
     * @param tab  tab
     * @return hash
     */
    public static int zobrist(char[] key, int mask, int[][] tab) {
        int hash, i;
        for (hash = key.length, i = 0; i < key.length; ++i) {
            hash ^= tab[i][key[i]];
        }
        return (hash & mask);
    }

    /**
     * 改进的32位FNV 1
     * @param data 数组
     * @return hash
     */
    public static int fnvHash(byte[] data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (byte b : data) {
            hash = (hash ^ b) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    /**
     * 改进的32位FNV 1
     * @param data 字符串
     * @return hash
     */
    public static int fnvHash(String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash ^ data.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    /**
     * Thomas Wang 整数hash
     * @param key 整数
     * @return hash
     */
    public static int intHash(int key) {
        key += ~(key << 15);
        key ^= (key >>> 10);
        key += (key << 3);
        key ^= (key >>> 6);
        key += ~(key << 11);
        key ^= (key >>> 16);
        return key;
    }

    /**
     * RS hash
     * @param s s
     * @return hash
     */
    public static int rsHash(String s) {
        int b = 378551;
        int a = 63689;
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = hash * a + s.charAt(i);
            a = a * b;
        }
        return hash & 0x7FFFFFFF;
    }

    /**
     * JS hash
     * @param s s
     * @return hash
     */
    public static int jsHash(String s) {
        int hash = 1315423911;
        for (int i = 0; i < s.length(); i++) {
            hash ^= ((hash << 5) + s.charAt(i) + (hash >> 2));
        }
        return hash & 0x7FFFFFFF;
    }

    /**
     * PJW hash
     * @param s s
     * @return hash
     */
    public static int pjwHash(String s) {
        int bitsInUnsignedInt = 32;
        int threeQuarters = (bitsInUnsignedInt * 3) / 4;
        int oneEighth = bitsInUnsignedInt / 8;
        int highBits = 0xFFFFFFFF << (bitsInUnsignedInt - oneEighth);
        int hash = 0;
        int test;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash << oneEighth) + s.charAt(i);
            if ((test = hash & highBits) != 0) {
                hash = ((hash ^ (test >> threeQuarters)) & (~highBits));
            }
        }
        return hash & 0x7FFFFFFF;
    }

    /**
     * ELF hash
     * @param s s
     * @return hash
     */
    public static int elfHash(String s) {
        int hash = 0;
        int x;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash << 4) + s.charAt(i);
            if ((x = (int) (hash & 0xF0000000L)) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }
        return hash & 0x7FFFFFFF;
    }

    /**
     * BKDR hash
     * @param s s
     * @return hash
     */
    public static int bkdrHash(String s) {
        int seed = 131;
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * seed) + s.charAt(i);
        }
        return hash & 0x7FFFFFFF;
    }

    /**
     * SDBM hash
     * @param s s
     * @return hash
     */
    public static int sdbmHash(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = s.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return hash & 0x7FFFFFFF;
    }

    /**
     * DJB hash
     * @param s s
     * @return hash
     */
    public static int djbHash(String s) {
        int hash = 5381;
        for (int i = 0; i < s.length(); i++) {
            hash = ((hash << 5) + hash) + s.charAt(i);
        }
        return hash & 0x7FFFFFFF;
    }

    /**
     * DEK hash
     * @param s s
     * @return hash
     */
    public static int dekHash(String s) {
        int hash = s.length();
        for (int i = 0; i < s.length(); i++) {
            hash = ((hash << 5) ^ (hash >> 27)) ^ s.charAt(i);
        }
        return hash & 0x7FFFFFFF;
    }

    /**
     * AP hash
     * @param s s
     * @return hash
     */
    public static int apHash(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash ^= ((i & 1) == 0) ? ((hash << 7) ^ s.charAt(i) ^ (hash >> 3)) : (~((hash << 11) ^ s.charAt(i) ^ (hash >> 5)));
        }
        // return (hash & 0x7FFFFFFF);
        return hash;
    }

    /**
     * TianL Hash
     * @param s s
     * @return hash
     */
    public static long tianlHash(String s) {
        long hash;
        int iLength = s.length();
        if (iLength == 0) {
            return 0;
        }
        if (iLength <= 256) {
            hash = 16777216L * (iLength - 1);
        } else {
            hash = 4278190080L;
        }
        int i;
        char ucChar;
        if (iLength <= 96) {
            for (i = 1; i <= iLength; i++) {
                ucChar = s.charAt(i - 1);
                if (ucChar <= 'Z' && ucChar >= 'A') {
                    ucChar = (char) (ucChar + 32);
                }
                hash += (3 * i * ucChar * ucChar + 5 * i * ucChar + 7 * i + 11 * ucChar) % 16777216;
            }
        } else {
            for (i = 1; i <= 96; i++) {
                ucChar = s.charAt(i + iLength - 96 - 1);
                if (ucChar <= 'Z' && ucChar >= 'A') {
                    ucChar = (char) (ucChar + 32);
                }
                hash += (3 * i * ucChar * ucChar + 5 * i * ucChar + 7 * i + 11 * ucChar) % 16777216;
            }
        }
        if (hash < 0) {
            hash *= -1;
        }
        return hash;
    }

    /**
     * 混合hash 输出64位的值
     * @param str 字符串
     * @return hash值
     */
    public static long mixHash(String str) {
        long hash = str.hashCode();
        hash <<= 32;
        hash |= fnvHash(str);
        return hash;
    }




}
