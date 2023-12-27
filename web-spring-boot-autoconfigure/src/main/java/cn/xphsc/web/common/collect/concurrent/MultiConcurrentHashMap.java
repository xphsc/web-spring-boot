/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.common.collect.concurrent;


import cn.xphsc.web.common.collect.MultiMap;
import cn.xphsc.web.common.lang.constant.Constants;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link ConcurrentHashMap}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: MultiConcurrentHashMap
 * @since 1.1.6
 */
public class MultiConcurrentHashMap<E, K, V> extends ConcurrentHashMap<E, ConcurrentHashMap<K, V>>
        implements MultiMap<E, K, V, ConcurrentHashMap<K, V>>, Serializable {

    private static final long serialVersionUID = 8455892712354974891L;

    /**
     * key 初始化空间
     */
    private int keyInitialCapacity;

    public MultiConcurrentHashMap() {
        this(Constants.CAPACITY_16, Constants.CAPACITY_16);
    }

    public MultiConcurrentHashMap(int elementInitialCapacity) {
        this(elementInitialCapacity, Constants.CAPACITY_16);
    }

    public MultiConcurrentHashMap(int elementInitialCapacity, int keyInitialCapacity) {
        super(elementInitialCapacity);
        this.keyInitialCapacity = keyInitialCapacity;
    }

    /**
     * 设置 key 初始化空间
     * @param keyInitialCapacity key 初始化空间
     */
    public void keyInitialCapacity(int keyInitialCapacity) {
        this.keyInitialCapacity = keyInitialCapacity;
    }

    @Override
    public ConcurrentHashMap<K, V> computeSpace(E e) {
        return super.computeIfAbsent(e, k -> new ConcurrentHashMap<>(keyInitialCapacity));
    }

}

