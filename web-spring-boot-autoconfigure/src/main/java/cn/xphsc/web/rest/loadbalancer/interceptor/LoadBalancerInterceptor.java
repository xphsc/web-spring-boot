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
package cn.xphsc.web.rest.loadbalancer.interceptor;

import cn.xphsc.web.rest.loadbalancer.loadbalance.LoadBalancer;
import cn.xphsc.web.rest.loadbalancer.loadbalance.LoadBalancerServer;
import cn.xphsc.web.rest.loadbalancer.loadbalance.RandomLoadBalaner;
import cn.xphsc.web.rest.loadbalancer.config.LoadBalancerServerConfig;
import cn.xphsc.web.rest.loadbalancer.uri.UriBuilder;
import cn.xphsc.web.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class LoadBalancerInterceptor  implements ClientHttpRequestInterceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoadBalancerInterceptor.class);

    private final static Object LOCK = new Object();

    private static final Map<String, LoadBalancer> serviceLoadBancorList = new HashMap<>();

    private Map<String, LoadBalancerServerConfig> serviceList = new HashMap<>();

    /**
     * Instantiates a new Load balancer interceptor.
     *
     * @param serviceList the service list
     */
    public LoadBalancerInterceptor(Map<String, LoadBalancerServerConfig> serviceList) {
        this.serviceList = serviceList;
    }

    /**
     * Gets loadbalancer.
     * @param serviceId the service id
     * @return the loadbalancer
     */
    public LoadBalancer getLoadbalancer(String serviceId) {
        if (serviceLoadBancorList.containsKey(serviceId)) {
            return serviceLoadBancorList.get(serviceId);
        }
        synchronized (LOCK) {
            if (serviceLoadBancorList.containsKey(serviceId)) {
                return serviceLoadBancorList.get(serviceId);
            }
            LoadBalancerServerConfig serviceConfig = serviceList.get(serviceId);
            LoadBalancer newInstance = null;
            try {
                Constructor<?> constructor = serviceConfig.getLoadBalancor().getConstructor(List.class);
                newInstance = (LoadBalancer) constructor.newInstance(serviceConfig.getServerList());
                serviceLoadBancorList.put(serviceId, newInstance);
                return newInstance;
            } catch (Exception e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("create loadbalancer instance failed, " + e);
                }
                if(serviceList.get(serviceId)!=null){
                    newInstance = new RandomLoadBalaner(serviceList.get(serviceId).getServerList());
                }
            }
            serviceLoadBancorList.put(serviceId, newInstance);
            return newInstance;
        }
    }



    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        LoadBalancerHttpReqeustWrapper reqeustWrapper = new LoadBalancerHttpReqeustWrapper(httpRequest);
            return clientHttpRequestExecution.execute(reqeustWrapper, bytes);
    }

    private ClientHttpResponse proc( LoadBalancerHttpReqeustWrapper reqeustWrapper, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        ClientHttpResponse execute = clientHttpRequestExecution.execute(reqeustWrapper, bytes);
            return execute;


    }

    /**
     * The type Load balancer http reqeust wrapper.
     */
    public class LoadBalancerHttpReqeustWrapper extends HttpRequestWrapper {

        private LoadBalancerServer choosedServer = null;

        private String serviceId = StringUtils.EMPTY;

        /**
         * Gets choosed server.
         * @return the choosed server
         */
        public LoadBalancerServer getChoosedServer() {
            return choosedServer;
        }

        /**
         * Gets service id.
         * @return the service id
         */
        public String getServiceId() {
            if (StringUtils.isEmpty(this.serviceId)) {
                URI oldUri = super.getURI();
                this.serviceId = oldUri.getHost();
            }
            return serviceId;
        }

        /**
         * Instantiates a new Load balancer http reqeust wrapper.
         * @param request the request
         */
        public LoadBalancerHttpReqeustWrapper(HttpRequest request) {
            super(request);
        }


        @Override
        public URI getURI() {
            URI oldUri = super.getURI();
            this.serviceId = oldUri.getHost();
            LoadBalancer loadBancor = getLoadbalancer(this.serviceId);
            if(loadBancor!=null){
                LoadBalancerServer server = loadBancor.getServer();
                choosedServer = server;
                try {
                    return UriBuilder.replaceHost(oldUri, server.getUrl());
                } catch (URISyntaxException e) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(String.format("replace host fail with error: %s", e));
                    }
                    return oldUri;
                }
            }
            return oldUri;
        }
    }
}
