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
package cn.xphsc.web.common.collect.iterator;

import java.io.Serializable;
import java.util.Iterator;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.6
 */
public class ClassIterator<T> implements Iterator<Class<? super T>>, Iterable<Class<? super T>>, Serializable {
    private final Class<T> clazz;
    private Class<? super T> current;
    private final boolean includeObject;

    public ClassIterator(Class<T> clazz) {
        this(clazz, false);
    }

    public ClassIterator(Class<T> clazz, boolean includeObject) {
        this.current = this.clazz = clazz;
        this.includeObject = includeObject;
    }

    public boolean hasNext() {
        if (this.clazz.getName().contains("$$")) {
            return false;
        } else {
            this.current = this.current.getSuperclass();
            if (this.current == null) {
                return false;
            } else {
                return this.includeObject || !Object.class.equals(this.current);
            }
        }
    }

    public Class<? super T> next() {
        return this.current;
    }

    public Iterator<Class<? super T>> iterator() {
        return this;
    }
}
