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
package cn.xphsc.web.boot.sensitive.autoConfiguration;


import cn.xphsc.web.sensitive.instance.InstanceSensitiveProperties;
import cn.xphsc.web.boot.sensitive.SensitiveProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import static cn.xphsc.web.common.WebBeanTemplate.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties({SensitiveProperties.class})
@ConditionalOnProperty(prefix =SENSITIVE_PREFIX, name = ENABLED , havingValue="true" , matchIfMissing = true)
public class SensitiveAutoConfiguration implements Ordered {

    private SensitiveProperties sensitiveProperties;
     SensitiveAutoConfiguration(SensitiveProperties sensitiveProperties){
         this.sensitiveProperties=sensitiveProperties;
     }

    @Bean
    public InstanceSensitiveProperties instanceSensitiveProperties(){
        InstanceSensitiveProperties properties=new InstanceSensitiveProperties();
        properties.setSensitiveProperties(sensitiveProperties);
        return properties;
    }

    @Override
    public int getOrder() {
        return sensitiveProperties.getOrder();
    }
}
