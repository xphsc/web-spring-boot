/*
 * Copyright (c) 2018 huipei.x
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

import cn.xphsc.web.common.collect.Collections;
import java.lang.reflect.Array;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Collects extends Collections{

    public Collects() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        return null == iterable || isEmpty(iterable.iterator());
    }

    public static boolean isEmpty(Iterator<?> iterable) {
        return null == iterable || !iterable.hasNext();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Iterable<?> iterable) {
        return null != iterable && isNotEmpty(iterable.iterator());
    }

    public static boolean isNotEmpty(Iterator<?> iterable) {
        return null != iterable && iterable.hasNext();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
    public static <T> List<T> toList(Enumeration<T> enumeration) {
        return (List)(null == enumeration? java.util.Collections.emptyList(): java.util.Collections.list(enumeration));
    }



    @SafeVarargs
    public static <T> List<T> toList(T... arrays) {
        return ArrayUtils.isEmpty(arrays)? java.util.Collections.emptyList():new ArrayList(Arrays.asList(arrays));
    }


    @SafeVarargs
    public static <T> Collection<T> intersection(Collection<T> coll1, Collection<T> coll2, Collection... otherColls) {
        Collection intersection = intersection(coll1, coll2);
        if(isEmpty(intersection)) {
            return intersection;
        } else {
            Collection[] arr = otherColls;
            int len = otherColls.length;

            for(int i = 0; i < len; ++i) {
                Collection coll = arr[i];
                intersection = intersection(intersection, coll);
                if(isEmpty(intersection)) {
                    return intersection;
                }
            }

            return intersection;
        }
    }



    public static boolean containsAny(Collection<?> coll1, Collection<?> coll2) {
        if(!isEmpty(coll1) && !isEmpty(coll2)) {
            Iterator i;
            Object object;
            if(coll1.size() < coll2.size()) {
                i = coll1.iterator();

                while(i.hasNext()) {
                    object = i.next();
                    if(coll2.contains(object)) {
                        return true;
                    }
                }
            } else {
                i = coll2.iterator();

                while(i.hasNext()) {
                    object = i.next();
                    if(coll1.contains(object)) {
                        return true;
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static <T> Map<T, Integer> countMap(Collection<T> collection) {
        HashMap countMap = new HashMap();
        Iterator i = collection.iterator();

        while(i.hasNext()) {
            Object t = i.next();
            Integer count = (Integer)countMap.get(t);
            if(null == count) {
                countMap.put(t, Integer.valueOf(1));
            } else {
                countMap.put(t, Integer.valueOf(count.intValue() + 1));
            }
        }

        return countMap;
    }

    public static <T> String join(Iterable<T> iterable, String conjunction) {
        return null == iterable?null:join(iterable.iterator(), conjunction);
    }

    public static <T> String join(Iterator<T> iterator, String conjunction) {
        if(null == iterator) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;

            while(iterator.hasNext()) {
                if(isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                Object item = iterator.next();
                if(ArrayUtils.isArray(item)) {
                    sb.append(ArrayUtils.join(ArrayUtils.wrap(item), conjunction));
                } else if(item instanceof Iterable) {
                    sb.append(join((Iterable)item, conjunction));
                } else if(item instanceof Iterator) {
                    sb.append(join((Iterator)item, conjunction));
                } else {
                    sb.append(item);
                }
            }

            return sb.toString();
        }
    }
    public static <T> Collection<T> addAll(Collection<T> collection, Iterator<T> iterator) {
        if(null != collection && null != iterator) {
            while(iterator.hasNext()) {
                collection.add(iterator.next());
            }
        }

        return collection;
    }

    public static <T> Collection<T> addAll(Collection<T> collection, Iterable<T> iterable) {
        return addAll(collection, iterable.iterator());
    }

    public static <T> Collection<T> addAll(Collection<T> collection, Enumeration<T> enumeration) {
        if(null != collection && null != enumeration) {
            while(enumeration.hasMoreElements()) {
                collection.add(enumeration.nextElement());
            }
        }

        return collection;
    }

    public static <T> Collection<T> union(Collection<T> coll1, Collection<T> coll2, Collection... otherColls) {
        Collection union = union(coll1, coll2);
        Collection[] arr = otherColls;
        int len = otherColls.length;

        for(int i = 0; i < len; ++i) {
            Collection coll = arr[i];
            union = union(union, coll);
        }

        return union;
    }


    public static <T> ArrayList<T> newArrayList(T... values) {
        ArrayList arrayList = new ArrayList(values.length);
        Object[] arr = values;
        int len = values.length;

        for(int i = 0; i < len; ++i) {
            Object t = arr[i];
            arrayList.add(t);
        }

        return arrayList;
    }

    public static boolean sizeIsEmpty(Object object) {
        if(object == null) {
            return true;
        } else if(object instanceof Collection) {
            return ((Collection)object).isEmpty();
        } else if(object instanceof Iterable) {
            return isEmpty((Iterable) object);
        } else if(object instanceof Map) {
            return ((Map)object).isEmpty();
        } else if(object instanceof Object[]) {
            return ((Object[]) object).length == 0;
        } else if(object instanceof Iterator) {
            return !((Iterator)object).hasNext();
        } else if(object instanceof Enumeration) {
            return !((Enumeration)object).hasMoreElements();
        } else {
            try {
                return Array.getLength(object) == 0;
            } catch (IllegalArgumentException var2) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

}
