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



import cn.xphsc.web.validation.validator.PropertyScriptAssertValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 脚本验证
 * @since 1.0.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PropertyScriptAssertValidator.class})
@Documented
public @interface PropertyScriptAssert {

    String message() default "{org.hibernate.validator.constraints.ScriptAssert.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String lang();

    /**
     *  脚本
     * @return
     */
    String script();

    /**
     *  别名
     * @return
     */
    String alias() default "_this";


    /**
     *  属性 出错后赋值属性
     * @return
     */
    String property();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        PropertyScriptAssert[] value();
    }
}
