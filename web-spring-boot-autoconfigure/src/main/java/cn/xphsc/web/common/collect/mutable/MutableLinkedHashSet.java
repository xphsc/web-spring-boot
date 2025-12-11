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
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 可转换的 LinkedHashSet
 * @since 2.0.2
 */
public class MutableLinkedHashSet<E> extends LinkedHashSet<E> implements MutableSet<E>, Serializable {

    private static final long serialVersionUID = -822822893457969823L;

    public MutableLinkedHashSet() {
        super();
    }

    public MutableLinkedHashSet(Collection<? extends E> c) {
        super(c);
    }

    public MutableLinkedHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MutableLinkedHashSet(int initialCapacity) {
        super(initialCapacity);
    }

    public static <E> MutableLinkedHashSet<E> create() {
        return new MutableLinkedHashSet<>();
    }

    public static <E> MutableLinkedHashSet<E> create(Collection<? extends E> c) {
        return new MutableLinkedHashSet<>(c);
    }

}
