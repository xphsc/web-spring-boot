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
package cn.xphsc.web.boot.event.autoconfigure;


import cn.xphsc.web.event.EventListenerPostProcessor;
import cn.xphsc.web.event.publisher.EventPublisherStrategy;
import cn.xphsc.web.event.publisher.AsyncEventListener;
import cn.xphsc.web.event.publisher.SyncEventListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.xphsc.web.common.WebBeanTemplate.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix =EVENT_PREFIX, name = ENABLED)
public class EventAutoConfiguretion {

    @Bean
    public EventListenerPostProcessor eventListenerRegistry() {
        return new EventListenerPostProcessor();
    }

    @Bean
    public AsyncEventListener asyncEventListener() {
        return new AsyncEventListener(this.eventListenerRegistry());
    }

    @Bean
    public SyncEventListener syncEventListener() {
        return new SyncEventListener(this.eventListenerRegistry());
    }

    @Bean
    public EventPublisherStrategy   eventPublisherStrategy() {
        return new EventPublisherStrategy(eventListenerRegistry());
    }
}
