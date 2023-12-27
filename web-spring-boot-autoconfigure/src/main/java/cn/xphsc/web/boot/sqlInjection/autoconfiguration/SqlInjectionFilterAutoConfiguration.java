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
package cn.xphsc.web.boot.sqlInjection.autoconfiguration;

import cn.xphsc.web.boot.sqlInjection.autoconfiguration.SqlInjectionProperties;
import cn.xphsc.web.boot.sqlInjection.filter.SqlInjectionFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import javax.servlet.DispatcherType;
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
@EnableConfigurationProperties({SqlInjectionProperties.class})
@ConditionalOnProperty(prefix =SQLINJECTION_PREFIX, name = ENABLED)
public class SqlInjectionFilterAutoConfiguration {
    private SqlInjectionProperties sqlInjectionProperties;
    public SqlInjectionFilterAutoConfiguration(SqlInjectionProperties sqlInjectionProperties){
        this.sqlInjectionProperties=sqlInjectionProperties;
    }
    @Bean
    public FilterRegistrationBean sqlInjectFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new SqlInjectionFilter());
        registration.addUrlPatterns(sqlInjectionProperties.getUrlPatterns());
        registration.setName(sqlInjectionProperties.getName());
        Map<String,String> initParameters = new HashMap(2);
        if(StringUtils.hasText(sqlInjectionProperties.getExcludes())){
            initParameters.put("excludes",sqlInjectionProperties.getExcludes());
        }
        initParameters.put("parameterEabled",String.valueOf(sqlInjectionProperties.isParameterEabled()));

        registration.setInitParameters(initParameters);
        registration.setOrder(sqlInjectionProperties.getOrder());
        return registration;
    }


}

