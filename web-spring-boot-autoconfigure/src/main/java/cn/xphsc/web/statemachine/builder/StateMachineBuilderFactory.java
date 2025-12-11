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

import cn.xphsc.web.statemachine.builder.internal.StateMachineBuilderImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: State organization construction factory
 * @since 2.0.1
 */
public class StateMachineBuilderFactory<S, E, C> {
    private static Map<String, StateMachineBuilder> stateMachineBuilderMap = new ConcurrentHashMap<String, StateMachineBuilder>();

    /**
     * 创建一个状态机构建类
     * @param name 状态机名称
     * @param <S> 状态
     * @param <E> 事件
     * @param <C> 上下文
     * @return 状态机构建器
     */
    public static <S,E,C>StateMachineBuilder create(String name) {
        if (stateMachineBuilderMap.get(name) == null) {
            return new StateMachineBuilderImpl<S,E,C>(name);
        }
        return stateMachineBuilderMap.get(name);
    }
}
