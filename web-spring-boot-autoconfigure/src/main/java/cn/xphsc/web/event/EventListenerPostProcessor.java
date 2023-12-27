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
package cn.xphsc.web.event;

import cn.xphsc.web.event.annotation.EventListener;
import cn.xphsc.web.event.entity.EventType;
import cn.xphsc.web.event.publisher.EventPublishListener;
import cn.xphsc.web.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class EventListenerPostProcessor implements BeanFactoryPostProcessor {
    private final static Logger logger = LoggerFactory.getLogger(EventListenerPostProcessor.class);
    private static final Pattern skipPackagesPattern = Pattern.compile("^org\\.apache\\..*|^org\\.hibernate\\..*|^org\\.springframework\\..*");
    private Map<String, Map<String, List<Method>>> registry = new HashMap<>(8);
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        long start = System.currentTimeMillis();
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (int i = 0; i < beanNames.length; i++) {
            String beanName = beanNames[i];
            BeanDefinition beanDefinition;
            try {
                beanDefinition = beanFactory.getBeanDefinition(beanName);
            } catch (NoSuchBeanDefinitionException e) {
                continue;
            }
            String beanClassName = beanDefinition.getBeanClassName();
            if (StringUtils.isEmpty(beanClassName) || skipPackagesPattern.matcher(beanClassName).matches()) {
                continue;
            }
            Class beanClass;
            try {
                beanClass = Class.forName(beanFactory.getBeanDefinition(beanName).getBeanClassName());
            } catch (ClassNotFoundException var23) {
                throw new RuntimeException(var23);
            }

            Class targetClass = beanClass;
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(targetClass);
            for (Method method : methods) {
                AnnotationAttributes annotationAttributes = AnnotatedElementUtils
                        .findMergedAnnotationAttributes(method, EventListener.class, false, false);
                if (null != annotationAttributes) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1) {
                        String name = (String) annotationAttributes.get("name");
                        String outerKey=annotationAttributes.getClass("type").getSimpleName();
                        EventType eventType= (EventType) annotationAttributes.get("eventType");
                        if(ObjectUtils.isNotEmpty(eventType)) {
                            switch (eventType) {
                                case SYNC:
                                    outerKey = "SyncEventListener";
                                    break;
                                case ASYNC:
                                    outerKey = "AsyncEventListener";
                                    break;
                                default:
                            }
                        }
                        Map<String, List<Method>> methodHashMap = registry.getOrDefault(outerKey, new HashMap<>());
                        if (!methodHashMap.containsKey(name)) {
                            methodHashMap.put(name, new LinkedList<>());
                        }
                        methodHashMap.get(name).add(method);
                        registry.put(outerKey, methodHashMap);
                    }else {
                        throw new RuntimeException("Methods annotated with @EventListener have and can only contain one parameter");
                    }
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("获取的监听器,size:{},cost:{}ms", registry.size(), (System.currentTimeMillis() - start));
            }
        }

    }

    public Map<String, List<Method>> getListeners(Class<?> typeClazz) {
        return registry.getOrDefault(typeClazz.getSimpleName(), Collections.emptyMap());
    }

    public List<Method> getListeners(Class<?> typeClazz, String name) {
        return registry.getOrDefault(typeClazz.getSimpleName(), Collections.emptyMap()).get(name);
    }

    public Map<String,Map<String,List<Method>>> getRegistry(){
        return registry;
    }


}
