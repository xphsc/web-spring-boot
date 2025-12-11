/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.cache.provider;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: LRU  Cache Listener
 * @since 2.0.1
 */
public interface CacheListener<K, V> {
    /**
     * 当缓存中的一个条目被移除时调用此方法。
     * @param key   被移除条目的键
     * @param value 被移除条目的值
     */
    void onEntryRemoved(K key, V value);

    /**
     * 当缓存中添加了一个新的条目时调用此方法。
     * @param key   新添加条目的键
     * @param value 新添加条目的值
     */
    void onEntryAdded(K key, V value);
}
