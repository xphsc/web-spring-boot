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

import cn.xphsc.web.common.validator.Validator;
import cn.xphsc.web.utils.PropertyUtils;
import cn.xphsc.web.validation.constraints.Identification;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 验证身份证件（身份证、居民证、护照、回乡证、台胞证、港澳通行证等）
 * @since 1.0.0
 */
public class IdentificationValidator implements ConstraintValidator<Identification, Object> {

	private String idTypeField;
	private String idCardField;


	public void initialize(Identification constraintAnnotation) {
		this.idTypeField = constraintAnnotation.idTypeField();
		this.idCardField = constraintAnnotation.idCardField();
	}

	@SuppressWarnings("deprecation")
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean flag = isValid2(value,context);;
		if (!flag) {
			String messageTemplate = context.getDefaultConstraintMessageTemplate();
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(messageTemplate).addNode(idTypeField).addConstraintViolation();
			context.buildConstraintViolationWithTemplate(messageTemplate).addNode(idCardField).addConstraintViolation();
		}
		return flag;
	}
	
	public boolean isValid2(Object value, ConstraintValidatorContext context) {
		try
		{
			String idTypeFieldValue = PropertyUtils.getProperty(value, idTypeField);
			String idCardFieldValue = PropertyUtils.getProperty(value, idCardField);
			if (idTypeFieldValue == null || idCardFieldValue==null)  {
				return true;
			}
			if("IDCARD".equals(idTypeFieldValue))
			{
				IdCardValidator idCardNumberValidator = new IdCardValidator();
				return idCardNumberValidator.isValid(idCardFieldValue, context);
			}else if("TAINWAESENCARD".equals(idTypeFieldValue))
			{
				return Validator.taiwaneseCard(idTypeFieldValue);
			}else if("RETURNPERMITCARD".equals(idTypeFieldValue))
			{
				return Validator.returnPermitCard(idTypeFieldValue);
			}else if("PASSPORT".equals(idTypeFieldValue)){
				return Validator.passportCard(idTypeFieldValue);
			}else if("RESIDENCEPERMIT".equals(idTypeFieldValue))
			{
				return Validator.residencePermit(idTypeFieldValue);
			}else if("HONEKONGPASS".equals(idTypeFieldValue))
			{
				return Validator.honeKongPass(idTypeFieldValue);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}




	public String getIdTypeField()
	{
		return idTypeField;
	}

	public void setIdTypeField(String idTypeField)
	{
		this.idTypeField = idTypeField;
	}

	public String getIdCardField()
	{
		return idCardField;
	}

	public void setIdCardField(String idCardField)
	{
		this.idCardField = idCardField;
	}


}