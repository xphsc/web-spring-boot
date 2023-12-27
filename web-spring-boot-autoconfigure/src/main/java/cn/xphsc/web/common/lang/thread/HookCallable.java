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

import java.util.concurrent.Callable;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 具有钩子的Callable
 * @since 1.1.7
 */
public class HookCallable<V> implements Callable<V> {

    /**
     * task
     */
    private final HookCallable<V> task;

    /**
     * 钩子任务
     */
    private final Runnable hook;

    /**
     * task 异常是否执行钩子
     */
    private boolean taskErrorRunHook = true;

    public HookCallable(HookCallable<V> task, Runnable hook) {
        this.task = task;
        this.hook = hook;
    }

    public HookCallable(HookCallable<V> task, Runnable hook, boolean taskErrorRunHook) {
        this.task = task;
        this.hook = hook;
        this.taskErrorRunHook = taskErrorRunHook;
    }

    @Override
    public V call() throws Exception {
        Exception e = null;
        V v = null;
        try {
            v = task.call();
        } catch (Exception ex) {
            e = ex;
        }
        if (e == null || taskErrorRunHook) {
            hook.run();
        }
        if (e != null) {
            throw e;
        } else {
            return v;
        }
    }

}
