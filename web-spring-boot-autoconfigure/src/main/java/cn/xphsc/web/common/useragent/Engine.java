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

import cn.xphsc.web.utils.Collects;

import java.util.List;
import java.util.regex.Pattern;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 引擎对象
 * @since 1.0.0
 */
public class Engine extends UserAgentInfo {
	private static final long serialVersionUID = 1L;

	/** 未知 */
	public static final Engine Unknown = new Engine(NameUnknown, null);

	/**
	 * 支持的引擎类型
	 */
	public static final List<Engine> engines =  Collects.newArrayList(//
			new Engine("Trident", "trident"), //
			new Engine("Webkit", "webkit"), //
			new Engine("Chrome", "chrome"), //
			new Engine("Opera", "opera"), //
			new Engine("Presto", "presto"), //
			new Engine("Gecko", "gecko"), //
			new Engine("KHTML", "khtml"), //
			new Engine("Konqeror", "konqueror"), //
			new Engine("MIDP", "MIDP")//
	);

	private final Pattern versionPattern;

	/**
	 * 构造
	 *
	 * @param name 引擎名称
	 * @param regex 关键字或表达式
	 */
	public Engine(String name, String regex) {
		super(name, regex);
		this.versionPattern = Pattern.compile(name + "[/\\- ]([\\d\\w.\\-]+)", Pattern.CASE_INSENSITIVE);
	}

	/**
	 * 获取引擎版本
	 *
	 * @param userAgentString User-Agent字符串
	 * @return 版本
	 * @since 5.7.4
	 */
	public String getVersion(String userAgentString) {
		if(isUnknown()){
			return null;
		}
		return Rexs.getGroup(this.versionPattern, userAgentString);
	}
}
