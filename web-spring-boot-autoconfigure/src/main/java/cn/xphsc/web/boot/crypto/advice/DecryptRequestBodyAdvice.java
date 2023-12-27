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
import cn.xphsc.web.crypto.annotation.Decrypt;
import cn.xphsc.web.crypto.encrypt.CryptoType;
import cn.xphsc.web.crypto.encrypt.EncodingType;
import cn.xphsc.web.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import static cn.xphsc.web.crypto.encrypt.CryptoUtils.decryptOf;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 对 RequestBody 中加密信息做解密处理
 * @since 1.0.0
 */
@RestControllerAdvice
@EnableConfigurationProperties({CryptoProperties.class})
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {
    private CryptoProperties cryptoProperties;
    public  DecryptRequestBodyAdvice(CryptoProperties cryptoProperties){
        this.cryptoProperties=cryptoProperties;
    }
    private final Log log = LogFactory.getLog(DecryptRequestBodyAdvice.class);


    private static final String MARK = "\"";


    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Annotation[] annotations = methodParameter.getDeclaringClass().getAnnotations();
        if (annotations != null && annotations.length > 0) {
            Annotation[] var4 = annotations;
            int var5 = annotations.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Annotation annotation = var4[var6];
                if (annotation instanceof Decrypt) {
                    return true;
                }
            }
        }

        return methodParameter.getMethod().isAnnotationPresent(Decrypt.class);
    }


    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        final InputStream inputStream = inputMessage.getBody();
        try {
            final Charset charset = Charset.forName(cryptoProperties.getCharset());
            final EncodingType encodingType = cryptoProperties.getEncodingType();

            final CryptoProperties.Decrypt decrypts = cryptoProperties.getDecrypt()!=null?cryptoProperties.getDecrypt():null;
            CryptoType cryptoType=(decrypts!=null?decrypts.getType():null);
            final Decrypt decrypt;
            if(parameter.getDeclaringClass().isAnnotationPresent(Decrypt.class)){
                decrypt=parameter.getDeclaringClass().getAnnotation(Decrypt.class);
            }else{
                 decrypt = parameter.getMethod().getAnnotation(Decrypt.class);

            }
            final CryptoProperties.Decrypt propertiesDecrypt = cryptoProperties.getDecrypt();
            final String key = (decrypt!=null&&StringUtils.isNotBlank(decrypt.key())) ? decrypt.key() : propertiesDecrypt.getKey();
            CryptoType cryptoTypes=cryptoType!=null?cryptoType:decrypt.type();
             ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            final String content = result.toString(StandardCharsets.UTF_8.name());
            String decryptContent=null;
            decryptContent=decryptOf(encodingType,cryptoTypes,key,content,charset);
            if (decryptContent.indexOf(MARK) == 0 && decryptContent.lastIndexOf(MARK) == decryptContent.length() - 1) {
                decryptContent = StringUtils.substringBetween(decryptContent, MARK, MARK);
            }
            InputStream  is = new ByteArrayInputStream(decryptContent.getBytes());

            return new MappingJacksonInputMessage(is, inputMessage.getHeaders());
        } catch (IOException e) {
            log.error("格式转换错误", e);
            throw new IOException("格式转换错误)");
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
