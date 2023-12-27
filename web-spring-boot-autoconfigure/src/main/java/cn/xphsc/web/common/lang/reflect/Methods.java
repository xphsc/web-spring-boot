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



import cn.xphsc.web.common.collect.Lists;
import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.common.collect.concurrent.ConcurrentReferenceHashMap;
import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.StringUtils;
import cn.xphsc.web.utils.Asserts;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 反射 方法工具类
 * @since 1.1.7
 */
@SuppressWarnings("ALL")
public class Methods {

    /**
     * set方法前缀
     */
    protected static final String SETTER_PREFIX = "set";

    /**
     * get方法前缀
     */
    protected static final String GETTER_PREFIX = "get";

    /**
     * boolean get方法前缀
     */
    protected static final String BOOLEAN_GETTER_PREFIX = "is";

    private static final Map<Class<?>, List<Method>> CLASS_SET_METHOD_CACHE = new ConcurrentReferenceHashMap<>(Constants.CAPACITY_16, ConcurrentReferenceHashMap.ReferenceType.SOFT);

    private static final Map<Class<?>, List<Method>> CLASS_GET_METHOD_CACHE = new ConcurrentReferenceHashMap<>(Constants.CAPACITY_16, ConcurrentReferenceHashMap.ReferenceType.SOFT);

    private Methods() {
    }

    // -------------------- cache start --------------------

    /**
     * 获取getter方法
     * @param clazz class
     * @param field field
     * @return getter
     */
    public static Method getGetterMethodByCache(Class<?> clazz, String field) {
        List<Method> methods = getGetterMethodsByCache(clazz);
        if (methods == null) {
            return null;
        }
        String methodName1 = GETTER_PREFIX + StringUtils.upperCaseFirst(field);
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && method.getName().equals(methodName1)) {
                return method;
            }
        }
        String methodName2 = BOOLEAN_GETTER_PREFIX + StringUtils.upperCaseFirst(field);
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && method.getName().equals(methodName2)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 获取setter方法
     * @param clazz class
     * @param field field
     * @return method
     */
    public static Method getSetterMethodByCache(Class<?> clazz, String field) {
        List<Method> methods = getSetterMethodsByCache(clazz);
        if (methods == null) {
            return null;
        }
        String methodName = SETTER_PREFIX + StringUtils.upperCaseFirst(field);
        for (Method method : methods) {
            if (method.getParameterCount() == 1 && method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 获取所有getter方法
     * @param clazz class
     * @return method
     */
    public static List<Method> getGetterMethodsByCache(Class<?> clazz) {
        List<Method> methodList = CLASS_GET_METHOD_CACHE.get(clazz);
        if (methodList == null) {
            CLASS_GET_METHOD_CACHE.put(clazz, methodList = getGetterMethods(clazz));
        }
        return methodList;
    }

    /**
     * 获取所有setter方法
     * @param clazz class
     * @return method
     */
    public static List<Method> getSetterMethodsByCache(Class<?> clazz) {
        List<Method> methodList = CLASS_SET_METHOD_CACHE.get(clazz);
        if (methodList == null) {
            CLASS_SET_METHOD_CACHE.put(clazz, methodList = getSetterMethods(clazz));
        }
        return methodList;
    }

    // -------------------- cache end --------------------

    /**
     * 通过字段获取getter方法
     * @param field field
     * @return getter方法
     */
    public static String getGetterMethodNameByField(Field field) {
        return getGetterMethodNameByFieldName(field.getName(), field.getType().equals(Boolean.TYPE));
    }

    /**
     * 通过字段名称获取getter方法
     * @param fieldName fieldName
     * @return getter方法
     */
    public static String getGetterMethodNameByFieldName(String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        return GETTER_PREFIX + StringUtils.upperCaseFirst(fieldName.trim());
    }

    /**
     * 通过字段名称获取getter方法
     * @param fieldName      fieldName
     * @param isBooleanClass 是否为 Boolean.TYPE
     * @return getter方法
     */
    public static String getGetterMethodNameByFieldName(String fieldName, boolean isBooleanClass) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        return (isBooleanClass ? BOOLEAN_GETTER_PREFIX : GETTER_PREFIX) + StringUtils.upperCaseFirst(fieldName.trim());
    }

    /**
     * 通过字段获取setter方法
     *
     * @param field field
     * @return setter方法
     */
    public static String getSetterMethodNameByField(Field field) {
        return SETTER_PREFIX + StringUtils.upperCaseFirst(field.getName());
    }

    /**
     * 通过字段名称获取setter方法
     * @param fieldName fieldName
     * @return setter方法
     */
    public static String getSetterMethodNameByFieldName(String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        return SETTER_PREFIX + StringUtils.upperCaseFirst(fieldName.trim());
    }

    /**
     * 通过方法获取所有的getter方法
     * @param clazz class
     * @return getter方法
     */
    public static List<Method> getGetterMethods(Class<?> clazz) {
        List<Method> list = new ArrayList<>();
        // get super class methods
        for (Method method : clazz.getMethods()) {
            if (!"getClass".equals(method.getName()) && !Modifier.isStatic(method.getModifiers()) && method.getParameters().length == 0) {
                String name = method.getName();
                if (name.startsWith(GETTER_PREFIX) && name.length() != 3 && !method.getReturnType().equals(Void.TYPE)) {
                    setAccessible(method);
                    list.add(method);
                } else if (method.getName().startsWith(BOOLEAN_GETTER_PREFIX) && name.length() != 2 && method.getReturnType().equals(Boolean.TYPE)) {
                    setAccessible(method);
                    list.add(method);
                }
            }
        }
        return list;
    }

    /**
     * 通过方法获取所有的setter方法
     * @param clazz class
     * @return setter方法
     */
    public static List<Method> getSetterMethods(Class<?> clazz) {
        List<Method> list = new ArrayList<>();
        // get super class methods
        for (Method method : clazz.getMethods()) {
            String name = method.getName();
            if (name.startsWith(SETTER_PREFIX) && name.length() != 3 && !Modifier.isStatic(method.getModifiers()) && method.getParameters().length == 1) {
                setAccessible(method);
                list.add(method);
            }
        }
        return list;
    }

    /**
     * 通过字段获取所有的getter方法
     * @param clazz class
     * @return getter方法
     */
    public static List<Method> getGetterMethodsByField(Class<?> clazz) {
        List<Method> list = new ArrayList<>();
        List<Field> fields = Fields.getFields(clazz);
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                Method method;
                if (field.getType().equals(Boolean.TYPE)) {
                    String fieldName = StringUtils.upperCaseFirst(field.getName());
                    method = getAccessibleMethod(clazz, BOOLEAN_GETTER_PREFIX + fieldName, 0);
                    if (method == null) {
                        method = getAccessibleMethod(clazz, GETTER_PREFIX + fieldName, 0);
                    }
                } else {
                    method = getAccessibleMethod(clazz, GETTER_PREFIX + StringUtils.upperCaseFirst(field.getName()), 0);
                }
                if (method != null) {
                    list.add(method);
                }
            }
        }
        return list;
    }

    /**
     * 通过字段获取所有的setter方法
     * @param clazz class
     * @return setter方法
     */
    public static List<Method> getSetterMethodsByField(Class<?> clazz) {
        List<Method> list = new ArrayList<>();
        List<Field> fields = Fields.getFields(clazz);
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                String methodName = SETTER_PREFIX + StringUtils.upperCaseFirst(field.getName());
                Method method = getAccessibleMethod(clazz, methodName, field.getType());
                if (method != null) {
                    list.add(method);
                }
            }
        }
        return list;
    }

    /**
     * 通过字段获取getter方法
     * @param clazz class
     * @param field field
     * @return get方法
     */
    public static Method getGetterMethodByField(Class<?> clazz, Field field) {
        Method method;
        if (field.getType().equals(Boolean.TYPE)) {
            String fieldName = StringUtils.upperCaseFirst(field.getName());
            method = getAccessibleMethod(clazz, BOOLEAN_GETTER_PREFIX + fieldName, 0);
            if (method == null) {
                method = getAccessibleMethod(clazz, GETTER_PREFIX + fieldName, 0);
            }
        } else {
            String methodName = GETTER_PREFIX + StringUtils.upperCaseFirst(field.getName());
            method = getAccessibleMethod(clazz, methodName, 0);
        }
        return method;
    }

    /**
     * 通过字段名称获取getter方法
     * @param clazz     class
     * @param fieldName fieldName
     * @return getter方法
     */
    public static Method getGetterMethodByFieldName(Class<?> clazz, String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        fieldName = StringUtils.upperCaseFirst(fieldName.trim());
        Method method = getAccessibleMethod(clazz, GETTER_PREFIX + fieldName, 0);
        if (method == null) {
            method = getAccessibleMethod(clazz, BOOLEAN_GETTER_PREFIX + fieldName, 0);
        }
        return method;
    }

    /**
     * 通过字段名称获取setter方法
     * @param clazz class
     * @param field field
     * @return setter方法
     */
    public static Method getSetterMethodByField(Class<?> clazz, Field field) {
        String methodName = SETTER_PREFIX + StringUtils.upperCaseFirst(field.getName());
        return getAccessibleMethod(clazz, methodName, field.getType());
    }

    /**
     * 通过字段获取setter方法
     * @param clazz     class
     * @param fieldName field
     * @return setter方法
     */
    public static Method getSetterMethodByFieldName(Class<?> clazz, String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        fieldName = fieldName.trim();
        String methodName = SETTER_PREFIX + StringUtils.upperCaseFirst(fieldName);
        return getAccessibleMethod(clazz, methodName, 1);
    }

    /**
     * 获取对象匹配方法名和参数类型的DeclaredMethod, 并强制设置为可访问, 可以获取基本类型的方法
     * @param clazz          class
     * @param methodName     方法名
     * @param parameterTypes 参数类型
     * @return 方法对象
     */
    public static Method getAccessibleMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        Asserts.notNull(clazz, "method class is null");
        for (Class<?> searchType = clazz; searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                setAccessible(method);
                return method;
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 获取对象匹配方法名和参数长度的第一个DeclaredMethod, 并强制设置为可访问, 可以获取基本类型的方法
     * @param clazz      class
     * @param methodName 方法名称
     * @param argsNum    参数数量
     * @return 方法对象
     */
    public static Method getAccessibleMethod(Class<?> clazz, String methodName, int argsNum) {
        Asserts.notNull(clazz, "method class is null");
        for (Class<?> searchType = clazz; searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == argsNum) {
                    setAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 获取对象匹配方法名的第一个DeclaredMethod, 并强制设置为可访问, 可以获取基本类型的方法
     * @param clazz      class
     * @param methodName 方法名称
     * @return 方法对象
     */
    public static Method getAccessibleMethod(Class<?> clazz, String methodName) {
        for (Class<?> searchType = clazz; searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    setAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 获取对象匹配方法名和参数长度的DeclaredMethod, 并强制设置为可访问, 可以获取基本类型的方法
     * @param clazz      class
     * @param methodName 方法名称
     * @param argsNum    参数数量
     * @return 方法对象
     */
    public static List<Method> getAccessibleMethods(Class<?> clazz, String methodName, int argsNum) {
        Asserts.notNull(clazz, "method class is null");
        List<Method> methods = new ArrayList<>();
        for (Class<?> searchType = clazz; searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] searchMethods = searchType.getDeclaredMethods();
            for (Method method : searchMethods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == argsNum) {
                    setAccessible(method);
                    methods.add(method);
                }
            }
        }
        return methods;
    }

    /**
     * 获取对象匹配方法名的DeclaredMethod, 并强制设置为可访问
     * @param clazz      class
     * @param methodName 方法名称
     * @return 方法对象
     */
    public static List<Method> getAccessibleMethods(Class<?> clazz, String methodName) {
        Asserts.notNull(clazz, "method class is null");
        List<Method> methods = new ArrayList<>();
        for (Class<?> searchType = clazz; searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] searchMethods = searchType.getDeclaredMethods();
            for (Method method : searchMethods) {
                if (method.getName().equals(methodName)) {
                    setAccessible(method);
                    methods.add(method);
                }
            }
        }
        return methods;
    }

    /**
     * 获取该类的所有方法
     * @param clazz 反射类
     * @return 方法
     */
    public static List<Method> getAccessibleMethods(Class<?> clazz) {
        Asserts.notNull(clazz, "method class is null");
        if (clazz.getSuperclass() != null) {
            List<Method> methodList = Stream.of(clazz.getDeclaredMethods())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .peek(Methods::setAccessible)
                    .collect(toCollection(ArrayList::new));
            Class<?> superClass = clazz.getSuperclass();
            // 当前类方法
            Map<String, Method> methodMap = methodList.stream().collect(toMap(Method::getName, identity()));
            // 父类方法
            getAccessibleMethods(superClass).stream().filter(m -> !methodMap.containsKey(m.getName())).forEach(methodList::add);
            return methodList;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 获取该类的所有方法列表
     * @param clazz 反射类
     * @return 方法
     */
    public static Map<String, Method> getAccessibleMethodMap(Class<?> clazz) {
        List<Method> methodList = getAccessibleMethods(clazz);
        return Lists.isNotEmpty(methodList) ? methodList.stream().collect(Collectors.toMap(Method::getName, identity())) : new HashMap<>();
    }

    /**
     * 设置方法可访问
     */
    public static void setAccessible(Method method) {
        Asserts.notNull(method, "set accessible method class is null");
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 获取所有static的方法
     * @param clazz 类
     * @return static方法
     */
    public static List<Method> getStaticMethods(Class<?> clazz) {
        Asserts.notNull(clazz, "class is null");
        Method[] methods = clazz.getDeclaredMethods();
        return Arrays.stream(methods)
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .collect(Collectors.toList());
    }

    /**
     * 调用field的getter方法
     *
     * @param obj       对象
     * @param fieldName 字段名称
     * @param <E>       属性类型
     * @return ignore
     */
    public static <E> E invokeGetter(Object obj, String fieldName) {
        Asserts.notNull(obj, "invoke object is null");
        Asserts.notBlank(fieldName, "invoke getter field is null");
        try {
            Field field = Fields.getAccessibleField(obj.getClass(), fieldName);
            if (field == null || !field.getType().equals(Boolean.TYPE)) {
                return invokeMethod(obj, GETTER_PREFIX + StringUtils.upperCaseFirst(fieldName), null, (Object[]) null);
            } else {
                return invokeMethod(obj, BOOLEAN_GETTER_PREFIX + StringUtils.upperCaseFirst(fieldName), null, (Object[]) null);
            }
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("invoke field: {} setter method error {}", fieldName, e.getMessage()), e);
        }
    }

    /**
     * 调用setter方法 不支持基本类型
     * @param obj       对象
     * @param fieldName 字段名称
     * @param value     ignore
     * @param <E>       属性类型
     */
    public static <E, R> R invokeSetter(Object obj, String fieldName, E value) {
        return (R) invokeMethod(obj, SETTER_PREFIX + StringUtils.upperCaseFirst(fieldName), new Class[]{value.getClass()}, new Object[]{value});
    }

    /**
     * 调用setter方法, 多级调用需要手动拼接set 不支持基本类型
     *
     * @param obj                   对象
     * @param fieldSetterMethodName 字段名称
     * @param values                ignore
     * @param <E>                   属性类型
     */
    @SafeVarargs
    public static <E, R> R invokeSetter(Object obj, String fieldSetterMethodName, E... values) {
        Asserts.notNull(obj, "invoke object is null");
        Asserts.notBlank(fieldSetterMethodName, "invoke Setter Method is null");
        String[] names = fieldSetterMethodName.split("\\.");
        if (names.length != ArrayUtils.length(values)) {
            throw Exceptions.argument("setting method and parameter length are inconsistent");
        }
        if (names.length == 1) {
            return (R) invokeMethod(obj, SETTER_PREFIX + StringUtils.upperCaseFirst(names[0]), new Class[]{values[0].getClass()}, new Object[]{values[0]});
        } else {
            for (int i = 0; i < names.length - 1; i++) {
                E value = values[i];
                invokeMethod(obj, names[i], value == null ? null : new Class[]{value.getClass()}, value == null ? null : new Object[]{values[i]});
            }
            int end = names.length - 1;
            return (R) invokeMethod(obj, names[end], values[end] == null ? null : new Class[]{values[end].getClass()}, values[end] == null ? null : new Object[]{values[end]});
        }
    }




    /**
     * 直接调用对象方法 不支持基本类型
     * @param obj            对象
     * @param methodName     方法名称
     * @param parameterTypes 参数列表类型
     * @param args           参数列表
     * @param <E>            返回值类型
     * @return ignore
     */
    public static <E> E invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object... args) {
        Asserts.notNull(obj, "invoker object is null");
        Asserts.notBlank(methodName, "invoke method is null");
        Method method = Methods.getAccessibleMethod(obj.getClass(), methodName, parameterTypes);
        if (method == null) {
            throw Exceptions.invoke(StringUtils.format("method {} not found in class {}", methodName, obj.getClass()));
        }
        try {
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("invoke method error: {}, class: {}, args: {}", methodName, obj.getClass(), Arrays.toString(args)), e);
        }
    }

    /**
     * 直接调用对象方法
     *
     * @param obj        对象
     * @param methodName 方法名称
     * @param <E>        返回值类型
     * @return 对象
     */
    public static <E> E invokeMethod(Object obj, String methodName) {
        return invokeMethod(obj, methodName, (Object[]) null);
    }

    /**
     * 直接调用对象方法
     *
     * @param obj    对象
     * @param method 方法
     * @param <E>    返回值类型
     * @return 对象
     */
    public static <E> E invokeMethod(Object obj, Method method) {
        return invokeMethod(obj, method, (Object[]) null);
    }

    /**
     * 直接调用对象方法 不支持基本数据类型
     *
     * @param obj        对象
     * @param methodName 方法名称
     * @param args       参数列表
     * @param <E>        返回值类型
     * @return 对象
     */
    public static <E> E invokeMethod(Object obj, String methodName, Object... args) {
        Asserts.notNull(obj, "invoker object is null");
        Asserts.notBlank(methodName, "invoke method is null");
        Method method = Methods.getAccessibleMethod(obj.getClass(), methodName, ArrayUtils.length(args));
        if (method == null) {
            throw Exceptions.invoke(StringUtils.format("invoke method error: {} not found in class {}", methodName, obj.getClass().getName()));
        }
        try {
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("invoke method error: {}, class: {}, args: {}", methodName, obj.getClass().getName(), Arrays.toString(args)), e);
        }
    }

    /**
     * 直接调用对象方法 不支持基本数据类型
     *
     * @param obj    对象
     * @param method 方法
     * @param args   参数列表
     * @param <E>    返回值类型
     * @return 对象
     */
    public static <E> E invokeMethod(Object obj, Method method, Object... args) {
        Asserts.notNull(obj, "invoke object is null");
        Asserts.notNull(method, "invoke method is null");
        try {
            Methods.setAccessible(method);
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("invoke method error: {}, class: {}, args: {}", method.getName(), obj.getClass().getName(), Arrays.toString(args)), e);
        }
    }

}
