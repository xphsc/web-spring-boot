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

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class PropertyUtils {


    public static void setProperty(Object bean, String propertyName, Object value) {
        try {
            PropertyUtils.setProperty(bean, propertyName, value);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static void setPropertyIfValueNotNullOrEmpty(Object bean, String propertyName, Object value) {
        if(value!=null) {
            setProperty(bean, propertyName, value);
        }

    }

    public static void setPropertyIfValueNotNull(Object bean, String propertyName, Object value) {
        if(null != value) {
            setProperty(bean, propertyName, value);
        }

    }

    public static <T> T getProperty(Object bean, String propertyName) {
        try {
            return (T) PropertyUtils.getProperty(bean, propertyName);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String getProperty(String key, String defautValue) {
        return getProperty(key, key, defautValue);
    }

    public static String getProperty(String dKey, String shellKey, String defautValue) {
        String value = System.getProperty(dKey);
        if(value == null || value.length() == 0) {
            value = System.getenv(shellKey);
            if(value == null || value.length() == 0) {
                value = defautValue;
            }
        }

        return value;
    }

}
