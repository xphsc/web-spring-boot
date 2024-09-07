/*
 * Copyright (c) 2023 huipei.x
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
package cn.xphsc.web.validation.constraints;

import cn.xphsc.web.validation.validator.StringRangeConstraintValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 字符串元素范围约束注解
 * 当参数<code>string</code>不为"a","b"之一时，则校验不通过，反之通过
 * @since 1.2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = {StringRangeConstraintValidator.class})
public @interface StringRange {

    /**
     * 字符串元素范围
     * @return value 至少必须配置一个元素
     */
    String[] value();

    boolean trim() default false;

    boolean ignoreCase() default false;

    String message() default "{cn.xphsc.web.validation.constraints.StringRange.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
