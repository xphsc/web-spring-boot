/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.common.collect.mutable;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 可转换的 LinkedHashMap
 * @since 2.0.2
 */
public class MutableLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements MutableMap<K, V>, Serializable {

    private static final long serialVersionUID = 876812409459012839L;

    public MutableLinkedHashMap() {
        super();
    }

    public MutableLinkedHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public MutableLinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MutableLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    public MutableLinkedHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public static <K, V> MutableLinkedHashMap<K, V> create() {
        return new MutableLinkedHashMap<>();
    }

    public static <K, V> MutableLinkedHashMap<K, V> create(Map<? extends K, ? extends V> m) {
        return new MutableLinkedHashMap<>(m);
    }

}
