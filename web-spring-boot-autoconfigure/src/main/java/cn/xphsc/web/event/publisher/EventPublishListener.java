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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public  class EventPublishListener implements  ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(EventPublishListener.class);

    protected EventListenerPostProcessor registry;
    protected ApplicationContext applicationContext;

    protected Object message;
    public EventPublishListener(EventListenerPostProcessor registry){
        this.registry=registry;
    }
    public void localPublish(String eventName, Object... params) {
        List<Method> methodSet = this.registry.getListeners(this.getClass(), eventName);
            if (CollectionUtils.isEmpty(methodSet)) {
                logger.warn("没有找到对应的监听器,eventName:{}", eventName);
                return;
            }
            for (Method eventMethod : methodSet) {
                MDC.put("UUID", UUID.randomUUID().toString());
                eventMethod.setAccessible(true);
                try {
                    Class clazz = eventMethod.getDeclaringClass();
                    eventMethod.invoke(applicationContext.getBean(clazz), params);
                } catch (Exception e) {
                    logger.error("执行监听器方法失败,eventName:{},params:{}", eventName, params, e);
                }finally {
                    MDC.remove("UUID");
                }
            }
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
