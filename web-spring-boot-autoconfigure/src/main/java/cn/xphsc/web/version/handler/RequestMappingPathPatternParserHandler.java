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
package cn.xphsc.web.version.handler;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Api  Version  Handler
 * Support Spring Boot 2.6 and above versions
 * For reference, the example
 * @Component
 * public class ApiVersionRequestMappingPathPatternParserHandler implements RequestMappingPathPatternParserHandler{
 *  @Override
 * public RequestMappingInfo.BuilderConfiguration patternParser(RequestMappingHandlerMapping requestMappingHandlerMapping) {
 *  RequestMappingInfo.BuilderConfiguration builderConfiguration=new RequestMappingInfo.BuilderConfiguration();
 *  builderConfiguration.setPatternParser(requestMappingHandlerMapping.getPatternParser());
 *  return builderConfiguration;}}
 * @since 2.0.0
 */
public interface RequestMappingPathPatternParserHandler {

    /**  Support Spring Boot 2.6 and above versions
     * return RequestMappingInfo.BuilderConfiguration;;
     * @param requestMappingHandlerMapping
     * @return
     */
    RequestMappingInfo.BuilderConfiguration patternParser(RequestMappingHandlerMapping requestMappingHandlerMapping);
}
