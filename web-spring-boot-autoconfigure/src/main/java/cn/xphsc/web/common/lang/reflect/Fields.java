package cn.xphsc.web.common.lang.reflect;

import cn.xphsc.web.common.collect.Lists;
import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.common.collect.concurrent.ConcurrentReferenceHashMap;
import cn.xphsc.web.common.lang.type.ObjectTypeStore;
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
 * @description: 反射 字段工具类
 * @since 1.1.7
 */
@SuppressWarnings("ALL")
public class Fields {

    private static final Map<Class<?>, List<Field>> FIELD_CACHE = new ConcurrentReferenceHashMap<>(Constants.CAPACITY_16, ConcurrentReferenceHashMap.ReferenceType.SOFT);

    private Fields() {
    }

    // -------------------- cache --------------------

    public static List<Field> getFieldsByCache(Class<?> clazz) {
        List<Field> fields = FIELD_CACHE.get(clazz);
        if (fields == null) {
            FIELD_CACHE.put(clazz, fields = getFields(clazz));
        }
        return fields;
    }

    public static Field getFieldByCache(Class<?> clazz, String fieldName) {
        List<Field> fields = FIELD_CACHE.get(clazz);
        if (fields == null) {
            FIELD_CACHE.put(clazz, fields = getFields(clazz));
        }
        if (fields == null) {
            return null;
        }
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 通过方法获取字段名 仅限于 getter setter
     *
     * @param method 方法
     * @return 字段名称
     */
    public static String getFieldNameByMethod(Method method) {
        if (method == null) {
            return null;
        }
        return getFieldNameByMethodName(method.getName());
    }

    /**
     * 通过方法名获取字段名 仅限于 getter setter
     *
     * @param methodName 方法名称
     * @return 字段名称
     */
    public static String getFieldNameByMethodName(String methodName) {
        if (StringUtils.isBlank(methodName)) {
            return null;
        }
        methodName = methodName.trim();
        String fieldName = null;
        int length = methodName.length();
        if (methodName.startsWith(Methods.GETTER_PREFIX) || methodName.startsWith(Methods.SETTER_PREFIX)) {
            if (length != 3) {
                fieldName = methodName.substring(3, length);
            }
        } else if (methodName.startsWith(Methods.BOOLEAN_GETTER_PREFIX)) {
            if (length != 2) {
                fieldName = methodName.substring(2, length);
            }
        }
        if (fieldName != null) {
            return StringUtils.lowerCaseOfStart(fieldName);
        }
        return null;
    }

    /**
     * 通过方法获取字段 仅限于 getter setter
     *
     * @param methodClazz 方法类
     * @param method      方法
     * @return 字段
     */
    public static Field getFieldByMethod(Class<?> methodClazz, Method method) {
        if (method == null) {
            return null;
        }
        String fieldName = getFieldNameByMethodName(method.getName());
        if (fieldName != null) {
            return getAccessibleField(methodClazz, fieldName);
        }
        return null;
    }

    /**
     * 通过方法获取字段 仅限于 getter setter
     *
     * @param methodClazz 方法类
     * @param methodName  方法名称
     * @return 字段
     */
    public static Field getFieldByMethodName(Class<?> methodClazz, String methodName) {
        String fieldName = getFieldNameByMethodName(methodName);
        if (fieldName != null) {
            return getAccessibleField(methodClazz, fieldName);
        }
        return null;
    }

    /**
     * 直接读取对象属性值
     * @param obj       对象
     * @param fieldName 字段名称
     * @param <E>       属性类型
     * @return 对象
     */
    public static <E> E getFieldValue(Object obj, String fieldName) {
        Asserts.notNull(obj, "invoker object is null");
        Asserts.notBlank(fieldName, "invoke field is null");
        Field field = getAccessibleField(obj.getClass(), fieldName);
        if (field == null) {
            throw Exceptions.invoke(StringUtils.format("get field value not found field: {}, class {}", fieldName, obj.getClass().getName()));
        }
        try {
            return (E) field.get(obj);
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("get field value error: {}, field: {}, class: {}", e.getMessage(), fieldName, obj.getClass().getName()), e);
        }
    }

    /**
     * 直接读取对象属性值
     * @param obj   对象
     * @param field 字段
     * @param <E>   属性类型
     * @return 对象
     */
    public static <E> E getFieldValue(Object obj, Field field) {
        Asserts.notNull(obj, "invoker object is null");
        Asserts.notNull(field, "invoke field is null");
        try {
            setAccessible(field);
            return (E) field.get(obj);
        } catch (Exception e) {
            throw Exceptions.runtime(StringUtils.format("get field value error: {}, field: {}, class: {}", e.getMessage(), field.getName(), obj.getClass().getName()), e);
        }
    }

    /**
     * 直接设置对象属性值 可用于基本类型字段
     * @param obj       对象
     * @param fieldName 字段名称
     * @param value     对象值
     * @param <E>       属性类型
     */
    public static <E> void setFieldValue(Object obj, String fieldName, E value) {
        Asserts.notNull(obj, "invoker object is null");
        Asserts.notBlank(fieldName, "invoke field is null");
        Field field = getAccessibleField(obj.getClass(), fieldName);
        if (field == null) {
            throw Exceptions.invoke(StringUtils.format("set field value not found field: {}, class {}", fieldName, obj.getClass().getName()));
        }
        try {
            field.set(obj, value);
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("set field value error: {}, field: {}, class: {}", e.getMessage(), fieldName, obj.getClass().getName(), value), e);
        }
    }

    /**
     * 直接设置对象属性值 可用于基本类型字段
     *
     * @param obj   对象
     * @param field 字段
     * @param value 对象值
     * @param <E>   属性类型
     */
    public static <E> void setFieldValue(Object obj, Field field, E value) {
        Asserts.notNull(obj, "invoker object is null");
        Asserts.notNull(field, "invoke field is null");
        try {
            setAccessible(field);
            field.set(obj, value);
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("set field value error: {}, field: {}, class: {}", e.getMessage(), field.getName(), obj.getClass().getName(), value), e);
        }
    }

    /**
     * 直接设置对象属性值 类型推断 可用于基本类型字段
     *
     * @param obj       对象
     * @param fieldName 字段
     * @param value     对象值
     * @param <E>       属性类型
     */
    public static <E> void setFieldValueInfer(Object obj, String fieldName, E value) {
        Asserts.notNull(obj, "invoker object is null");
        Asserts.notNull(fieldName, "invoke field is null");
        Field field = getAccessibleField(obj.getClass(), fieldName);
        try {
            if (ObjectTypeStore.canConvert(value.getClass(), field.getType())) {
                field.set(obj, ObjectTypeStore.STORE.to(value, field.getType()));
                return;
            }
            throw Exceptions.invoke(StringUtils.format("could infer set field value, field: {}, class: {}", fieldName, obj.getClass().getName()));
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("set field value error: {}, field: {}, class: {}", e.getMessage(), fieldName, obj.getClass().getName()), e);
        }
    }

    /**
     * 直接设置对象属性值 类型推断 可用于基本类型字段
     *
     * @param obj   对象
     * @param field 字段
     * @param value 对象值
     * @param <E>   属性类型
     */
    public static <E> void setFieldValueInfer(Object obj, Field field, E value) {
        Asserts.notNull(obj, "invoker object is null");
        Asserts.notNull(field, "invoke field is null");
        try {
            setAccessible(field);
            if (ObjectTypeStore.canConvert(value.getClass(), field.getType())) {
                field.set(obj, ObjectTypeStore.STORE.to(value, field.getType()));
                return;
            }
            throw Exceptions.invoke(StringUtils.format("could infer set field value, field: {}, class: {}", field.getName(), obj.getClass().getName()));
        } catch (Exception e) {
            throw Exceptions.invoke(StringUtils.format("set field value error: {}, field: {}, class: {}", e.getMessage(), field.getName(), obj.getClass().getName()), e);
        }
    }

    /**
     * 获取该类的所有属性列表
     *
     * @param clazz 反射类
     */
    public static Map<String, Field> getFieldMap(Class<?> clazz) {
        List<Field> fieldList = getFields(clazz);
        return Lists.isNotEmpty(fieldList) ? fieldList.stream().collect(Collectors.toMap(Field::getName, field -> field)) : Collections.emptyMap();
    }

    /**
     * 获取该类的所有属性列表 包括父类 不包括 static transient
     *
     * @param clazz 反射类
     * @return 属性
     */
    public static List<Field> getFields(Class<?> clazz) {
        Asserts.notNull(clazz, "field class is null");
        if (clazz.getSuperclass() != null) {
            List<Field> fieldList = Stream.of(clazz.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .filter(field -> !Modifier.isTransient(field.getModifiers()))
                    .collect(Collectors.toList());
            Class<?> superClass = clazz.getSuperclass();
            // 当前类属性
            Map<String, Field> fieldMap = fieldList.stream().collect(toMap(Field::getName, identity()));
            // 父类属性
            getFields(superClass).stream().filter(field -> !fieldMap.containsKey(field.getName())).forEach(fieldList::add);
            return fieldList;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 获取所有static的属性
     *
     * @param clazz 类
     * @return static属性
     */
    public static List<Field> getStaticFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isTransient(f.getModifiers()))
                .collect(toCollection(ArrayList::new));
    }

    /**
     * 获取对象的DeclaredField, 并强制设置为可访问
     *
     * @param clazz     class
     * @param fieldName 字段名称
     * @return 字段对象
     */
    public static Field getAccessibleField(Class<?> clazz, String fieldName) {
        Asserts.notNull(clazz, "field class is null");
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                setAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 设置属性可访问
     *
     * @param field 属性
     */
    public static void setAccessible(Field field) {
        Asserts.notNull(field, "set accessible field class is null");
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

}
