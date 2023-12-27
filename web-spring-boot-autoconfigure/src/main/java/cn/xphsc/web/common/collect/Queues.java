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
package cn.xphsc.web.common.collect;

import cn.xphsc.web.utils.ArrayUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Queues {
    /**
     * 创建ArrayDeque (JDK无ArrayQueue)
     * 需设置初始长度，默认为16，数组满时成倍扩容
     */
    public static <E> ArrayDeque<E> newArrayDeque(int initSize) {
        return new ArrayDeque<E>(initSize);
    }

    /**
     * 创建LinkedDeque (LinkedList实现了Deque接口)
     */
    public static <E> LinkedList<E> newLinkedDeque() {
        return new LinkedList<E>();
    }

    /**
     * 创建无阻塞情况下，性能最优的并发队列
     */
    public static <E> ConcurrentLinkedQueue<E> newConcurrentNonBlockingQueue() {
        return new ConcurrentLinkedQueue<E>();
    }

    /**
     * 创建无阻塞情况下，性能最优的并发双端队列
     */
    public static <E> Deque<E> newConcurrentNonBlockingDeque() {
        return new java.util.concurrent.ConcurrentLinkedDeque<E>();
    }

    /**
     * 创建并发阻塞情况下，长度不受限的队列.
     * 长度不受限，即生产者不会因为满而阻塞，但消费者会因为空而阻塞.
     */
    public static <E> LinkedBlockingQueue<E> newBlockingUnlimitQueue() {
        return new LinkedBlockingQueue<E>();
    }

    /**
     * 创建并发阻塞情况下，长度不受限的双端队列.
     *
     * 长度不受限，即生产者不会因为满而阻塞，但消费者会因为空而阻塞.
     */
    public static <E> LinkedBlockingDeque<E> newBlockingUnlimitDeque() {
        return new LinkedBlockingDeque<E>();
    }

    /**
     * 创建并发阻塞情况下，长度受限，更节约内存，但共用一把锁的队列（无双端队列实现）.
     */
    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(int capacity) {
        return new ArrayBlockingQueue<E>(capacity);
    }

    /**
     * 创建并发阻塞情况下，长度受限，头队尾两把锁, 但使用更多内存的队列.
     */
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(int capacity) {
        return new LinkedBlockingQueue<E>(capacity);
    }

    /**
     * 创建并发阻塞情况下，长度受限，头队尾两把锁, 但使用更多内存的双端队列.
     */
    public static <E> LinkedBlockingDeque<E> newBlockingDeque(int capacity) {
        return new LinkedBlockingDeque<E>(capacity);
    }
    @SafeVarargs
    public static <E> Queue<E> of(E... e) {
        return new ConcurrentLinkedQueue<>(Arrays.asList(e));
    }

    @SafeVarargs
    public static <E> Deque<E> ofd(E... e) {
        return new ConcurrentLinkedDeque<>(Arrays.asList(e));
    }

    @SafeVarargs
    public static <E, V> Queue<E> of(Function<V, E> f, V... e) {
        Queue<E> q = new ConcurrentLinkedQueue<>();
        int length = ArrayUtils.length(e);
        for (int i = 0; i < length; i++) {
            q.add(f.apply(e[i]));
        }
        return q;
    }


}
