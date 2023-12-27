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
package cn.xphsc.web.event.annotation;



import cn.xphsc.web.event.entity.EventType;
import cn.xphsc.web.event.publisher.EventPublishListener;
import cn.xphsc.web.event.publisher.SyncEventListener;

import java.lang.annotation.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: event listeners
  * For reference, the example
 *  @EventListener(name = "TEST_EVENT_SERVICE")
 * public void  listenerMssage(String message) {}
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {
    /**
     *  event listener name
     */
    String name();

    /**
     *Initialize load order
     */
    int order() default 0;

    /**
     * event listener type The default is synchronous and asynchronous is asynceventlistener
     */
    @Deprecated
    Class<? extends EventPublishListener> type() default SyncEventListener.class;

    EventType eventType() default EventType.SYNC;
}
