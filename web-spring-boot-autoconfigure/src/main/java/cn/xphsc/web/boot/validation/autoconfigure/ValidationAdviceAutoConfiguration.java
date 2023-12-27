package cn.xphsc.web.boot.validation.autoconfigure;

import cn.xphsc.web.boot.validation.advice.ValidationControllerAdvice;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static cn.xphsc.web.common.WebBeanTemplate.*;

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

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation
                .byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
