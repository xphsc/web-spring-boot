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

import cn.xphsc.web.common.exception.Exceptions;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: callable 结果收集器
 * @since 1.1.7
 */
public class TaskCollector {

    /**
     * 调度器
     */
    private final ExecutorService dispatch;

    /**
     * 结果
     */
    private Future<?>[] futures;

    public TaskCollector(ExecutorService dispatch) {
        this.dispatch = dispatch;
    }

    /**
     * 执行任务
     *
     * @param tasks task
     * @return this
     */
    public TaskCollector tasks(Callable<?>... tasks) {
        this.futures = new Future<?>[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            Callable<?> task = tasks[i];
            try {
                futures[i] = dispatch.submit(task);
            } catch (Exception e) {
                throw Exceptions.runtime("an exception occurred while the task was running", e);
            }
        }
        return this;
    }

    /**
     * 收集结果
     *
     * @return result
     */
    public Object collect() {
        Object[] result = new Object[futures.length];
        for (int i = 0; i < futures.length; i++) {
            Future<?> future = futures[i];
            try {
                result[i] = future.get();
            } catch (InterruptedException e) {
                throw Exceptions.runtime("collect result timeout", e);
            } catch (Exception e) {
                throw Exceptions.runtime("collect result error", e);
            }
        }
        return result;
    }

}
