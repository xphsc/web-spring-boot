package cn.xphsc.web.boot.log.annotation;

import cn.xphsc.web.boot.log.advice.OperationLogRegistrar;
import org.springframework.context.annotation.Import;
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
@Import({OperationLogRegistrar.class})
public @interface EnableOperationLog {
    int order() default Integer.MAX_VALUE;
}
