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
package cn.xphsc.web.boot.cache.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static cn.xphsc.web.common.WebBeanTemplate.CACHE_PREFIX;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  LRU  Cache Properties
 * @since 2.0.1
 */
@ConfigurationProperties(prefix = CACHE_PREFIX)
public class LRUCacheProperties {
    /**
     * 容量
     */
    private int capacity = 100; // 默认容量

    /**
     * 访问后过期时间
     */
    private long expireAfterAccess = -1; // 访问后过期时间，单位秒，默认不过期
    /**
     * 写入后过期时间
     */
    private long expireAfterWrite = -1; // 写入后过期时间，单位秒，默认不过期

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public long getExpireAfterAccess() {
        return expireAfterAccess;
    }

    public void setExpireAfterAccess(long expireAfterAccess) {
        this.expireAfterAccess = expireAfterAccess;
    }

    public long getExpireAfterWrite() {
        return expireAfterWrite;
    }

    public void setExpireAfterWrite(long expireAfterWrite) {
        this.expireAfterWrite = expireAfterWrite;
    }
}
