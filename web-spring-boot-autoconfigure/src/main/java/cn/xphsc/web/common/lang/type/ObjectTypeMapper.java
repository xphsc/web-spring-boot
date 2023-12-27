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
package cn.xphsc.web.common.lang.type;



import cn.xphsc.web.common.lang.function.ConvertFunction;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 对象类型转换映射的自定义实现
 * @since 1.1.6
 */
public abstract class ObjectTypeMapper<T> implements Serializable {

    private static final long serialVersionUID = -123903459090989178L;

    private final ObjectTypeStore store;

    private final Class<T> sourceType;

    public ObjectTypeMapper(Class<T> sourceType) {
        this(sourceType, ObjectTypeStore.STORE);
    }

    public ObjectTypeMapper(Class<T> sourceType, ObjectTypeStore store) {
        this.sourceType = sourceType;
        this.store = store;
    }

    /**
     * 注册转换器
     *
     * @param target     target class
     * @param conversion 转换器
     * @param <R>        R
     */
    protected <R> void register(Class<R> target, ConvertFunction<T, R> conversion) {
        store.register(sourceType, target, conversion);
    }

    /**
     * 获取转换器
     *
     * @param target target class
     * @param <R>    target
     * @return Conversion
     */
    public <R> ConvertFunction<T, R> get(Class<R> target) {
        return store.get(sourceType, target);
    }

    /**
     * 转换
     *
     * @param t      T
     * @param target targetClass
     * @param <R>    target
     * @return R
     */
    public <R> R to(T t, Class<R> target) {
        return store.to(t, target);
    }

    /**
     * 获取适配的 class
     *
     * @return set
     */
    public Set<Class<?>> getSuitableClasses() {
        return store.getSuitableClasses(sourceType);
    }

    /**
     * 获取所有适配的 class
     *
     * @return set
     */
    public Set<Class<?>> getAllSuitableClasses() {
        return store.getSuitableClasses(sourceType, true);
    }

    /**
     * 获取适配的 Conversion
     *
     * @return map
     */
    public Map<Class<?>, ConvertFunction<?, ?>> getSuitableConversion() {
        return store.getSuitableConversion(sourceType);
    }

    /**
     * 获取所有适配的 Conversion
     *
     * @return map
     */
    public Map<Class<?>, ConvertFunction<?, ?>> getAllSuitableConversion() {
        return store.getSuitableConversion(sourceType, true);
    }

}
