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
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Logger
 * @since 2.0.4
 */
public interface Logger {

	boolean isTraceEnabled();

	boolean isDebugEnabled();

	boolean isInfoEnabled();

	boolean isWarnEnabled();

	boolean isErrorEnabled();

	void trace(String msg);

	void trace(String msg, Object... arguments);

	void trace(String msg, Throwable t);

	void trace(String msg, Object[] arguments, Throwable t);

	void trace(Throwable t, String msg, Object... arguments);

	void debug(String msg);

	void debug(String msg, Object... arguments);

	void debug(String msg, Throwable t);

	void debug(String msg, Object[] arguments, Throwable t);

	void debug(Throwable t, String msg, Object... arguments);

	void info(String msg);

	void info(String msg, Object... arguments);

	void info(String msg, Throwable t);

	void info(String msg, Object[] arguments, Throwable t);

	void info(Throwable t, String msg, Object... arguments);

	void warn(String msg);

	void warn(String msg, Object... arguments);

	void warn(String msg, Throwable t);

	void warn(String msg, Object[] arguments, Throwable t);

	void warn(Throwable t, String msg, Object... arguments);

	void error(String msg);

	void error(String msg, Object... arguments);

	void error(String msg, Throwable t);

	void error(String msg, Object[] arguments, Throwable t);

	void error(Throwable t, String msg, Object... arguments);
}
