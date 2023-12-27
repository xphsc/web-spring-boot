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
package cn.xphsc.web.common.bean;
import cn.xphsc.web.utils.ClassUtils;
import cn.xphsc.web.utils.PropertiesUtils;
import static cn.xphsc.web.utils.PropertiesUtils.getPropertyDescriptors;
import static cn.xphsc.web.utils.PropertiesUtils.setSimpleProperty;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: bean
 * @since 1.0.0
 */
public class BeanFor {

    public static boolean bean(Class<?> clazz) {
        if (ClassUtils.isNormalClass(clazz)) {
            Method[] methods = clazz.getMethods();
            Method[] arr = methods;
            int len = methods.length;

            for(int i = 0; i < len; ++i) {
                Method method = arr[i];
                if (method.getParameterTypes().length == 1 && method.getName().startsWith("set")) {
                    return true;
                }
            }
        }

        return false;
    }



    public static <T> Map<String,Object> mapFor(T entity, Map<String, ? extends Object> addProperties) {
        Map<String, Object> beanMap= beanOf(PropertiesUtils.getTarget(entity, addProperties));
        return beanMap;
    }

    public static <T> T entityFor(T entity, Map<String, ? extends Object> addProperties) {
        entity= (T) PropertiesUtils.getTarget(entity, addProperties);
        return entity;
    }


    public static Object beanOf(Object bean, Map<String, ? extends Object> properties) {
        if (bean != null && properties != null) {
            Iterator i = properties.entrySet().iterator();
            while(i.hasNext()) {
                Map.Entry<String, ? extends Object> entry = (Map.Entry)i.next();
                String name = (String)entry.getKey();
                if (name != null) {
                    setSimpleProperty(bean, name, entry.getValue(), true);
                }
            }

            return bean;
        } else {
            return null;
        }
    }

    public static Map<String, Object> beanOf(Object bean) {
        if (bean == null) {
            return new HashMap();
        } else {
            Map<String, Object> beanMap = new HashMap();
            PropertyDescriptor[] descriptors = getPropertyDescriptors(bean);
            PropertyDescriptor[] arr = descriptors;
            int len = descriptors.length;
            for(int i = 0; i < len; ++i) {
                PropertyDescriptor descriptor = arr[i];
                String name = descriptor.getName();
                Method readMethod = descriptor.getReadMethod();
                if (readMethod != null) {
                    try {
                        beanMap.put(name, readMethod.invoke(bean));
                    } catch (IllegalAccessException var10) {
                        var10.printStackTrace();
                    } catch (InvocationTargetException var11) {
                        var11.printStackTrace();
                    }
                }
            }

            return beanMap;
        }
    }

}
