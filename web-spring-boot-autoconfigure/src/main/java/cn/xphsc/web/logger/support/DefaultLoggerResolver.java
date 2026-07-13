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
package cn.xphsc.web.logger.support;




import cn.xphsc.web.logger.LogResolver;
import cn.xphsc.web.logger.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: DefaultLoggerResolver
 * @since 2.0.4
 */
public class DefaultLoggerResolver implements LogResolver {
	private final Map<String, Logger> CACHE = new ConcurrentHashMap<>();

	@Override
	public Logger getLogger(String name) {
		return CACHE.computeIfAbsent(name, k -> newLogger(name));
	}


	private Logger newLogger(String name) {
		Logger logger = getDirectSlf4jLogger(name);
		if (logger != null) {
			return logger;
		}
		return new StdoutLogger(name);
	}

	private static Logger getDirectSlf4jLogger(String name) {
		try {
			org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(name);
			if (logger instanceof org.slf4j.spi.LocationAwareLogger) {
				return new Slf4jAwareLogger((org.slf4j.spi.LocationAwareLogger) logger);
			} else {
				return new Slf4jLogger(logger);
			}
		} catch (Throwable ignored) { // no dependency
		}
		return null;
	}
}
