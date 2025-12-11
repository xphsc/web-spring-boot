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





import cn.xphsc.web.statemachine.Action;
import cn.xphsc.web.statemachine.Condition;
import cn.xphsc.web.statemachine.Transition;
import cn.xphsc.web.statemachine.builder.TransitionBuilder;
import cn.xphsc.web.statemachine.provider.TransitionProvider;

import java.util.function.Consumer;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.1
 */
public class TransitionBuilderImpl<S, E, C> implements TransitionBuilder<S, E, C> {
    private TransitionProvider<S, E, C> transition = new TransitionProvider<>();
    private Consumer<Transition<S, E, C>> whenComplete;

    @Override
    public TransitionBuilder from(S sourceState) {
        transition.setSourceState(sourceState);
        return this;
    }

    @Override
    public TransitionBuilder to(S targetState) {
        transition.setTargetState(targetState);
        return this;
    }

    @Override
    public TransitionBuilder on(E event) {
        transition.setEvent(event);
        return this;
    }

    @Override
    public TransitionBuilder when(Condition<C> condition) {
        transition.setCondition(condition);
        return this;
    }

    @Override
    public void then(Action<S, E, C> action) {
        transition.setAction(action);
        whenComplete.accept(transition);
    }

    @Override
    public void whenComplete(Consumer<Transition<S, E, C>> consumer) {
        this.whenComplete = consumer;
    }
}
