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




import cn.xphsc.web.common.collect.concurrent.ConcurrentHashSet;
import cn.xphsc.web.common.validator.Validator;
import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.RandomUtils;

import java.util.*;
import java.util.function.Function;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Sets {

    public static <T> HashSet<T> newHashSet(Collection<T> collection) {
        return new HashSet(collection);
    }
    public static <T> HashSet<T> newHashSet() {
        return new HashSet();
    }

    public static <T> HashSet<T> newHashSet(boolean isSorted, Collection<T> collection) {
        return (HashSet)(isSorted?new LinkedHashSet():new HashSet(collection));
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... ts) {
        HashSet set = new HashSet(Math.max((int)((float)ts.length / 0.75F) + 1, 16));
        Object[] arr = ts;
        int len = ts.length;

        for(int i = 0; i < len; ++i) {
            Object t = arr[i];
            set.add(t);
        }

        return set;
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(boolean isSorted, T... ts) {
        int initialCapacity = Math.max((int)((float)ts.length / 0.75F) + 1, 16);
        Object set = isSorted?new LinkedHashSet(initialCapacity):new HashSet(initialCapacity);
        Object[] arr = ts;
        int len = ts.length;

        for(int i = 0; i < len; ++i) {
            Object t = arr[i];
            ((HashSet)set).add(t);
        }

        return (HashSet)set;
    }
    public static <E> TreeSet<E> newTreeSet(Collection<? extends E> c) {
        if (c == null) {
            return new TreeSet<>();
        }
        return new TreeSet<>(c);
    }

    public static <E> Set<E> newSynchronizedSet() {
        return java.util.Collections.synchronizedSet(new HashSet<>());
    }

    public static <E> Set<E> newSynchronizedSet(Set<E> set) {
        if (set == null) {
            return java.util.Collections.synchronizedSet(new HashSet<>());
        }
        return java.util.Collections.synchronizedSet(set);
    }

    public static <E> SortedSet<E> newSynchronizedSortedSet() {
        return java.util.Collections.synchronizedSortedSet(new TreeSet<>());
    }

    public static <E> SortedSet<E> newSynchronizedSortedSet(SortedSet<E> set) {
        if (set == null) {
            return java.util.Collections.synchronizedSortedSet(new TreeSet<>());
        }
        return java.util.Collections.synchronizedSortedSet(set);
    }

    public static BitSet newBitSet() {
        return new BitSet();
    }

    public static BitSet newBitSet(int initialSize) {
        return new BitSet(initialSize);
    }

    public static <E> ConcurrentHashSet<E> newCurrentSet() {
        return new ConcurrentHashSet<>();
    }



    @SafeVarargs
    public static <E> Set<E> of(E... e) {
        return new HashSet<>(Arrays.asList(e));
    }

    @SafeVarargs
    public static <E, V> Set<E> of(Function<V, E> f, V... e) {
        Set<E> list = new HashSet<>();
        int length = ArrayUtils.length(e);
        for (int i = 0; i < length; i++) {
            list.add(f.apply(e[i]));
        }
        return list;
    }

    public static <E> Set<E> of(Iterator<E> iterator) {
        Set<E> list = new LinkedHashSet<>();
        if (iterator != null) {
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
        }
        return list;
    }

    public static <E> Set<E> of(Enumeration<E> iterator) {
        Set<E> list = new LinkedHashSet<>();
        if (iterator != null) {
            while (iterator.hasMoreElements()) {
                list.add(iterator.nextElement());
            }
        }
        return list;
    }

    public static <E, V> Set<E> map(List<V> l, Function<V, E> f) {
        Set<E> set = new HashSet<>();
        if (Collections.isEmpty(l)) {
            return set;
        }
        for (V v : l) {
            set.add(f.apply(v));
        }
        return set;
    }



    /**
     * 根据等号左边的类型，构造类型正确的TreeSet, 通过实现了Comparable的元素自身进行排序.
     * @param <T>
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T extends Comparable> TreeSet<T> newSortedSet() {
        return new TreeSet<T>();
    }



    /**
     * 根据等号左边的类型，构造类型正确的ConcurrentHashSet
     */
    public static <T> ConcurrentHashSet<T> newConcurrentHashSet() {
        return new ConcurrentHashSet<T>();
    }

    ///////////////// from JDK Collections的常用构造函数 ///////////////////

    /**
     * 返回一个空的结构特殊的Set，节约空间.
     * 注意返回的Set不可写, 写入会抛出UnsupportedOperationException.
     * @see java.util.Collections#emptySet()
     */
    public static final <T> Set<T> emptySet() {
        return java.util.Collections.emptySet();
    }

    /**
     * 如果set为null，转化为一个安全的空Set.
     * 注意返回的Set不可写, 写入会抛出UnsupportedOperationException.
     * @see java.util.Collections#emptySet()
     */
    public static <T> Set<T> emptySetIfNull(final Set<T> set) {
        return set == null ? (Set<T>) java.util.Collections.EMPTY_SET : set;
    }

    /**
     * 返回只含一个元素但结构特殊的Set，节约空间.
     * 注意返回的Set不可写, 写入会抛出UnsupportedOperationException.
     * @see java.util.Collections#singleton(Object)
     */
    public static final <T> Set<T> singletonSet(T o) {
        return java.util.Collections.singleton(o);

    }


    /**
     * 返回包装后不可修改的Set
     * 如果尝试修改，会抛出UnsupportedOperationException
     * @param s
     * @param <T>
     * @return
     */
    public static <T> Set<T> unmodifiableSet(Set<? extends T> s) {
        return java.util.Collections.unmodifiableSet(s);
    }


    /**
     * 从Map构造Set的大杀器, 可以用来制造各种Set
     * @param map
     * @param <T>
     * @return
     */
    public static <T> Set<T> newSetFromMap(Map<T, Boolean> map) {
        return java.util.Collections.newSetFromMap(map);
    }

    @SafeVarargs
    public static <T> Set<T> toSet(T... arrays) {
        return (Set)(Validator.isNullOrEmpty(arrays)? java.util.Collections.emptySet():new LinkedHashSet(Arrays.asList(arrays)));
    }
    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return set == null? (Set<T>) java.util.Collections.emptySet() :set;
    }
    public static boolean isEqualSet(Collection<?> set1, Collection<?> set2) {
        return set1 == set2?true:(set1 != null && set2 != null && set1.size() == set2.size()?set1.containsAll(set2):false);
    }

    /**
     * 合并set
     * @param set 合并到的set
     * @param ms  需要合并的set
     * @param <E> ignore
     * @return 合并后的set
     */
    @SafeVarargs
    public static <E> Set<E> merge(Set<E> set, Set<E>... ms) {
        if (set == null) {
            set = new HashSet<>();
        }
        if (ms == null) {
            return set;
        }
        for (Set<E> m : ms) {
            if (m != null) {
                set.addAll(m);
            }
        }
        return set;
    }

    /**
     * 从set获取元素
     *
     * @param set set
     * @param i   index
     * @param <E> ignore
     * @return 元素
     */
    public static <E> E get(Set<E> set, int i) {
        if (Collections.size(set) <= i) {
            return null;
        }
        int idx = 0;
        for (E e : set) {
            if (idx++ == i) {
                return e;
            }
        }
        return null;
    }

    /**
     * 从set随机获取一个元素
     *
     * @param set set
     * @param <E> ignore
     * @return 元素
     */
    public static <E> E random(Set<E> set) {
        int size = Collections.size(set);
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return set.iterator().next();
        } else {
            return get(set, RandomUtils.getRandom().nextInt(size));
        }
    }

    /**
     * 删除元素到指定大小
     *
     * @param set  set
     * @param size 大小
     * @param <E>  E
     */
    public static <E> void removeToSize(Set<E> set, int size) {
        int oldSize = Collections.size(set);
        if (size >= oldSize) {
            return;
        }
        Iterator<E> iterator = set.iterator();
        int d = oldSize - size;
        for (int i = 0; i < size; i++) {
            iterator.next();
        }
        for (int i = 0; i < d; i++) {
            iterator.next();
            iterator.remove();
        }
    }
}
