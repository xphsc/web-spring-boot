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
 * @description: Execute action
 * @since 2.0.1
 */
public interface Action<S, E, C> {
    /**
     * 具体执行动作
     * @param from 起始状态
     * @param to   目的状态
     * @param event 事件
     * @param context 上下文
     */
    public void execute(S from, S to, E event, C context);
}
