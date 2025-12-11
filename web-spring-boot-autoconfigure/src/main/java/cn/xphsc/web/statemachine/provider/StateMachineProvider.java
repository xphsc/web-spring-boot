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
package cn.xphsc.web.statemachine.provider;





import cn.xphsc.web.statemachine.StateMachine;
import cn.xphsc.web.statemachine.Transition;
import cn.xphsc.web.common.exception.StateMachineException;

import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.1
 */
public class StateMachineProvider<S, E, C> implements StateMachine<S, E, C> {
    private String name;
    private final Map<String, Transition> transitionMap;
    private boolean ready;

    public StateMachineProvider(Map<String, Transition> transitionMap) {
        this.transitionMap = transitionMap;
    }


    @Override
    public S fire(S sourceState, E event, C ctx) {
        isReady();
        Transition<S, E, C> transition = transitionMap.get(Transitions.buildTransitionKey(sourceState, event));
        if (transition == null) {
            throw new StateMachineException("No transition found for source state %s and event %s", sourceState, event);
        }
        return transition.translate(ctx, true);
    }

    private void isReady() {
        if (!ready) {
            throw new StateMachineException("statemachine is not ready");
        }
    }

    public boolean getReady(){
        return ready;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
