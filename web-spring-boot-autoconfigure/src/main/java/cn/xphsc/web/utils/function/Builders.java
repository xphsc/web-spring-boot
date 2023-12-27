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
package cn.xphsc.web.utils.function;

import cn.xphsc.web.common.lang.function.consumer.Consumer2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: The general builder provided by POJO can also be used as a fluent style setter for POJO
 * @since 1.1.4
 */
public class Builders<T> {

    private final Supplier<T> instanceSupplier;

    private final List<Consumer<T>> modifiers;

    private Builders(Supplier<T> instanceSupplier) {
        this.instanceSupplier = instanceSupplier;
        this.modifiers = new ArrayList<>();
    }

    public static <T> Builders<T> of(Supplier<T> instanceSupplier) {
        return new Builders<>(instanceSupplier);
    }

    public <P1> Builders<T> with(Consumer2<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        modifiers.add(c);
        return this;
    }

    public T build() {
        T instance = instanceSupplier.get();
        modifiers.forEach(modifier -> modifier.accept(instance));
        return instance;
    }

}
