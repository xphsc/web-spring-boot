package cn.xphsc.web.boot.validation.autoconfiguration;

import cn.xphsc.web.boot.validation.advice.ValidationControllerAdvice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import static cn.xphsc.web.common.WebBeanTemplate.ENABLED;
import static cn.xphsc.web.common.WebBeanTemplate.VALIDATION_PREFIX;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix =VALIDATION_PREFIX, name = ENABLED )
@Import(ValidationControllerAdvice.class)
public class ValidationAdviceAutoConfiguration  {
}
