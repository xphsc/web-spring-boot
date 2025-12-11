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
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 可转换的 TreeMap
 * @since 2.0.2
 */
public class MutableTreeMap<K, V> extends TreeMap<K, V> implements MutableMap<K, V>, Serializable {

    private static final long serialVersionUID = 89347891239048579L;

    public MutableTreeMap() {
        super();
    }

    public MutableTreeMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    public MutableTreeMap(SortedMap<K, ? extends V> m) {
        super(m);
    }

    public MutableTreeMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public static <K, V> MutableTreeMap<K, V> create() {
        return new MutableTreeMap<>();
    }

    public static <K, V> MutableTreeMap<K, V> create(Map<? extends K, ? extends V> m) {
        return new MutableTreeMap<>(m);
    }

}
