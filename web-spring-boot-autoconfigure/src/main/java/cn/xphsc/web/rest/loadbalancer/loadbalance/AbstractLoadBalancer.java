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
package cn.xphsc.web.rest.loadbalancer.loadbalance;





import java.util.List;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Abstract LoadBalaner
 * @since 1.0.0
 */
public abstract class AbstractLoadBalancer implements LoadBalancer {

    /**
     * Instantiates a new Abstract load balancer.
     *
     * @param serverList the server list
     */
    public AbstractLoadBalancer(List<LoadBalancerServer> serverList) {
        this.serverList = serverList;
    }

    private final List<LoadBalancerServer> serverList;

    /**
     * Gets server list.
     *
     * @return the server list
     */
    public List<LoadBalancerServer> getServerList() {
        return serverList;
    }

    /**
     * check server node list
     *
     * @return boolean boolean
     */
    protected boolean checkServerList() {
        if (serverList == null || serverList.size() <= 0) {
            throw new RuntimeException("No list of available services");
        }
        return true;
    }

    @Override
    public abstract LoadBalancerServer getServer();
}
