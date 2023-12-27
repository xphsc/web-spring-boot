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
package cn.xphsc.web.rest.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public final class SimpleHttpRestBuilderConvert {
    private static final String SERIAL_VERSION_UID = "serialVersionUID";
    private static final Integer SUCCESS = 200;
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleHttpRestBuilderConvert.class);
    /**
     *  对象转换MultiValueMap
     * @param obj 对象
     * @param excludes 排除字段名
     * @return MultiValueMap key: 字段名，value: 值
     */
    public static  MultiValueMap<String, Object> objectToMultiMap(Object obj, List<String> excludes) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        if (obj != null) {
            Arrays.asList(obj.getClass().getDeclaredFields()).forEach(field -> {
                field.setAccessible(true);
                Object value = null;

                try {
                    value = field.get(obj);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    LOGGER.error("MultiValueMap() IllegalAccessException {}", e.getMessage());
                }

                if (value != null && !SERIAL_VERSION_UID.equals(field.getName())) {
                    map.add(field.getName(), value);
                }
            });

            if (excludes != null) {
                excludes.forEach(map::remove);
            }
        }
        return map;
    }

    public static String urlSplice(String originalUrl, Map<String, ?> params) {
        StringBuilder urlSb = new StringBuilder(originalUrl);
        if (!params.isEmpty() && params.size() > 0) {
            StringJoiner stringJoiner = new StringJoiner("&");
            params.forEach((k, v) -> {
                if (!Objects.isNull(v)){
                    stringJoiner.add(k + "=" + v);
                }
            });
            urlSb.append("?").append(stringJoiner.toString());
        }
        return urlSb.toString();
    }

    public static Integer getSUCCESS() {
        return SUCCESS;
    }
}
