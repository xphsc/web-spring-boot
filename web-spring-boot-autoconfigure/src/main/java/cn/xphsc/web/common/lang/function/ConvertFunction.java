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
package cn.xphsc.web.common.lang.function;

import java.util.function.Function;


/**
 * {@link FunctionalInterface}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 转化接口
 * @since 1.1.6
 */
@FunctionalInterface
public interface ConvertFunction<T, R> extends Function<T, R> {

    /**
     * 转化
     * @param t t
     * @return R
     */
    R apply(T t);

    /**
     * Function -> Conversion
     *
     * @param f   Function
     * @param <T> T
     * @param <R> R
     * @return Conversion
     */
    static <T, R> ConvertFunction<T, R> with(Function<T, R> f) {
        return f::apply;
    }

}
