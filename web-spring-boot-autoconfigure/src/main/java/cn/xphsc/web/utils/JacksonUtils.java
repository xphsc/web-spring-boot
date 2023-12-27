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

import cn.xphsc.web.common.exception.DeserializationException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class JacksonUtils {
    static ObjectMapper mapper = new ObjectMapper();

    public JacksonUtils() {
    }

    public static String toJSONString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            throw new DeserializationException(obj.getClass(), var2);
        }
    }

    public static String toJSONString(Object obj,boolean writeNullValue) {
        try {
            return writeNullValue?mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS).writeValueAsString(obj):mapper.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            throw new DeserializationException(obj.getClass(), var2);
        }
    }

        public static byte[] toJsonBytes(Object obj) {
        try {
            return ByteUtils.toBytes(mapper.writeValueAsString(obj));
        } catch (JsonProcessingException var2) {
            throw new DeserializationException(obj.getClass(), var2);
        }
    }

    public static <T> T toJsonObject(byte[] json, Class<T> cls) {
        try {
            return toJsonObject(StringUtils.newStringForUtf8(json), cls);
        } catch (Exception var3) {
            throw new DeserializationException(cls, var3);
        }
    }

    public static <T> T toJsonObject(byte[] json, Type cls) {
        try {
            return toJsonObject(StringUtils.newStringForUtf8(json), cls);
        } catch (Exception var3) {
            throw new DeserializationException(var3);
        }
    }

    public static <T> T toJsonObject(InputStream inputStream, Class<T> cls) {
        try {
            return mapper.readValue(inputStream, cls);
        } catch (IOException var3) {
            throw new DeserializationException(var3);
        }
    }

    public static <T> T toJsonObject(byte[] json, TypeReference<T> typeReference) {
        try {
            return toJsonObject(StringUtils.newStringForUtf8(json), typeReference);
        } catch (Exception var3) {
            throw new DeserializationException(var3);
        }
    }

    public static <T> T toJsonObject(String json, Class<T> cls) {
        try {
            return mapper.readValue(json, cls);
        } catch (IOException var3) {
            throw new DeserializationException(cls, var3);
        }
    }

    public static <T> T toJsonObject(String json, Class<T> cls,boolean writeNullValue) {
        try {
            if (writeNullValue){
                return mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS).readValue(json, cls);
            }else{
                return mapper.readValue(json, cls);
            }

        } catch (IOException var3) {
            throw new DeserializationException(cls, var3);
        }
    }

    public static <T> T toJsonObject(String json, Type type) {
        try {
            return mapper.readValue(json, mapper.constructType(type));
        } catch (IOException var3) {
            throw new DeserializationException(var3);
        }
    }

    public static <T> T toJsonObject(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException var3) {
            throw new DeserializationException(typeReference.getClass(), var3);
        }
    }

    public static <T> T toJsonObject(InputStream inputStream, Type type) {
        try {
            return mapper.readValue(inputStream, mapper.constructType(type));
        } catch (IOException var3) {
            throw new DeserializationException(type, var3);
        }
    }

    public static JsonNode toJsonObject(String json) {
        try {
            return mapper.readTree(json);
        } catch (IOException var2) {
            throw new DeserializationException(var2);
        }
    }

    public static void registerSubtype(Class<?> clz, String type) {
        mapper.registerSubtypes(new NamedType[]{new NamedType(clz, type)});
    }

    public static ObjectNode createEmptyJsonNode() {
        return new ObjectNode(mapper.getNodeFactory());
    }

    public static ArrayNode createEmptyArrayNode() {
        return new ArrayNode(mapper.getNodeFactory());
    }

    public static JsonNode transferToJsonNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
