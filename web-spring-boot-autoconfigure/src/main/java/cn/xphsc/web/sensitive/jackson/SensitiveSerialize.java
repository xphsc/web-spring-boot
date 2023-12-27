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
package cn.xphsc.web.sensitive.jackson;
import cn.xphsc.web.sensitive.annotation.Sensitive;
import cn.xphsc.web.sensitive.SensitiveType;
import cn.xphsc.web.sensitive.instance.InstanceSensitiveProperties;
import cn.xphsc.web.sensitive.instance.InstanceSensitiveHandler;
import cn.xphsc.web.utils.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: jackson的脱敏实现
 * @since 1.0.0
 */

public class SensitiveSerialize extends JsonSerializer<String> implements ContextualSerializer {

	private SensitiveType type;

	private Sensitive sensitive;
	Map<Object,Object> map=new HashMap<Object,Object>();
	public SensitiveSerialize() {
	}

	public SensitiveSerialize(final Sensitive sensitive) {
		this.type = sensitive.value();
		this.sensitive = sensitive;
	}

	@Override
	public void serialize(final String value, final JsonGenerator jsonGenerator,
						  final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		if(!InstanceSensitiveProperties.getInstance().isJacksonEnable()){
			jsonGenerator.writeString(value);
		}
		try {
			//有正则优先使用正则
			if(StringUtils.isNotBlank(sensitive.regexp())){
				jsonGenerator.writeString(value.replaceAll(sensitive.regexp(), sensitive.regReplaceChar()));
				return;
			}
			switch (this.type) {
				case CHINESE_NAME: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().chineseName(value),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().chineseName(value));
					break;
				}
				case ID_CARD:
				case MOBILE_PHONE: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().idCardNumber(value, sensitive.front(), sensitive.back()),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().idCardNumber(value, sensitive.front(), sensitive.back()));
					break;
				}
				case FIXED_PHONE: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().fixedPhone(value),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().fixedPhone(value));
					break;
				}
				case PASSWORD: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().password(value),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().password(value));
					break;
				}
				case ADDRESS: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().address(value, sensitive.address()),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().address(value, sensitive.address()));
					break;
				}
				case EMAIL: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().email(value),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().email(value));
					break;
				}
				case BANK_CARD: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().bankCardNo(value),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().bankCardNo(value));
					break;
				}
				case SHOPS_CODE: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().bankJointNum(value),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().bankJointNum(value));
					break;
				}
				case NULL: {
					jsonGenerator.writeString((String) null);
					break;
				}
				case CUSTOM: {
					map.put(InstanceSensitiveHandler.getSensitiveHandler().customJacksonHandler(value, sensitive),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().customJacksonHandler(value, sensitive));
					break;
				}
				case AUTO: {
				 	map.put(InstanceSensitiveHandler.getSensitiveHandler().currency(value),value);
					jsonGenerator.writeString(InstanceSensitiveHandler.getSensitiveHandler().currency(value));
					break;
				}
				default:{
					jsonGenerator.writeString(value);
				}

			}
		}catch (Exception e){
			jsonGenerator.writeString(value);
		}
	}

	@Override
	public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
											  final BeanProperty beanProperty) throws JsonMappingException {
		if (beanProperty != null) {
			if(!InstanceSensitiveProperties.getInstance().isJacksonEnable()){
				return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
			}
			Sensitive sensitive = beanProperty.getAnnotation(Sensitive.class);
			if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
				if (sensitive != null) {
					return new SensitiveSerialize(sensitive);
				}
			}
			if(sensitive != null && sensitive.value() == SensitiveType.NULL){
				return new SensitiveObjectSerialize(sensitive);
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(null);
	}

	public Map<Object, Object> getMap() {
		return map;
	}
}
