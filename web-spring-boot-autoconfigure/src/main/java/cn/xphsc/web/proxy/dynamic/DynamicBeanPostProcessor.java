
/*
 * Copyright (c) 2023 huipei.x
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
package cn.xphsc.web.proxy.dynamic;


import cn.xphsc.web.proxy.annotation.DynamicProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import java.beans.PropertyDescriptor;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.8
 */
public  class DynamicBeanPostProcessor   implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    @SuppressWarnings("all")
    public Object postProcessBeforeInstantiation(Class<?> type, String name) throws BeansException {
        DynamicProxy proxy = AnnotationUtils.getAnnotation(type, DynamicProxy.class);
        if (proxy == null) {
            return null;
        }
        if (!type.isInterface()) {
            throw new BeanCreationNotAllowedException(name, type.getName() + " 不是Interface");
        }
        DynamicProxyFactory dynamicProxyFactory = getDynamicProxyFactory(proxy);
        return dynamicProxyFactory.createProxy(type, proxy);
    }


    public boolean postProcessAfterInstantiation(Object o, String s) throws BeansException {
        return true;
    }


    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, PropertyDescriptor[] propertyDescriptors, Object o, String s) throws BeansException {
        return  propertyValues;
    }

    private DynamicProxyFactory getDynamicProxyFactory(DynamicProxy dynamicProxy) {
        Class<? extends DynamicProxyFactory> factoryType = dynamicProxy.factoryType();
        return beanFactory.getBean(factoryType);
    }


    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }


    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}

