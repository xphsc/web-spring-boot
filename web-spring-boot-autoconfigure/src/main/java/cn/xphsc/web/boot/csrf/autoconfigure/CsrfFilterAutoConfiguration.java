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
package cn.xphsc.web.boot.csrf.autoconfigure;

import cn.xphsc.web.boot.csrf.filter.CsrfFilter;
import cn.xphsc.web.boot.csrf.CsrfProperties;
import cn.xphsc.web.boot.xss.autoconfigure.XssFilterProperties;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static cn.xphsc.web.common.WebBeanTemplate.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties({CsrfProperties.class})
@ConditionalOnProperty(
        prefix = CSRF_PREFIX,
        name = ENABLED
)
public class CsrfFilterAutoConfiguration {

    private CsrfProperties csrfProperties;
    public CsrfFilterAutoConfiguration(CsrfProperties csrfProperties){
        this.csrfProperties=csrfProperties;
    }
    @Bean
    public FilterRegistrationBean csrfFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CsrfFilter());
        if(StringUtils.isNotBlank(csrfProperties.getUrlPatterns())){
            registration.addUrlPatterns(csrfProperties.getUrlPatterns());
        }
        Map<String,String> initParameters = new HashMap(2);
        if(StringUtils.isNotBlank(csrfProperties.getRefererDomain())){
            initParameters.put("refererDomain",csrfProperties.getRefererDomain());
        }
        registration.setInitParameters(initParameters);
        registration.setName(csrfProperties.getName());
        registration.setOrder(csrfProperties.getOrder());
        return registration;
    }

}
