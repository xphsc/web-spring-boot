/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.boot.rest.autoconfigure;


import cn.xphsc.web.boot.rest.properties.RestBuilderTemplateProperties;
import cn.xphsc.web.rest.RestBuilderTemplate;
import cn.xphsc.web.rest.annotation.LoadBalanced;
import cn.xphsc.web.rest.http.HttpClientHttpRequestFactory;
import cn.xphsc.web.rest.http.HttpMessageConverterBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

import static cn.xphsc.web.common.WebBeanTemplate.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: RestBuilderTemplate 自动配置
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix =REST_PREFIX, name = ENABLED)
@EnableConfigurationProperties(RestBuilderTemplateProperties.class)
public class RestBuilderTemplateAutoConfiguration {

    public RestBuilderTemplateProperties restBuilderTemplateProperties;
    public RestBuilderTemplateAutoConfiguration(RestBuilderTemplateProperties restBuilderTemplateProperties){
        this.restBuilderTemplateProperties=restBuilderTemplateProperties;

    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate= new RestTemplate();
        restTemplate.setRequestFactory(factory());
        new HttpMessageConverterBuilder(restTemplate,restBuilderTemplateProperties.getCharset());
        return restTemplate;
    }

    @Bean
     public RestBuilderTemplate restBuilderTemplate(){
        return new RestBuilderTemplate(restTemplate());
    }

      @Bean
      public HttpClientHttpRequestFactory factory() {
        return new HttpClientHttpRequestFactory (){
            {
                setReadTimeout(restBuilderTemplateProperties.getReadTimeout());
                setConnectTimeout(restBuilderTemplateProperties.getConnectionTimeout());
                ignoreCertificate(restBuilderTemplateProperties.isIgnoreCertificate());
            }
        };
    }


}
