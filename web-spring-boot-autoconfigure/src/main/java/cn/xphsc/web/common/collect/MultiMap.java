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

import java.util.*;
import java.util.function.BiConsumer;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  MultiMap接口定义了一个多级映射结构，它继承自Map接口。
 *  该接口表示一个三层映射关系，其中：
 * - E: 第一层键类型
 * - K: 第二层键类型
 * - V: 第二层值类型
 *  - M: 第二层映射类型，必须是Map<K,V>的子类型
 * @since 1.1.6
 */
public interface MultiMap<E, K, V, M extends Map<K, V>> extends Map<E, M> {

    /**
     * 开辟空间
     * @param e e
     * @return map
     */
    M computeSpace(E e);

    default V put(E elem, K key, V value) {
        return this.computeSpace(elem).put(key, value);
    }

    default void putAll(E elem, Map<K, V> map) {
        this.computeSpace(elem).putAll(map);
    }

    default V removeKey(E elem, K key) {
        Map<K, V> r = this.get(elem);
        if (r == null || r.isEmpty()) {
            return null;
        }
        return r.remove(key);
    }

    default boolean removeKey(E elem, K key, V value) {
        Map<K, V> r = this.get(elem);
        if (r == null || r.isEmpty()) {
            return false;
        }
        return r.remove(key, value);
    }

    default V get(E elem, K key) {
        Map<K, V> r = this.get(elem);
        if (r == null || r.isEmpty()) {
            return null;
        }
        return r.get(key);
    }

    default int size(E elem) {
        Map<K, V> r = this.get(elem);
        if (r == null || r.isEmpty()) {
            return 0;
        }
        return r.size();
    }

    default boolean isEmpty(E elem) {
        Map<K, V> r = this.get(elem);
        if (r == null) {
            return true;
        }
        return r.isEmpty();
    }

    default void clear(E elem) {
        Map<K, V> r = this.get(elem);
        if (r != null) {
            r.clear();
        }
    }

    default boolean containsKey(E elem, K key) {
        Map<K, V> r = this.get(elem);
        if (r == null || r.isEmpty()) {
            return false;
        }
        return r.containsKey(key);
    }

    default Collection<V> values(E elem) {
        Map<K, V> r = this.get(elem);
        if (r == null || r.isEmpty()) {
            return new ArrayList<>(1);
        }
        return r.values();
    }

    default Set<Entry<K, V>> entrySet(E elem) {
        Map<K, V> r = this.get(elem);
        if (r == null || r.isEmpty()) {
            return new HashSet<>(1);
        }
        return r.entrySet();
    }

    default void forEach(E elem, BiConsumer<? super K, ? super V> action) {
        Map<K, V> r = this.get(elem);
        if (r == null || r.isEmpty()) {
            return;
        }
        r.forEach(action);
    }

}
