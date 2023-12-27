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
package cn.xphsc.web.boot.response.interceptor;




import cn.xphsc.web.response.annotation.ResponseResult;
import cn.xphsc.web.common.response.Response;
import cn.xphsc.web.common.response.ResponseCode;
import cn.xphsc.web.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.lang.annotation.Annotation;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object>  {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 请求中是否包含了 响应需要被包装的标记，如果没有，则直接返回，不需要重写返回体
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Annotation[] annotations = methodParameter.getDeclaringClass().getAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof ResponseResult ){
                    return true;
                }
            }
        }
        return methodParameter.getMethod().isAnnotationPresent(ResponseResult.class) ;
    }


    /**
     * 对 响应体 进行包装; 除此之外还可以对响应体进行统一的加密、签名等
     * @param responseBody       请求的接口方法执行后得到返回值(返回响应)
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object responseBody, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(logger.isDebugEnabled()){
            logger.debug("返回响应 包装进行中。。。");
        }
       Response response = null;
        // boolean类型时判断一些数据库新增、更新、删除的操作是否成功
        if (responseBody instanceof Boolean) {
            if ((Boolean) responseBody) {
                response = Response.ok(responseBody);
            }

        }  else if (responseBody instanceof String) {
            return JacksonUtils.toJSONString(Response.ok(responseBody));
        } else if (responseBody instanceof ResponseEntity || responseBody instanceof Response) {
            return responseBody;
        }else if (aClass.isAssignableFrom(StringHttpMessageConverter.class)) {
            return responseBody;
        } else {
            response = Response.ok(responseBody);
        }
        return response;
    }


}
