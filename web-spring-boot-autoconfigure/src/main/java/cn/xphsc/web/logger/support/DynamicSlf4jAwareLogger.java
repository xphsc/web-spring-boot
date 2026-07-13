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



import cn.xphsc.web.logger.Logger;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: DynamicSlf4jAwareLogger
 * @since 2.0.4
 */
public class DynamicSlf4jAwareLogger implements Logger {
	private static final String FQCN = Slf4jAwareLogger.class.getName();
	private static final int TRACE_INT = 00;
	private static final int DEBUG_INT = 10;
	private static final int INFO_INT = 20;
	private static final int WARN_INT = 30;
	private static final int ERROR_INT = 40;

	private final Class<?> classLocationAwareLogger;
	private final Class<?> classMarker;
	private final Object instanceLogger;
	private final MethodHandle isTraceEnabled;
	private final MethodHandle isDebugEnabled;
	private final MethodHandle isInfoEnabled;
	private final MethodHandle isWarnEnabled;
	private final MethodHandle isErrorEnabled;
	private final MethodHandle log;

	public DynamicSlf4jAwareLogger(Class<?> classLocationAwareLogger, Class<?> classMarker, Object instanceLocationAwareLogger) throws NoSuchMethodException, IllegalAccessException {
		this.classLocationAwareLogger = classLocationAwareLogger;
		this.classMarker = classMarker;
		this.instanceLogger = instanceLocationAwareLogger;
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		this.isTraceEnabled = lookup.findVirtual(classLocationAwareLogger, "isTraceEnabled", MethodType.methodType(boolean.class));
		this.isDebugEnabled = lookup.findVirtual(classLocationAwareLogger, "isDebugEnabled", MethodType.methodType(boolean.class));
		this.isInfoEnabled = lookup.findVirtual(classLocationAwareLogger, "isInfoEnabled", MethodType.methodType(boolean.class));
		this.isWarnEnabled = lookup.findVirtual(classLocationAwareLogger, "isWarnEnabled", MethodType.methodType(boolean.class));
		this.isErrorEnabled = lookup.findVirtual(classLocationAwareLogger, "isErrorEnabled", MethodType.methodType(boolean.class));

		this.log = lookup.findVirtual(classLocationAwareLogger, "log", MethodType.methodType(void.class, classMarker, String.class, int.class, String.class, Object[].class, Throwable.class));
	}


	@Override
	public boolean isTraceEnabled() {
		try {
			return (boolean) isTraceEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public boolean isDebugEnabled() {
		try {
			return (boolean) isDebugEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public boolean isInfoEnabled() {
		try {
			return (boolean) isInfoEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public boolean isWarnEnabled() {
		try {
			return (boolean) isWarnEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public boolean isErrorEnabled() {
		try {
			return (boolean) isErrorEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public void trace(String msg) {
		trace("{}", new Object[]{msg}, null);
	}

	@Override
	public void trace(String msg, Object... arguments) {
		trace(msg, arguments, null);
	}

	@Override
	public void trace(String msg, Throwable t) {
		trace("{}", new Object[]{msg}, t);
	}

	@Override
	public void trace(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isTraceEnabled()) {
			try {
				log.invoke(instanceLogger, null, FQCN, TRACE_INT, msg, arguments, t);
			} catch (Throwable ignored) {
			}
		}
	}

	@Override
	public void trace(Throwable t, String msg, Object... arguments) {
		trace(msg, arguments, t);
	}

	@Override
	public void debug(String msg) {
		debug("{}", new Object[]{msg}, null);
	}

	@Override
	public void debug(String msg, Object... arguments) {
		debug(msg, arguments, null);
	}

	@Override
	public void debug(String msg, Throwable t) {
		debug("{}", new Object[]{msg}, t);
	}

	@Override
	public void debug(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isDebugEnabled()) {
			try {
				log.invoke(instanceLogger, null, FQCN, DEBUG_INT, msg, arguments, t);
			} catch (Throwable ignored) {
			}
		}
	}

	@Override
	public void debug(Throwable t, String msg, Object... arguments) {
		debug(msg, arguments, t);
	}

	@Override
	public void info(String msg) {
		info("{}", new Object[]{msg}, null);
	}

	@Override
	public void info(String msg, Object... arguments) {
		info(msg, arguments, null);
	}

	@Override
	public void info(String msg, Throwable t) {
		info("{}", new Object[]{msg}, t);
	}

	@Override
	public void info(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isInfoEnabled()) {
			try {
				log.invoke(instanceLogger, null, FQCN, INFO_INT, msg, arguments, t);
			} catch (Throwable ignored) {
			}
		}
	}

	@Override
	public void info(Throwable t, String msg, Object... arguments) {
		info(msg, arguments, t);
	}

	@Override
	public void warn(String msg) {
		warn("{}", new Object[]{msg}, null);
	}

	@Override
	public void warn(String msg, Object... arguments) {
		warn(msg, arguments, null);
	}

	@Override
	public void warn(String msg, Throwable t) {
		warn("{}", new Object[]{msg}, t);
	}

	@Override
	public void warn(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isWarnEnabled()) {
			try {
				log.invoke(instanceLogger, null, FQCN, WARN_INT, msg, arguments, t);
			} catch (Throwable ignored) {
			}
		}
	}

	@Override
	public void warn(Throwable t, String msg, Object... arguments) {
		warn(msg, arguments, t);
	}

	@Override
	public void error(String msg) {
		error("{}", new Object[]{msg}, null);
	}

	@Override
	public void error(String msg, Object... arguments) {
		error(msg, arguments, null);
	}

	@Override
	public void error(String msg, Throwable t) {
		error("{}", new Object[]{msg}, t);
	}


	@Override
	public void error(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isErrorEnabled()) {
			try {
				log.invoke(instanceLogger, null, FQCN, ERROR_INT, msg, arguments, t);
			} catch (Throwable ignored) {
			}
		}
	}

	@Override
	public void error(Throwable t, String msg, Object... arguments) {
		error(msg, arguments, t);
	}


}
