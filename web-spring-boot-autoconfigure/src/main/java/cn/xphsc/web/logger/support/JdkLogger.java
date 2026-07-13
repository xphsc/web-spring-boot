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
import cn.xphsc.web.logger.Logger;
import cn.xphsc.web.utils.StringUtils;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: JdkLogger
 * @since 2.0.4
 */
public class JdkLogger implements Logger {
	static final String INNER_CALLER = JdkLogger.class.getName();
	private final java.util.logging.Logger logger;

	public JdkLogger(String name) {
		this.logger = java.util.logging.Logger.getLogger(name);
	}

	public JdkLogger(Class clazz) {
		this.logger = java.util.logging.Logger.getLogger(clazz.getName());
	}

	private void fillSource(LogRecord record) {
		fillSource(record, null);
	}

	private void fillSource(LogRecord record, Throwable throwable) {
		if (throwable != null) {
			record.setThrown(throwable);
		}
		if (record.getSourceClassName() == null) {
			if (throwable == null) {
				throwable = new Throwable();
			}
			final StackTraceElement[] frames = throwable.getStackTrace();
			for (int i = 0; i < frames.length; i++) {
				StackTraceElement frame = frames[i];
				String cname = frame.getClassName();
				if (!cname.equals(INNER_CALLER)) {
					record.setSourceClassName(cname);
					record.setSourceMethodName(frame.getMethodName());
					break;
				}
			}
		}
	}


	@Override
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.ALL);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.FINE);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
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
		if (isTraceEnabled()) {
			LogRecord lr = new LogRecord(Level.ALL, StringUtils.format(msg, arguments));
			fillSource(lr, t);
			logger.log(lr);
		}
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
		if (isDebugEnabled()) {
			LogRecord lr = new LogRecord(Level.FINE, StringUtils.format(msg, arguments));
			fillSource(lr, t);
			logger.log(lr);
		}
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
		if (isInfoEnabled()) {
			LogRecord lr = new LogRecord(Level.INFO, StringUtils.format(msg, arguments));
			fillSource(lr, t);
			logger.log(lr);
		}
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
		if (isWarnEnabled()) {
			LogRecord lr = new LogRecord(Level.WARNING, StringUtils.format(msg, arguments));
			fillSource(lr, t);
			logger.log(lr);
		}
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
		if (isErrorEnabled()) {
			LogRecord lr = new LogRecord(Level.SEVERE, StringUtils.format(msg, arguments));
			fillSource(lr, t);
			logger.log(lr);
		}
	}

	@Override
	public void error(Throwable t, String msg, Object... arguments) {
		error(msg, arguments, t);
	}

}
