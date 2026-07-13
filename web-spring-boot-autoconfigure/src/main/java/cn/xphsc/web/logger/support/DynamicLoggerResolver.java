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



import cn.xphsc.web.common.lang.reflect.ClassLoaders;
import cn.xphsc.web.logger.LogResolver;
import cn.xphsc.web.logger.Logger;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: DynamicLoggerResolver
 * @since 2.0.4
 */
public class DynamicLoggerResolver implements LogResolver {
	public static final String PREFER_DYNAMIC_SLF4J = DynamicLoggerResolver.class.getName() + ".prefer-dynamic-slf4j";
	private final Map<String, Logger> CACHE = new ConcurrentHashMap<>();
	private volatile long lastLoaderChangeId =new ClassLoaders().getChangeId();
	private boolean preferDynamicSlf4j=false;

	public DynamicLoggerResolver() {
		this.preferDynamicSlf4j = parseBoolean(PREFER_DYNAMIC_SLF4J);
	}

	@Override
	public Logger getLogger(String name) {
		if (new ClassLoaders().getChangeId() != lastLoaderChangeId) {
			lastLoaderChangeId = new ClassLoaders().getChangeId();
			CACHE.clear();
		}
		return CACHE.computeIfAbsent(name, k -> newLogger(name));
	}


	private Logger newLogger(String name) {
		Logger logger = null;
		if (preferDynamicSlf4j) {
			logger = getDynamicSlf4jLogger(name);
			if (logger != null) {
				return logger;
			}
		}
		logger = getDirectSlf4jLogger(name);
		if (logger != null) {
			return logger;
		}
		if (!preferDynamicSlf4j) {
			logger = getDynamicSlf4jLogger(name);
			if (logger != null) {
				return logger;
			}
		}
		return new StdoutLogger(name);
	}

	private static Logger getDynamicSlf4jLogger(String name) {
		try {
			Class<?> classLoggerFactory = new ClassLoaders().loadClass("org.slf4j.LoggerFactory");
			Class<?> classLogger = new ClassLoaders().loadClass("org.slf4j.Logger");
			Class<?> classLocationAwareLogger = new ClassLoaders().loadClass("org.slf4j.spi.LocationAwareLogger");
			Class<?> classMarker = new ClassLoaders().loadClass("org.slf4j.Marker");
			MethodHandles.Lookup lookup = MethodHandles.lookup();
			MethodType methodType = MethodType.methodType(classLogger, new Class[]{String.class});
			MethodHandle handle = lookup.findStatic(classLoggerFactory, "getLogger", methodType);
			Object logger = handle.invokeWithArguments(name);
			if (classLocationAwareLogger.isInstance(logger)) {
				return new DynamicSlf4jAwareLogger(classLocationAwareLogger, classMarker, logger);
			} else {
				return new DynamicSlf4jLogger(classLogger, logger);
			}
		} catch (ClassNotFoundException | NoClassDefFoundError ignored) {
		} catch (Throwable e) {
			// noinspection CallToPrintStackTrace
			e.printStackTrace();
		}
		return null;
	}

	private static Logger getDirectSlf4jLogger(String name) {
		try {
			org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(name);
			if (logger instanceof org.slf4j.spi.LocationAwareLogger) {
				return new Slf4jAwareLogger((org.slf4j.spi.LocationAwareLogger) logger);
			} else {
				return new Slf4jLogger(logger);
			}
		} catch (NoClassDefFoundError ignored) {
		} catch (Throwable e) {
			// noinspection CallToPrintStackTrace
			e.printStackTrace();
		}
		return null;
	}
	public static boolean parseBoolean(String s) {
		if (s == null || s.isEmpty()) {
			return false;
		}
		return s.equalsIgnoreCase("true")
				|| s.equalsIgnoreCase("on")
				|| s.equalsIgnoreCase("yes")
				|| s.equalsIgnoreCase("1")
				|| s.equalsIgnoreCase("y")
				|| s.equalsIgnoreCase("t");
	}

}
