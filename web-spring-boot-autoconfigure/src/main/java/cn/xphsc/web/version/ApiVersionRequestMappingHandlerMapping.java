/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.version;

import cn.xphsc.web.boot.version.autoconfigure.ApiVersionProperties;
import cn.xphsc.web.version.annotation.ApiVersion;
import cn.xphsc.web.version.handler.RequestMappingPathPatternParserHandler;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Api Version RequestMapping Handler Mapping
 * @since 2.0.0
 */
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    private final ApiVersionProperties apiVersionProperties;
    private final static Pattern VERSION_NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+){0,2}$");
     private final ApplicationContext applicationContext;
    private RequestMappingPathPatternParserHandler requestMappingPathPatternParserHandler;
    public ApiVersionRequestMappingHandlerMapping(ApiVersionProperties apiVersionProperties, ApplicationContext applicationContext) {
        this.apiVersionProperties = apiVersionProperties;
        this.applicationContext = applicationContext;
        if(containsBean(RequestMappingPathPatternParserHandler.class)){
            requestMappingPathPatternParserHandler=this.applicationContext.getBean(RequestMappingPathPatternParserHandler.class);
        }
    }

   @Override
   protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
       return createRequestCondition(handlerType);
   }
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return createRequestCondition(method);
    }

    private RequestCondition<CustomApiVersionMethodCondition> createRequestCondition(AnnotatedElement target) {
        if (apiVersionProperties.getType() == ApiVersionProperties.Type.URI) {
            return null;
        }
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(target, ApiVersion.class);
        if (cn.xphsc.web.utils.ObjectUtils.isEmpty(apiVersion)) {
            return null;

        }
        String version = apiVersion.value().trim();
        verifyVersionNumber(version, target);
        return new CustomApiVersionMethodCondition(version, apiVersionProperties);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = this.createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = this.createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
            // 指定URL前缀
            if (apiVersionProperties.getType() == ApiVersionProperties.Type.URI) {
                ApiVersion apiVersion = AnnotationUtils.getAnnotation(method, ApiVersion.class);
                if (apiVersion == null) {
                    apiVersion = AnnotationUtils.getAnnotation(handlerType, ApiVersion.class);
                }
                if (apiVersion != null) {
                    String version = apiVersion.value().trim();
                    verifyVersionNumber(version, method);
                    String prefix = "/v" + version;
                    RequestMappingInfo.BuilderConfiguration builderConfiguration=null;
                    if(requestMappingPathPatternParserHandler!=null){
                        builderConfiguration=requestMappingPathPatternParserHandler.patternParser(this);
                    }else{
                        builderConfiguration=  new RequestMappingInfo.BuilderConfiguration();
                    }
                    if (apiVersionProperties.getUriLocation() == ApiVersionProperties.UriLocation.END) {

                            info = info.combine(RequestMappingInfo.paths(prefix).options(builderConfiguration).build());

                    } else {
                        if (StringUtils.hasText(apiVersionProperties.getUriPrefix())) {
                            prefix = apiVersionProperties.getUriPrefix().trim() + prefix;
                        }
                        try {
                            info = RequestMappingInfo.paths(prefix).options(builderConfiguration).build().combine(info);
                        } catch ( IllegalArgumentException e) {

                        }

                    }
                }
            }
        }

        return info;
    }
    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = element instanceof Class ? this.getCustomTypeCondition((Class) element) : this.getCustomMethodCondition((Method) element);
        return requestMapping != null ? this.createRequestMappingInfo(requestMapping, condition) : null;
    }
    public static void verifyVersionNumber(String version, Object targetMethodOrType) {
        if (!matchVersionNumber(version)) {
            throw new IllegalArgumentException(String.format("Invalid version : @ApiVersion(\"%s\") at %s", version, targetMethodOrType));
        }
    }

    public static boolean matchVersionNumber(String version) {
        return version.length() != 0 && VERSION_NUMBER_PATTERN.matcher(version).find();
    }
    private  boolean containsBean(Class<?> beanType) {
        try {
            this.applicationContext.getBean(beanType);
            return true;
        } catch (NoUniqueBeanDefinitionException e) {
            return true;
        } catch (NoSuchBeanDefinitionException e) {
            return false;
        }
    }

}
