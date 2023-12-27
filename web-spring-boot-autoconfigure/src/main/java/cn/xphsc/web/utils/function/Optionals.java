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
package cn.xphsc.web.utils.function;
import cn.xphsc.web.common.lang.tuple.Tuple;
import cn.xphsc.web.common.lang.tuple.Tuple2;
import cn.xphsc.web.common.lang.tuple.TupleFunction2;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
/**
 * {@link Optional}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Optional
 * @since 1.1.4
 */
public class Optionals {

    /**
     * 在java8中，{@link Optional} 只有 {@link Optional#ifPresent(Consumer)} 和 {@link Optional#orElseGet(Supplier)}，
     * 其中 {@link Optional#orElseGet(Supplier)} 必须要有返回值，必要的时候只能返回  {@link Void} 类型，不够优雅且冗余。
     * java8中在不需要返回值的情况下，缺少同时对{@link Optional} 中有值和没值2种情况的处理的方法，所以定义了这个方法，
     * 如果2种情况都需要处理，同时定义更加符合函数式编程的风格。
     * 不建议使用了。可以用如下代码实现相同功能
     * {@code Optional.of("")
     *          .<Runnable>map(x -> ()  -> {
     *              // exist value do something
     *          })
     *          .orElse(() -> {
     *              // no value do something
     *          })
     *   .run();}
     */
    public static <T> OptionalConsumer<T> ifPresentOrElse(Consumer<T> c, Runnable r) {
        return new OptionalConsumer<>(c, r);
    }

    public static class OptionalConsumer<T> implements Consumer<Optional<T>> {

        private final Consumer<T> c;
        private final Runnable r;

        OptionalConsumer(Consumer<T> c, Runnable r) {
            this.c = c;
            this.r = r;
        }

        @Override
        public void accept(Optional<T> t) {
            if (t.isPresent()) {
                c.accept(t.get());
            } else {
                r.run();
            }
        }
    }


    public static class Fors {

        private Fors() {
            throw new UnsupportedOperationException("The class cannot be instantiated");
        }

        public static <T1, T2> Function<T1, Optional<Tuple2<T1, T2>>> For(Function<T1, Optional<T2>> f) {
            return t1 -> f.apply(t1).map(t2 -> Tuple.of(t1, t2));
        }

        public static <T1, R> Function<T1, R> Yield(Function<T1, R> f) {
            return f;
        }

        public static <T1, T2, R> Function<Tuple2<T1, T2>, R> Yield(TupleFunction2<T1, T2, R> f) {
            return f;
        }


    }
}
