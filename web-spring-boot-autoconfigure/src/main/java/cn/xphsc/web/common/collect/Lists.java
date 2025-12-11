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
import cn.xphsc.web.utils.RandomUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Lists {

    private Lists() {
    }

    public static <T> ArrayList<T> newArrayList(Collection<T> collection) {
        return new ArrayList(collection);
    }
    @SafeVarargs
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
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> ArrayList<T> newArrayList(int initSize) {
        return new ArrayList<T>(initSize);
    }

    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<T>();
    }

    public static <T> LinkedList<T> newLinkedList(Iterable<? extends T> elements) {
        return Lists.newLinkedList(elements);

    }
    public static <E> LinkedList<E> newLinkedList(Collection<? extends E> c) {
        if (c == null) {
            return new LinkedList<>();
        }
        return new LinkedList<>(c);
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
    @SafeVarargs
    public static <E> List<E> of(E... e) {
        return new ArrayList<>(Arrays.asList(e));
    }

    @SafeVarargs
    public static <E, V> List<E> of(Function<V, E> f, V... e) {
        List<E> list = new ArrayList<>();
        int length = ArrayUtils.length(e);
        for (int i = 0; i < length; i++) {
            list.add(f.apply(e[i]));
        }
        return list;
    }
    public static <T> List<T> toList(Collection<T> collection) {
        return (List)(null == collection? java.util.Collections.emptyList():(collection instanceof List?(List)collection:new ArrayList(collection)));
    }
    public static <T> List<T> toList(Enumeration<T> enumeration) {
        return (List)(null == enumeration? java.util.Collections.emptyList(): java.util.Collections.list(enumeration));
    }
    @SafeVarargs
    public static <T> List<T> toList(T... arrays) {
        return (List)(Validator.isNullOrEmpty(arrays)? java.util.Collections.emptyList():new ArrayList(Arrays.asList(arrays)));
    }

    /**
     * 如果list为null，转化为一个安全的空List.
     * 注意返回的List不可写, 写入会抛出UnsupportedOperationException.
     */
    public static <T> List<T> emptyListIfNull(final List<T> list) {
        return list == null ? (List<T>) java.util.Collections.EMPTY_LIST : list;
    }

    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null? (List<T>) java.util.Collections.emptyList() :list;
    }

    public static <T> List<T> defaultIfNull(List<T> list, List<T> defaultList) {
        return list == null?defaultList:list;
    }

    public static <E> List<E> intersection(List<? extends E> list1, List<? extends E> list2) {
        ArrayList result = new ArrayList();
        List smaller = list1;
        List larger = list2;
        if(list1.size() > list2.size()) {
            smaller = list2;
            larger = list1;
        }

        HashSet hashSet = new HashSet(smaller);
        Iterator i = larger.iterator();

        while(i.hasNext()) {
            Object e = i.next();
            if(hashSet.contains(e)) {
                result.add(e);
                hashSet.remove(e);
            }
        }

        return result;
    }


    public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<T>();
    }

    /**
     * 根据等号左边的类型，构造类型转换的CopyOnWriteArrayList, 并初始化元素.
     */
    public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList(T... elements) {
        return new CopyOnWriteArrayList<T>(elements);
    }

    /**
     * 获取第一个元素, 如果List为空返回 null.
     */
    public static <T> T getFirst(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取最后一个元素，如果List为空返回null.
     */
    public static <T> T getLast(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }

        return list.get(list.size() - 1);
    }
    public static <E> List<E> union(List<? extends E> list1, List<? extends E> list2) {
        ArrayList result = new ArrayList(list1);
        result.addAll(list2);
        return result;
    }

    /**
     * 从list随机获取一个元素
     * @param list list
     * @param <E>  ignore
     * @return 元素
     */
    public static <E> E random(List<E> list) {
        int size = Collections.size(list);
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return list.get(0);
        } else {
            return list.get(RandomUtils.getRandom().nextInt(size));
        }
    }
    /**
     * 随机打乱list
     */
    public static <E> void shuffle(List<E> list) {
        if (isEmpty(list) || list.size() == 1) {
            return;
        }
        java.util.Collections.shuffle(list);
    }
    /**
     * 翻转集合
     * @param list 集合
     * @param <E>  ignore
     */
    public static <E> void reverse(List<E> list) {
        int size = Collections.size(list);
        if (size == 0) {
            return;
        }
        for (int i = 0, mid = size / 2; i < mid; i++) {
            int len = size - i - 1;
            if (list.get(i) != list.get(len)) {
                list.set(len, list.set(i, list.get(len)));
            }
        }
    }

    public static boolean isEqualList(Collection<?> list1, Collection<?> list2) {
        if(list1 == list2) {
            return true;
        } else if(isNotEmpty(list1) &&isNotEmpty(list2) && list1.size() == list2.size()) {
            Iterator it1 = list1.iterator();
            Iterator it2 = list2.iterator();
            Object obj1 = null;
            Object obj2 = null;

            while(true) {
                if(it1.hasNext() && it2.hasNext()) {
                    obj1 = it1.next();
                    obj2 = it2.next();
                    if(obj1 == null) {
                        if(obj2 == null) {
                            continue;
                        }
                    } else if(obj1.equals(obj2)) {
                        continue;
                    }

                    return false;
                }

                return !it1.hasNext() && !it2.hasNext();
            }
        } else {
            return false;
        }
    }

    public static int hashCodeForList(Collection<?> list) {
        if(list == null) {
            return 0;
        } else {
            int hashCode = 1;

            Object obj;
            for(Iterator it = list.iterator(); it.hasNext(); hashCode = 31 * hashCode + (obj == null?0:obj.hashCode())) {
                obj = it.next();
            }

            return hashCode;
        }
    }

    public static <E> List<E> retainAll(Collection<E> collection, Collection<?> retain) {
        ArrayList list = new ArrayList(Math.min(collection.size(), retain.size()));
        Iterator i = collection.iterator();

        while(i.hasNext()) {
            Object obj = i.next();
            if(retain.contains(obj)) {
                list.add(obj);
            }
        }

        return list;
    }

    public static <E> List<E> removeAll(Collection<E> collection, Collection<?> remove) {
        ArrayList list = new ArrayList();
        Iterator i = collection.iterator();

        while(i.hasNext()) {
            Object obj = i.next();
            if(!remove.contains(obj)) {
                list.add(obj);
            }
        }

        return list;
    }

    public static <E> List<E> synchronizedList(List<E> list) {
        return java.util.Collections.synchronizedList(list);
    }


    public static <T> List<List<T>> partition(List<T> list, int size) {
        if(list == null) {
            throw new NullPointerException("List must not be null");
        } else if(size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        } else {
            return new Lists.Partition(list, size);
        }
    }
    public static <E> List<E> as(Iterator<E> iterator) {
        List<E> list = new ArrayList<>();
        if (iterator != null) {
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
        }
        return list;
    }

    public static <E> List<E> as(Enumeration<E> iterator) {
        List<E> list = new ArrayList<>();
        if (iterator != null) {
            while (iterator.hasMoreElements()) {
                list.add(iterator.nextElement());
            }
        }
        return list;
    }

    public static <T> boolean anyMatch(List<T> collection, Predicate<T> predicate) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        return collection.stream().anyMatch(predicate);
    }

    public static <T> boolean allMatch(List<T> collection, Predicate<T> predicate) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        return collection.stream().allMatch(predicate);
    }

    private static class Partition<T> extends AbstractList<List<T>> {
        private final List<T> list;
        private final int size;

        private Partition(List<T> list, int size) {
            this.list = list;
            this.size = size;
        }

        @Override
        public List<T> get(int index) {
            int listSize = this.size();
            if(listSize < 0) {
                throw new IllegalArgumentException("negative size: " + listSize);
            } else if(index < 0) {
                throw new IndexOutOfBoundsException("Index " + index + " must not be negative");
            } else if(index >= listSize) {
                throw new IndexOutOfBoundsException("Index " + index + " must be less than size " + listSize);
            } else {
                int start = index * this.size;
                int end = Math.min(start + this.size, this.list.size());
                return this.list.subList(start, end);
            }
        }



        @Override
        public int size() {
            return (this.list.size() + this.size - 1) / this.size;
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }
    }

    private static final class CharSequenceAsList extends AbstractList<Character> {
        private final CharSequence sequence;

        public CharSequenceAsList(CharSequence sequence) {
            this.sequence = sequence;
        }

        @Override
        public Character get(int index) {
            return Character.valueOf(this.sequence.charAt(index));
        }

        @Override
        public int size() {
            return this.sequence.length();
        }
    }




}
