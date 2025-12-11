/*
 * Copyright (c) 2025 huipei.x
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
package cn.xphsc.web.common.collect;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 集合排序工具
 * * For reference, the example
 * Sorts.Builder.<T>of()....applyTo(list);
 * @since 2.0.3
 */
public class Sorts {
    /**
     * 通用链式排序构建器。
     * 使用方式：Sorts.Builder.of()....applyTo(list);
     */
    public static class Builder<T> {
        private final List<Comparator<T>> comparators = new ArrayList<>();
        private Predicate<T> conditionalFirst;
        private boolean distinct;  // 去重标志
        private Function<T, ?> groupByFunction;  // 用于分组的字段

        private Builder() {
        }

        public static <T> Builder<T> of() {
            return new Builder<>();
        }

        /**
         * 条件优先排序（满足条件的排前面）
         */
        public Builder<T> conditionalFirst(Predicate<T> condition) {
            this.conditionalFirst = condition;
            return this;
        }

        /**
         * Comparable 字段升序
         */
        public Builder<T> thenAsc(Function<T, ? extends Comparable> keyExtractor) {
            comparators.add(Comparator.comparing(keyExtractor, Comparator.nullsFirst(Comparator.naturalOrder())));
            return this;
        }

        /**
         * Comparable 字段降序
         */
        public Builder<T> thenDesc(Function<T, ? extends Comparable> keyExtractor) {
            comparators.add(Comparator.comparing(keyExtractor, Comparator.nullsLast(Comparator.reverseOrder())));
            return this;
        }

        /**
         * 忽略大小写的字符串升序
         */
        public Builder<T> thenStringAscIgnoreCase(Function<T, String> keyExtractor) {
            comparators.add(Comparator.comparing(keyExtractor, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)));
            return this;
        }

        /**
         * 忽略大小写的字符串降序
         */
        public Builder<T> thenStringDescIgnoreCase(Function<T, String> keyExtractor) {
            comparators.add(Comparator.comparing(keyExtractor, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER.reversed())));
            return this;
        }

        /**
         * int 字段升序
         */
        public Builder<T> thenIntAsc(ToIntFunction<T> keyExtractor) {
            comparators.add(Comparator.comparingInt(keyExtractor));
            return this;
        }

        /**
         * int 字段降序
         */
        public Builder<T> thenIntDesc(ToIntFunction<T> keyExtractor) {
            comparators.add((a, b) -> Integer.compare(keyExtractor.applyAsInt(b), keyExtractor.applyAsInt(a)));
            return this;
        }

        /**
         * long 字段升序
         */
        public Builder<T> thenLongAsc(ToLongFunction<T> keyExtractor) {
            comparators.add(Comparator.comparingLong(keyExtractor));
            return this;
        }

        /**
         * long 字段降序
         */
        public Builder<T> thenLongDesc(ToLongFunction<T> keyExtractor) {
            comparators.add((a, b) -> Long.compare(keyExtractor.applyAsLong(b), keyExtractor.applyAsLong(a)));
            return this;
        }

        /**
         * 自定义排序器
         */
        public Builder<T> thenCustom(Comparator<T> customComparator) {
            comparators.add(customComparator);
            return this;
        }

        public Builder<T> distinct() {
            this.distinct = true;
            return this;
        }
        public Builder<T> groupBy(Function<T, ?> groupByFunction) {
            this.groupByFunction = groupByFunction;
            return this;
        }
        public Comparator<T> build() {
            if (comparators.isEmpty()) {
                throw new IllegalStateException("未指定任何排序字段");
            }

            Comparator<T> result = comparators.get(0);
            for (int i = 1; i < comparators.size(); i++) {
                result = result.thenComparing(comparators.get(i));
            }
            if (conditionalFirst != null) {
                result = Comparator.comparing((T t) -> conditionalFirst.test(t) ? 0 : 1)
                        .thenComparing(result);
            }
            return result;
        }


        public List<T> applyTo(List<T> list) {
            if (list == null) {
                throw new IllegalArgumentException("列表不能为 null");
            }

            Comparator<T> comparator = build();

            Stream<T> stream = list.stream();

            // 先按 groupBy 处理
            if (groupByFunction != null) {
                Map<Object, List<T>> grouped = stream.collect(Collectors.groupingBy(groupByFunction));
                stream = grouped.values().stream()
                        .flatMap(group -> {
                            group.sort(comparator);
                            return group.stream();
                        });
            } else {
                stream = stream.sorted(comparator);
            }

            if (distinct) {
                // 使用 LinkedHashSet 保证顺序 & 去重
                return stream.collect(Collectors.toCollection(() -> new LinkedHashSet<>()))
                        .stream().collect(Collectors.toList());
            } else {
                return stream.collect(Collectors.toList());
            }
        }
    }


}
