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
package cn.xphsc.web.common.lang.mutable;



import java.io.Serializable;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: variable Object
 * @since 1.1.6
 */
public class MutableObject<T> implements Serializable {

    private static final long serialVersionUID = 298530495803846909L;

    private T value;

    public MutableObject() {
    }

    public MutableObject(T value) {
        this.value = value;
    }


    public T get() {
        return this.value;
    }


    public void set(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (this.getClass() == obj.getClass()) {
            MutableObject<?> that = (MutableObject<?>) obj;
            return this.value.equals(that.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.value == null ? 0 : this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.value == null ? "null" : this.value.toString();
    }

}
