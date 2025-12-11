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
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 可转换的 TreeSet
 * @since 2.0.2
 */
public class MutableTreeSet<E> extends TreeSet<E> implements MutableSet<E>, Serializable {

    private static final long serialVersionUID = -9345897822739429L;

    public MutableTreeSet() {
        super();
    }

    public MutableTreeSet(Comparator<? super E> comparator) {
        super(comparator);
    }

    public MutableTreeSet(Collection<? extends E> c) {
        super(c);
    }

    public MutableTreeSet(SortedSet<E> s) {
        super(s);
    }

    public static <E> MutableTreeSet<E> create() {
        return new MutableTreeSet<>();
    }

    public static <E> MutableTreeSet<E> create(Comparator<? super E> comparator) {
        return new MutableTreeSet<>(comparator);
    }

    public static <E> MutableTreeSet<E> create(Collection<? extends E> c) {
        return new MutableTreeSet<>(c);
    }

}
