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
package cn.xphsc.web.boot.threadpool;


import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.common.WebBeanTemplate.THREAD_POOL_PREFIX;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = ThreadPoolProperties.CONFIG_PREFIX)
public class ThreadPoolProperties {

    public static final String CONFIG_PREFIX = THREAD_POOL_PREFIX;
    private boolean enabled;
    /**
     * 核心线程大小
     */
    private Integer corePoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * 最大线程数
     */
    private Integer maximumPoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * 空闲线程等待工作的超时时间
     */
    private Long keepAliveTime = 0L;
    /**
     * 超时时间单位
     */
    private String timeUnit = "milliseconds";
    /**
     * 队列大小
     */
    private Integer queueSize = 10000;
    /**
     * 在执行饱和或关闭时调用处理策略
     */
    private String rejectStrategy;
    /**
     * 在执行饱和或关闭时调用处理策略,
     */
    private String rejectStrategyBeanName;
    /**
     * 线程工厂对应的bean的名称
     */
    private String threadFactoryBeanName;

    public static String getConfigPrefix() {
        return CONFIG_PREFIX;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public String getRejectStrategy() {
        return rejectStrategy;
    }

    public void setRejectStrategy(String rejectStrategy) {
        this.rejectStrategy = rejectStrategy;
    }

    public String getRejectStrategyBeanName() {
        return rejectStrategyBeanName;
    }

    public void setRejectStrategyBeanName(String rejectStrategyBeanName) {
        this.rejectStrategyBeanName = rejectStrategyBeanName;
    }

    public String getThreadFactoryBeanName() {
        return threadFactoryBeanName;
    }

    public void setThreadFactoryBeanName(String threadFactoryBeanName) {
        this.threadFactoryBeanName = threadFactoryBeanName;
    }
}
