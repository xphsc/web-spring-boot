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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.4
 */
public class Functions {
    /**
     * 在某些遍历的场景，可以通过该方法，在 {@code biFunction} 中拿到遍历的下标
     * <pre>{@code
     *     List(...)
     *      .stream()
     *      .map(Functions.mapWithIndex((index, item) -> {
     *          ...
     *      })
     * }</pre>
     */
    public static <T, R> Function<T, R> mapWithIndex(BiFunction<Integer, T, R> biFunction) {
        AtomicInteger i = new AtomicInteger();
        return t -> biFunction.apply(i.getAndIncrement(), t);
    }

}
