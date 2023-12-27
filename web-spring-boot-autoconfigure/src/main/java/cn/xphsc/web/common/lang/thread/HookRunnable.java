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


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 具有钩子的Runnable
 * @since 1.1.7
 */
public class HookRunnable implements Runnable {

    /**
     * task
     */
    private final Runnable task;

    /**
     * hook
     */
    private final Runnable hook;

    /**
     * task 异常是否执行hook
     */
    private final boolean taskErrorRunHook;

    public HookRunnable(Runnable task, Runnable hook) {
        this(task, hook, true);
    }

    public HookRunnable(Runnable task, Runnable hook, boolean taskErrorRunHook) {
        this.task = task;
        this.hook = hook;
        this.taskErrorRunHook = taskErrorRunHook;
    }

    @Override
    public void run() {
        RuntimeException e = null;
        try {
            task.run();
        } catch (RuntimeException ex) {
            e = ex;
        }
        if (hook != null && (e == null || taskErrorRunHook)) {
            hook.run();
        }
        if (e != null) {
            throw e;
        }
    }

}
