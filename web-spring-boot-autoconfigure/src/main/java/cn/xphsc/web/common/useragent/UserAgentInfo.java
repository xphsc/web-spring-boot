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
package cn.xphsc.web.common.useragent;


import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class UserAgentInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String NameUnknown = "Unknown";

	/** 信息名称 */
	private final String name;
	/** 信息匹配模式 */
	private final Pattern pattern;

	/**
	 * 构造
	 *
	 * @param name 名字
	 * @param regex 表达式
	 */
	public UserAgentInfo(String name, String regex) {
		this(name, (null == regex) ? null : Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
	}

	/**
	 * 构造
	 *
	 * @param name 名字
	 * @param pattern 匹配模式
	 */
	public UserAgentInfo(String name, Pattern pattern) {
		this.name = name;
		this.pattern = pattern;
	}

	/**
	 * 获取信息名称
	 *
	 * @return 信息名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取匹配模式
	 *
	 * @return 匹配模式
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * 指定内容中是否包含匹配此信息的内容
	 *
	 * @param content User-Agent字符串
	 * @return 是否包含匹配此信息的内容
	 */
	public boolean isMatch(String content) {
		return Rexs.contains(this.pattern, content);
	}

	/**
	 * 是否为Unknown
	 *
	 * @return 是否为Unknown
	 */
	public boolean isUnknown() {
		return NameUnknown.equals(this.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UserAgentInfo other = (UserAgentInfo) obj;
		if (name == null) {
			return other.name == null;
		} else {return name.equals(other.name);}
	}

	@Override
	public String toString() {
		return this.name;
	}
}
