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



import cn.xphsc.web.validation.validator.ChineseValidator;
import cn.xphsc.web.validation.validator.ValidatorConstant;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 是否中文验证注解
 * @since 1.0.0
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChineseValidator.class)
@Inherited
@Documented
public @interface Chinese {


    /**
     *  异常消息
     * @return
     */
    String message() default ValidatorConstant.CHINESE_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    /**
     *  中文or非中文 验证
     * @return
     */
    boolean whether() default true;
}
