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
package cn.xphsc.web.dicttraslate.annotation;

import cn.xphsc.web.dicttraslate.handler.DictTransHandler;

import java.lang.annotation.*;

/**
 * {@link DictTransHandler}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 标记方法返回值，将进行字典自动转义.
 * @see DictField
 * @see  DictTransHandler
 * @since 1.0.0
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictTranslation {

    Class<? extends DictTransHandler> dictTransHandler() ;
}
