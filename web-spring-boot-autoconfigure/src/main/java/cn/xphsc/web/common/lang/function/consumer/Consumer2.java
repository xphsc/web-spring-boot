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
package cn.xphsc.web.common.lang.function.consumer;

import java.util.function.Consumer;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.4
 */
@FunctionalInterface
public interface Consumer2<T1, T2> {
    void accept(T1 t1, T2 t2);

    static <T1, T2> Consumer2<T1, T2> $1(Consumer<T1> consumer) {
        return (t1, t2) -> consumer.accept(t1);
    }

    static <T1, T2> Consumer2<T1, T2> $2(Consumer<T2> consumer) {
        return (t1, t2) -> consumer.accept(t2);
    }
}
