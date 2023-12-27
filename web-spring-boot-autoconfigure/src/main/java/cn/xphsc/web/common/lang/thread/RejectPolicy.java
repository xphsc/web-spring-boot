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

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 线程池拒绝策略
 * @since 1.1.7
 */
public enum RejectPolicy {

    /**
     * 任务遭到拒绝将抛出异常
     */
    ABORT(new ThreadPoolExecutor.AbortPolicy()),

    /**
     * 放弃当前任务 静默抛出
     */
    DISCARD(new ThreadPoolExecutor.DiscardPolicy()),

    /**
     * 删除工作队列头部任务进行重试, 如果失败则重复操作
     */
    DISCARD_OLDEST(new ThreadPoolExecutor.DiscardOldestPolicy()),

    /**
     * 由调用线程执行任务
     */
    CALLER_RUNS(new ThreadPoolExecutor.CallerRunsPolicy());

    private final RejectedExecutionHandler handler;

    RejectPolicy(RejectedExecutionHandler handler) {
        this.handler = handler;
    }

    public RejectedExecutionHandler getHandler() {
        return this.handler;
    }

}
