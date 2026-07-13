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
package cn.xphsc.web.logger;

import cn.xphsc.web.logger.support.DefaultLoggerResolver;
import cn.xphsc.web.logger.support.StdoutLogger;
import cn.xphsc.web.utils.StringUtils;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Loggers
 * @since 2.0.4
 */
public class Loggers {

	private static LogResolver RESOLVER = new DefaultLoggerResolver();

	public static Logger of(Class<?> c) {
		if (RESOLVER == null) {
			return new StdoutLogger(c.getName());
		}
		return RESOLVER.getLogger(c);
	}

	public static Logger of(String name) {
		if (RESOLVER == null) {
			return new StdoutLogger(name);
		}
		return RESOLVER.getLogger(name);
	}

	public static Logger of() {
		return of(detectLoggerName());
	}

	private static String detectLoggerName() {
		String name = null;
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < elements.length; i++) {
			String className = elements[i].getClassName();
			if (!Loggers.class.getName().equals(className)) {
				name = className;
				break;
			}
		}
		return StringUtils.coalesce(name, "");
	}

	public static void setResolver(LogResolver resolver) {
		RESOLVER = resolver;
	}

}
