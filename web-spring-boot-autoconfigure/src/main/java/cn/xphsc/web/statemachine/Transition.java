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
 * @description:
 * @since 2.0.1
 */
public interface Transition<S, E, C> {
    String getKey();

    void setSourceState(S sourceState);

    void setTargetState(S targetState);

    void setEvent(E event);

    void setCondition(Condition<C> condition);

    void setAction(Action<S, E, C> action);

    /**
     *  状态机中translate的核心实现，先查看是否有当前transition, 如果有则执行transition
     * @param context 上下文
     * @param checkCondition 是否检查条件
     * @return 返回状态
     */
     S translate(C context, boolean checkCondition);
}
