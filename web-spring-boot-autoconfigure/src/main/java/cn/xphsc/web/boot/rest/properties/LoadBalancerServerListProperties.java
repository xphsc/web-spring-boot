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
package cn.xphsc.web.boot.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.HashMap;
import java.util.Map;
import static cn.xphsc.web.common.WebBeanTemplate.REST_LOADBALANCER_PREFIX;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = REST_LOADBALANCER_PREFIX)
public class LoadBalancerServerListProperties {


    private Map<String, ServiceProperties> services = new HashMap<>();

    public Map<String, ServiceProperties> getServices() {
        return services;
    }

    public void setServices(Map<String, ServiceProperties> services) {
        this.services = services;
    }
}
