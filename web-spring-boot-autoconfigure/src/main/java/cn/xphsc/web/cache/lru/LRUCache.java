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
package cn.xphsc.web.cache.lru;

import cn.xphsc.web.cache.provider.CacheListener;
import cn.xphsc.web.cache.CacheStats;

import java.util.*;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: LRU  Cache
 * @since 2.0.1
 */
public interface LRUCache<K, V> {
    /**
     * 根据键获取缓存中的值。如果键存在，则更新其访问时间戳。
     * @param key 缓存键
     * @return 缓存中的值，如果不存在则返回null
     */
     V get(K key) ;

    /**
     * 根据键手动删除缓存中的条目。
     * @param key 要删除的缓存条目的键
     */
     void remove(K key) ;

    /**
     * 将键值对放入缓存，并设置一个特定的过期时间。
     * @param key           缓存键
     * @param value         缓存值
     * @param expireAfterMs 过期时间（单位：毫秒）
     */
     void putWithExpire(K key, V value, long expireAfterMs) ;

    /**
     * 将键值对放入缓存。如果键已存在，则更新其值和写入时间戳。
     * @param key   缓存键
     * @param value 缓存值
     */
     void put(K key, V value) ;

    /**
     * 获取缓存统计信息
     * @return {@code CacheStats}
     */
     CacheStats cacheStats();

    /**
     * 获取缓存的当前大小。
     * @return 缓存中的条目数量
     */
     int size() ;
    /**
     * 清空缓存。
     */
    public void clear() ;

    /**
     * 获取缓存中的所有键值对。
     * @return 包含所有缓存条目的Map
     */
     Map<K, V> getAll() ;

    /**
     * 获取访问频率靠前的n个缓存条目
     * @return {@code List<Map.Entry<K, V>>}
     */
     List<Map.Entry<K, V>> getEntriesByFrequency(int n);


     void addCacheListener(CacheListener<K, V> listener);

    /**
     * 关闭缓存，释放资源。 应在缓存不再使用时调用此方法。
     */
     void shutdown() ;
}
