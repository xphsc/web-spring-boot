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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.function.Function;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Iterators tools
 * @since 2.0.3
 */
public class Iterators {

	public static <E> Iterator<E> iterator(Enumeration<E> enumeration) {
		return new Iterator<E>() {
			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public E next() {
				return enumeration.nextElement();
			}
		};
	}

	public static <E> Enumeration<E> enumeration(Iterable<E> iterable) {
		return enumeration(iterable.iterator());
	}

	public static <E> Enumeration<E> enumeration(Iterator<E> iterator) {
		return new Enumeration<E>() {
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public E nextElement() {
				return iterator.next();
			}
		};
	}

	public static <S, T> Iterator<T> convert(Iterator<S> iterator, Function<S, T> converter) {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				return converter.apply(iterator.next());
			}

			@Override
			public void remove() {
				iterator.next();
			}
		};
	}


	public static <E>  E getNext(Iterator<? extends E> iterator) {
		return getNext(iterator, null);
	}

	public static <E>  E getNext(Iterator<? extends E> iterator, E defaultValue) {
		return iterator.hasNext() ? iterator.next() : defaultValue;
	}

	public static <E> E getLast(Iterator<E> iterator) {
		while (true) {
			E current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	public static <E> E getLast(Iterator<? extends E> iterator,  E defaultValue) {
		return iterator.hasNext() ? getLast(iterator) : defaultValue;
	}

	public static <E>  E get(Iterator<? extends E> iterator, int position) {
		return get(iterator, position, null);
	}

	public static <E>  E get(Iterator<? extends E> iterator, int position,  E defaultValue) {
		skip(iterator, position);
		return getNext(iterator, defaultValue);
	}

	public static int skip(Iterator<?> iterator, int num) {
		int i;
		for (i = 0; i < num && iterator.hasNext(); i++) {
			iterator.next();
		}
		return i;
	}
}
