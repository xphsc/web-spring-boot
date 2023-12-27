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



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  带权重的随机算法
 * @since 1.0.0
 */
public class WeightedRandomLoadBalancer extends AbstractLoadBalancer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeightedRandomLoadBalancer.class);
    private TreeMap<Double, LoadBalancerServer> weightMap = new TreeMap<>();

    /**
     * Instantiates a new Random with weight load balancer.
     * @param serverList the server list
     */
    public WeightedRandomLoadBalancer(List<LoadBalancerServer> serverList) {
        super(serverList);
        checkServerList();
        serverList.forEach(k -> {
            double lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey();
            Integer weight = k.getWeight();
            LOGGER.debug("实例列表: {}, 权重: {}", k.getUrl(), k.getWeight());
            if (weight == null) {
                LOGGER.warn("There are services without configured weights,{}", k);
                return;
            }
            if (weight <= 0) {
                return;
            }
            weightMap.put(weight + lastWeight, k);
        });
    }

    @Override
    public LoadBalancerServer getServer() {
        double randomWeight = weightMap.lastKey() * Math.random();
        NavigableMap<Double, LoadBalancerServer> doubleLbServerNavigableMap = weightMap.tailMap(randomWeight, false);
        return weightMap.get(doubleLbServerNavigableMap.firstKey());
    }
}
