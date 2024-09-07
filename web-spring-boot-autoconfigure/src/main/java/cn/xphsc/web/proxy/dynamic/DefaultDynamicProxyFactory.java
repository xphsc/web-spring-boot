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
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.8
 */
public class DefaultDynamicProxyFactory implements DynamicProxyFactory, BeanFactoryAware {

    private BeanFactory beanFactory;

    @SuppressWarnings("rawtypes,unchecked")
    @Override
    public <T> T createProxy(Class<T> dynamicInterface, DynamicProxy dynamicAnnotation) {
       AbstractInvocationDispatcher invocationDispatcher = getInvocationDispatcher(dynamicInterface, dynamicAnnotation);
        Class annotationType = invocationDispatcher.getAnnotationType();
        Annotation annotation = AnnotationUtils.getAnnotation(dynamicInterface, annotationType);
        cn.xphsc.web.proxy.dynamic.AbstractInvocationDispatcher.DynamicProxyContext<?> dynamicProxyContext = cn.xphsc.web.proxy.dynamic.AbstractInvocationDispatcher.DynamicProxyContext.valueOf(dynamicInterface, annotation);
        return (T) Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), collectProxyInterface(dynamicInterface), StubInvocationHandler.newInstance(dynamicProxyContext, invocationDispatcher));
    }

    @SuppressWarnings("rawtypes")
    private AbstractInvocationDispatcher getInvocationDispatcher(Class<?> type, DynamicProxy dynamicProxy) {
        Class<? extends AbstractInvocationDispatcher> dispatcherType = dynamicProxy.dispatcherType();
        Object handler;
        if (dispatcherType != cn.xphsc.web.proxy.dynamic.AbstractInvocationDispatcher.class) {
            handler = beanFactory.getBean(dispatcherType);
        } else {
            throw new BeanCreationException(type.getName() + " 没有指定InvocationDispatcher");
        }
        return (AbstractInvocationDispatcher) handler;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("rawtypes")
    static class StubInvocationHandler implements InvocationHandler {

        private final AbstractInvocationDispatcher.DynamicProxyContext dynamicProxyContext;
        private final AbstractInvocationDispatcher dispatcher;

        private StubInvocationHandler(AbstractInvocationDispatcher.DynamicProxyContext dynamicProxyContext, cn.xphsc.web.proxy.dynamic.AbstractInvocationDispatcher dispatcher) {
            this.dynamicProxyContext = dynamicProxyContext;
            this.dispatcher = dispatcher;
        }

        public static StubInvocationHandler newInstance(AbstractInvocationDispatcher.DynamicProxyContext dynamicProxyContext, AbstractInvocationDispatcher dispatcher) {
            return new StubInvocationHandler(dynamicProxyContext, dispatcher);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (ReflectionUtils.isToStringMethod(method)) {
                return "DynamicProxy:" + ClassUtils.classNamesToString(dynamicProxyContext.getDynamicType()) + ":" + dynamicProxyContext.getAnnotation();
            }
            if (ReflectionUtils.isEqualsMethod(method)
                    || ReflectionUtils.isHashCodeMethod(method)) {
                return method.invoke(this, args);
            }
            return dispatcher.invoke(dynamicProxyContext, proxy, method, args);
        }
    }
}
