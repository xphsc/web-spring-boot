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
package cn.xphsc.web.boot.cors;

import cn.xphsc.web.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static cn.xphsc.web.common.WebBeanTemplate.*;


/**
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Cors 跨域支持
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {CorsFilterProperties.class})
@ConditionalOnProperty(prefix =CORS_PREFIX, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class CorsFilterAutoConfiguration {

    private static final String PATH = "/**";
    private final CorsFilterProperties properties;

    @Autowired
    public CorsFilterAutoConfiguration(CorsFilterProperties properties) {
        this.properties = properties;
    }


    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(StringUtils.defaultString(properties.getOrigin(), CorsConfiguration.ALL));
        corsConfiguration.addAllowedHeader(StringUtils.defaultString(properties.getAllowedHeader(), CorsConfiguration.ALL));
        corsConfiguration.addAllowedMethod(StringUtils.defaultString(properties.getMethod(), CorsConfiguration.ALL));
        // 是否发送 Cookie 信息
        corsConfiguration.setAllowCredentials(properties.getAllowCredentials());
        if (properties.getMaxAge() != null) {
            corsConfiguration.setMaxAge(properties.getMaxAge());
        }
        if (properties.getExposedHeader() != null) {
            corsConfiguration.addExposedHeader(properties.getExposedHeader());
        }
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     *
     * @return Cors过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(StringUtils.defaultString(properties.getPath(), PATH), buildConfig());
        return new CorsFilter(source);
    }

}
