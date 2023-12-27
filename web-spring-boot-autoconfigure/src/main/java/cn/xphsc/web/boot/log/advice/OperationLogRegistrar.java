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
package cn.xphsc.web.boot.log.advice;



import cn.xphsc.web.boot.log.annotation.EnableOperationLog;
import cn.xphsc.web.common.spring.BeanRegistrarUtils;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * {@link ImportBeanDefinitionRegistrar}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class OperationLogRegistrar implements ImportBeanDefinitionRegistrar  {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata
                .getAnnotationAttributes(EnableOperationLog.class.getName()));

        //优先级
        int order = attributes.getNumber("order");
        BeanDefinitionBuilder bdb = BeanRegistrarUtils.genericBeanDefinition(OperationLogPointcutAdvisor.class);
        bdb.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        bdb.addPropertyValue("order", order!=0?order:Ordered.HIGHEST_PRECEDENCE-10000);
        BeanDefinition bd = bdb.getBeanDefinition();
        String beanName = OperationLogPointcutAdvisor.class.getName();
        BeanRegistrarUtils.registerBeanDefinitionIfNotExists(registry, beanName, bd);
        AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
        AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);

    }


}
