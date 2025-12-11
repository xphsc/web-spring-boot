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
package cn.xphsc.web.common.lang.reflect;


import cn.xphsc.web.common.collect.Maps;
import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.StringUtils;
import java.lang.reflect.*;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 反射 类型工具类
 * @since 1.1.7
 */
// @SuppressWarnings("ALL")
public class Types {

    private Types() {
    }

    /**
     * 是否未知类型
     * @param type type
     * @return 是否未知类型
     */
    public static boolean isUnknown(Type type) {
        return type == null || type instanceof TypeVariable;
    }

    /**
     * 获取 type 对应的原始 class
     * @param type type
     * @return 原始class
     */
    public static Class<?> getClass(Type type) {
        if (type != null) {
            if (type instanceof Class) {
                return (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                return (Class<?>) ((ParameterizedType) type).getRawType();
            } else if (type instanceof TypeVariable) {
                return (Class<?>) ((TypeVariable<?>) type).getBounds()[0];
            } else if (type instanceof WildcardType) {
                final Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                if (upperBounds.length == 1) {
                    return getClass(upperBounds[0]);
                }
            }
        }
        return null;
    }

    public static Type getType(Field field) {
        return field == null ? null : field.getGenericType();
    }

    public static Class<?> getClass(Field field) {
        return field == null ? null : field.getType();
    }

    public static Type getParameterType(Method method, int index) {
        Type[] types = method.getGenericParameterTypes();
        if (types != null && types.length > index) {
            return types[index];
        }
        return null;
    }

    public static Class<?> getParameterClass(Method method, int index) {
        Class<?>[] classes = method.getParameterTypes();
        if (classes.length > index) {
            return classes[index];
        }
        return null;
    }

    public static Type getReturnType(Method method) {
        return method == null ? null : method.getGenericReturnType();
    }

    public static Class<?> getReturnClass(Method method) {
        return method == null ? null : method.getReturnType();
    }

    public static Type getTypeArgument(Type type) {
        return getTypeArgument(type, 0);
    }

    public static Type getTypeArgument(Type type, int index) {
        Type[] typeArguments = getTypeArguments(type);
        if (typeArguments != null && typeArguments.length > index) {
            return typeArguments[index];
        }
        return null;
    }

    public static Type[] getTypeArguments(Type type) {
        if (type == null) {
            return null;
        }
        ParameterizedType parameterizedType = toParameterizedType(type);
        return parameterizedType == null ? null : parameterizedType.getActualTypeArguments();
    }

    public static ParameterizedType toParameterizedType(Type type) {
        ParameterizedType result = null;
        if (type instanceof ParameterizedType) {
            result = (ParameterizedType) type;
        } else if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            Type genericSuper = clazz.getGenericSuperclass();
            if (genericSuper == null || Object.class.equals(genericSuper)) {
                final Type[] genericInterfaces = clazz.getGenericInterfaces();
                if (ArrayUtils.isNotEmpty(genericInterfaces)) {
                    genericSuper = genericInterfaces[0];
                }
            }
            result = toParameterizedType(genericSuper);
        }
        return result;
    }

    public static Type[] getActualTypes(Type actualType, Class<?> typeDefineClass, Type... typeVariables) {
        if (!typeDefineClass.isAssignableFrom(getClass(actualType))) {
            throw Exceptions.argument(StringUtils.format("Parameter {} must be assignable from {}", typeDefineClass, actualType));
        }
        TypeVariable<?>[] typeVars = typeDefineClass.getTypeParameters();
        if (ArrayUtils.isEmpty(typeVars)) {
            return null;
        }
        Type[] actualTypeArguments = getTypeArguments(actualType);
        if (ArrayUtils.isEmpty(actualTypeArguments)) {
            return null;
        }
        int size = Math.min(typeVars.length, actualTypeArguments.length);
        Map<Type, Type> tableMap = Maps.of(typeVars, actualTypeArguments);
        Type[] result = new Type[size];
        for (int i = 0; i < typeVariables.length; i++) {
            result[i] = typeVariables[i] instanceof TypeVariable ? tableMap.get(typeVariables[i]) : typeVariables[i];
        }
        return result;
    }

    public static Type getActualType(Type actualType, Class<?> typeDefineClass, Type typeVariable) {
        Type[] types = getActualTypes(actualType, typeDefineClass, typeVariable);
        if (ArrayUtils.isNotEmpty(types)) {
            return types[0];
        }
        return null;
    }

}
