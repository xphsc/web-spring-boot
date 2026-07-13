package cn.xphsc.web.common.collect;


import java.lang.reflect.Array;
import java.util.Collections;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Iterables tools
 * @since 2.0.4
 */
public class Iterables {

	public static <E> Iterator<E> iterator(Enumeration<E> enumeration) {
		return Iterators.iterator(enumeration);
	}

	public static <E> Enumeration<E> enumeration(Iterable<E> iterable) {
		return Iterators.enumeration(iterable);
	}

	public static <E> Enumeration<E> enumeration(Iterator<E> iterator) {
		return Iterators.enumeration(iterator);
	}


	public static <E, K> Map<K, E> asMap(Iterable<E> iterable, Function<E, K> keyConverter) {
		return asMap(iterable, HashMap::new, keyConverter, Function.identity(), false);
	}

	public static <E, K> Map<K, E> asMap(Iterable<E> iterable, Supplier<Map<K, E>> supplier, Function<E, K> keyConverter) {
		return asMap(iterable, supplier, keyConverter, Function.identity(), false);
	}

	public static <E, K, V> Map<K, V> asMap(Iterable<E> iterable, Supplier<Map<K, V>> supplier, Function<E, K> keyConverter, Function<E, V> valueConverter) {
		return asMap(iterable, supplier, keyConverter, valueConverter, false);
	}

	public static <E, K, V> Map<K, V> asMap(Iterable<E> iterable, Function<E, K> keyConverter, Function<E, V> valueConverter) {
		return asMap(iterable, HashMap::new, keyConverter, valueConverter, false);
	}


	public static <E, K, V> Map<K, V> asMap(Iterable<E> iterable, Function<E, K> keyConverter, Function<E, V> valueConverter, boolean replaceIfPresent) {
		return asMap(iterable, HashMap::new, keyConverter, valueConverter, replaceIfPresent);
	}


	public static <E, K, V> Map<K, V> asMap(Iterable<E> iterable, Supplier<Map<K, V>> supplier, Function<E, K> keyConverter, Function<E, V> valueConverter, boolean replaceIfPresent) {
		Map<K, V> map = supplier.get();
		if (replaceIfPresent) {
			for (E e : iterable) {
				map.put(keyConverter.apply(e), valueConverter.apply(e));
			}
		} else {
			for (E e : iterable) {
				map.putIfAbsent(keyConverter.apply(e), valueConverter.apply(e));
			}
		}
		return map;
	}

	public static <C extends Collection<O>, E, O> C asCollection(Supplier<C> supplier, Function<E, O> converter, Enumeration<E> enumeration) {
		C c = supplier.get();
		while (enumeration.hasMoreElements()) {
			c.add(converter.apply(enumeration.nextElement()));
		}
		return c;
	}

	public static <C extends Collection<E>, E> C asCollection(Supplier<C> supplier, Enumeration<E> enumeration) {
		C c = supplier.get();
		while (enumeration.hasMoreElements()) {
			c.add(enumeration.nextElement());
		}
		return c;
	}

	public static <C extends Collection<O>, E, O> C asCollection(Supplier<C> supplier, Function<E, O> converter, E... iterable) {
		C c = supplier.get();
		for (E e : iterable) {
			c.add(converter.apply(e));
		}
		return c;
	}

	public static <C extends Collection<E>, E> C asCollection(Supplier<C> supplier, E... iterable) {
		C c = supplier.get();
		Collections.addAll(c, iterable);
		return c;
	}

	public static <C extends Collection<O>, E, O> C asCollection(Supplier<C> supplier, Function<E, O> converter, Iterable<E> iterable) {
		C c = supplier.get();
		for (E e : iterable) {
			c.add(converter.apply(e));
		}
		return c;
	}

	public static <C extends Collection<E>, E> C asCollection(Supplier<C> supplier, Iterable<E> iterable) {
		C c = supplier.get();
		for (E e : iterable) {
			c.add(e);
		}
		return c;
	}

	public static <C extends Collection<O>, E, O> C asCollection(Supplier<C> supplier, Function<E, O> converter, Iterator<E> iterator) {
		C c = supplier.get();
		while (iterator.hasNext()) {
			c.add(converter.apply(iterator.next()));
		}
		return c;
	}

	public static <C extends Collection<E>, E> C asCollection(Supplier<C> supplier, Iterator<E> iterator) {
		C c = supplier.get();
		while (iterator.hasNext()) {
			c.add(iterator.next());
		}
		return c;
	}

	public static <E> List<E> asList(Enumeration<E> enumeration) {
		return Lists.as(enumeration);
	}

	public static <E> Set<E> asSet(Enumeration<E> enumeration) {
		return Sets.of(enumeration);
	}

	@SafeVarargs
	public static <E> List<E> asList(E... iterable) {
		return Lists.as(iterable);
	}

	@SafeVarargs
	public static <E> Set<E> asSet(E... iterable) {
		return Sets.of(iterable);
	}


	public static <E> List<E> asList(Iterable<E> iterable) {
		return Lists.as(iterable);
	}



	public static <E> List<E> asList(Iterator<E> iterator) {
		return Lists.as(iterator);
	}

	public static <E> Set<E> asSet(Iterator<E> iterator) {
		return Sets.of(iterator);
	}

	public static <E> E[] copyOf(E[] array) {
		return Arrays.copyOf(array, array.length);
	}

	public static <S, T> Iterator<T> convert(Iterator<S> iterator, Function<S, T> converter) {
		return Iterators.convert(iterator, converter);
	}

	@SuppressWarnings("unchecked")
	public static <S, T> T[] convert(S[] array, T[] target, Function<S, T> converter) {
		if (target.length < array.length) {
			if (target.getClass() == Object[].class) {
				target = (T[]) new Object[array.length];
			} else {
				target = (T[]) Array.newInstance(target.getClass().getComponentType(), array.length);
			}
		}
		for (int i = 0; i < array.length; i++) {
			target[i] = (T) converter.apply((S) array[i]);
		}
		return target;
	}


	public static <S, T> boolean isMatchAll(S[] array1, T[] array2, BiFunction<S, T, Boolean> matcher) {
		if (array1 == null && array2 == null) {
			return true;
		}
		if (array1 == null || array2 == null) {
			return false;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			Boolean matched = matcher.apply(array1[i], array2[i]);
			if (!matched) {
				return false;
			}
		}
		return true;
	}

	public static <E> boolean isEmpty(Iterable<E> iterable) {
		if (iterable == null) {
			return true;
		}
		if (iterable instanceof Collection) {
			return ((Collection<E>) iterable).isEmpty();
		}
		return iterable.iterator().hasNext();
	}

	public static <E> boolean isEmpty(Collection<E> collection) {
		return collection == null || collection.isEmpty();
	}

	public static <E> boolean isEmpty(E[] array) {
		return array == null || array.length == 0;
	}

	public static <E> boolean isNotEmpty(Iterable<E> iterable) {
		return !isEmpty(iterable);
	}

	public static <E> boolean isNotEmpty(Collection<E> collection) {
		return collection != null && !collection.isEmpty();
	}

	public static <E> boolean isNotEmpty(E[] array) {
		return array != null && array.length > 0;
	}

	public static <E> boolean hasNull(E[] array) {
		if (array == null) {
			return false;
		}
		for (E e : array) {
			if (e == null) {
				return true;
			}
		}
		return false;
	}

	public static <E> boolean hasNull(Iterable<E> array) {
		if (array == null) {
			return false;
		}
		for (E e : array) {
			if (e == null) {
				return true;
			}
		}
		return false;
	}

	public static <E> boolean isMatchAny(E[] array, Function<E, Boolean> matcher) {
		if (array == null) {
			return false;
		}
		for (E e : array) {
			if (matcher.apply(e)) {
				return true;
			}
		}
		return false;
	}

	public static <E> boolean isMatchAny(Iterable<E> array, Function<E, Boolean> matcher) {
		if (array == null) {
			return false;
		}
		for (E e : array) {
			if (matcher.apply(e)) {
				return true;
			}
		}
		return false;
	}

	public static <E> boolean isMatchAll(E[] array, Function<E, Boolean> matcher) {
		if (array == null) {
			return false;
		}
		for (E e : array) {
			if (!matcher.apply(e)) {
				return false;
			}
		}
		return true;
	}

	public static <E> boolean isMatchAll(Iterable<E> array, Function<E, Boolean> matcher) {
		if (array == null) {
			return false;
		}
		for (E e : array) {
			if (!matcher.apply(e)) {
				return false;
			}
		}
		return true;
	}

	public static String toArrayString( Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof long[]) {
			return Arrays.toString((long[]) obj);
		} else if (obj instanceof int[]) {
			return Arrays.toString((int[]) obj);
		} else if (obj instanceof short[]) {
			return Arrays.toString((short[]) obj);
		} else if (obj instanceof char[]) {
			return Arrays.toString((char[]) obj);
		} else if (obj instanceof byte[]) {
			return Arrays.toString((byte[]) obj);
		} else if (obj instanceof boolean[]) {
			return Arrays.toString((boolean[]) obj);
		} else if (obj instanceof float[]) {
			return Arrays.toString((float[]) obj);
		} else if (obj instanceof double[]) {
			return Arrays.toString((double[]) obj);
		} else if (obj.getClass().isArray()) {
			try {
				return Arrays.deepToString((Object[]) obj);
			} catch (Exception ignore) {
			}
		}
		return obj.toString();
	}

	public static boolean contains(Collection<?> collection, Object value) {
		return collection != null && !collection.isEmpty() && collection.contains(value);
	}

	public static <T> boolean contains(Collection<T> collection, Predicate<? super T> predicate) {
		if (collection == null || collection.isEmpty()) {
			return false;
		}
		for (T t : collection) {
			if (predicate.test(t)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsAny(Collection<?> coll1, Collection<?> coll2) {
		if (coll1 == null || coll1.isEmpty() || coll2 == null || coll2.isEmpty()) {
			return false;
		}
		if (coll1.size() < coll2.size()) {
			for (Object object : coll1) {
				if (coll2.contains(object)) {
					return true;
				}
			}
		} else {
			for (Object object : coll2) {
				if (coll1.contains(object)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsAll(Collection<?> coll1, Collection<?> coll2) {
		if (coll1 == null || coll1.isEmpty()) {
			return coll2 == null || coll2.isEmpty();
		}

		if (coll2 == null || coll2.isEmpty()) {
			return true;
		}

		// Set直接判定
		if (coll1 instanceof Set) {
			// noinspection SuspiciousMethodCalls
			return coll1.containsAll(coll2);
		}

		// 参考Apache commons collection4
		// 将时间复杂度降低到O(n + m)
		final Iterator<?> it = coll1.iterator();
		final Set<Object> elementsAlreadySeen = new HashSet<>(coll1.size(), 1);
		for (final Object nextElement : coll2) {
			if (elementsAlreadySeen.contains(nextElement)) {
				continue;
			}

			boolean foundCurrentElement = false;
			while (it.hasNext()) {
				final Object p = it.next();
				elementsAlreadySeen.add(p);
				if (Objects.equals(nextElement, p)) {
					foundCurrentElement = true;
					break;
				}
			}

			if (!foundCurrentElement) {
				return false;
			}
		}
		return true;
	}

	public static <T> T firstNonNull(Collection<T> collection) {
		return firstMatch(collection, Objects::nonNull);
	}

	public static <T> T firstNonNull(Iterable<T> collection) {
		return firstMatch(collection, Objects::nonNull);
	}

	public static <T> T firstMatch(Collection<T> collection, Predicate<T> matcher) {
		if (collection == null || collection.isEmpty()) {
			return null;
		}
		for (T next : collection) {
			if (matcher.test(next)) {
				return next;
			}
		}
		return null;
	}

	public static <T> T firstMatch(Iterable<T> collection, Predicate<T> matcher) {
		if (collection == null) {
			return null;
		}
		for (T next : collection) {
			if (matcher.test(next)) {
				return next;
			}
		}
		return null;
	}

	public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> predicate) {
		if (collection == null || collection.isEmpty()) {
			return false;
		}
		return collection.stream().anyMatch(predicate);
	}

	public static <T> boolean allMatch(Collection<T> collection, Predicate<T> predicate) {
		if (collection == null || collection.isEmpty()) {
			return false;
		}
		return collection.stream().allMatch(predicate);
	}

	public static <T> T get(Collection<T> collection, int index) {
		if (null == collection) {
			return null;
		}

		final int size = collection.size();
		if (0 == size) {
			return null;
		}

		if (index < 0) {
			index += size;
		}

		// 检查越界
		if (index >= size || index < 0) {
			return null;
		}

		if (collection instanceof List) {
			final List<T> list = ((List<T>) collection);
			return list.get(index);
		} else {
			return Iterators.get(collection.iterator(), index);
		}
	}

	public static <T> List<T> getAll(Collection<T> collection, int... indexes) {
		final int size = collection.size();
		final List<T> result = new ArrayList<>();
		if (collection instanceof List) {
			final List<T> list = ((List<T>) collection);
			for (int index : indexes) {
				if (index < 0) {
					index += size;
				}
				if (index >= size || index < 0) {
					result.add(null);
				} else {
					result.add(list.get(index));
				}
			}
		} else {
			final Object[] array = collection.toArray();
			for (int index : indexes) {
				if (index < 0) {
					index += size;
				}
				if (index >= size || index < 0) {
					result.add(null);
				} else {
					// noinspection unchecked
					result.add((T) array[index]);
				}
			}
		}
		return result;
	}

	public static <T> T getFirst(Iterable<T> iterable) {
		if (iterable == null) {
			return null;
		}
		if (iterable instanceof List) {
			final List<T> list = (List<T>) iterable;
			return list.isEmpty() ? null : list.get(0);
		}
		return Iterators.getNext(iterable.iterator());
	}

}
