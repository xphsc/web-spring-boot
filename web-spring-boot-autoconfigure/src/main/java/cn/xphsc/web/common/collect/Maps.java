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
package cn.xphsc.web.common.collect;



import cn.xphsc.web.common.validator.Validator;
import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.NumberUtils;
import cn.xphsc.web.utils.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Maps {

    public Maps() {
    }

    public static <K, V> Map<K, V> of(K key, V value) {
        LinkedHashMap map = new LinkedHashMap();
        map.put(key, value);
        return map;
    }

    public static <K, V> Map<K, V> of(K key1, V value1, K key2, V value2) {
        Map map = of(key1, value1);
        map.put(key2, value2);
        return map;
    }



    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }



    public static Map<String, Object> of(Object obj) {
        HashMap map = null;
        if(obj == null) {
            return map;
        } else {
            map = new HashMap();

            try {
                BeanInfo e = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = e.getPropertyDescriptors();
                PropertyDescriptor[] var4 = propertyDescriptors;
                int var5 = propertyDescriptors.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor property = var4[var6];
                    String key = property.getName();
                    if(!"class".equals(key)) {
                        Method getter = property.getReadMethod();
                        map.put(key, getter.invoke(obj, new Object[0]));
                    }
                }
            } catch (Exception var10) {
                map = null;
            }

            return map;
        }
    }

    public static <K> Integer getInteger(Map<? super K, ?> map, K key) {
        Number value = getNumber(map, key);
        return value == null?null:(value instanceof Integer?(Integer)value:Integer.valueOf(value.intValue()));
    }

    public static <K> Double getDouble(Map<? super K, ?> map, K key) {
        Number value = getNumber(map, key);
        return value == null?null:(value instanceof Double?(Double)value:Double.valueOf(value.doubleValue()));
    }

    public static <K> Long getLong(Map<? super K, ?> map, K key) {
        Number value = getNumber(map, key);
        return value == null?null:(value instanceof Long?(Long)value:Long.valueOf(value.longValue()));
    }

    public static <K> Short getShort(Map<? super K, ?> map, K key, Short defaultValue) {
        Short value = getShort(map, key);
        if(value == null) {
            value = defaultValue;
        }

        return value;
    }

    public static <K> Short getShort(Map<? super K, ?> map, K key) {
        Number value = getNumber(map, key);
        return value == null?null:(value instanceof Short?(Short)value:Short.valueOf(value.shortValue()));
    }

    public static <K> Byte getByte(Map<? super K, ?> map, K key) {
        Number value = getNumber(map, key);
        return value == null?null:(value instanceof Byte?(Byte)value:Byte.valueOf(value.byteValue()));
    }

    public static <K> Float getFloat(Map<? super K, ?> map, K key) {
        Number value = getNumber(map, key);
        return value == null?null:(value instanceof Float?(Float)value:Float.valueOf(value.floatValue()));
    }

    public static <K> Float getFloat(Map<? super K, ?> map, K key, Float defaultValue) {
        Float value = getFloat(map, key);
        if(value == null) {
            value = defaultValue;
        }

        return value;
    }

    public static <K> Double getDouble(Map<? super K, ?> map, K key, Double defaultValue) {
        Double value = getDouble(map, key);
        if(value == null) {
            value = defaultValue;
        }

        return value;
    }

    public static <K> Boolean getBoolean(Map<? super K, ?> map, K key) {
        if(map != null) {
            Object value = map.get(key);
            if(value != null) {
                if(value instanceof Boolean) {
                    return (Boolean)value;
                }

                if(value instanceof String) {
                    return Boolean.valueOf((String)value);
                }

                if(value instanceof Number) {
                    Number n = (Number)value;
                    return n.intValue() != 0?Boolean.TRUE:Boolean.FALSE;
                }
            }
        }

        return null;
    }


    public static <K> int getIntValue(Map<? super K, ?> map, K key) {
        Integer integerObject = getInteger(map, key);
        return integerObject == null?0:integerObject.intValue();
    }

    public static <K> boolean getBooleanValue(Map<? super K, ?> map, K key) {
        return Boolean.TRUE.equals(getBoolean(map, key));
    }

    public static <K> long getLongValue(Map<? super K, ?> map, K key) {
        Long longObject = getLong(map, key);
        return longObject == null?0L:longObject.longValue();
    }


    public static <K> byte getByteValue(Map<? super K, ?> map, K key) {
        Byte byteObject = getByte(map, key);
        return byteObject == null?0:byteObject.byteValue();
    }

    public static <K> short getShortValue(Map<? super K, ?> map, K key) {
        Short shortObject = getShort(map, key);
        return shortObject == null?0:shortObject.shortValue();
    }





    public static <K> float getFloatValue(Map<? super K, ?> map, K key) {
        Float floatObject = getFloat(map, key);
        return floatObject == null?0.0F:floatObject.floatValue();
    }

    public static <K> byte getByteValue(Map<? super K, ?> map, K key, byte defaultValue) {
        Byte byteObject = getByte(map, key);
        return byteObject == null?defaultValue:byteObject.byteValue();
    }

    public static <K> short getShortValue(Map<? super K, ?> map, K key, short defaultValue) {
        Short shortObject = getShort(map, key);
        return shortObject == null?defaultValue:shortObject.shortValue();
    }


    public static <K> float getFloatValue(Map<? super K, ?> map, K key, float defaultValue) {
        Float floatObject = getFloat(map, key);
        return floatObject == null?defaultValue:floatObject.floatValue();
    }


    public static <K> Number getNumber(Map<? super K, ?> map, K key) {
        if(map != null) {
            Object value = map.get(key);
            if(value != null) {
                if(value instanceof Number) {
                    return (Number)value;
                }

                if(value instanceof String) {
                    try {
                        String e = (String)value;
                        return NumberFormat.getInstance().parse(e);
                    } catch (ParseException var4) {
                        ;
                    }
                }
            }
        }

        return null;
    }

    public static <K> String getString(Map<? super K, ?> map, K key) {
        Number value = getNumber(map, key);
        return value == null?null:String.valueOf(value);
    }

    public static boolean containsKey(Object key) {
        return key instanceof Number?containsKey(((Number) key).intValue()):false;
    }

    public static boolean containsKey(Map<?, ?> map, String key) {
        return Maps.isNotEmpty(map)&& StringUtils.isNotBlank(key)?containsKey(key):false;
    }




    public static boolean check(Map<String, Boolean> data, String key) {
        boolean success;
        if(!data.containsKey(key)) {
            success = false;
        } else {
            success = ((Boolean)data.get(key)).booleanValue();
        }

        return success;
    }

    public static String linkString(Map<String, String> params) {
        String ret = "";
        ArrayList keys = new ArrayList(params.keySet());
        java.util.Collections.sort(keys);

        for(int i = 0; i < keys.size(); ++i) {
            String key = (String)keys.get(i);
            String value = (String)params.get(key);
            if(!StringUtils.isBlank(value)) {
                if(i == keys.size() - 1) {
                    ret = ret + key + "=" + value;
                } else {
                    ret = ret + key + "=" + value + "&";
                }
            }
        }

        return ret;
    }


    public static <K, V> void putIfValueNotNull(Map<K, V> map, K key, V value) {
        if(null != map && null != value) {
            map.put(key, value);
        }

    }

    public static <K, V> void putAllIfNotNull(Map<K, V> map, Map<? extends K, ? extends V> m) {
        if(null != map && null != m) {
            map.putAll(m);
        }

    }

    public static <K> Map<K, Integer> putSumValue(Map<K, Integer> map, K key, Integer value) {
        Validator.notNull(map, "map can\'t be null!", new Object[0]);
        Validator.notNull(value, "value can\'t be null!", new Object[0]);
        Integer v = (Integer)map.get(key);
        map.put(key, Integer.valueOf(null == v?value.intValue():value.intValue() + v.intValue()));
        return map;
    }


    public static <T, K> HashMap<T, K> newHashMap() {
        return new HashMap();
    }
    public static <K, V> HashMap<K, V> newHashMap(int expectedSize) {
        return new HashMap(toInitialCapacity(expectedSize));
    }
    public static <K, V> HashMap<K, V> newHashMap(int size, boolean isOrder) {
        int initialCapacity = (int)((float)size / 0.75F) + 1;
        return (HashMap)(isOrder ? new LinkedHashMap(initialCapacity) : new HashMap(initialCapacity));
    }


    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int expectedSize) {
        return new LinkedHashMap(toInitialCapacity(expectedSize));
    }
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }
    /**
     * 根据等号左边的类型，构造类型正确的TreeMap.
     *
     */
    @SuppressWarnings("rawtypes")
    public static <K extends Comparable, V> TreeMap<K, V> newSortedMap() {
        return new TreeMap<K, V>();
    }

    /**
     * 根据等号左边的类型，构造类型正确的ConcurrentHashMap.
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<K, V>();
    }

    /**
     * 根据等号左边的类型，构造类型正确的ConcurrentSkipListMap.
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSortedMap() {
        return new ConcurrentSkipListMap<K, V>();
    }
    public static final <K, V> Map<K, V> emptyMap() {
        return java.util.Collections.emptyMap();
    }

    /**
     * 如果map为null，转化为一个安全的空Map.
     *
     * 注意返回的Map不可写, 写入会抛出UnsupportedOperationException.
     *
     * @see java.util.Collections#emptyMap()
     */
    public static <K, V> Map<K, V> emptyMapIfNull(final Map<K, V> map) {
        return map == null ? (Map<K, V>) java.util.Collections.EMPTY_MAP : map;
    }

    private static int toInitialCapacity(int size) {
        Validator.isTrue(size >= 0, "size :[%s] must >=0", (long)size);
        return (int)((float)size / 0.75F) + 1;
    }


    public static <K, V> List<Map<K, V>> getList(Map<K, ? extends Iterable<V>> listMap) {
        ArrayList resultList = new ArrayList();
        if(isEmpty(listMap)) {
            return resultList;
        } else {
            boolean isEnd = true;
            int index = 0;

            do {
                isEnd = true;
                HashMap map = new HashMap();
                Iterator i = listMap.entrySet().iterator();

                while(i.hasNext()) {
                    Map.Entry entry = (Map.Entry)i.next();
                    ArrayList vList = Lists.newArrayList((Iterable)entry.getValue());
                    int vListSize = vList.size();
                    if(index < vListSize) {
                        map.put(entry.getKey(), vList.get(index));
                        if(index != vListSize - 1) {
                            isEnd = false;
                        }
                    }
                }

                if(!map.isEmpty()) {
                    resultList.add(map);
                }

                ++index;
            } while(!isEnd);

            return resultList;
        }
    }
    public static <E extends Map.Entry<K, V>, K, V> Map<K, V> of(E... entries) {
        int len = ArrayUtils.length(entries);
        if (len == 0) {
            return new HashMap<>(16);
        }
        Map<K, V> map = new HashMap<>(getNoCapacitySize(len));
        for (int i = 0; i < len; i++) {
            map.put(entries[i].getKey(), entries[i].getValue());
        }
        return map;
    }

    public static <K, V> Map<K, V> of(K[] keys, V[] values) {
        int klen = ArrayUtils.length(keys);
        int vlen = ArrayUtils.length(values);
        if (klen == 0) {
            return new HashMap<>(16);
        }
        Map<K, V> map = new HashMap<>(getNoCapacitySize(klen));
        for (int i = 0; i < klen; i++) {
            if (vlen > i) {
                map.put(keys[i], values[i]);
            } else {
                map.put(keys[i], null);
            }
        }
        return map;
    }

    public static <K, V> Map<K, V> of(Object... kv) {
        if (kv == null) {
            return new HashMap<>(16);
        }
        int c = kv.length / 2;
        int hn = kv.length % 2;
        Map<K, V> res = new HashMap<>(getNoCapacitySize(hn == 0 ? c : c + 1));
        for (int i = 0; i < c; i++) {
            res.put(((K) kv[i * 2]), ((V) kv[i * 2 + 1]));
        }
        if (hn == 1) {
            res.put(((K) kv[c * 2]), null);
        }
        return res;
    }

    public static <K1, V1, K2, V2> Map<K2, V2> map(Map<K1, V1> map, Function<K1, K2> kf, Function<V1, V2> vf) {
        int size = size(map);
        if (size == 0) {
            return new HashMap<>(16);
        }
        Map<K2, V2> res = new HashMap<>(getNoCapacitySize(size));
        map.forEach((k, v) -> {
            res.put(kf.apply(k), vf.apply(v));
        });
        return res;
    }

    /**
     * 合并map
     * @param map 合并到的map
     * @param ms  需要合并的map
     * @param <K> ignore
     * @param <V> ignore
     * @return 合并后的map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> merge(Map<K, V> map, Map<K, V>... ms) {
        if (map == null) {
            map = new HashMap<>(16);
        }
        if (ms == null) {
            return map;
        }
        for (Map<K, V> m : ms) {
            map.putAll(m);
        }
        return map;
    }
    public static int getNoCapacitySize(int size) {
        if (size == 0) {
            return 16;
        }
        return NumberUtils.getMin2Power(size * 4 / 3);
    }
    /**
     * map长度
     * @param m map
     * @return 长度
     */
    public static int size(Map<?, ?> m) {
        return m == null ? 0 : m.size();
    }

}
