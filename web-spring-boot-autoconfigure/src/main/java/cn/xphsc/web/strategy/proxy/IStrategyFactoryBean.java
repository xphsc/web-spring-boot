/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.strategy.proxy;



import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.1
 */
public class IStrategyFactoryBean<T> implements FactoryBean<T> , BeanFactoryAware  , SmartApplicationListener {

    private Class<T> interfaceClass;
    private Class argType;
    private IStrategyProxy proxy;

    public IStrategyFactoryBean(Class<T> interfaceClass , Class argType ){
        this.interfaceClass = interfaceClass;
        this.argType = argType;
        proxy = new IStrategyProxy( argType );
    }

    @Override
    public T getObject() throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget( proxy );
        proxyFactory.addInterface( interfaceClass );
        proxy.init();
        return (T)proxyFactory.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        proxy.setBeanFactory( (ConfigurableListableBeanFactory) beanFactory );
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationReadyEvent.class.isAssignableFrom(eventType);
    }


    public boolean supportsSourceType(Class<?> aClass) {
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
