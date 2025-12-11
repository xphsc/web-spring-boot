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
package cn.xphsc.web.log.annotation;


import cn.xphsc.web.common.annotation.Language;
import cn.xphsc.web.log.entity.ActionType;

import java.lang.annotation.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Application log
 * @since 1.0.0
 * @since 2.0.3 Deprecated
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SysOperationLog {
    /**
     *名称
     */
    String name() default "";
    /**
     *模块名
     * @since 2.0.3
     */
    String moduleName() default "";
    /**
     * 操作人
     * @since 2.0.3
     */
    @Language("SpEL")
    String operator() default "";
    /**
     *方法说明
     */
    String description() default "";
    /**
     *操作类型
     */
    ActionType actionType() default ActionType.AUTO;

    /**
     * 操作日志的类型
     * @since 2.0.3
     */

     String type() default "";

    /**
     *业务标识
     * @since 2.0.3
     */
    @Language("SpEL")
   String bizNo() default "";
    /**
     *扩展
     */
    @Language("SpEL")
    String  extra() default "";

    /**
     *内容 [content="#name"] or [content="'内容'+ #name"]
     */
    @Language("SpEL")
    String content() default "";

}
