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


import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.common.lang.date.format.DatesFormat;
import cn.xphsc.web.common.lang.function.consumer.ConsumerWithArgs4;
import cn.xphsc.web.logger.Level;
import cn.xphsc.web.logger.Logger;
import cn.xphsc.web.utils.StringUtils;

import java.time.Instant;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: StdoutLogger
 * @since 2.0.4
 */
public class StdoutLogger implements Logger {
	private final String name;
	private final Level level;
	private ConsumerWithArgs4<Level, String, Object[], Throwable> printer;

	public StdoutLogger(String name) {
		this.name = name;
		Level level;
		try {
			String levelStr = System.getProperty("logger.level" + "." + name);
			level = Level.valueOf(StringUtils.coalesce(levelStr, Level.DEBUG.name()).toLowerCase());
		} catch (Throwable e) {
			level = Level.DEBUG;
		}
		this.level = level;
	}

	public StdoutLogger(String name, Level level, ConsumerWithArgs4<Level, String, Object[], Throwable> printer) {
		this.name = name;
		this.level = level;
		this.printer = printer;
	}

	private void print(Level level, String msg, Object[] arguments, Throwable t) {
		if (this.level.ordinal() > level.ordinal()) {
			return;
		}
		if (printer != null) {
			printer.accept(level, msg, arguments, t);
			return;
		}
		String delimiter = " ";
		long tid = Thread.currentThread().getId();
		if (arguments != null && arguments.length > 0) {
			System.out.println(DatesFormat.YYYY_MM_DD_HH_MM_SS_SSS_FORMATTER.format(Instant.now()) + delimiter + level + delimiter
				+ "[" + tid + "]" + delimiter + name + delimiter + StringUtils.format(msg, arguments));
		} else {
			System.out.println(DatesFormat.YYYY_MM_DD_HH_MM_SS_SSS_FORMATTER.format(Instant.now()) + delimiter + level + delimiter
				+ "[" + tid + "]" + delimiter + name + delimiter + msg);
		}
		if (t != null) {
			t.printStackTrace(System.out);
		}
	}

	@Override
	public boolean isTraceEnabled() {
		return level.ordinal() <= Level.TRACE.ordinal();
	}

	@Override
	public boolean isDebugEnabled() {
		return level.ordinal() <= Level.DEBUG.ordinal();
	}

	@Override
	public boolean isInfoEnabled() {
		return level.ordinal() <= Level.INFO.ordinal();
	}

	@Override
	public boolean isWarnEnabled() {
		return level.ordinal() <= Level.WARN.ordinal();
	}

	@Override
	public boolean isErrorEnabled() {
		return level.ordinal() <= Level.ERROR.ordinal();
	}

	@Override
	public void trace(String msg) {
		trace(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void trace(String msg, Object... arguments) {
		trace(msg, arguments, null);
	}

	@Override
	public void trace(String msg, Throwable t) {
		trace(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	@Override
	public void trace(String msg, Object[] arguments, Throwable t) {
		print(Level.TRACE, msg, arguments, t);
	}

	@Override
	public void trace(Throwable t, String msg, Object... arguments) {
		trace(msg, arguments, t);
	}

	@Override
	public void debug(String msg) {
		debug(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void debug(String msg, Object... arguments) {
		debug(msg, arguments, null);
	}

	@Override
	public void debug(String msg, Throwable t) {
		debug(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	@Override
	public void debug(String msg, Object[] arguments, Throwable t) {
		print(Level.DEBUG, msg, arguments, t);
	}


	@Override
	public void debug(Throwable t, String msg, Object... arguments) {
		debug(msg, arguments, t);
	}

	@Override
	public void info(String msg) {
		info(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void info(String msg, Object... arguments) {
		info(msg, arguments, null);
	}

	@Override
	public void info(String msg, Throwable t) {
		info(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	@Override
	public void info(String msg, Object[] arguments, Throwable t) {
		print(Level.INFO, msg, arguments, t);
	}

	@Override
	public void info(Throwable t, String msg, Object... arguments) {
		info(msg, arguments, t);
	}

	@Override
	public void warn(String msg) {
		warn(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void warn(String msg, Object... arguments) {
		warn(msg, arguments, null);
	}

	@Override
	public void warn(String msg, Throwable t) {
		warn(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	@Override
	public void warn(String msg, Object[] arguments, Throwable t) {
		print(Level.WARN, msg, arguments, t);
	}

	@Override
	public void warn(Throwable t, String msg, Object... arguments) {
		warn(msg, arguments, t);
	}

	@Override
	public void error(String msg) {
		error(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void error(String msg, Object... arguments) {
		error(msg, arguments, null);
	}

	@Override
	public void error(String msg, Throwable t) {
		error(msg, Constants.OBJECT_ARR_EMPTY, t);
	}


	@Override
	public void error(String msg, Object[] arguments, Throwable t) {
		print(Level.ERROR, msg, arguments, t);
	}

	@Override
	public void error(Throwable t, String msg, Object... arguments) {
		error(msg, arguments, t);
	}

}
