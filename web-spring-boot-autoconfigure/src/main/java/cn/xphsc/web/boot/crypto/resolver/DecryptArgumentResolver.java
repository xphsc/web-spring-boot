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
package cn.xphsc.web.boot.crypto.resolver;


import cn.xphsc.web.boot.crypto.autoconfigure.CryptoProperties;
import cn.xphsc.web.crypto.annotation.Decrypt;
import cn.xphsc.web.crypto.encrypt.CryptoType;
import cn.xphsc.web.crypto.encrypt.CryptoUtils;
import cn.xphsc.web.crypto.encrypt.EncodingType;
import cn.xphsc.web.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.4
 */
public class DecryptArgumentResolver implements HandlerMethodArgumentResolver {

    private final CryptoProperties cryptoProperties;
    private final Log log = LogFactory.getLog(DecryptArgumentResolver.class);

    public DecryptArgumentResolver(CryptoProperties cryptoProperties) {
        this.cryptoProperties = cryptoProperties;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 支持方法或类级别有 @Decrypt 注解的参数
        return parameter.hasParameterAnnotation(Decrypt.class) ||
                parameter.getMethod().isAnnotationPresent(Decrypt.class) ||
                parameter.getDeclaringClass().isAnnotationPresent(Decrypt.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String paramName = parameter.getParameterName();
        String encryptedValue = webRequest.getParameter(paramName);

        if (StringUtils.isBlank(encryptedValue)) {
            return null;
        }
        // 获取解密配置
        Decrypt decrypt = parameter.getParameterAnnotation(Decrypt.class);
        if (decrypt == null) {
            decrypt = parameter.getMethod().getAnnotation(Decrypt.class);
        }
        if (decrypt == null) {
            decrypt = parameter.getDeclaringClass().getAnnotation(Decrypt.class);
        }

        try {
            final Charset charset = Charset.forName(cryptoProperties.getCharset());
             EncodingType encodingType = cryptoProperties.getEncodingType();
            final CryptoProperties.Decrypt propertiesDecrypt = cryptoProperties.getDecrypt();

            final String key = (decrypt != null && StringUtils.isNotBlank(decrypt.key())) ?
                    decrypt.key() : propertiesDecrypt.getKey();
            CryptoType cryptoType = propertiesDecrypt != null ? propertiesDecrypt.getType() : CryptoType.AES;
            if (decrypt != null) {
                cryptoType = decrypt.type();
            }
            String decryptContent = CryptoUtils.decryptOf(encodingType, cryptoType, key, encryptedValue, charset);
            // 处理字符串引号
            if (decryptContent.indexOf("\"") == 0 && decryptContent.lastIndexOf("\"") == decryptContent.length() - 1) {
                decryptContent = StringUtils.substringBetween(decryptContent, "\"", "\"");
            }
            // 类型转换
            return convertParameterValue(decryptContent, parameter.getParameterType());
        } catch (Exception e) {
            log.error("参数解密失败: " + paramName, e);
            throw new RuntimeException("参数解密失败: " + paramName, e);
        }
    }

    private Object convertParameterValue(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.valueOf(value);
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.valueOf(value);
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.valueOf(value);
        } else if (targetType == Float.class || targetType == float.class) {
            return Float.valueOf(value);
        } else if (targetType == Short.class || targetType == short.class) {
            return Short.valueOf(value);
        } else if (targetType == Byte.class || targetType == byte.class) {
            return Byte.valueOf(value);
        } else if (targetType == Character.class || targetType == char.class) {
            return value.length() > 0 ? value.charAt(0) : null;
        } else if (targetType == BigDecimal.class) {
            return new BigDecimal(value);
        } else if (targetType == BigInteger.class) {
            return new BigInteger(value);
        } else if (targetType.isEnum()) {
            Class<? extends Enum> enumClass = (Class<? extends Enum>) targetType;
            return Enum.valueOf(enumClass, value);
        }
        return value;
    }
}