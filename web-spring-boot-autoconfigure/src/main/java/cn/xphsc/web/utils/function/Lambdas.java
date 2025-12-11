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
package cn.xphsc.web.utils.function;

import cn.xphsc.web.common.lang.function.Lambda.LambdaPropertyFunction;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link Optional}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Lambda
 * @since 2.0.3
 */
public class Lambdas {
    private Lambdas() {}

    // 缓存 SerializedLambda 以提升性能
    private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>(1024);

    /**
     * 获取 SerializedLambda 实例
     */
    public static <T> SerializedLambda resolve(Serializable lambda) {
        Class<?> clazz = lambda.getClass();
        return Optional.ofNullable(FUNC_CACHE.get(clazz))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda resolved = resolveLambda(lambda);
                    FUNC_CACHE.put(clazz, new WeakReference<>(resolved));
                    return resolved;
                });
    }

    private static SerializedLambda resolveLambda(Serializable lambda) {
        try {
            Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object result = writeReplace.invoke(lambda);
            if (result instanceof SerializedLambda) {
                return (SerializedLambda) result;
            }
            throw new IllegalStateException("writeReplace did not return SerializedLambda");
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve lambda", e);
        }
    }

    // ------------------ 字段相关 API ------------------

    public static <T> String getProperty(LambdaPropertyFunction<T, ?> func) {
        SerializedLambda lambda = resolve(func);
        return methodToProperty(lambda.getImplMethodName());
    }

    @SafeVarargs
    public static <T> List<String> getPropertyList(LambdaPropertyFunction<T, ?>... funcs) {
        List<String> list = new ArrayList<>(funcs.length);
        for (LambdaPropertyFunction<T, ?> func : funcs) {
            list.add(getProperty(func));
        }
        return list;
    }

    @SafeVarargs
    public static <T> String[] getProperties(LambdaPropertyFunction<T, ?>... funcs) {
        return getPropertyList(funcs).toArray(new String[0]);
    }

    /**
     * 将方法名转换为字段名，如 getUsername -> username
     */
    public static String methodToProperty(String methodName) {
        if (methodName.startsWith("get") && methodName.length() > 3) {
            return decapitalize(methodName.substring(3));
        } else if (methodName.startsWith("is") && methodName.length() > 2) {
            return decapitalize(methodName.substring(2));
        }
        return methodName;
    }

    public static String decapitalize(String value) {
        if (value == null || value.isEmpty()) return value;
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }


    /**
     * 获取 Lambda 表达式引用方法的返回类型。
     * @param func 方法引用
     * @param <T>  Lambda 的输入类型
     * @param <R>  Lambda 的返回类型
     * @return 返回类型
     */
    public static <T, R> Class<?> getReturnType(LambdaPropertyFunction<T, R> func) {
        SerializedLambda lambda = resolve(func);
        try {
            Class<?> clazz = Class.forName(lambda.getImplClass().replace('/', '.'));
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(lambda.getImplMethodName())) {
                    return method.getReturnType();
                }
            }
            throw new RuntimeException("Method not found in class: " + clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get return type", e);
        }
    }

    /**
     * 获取 Lambda 表达式对应的实现类名。
     * @param func 方法引用
     * @param <T>  Lambda 的输入类型
     * @return 实现类名
     */
    public static <T> String getImplClassName(LambdaPropertyFunction<T, ?> func) {
        return resolve(func).getImplClass().replace('/', '.');
    }
    /**
     * 获取 Lambda 表达式方法的参数类型。
     * @param func 方法引用
     * @param <T>  Lambda 的输入类型
     * @return 参数类型
     */
    public static <T> Class<?>[] getParameterTypes(LambdaPropertyFunction<T, ?> func) {
        SerializedLambda lambda = resolve(func);
        try {
            Class<?> clazz = Class.forName(lambda.getImplClass().replace('/', '.'));
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(lambda.getImplMethodName())) {
                    return method.getParameterTypes();
                }
            }
            throw new RuntimeException("Method not found in class: " + clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get parameter types", e);
        }
    }
    public static <T> String generateLambdaCacheKey(LambdaPropertyFunction<T, ?> func) {
        SerializedLambda sl = resolve(func);
        return sl.getImplClass() + "#" + sl.getImplMethodName();
    }

}
