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

import cn.xphsc.web.validation.constraints.Matches;
import cn.xphsc.web.utils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class MatchesValidator implements ConstraintValidator<Matches, Object> {
	private String field;
	private String verifyField;

	public void initialize(Matches matches) {
		this.field = matches.field();
		this.verifyField = matches.verifyField();
	}

	@SuppressWarnings("deprecation")
	public boolean isValid(Object value, ConstraintValidatorContext context) {
			String fieldValue = PropertyUtils.getProperty(value, field);
			String verifyFieldValue = PropertyUtils.getProperty(value, verifyField);
			boolean valid = (fieldValue == null) && (verifyFieldValue == null);
			if (valid) {
				return true;
			}

			boolean match = (fieldValue != null) && fieldValue.equals(verifyFieldValue);
			if (!match) {
				String messageTemplate = context.getDefaultConstraintMessageTemplate();
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(messageTemplate).addNode(verifyField).addConstraintViolation();
			}
			return match;
	}
}