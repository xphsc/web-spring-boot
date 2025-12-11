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


import cn.xphsc.web.boot.cache.CacheListenerBeanPostProcessor;
import cn.xphsc.web.cache.lru.DefaultLRUCache;
import cn.xphsc.web.cache.provider.DefaultCacheListenerProvider;
import cn.xphsc.web.cache.lru.LRUCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static cn.xphsc.web.common.WebBeanTemplate.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  LRU  Cache Configuration
 * @since 2.0.1
 */
@Configuration
@ConditionalOnClass({LRUCache.class})
@Import({DefaultCacheListenerProvider.class})
@EnableConfigurationProperties({LRUCacheProperties.class})
@ConditionalOnProperty(prefix =CACHE_PREFIX, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class LRUCacheAutoConfiguration {
    private  final LRUCacheProperties lruCacheProperties;

    public LRUCacheAutoConfiguration(LRUCacheProperties lruCacheProperties) {
    this.lruCacheProperties = lruCacheProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public LRUCache lruCache() {
        return new DefaultLRUCache<>(lruCacheProperties.getCapacity(), lruCacheProperties.getExpireAfterAccess(), lruCacheProperties.getExpireAfterWrite());
    }
    @Bean
    public CacheListenerBeanPostProcessor cacheListenerBeanPostProcessor() {
        return new CacheListenerBeanPostProcessor(lruCache());
    }

}





