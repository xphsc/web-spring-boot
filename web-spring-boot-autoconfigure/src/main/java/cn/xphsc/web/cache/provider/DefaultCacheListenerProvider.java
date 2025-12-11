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

import org.springframework.stereotype.Component;

import java.util.List;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Default LRU  Cache Provider
 * @since 2.0.1
 */
@Component
public class DefaultCacheListenerProvider implements CacheListenerProvider {

    private final List<CacheListener> listeners;

    public DefaultCacheListenerProvider(List<CacheListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public List<CacheListener> cacheListeners() {
        return listeners;
    }
}

