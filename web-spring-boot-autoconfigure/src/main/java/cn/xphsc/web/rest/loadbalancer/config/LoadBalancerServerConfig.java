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
package cn.xphsc.web.rest.loadbalancer.config;



import cn.xphsc.web.rest.loadbalancer.loadbalance.LoadBalancerServer;
import cn.xphsc.web.rest.loadbalancer.loadbalance.RandomLoadBalaner;
import java.util.List;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  The type Service config.
 * @since 1.0.0
 */
public class LoadBalancerServerConfig {
    /**
     * 服务列表
     */
    private List<LoadBalancerServer> serverList;

    /**
     * 负责均衡器 默认: 随机负载
     */
    private Class<?> loadBalancor = RandomLoadBalaner.class;




    /**
     * Gets server list.
     * @return the server list
     */
    public List<LoadBalancerServer> getServerList() {
        return serverList;
    }

    /**
     * Sets server list.
     * @param serverList the server list
     */
    public void setServerList(List<LoadBalancerServer> serverList) {
        this.serverList = serverList;
    }

    /**
     * Gets load balancor.
     *
     * @return the load balancor
     */
    public Class<?> getLoadBalancor() {
        return loadBalancor;
    }

    /**
     * Sets load balancor.
     * @param loadBalancor the load balancor
     */
    public void setLoadBalancor(Class<?> loadBalancor) {
        this.loadBalancor = loadBalancor;
    }

}
