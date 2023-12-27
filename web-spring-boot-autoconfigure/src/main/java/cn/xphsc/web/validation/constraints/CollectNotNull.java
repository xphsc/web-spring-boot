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
package cn.xphsc.web.validation.constraints;

import cn.xphsc.web.validation.validator.CollectNotNullValidator;
import cn.xphsc.web.validation.validator.ValidatorConstant;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 验证集合中是否有空元素
 * @since 1.0.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CollectNotNullValidator.class)
public @interface CollectNotNull {

    String message() default ValidatorConstant.COLLECT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    /**
     * 定义List，为了让Bean的一个属性上可以添加多套规则
     */
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CollectNotNull[] value();
    }
}
