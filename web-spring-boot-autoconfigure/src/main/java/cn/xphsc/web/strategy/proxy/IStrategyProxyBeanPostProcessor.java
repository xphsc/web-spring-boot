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


import cn.xphsc.web.strategy.IStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.1
 */

public class IStrategyProxyBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor, PriorityOrdered, BeanFactoryAware , DisposableBean {

    ConfigurableListableBeanFactory configurableListableBeanFactory;

    Map<String,InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<>();

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet(4);

    public IStrategyProxyBeanPostProcessor(){
        this.autowiredAnnotationTypes.add(Autowired.class);
        this.autowiredAnnotationTypes.add(Resource.class);
        try {
            autowiredAnnotationTypes.add((Class<? extends Annotation>) ClassUtils.forName("javax.inject.Inject", IStrategyProxyBeanPostProcessor.class.getClassLoader()));
            autowiredAnnotationTypes.add((Class<? extends Annotation>) ClassUtils.forName("jakarta.inject.Inject", IStrategyProxyBeanPostProcessor.class.getClassLoader()));
        } catch (ClassNotFoundException var2) {
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        configurableListableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition rootBeanDefinition, Class<?> beanType, String beanName ) {
        InjectionMetadata fieldMetadata = findFieldMetadata( beanName , beanType);
        fieldMetadata.checkConfigMembers( rootBeanDefinition );
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private InjectionMetadata findFieldMetadata(String beanName , Class<?> clazz ) {
        InjectionMetadata metadata = this.injectionMetadataCache.get(beanName);
        if( InjectionMetadata.needsRefresh(metadata , clazz )){
            synchronized ( this.injectionMetadataCache ){
                metadata = this.injectionMetadataCache.get( beanName );
                if( InjectionMetadata.needsRefresh( metadata , clazz )){
                    metadata = this.buildResourceMetadata( clazz );
                    this.injectionMetadataCache.put( beanName , metadata );
                }
            }
        }
        return metadata;
    }

    private InjectionMetadata buildResourceMetadata( Class<?> clazz ){
        List<InjectionMetadata.InjectedElement> elements = new ArrayList<>();
        ReflectionUtils.doWithFields( clazz , field -> {
            boolean isField = autowiredAnnotationTypes.stream().anyMatch(type -> {
                return field.isAnnotationPresent(type);
            });

            if(isField && field.getType().isAssignableFrom(IStrategy.class ) ){
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Type[] actualTypeArguments = genericType.getActualTypeArguments();
                if( actualTypeArguments.length > 0 ){
                    Class typeClz = (Class)actualTypeArguments[0];
                    elements.add(new ResourceElement(field , null ,genericType.getTypeName() , (Class)genericType.getRawType() ));
                    registerBeanDefinition( genericType.getTypeName() , (Class)genericType.getRawType() ,  typeClz );
                }
            }
        });

        return new InjectionMetadata(clazz, elements  );
    }

    private void registerBeanDefinition(String name, Class beanType , Class typeClz) {
        BeanDefinition def = BeanDefinitionBuilder.genericBeanDefinition(IStrategyFactoryBean.class)
                .addConstructorArgValue(beanType)
                .addConstructorArgValue(typeClz)
                .getBeanDefinition();
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        if( !factory.containsBeanDefinition( name ) ) {
            factory.registerBeanDefinition(name, def);
        }
    }

    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        InjectionMetadata fieldMetadata = findFieldMetadata(beanName, bean.getClass());

        try{
            fieldMetadata.inject( bean , beanName , pvs );
            return pvs;
        }catch ( Throwable e ){
            throw new BeanCreationException(beanName , "[SPI] Injection of resource dependencies failed" , e );
        }
    }

    @Override
    public void destroy() throws Exception {
        injectionMetadataCache.clear();
    }

    class ResourceElement extends InjectionMetadata.InjectedElement{

        String targetName ;
        Class targetClazz ;

        protected ResourceElement(Member member, PropertyDescriptor pd , String targetName ,  Class targetClazz) {
            super(member, pd);
            this.targetName = targetName;
            this.targetClazz = targetClazz;
        }

        @Override
        protected Object getResourceToInject(Object target, String requestingBeanName) {
            return configurableListableBeanFactory.getBean( targetName );
        }
    }

}
