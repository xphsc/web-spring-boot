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

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Stream;

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
    /**
     * 带索引的 forEach，用于遍历流中的元素并获取索引。
     * <pre>{@code
     *     list.forEach(Functions.forEachWithIndex((index, item) -> {...}));
     * }</pre>
     */
    public static <T> Consumer<T> forEachWithIndex(BiConsumer<Integer, T> biConsumer) {
        AtomicInteger i = new AtomicInteger();
        return t -> biConsumer.accept(i.getAndIncrement(), t);
    }

    /**
     * 带索引的 filter，用于在流中根据索引进行过滤。
     * <pre>{@code
     *     list.stream()
     *         .filter(Functions.filterWithIndex((index, item) -> index % 2 == 0))
     *         .collect(Collectors.toList());
     * }</pre>
     */
    public static <T> Predicate<T> filterWithIndex(BiPredicate<Integer, T> biPredicate) {
        AtomicInteger i = new AtomicInteger();
        return t -> biPredicate.test(i.getAndIncrement(), t);
    }

    /**
     * 带索引的 peek，用于在流中查看元素及其索引，通常用于调试。
     * <pre>{@code
     *     list.stream()
     *         .map(String::toUpperCase)
     *         .map(Functions.peekWithIndex((index, value) -> System.out.println(index + ": " + value)))
     *         .collect(Collectors.toList());
     * }</pre>
     */
    public static <T> Function<T, T> peekWithIndex(BiConsumer<Integer, T> biConsumer) {
        AtomicInteger i = new AtomicInteger();
        return t -> {
            biConsumer.accept(i.getAndIncrement(), t);
            return t;
        };
    }

    /**
     * 将流中的元素与它们的索引一起打包成 Map.Entry 对象。
     * <pre>{@code
     *     Functions.zipWithIndex(list.stream())
     *         .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
     * }</pre>
     */
    public static <T> Stream<Map.Entry<Integer, T>> zipWithIndex(Stream<T> stream) {
        AtomicInteger i = new AtomicInteger();
        return stream.map(t -> new AbstractMap.SimpleEntry<>(i.getAndIncrement(), t));
    }
}
