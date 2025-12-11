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
package cn.xphsc.web.boot.cache;

import cn.xphsc.web.cache.provider.CacheListener;
import cn.xphsc.web.cache.lru.LRUCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  LRU  Cache BeanPostProcessor
 * @since 2.0.1
 */
public class CacheListenerBeanPostProcessor implements BeanPostProcessor {

    private final LRUCache<String, Object> lruCache;

    public CacheListenerBeanPostProcessor(LRUCache<String, Object> lruCache) {
        this.lruCache = lruCache;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CacheListener) {
            lruCache.addCacheListener((CacheListener<String, Object>) bean);
        }
        return bean;
    }
}
