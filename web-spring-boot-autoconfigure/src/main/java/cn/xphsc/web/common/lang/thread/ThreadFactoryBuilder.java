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


import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 线程器构建器
 * @since 1.1.7
 */
public class ThreadFactoryBuilder  {

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    /**
     * 是否守护线程
     */
    private boolean daemon;

    /**
     * 线程优先级
     */
    private int priority;

    /**
     * 未捕获异常处理器
     */
    private UncaughtExceptionHandler uncaughtExceptionHandler;

    /**
     * 线程组
     */
    private ThreadGroup group;

    public ThreadFactoryBuilder() {
        this.priority = 5;
    }

    /**
     * 创建 ThreadFactoryBuilder
     *
     * @return ThreadFactoryBuilder
     */
    public static ThreadFactoryBuilder create() {
        return new ThreadFactoryBuilder();
    }

    public ThreadFactoryBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadFactoryBuilder setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public ThreadFactoryBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    public ThreadFactoryBuilder setGroup(String groupName) {
        this.group = new ThreadGroup(groupName);
        return this;
    }

    public ThreadFactoryBuilder setGroup(ThreadGroup group) {
        this.group = group;
        return this;
    }

    /**
     * 构建 ThreadFactory
     * @return ThreadFactory
     */
    public ThreadFactory build() {
        return build(this);
    }

    /**
     * 构建
     *
     * @param builder ThreadFactoryBuilder
     * @return ThreadFactory
     */
    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        String namePrefix = builder.prefix;
        UncaughtExceptionHandler handler = builder.uncaughtExceptionHandler;
        AtomicLong count = namePrefix == null ? null : new AtomicLong();
        return r -> {
            Thread thread;
            if (builder.group == null) {
                thread = new Thread(r);
            } else {
                thread = new Thread(builder.group, r);
            }
            if (namePrefix != null) {
                thread.setName(namePrefix + count.getAndIncrement());
            }
            if (builder.classLoader != null) {
                thread.setContextClassLoader(builder.classLoader);
            }
            thread.setDaemon(builder.daemon);
            thread.setPriority(builder.priority);
            if (handler != null) {
                thread.setUncaughtExceptionHandler(handler);
            }
            return thread;
        };
    }

}
