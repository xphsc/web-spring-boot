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
package cn.xphsc.web.boot.i18n;

import cn.xphsc.web.common.WebBeanTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.Validator;
import static cn.xphsc.web.common.WebBeanTemplate.*;
import static cn.xphsc.web.utils.StringUtils.defaultString;


/**
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {I18nProperties.class})
@ConditionalOnProperty(prefix = I18N_PREFIX, name = ENABLED, havingValue = WebBeanTemplate.TRUE, matchIfMissing = true)
public class I18nAutoConfiguration {

    private final I18nProperties properties;
    private static final String DEFAULT_CHARSET = "UTF-8";

    public I18nAutoConfiguration(I18nProperties properties) {
        this.properties = properties;
    }

    private ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding(defaultString(properties.getDefaultEncoding(), DEFAULT_CHARSET));
        bundleMessageSource.setBasenames(properties.getBaseNames());
        return bundleMessageSource;
    }

    @ConditionalOnProperty(prefix = I18N_VALIDATOR_PREFIX, name = ENABLED)
    @Bean
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(getMessageSource());
        return validator;
    }
    @Bean
    public LocaleMessage localeMessage(MessageSource messageSource){
        return new LocaleMessage(messageSource);
    }
}
