/*
 * Copyright (c) 2021 huipei.x
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

import cn.xphsc.web.validation.constraints.CollectNotNull;
import org.springframework.util.CollectionUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 验证集合中是否有null元素
 * @since 1.0.0
 */
public class CollectNotNullValidator implements ConstraintValidator<CollectNotNull, Collection<?>> {


    @Override
    public void initialize(CollectNotNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Collection<?> objects, ConstraintValidatorContext constraintValidatorContext) {
        /**
         *  null不验证
         */
        if (null == objects) {
            return true;
        }

        if (CollectionUtils.isEmpty(objects)) {
            return false;
        }
        return objects.parallelStream().allMatch(v -> v != null);
    }
}

