package cn.xphsc.web.boot.dicttraslate.annotation;

import cn.xphsc.web.boot.dicttraslate.advice.DictRegistrar;
import cn.xphsc.web.boot.dicttraslate.advice.DictTransMapRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({DictRegistrar.class, DictTransMapRegistrar.class})
public @interface EnableDicTrans {

    int dictTransOrder() default Ordered.LOWEST_PRECEDENCE-9999;

    int dictTransMapOrder() default Ordered.LOWEST_PRECEDENCE-9998;
}
