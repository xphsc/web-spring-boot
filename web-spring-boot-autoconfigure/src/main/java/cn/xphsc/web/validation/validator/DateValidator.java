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


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import cn.xphsc.web.common.validator.Validator;
import cn.xphsc.web.validation.constraints.Date;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 时间
 * @since 1.0.0
 */
public class DateValidator implements ConstraintValidator<Date, String> {

	public void initialize(Date constraintAnnotation)
	{
		
	}

	public boolean isValid(String date, ConstraintValidatorContext context) {
		boolean match = Validator.date(date);
		return match;
	}
	

}