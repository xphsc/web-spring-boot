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

import cn.xphsc.web.validation.constraints.StringRange;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 字符串元素范围约束检验器
 * @since 1.2.0
 */
public class StringRangeConstraintValidator extends AbstractElementRangeConstraintValidator<StringRange, String> {

    private boolean trim;
    private boolean ignoreCase;

    @Override
    protected Set<String> getElements(StringRange stringRange) {

        this.trim = stringRange.trim();
        this.ignoreCase = stringRange.ignoreCase();
        return Stream.of(stringRange.value()).map(this::applyValue).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return super.isValid(applyValue(value), context);
    }

    private String applyValue(String value) {

        String v = trim ? value.trim() : value;
        return ignoreCase ? v.toUpperCase() : v;
    }
}
