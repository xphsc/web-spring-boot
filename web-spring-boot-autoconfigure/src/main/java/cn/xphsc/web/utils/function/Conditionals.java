/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.utils.function;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: condition builder
 * @since 1.1.6
 */
public class Conditionals<T,R> {
    /**
     * 条件的值
     */
    private final T value;
    /**
     * 设置的条件
     */
    private final boolean condition;
    /**
     * 是否已经执行
     */
    private final boolean alreadyDo;
    /**
     * 是否是else类型的条件
     */
    private final boolean elseCondition;
    /**
     * 设置的结果
     */
    private final R result;
    /**
     * 空对象
     */
    private static final Conditionals<?,?> EMPTY = new Conditionals<>(null, null, false, false, false);

    private Conditionals(T value, R result, boolean condition, boolean alreadyDo, boolean elseCondition) {
        this.value = value;
        this.condition = condition;
        this.result = result;
        this.alreadyDo = alreadyDo;
        this.elseCondition = elseCondition;
    }

    public static Conditionals<?,?> empty() {
        return EMPTY;
    }

    /**
     * conditionValue map变换
     */
    public <U> Conditionals<U,R> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return thisObject();
        } else {
            return conditionBuilder().from(this).value(mapper.apply(this.value)).build();
        }
    }

    /**
     * conditionValue flatMap变换
     */
    public <U> Conditionals<U,R> flatMap(Function<? super T, Conditionals<U,R>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return thisObject();
        } else {
            return Objects.requireNonNull(mapper.apply(this.value));
        }
    }

    private <T,R> Conditionals<T,R> thisObject() {
        return (Conditionals<T,R>) this;
    }

    /**
     * 获取结果
     * @param consumer
     */
    public void result(Consumer<R> consumer) {
        Objects.requireNonNull(consumer);
        consumer.accept(result);
    }

    /**
     * 返回结果
     */
    public R result() {
        return this.result;
    }

    /**
     * 返回结果
     * @return
     */
    public Optional<R> optionalResult(){
        return Optional.ofNullable(this.result);
    }

    /**
     * @param resultHandler
     */
    public void handleResult(Consumer<R> resultHandler) {
        Objects.requireNonNull(resultHandler);
        resultHandler.accept(result);
    }

    /**
     * 设置conditionValue
     */
    public <T> Conditionals<T,R> set(T conditionValue) {
        return conditionBuilder().from(this).value(conditionValue).build();
    }

    /**
     * 初始化
     */
    public static <T> Conditionals<T,?> of(T value) {
        return new Conditionals<>(value, null, false, false, false);
    }

    /**
     * 但满足条件时，设置返回的结果
     */
    public <R> Conditionals<T,R> setResult(R result) {
        if(checkCondition()){
            return conditionBuilder().from(this).result(result).alreadyDo(true).build();
        }
        return thisObject();
    }

    /**
     * 但满足条件时，设置返回的结果
     */
    public Conditionals<T,T> setConditionValueAsResult() {
        if (checkCondition()) {
            return conditionBuilder().from(this).result(this.value).alreadyDo(true).build();
        }
        return thisObject();
    }


    public Conditionals<T,R> when(boolean condition) {
        return conditionBuilder().from(this).condition(condition).build();
    }

    public Conditionals<T,R> andWhen(boolean condition) {
        return conditionBuilder().from(this).condition(this.condition && condition).build();
    }

    public Conditionals<T,R> andWhenEquals(T value) {
        return conditionBuilder().from(this).condition(this.condition && (value != null && value.equals(this.value))).build();
    }

    public Conditionals<T,R> andWhen(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return thisObject();
        } else {
            return conditionBuilder().from(this).condition(this.condition && predicate.test(this.value)).build();
        }
    }

    public Conditionals<T,R> orWhen(boolean condition) {
        return conditionBuilder().from(this).condition(this.condition || condition).build();
    }

    public Conditionals<T,R> orWhenEquals(T value) {
        return conditionBuilder().from(this).condition(this.condition || (value != null && value.equals(this.value))).build();
    }

    public Conditionals<T,R> orWhen(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return thisObject();
        } else {
            return conditionBuilder().from(this).condition(this.condition || predicate.test(this.value)).build();
        }
    }

    public Conditionals<T,R> whenEquals(T value) {
        return conditionBuilder().from(this).condition(value != null && value.equals(this.value)).build();
    }

    public Conditionals<T,R> when(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return thisObject();
        } else {
            return conditionBuilder().from(this).condition(predicate.test(this.value)).build();
        }
    }


    public Conditionals<T,R> elseWhen(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return thisObject();
        } else {
            return conditionBuilder().from(this).condition(predicate.test(this.value)).elseCondition(true).build();
        }
    }

    public Conditionals<T,R> elseWhen(boolean condition) {
        return conditionBuilder().from(this).condition(condition).elseCondition(true).build();
    }

    public Conditionals<T,R> elseWhenEquals(T value) {
        return conditionBuilder().from(this).condition(value != null && value.equals(this.value)).elseCondition(true).build();
    }

    /**
     * 当满足条件时执行
     * @param conditionValueConsumer
     * @return
     */
    public Conditionals<T,R> toDo(Consumer<T> conditionValueConsumer) {
        Objects.requireNonNull(conditionValueConsumer);
        if (checkCondition()) {
            conditionValueConsumer.accept(this.value);
            return conditionBuilder().from(this).condition(condition).alreadyDo(true).build();
        } else {
            return thisObject();
        }
    }

    /**
     * 当满足条件时执行
     * @param doFunction
     * @return
     */
    public Conditionals<T,R> toDo(DoFunction doFunction) {
        Objects.requireNonNull(doFunction);
        if (checkCondition()) {
            doFunction.excute();
            return conditionBuilder().from(this).condition(condition).alreadyDo(true).build();
        } else {
            return thisObject();
        }
    }

    /**
     * 当满足条件时执行
     * @param resultConsumer
     * @return
     */
    public Conditionals<T,R> toDoWithResult(Consumer<R> resultConsumer) {
        Objects.requireNonNull(resultConsumer);
        if (checkCondition()) {
            resultConsumer.accept(this.result);
            return conditionBuilder().from(this).condition(condition).alreadyDo(true).build();
        } else {
            return thisObject();
        }
    }

    /**
     * 当满足条件时抛出异常
     * @param exception
     * @throws Exception
     */
    public Conditionals<T,R> doThrow(Exception exception) throws Exception {
        if (checkCondition()) {
            throw exception;
        }
        return this;
    }

    /**
     * 当满足条件时抛出异常
     * @param supplierFunction
     * @param <U>
     * @throws U
     */
    public <U extends Exception> Conditionals<T,R> doThrow(Function<? super T,U> supplierFunction) throws U {
        if (checkCondition()) {
            throw supplierFunction.apply(this.value);
        }
        return this;
    }

    /**
     * 无条件执行
     * @param conditionValueConsumer
     */
    public Conditionals<T,R> alwaysDo(Consumer<T> conditionValueConsumer) {
        Objects.requireNonNull(conditionValueConsumer);
        conditionValueConsumer.accept(this.value);
        return thisObject();
    }

    /**
     * 无条件执行
     * @param doFunction
     */
    public Conditionals<T,R> alwaysDo(DoFunction doFunction) {
        Objects.requireNonNull(doFunction);
        doFunction.excute();
        return thisObject();
    }

    /**
     * 当未执行过时，执行
     * @param conditionValueConsumer
     */
    public Conditionals<T,R> elseDo(Consumer<T> conditionValueConsumer) {
        Objects.requireNonNull(conditionValueConsumer);
        if (!this.alreadyDo) {
            conditionValueConsumer.accept(this.value);
        }
        return thisObject();
    }

    /**
     * 当未执行过时，执行
     * @param doFunction
     */
    public Conditionals<T,R> elseDo(DoFunction doFunction) {
        Objects.requireNonNull(doFunction);
        if (!this.alreadyDo) {
            doFunction.excute();
        }
        return thisObject();
    }

    private boolean checkCondition() {
        return (this.condition && !this.elseCondition) || (this.condition && this.elseCondition && !this.alreadyDo);
    }

    private ConditionBuilder conditionBuilder() {
        return new ConditionBuilder();
    }

    private boolean isPresent() {
        return this.value != null;
    }

    @FunctionalInterface
    interface DoFunction {
        void excute();
    }

    class ConditionBuilder {

        private Object value;
        private boolean condition;
        private boolean alreadyDo;
        private boolean elseCondition;
        private Object result;

        ConditionBuilder from(Conditionals<T,R> condition) {
            this.value = condition.value;
            this.condition = condition.condition;
            this.alreadyDo = condition.alreadyDo;
            this.result = condition.result;
            this.elseCondition = condition.elseCondition;
            return this;
        }

        ConditionBuilder clearElseCondition() {
            this.elseCondition = false;
            return this;
        }

        ConditionBuilder value(Object value) {
            this.value = value;
            return this;
        }

        ConditionBuilder condition(boolean condition) {
            this.condition = condition;
            return this;
        }

        ConditionBuilder alreadyDo(boolean isAlreadyDo) {
            this.alreadyDo = isAlreadyDo;
            return this;
        }

        ConditionBuilder elseCondition(boolean elseCondition) {
            this.elseCondition = elseCondition;
            return this;
        }

        ConditionBuilder result(Object result) {
            this.result = result;
            return this;
        }

        <T,R> Conditionals<T,R> build() {
            return new Conditionals<>((T) this.value, (R)this.result, this.condition, this.alreadyDo, this.elseCondition);
        }

    }
}