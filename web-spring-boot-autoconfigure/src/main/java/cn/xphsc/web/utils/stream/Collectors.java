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
package cn.xphsc.web.utils.stream;

import cn.xphsc.web.common.lang.function.Function2;
import cn.xphsc.web.common.lang.tuple.Tuple;
import cn.xphsc.web.common.lang.tuple.Tuple2;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Collection Operator
 * @since 1.1.4
 */
public class Collectors {
    public static class Reversed {

        /**
         * 倒序
         *
         * @param <T> Stream里的类型
         */
        public static <T> Collector<T, ?, List<T>> reversed() {
            return reversed(Function.identity());
        }

        /**
         * 倒序，并对list进行 {@code finisher} 转换
         *
         * @param <T> Stream里的类型
         */
        public static <T, R> Collector<T, ?, R> reversed(Function<List<T>, R> finisher) {
            return java.util.stream.Collectors.collectingAndThen(java.util.stream.Collectors.toList(), list -> {
                Collections.reverse(list);
                return finisher.apply(list);
            });
        }
    }

    public static class Distinct {

        /**
         * 去重复，如果数据存在相同的值，则保留最新配置的值
         * 如，before: A B A C   after: B A C
         */
        public static <T> Collector<T, ?, List<T>> distinctLastPut() {
            return distinctByKey(Function.identity(), Order.LAST_PUT);
        }

        public static <T> Collector<T, ?, List<T>> distinctLastPutByKey(Function<? super T, ?> keyExtractor) {
            return distinctByKey(keyExtractor, Order.LAST_PUT);
        }

        /**
         * 去重复，如果数据存在相同的值，则保留最早配置的值
         * 如，before: A B A C  after: A B C
         */
        public static <T> Collector<T, ?, List<T>> distinctFirstPut() {
            return distinctByKey(Function.identity(), Order.FIRST_PUT);
        }

        /**
         * 基于 {@code keyExtractor} 进行去重，只保存第一个值，之后的都忽略。
         */
        public static <T> Collector<T, ?, List<T>> distinctFirstPutByKey(Function<? super T, ?> keyExtractor) {
            return distinctByKey(keyExtractor, Order.FIRST_PUT);
        }

        private static <T> Collector<T, ?, List<T>> distinctByKey(
                Function<? super T, ?> keyExtractor, Order order) {
            return java.util.stream.Collectors.collectingAndThen(
                    java.util.stream.Collectors.toMap(
                            keyExtractor,
                            Function.identity(),
                            (a, b) -> merge(order, a, b),
                            () -> buildMap(order)
                    ),
                    res -> new ArrayList<>(res.values()));
        }

        private static <T> T merge(Order order, T a, T b) {
            switch (order) {
                case FIRST_PUT:
                    return a;
                case LAST_PUT:
                    return b;
                default:
                    throw new IllegalStateException("not support order = " + order);
            }
        }

        private static <K, V> Map<K, V> buildMap(Order order) {
            switch (order) {
                case FIRST_PUT:
                    return new LinkedHashMap<>();
                case LAST_PUT:
                    return new LinkedHashMap<>(16, 0.75f, true);
                default:
                    throw new IllegalStateException("not support order = " + order);
            }
        }

        private enum Order {
            /**
             * key相同的第一个值
             */
            FIRST_PUT,

            /**
             * key相同的最后一个值
             */
            LAST_PUT
        }
    }

    public static <K, U, M extends Map<K, U>>
    Collector<Map.Entry<K, U>, ?, M> toMap(Supplier<M> mapSupplier) {
        return toMap(Map.Entry::getValue, mapSupplier);
    }

    public static <T, K, U, M extends Map<K, T>>
    Collector<Map.Entry<K, U>, ?, M> toMap(
            Function<? super Map.Entry<K, U>, ? extends T> valueMapper, Supplier<M> mapSupplier) {
        return java.util.stream.Collectors.toMap(
                Map.Entry::getKey, valueMapper,
                (u, v) -> {
                    throw new IllegalStateException(String.format("Duplicate key %s", u));
                },
                mapSupplier);
    }

    public static <T1, T2, K, U, M extends Map<K, U>>
    Collector<Tuple2<T1, T2>, ?, M> toMap(
            Function2<? super T1, ? super T2, ? extends K> keyMapper,
            Function2<? super T1, ? super T2, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction,
            Supplier<M> mapSupplier) {
        return java.util.stream.Collectors.toMap(
                t -> keyMapper.apply(t.getT1(), t.getT2()),
                t -> valueMapper.apply(t.getT1(), t.getT2()),
                mergeFunction, mapSupplier);
    }

    public static <T1, T2, K, U, M extends Map<K, U>>
    Collector<Tuple2<T1, T2>, ?, Stream<Map.Entry<K, U>>> toMapEntryStream(
            Function2<? super T1, ? super T2, ? extends K> keyMapper,
            Function2<? super T1, ? super T2, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction,
            Supplier<M> mapSupplier) {
        return toMapEntryStream(t -> keyMapper.apply(t.getT1(), t.getT2()), t -> valueMapper.apply(t.getT1(), t.getT2()),
                mergeFunction, mapSupplier);
    }

    public static <T, K, U, M extends Map<K, U>>
    Collector<T, ?, Stream<Map.Entry<K, U>>> toMapEntryStream(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction,
            Supplier<M> mapSupplier) {
        return java.util.stream.Collectors.collectingAndThen(
                java.util.stream.Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier),
                m -> m.entrySet().stream());
    }

    public static <T1, T2, K, U, M extends Map<K, U>>
    Collector<Tuple2<T1, T2>, ?, Stream<Tuple2<K, U>>> toMapTupleStream(
            Function2<? super T1, ? super T2, ? extends K> keyMapper,
            Function2<? super T1, ? super T2, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction,
            Supplier<M> mapSupplier) {
        return toMapTupleStream(t -> keyMapper.apply(t.getT1(), t.getT2()), t -> valueMapper.apply(t.getT1(), t.getT2()),
                mergeFunction, mapSupplier);
    }

    public static <T, K, U, M extends Map<K, U>>
    Collector<T, ?, Stream<Tuple2<K, U>>> toMapTupleStream(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction,
            Supplier<M> mapSupplier) {
        return java.util.stream.Collectors.collectingAndThen(
                java.util.stream.Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier),
                m -> m.entrySet().stream().map(e -> Tuple.of(e.getKey(), e.getValue())));
    }

}
