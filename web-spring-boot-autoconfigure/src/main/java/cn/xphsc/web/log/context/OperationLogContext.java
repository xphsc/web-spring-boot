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
package cn.xphsc.web.log.context;

import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Operation log context
 * For reference, the example
 * @SysOperationLog(content="#attribute") OperationLogContext.putVariable("attribute",Original data object or value)
 * or  Modify data comparison OperationLogContext.putVariable(Original data object or value);
 * @since 1.0.0
 */
public class OperationLogContext {

    private static ThreadLocal<Map<String, Object>> optContextHolder = new ThreadLocal<>();


    public static void putVariable(Object objectVariable) {
        if (objectVariable == null) {
            return;
        }
        Map<String, Object> map = optContextHolder.get();
        if (map == null) {
            map = new HashMap<>(1);
        }
        map.put("putVariable", objectVariable);
        optContextHolder.set(map);
    }

    @Deprecated
    public static void originObject(Object originObject) {
        if (originObject == null) {
            return;
        }
        Map<String, Object> map = optContextHolder.get();
        if (map == null) {
            map = new HashMap<>(1);
        }
        map.put("originObject", originObject);
        optContextHolder.set(map);
    }


    public static void putVariable(String key, Object value) {
        if (key == null) {
            return;
        }
        Map<String, Object> map = optContextHolder.get();
        if (map == null) {
            map = new HashMap<>(16);
        }
        map.put(key, value);
        optContextHolder.set(map);
    }

    public static void putFailVariable(Object failVariableValue) {
        if (failVariableValue == null) {
            return;
        }
        Map<String, Object> map = optContextHolder.get();
        if (map == null) {
            map = new HashMap<>(1);
        }
        map.put("failVariableValue", failVariableValue);
        optContextHolder.set(map);
    }

    public static Object get(String key) {
        if (key == null) {
            return null;
        }
        Map<String, Object> map = optContextHolder.get();
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    public static void clean() {
        optContextHolder.remove();
    }

    public static Map<String, Object> getCopyOfContextMap() {
        Map<String, Object> map = optContextHolder.get();
        if (map == null) {
            return null;
        } else {
            return new HashMap<>(map);
        }
    }
}
