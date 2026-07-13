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
package cn.xphsc.web.common.bean.acess;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  AccessClassPool
 * @since 2.0.4
 */
class AccessClassPool<K, V> {
	private final LimitedLinkedHashMap<K, V> raw;
	private final Map<K, V> pool;

	public AccessClassPool(int maxCapacity, int initialCapacity) {
		raw = new LimitedLinkedHashMap<>(maxCapacity, true, initialCapacity);
		pool = Collections.synchronizedMap(raw);
	}

	public AccessClassPool(int maxCapacity) {
		this(maxCapacity, 128);
	}

	public AccessClassPool() {
		this(10240);
	}

	public V get(K key) {
		return pool.get(key);
	}

	public V put(K key, V value) {
		return pool.put(key, value);
	}

	public V remove(Object key) {
		return pool.remove(key);
	}

	public V putIfAbsent(K key, V value) {
		return pool.putIfAbsent(key, value);
	}

	public boolean remove(Object key, Object value) {
		return pool.remove(key, value);
	}

	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return pool.computeIfAbsent(key, mappingFunction);
	}

	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return pool.computeIfPresent(key, remappingFunction);
	}

	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return pool.compute(key, remappingFunction);
	}

	public void clear() {
		pool.clear();
	}

	public int size() {
		return pool.size();
	}

	public int getMaxCapacity() {
		return raw.getMaxCapacity();
	}

	public void setMaxCapacity(int maxCapacity) {
		raw.setMaxCapacity(maxCapacity);
	}


	static class LimitedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

		private int maxCapacity = 0;

		public LimitedLinkedHashMap(int maxCapacity) {
			this(maxCapacity, true);
		}

		public LimitedLinkedHashMap(int maxCapacity, boolean accessOrder) {
			this(maxCapacity, accessOrder, 128, .75f);
		}

		public LimitedLinkedHashMap(int maxCapacity, boolean accessOrder, int initialCapacity) {
			this(maxCapacity, accessOrder, initialCapacity, .75f);
		}

		public LimitedLinkedHashMap(int maxCapacity, boolean accessOrder, int initialCapacity, float loadFactor) {
			super(initialCapacity, loadFactor, accessOrder);
			this.maxCapacity = maxCapacity;
		}

		public void setMaxCapacity(int maxCapacity) {
			this.maxCapacity = maxCapacity;
		}

		public int getMaxCapacity() {
			return maxCapacity;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			return size() > maxCapacity;
		}
	}

}
