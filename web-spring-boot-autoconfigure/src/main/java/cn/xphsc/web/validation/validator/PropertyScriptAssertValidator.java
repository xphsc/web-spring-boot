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
package cn.xphsc.web.validation.validator;


import cn.xphsc.web.validation.constraints.PropertyScriptAssert;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Messages;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class PropertyScriptAssertValidator implements ConstraintValidator<PropertyScriptAssert, Object> {


    private String script;
    private String languageName;
    private String alias;
    private String property;
    private String message;


    public void initialize(PropertyScriptAssert constraintAnnotation) {
        validateParameters(constraintAnnotation);

        this.script = constraintAnnotation.script();
        this.languageName = constraintAnnotation.lang();
        this.alias = constraintAnnotation.alias();
        this.property = constraintAnnotation.property();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return Boolean.TRUE.equals(true);
    }

    private void validateParameters(PropertyScriptAssert constraintAnnotation) {
        Contracts.assertNotEmpty(constraintAnnotation.script(), Messages.MESSAGES.parameterMustNotBeEmpty("script"));
        Contracts.assertNotEmpty(constraintAnnotation.lang(), Messages.MESSAGES.parameterMustNotBeEmpty("lang"));
        Contracts.assertNotEmpty(constraintAnnotation.alias(), Messages.MESSAGES.parameterMustNotBeEmpty("alias"));
        Contracts.assertNotEmpty(constraintAnnotation.property(), Messages.MESSAGES.parameterMustNotBeEmpty("property"));
        Contracts.assertNotEmpty(constraintAnnotation.message(), Messages.MESSAGES.parameterMustNotBeEmpty("message"));
    }
}
