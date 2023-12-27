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
package cn.xphsc.web.common.lang.thread;

import java.util.Objects;
import java.util.function.Supplier;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: ThreadLocal
 * @since 1.1.7
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

    private final String name;

    public NamedThreadLocal(String name) {
        this.name = name;
    }

    public static <S> NamedThreadLocal<S> withInitial(String name, Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<>(name, supplier);
    }

    @Override
    public String toString() {
        return this.name;
    }

    static final class SuppliedThreadLocal<T> extends NamedThreadLocal<T> {

        private final Supplier<? extends T> supplier;

        SuppliedThreadLocal(String name, Supplier<? extends T> supplier) {
            super(name);
            this.supplier = Objects.requireNonNull(supplier);
        }

        @Override
        protected T initialValue() {
            return supplier.get();
        }
    }

}
