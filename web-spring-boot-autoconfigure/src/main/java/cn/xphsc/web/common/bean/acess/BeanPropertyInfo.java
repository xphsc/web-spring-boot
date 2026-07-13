/*
 * Copyright (c) 2025 huipei.x
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
package cn.xphsc.web.common.bean.acess;


import cn.xphsc.web.utils.ReflectUtils;
import cn.xphsc.web.utils.StringUtils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  BeanPropertyInfo
 * @since 2.0.4
 */
public class BeanPropertyInfo {

	private final String propertyName;
	private final Type propertyGenericType;
	private final Class<?> propertyType;
	private final Method writeMethod;
	private final Method readMethod;
	private final Field field;

	public String getPropertyName() {
		return propertyName;
	}

	public Type getPropertyGenericType() {
		return propertyGenericType;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public Method getWriteMethod() {
		return writeMethod;
	}

	public Method getReadMethod() {
		return readMethod;
	}

	public Field getField() {
		return field;
	}

	/**
	 * 要求：存在属性方法时，直接字段为空，只能通过属性方法访问属性
	 */
	private BeanPropertyInfo(String propertyName, Type propertyGenericType, Class<?> propertyType, Method writeMethod, Method readMethod, Field field) {
		this.propertyName = propertyName;
		this.propertyGenericType = propertyGenericType;
		this.propertyType = propertyType;
		this.writeMethod = writeMethod;
		this.readMethod = readMethod;
		this.field = field;
	}

	public boolean hasSetter() {
		return writeMethod != null || field != null;
	}

	public boolean hasGetter() {
		return readMethod != null || field != null;
	}

	public static BeanPropertyInfo of(PropertyDescriptor propertyDescriptor) {
		return of(propertyDescriptor.getName(), propertyDescriptor.getWriteMethod(), propertyDescriptor.getReadMethod(), null);
	}

	private static BeanPropertyInfo of(String propertyName, Type propertyGenericType, Class<?> propertyType, Method writeMethod, Method readMethod, Field field) {
		return new BeanPropertyInfo(propertyName, propertyGenericType, propertyType
			, writeMethod, readMethod, field);
	}

	private static BeanPropertyInfo of(String propertyName, Method writeMethod, Method readMethod, Field field) {
		propertyName = StringUtils.defaultString(propertyName);
		if (propertyName == null) {
			throw new IllegalArgumentException("propertyName is required");
		}
		if (readMethod == null && writeMethod == null && field == null) {
			throw new IllegalArgumentException("readMethodName or writeMethodName or field is required");
		}
		if (field != null) {
			if (!field.getName().equals(propertyName)) {
				throw new IllegalArgumentException("field name must be same as propertyName");
			}
			if (writeMethod != null || readMethod != null) {
				throw new IllegalArgumentException("field must be null when writeMethod or readMethod exists");
			}
		}
		Type propertyGenericType = null;
		Class<?> propertyType = null;
		if (writeMethod != null) {
			propertyGenericType = writeMethod.getGenericParameterTypes()[0];
			propertyType = writeMethod.getParameterTypes()[0];
		} else if (field != null) {
			propertyGenericType = field.getGenericType();
			propertyType = field.getType();
		} else {
			propertyGenericType = readMethod.getGenericReturnType();
			propertyType = readMethod.getReturnType();
		}
		if (propertyType == null) {
			propertyType = Object.class;
		}
		if (propertyGenericType == null) {
			propertyGenericType = propertyType;
		}

		return BeanPropertyInfo.of(propertyName, propertyGenericType, propertyType
			, writeMethod, readMethod, field);
	}

	public static List<BeanPropertyInfo> listOf(Class<?> beanType) {
		Map<String, BeanPropertyInfo> map = mapOf(beanType);
		List<BeanPropertyInfo> list = new ArrayList<>(map.size());
		list.addAll(map.values());
		return list;
	}

	public static Map<String, BeanPropertyInfo> mapOf(Class<?> beanType) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(beanType);
		} catch (IntrospectionException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		Map<String, BeanPropertyInfo> rs = new LinkedHashMap<>();

		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			Method writeMethod = pd.getWriteMethod();
			Method readMethod = pd.getReadMethod();
			if (ReflectUtils.isGetClassMethod(pd.getReadMethod())) {
				continue;
			}
			if (writeMethod != null || readMethod != null) {
				BeanPropertyInfo info = BeanPropertyInfo.of(pd.getName(), writeMethod, readMethod, null);
				rs.put(info.getPropertyName(), info);
			}
		}
		Map<String, Field> fields = new LinkedHashMap<>();
		{
			Class<?> nextClass = beanType;
			while (nextClass != null && nextClass != Object.class) {
				Field[] declaredFields = nextClass.getDeclaredFields();
				for (int i = 0, n = declaredFields.length; i < n; i++) {
					Field field = declaredFields[i];
					int modifiers = field.getModifiers();
					// 只支持public，视为另一种公开属性，忽略private、final、static等字段
					if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers)) {
						fields.putIfAbsent(field.getName(), field);
					}
				}
				nextClass = nextClass.getSuperclass();
			}
		}
		for (Map.Entry<String, Field> entry : fields.entrySet()) {
			String name = entry.getKey();
			// 针对缺少方法的情况，添加直接字段
			if (!rs.containsKey(name)) {
				BeanPropertyInfo info = BeanPropertyInfo.of(name, null, null, entry.getValue());
				rs.put(info.getPropertyName(), info);
			}
		}
		return rs;
	}

	/**
	 * 获取所有属性访问贪睡并返回划分setter、getter、field三种类型后的数组
	 */
	public static Classification classify(Class<?> beanType) {
		Map<String, BeanPropertyInfo> properties = mapOf(beanType);
		List<BeanPropertyInfo> setters = new ArrayList<>();
		List<BeanPropertyInfo> getters = new ArrayList<>();
		List<BeanPropertyInfo> fields = new ArrayList<>();
		for (Map.Entry<String, BeanPropertyInfo> entry : properties.entrySet()) {
			BeanPropertyInfo beanPropertyInfo = entry.getValue();
			if (beanPropertyInfo.getWriteMethod() != null) {
				setters.add(beanPropertyInfo);
			}
			if (beanPropertyInfo.getReadMethod() != null) {
				getters.add(beanPropertyInfo);
			}
			if (beanPropertyInfo.getField() != null) {
				fields.add(beanPropertyInfo);
			}
		}
		Classification rs = new Classification();
		rs.properties = properties;
		rs.setters = setters;
		rs.getters = getters;
		rs.fields = fields;
		return rs;
	}

	public static class Classification {
		public Map<String, BeanPropertyInfo> properties;
		public List<BeanPropertyInfo> setters;
		public List<BeanPropertyInfo> getters;
		public List<BeanPropertyInfo> fields;
	}
}
