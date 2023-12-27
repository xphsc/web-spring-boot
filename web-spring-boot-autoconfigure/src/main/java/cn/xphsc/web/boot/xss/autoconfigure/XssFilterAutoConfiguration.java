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
package cn.xphsc.web.boot.xss.autoconfigure;




import cn.xphsc.web.boot.xss.filter.XssFilter;
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
@EnableConfigurationProperties({XssFilterProperties.class})
@ConditionalOnProperty(
        prefix = XSS_PREFIX,
        name = ENABLED
)
public class XssFilterAutoConfiguration {
    private XssFilterProperties xssFilterProperties;
    public XssFilterAutoConfiguration(XssFilterProperties xssFilterProperties){
        this.xssFilterProperties=xssFilterProperties;
    }
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new XssFilter());
        if(StringUtils.isNotBlank(xssFilterProperties.getUrlPatterns())){
            registration.addUrlPatterns(xssFilterProperties.getUrlPatterns());
        }
        Map<String,String> initParameters = new HashMap(2);
        if(StringUtils.isNotBlank(xssFilterProperties.getExcludes())){
            initParameters.put("excludes",xssFilterProperties.getExcludes());
        }
            initParameters.put("parameterEabled",String.valueOf(xssFilterProperties.isParameterEabled()));

        registration.setInitParameters(initParameters);
        registration.setName("xssFilter");
        registration.setOrder(xssFilterProperties.getOrder());
        return registration;
    }


}
