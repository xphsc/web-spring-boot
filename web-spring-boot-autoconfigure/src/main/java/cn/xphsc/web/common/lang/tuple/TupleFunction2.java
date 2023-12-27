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
package cn.xphsc.web.common.lang.tuple;

import cn.xphsc.web.common.lang.function.Function2;

import java.util.function.Function;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.4
 */
@FunctionalInterface
public interface TupleFunction2<T1, T2, R> extends Function<Tuple2<T1, T2>, R>, Function2<T1, T2, R> {

    @Override
    default R apply(Tuple2<T1, T2> tuple2) {
        return apply(tuple2.getT1(), tuple2.getT2());
    }

}