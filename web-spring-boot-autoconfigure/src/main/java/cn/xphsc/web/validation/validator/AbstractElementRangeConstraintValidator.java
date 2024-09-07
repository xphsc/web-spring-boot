/*
 * Copyright (c) 2023 huipei.x
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
package cn.xphsc.web.validation.validator;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Set;


 /**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 元素范围约束检验器抽象实现
 * @since 1.2.0
 */
public abstract class AbstractElementRangeConstraintValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    private Set<T> elements;

    @Override
    public void initialize(A constraintAnnotation) {

        Set<T> elements = getElements(constraintAnnotation);
        if (CollectionUtils.isEmpty(elements)) {
            throw new ConstraintDeclarationException("The elements in the <" + constraintAnnotation.getClass().getName() + "> annotation must contain at least one element");
        }
        this.elements = elements;
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {

        if (!elements.contains(value)) {

            invalid(value, context);
            return false;
        }
        return true;
    }

    /**
     * 从注解中获取元素的Set集合
     *
     * @param constraintAnnotation constraintAnnotation
     * @return {@link Set#isEmpty()}必须为false
     */

    protected abstract Set<T> getElements(A constraintAnnotation);

    /**
     * 校验不通过时调用
     *
     * @param value   value
     * @param context ConstraintValidatorContext
     */
    protected void invalid(T value, ConstraintValidatorContext context) {

    }
}
