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
package cn.xphsc.web.sensitive.annotation;


import cn.xphsc.web.sensitive.SensitiveType;
import cn.xphsc.web.sensitive.jackson.SensitiveSerialize;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * {@link SensitiveSerialize}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Desensitization of sensitive data
 * For reference, the example
 *public class TestModel {
 * @Sensitive(value = SensitiveType.PASSWORD)
 * private String password;}
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface Sensitive {
	SensitiveType value() default SensitiveType.REGEXP;

	/**
	 * number前面保留几位
	 * @return
	 */
	int front() default 3;

	/**
	 * number后面保留几位
	 */
	int back() default 3;

	/**
	 * 地址默认保留前几位
	 */
	int address() default 8;

	/**
	 * 自定义正则匹配规则
	 */
	String regexp() default "";

	/**
	 * 正则替换字符
	 */
	String regReplaceChar() default "*";

	/**
	 * 自定义处理标识
	 */
	String tag() default "custom";
}
