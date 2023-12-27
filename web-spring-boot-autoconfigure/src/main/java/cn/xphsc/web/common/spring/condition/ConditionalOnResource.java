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
package cn.xphsc.web.common.spring.condition;

import org.springframework.context.annotation.Conditional;
import java.lang.annotation.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ConditionalOnResourceCondition.class)
public @interface ConditionalOnResource {

    /**
     * locations
     */
    public String[] value();

    /**
     * 存在性
     */
    public Existence existence() default Existence.ALL;

    /**
     * 类型
     */
    public Type type() default Type.ANY;

    public enum Existence {
        ALL, NONE, ANY
    }

    public enum Type {
        FILE, DIRECTORY, ANY
    }

}
