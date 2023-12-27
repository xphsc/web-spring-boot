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
package cn.xphsc.web.common.lang.function;


/**
 * {@link if}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: short supplier
 * @since 1.1.7
 */
@FunctionalInterface
public interface ShortSupplier {

    /**
     * 获取 short 值
     *
     * @return short
     */
    short getAsShort();

}
