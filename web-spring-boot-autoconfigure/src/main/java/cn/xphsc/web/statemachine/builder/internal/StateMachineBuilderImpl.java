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
package cn.xphsc.web.statemachine.builder.internal;




import cn.xphsc.web.statemachine.StateMachine;
import cn.xphsc.web.statemachine.Transition;
import cn.xphsc.web.statemachine.builder.StateMachineBuilder;
import cn.xphsc.web.statemachine.builder.TransitionBuilder;
import cn.xphsc.web.common.exception.StateMachineException;
import cn.xphsc.web.statemachine.provider.StateMachineProvider;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.1
 */
public class StateMachineBuilderImpl<S, E, C> implements StateMachineBuilder<S, E, C> {
    private final String stateMachineName;
    private Map<String, Transition<S, E, C>> transitionMap = new ConcurrentHashMap<>();
    private StateMachine stateMachine = new StateMachineProvider(transitionMap);

    public StateMachineBuilderImpl(String stateMachineName) {
        this.stateMachineName = stateMachineName;
    }


    @Override
    public StateMachine build() {
        if (stateMachine == null || stateMachine.getReady()) {
            throw new StateMachineException("stateMachine is null or already build");
        }
        stateMachine.setName(stateMachineName);
        stateMachine.setReady(true);
        return stateMachine;
    }

    @Override
    public TransitionBuilder<S, E, C> transition() {
        TransitionBuilderImpl<S, E, C> transitionBuilder = new TransitionBuilderImpl<>();
        transitionBuilder.whenComplete((transition) -> {
            transitionMap.put(transition.getKey(), transition);
        });
        return transitionBuilder;
    }
}
