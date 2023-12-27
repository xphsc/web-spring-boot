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


import cn.xphsc.web.boot.rest.properties.LoadBalancerServerListProperties;
import cn.xphsc.web.boot.rest.properties.ServiceProperties;
import cn.xphsc.web.rest.annotation.LoadBalanced;
import cn.xphsc.web.rest.loadbalancer.config.LoadBalancerServerConfig;
import cn.xphsc.web.rest.loadbalancer.loadbalance.LoadBalancerServer;
import cn.xphsc.web.rest.loadbalancer.interceptor.LoadBalancerInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;
import static cn.xphsc.web.common.WebBeanTemplate.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: RestTemplate LoadBalancer 自动配置
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix =REST_LOADBALANCER_PREFIX, name = ENABLED)
@EnableConfigurationProperties(LoadBalancerServerListProperties.class)
public class RestLoadBalancerAutoConfiguration {

    public LoadBalancerServerListProperties loadBalancerServerListProperties;
    @LoadBalanced
    @Autowired(required = false)
    private List<RestTemplate> restTemplates = Collections.emptyList();
    public RestLoadBalancerAutoConfiguration(LoadBalancerServerListProperties loadBalancerServerListProperties){
        this.loadBalancerServerListProperties=loadBalancerServerListProperties;

    }



    /**
     * Service config map.
     */
    @Bean
    public Map<String, LoadBalancerServerConfig> loadBalancerServer() {
        Map<String, LoadBalancerServerConfig> serviceConfigMap = new HashMap<>();
        Map<String, ServiceProperties> serviceList = loadBalancerServerListProperties.getServices();
        if (serviceList == null || serviceList.isEmpty()) {
            return serviceConfigMap;
        }
        serviceList.forEach((k, v) -> {
            LoadBalancerServerConfig serviceConfig = new LoadBalancerServerConfig();
            serviceConfig.setServerList(v.getServerList().stream().map(c -> {
                LoadBalancerServer lbServer = new LoadBalancerServer();
                lbServer.setProperties(c.getProperties());
                lbServer.setUrl(c.getUrl());
                lbServer.setWeight(c.getWeight());
                return lbServer;
            }).collect(Collectors.toList()));

            serviceConfig.setLoadBalancor(v.getLoadBalancor());
            serviceConfigMap.put(k, serviceConfig);
        });
        return serviceConfigMap;
    }

    /**
     * Load balancer interceptor load balancer interceptor.
     */
    @Bean
    public LoadBalancerInterceptor loadBalancerInterceptor(Map<String, LoadBalancerServerConfig> LoadBalancerServerMap) {
        return new LoadBalancerInterceptor(LoadBalancerServerMap);
    }

    @Bean
    public SmartInitializingSingleton loadBalancedRestTemplateInitializerDeprecated(
            final ObjectProvider<List<RestTemplateCustomizer>> restTemplateCustomizers) {
        SmartInitializingSingleton smartInitializingSingleton= new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                Iterator restTemplateIterator = restTemplates.iterator();
                while(restTemplateIterator.hasNext()) {
                    RestTemplate restTemplate = (RestTemplate)restTemplateIterator.next();
                    Iterator restTemplateCustomizerIterator = restTemplateCustomizers.getIfAvailable().iterator();
                    while(restTemplateCustomizerIterator.hasNext()) {
                        // 利用上面的RestTemplateCustomizer给RestTemplate们加拦截器
                        RestTemplateCustomizer customizer = (RestTemplateCustomizer)restTemplateCustomizerIterator.next();
                        customizer.customize(restTemplate);
                    }
                }
            }
        };
        return smartInitializingSingleton;
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplateCustomizer restTemplateCustomizer(
            final LoadBalancerInterceptor loadBalancerInterceptor) {
        return restTemplate -> {
            List<ClientHttpRequestInterceptor> list = new ArrayList<>(
                    restTemplate.getInterceptors());
            list.add(loadBalancerInterceptor);
            restTemplate.setInterceptors(list);
        };
    }

}
