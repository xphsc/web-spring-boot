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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 可以并发执行的runnable
 * @since 1.1.7
 */
public class ConcurrentRunnable implements Runnable {

    private final Runnable r;

    private CyclicBarrier cb;

    private CountDownLatch cd;

    public ConcurrentRunnable(Runnable r, CyclicBarrier cb) {
        this.r = r;
        this.cb = cb;
    }

    public ConcurrentRunnable(Runnable r, CountDownLatch cd) {
        this.r = r;
        this.cd = cd;
    }

    @Override
    public void run() {
        try {
            if (cb != null) {
                cb.await();
            } else if (cd != null) {
                cd.await();
            }
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
        r.run();
    }

}
