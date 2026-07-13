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


import cn.xphsc.web.common.lang.constant.Constants;
import java.util.StringJoiner;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Slf4jLoggers
 * @since 2.0.4
 */
public class Slf4jLoggers {
	private static String FQCN = Slf4jLoggers.class.getName();
	private static String DFT_NAME;

	static {
		String[] arr = Slf4jLoggers.class.getPackage().getName().split("\\.");
		StringJoiner joiner = new StringJoiner(".");
		joiner.add(arr[0]);
		if (arr.length > 1) {
			joiner.add(arr[1]);
		}
		DFT_NAME = joiner.toString();
	}

	public static org.slf4j.spi.LocationAwareLogger of() {
		return of(DFT_NAME);
	}

	public static org.slf4j.spi.LocationAwareLogger of(String name) {
		org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(name);
		if (logger instanceof org.slf4j.spi.LocationAwareLogger) {
			return (org.slf4j.spi.LocationAwareLogger) logger;
		}
		return null;
	}

	// region logName

	public static void trace(String logName, String msg, Object... arguments) {
		trace(logName, msg, arguments, null);
	}

	public static void trace(String logName, String msg, Throwable t) {
		trace(logName, msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void trace(String logName, String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of(logName);
		if (log != null && log.isTraceEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.TRACE_INT, msg, arguments, t);
		}
	}

	public static void trace(String logName, Throwable t, String msg, Object... arguments) {
		trace(logName, msg, arguments, t);
	}


	public static void debug(String logName, String msg, Object... arguments) {
		debug(logName, msg, arguments, null);
	}

	public static void debug(String logName, String msg, Throwable t) {
		debug(logName, msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void debug(String logName, String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of(logName);
		if (log != null && log.isDebugEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.DEBUG_INT, msg, arguments, t);
		}
	}

	public static void debug(String logName, Throwable t, String msg, Object... arguments) {
		debug(logName, msg, arguments, t);
	}


	public static void info(String logName, String msg, Object... arguments) {
		info(logName, msg, arguments, null);
	}

	public static void info(String logName, String msg, Throwable t) {
		info(logName, msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void info(String logName, String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of(logName);
		if (log != null && log.isInfoEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.INFO_INT, msg, arguments, t);
		}
	}

	public static void info(String logName, Throwable t, String msg, Object... arguments) {
		info(logName, msg, arguments, t);
	}

	public static void warn(String logName, String msg, Object... arguments) {
		warn(logName, msg, arguments, null);
	}

	public static void warn(String logName, String msg, Throwable t) {
		warn(logName, msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void warn(String logName, String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of(logName);
		if (log != null && log.isWarnEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.WARN_INT, msg, arguments, t);
		}
	}

	public static void warn(String logName, Throwable t, String msg, Object... arguments) {
		warn(logName, msg, arguments, t);
	}

	public static void error(String logName, String msg, Object... arguments) {
		error(logName, msg, arguments, null);
	}

	public static void error(String logName, String msg, Throwable t) {
		error(logName, msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void error(String logName, String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of(logName);
		if (log != null && log.isErrorEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.ERROR_INT, msg, arguments, t);
		}
	}

	public static void error(String logName, Throwable t, String msg, Object... arguments) {
		error(logName, msg, arguments, t);
	}

	// endregion


	// region default log

	public static void trace(String msg) {
		trace(msg,Constants.OBJECT_ARR_EMPTY, null);
	}

	public static void trace(String msg, Object[] arguments) {
		trace(msg, arguments, null);
	}

	public static void trace(String msg, Throwable t) {
		trace(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void trace(String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of();
		if (log != null && log.isTraceEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.TRACE_INT, msg, arguments, t);
		}
	}

	public static void trace(Throwable t, String msg, Object... arguments) {
		trace(msg, arguments, t);
	}

	public static void debug(String msg) {
		debug(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	public static void debug(String msg, Object[] arguments) {
		debug(msg, arguments, null);
	}

	public static void debug(String msg, Throwable t) {
		debug(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void debug(String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of();
		if (log != null && log.isDebugEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.DEBUG_INT, msg, arguments, t);
		}
	}

	public static void debug(Throwable t, String msg, Object... arguments) {
		debug(msg, arguments, t);
	}

	public static void info(String msg) {
		info(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	public static void info(String msg, Object[] arguments) {
		info(msg, arguments, null);
	}

	public static void info(String msg, Throwable t) {
		info(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void info(String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of();
		if (log != null && log.isInfoEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.INFO_INT, msg, arguments, t);
		}
	}

	public static void info(Throwable t, String msg, Object... arguments) {
		info(msg, arguments, t);
	}

	public static void warn(String msg) {
		warn(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	public static void warn(String msg, Object[] arguments) {
		warn(msg, arguments, null);
	}

	public static void warn(String msg, Throwable t) {
		warn(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	public static void warn(String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of();
		if (log != null && log.isWarnEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.WARN_INT, msg, arguments, t);
		}
	}

	public static void warn(Throwable t, String msg, Object... arguments) {
		warn(msg, arguments, t);
	}

	public static void error(String msg) {
		error(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	public static void error(String msg, Object[] arguments) {
		error(msg, arguments, null);
	}

	public static void error(String msg, Throwable t) {
		error(msg, Constants.OBJECT_ARR_EMPTY, t);
	}


	public static void error(String msg, Object[] arguments, Throwable t) {
		org.slf4j.spi.LocationAwareLogger log = of();
		if (log != null && log.isErrorEnabled()) {
			log.log(null, FQCN, org.slf4j.spi.LocationAwareLogger.ERROR_INT, msg, arguments, t);
		}
	}

	public static void error(Throwable t, String msg, Object... arguments) {
		error(msg, arguments, t);
	}

	// endregion
}
