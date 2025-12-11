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
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Map排序工具
 * @since 2.0.3
 */
public class MapSorts {
    // ====== 排序相关方法 ======

    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKeyAsc(Map<K, V> map) {
        return sortByKey(map, Comparator.naturalOrder());
    }

    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKeyDesc(Map<K, V> map) {
        return sortByKey(map, Comparator.reverseOrder());
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAsc(Map<K, V> map) {
        return sortByValue(map, Comparator.naturalOrder());
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
        return sortByValue(map, Comparator.reverseOrder());
    }

    public static <K, V> Map<K, V> sortByKey(Map<K, V> map, Comparator<K> keyComparator) {
        return sort(map, Map.Entry.comparingByKey(keyComparator));
    }

    public static <K, V> Map<K, V> sortByValue(Map<K, V> map, Comparator<V> valueComparator) {
        return sort(map, Map.Entry.comparingByValue(valueComparator));
    }

    public static <K, V> Map<K, V> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> entryComparator) {
        return sort(map, entryComparator, LinkedHashMap::new);
    }

    public static <K, V, M extends Map<K, V>> M sort(Map<K, V> map,
                                                     Comparator<Map.Entry<K, V>> comparator,
                                                     Supplier<M> mapSupplier) {
        return map.entrySet().stream()
                .sorted(comparator)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        mapSupplier
                ));
    }

    public static <K, V, U extends Comparable<? super U>> Map<K, V> sortByValueKeyExtractor(
            Map<K, V> map, Function<V, U> keyExtractor, boolean ascending) {
        Comparator<Map.Entry<K, V>> comparator = Comparator.comparing(e -> keyExtractor.apply(e.getValue()));
        if (!ascending) comparator = comparator.reversed();
        return sort(map, comparator);
    }

    // ====== 分组方法 ======

    public static <K, V, G> Map<G, List<Map.Entry<K, V>>> groupByValueKeyExtractor(
            Map<K, V> map, Function<V, G> groupKeyExtractor) {
        return map.entrySet().stream()
                .collect(Collectors.groupingBy(
                        entry -> groupKeyExtractor.apply(entry.getValue())
                ));
    }

    public static <K, V, G> Map<G, List<Map.Entry<K, V>>> groupByKeyExtractor(
            Map<K, V> map, Function<K, G> groupKeyExtractor) {
        return map.entrySet().stream()
                .collect(Collectors.groupingBy(
                        entry -> groupKeyExtractor.apply(entry.getKey())
                ));
    }

    // ====== 去重方法 ======

    public static <K, V, U> Map<K, V> distinctByValueKeyExtractor(
            Map<K, V> map, Function<V, U> uniqueKeyExtractor) {
        Set<U> seen = new HashSet<>();
        return map.entrySet().stream()
                .filter(entry -> seen.add(uniqueKeyExtractor.apply(entry.getValue())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static <K, V, U> Map<K, V> distinctByKeyExtractor(
            Map<K, V> map, Function<K, U> uniqueKeyExtractor) {
        Set<U> seen = new HashSet<>();
        return map.entrySet().stream()
                .filter(entry -> seen.add(uniqueKeyExtractor.apply(entry.getKey())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    // ====== 合并方法 ======
    public static <K, V> Map<K, V> mergeByKey(
            Map<K, V> map,
            BinaryOperator<V> mergeFunction) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        mergeFunction,
                        LinkedHashMap::new
                ));
    }

}
