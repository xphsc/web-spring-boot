/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.statemachine;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: State machine condition, determine whether to execute subsequent actions based on context
 * @since 2.0.1
 */
public interface Condition<C> {
    /**
     * 判断条件是否满足,
     * @param context 上下文
     * @return true，继续执行；false中断执行，返回初始状态
     */
    boolean Condition(C context);
}
