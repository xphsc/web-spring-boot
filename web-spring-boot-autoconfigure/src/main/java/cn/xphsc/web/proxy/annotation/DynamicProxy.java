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
package cn.xphsc.web.proxy.annotation;



import cn.xphsc.web.proxy.dynamic.AbstractInvocationDispatcher;
import cn.xphsc.web.proxy.dynamic.DefaultDynamicProxyFactory;
import cn.xphsc.web.proxy.dynamic.DynamicProxyFactory;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import java.lang.annotation.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.8
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface DynamicProxy {
    /**
     * 指定beanName
     *
     * @return
     */
    @AliasFor(annotation = Component.class, attribute = "value")
    String beanName() default "";

    /**
     * 指定动态代理工厂,用于定制动态代理方案比如Cglib、ByteBuddy等
     * 默认使用基于JdkDynamicProxy代理并使用AbstractInvocationDispatcher拦截方法调用
     */
    Class<? extends DynamicProxyFactory> factoryType() default DefaultDynamicProxyFactory.class;

    /**
     * 指定JdkDynamicProxy调用拦截器BeanType,用于从BeanFactory中按类型获取bean
     */
    @AliasFor("dispatcherType")
    Class<? extends AbstractInvocationDispatcher> value() default AbstractInvocationDispatcher.class;

    /**
     * 指定JdkDynamicProxy调用拦截器BeanType,用于从BeanFactory中按类型获取bean
     */
    @AliasFor("value")
    Class<? extends AbstractInvocationDispatcher> dispatcherType() default AbstractInvocationDispatcher.class;
}
