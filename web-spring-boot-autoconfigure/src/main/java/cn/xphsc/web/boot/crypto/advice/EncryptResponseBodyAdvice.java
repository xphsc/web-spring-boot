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
package cn.xphsc.web.boot.crypto.advice;

import cn.xphsc.web.boot.crypto.autoconfigure.CryptoProperties;
import cn.xphsc.web.crypto.annotation.Encrypt;
import cn.xphsc.web.crypto.encrypt.CryptoType;
import cn.xphsc.web.crypto.encrypt.EncodingType;
import cn.xphsc.web.utils.JacksonUtils;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.nio.charset.Charset;

import static cn.xphsc.web.crypto.encrypt.CryptoUtils.encryptOf;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 对 ResponseBody 返回的信息做加密处理
 * @since 1.0.0
 */
@RestControllerAdvice
@EnableConfigurationProperties({CryptoProperties.class})
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {

    private CryptoProperties cryptoProperties;

    EncryptResponseBodyAdvice(CryptoProperties cryptoProperties){
        this.cryptoProperties=cryptoProperties;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Annotation[] annotations = returnType.getDeclaringClass().getAnnotations();
        if (annotations != null && annotations.length > 0) {
            Annotation[] var4 = annotations;
            int var5 = annotations.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Annotation annotation = var4[var6];
                if (annotation instanceof Encrypt) {
                    return true;
                }
            }
        }
        return returnType.getMethod().isAnnotationPresent(Encrypt.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        final Charset charset = Charset.forName(cryptoProperties.getCharset());
        final EncodingType encodingType = cryptoProperties.getEncodingType();
        final CryptoProperties.Encrypt encrypts = cryptoProperties.getEncrypt()!=null?cryptoProperties.getEncrypt():null;
        CryptoType cryptoType=(encrypts!=null?encrypts.getType():null);

        final Encrypt encrypt;
        if(returnType.getDeclaringClass().isAnnotationPresent(Encrypt.class)){
            encrypt=returnType.getDeclaringClass().getAnnotation(Encrypt.class);
        }else{
           encrypt = returnType.getMethodAnnotation(Encrypt.class);

        }

        final CryptoProperties.Encrypt propertiesEncrypt = cryptoProperties.getEncrypt();
        CryptoType cryptoTypes=cryptoType!=null?cryptoType:encrypt.type();
        final String key = (encrypt!=null&&StringUtils.isNotBlank(encrypt.key()) )? encrypt.key() : propertiesEncrypt.getKey();
        String result= encryptOf(encodingType,cryptoTypes,key,JacksonUtils.toJSONString(body),charset);
        return result;
    }
}
