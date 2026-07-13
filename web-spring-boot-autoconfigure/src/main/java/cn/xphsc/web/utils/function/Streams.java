package cn.xphsc.web.utils.function;

import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.Collects;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * {@link Stream}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Streams类提供了对数据流进行各种操作的工具方法集合。
 * 该类包含了创建、转换和处理数据流的静态方法，用于简化集合数据的批量处理操作
 * @since 2.0.3
 */
public class Streams {

	public static <T> Stream<T> of(Iterable<T> iterable) {
		if (iterable == null) {
			return Stream.empty();
		}
		if (iterable instanceof Collection) {
			return ((Collection<T>) iterable).stream();
		}
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	public static <T> Stream<T> of(Collection<T> collection) {
		if (collection == null) {
			return Stream.empty();
		}
		return collection.stream();
	}

	public static <T> Stream<T> of(T[] array) {
		if (array == null) {
			return Stream.empty();
		}
		return Arrays.stream(array);
	}


	public static <T> List<T> filter(Stream<T> from, Predicate<T> predicate) {
		if (from == null) {
			return new ArrayList<>();
		}
		return from.filter(predicate).collect(Collectors.toList());
	}

	public static <T> List<T> filter(Iterable<T> from, Predicate<T> predicate) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return filter(of(from), predicate);
	}

	public static <T> List<T> filter(T[] from, Predicate<T> predicate) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyList();
		}
		return filter(of(from), predicate);
	}

	public static <T> List<T> filter(Collection<T> from, Predicate<T> predicate) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return filter(of(from), predicate);
	}


	public static <T, U> List<U> toList(Stream<T> from, Function<T, U> func) {
		if (from == null) {
			return Collections.emptyList();
		}
		return from.map(func)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	public static <T, U> List<U> toList(Iterable<T> from, Function<T, U> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return toList(of(from), func);
	}

	public static <T, U> List<U> toList(T[] from, Function<T, U> func) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyList();
		}
		return toList(of(from), func);
	}

	public static <T, U> List<U> toList(Collection<T> from, Function<T, U> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return toList(of(from), func);
	}


	public static <T, U> List<U> toList(Stream<T> from, Function<T, U> func, Predicate<T> filter) {
		if (from == null) {
			return Collections.emptyList();
		}
		return from
			.filter(filter)
			.map(func)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	public static <T, U> List<U> toList(Iterable<T> from, Function<T, U> func, Predicate<T> filter) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return toList(of(from), func, filter);
	}

	public static <T, U> List<U> toList(T[] from, Function<T, U> func, Predicate<T> filter) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyList();
		}
		return toList(of(from), func, filter);
	}

	public static <T, U> List<U> toList(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return toList(of(from), func, filter);
	}


	public static <T, U> List<U> flatMap(Stream<T> from,
		Function<T, ? extends Stream<? extends U>> func) {
		if (from == null) {
			return Collections.emptyList();
		}
		return from
			.filter(Objects::nonNull)
			.flatMap(func)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	public static <T, U> List<U> flatMap(Iterable<T> from,
		Function<T, ? extends Stream<? extends U>> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return flatMap(of(from), func);
	}

	public static <T, U> List<U> flatMap(T[] from,
		Function<T, ? extends Stream<? extends U>> func) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyList();
		}
		return flatMap(of(from), func);
	}

	public static <T, U> List<U> flatMap(Collection<T> from,
		Function<T, ? extends Stream<? extends U>> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return flatMap(of(from), func);
	}


	public static <T, U, R> List<R> flatMap(Stream<T> from,
		Function<? super T, ? extends U> mapper,
		Function<U, ? extends Stream<? extends R>> func) {
		if (from == null) {
			return Collections.emptyList();
		}
		return from
			.map(mapper)
			.filter(Objects::nonNull)
			.flatMap(func)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	public static <T, U, R> List<R> flatMap(Iterable<T> from,
		Function<? super T, ? extends U> mapper,
		Function<U, ? extends Stream<? extends R>> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return flatMap(of(from), mapper, func);
	}

	public static <T, U, R> List<R> flatMap(T[] from,
		Function<? super T, ? extends U> mapper,
		Function<U, ? extends Stream<? extends R>> func) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyList();
		}
		return flatMap(of(from), mapper, func);
	}

	public static <T, U, R> List<R> flatMap(Collection<T> from,
		Function<? super T, ? extends U> mapper,
		Function<U, ? extends Stream<? extends R>> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return flatMap(of(from), mapper, func);
	}


	public static <K, V> List<V> mergeValuesByMap(Map<K, List<V>> map) {
		return map.values()
			.stream()
			.flatMap(List::stream)
			.collect(Collectors.toList());
	}


	public static <T, U> Set<U> toSet(Stream<T> from, Function<T, U> func) {
		if (from == null) {
			return Collections.emptySet();
		}
		return from
			.map(func)
			.filter(Objects::nonNull)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public static <T, U> Set<U> toSet(Iterable<T> from, Function<T, U> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptySet();
		}
		return toSet(of(from), func);
	}

	public static <T, U> Set<U> toSet(T[] from, Function<T, U> func) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptySet();
		}
		return toSet(of(from), func);
	}

	public static <T, U> Set<U> toSet(Collection<T> from, Function<T, U> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptySet();
		}
		return toSet(of(from), func);
	}

	public static <T> Set<T> toSet(Stream<T> from) {
		return toSet(from, Function.identity());
	}

	public static <T> Set<T> toSet(Iterable<T> from) {
		return toSet(from, Function.identity());
	}

	public static <T> Set<T> toSet(T[] from) {
		return toSet(from, Function.identity());
	}

	public static <T> Set<T> toSet(Collection<T> from) {
		return toSet(from, Function.identity());
	}


	public static <T, U> Set<U> toSet(Stream<T> from, Function<T, U> func, Predicate<T> filter) {
		if (from == null) {
			return Collections.emptySet();
		}
		return from
			.filter(filter)
			.map(func)
			.filter(Objects::nonNull)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public static <T, U> Set<U> toSet(Iterable<T> from, Function<T, U> func, Predicate<T> filter) {
		if (Collects.isEmpty(from)) {
			return Collections.emptySet();
		}
		return toSet(of(from), func, filter);
	}

	public static <T, U> Set<U> toSet(T[] from, Function<T, U> func, Predicate<T> filter) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptySet();
		}
		return toSet(of(from), func, filter);
	}

	public static <T, U> Set<U> toSet(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
		if (Collects.isEmpty(from)) {
			return Collections.emptySet();
		}
		return toSet(of(from), func, filter);
	}


	public static <T, K> Map<K, T> filter(Stream<T> from, Predicate<T> filter, Function<T, K> keyFunc) {
		if (from == null) {
			return Collections.emptyMap();
		}
		return from
			.filter(filter)
			.collect(Collectors.toMap(keyFunc, v -> v,(t1, t2) -> t1, LinkedHashMap::new));
	}

	public static <T, K> Map<K, T> filter(Iterable<T> from, Predicate<T> filter, Function<T, K> keyFunc) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return filter(of(from), filter, keyFunc);
	}

	public static <T, K> Map<K, T> filter(T[] from, Predicate<T> filter, Function<T, K> keyFunc) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return filter(of(from), filter, keyFunc);
	}

	public static <T, K> Map<K, T> filter(Collection<T> from, Predicate<T> filter, Function<T, K> keyFunc) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return filter(of(from), filter, keyFunc);
	}


	public static <T, U> Set<U> setByFlatMap(Stream<T> from,
		Function<T, ? extends Stream<? extends U>> func) {
		if (from == null) {
			return Collections.emptySet();
		}
		return from
			.filter(Objects::nonNull)
			.flatMap(func)
			.filter(Objects::nonNull)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public static <T, U> Set<U> setByFlatMap(Iterable<T> from,
		Function<T, ? extends Stream<? extends U>> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptySet();
		}
		return setByFlatMap(of(from), func);
	}

	public static <T, U> Set<U> setByFlatMap(T[] from,
		Function<T, ? extends Stream<? extends U>> func) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptySet();
		}
		return setByFlatMap(of(from), func);
	}

	public static <T, U> Set<U> setByFlatMap(Collection<T> from,
		Function<T, ? extends Stream<? extends U>> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptySet();
		}
		return setByFlatMap(of(from), func);
	}


	public static <T, U, R> Set<R> setByFlatMap(Stream<T> from,
		Function<? super T, ? extends U> mapper,
		Function<U, ? extends Stream<? extends R>> func) {
		if (from == null) {
			return Collections.emptySet();
		}
		return from
			.map(mapper)
			.filter(Objects::nonNull)
			.flatMap(func)
			.filter(Objects::nonNull)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public static <T, U, R> Set<R> setByFlatMap(Iterable<T> from,
		Function<? super T, ? extends U> mapper,
		Function<U, ? extends Stream<? extends R>> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptySet();
		}
		return setByFlatMap(of(from), mapper, func);
	}

	public static <T, U, R> Set<R> setByFlatMap(T[] from,
		Function<? super T, ? extends U> mapper,
		Function<U, ? extends Stream<? extends R>> func) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptySet();
		}
		return setByFlatMap(of(from), mapper, func);
	}

	public static <T, U, R> Set<R> setByFlatMap(Collection<T> from,
		Function<? super T, ? extends U> mapper,
		Function<U, ? extends Stream<? extends R>> func) {
		if (Collects.isEmpty(from)) {
			return Collections.emptySet();
		}
		return setByFlatMap(of(from), mapper, func);
	}


	public static <T, K> Map<K, T> toMap(Stream<T> from, Function<T, K> keyFunc) {
		return toMap(from, keyFunc, Function.identity());
	}

	public static <T, K> Map<K, T> toMap(Iterable<T> from, Function<T, K> keyFunc) {
		return toMap(from, keyFunc, Function.identity());
	}

	public static <T, K> Map<K, T> toMap(T[] from, Function<T, K> keyFunc) {
		return toMap(from, keyFunc, Function.identity());
	}

	public static <T, K> Map<K, T> toMap(Collection<T> from, Function<T, K> keyFunc) {
		return toMap(from, keyFunc, Function.identity());
	}


	public static <T, K, V> Map<K, V> toMap(Stream<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		return toMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
	}

	public static <T, K, V> Map<K, V> toMap(Iterable<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		return toMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
	}

	public static <T, K, V> Map<K, V> toMap(T[] from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		return toMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
	}

	public static <T, K, V> Map<K, V> toMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		return toMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
	}


	public static <T, K> Map<K, T> toMap(Stream<T> from, Function<T, K> keyFunc, Supplier<? extends Map<K, T>> supplier) {
		return toMap(from, keyFunc, Function.identity(), supplier);
	}

	public static <T, K> Map<K, T> toMap(Iterable<T> from, Function<T, K> keyFunc, Supplier<? extends Map<K, T>> supplier) {
		return toMap(from, keyFunc, Function.identity(), supplier);
	}

	public static <T, K> Map<K, T> toMap(T[] from, Function<T, K> keyFunc, Supplier<? extends Map<K, T>> supplier) {
		return toMap(from, keyFunc, Function.identity(), supplier);
	}

	public static <T, K> Map<K, T> toMap(Collection<T> from, Function<T, K> keyFunc, Supplier<? extends Map<K, T>> supplier) {
		return toMap(from, keyFunc, Function.identity(), supplier);
	}


	public static <T, K, V> Map<K, V> toMap(Stream<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
		return toMap(from, keyFunc, valueFunc, mergeFunction, LinkedHashMap::new);
	}

	public static <T, K, V> Map<K, V> toMap(Iterable<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
		return toMap(from, keyFunc, valueFunc, mergeFunction, LinkedHashMap::new);
	}

	public static <T, K, V> Map<K, V> toMap(T[] from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
		return toMap(from, keyFunc, valueFunc, mergeFunction, LinkedHashMap::new);
	}

	public static <T, K, V> Map<K, V> toMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
		return toMap(from, keyFunc, valueFunc, mergeFunction, LinkedHashMap::new);
	}


	public static <T, K, V> Map<K, V> toMap(Stream<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
		return toMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
	}

	public static <T, K, V> Map<K, V> toMap(Iterable<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
		return toMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
	}

	public static <T, K, V> Map<K, V> toMap(T[] from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
		return toMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
	}

	public static <T, K, V> Map<K, V> toMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
		return toMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
	}


	public static <T, K, V> Map<K, V> toMap(Stream<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
		if (from == null) {
			return supplier.get();
		}
		return from
			.collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction, supplier));
	}

	public static <T, K, V> Map<K, V> toMap(Iterable<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
		if (Collects.isEmpty(from)) {
			return supplier.get();
		}
		return toMap(of(from), keyFunc, valueFunc, mergeFunction, supplier);
	}

	public static <T, K, V> Map<K, V> toMap(T[] from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
		if (ArrayUtils.isEmpty(from)) {
			return supplier.get();
		}
		return toMap(of(from), keyFunc, valueFunc, mergeFunction, supplier);
	}

	public static <T, K, V> Map<K, V> toMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
		if (Collects.isEmpty(from)) {
			return supplier.get();
		}
		return toMap(of(from), keyFunc, valueFunc, mergeFunction, supplier);
	}


	public static <T, K> Map<K, List<T>> multiMap(Stream<T> from, Function<T, K> keyFunc) {
		if (from == null) {
			return Collections.emptyMap();
		}
		return from
			.collect(
				Collectors.groupingBy(keyFunc, Collectors.mapping(t -> t, Collectors.toList()))
			);
	}

	public static <T, K> Map<K, List<T>> multiMap(Iterable<T> from, Function<T, K> keyFunc) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiMap(of(from), keyFunc);
	}

	public static <T, K> Map<K, List<T>> multiMap(T[] from, Function<T, K> keyFunc) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiMap(of(from), keyFunc);
	}

	public static <T, K> Map<K, List<T>> multiMap(Collection<T> from, Function<T, K> keyFunc) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiMap(of(from), keyFunc);
	}


	public static <T, K, V> Map<K, List<V>> multiMap(Stream<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		if (from == null) {
			return Collections.emptyMap();
		}
		return from
			.collect(
				Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toList()))
			);
	}

	public static <T, K, V> Map<K, List<V>> multiMap(Iterable<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiMap(of(from), keyFunc, valueFunc);
	}

	public static <T, K, V> Map<K, List<V>> multiMap(T[] from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiMap(of(from), keyFunc, valueFunc);
	}

	public static <T, K, V> Map<K, List<V>> multiMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiMap(of(from), keyFunc, valueFunc);
	}


	public static <T, K, V> Map<K, Set<V>> multiSetMap(Stream<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		if (from == null) {
			return Collections.emptyMap();
		}
		return from.collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toCollection(LinkedHashSet::new))));
	}

	public static <T, K, V> Map<K, Set<V>> multiSetMap(Iterable<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiSetMap(of(from), keyFunc, valueFunc);
	}

	public static <T, K, V> Map<K, Set<V>> multiSetMap(T[] from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiSetMap(of(from), keyFunc, valueFunc);
	}

	public static <T, K, V> Map<K, Set<V>> multiSetMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyMap();
		}
		return multiSetMap(of(from), keyFunc, valueFunc);
	}


	public static <T, R> List<T> distinct(Stream<T> from, Function<T, R> keyMapper) {
		if (from == null) {
			return Collections.emptyList();
		}
		return distinct(from, keyMapper, (t1, t2) -> t1);
	}

	public static <T, R> List<T> distinct(Iterable<T> from, Function<T, R> keyMapper) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return distinct(from, keyMapper, (t1, t2) -> t1);
	}

	public static <T, R> List<T> distinct(T[] from, Function<T, R> keyMapper) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyList();
		}
		return distinct(from, keyMapper, (t1, t2) -> t1);
	}

	public static <T, R> List<T> distinct(Collection<T> from, Function<T, R> keyMapper) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return distinct(from, keyMapper, (t1, t2) -> t1);
	}


	public static <T, R> List<T> distinct(Stream<T> from, Function<T, R> keyMapper, BinaryOperator<T> cover) {
		if (from == null) {
			return Collections.emptyList();
		}
		return new ArrayList<>(toMap(from, keyMapper, Function.identity(), cover).values());
	}

	public static <T, R> List<T> distinct(Iterable<T> from, Function<T, R> keyMapper, BinaryOperator<T> cover) {
		if (Collects.isEmpty(from)) {
			return new ArrayList<>();
		}
		return new ArrayList<>(toMap(from, keyMapper, Function.identity(), cover).values());
	}

	public static <T, R> List<T> distinct(T[] from, Function<T, R> keyMapper, BinaryOperator<T> cover) {
		if (ArrayUtils.isEmpty(from)) {
			return Collections.emptyList();
		}
		return new ArrayList<>(toMap(from, keyMapper, Function.identity(), cover).values());
	}

	public static <T, R> List<T> distinct(Collection<T> from, Function<T, R> keyMapper, BinaryOperator<T> cover) {
		if (Collects.isEmpty(from)) {
			return Collections.emptyList();
		}
		return new ArrayList<>(toMap(from, keyMapper, Function.identity(), cover).values());
	}


}
