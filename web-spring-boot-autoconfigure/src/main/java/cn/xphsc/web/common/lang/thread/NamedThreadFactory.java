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



import cn.xphsc.web.utils.StringUtils;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 自定义命名的线程工厂
 * @since 1.1.7
 */
public class NamedThreadFactory implements ThreadFactory {

    /**
     * 计数器
     */
    private final AtomicInteger counter;

    /**
     * 前缀
     */
    private final String prefix;

    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    /**
     * 守护线程
     */
    private boolean daemon;

    /**
     * 优先级
     */
    private int priority;

    /**
     * 线程组
     */
    private ThreadGroup group;

    /**
     * 异常处理器
     */
    private UncaughtExceptionHandler handler;

    public NamedThreadFactory(String prefix) {
        this.prefix = StringUtils.defaultString(prefix);
        this.counter = new AtomicInteger();
        this.priority = 5;
    }

    public NamedThreadFactory setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public NamedThreadFactory setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public NamedThreadFactory setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public NamedThreadFactory setHandler(UncaughtExceptionHandler handler) {
        this.handler = handler;
        return this;
    }

    public NamedThreadFactory setGroup(String groupName) {
        this.group = new ThreadGroup(groupName);
        return this;
    }

    public NamedThreadFactory setGroup(ThreadGroup group) {
        this.group = group;
        return this;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread;
        if (group == null) {
            thread = new Thread(r);
        } else {
            thread = new Thread(group, r);
        }
        thread.setName(prefix + counter.getAndIncrement());
        if (classLoader != null) {
            thread.setContextClassLoader(classLoader);
        }
        thread.setDaemon(daemon);
        thread.setPriority(priority);
        if (handler != null) {
            thread.setUncaughtExceptionHandler(handler);
        }
        return thread;
    }

}
