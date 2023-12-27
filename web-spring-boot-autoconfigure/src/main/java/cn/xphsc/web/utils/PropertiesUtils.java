/*
 * Copyright (c) 2021 huipei.x
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
package cn.xphsc.web.utils;


import cn.xphsc.web.common.bean.BeanOfMap;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class PropertiesUtils {


    public static Object getTarget(Object target, Map<String, ? extends Object> addProperties) {

        PropertyDescriptor[] descriptors =getPropertyDescriptors(target);
        Map<String, Class> propertyMap = new HashMap();
        for (PropertyDescriptor d : descriptors) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        // add extra properties
        addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
        // new dynamic bean
        BeanOfMap dynamicBean = new BeanOfMap(target.getClass(), propertyMap);
        // add old value
        propertyMap.forEach((k, v) -> {
            try {
                // filter extra properties
                if (!addProperties.containsKey(k)) {
                   dynamicBean.setValue(k, getSimpleProperty(target,k));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // add extra value
        addProperties.forEach((k, v) -> {
            try {
                dynamicBean.setValue(k, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Object result = dynamicBean.getTarget();
        return result;
    }


    /**
     * 通过Get方法获取属性的值
     *
     * @param bean
     * @param name
     * @return java.lang.Object
     */
    public static Object getSimpleProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        }
        if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" +
                    bean.getClass() + "'");
        }

        // Retrieve the property getter method for the specified property
        final PropertyDescriptor descriptor =
                getPropertyDescriptor(bean, name);
        if (descriptor == null) {
            throw new NoSuchMethodException("Unknown property '" +
                    name + "' on class '" + bean.getClass() + "'" );
        }
        final Method readMethod = descriptor.getReadMethod();
        if (readMethod == null) {
            throw new NoSuchMethodException("Property '" + name +
                    "' has no getter method in class '" + bean.getClass() + "'");
        }

        // Call the property getter and return the value
        final Object value = readMethod.invoke(bean);
        return (value);
    }

    /**
     * 通过Set方法设置属性的值
     *
     * @param bean
     * @param name
     * @param value
     * @param ignore 是否忽略不合法的属性
     * @return void
     */
    public static void setSimpleProperty(final Object bean, String name, final Object value, boolean ignore)
    {
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        }
        if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" +
                    bean.getClass() + "'");
        }

        final PropertyDescriptor descriptor =
                getPropertyDescriptor(bean, name);
        if (descriptor != null) {
            final Method writeMethod = descriptor.getWriteMethod();
            if (writeMethod != null) {
                final Object[] values = new Object[1];
                values[0] = value;
                try {
                    writeMethod.invoke(bean, values);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else if(!ignore) {
                try {
                    throw new NoSuchMethodException("Property '" + name +
                            "' has no setter method in class '" + bean.getClass() + "'");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        } else if(!ignore) {
            try {
                throw new NoSuchMethodException("Unknown property '" +
                        name + "' on class '" + bean.getClass() + "'" );
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取bean的所有有效属性描述
     *
     * @param bean
     * @return java.beans.PropertyDescriptor[]
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Object bean) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(bean.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null) {
            descriptors = new PropertyDescriptor[0];
        }
        return descriptors;
    }

    /**
     * 获取bean的指定有效属性描述
     *
     * @param bean
     * @param name
     * @return java.beans.PropertyDescriptor
     */
    public static PropertyDescriptor getPropertyDescriptor(Object bean, String name) {
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(bean);
        for (final PropertyDescriptor pd : propertyDescriptors) {
            if (name.equals(pd.getName())) {
                return pd;
            }
        }
        return null;
    }





}
