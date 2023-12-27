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


import cn.xphsc.web.common.collect.Sets;
import cn.xphsc.web.common.collect.iterator.ClassIterator;
import cn.xphsc.web.common.lang.function.ConvertFunction;
import cn.xphsc.web.common.lang.define.CloneSupport;
import cn.xphsc.web.common.collect.concurrent.MultiConcurrentHashMap;
import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.ClassUtils;
import cn.xphsc.web.utils.StringUtils;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 类型转换容器
 * @since 1.1.6
 */
public class ObjectTypeStore extends CloneSupport<ObjectTypeStore> implements Serializable {

    private static final long serialVersionUID = 12038041239487192L;

    public static final ObjectTypeStore STORE = new ObjectTypeStore();

    private final MultiConcurrentHashMap<Class<?>, Class<?>, ConvertFunction<?, ?>> conversionMapping;

    public ObjectTypeStore() {
        this.conversionMapping = new MultiConcurrentHashMap<>();
    }

    static {
        // 装载基础Mapper
        new BasicTypeStoreProvider(STORE);
    }

    /**
     * 注册转换器
     * @param source     源class
     * @param target     目标class
     * @param conversion 转换器
     * @param <T>        T
     * @param <R>        R
     */
    public <T, R> void register(Class<T> source, Class<R> target, ConvertFunction<T, R> conversion) {
        conversionMapping.put(source, target, conversion);
    }

    /**
     * 获取对象转换器
     * @param source 源class
     * @param target 目标class
     * @param <T>    T
     * @param <R>    R
     * @return 转换器
     */
    public <T, R> ConvertFunction<T, R> get(Class<T> source, Class<R> target) {
        return (ConvertFunction<T, R>) conversionMapping.get(source, target);
    }

    /**
     * 获取映射转换器
     *
     * @return 转换器列表
     */
    public MultiConcurrentHashMap<Class<?>, Class<?>, ConvertFunction<?, ?>> getConversionMapping() {
        return conversionMapping;
    }

    /**
     * 转换
     * @param t           T
     * @param targetClass targetClass
     * @param <R>         target
     * @return R
     */
    public <T, R> R to(T t, Class<R> targetClass) {
        Class<?> sourceClass = t.getClass();
        targetClass = (Class<R>) ClassUtils.wrapClass(targetClass);
        // 检查是否可以直接转换
        if (canDirectConvert(sourceClass, targetClass, false)) {
            return (R) t;
        }
        // 获取类转换器
        ConvertFunction<T, R> conversion = (ConvertFunction<T, R>) this.get(sourceClass, targetClass);
        if (conversion != null) {
            return conversion.apply(t);
        }
        // 获取父类转换器
        for (Class<?> sourceParentClass : new ClassIterator<>(sourceClass)) {
            conversion = (ConvertFunction<T, R>) this.get(sourceParentClass, targetClass);
            if (conversion != null) {
                return conversion.apply(t);
            }
        }
        // 检查是否是数组
        if (!ClassUtils.isArray(targetClass)) {
            throw new RuntimeException(StringUtils.format("unable to convert source [{}] class to target [{}] class", sourceClass, targetClass));
        }
        // 如果不是基本类型的数组则无法转换
        Class<?> baseArrayClass = ClassUtils.baseArrayClass(targetClass);
        if (baseArrayClass.equals(targetClass)) {
            throw new RuntimeException(StringUtils.format("unable to convert source [{}] class to target [{}] class", sourceClass, targetClass));
        }
        // 如果 targetClass 是 sourceClass 的包装类数组则直接包装
        if (sourceClass.equals(baseArrayClass)) {
            return (R) ArrayUtils.wrap(t);
        }
        // 尝试使用 targetClass 的基本类型数组获取
        ConvertFunction<T, ?> baseConvert = (ConvertFunction<T, ?>) this.get(sourceClass, baseArrayClass);
        if (baseConvert == null) {
            throw new RuntimeException(StringUtils.format("unable to convert source [{}] class to target [{}] class", sourceClass, targetClass));
        }
        // 如果能获取到则将转换结果包装
        Object apply = baseConvert.apply(t);
        if (apply != null) {
            return (R) ArrayUtils.wrap(apply);
        }
        return null;
    }

    /**
     * 获取适配的 class 不适配父类
     *
     * @param sourceType 原始类型
     * @return set
     */
    public Set<Class<?>> getSuitableClasses(Class<?> sourceType) {
        return this.getSuitableClasses(sourceType, false);
    }

    /**
     * 获取所有适配的 class 适配父类
     * @param sourceType 原始类型
     * @return set
     */
    public Set<Class<?>> getAllSuitableClasses(Class<?> sourceType) {
        return this.getSuitableClasses(sourceType, true);
    }

    protected Set<Class<?>> getSuitableClasses(Class<?> sourceType, boolean all) {
        Set<Class<?>> classes = Sets.of(conversionMapping.computeIfAbsent(sourceType, c -> new ConcurrentHashMap<>(8)).keys());
        if (all) {
            for (Class<?> parentType : new ClassIterator<>(sourceType)) {
                Map<Class<?>, ConvertFunction<?, ?>> map = conversionMapping.get(parentType);
                if (map != null) {
                    classes.addAll(map.keySet());
                }
            }
        }
        return classes;
    }

    /**
     * 获取适配的 Conversion 不适配父类
     * @param sourceType 原始类型
     * @return map
     */
    public Map<Class<?>, ConvertFunction<?, ?>> getSuitableConversion(Class<?> sourceType) {
        return this.getSuitableConversion(sourceType, false);
    }

    /**
     * 获取所有适配的 Conversion 适配父类
     *
     * @param sourceType 原始类型
     * @return map
     */
    public Map<Class<?>, ConvertFunction<?, ?>> getAllSuitableConversion(Class<?> sourceType) {
        return this.getSuitableConversion(sourceType, true);
    }

    protected Map<Class<?>, ConvertFunction<?, ?>> getSuitableConversion(Class<?> sourceType, boolean all) {
        Map<Class<?>, ConvertFunction<?, ?>> mapping = conversionMapping.computeIfAbsent(sourceType, c -> new ConcurrentHashMap<>(8));
        if (all) {
            for (Class<?> parentType : new ClassIterator<>(sourceType)) {
                Map<Class<?>, ConvertFunction<?, ?>> parentMapping = conversionMapping.get(parentType);
                if (parentMapping == null) {
                    continue;
                }
                parentMapping.forEach(mapping::putIfAbsent);
            }
        }
        return mapping;
    }

    /**
     * 获取全局 Store
     * @return TypeStore
     */
    public static ObjectTypeStore getStore() {
        return STORE;
    }

    /**
     * 判断类型是否可以转换 sourceClass -> targetClass
     * @param sourceClass 源class
     * @param targetClass 目标class
     * @return true可以直接转换
     */
    public static boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
        return canConvert(sourceClass, targetClass, STORE);
    }

    /**
     * 判断类型是否可以转换 sourceClass -> targetClass
     *
     * @param sourceClass 源class
     * @param targetClass 目标class
     * @param store       store
     * @return true可以直接转换
     */
    public static boolean canConvert(Class<?> sourceClass, Class<?> targetClass, ObjectTypeStore store) {
        sourceClass = ClassUtils.wrapClass(sourceClass);
        targetClass = ClassUtils.wrapClass(targetClass);
        if (canDirectConvert(sourceClass, targetClass, false)) {
            return true;
        }
        if (store.get(sourceClass, targetClass) != null) {
            return true;
        }
        for (Class<?> sourceParentClass : new ClassIterator<>(sourceClass)) {
            if (store.get(sourceParentClass, targetClass) != null) {
                return true;
            }
        }
        if (!ClassUtils.isArray(targetClass)) {
            return false;
        }
        Class<?> baseArrayClass = ClassUtils.baseArrayClass(targetClass);
        if (baseArrayClass.equals(targetClass)) {
            return false;
        }
        if (sourceClass.equals(baseArrayClass)) {
            return true;
        }
        return store.get(sourceClass, baseArrayClass) != null;
    }

    /**
     * 判断类型是否可以直接转换 sourceClass -> targetClass
     * @param sourceClass 源class
     * @param targetClass 目标class
     * @return true可以直接转换
     */
    public static boolean canDirectConvert(Class<?> sourceClass, Class<?> targetClass) {
        return canDirectConvert(sourceClass, targetClass, true);
    }

    /**
     * 判断类型是否可以直接转换 sourceClass -> targetClass
     * @param sourceClass 源class
     * @param targetClass 目标class
     * @param wrap        是否包装基本类型
     * @return true可以直接转换
     */
    private static boolean canDirectConvert(Class<?> sourceClass, Class<?> targetClass, boolean wrap) {
        if (wrap) {
            sourceClass = ClassUtils.wrapClass(sourceClass);
            targetClass = ClassUtils.wrapClass(targetClass);
        }
        if (targetClass.equals(Object.class) || targetClass.equals(sourceClass)) {
            return true;
        }
        // check impl
        return ClassUtils.isImplClass(targetClass, sourceClass);
    }

}
