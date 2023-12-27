/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.common.lang.thread;




import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.utils.ObjectUtils;
import cn.xphsc.web.utils.Systems;

import java.util.concurrent.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 线程池构造器
 * @since 1.1.7
 */
public class ExecutorBuilder  {

    /**
     * 默认的等待队列容量
     */
    public static final int DEFAULT_QUEUE_CAPACITY = 1024;

    /**
     * 初始池大小
     */
    private int corePoolSize;

    /**
     * 最大池大小 (允许同时执行的最大线程数) 默认: 核心数 * 2
     */
    private int maxPoolSize;

    /**
     * 线程存活时间, 即当池中线程多于初始大小时, 多出的线程保留的时长
     */
    private long keepAliveTime;

    /**
     * 队列, 用于存在未执行的线程
     */
    private BlockingQueue<Runnable> workQueue;

    /**
     * 线程工厂, 用于自定义线程创建
     */
    private ThreadFactory threadFactory;

    /**
     * 当线程阻塞 (block) 时的异常处理器, 所谓线程阻塞即线程池和等待队列已满, 无法处理线程时采取的策略
     */
    private RejectedExecutionHandler handler;

    /**
     * 线程执行超时后是否回收线程
     */
    private boolean allowCoreThreadTimeout;

    /**
     * 预开启所有的核心线程
     */
    private boolean preStartAllCoreThreads;

    public ExecutorBuilder() {
        this.keepAliveTime = Constants.MS_S_60;
        this.maxPoolSize = Systems.PROCESS_NUM * 2;
    }

    /**
     * 创建 ExecutorBuilder
     *
     * @return this
     */
    public static ExecutorBuilder create() {
        return new ExecutorBuilder();
    }

    /**
     * 设置初始池大小, 默认0
     *
     * @param corePoolSize 初始池大小
     * @return this
     */
    public ExecutorBuilder setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    /**
     * 设置最大池大小 (允许同时执行的最大线程数)
     *
     * @param maxPoolSize 最大池大小 (允许同时执行的最大线程数)
     * @return this
     */
    public ExecutorBuilder setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    /**
     * 设置线程存活时间, 即当池中线程多于初始大小时, 多出的线程保留的时长
     *
     * @param keepAliveTime 线程存活时间
     * @param unit          单位
     * @return this
     */
    public ExecutorBuilder setKeepAliveTime(long keepAliveTime, TimeUnit unit) {
        return setKeepAliveTime(unit.toNanos(keepAliveTime));
    }

    /**
     * 设置线程存活时间, 即当池中线程多于初始大小时, 多出的线程保留的时长, 单位ms
     *
     * @param keepAliveTime 线程存活时间, 单位ms
     * @return this
     */
    public ExecutorBuilder setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    /**
     * 设置队列, 用于存在未执行的线程
     * <p>
     * SynchronousQueue    它将任务直接提交给线程而不保持它们, 当运行线程小于maxPoolSize时会创建新线程, 否则触发异常策略
     * <p>
     * LinkedBlockingQueue 默认无界队列, 当运行线程大于corePoolSize时始终放入此队列, 此时maximumPoolSize无效
     * 当构造LinkedBlockingQueue对象时传入参数, 变为有界队列, 队列满时, 运行线程小于maxPoolSize时会创建新线程, 否则触发异常策略
     * <p>
     * ArrayBlockingQueue  有界队列, 相对无界队列有利于控制队列大小, 队列满时, 运行线程小于maxPoolSize时会创建新线程, 否则触发异常策略
     *
     * @param workQueue 队列
     * @return this
     */
    public ExecutorBuilder setWorkQueue(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        return this;
    }

    /**
     * 使用 ArrayBlockingQueue 做为等待队列
     * 有界队列, 相对无界队列有利于控制队列大小, 队列满时, 运行线程小于maxPoolSize时会创建新线程, 否则触发异常策略
     *
     * @param capacity 队列容量
     * @return this
     */
    public ExecutorBuilder useArrayBlockingQueue(int capacity) {
        return this.setWorkQueue(new ArrayBlockingQueue<>(capacity));
    }

    /**
     * 使用 SynchronousQueue 做为等待队列  (非公平策略)
     * 它将任务直接提交给线程而不保持它们当运行线程小于maxPoolSize时会创建新线程, 否则触发异常策略
     *
     * @return this
     */
    public ExecutorBuilder useSynchronousQueue() {
        return this.setWorkQueue(new SynchronousQueue<>(false));
    }

    /**
     * 使用 SynchronousQueue 做为等待队列
     * 它将任务直接提交给线程而不保持它们当运行线程小于maxPoolSize时会创建新线程, 否则触发异常策略
     *
     * @param fair 是否使用公平访问策略
     * @return this
     */
    public ExecutorBuilder useSynchronousQueue(boolean fair) {
        return this.setWorkQueue(new SynchronousQueue<>(fair));
    }

    /**
     * 设置线程工厂
     *
     * @param threadFactory ThreadFactory
     * @return this
     */
    public ExecutorBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    /**
     * 设置线程工厂
     *
     * @param threadPrefix 线程名称前缀
     * @return this
     */
    public ExecutorBuilder setNamedThreadFactory(String threadPrefix) {
        this.threadFactory = new NamedThreadFactory(threadPrefix);
        return this;
    }

    /**
     * 设置当线程阻塞(block)时的异常处理器, 所谓线程阻塞即线程池和等待队列已满, 无法处理线程时采取的策略
     *
     * @param handler RejectedExecutionHandler
     * @return this
     */
    public ExecutorBuilder setHandler(RejectedExecutionHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * 设置线程执行超时后是否回收线程
     *
     * @param allowCoreThreadTimeout 线程执行超时后是否回收线程
     * @return this
     */
    public ExecutorBuilder setAllowCoreThreadTimeout(boolean allowCoreThreadTimeout) {
        this.allowCoreThreadTimeout = allowCoreThreadTimeout;
        return this;
    }

    /**
     * 预开启所有的核心线程
     *
     * @param preStartAllCoreThreads 是否预开启所有的核心线程
     * @return this
     */
    public ExecutorBuilder setPreStartAllCoreThreads(boolean preStartAllCoreThreads) {
        this.preStartAllCoreThreads = preStartAllCoreThreads;
        return this;
    }

    /**
     * 构建 ThreadPoolExecutor
     */
    public ThreadPoolExecutor build() {
        return build(this);
    }

    /**
     * 构建 ThreadPoolExecutor
     *
     * @param builder ExecutorBuilder
     * @return ThreadPoolExecutor
     */
    private static ThreadPoolExecutor build(ExecutorBuilder builder) {
        int corePoolSize = builder.corePoolSize;
        int maxPoolSize = builder.maxPoolSize;
        long keepAliveTime = builder.keepAliveTime;
        BlockingQueue<Runnable> workQueue;
        if (builder.workQueue != null) {
            workQueue = builder.workQueue;
        } else {
            // corePoolSize 为 0 则要使用 SynchronousQueue 避免无限阻塞
            workQueue = (corePoolSize <= 0) ? new SynchronousQueue<>() : new LinkedBlockingQueue<>(DEFAULT_QUEUE_CAPACITY);
        }
        ThreadFactory threadFactory = (builder.threadFactory != null) ? builder.threadFactory : Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = ObjectUtils.def(builder.handler, ThreadPoolExecutor.AbortPolicy::new);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                workQueue, threadFactory, handler);
        threadPoolExecutor.allowCoreThreadTimeOut(builder.allowCoreThreadTimeout);
        if (builder.preStartAllCoreThreads) {
            threadPoolExecutor.prestartAllCoreThreads();
        }
        return threadPoolExecutor;
    }

}
