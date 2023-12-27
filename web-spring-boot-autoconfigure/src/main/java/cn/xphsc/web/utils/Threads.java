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
package cn.xphsc.web.utils;



import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.common.lang.thread.*;
import cn.xphsc.web.common.lang.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 线程工具类
 * @since 1.1.7
 */
public class Threads {

    /**
     * Threads 线程池
     */
    public static final ExecutorService GLOBAL_EXECUTOR = ExecutorBuilder.create()
            .setNamedThreadFactory("orion-global-thread-")
            .setCorePoolSize(2)
            .setMaxPoolSize(32)
            .setKeepAliveTime(Constants.MS_S_60)
            .setWorkQueue(new LinkedBlockingQueue<>())
            .setAllowCoreThreadTimeout(true)
            .build();

    /**
     * 全局线程池
     */
    public static final ExecutorService CACHE_EXECUTOR = ExecutorBuilder.create()
            .setNamedThreadFactory("orion-cache-thread-")
            .setCorePoolSize(1)
            .setMaxPoolSize(Integer.MAX_VALUE)
            .setKeepAliveTime(Constants.MS_S_60)
            .setWorkQueue(new SynchronousQueue<>())
            .setAllowCoreThreadTimeout(true)
            .build();

    private Threads() {
    }

    static {
        Systems.addShutdownHook(() -> {
            shutdownPoolNow(GLOBAL_EXECUTOR, Constants.MS_S_3);
            shutdownPoolNow(CACHE_EXECUTOR, Constants.MS_S_3);
        });
    }

    public static void start(Runnable r) {
        GLOBAL_EXECUTOR.execute(r);
    }

    /**
     * 执行线程
     * @param r    ignore
     * @param pool 线程池
     */
    public static void start(Runnable r, ExecutorService pool) {
        if (pool == null) {
            pool = GLOBAL_EXECUTOR;
        }
        pool.execute(r);
    }

    /**
     * 执行线程
     * @param rs   线程
     * @param pool 线程池
     */
    public static void start(Collection<Runnable> rs, ExecutorService pool) {
        if (pool == null) {
            pool = GLOBAL_EXECUTOR;
        }
        for (Runnable r : rs) {
            pool.execute(r);
        }
    }

    public static <V> Future<V> call(Callable<V> c) {
        return GLOBAL_EXECUTOR.submit(c);
    }

    /**
     * 执行线程
     * @param c    ignore
     * @param pool 线程池
     * @return Future
     */
    public static <V> Future<V> call(Callable<V> c, ExecutorService pool) {
        if (pool == null) {
            pool = GLOBAL_EXECUTOR;
        }
        return pool.submit(c);
    }

    /**
     * 执行线程
     *
     * @param cs   ignore
     * @param pool 线程池
     * @return Future
     */
    public static <V> List<Future<V>> call(Collection<? extends Callable<V>> cs, ExecutorService pool) {
        if (pool == null) {
            pool = GLOBAL_EXECUTOR;
        }
        List<Future<V>> list = new ArrayList<>();
        for (Callable<V> c : cs) {
            list.add(pool.submit(c));
        }
        return list;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * 线程休眠
     * @param duration 时间
     * @param unit     时间单位
     */
    public static void sleep(long duration, TimeUnit unit) {
        try {
            unit.sleep(duration);
        } catch (InterruptedException e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * 多线程执行任务并且收集结果
     * @param tasks 任务
     * @return 元组
     */
    public static Object collect(Callable<?>... tasks) {
        return new TaskCollector(GLOBAL_EXECUTOR)
                .tasks(tasks)
                .collect();
    }

    /**
     * 阻塞运行线程
     * @param r     线程
     * @param count 执行次数
     * @param pool  线程池
     * @throws InterruptedException InterruptedException
     */
    public static void blockRun(Runnable r, int count, ExecutorService pool) throws InterruptedException {
        blockRun(r, new CountDownLatch(count), pool);
    }

    /**
     * 阻塞运行线程
     * @param r    线程
     * @param c    闭锁
     * @param pool 线程池
     * @throws InterruptedException InterruptedException
     */
    public static void blockRun(Runnable r, CountDownLatch c, ExecutorService pool) throws InterruptedException {
        if (pool == null) {
            pool = GLOBAL_EXECUTOR;
        }
        long count = c.getCount();
        for (int i = 0; i < count; i++) {
            pool.execute(new HookRunnable(r, c::countDown));
        }
        c.await();
    }

    /**
     * 阻塞运行线程
     * @param rs   线程
     * @param pool 线程池
     * @throws InterruptedException InterruptedException
     */
    public static void blockRun(Collection<? extends Runnable> rs, ExecutorService pool) throws InterruptedException {
        if (pool == null) {
            pool = GLOBAL_EXECUTOR;
        }
        CountDownLatch c = new CountDownLatch(rs.size());
        for (Runnable r : rs) {
            pool.execute(new HookRunnable(r, c::countDown));
        }
        c.await();
    }

    /**
     * 并发多次单个线程
     * <p>
     * @param r     runnable
     * @param count 并发次数
     * @param pool  线程池
     * @return 屏障
     */
    public static CyclicBarrier concurrent(Runnable r, int count, ExecutorService pool) {
        if (pool == null) {
            pool = CACHE_EXECUTOR;
        }
        CyclicBarrier cb = new CyclicBarrier(count);
        ConcurrentRunnable cr = new ConcurrentRunnable(r, cb);
        for (int i = 0; i < count; i++) {
            pool.execute(cr);
        }
        return cb;
    }

    /**
     * 并发多次单个线程
     * <p>
     * 线程池的最大线程数必须大于等于并发次数
     *
     * @param r    runnable
     * @param pool 线程池
     * @param cb   屏障
     */
    public static void concurrent(Runnable r, ExecutorService pool, CyclicBarrier cb) {
        if (pool == null) {
            pool = CACHE_EXECUTOR;
        }
        int count = cb.getParties();
        ConcurrentRunnable cr = new ConcurrentRunnable(r, cb);
        for (int i = 0; i < count; i++) {
            pool.execute(cr);
        }
    }

    /**
     * 并发多次单个线程
     * <p>
     * 线程池的最大线程数必须大于等于并发次数
     * @param r    runnable
     * @param pool 线程池
     * @param cd   闭锁
     */
    public static void concurrent(Runnable r, ExecutorService pool, CountDownLatch cd) {
        if (pool == null) {
            pool = CACHE_EXECUTOR;
        }
        ConcurrentRunnable cr = new ConcurrentRunnable(r, cd);
        long count = cd.getCount();
        for (int i = 0; i < count; i++) {
            pool.execute(cr);
            cd.countDown();
        }
    }

    /**
     * 并发多次单个线程
     * <p>
     * 线程池的最大线程数必须大于等于并发次数
     *
     * @param c     callable
     * @param count 并发次数
     * @param pool  线程池
     * @return Future
     */
    public static <V> List<Future<V>> concurrent(Callable<V> c, int count, ExecutorService pool) {
        if (pool == null) {
            pool = CACHE_EXECUTOR;
        }
        List<Future<V>> list = new ArrayList<>(count);
        CyclicBarrier cb = new CyclicBarrier(count);
        ConcurrentCallable<V> cc = new ConcurrentCallable<>(c, cb);
        for (int i = 0; i < count; i++) {
            list.add(pool.submit(cc));
        }
        return list;
    }

    /**
     * 并发多次单个线程
     * <p>
     * 线程池的最大线程数必须大于等于并发次数
     * @param c    callable
     * @param pool 线程池
     * @param cb   屏障
     * @return Future
     */
    public static <V> List<Future<V>> concurrent(Callable<V> c, ExecutorService pool, CyclicBarrier cb) {
        if (pool == null) {
            pool = CACHE_EXECUTOR;
        }
        int count = cb.getParties();
        List<Future<V>> list = new ArrayList<>();
        ConcurrentCallable<V> cc = new ConcurrentCallable<>(c, cb);
        for (int i = 0; i < count; i++) {
            list.add(pool.submit(cc));
        }
        return list;
    }

    /**
     * 并发多次单个线程
     * <p>
     * 线程池的最大线程数必须大于等于并发次数
     * @param c    callable
     * @param pool 线程池
     * @param cd   闭锁
     * @return Future
     */
    public static <V> List<Future<V>> concurrent(Callable<V> c, ExecutorService pool, CountDownLatch cd) {
        if (pool == null) {
            pool = CACHE_EXECUTOR;
        }
        long count = cd.getCount();
        List<Future<V>> list = new ArrayList<>();
        ConcurrentCallable<V> cc = new ConcurrentCallable<>(c, cd);
        for (int i = 0; i < count; i++) {
            list.add(pool.submit(cc));
            cd.countDown();
        }
        return list;
    }

    /**
     * 并发多个线程
     * <p>
     * 线程池的最大线程数必须大于等于并发次数
     * @param rs   线程
     * @param pool 线程池
     */
    public static void concurrentRunnable(Collection<? extends Runnable> rs, ExecutorService pool) {
        if (pool == null) {
            pool = CACHE_EXECUTOR;
        }
        CyclicBarrier cb = new CyclicBarrier(rs.size());
        for (Runnable r : rs) {
            ConcurrentRunnable cr = new ConcurrentRunnable(r, cb);
            pool.execute(cr);
        }
    }

    /**
     * 并发多个线程
     * <p>
     * 线程池的最大线程数必须大于等于并发次数
     * @param cs   线程
     * @param pool 线程池
     * @return Future
     */
    public static <V> List<Future<V>> concurrentCallable(Collection<? extends Callable<V>> cs, ExecutorService pool) {
        if (pool == null) {
            pool = CACHE_EXECUTOR;
        }
        List<Future<V>> list = new ArrayList<>();
        CyclicBarrier cb = new CyclicBarrier(cs.size());
        for (Callable<V> c : cs) {
            ConcurrentCallable<V> cr = new ConcurrentCallable<>(c, cb);
            list.add(pool.submit(cr));
        }
        return list;
    }

    /**
     * 关闭线程池
     * @param pool            线程池
     * @param shutdownTimeout 终止时间
     */
    public static void shutdownPool(ExecutorService pool, int shutdownTimeout) {
        shutdownPool(pool, shutdownTimeout, shutdownTimeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 关闭线程池
     * @param pool            线程池
     * @param shutdownTimeout 终止时间
     * @param timeUnit        时间单位
     */
    public static void shutdownPool(ExecutorService pool, int shutdownTimeout, TimeUnit timeUnit) {
        shutdownPool(pool, shutdownTimeout, shutdownTimeout, timeUnit);
    }

    /**
     * 关闭线程池
     * @param pool               线程池
     * @param shutdownTimeout    终止时间
     * @param shutdownNowTimeout 如果调用终止超时, 取消队列中的Pending的任务, 中断阻塞的时间
     */
    public static void shutdownPool(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout) {
        shutdownPool(pool, shutdownTimeout, shutdownNowTimeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 关闭线程池
     * @param pool               线程池
     * @param shutdownTimeout    终止时间
     * @param shutdownNowTimeout 如果调用终止超时, 取消队列中的Pending的任务, 中断阻塞的时间
     * @param timeUnit           时间单位
     */
    public static void shutdownPool(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout, TimeUnit timeUnit) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    Console.error("thread pool did not terminated");
                }
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 立即关闭线程池
     * @param pool    线程池
     * @param timeout 取消队列中的Pending的任务, 中断阻塞的时间毫秒
     */
    public static void shutdownPoolNow(ExecutorService pool, int timeout) {
        try {
            pool.shutdownNow();
            if (!pool.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
                Console.error("thread pool did not terminated");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 立即关闭线程池
     *
     * @param pool     线程池
     * @param timeout  取消队列中的Pending的任务, 中断阻塞的时间
     * @param timeUnit 时间单位
     */
    public static void shutdownPoolNow(ExecutorService pool, int timeout, TimeUnit timeUnit) {
        try {
            pool.shutdownNow();
            if (!pool.awaitTermination(timeout, timeUnit)) {
                Console.error("thread pool did not terminated");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 创建线程池
     * @param core      核心线程数量
     * @param max       最大线程数
     * @param keepMilli 空闲线程等待时间
     * @param workQueue 任务队列
     * @param prefix    线程前缀
     * @return 线程池
     */
    public static ExecutorService newThreadPool(int core, int max, int keepMilli, BlockingQueue<Runnable> workQueue, String prefix) {
        return new ThreadPoolExecutor(core, max, keepMilli, TimeUnit.MILLISECONDS, workQueue, new NamedThreadFactory(prefix));
    }

    /**
     * @param core      核心线程数量
     * @param max       最大线程数
     * @param keepMilli 空闲线程等待时间
     * @param workQueue 任务队列
     * @param prefix    线程前缀
     * @param handler   任务队列已满时的拒绝策略
     * @return 线程池
     */
    public static ExecutorService newThreadPool(int core, int max, int keepMilli, BlockingQueue<Runnable> workQueue, String prefix, RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(core, max, keepMilli, TimeUnit.MILLISECONDS, workQueue, new NamedThreadFactory(prefix), handler);
    }

}
