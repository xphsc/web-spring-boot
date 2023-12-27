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






import java.security.SecureRandom;
import java.util.List;
import java.util.Random;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  the RandomLoadBalancer description.
 * @since 1.0.0
 */
public class RandomLoadBalaner extends AbstractLoadBalancer {
    private final SecureRandom random = new SecureRandom();

    /**
     * Instantiates a new Random load balaner.
     *
     * @param serverList the server list
     */
    public RandomLoadBalaner(List<LoadBalancerServer> serverList) {
        super(serverList);
    }


    @Override
    public LoadBalancerServer getServer() {
        checkServerList();
        int i = random.nextInt(1024) % getServerList().size();
        return getServerList().get(i);
    }
}
