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
package cn.xphsc.web.statemachine.builder;


import cn.xphsc.web.statemachine.StateMachine;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: The builder of state machines
 * @since 2.0.1
 */
public interface StateMachineBuilder<S, E, C> {

    /**
     * 构建一个状态机
     * @return 状态机实例
     */
    StateMachine build();


    /**
     * 初始化转换器的构建类
     * @return 转换构建器
     */
    TransitionBuilder transition();
}
