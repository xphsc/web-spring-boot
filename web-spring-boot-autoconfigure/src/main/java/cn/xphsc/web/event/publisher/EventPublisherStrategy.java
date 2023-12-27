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
package cn.xphsc.web.event.publisher;

import cn.xphsc.web.event.EventListenerPostProcessor;
import cn.xphsc.web.event.annotation.EventListener;
import cn.xphsc.web.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */

public class EventPublisherStrategy implements EventPublisher, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventPublisherStrategy.class);

    protected ApplicationContext applicationContext;
    protected EventListenerPostProcessor registry;
    public EventPublisherStrategy(EventListenerPostProcessor registry){
        this.registry=registry;
    }
    @Override
    public void publishEvent(String eventName, Object... params) {
        for (Map.Entry<String, Map<String, List<Method>>> entry : registry.getRegistry().entrySet()) {
            List<Method> methods = entry.getValue().getOrDefault(eventName, Collections.emptyList());
            if (CollectionUtils.isEmpty(methods)) {
                logger.error("不存在对应的监听器代码,eventName:{},params:{}", eventName, params);
                return;
            }
            Class<? extends EventPublishListener> classType = null;
            for (Method m : methods) {
                if(m.isAnnotationPresent(EventListener.class)){
                    EventListener eventListener = m.getAnnotation(EventListener.class);
                     classType=eventListener.type();
                    if(ObjectUtils.isNotEmpty(eventListener.eventType())) {
                        switch (eventListener.eventType()) {
                            case SYNC:
                                classType = SyncEventListener.class;
                                break;
                            case ASYNC:
                                classType = AsyncEventListener.class;
                                break;
                            default:
                        }
                    }
                  
                }

            }
            EventPublishListener eventPublishListener = applicationContext.getBean(classType);
           eventPublishListener.localPublish(eventName,params);

        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
