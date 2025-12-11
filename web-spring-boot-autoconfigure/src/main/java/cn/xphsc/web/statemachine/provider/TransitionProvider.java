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


import cn.xphsc.web.statemachine.Action;
import cn.xphsc.web.statemachine.Condition;
import cn.xphsc.web.statemachine.Transition;
import cn.xphsc.web.common.exception.StateMachineException;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.1
 */
public class TransitionProvider<S, E, C> implements Transition<S, E, C> {
    private S sourceState;
    private S targetState;
    private E event;
    private Condition<C> condition;
    private Action<S, E, C> action;


    @Override
    public String getKey() {
        return Transitions.buildTransitionKey(sourceState, event);
    }

    public void setSourceState(S sourceState) {
        this.sourceState = sourceState;
    }

    public void setTargetState(S targetState) {
        this.targetState = targetState;
    }

    public void setEvent(E event) {
        this.event = event;
    }

    public void setCondition(Condition<C> condition) {
        this.condition = condition;
    }

    public void setAction(Action<S, E, C> action) {
        this.action = action;
    }

    @Override
    public S translate(C context, boolean checkCondition) {
        if (checkCondition) {
            if (condition == null || !condition.Condition(context)) {
                return sourceState;
            }
        }
        if (action == null) {
            throw new StateMachineException("transition:%s,action is null", getKey());
        }
        action.execute(sourceState, targetState, event, context);
        return targetState;
    }
}
