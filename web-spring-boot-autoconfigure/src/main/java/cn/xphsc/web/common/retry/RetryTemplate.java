/*
 * Copyright (c) 2021 huipei.x
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
package cn.xphsc.web.common.retry;

import cn.xphsc.web.common.exception.RetryException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 重试类
 * @since 1.0.0
 */
public class RetryTemplate {

    /** 默认重试次数 */
    private static final int DEFAULT_RETRY_COUNT = 3;

    /** 默认休眠时间 毫秒 */
    private static final long DEFAULT_SLEEP_TIME = 3000L;

    /**
     * 重试调度方法
     * @param retry 方法执行体(返回数据)
     * @param <T>  返回数据类型
     * @throws RetryException 业务异常
     */
    public static <T> T retry(Retry<T> retry) throws RetryException {
        return retry(retry, null, DEFAULT_RETRY_COUNT, DEFAULT_SLEEP_TIME, null);
    }

    /**
     * 重试调度方法
     * @param retry 方法执行体(返回数据)
     * @param retryCount   重试次数
     * @param sleepTime    重试间隔睡眠时间(注意：阻塞当前线程)
     * @param <T>          返回数据类
     * @throws RetryException 业务异常
     */
    public static <T> T retry(Retry<T> retry, int retryCount, long sleepTime) throws RetryException {
        return retry(retry, null, retryCount, sleepTime, null);
    }

    /**
     * 对每一次失败进行调度(包括第一次执行)
     * @param retry 方法执行体(返回数据)
     * @param consumer 出错异常处理(包括第一次执行和重试错误)
     * @param <T>   返回数据类型
     */
    public static <T> T retryAnyFail(Retry<T> retry, Consumer<Throwable> consumer) {
        try {
            return retry(retry, consumer, DEFAULT_RETRY_COUNT, DEFAULT_SLEEP_TIME, null);
        } catch (RetryException c) {
        }
        return null;
    }

    /**
     * 对每一次失败进行调度(包括第一次执行)
     * @param retry 方法执行体(返回数据)
     * @param consumer  出错异常处理(包括第一次执行和重试错误)
     * @param retryCount 重试次数
     * @param sleepTime 重试间隔睡眠时间(注意：阻塞当前线程)
     */
    public static <T> T retryAnyFail(Retry<T> retry, Consumer<Throwable> consumer, int retryCount, long sleepTime) {
        try {
            return retry(retry, consumer, retryCount, sleepTime, null);
        } catch (RetryException e) {
            // 业务失败
        }
        return null;
    }

    /**
     * 重试调度方法和处理
     * @param retry 方法执行体(返回数据)
     * @param othhttps://github.com/wboost/spring-boot-starter-supporter  其他操作
     * @param <T> 返回数据类型
     */
    public static <T> T retrySuccessOrElseGet(Retry<T> retry, Supplier<T> other) {
        return retrySuccessOrElseGet(retry, null, DEFAULT_RETRY_COUNT, DEFAULT_SLEEP_TIME, null, other);
    }

    /**
     * 重试调度方法
     * @param retry 方法执行体(返回数据)
     * @param other  其他操作
     * @param retryCount 重试次数
     * @param sleepTime 重试间隔睡眠时间(注意：阻塞当前线程)
     * @param <T>  返回数据类型
     */
    public static <T> T retrySuccessOrElseGet(Retry<T> retry, Supplier<T> other, int retryCount, long sleepTime) {
        return retrySuccessOrElseGet(retry, null, retryCount, sleepTime, null, other);
    }

    /**
     * 重试调度方法和处理
     * @param retry 方法执行体(返回数据)
     * @param fn    处理业务成功失败
     * @param <T>   接收类型
     * @param <R>   返回类型
     */
    public static <T, R> R retryAnyFn(Retry<T> retry, BiFunction<? super T, Throwable, ? extends R> fn) {
        return retryAnyFn(retry, null, DEFAULT_RETRY_COUNT, DEFAULT_SLEEP_TIME, null, fn);
    }

    /**
     * 重试调度方法和处理
     * @param retry 方法执行体(返回数据)
     * @param fn   处理业务成功失败
     * @param retryCount 重试次数
     * @param sleepTime  重试间隔睡眠时间(注意：阻塞当前线程)
     * @param <T>   接收类型
     * @param <R>  返回类型
     */
    public static <T, R> R retryAnyFn(Retry<T> retry,
                                      BiFunction<? super T, Throwable, ? extends R> fn,
                                      int retryCount,
                                      long sleepTime) {
        return retryAnyFn(retry, null, retryCount, sleepTime, null, fn);
    }

    /**
     * 重试调度方法和处理
     * @param retry 方法执行体(返回数据)
     * @param exceptionCaught 出错异常处理(包括第一次执行和重试错误)
     * @param retryCount 重试次数
     * @param sleepTime  重试间隔睡眠时间(注意：阻塞当前线程)
     * @param expectExceptions 期待异常(抛出符合相应异常时重试), 空或者空容器默认进行重试
     * @param other  其他操作
     * @param <T>  返回数据类型
     */
    public static <T> T retrySuccessOrElseGet(Retry<T> retry,
                                              Consumer<Throwable> exceptionCaught,
                                              int retryCount,
                                              long sleepTime,
                                              List<Class<? extends Throwable>> expectExceptions,
                                              Supplier<T> other) {
        try {
            return retry(retry, exceptionCaught, retryCount, sleepTime, expectExceptions);
        } catch (RetryException e) {
            // 业务异常处理
            return null == other ? null : other.get();
        }
    }

    /**
     * 重试调度方法和处理
     * @param retry  方法执行体(返回数据)
     * @param exceptionCaught 出错异常处理(包括第一次执行和重试错误)
     * @param retryCount   重试次数
     * @param sleepTime  重试间隔睡眠时间(注意：阻塞当前线程)
     * @param expectExceptions 期待异常(抛出符合相应异常时重试), 空或者空容器默认进行重试
     * @param fn 处理业务成功失败
     * @param <T> 接收类型
     * @param <R> 返回类型
     */
    public static <T, R> R retryAnyFn(Retry<T> retry,
                                      Consumer<Throwable> exceptionCaught,
                                      int retryCount,
                                      long sleepTime,
                                      List<Class<? extends Throwable>> expectExceptions,
                                      BiFunction<? super T, Throwable, ? extends R> fn) {
        T retryEntity = null;
        Throwable ex = null;
        try {
            retryEntity = retry(retry, exceptionCaught, retryCount, sleepTime, expectExceptions);
        } catch (Throwable throwable) {
            ex = throwable;
        }
        return fn.apply(retryEntity, ex);
    }

    /**
     * 重试调度方法
     * @param retry     方法执行体(返回数据)
     * @param exceptionCaught  出错异常处理(包括第一次执行和重试错误)
     * @param retryCount       重试次数
     * @param sleepTime        重试间隔睡眠时间(注意：阻塞当前线程)
     * @param expectExceptions 期待异常(抛出符合相应异常时重试), 空或者空容器默认进行重试
     * @param <T>              返回数据类型
     */
    public static <T> T retry(Retry<T> retry,
                              Consumer<Throwable> exceptionCaught,
                              int retryCount,
                              long sleepTime,
                              List<Class<? extends Throwable>> expectExceptions) throws RetryException {
        Throwable ex;
        try {
            // 产生数据
            return retry == null ? null : retry.get();
        } catch (Throwable throwable) {
            ex = throwable;
        }
        // 校验异常是否匹配期待异常
        if (expectExceptions != null && ! expectExceptions.isEmpty()) {
            Class<? extends Throwable> exClass = ex.getClass();
            boolean match = expectExceptions.stream().anyMatch(clazz -> clazz == exClass);
            if (! match) {
                return null;
            }
        }

        // 匹配期待异常或者允许任何异常重试
        for (int i = 0; i < retryCount; i++) {
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
                return retry.get();
            } catch (InterruptedException e) {
                System.out.println("thread interrupted !! break retry, cause: " + e.getMessage());
                // 恢复中断信号
                Thread.currentThread().interrupt();
                // 线程中断直接退出重试
                break;
            } catch (Exception throwable) {
            }
        }
        throw new RetryException(ex);
    }



}
