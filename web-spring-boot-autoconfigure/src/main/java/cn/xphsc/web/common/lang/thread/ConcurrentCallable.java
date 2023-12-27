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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 具有并发特性的Callable
 * @since 1.1.7
 */
public class ConcurrentCallable<V> implements Callable<V> {

    private final Callable<V> c;

    private CyclicBarrier cb;

    private CountDownLatch cd;

    public ConcurrentCallable(Callable<V> c, CyclicBarrier cb) {
        this.c = c;
        this.cb = cb;
    }

    public ConcurrentCallable(Callable<V> c, CountDownLatch cd) {
        this.c = c;
        this.cd = cd;
    }

    @Override
    public V call() throws Exception {
        if (cb != null) {
            cb.await();
        } else if (cd != null) {
            cd.await();
        }
        return c.call();
    }

}
