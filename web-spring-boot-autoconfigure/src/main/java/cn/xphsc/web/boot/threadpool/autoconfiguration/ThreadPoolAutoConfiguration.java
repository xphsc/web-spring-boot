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
package cn.xphsc.web.boot.threadpool.autoconfiguration;

import cn.xphsc.web.boot.threadpool.ThreadPoolProperties;
import cn.xphsc.web.boot.threadpool.exception.ThreadPoolException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Configurable
@EnableConfigurationProperties(ThreadPoolProperties.class)
@ConditionalOnProperty(prefix = ThreadPoolProperties.CONFIG_PREFIX, name = "enabled")
public class ThreadPoolAutoConfiguration implements ApplicationContextAware {
    private ThreadPoolProperties threadPoolProperties;
    private ApplicationContext applicationContext;
    public  ThreadPoolAutoConfiguration(ThreadPoolProperties threadPoolProperties){
        this.threadPoolProperties=threadPoolProperties;
    }

    private static final Map<String, TimeUnit> TIME_UNIT = new HashMap<>();
    private static final Map<String, RejectedExecutionHandler> REJECTED_EXECUTION_HANDLER = new HashMap<>();
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = Thread::new;
    static {
        TIME_UNIT.put("nanoseconds", TimeUnit.NANOSECONDS);
        TIME_UNIT.put("microseconds", TimeUnit.MICROSECONDS);
        TIME_UNIT.put("milliseconds", TimeUnit.MILLISECONDS);
        TIME_UNIT.put("seconds", TimeUnit.SECONDS);
        TIME_UNIT.put("minutes", TimeUnit.MINUTES);
        TIME_UNIT.put("hours", TimeUnit.HOURS);
        TIME_UNIT.put("days", TimeUnit.DAYS);

        REJECTED_EXECUTION_HANDLER.put("CallerRunsPolicy", new ThreadPoolExecutor.CallerRunsPolicy());
        REJECTED_EXECUTION_HANDLER.put("AbortPolicy", new ThreadPoolExecutor.AbortPolicy());
        REJECTED_EXECUTION_HANDLER.put("DiscardPolicy", new ThreadPoolExecutor.DiscardPolicy());
        REJECTED_EXECUTION_HANDLER.put("DiscardOldestPolicy", new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        Integer corePoolSize = threadPoolProperties.getCorePoolSize();
        Integer maximumPoolSize = threadPoolProperties.getMaximumPoolSize();
        Long keepAliveTime = threadPoolProperties.getKeepAliveTime();
        String timeUnit = threadPoolProperties.getTimeUnit();
        Integer queueSize = threadPoolProperties.getQueueSize();
        String rejectStrategy = threadPoolProperties.getRejectStrategy();
        String rejectStrategyBeanName = threadPoolProperties.getRejectStrategyBeanName();
        String threadFactoryBeanName = threadPoolProperties.getThreadFactoryBeanName();
        ThreadFactory threadFactory;
        RejectedExecutionHandler rejectedExecutionHandler;
        TimeUnit unit = TIME_UNIT.get(timeUnit);
        if (threadFactoryBeanName == null || threadFactoryBeanName.length() == 0) {
            threadFactory = DEFAULT_THREAD_FACTORY;
        } else {
            Object bean = applicationContext.getBean(threadFactoryBeanName);
            if (!(bean instanceof ThreadFactory)) {
                throw new ThreadPoolException(500,threadFactoryBeanName + "请指定一个正确类型的bean名称");
            }
            threadFactory = (ThreadFactory) bean;
        }
        if (rejectStrategy == null || "".equals(rejectStrategy)) {
            if (rejectStrategyBeanName != null && rejectStrategyBeanName.length() > 0) {
                Object rejectStrategyBean = applicationContext.getBean(rejectStrategyBeanName);
                rejectedExecutionHandler = ((RejectedExecutionHandler) rejectStrategyBean);
            } else {
                rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
            }
        } else {
            if (!REJECTED_EXECUTION_HANDLER.containsKey(rejectStrategy)) {
                throw new ThreadPoolException(500,"请设置正确拒绝策略");
            }
            rejectedExecutionHandler = REJECTED_EXECUTION_HANDLER.get(rejectStrategy);

        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                new LinkedBlockingQueue<>(queueSize), threadFactory, rejectedExecutionHandler);
        return threadPoolExecutor;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
