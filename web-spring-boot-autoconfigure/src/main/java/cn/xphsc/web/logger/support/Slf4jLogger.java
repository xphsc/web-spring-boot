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

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Slf4jLogger
 * @since 2.0.4
 */
public class Slf4jLogger implements Logger {

	private org.slf4j.Logger log;

	public Slf4jLogger(org.slf4j.Logger log) {
		this.log = log;
	}

	@Override
	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}

	@Override
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
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
		if (log != null && log.isTraceEnabled()) {
			if (t == null) {
				if (arguments == null || arguments.length == 0) {
					log.trace(msg);
				} else {
					log.trace(msg, arguments);
				}
			}else{
				if (arguments == null || arguments.length == 0) {
					log.trace(msg, t);
				} else {
					log.trace(StringUtils.format(msg, arguments), t);
				}
			}
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
		if (log != null && log.isDebugEnabled()) {
			if (t == null) {
				if (arguments == null || arguments.length == 0) {
					log.debug(msg);
				} else {
					log.debug(msg, arguments);
				}
			}else{
				if (arguments == null || arguments.length == 0) {
					log.debug(msg, t);
				} else {
					log.debug(StringUtils.format(msg, arguments), t);
				}
			}
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
		if (log != null && log.isInfoEnabled()) {
			if (t == null) {
				if (arguments == null || arguments.length == 0) {
					log.info(msg);
				} else {
					log.info(msg, arguments);
				}
			}else{
				if (arguments == null || arguments.length == 0) {
					log.info(msg, t);
				} else {
					log.info(StringUtils.format(msg, arguments), t);
				}
			}
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
		if (log != null && log.isWarnEnabled()) {
			if (t == null) {
				if (arguments == null || arguments.length == 0) {
					log.warn(msg);
				} else {
					log.warn(msg, arguments);
				}
			}else{
				if (arguments == null || arguments.length == 0) {
					log.warn(msg, t);
				} else {
					log.warn(StringUtils.format(msg, arguments), t);
				}
			}
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
		if (log != null && log.isErrorEnabled()) {
			if (t == null) {
				if (arguments == null || arguments.length == 0) {
					log.error(msg);
				} else {
					log.error(msg, arguments);
				}
			}else{
				if (arguments == null || arguments.length == 0) {
					log.error(msg, t);
				} else {
					log.error(StringUtils.format(msg, arguments), t);
				}
			}
		}
	}

	@Override
	public void error(Throwable t, String msg, Object... arguments) {
		error(msg, arguments, t);
	}


}
