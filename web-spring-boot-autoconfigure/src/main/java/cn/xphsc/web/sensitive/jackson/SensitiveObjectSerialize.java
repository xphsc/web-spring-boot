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
import cn.xphsc.web.utils.ClassUtils;
import cn.xphsc.web.sensitive.instance.InstanceSensitiveProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.util.Objects;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: jackson的脱敏实现
 * @since 1.0.0
 */
public class SensitiveObjectSerialize extends JsonSerializer<Object> implements ContextualSerializer {

	private SensitiveType type;
	public SensitiveObjectSerialize() {
	}

	public SensitiveObjectSerialize(final Sensitive sensitiveInfo) {
		this.type = sensitiveInfo.value();
	}

	@Override
	public void serialize(final Object s, final JsonGenerator jsonGenerator,
						  final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		if(!InstanceSensitiveProperties.getInstance().isJacksonEnable()){
			jsonGenerator.writeObject(s);
		}
		try {
			if(this.type == SensitiveType.NULL && !ClassUtils.isBaseType(s.getClass())){
				jsonGenerator.writeObject(null);
				return;
			}
			jsonGenerator.writeObject(s);
		}catch (Exception e){
			jsonGenerator.writeObject(s);
		}
	}

	@Override
	public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
											  final BeanProperty beanProperty) throws JsonMappingException {
		if (beanProperty != null) {
			if(!InstanceSensitiveProperties.getInstance().isJacksonEnable()){
				return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
			}
			if (Objects.equals(beanProperty.getType().getRawClass(), Object.class)) {
				Sensitive sensitiveInfo = beanProperty.getAnnotation(Sensitive.class);
				if (sensitiveInfo == null) {
					sensitiveInfo = beanProperty.getContextAnnotation(Sensitive.class);
				}
				if (sensitiveInfo != null && sensitiveInfo.value() == SensitiveType.NULL) {
					return new SensitiveObjectSerialize(sensitiveInfo);
				}
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(null);
	}
}
