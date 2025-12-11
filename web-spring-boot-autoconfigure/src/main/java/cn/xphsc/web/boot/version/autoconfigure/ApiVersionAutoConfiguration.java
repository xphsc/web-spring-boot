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
package cn.xphsc.web.boot.version.autoconfigure;

import cn.xphsc.web.version.ApiVersionWebMvcRegistrations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.xphsc.web.common.WebBeanTemplate.*;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.0
 */
@Configuration
@EnableConfigurationProperties({ApiVersionProperties.class})
@ConditionalOnProperty(
        prefix = APIVERSION_PREFIX,
        name = ENABLED
)
public class ApiVersionAutoConfiguration {
    private final ApiVersionProperties apiVersionProperties;
    private final ApplicationContext applicationContext;

    public ApiVersionAutoConfiguration(ApiVersionProperties apiVersionProperties, ApplicationContext applicationContext) {
        this.apiVersionProperties = apiVersionProperties;
        this.applicationContext = applicationContext;
    }

    @Bean
   @ConditionalOnMissingBean
    public ApiVersionWebMvcRegistrations apiVersionWebMvcRegistrations() {
        return new ApiVersionWebMvcRegistrations(this.apiVersionProperties,this.applicationContext);
    }

}
