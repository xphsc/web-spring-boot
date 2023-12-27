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

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class UserAgent implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 浏览器类型
	 */
	private Browser browser;
	/**
	 * 浏览器版本
	 */
	private String version;

	/**
	 * 平台类型
	 */
	private Platform platform;

	/**
	 * 系统类型
	 */
	private OS os;
	/**
	 * 系统版本
	 */
	private String osVersion;

	/**
	 * 引擎类型
	 */
	private Engine engine;
	/**
	 * 引擎版本
	 */
	private String engineVersion;




	/**
	 * 获取浏览器类型
	 *
	 * @return 浏览器类型
	 */
	public Browser getBrowser() {
		return browser;
	}

	/**
	 * 设置浏览器类型
	 *
	 * @param browser 浏览器类型
	 */
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	/**
	 * 获取平台类型
	 *
	 * @return 平台类型
	 */
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * 设置平台类型
	 *
	 * @param platform 平台类型
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	/**
	 * 获取系统类型
	 *
	 * @return 系统类型
	 */
	public OS getOs() {
		return os;
	}

	/**
	 * 设置系统类型
	 *
	 * @param os 系统类型
	 */
	public void setOs(OS os) {
		this.os = os;
	}

	/**
	 * 获取系统版本
	 *
	 * @return 系统版本
	 */
	public String getOsVersion() {
		return this.osVersion;
	}

	/**
	 * 设置系统版本
	 *
	 * @param osVersion 系统版本
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 * 获取引擎类型
	 *
	 * @return 引擎类型
	 */
	public Engine getEngine() {
		return engine;
	}

	/**
	 * 设置引擎类型
	 *
	 * @param engine 引擎类型
	 */
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	/**
	 * 获取浏览器版本
	 *
	 * @return 浏览器版本
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 设置浏览器版本
	 *
	 * @param version 浏览器版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 获取引擎版本
	 *
	 * @return 引擎版本
	 */
	public String getEngineVersion() {
		return engineVersion;
	}

	/**
	 * 设置引擎版本
	 *
	 * @param engineVersion 引擎版本
	 */
	public void setEngineVersion(String engineVersion) {
		this.engineVersion = engineVersion;
	}

}
