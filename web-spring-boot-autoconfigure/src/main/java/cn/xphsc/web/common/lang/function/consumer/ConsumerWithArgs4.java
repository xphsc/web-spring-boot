package cn.xphsc.web.common.lang.function.consumer;

import java.util.Objects;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: ConsumerWithArgs4
 * @since 2.0.4
 */
@FunctionalInterface
public interface ConsumerWithArgs4<A, B, C, D> {

    void accept(A a, B b, C c, D d);

    default ConsumerWithArgs4<A, B, C, D> andThen(ConsumerWithArgs4<? super A, ? super B, ? super C, ? super D> after) {
        Objects.requireNonNull(after);
        return (a, b, c, d) -> {
            accept(a, b, c, d);
            after.accept(a, b, c, d);
        };
    }
}

