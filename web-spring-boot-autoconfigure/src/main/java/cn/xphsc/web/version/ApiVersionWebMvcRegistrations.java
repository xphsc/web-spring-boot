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
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Api Version WebMvc 注册
 * @since 2.0.0
 */
public class ApiVersionWebMvcRegistrations implements WebMvcRegistrations  {
    private final ApiVersionProperties apiVersionProperties;
    private final ApplicationContext applicationContext;
    public ApiVersionWebMvcRegistrations(ApiVersionProperties apiVersionProperties, ApplicationContext applicationContext) {
        this.apiVersionProperties = apiVersionProperties;
        this.applicationContext = applicationContext;
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionRequestMappingHandlerMapping(apiVersionProperties,this.applicationContext);
    }



}
