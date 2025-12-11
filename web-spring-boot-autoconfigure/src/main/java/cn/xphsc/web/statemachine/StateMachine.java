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
 * @description: State machine interface. Provided the fire method for triggering the state machine.
 * @since 2.0.1
 */
public interface StateMachine<S, E, C> {

    /**
     * 触发状态机
     * @param sourceState 触发状态
     * @param event 事件
     * @param ctx   上下文
     * @return 目标状态
     */
    S fire(S sourceState, E event, C ctx);

    void setName(String name);

    void setReady(boolean ready);

    boolean getReady();
}
