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
import java.util.concurrent.atomic.AtomicInteger;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  the RoundRobin description.
 * @since 1.0.0
 */
public class RoundRobinLoadBalancer extends AbstractLoadBalancer {

    private AtomicInteger index = new AtomicInteger(0);

    /**
     * Instantiates a new Round robin load balancer.
     * @param serverList the server list
     */
    public RoundRobinLoadBalancer(List<LoadBalancerServer> serverList) {
        super(serverList);
    }


    @Override
    public LoadBalancerServer getServer() {
        checkServerList();
        int size = getServerList().size();
        int i = this.index.get() % size;
        this.index.compareAndSet(index.get(), i + 1);
        return getServerList().get(i);
    }
}
