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
package cn.xphsc.web.boot.dicttraslate.autoconfiguration;

import cn.xphsc.web.boot.dicttraslate.advice.DictPointcutAdvisor;
import cn.xphsc.web.boot.dicttraslate.advice.DictTransMapPointcutAdvisor;
import cn.xphsc.web.dicttraslate.handler.DictTransHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import static cn.xphsc.web.common.WebBeanTemplate.*;

/**
 * {@link}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Configuration
@ConditionalOnBean(DictTransHandler.class)
@ConditionalOnProperty(prefix =DICT_PREFIX, name = ENABLED)
public class DictResponseBodyAdviceAutoConfiguration {


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DictPointcutAdvisor dictPointcutAdvisor(){
        return new DictPointcutAdvisor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DictTransMapPointcutAdvisor dictTransMapPointcutAdvisor(){
        return new DictTransMapPointcutAdvisor();
    }


}
