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

import org.apache.logging.log4j.ThreadContext;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Log4j2Stack
 * @since 2.0.4
 */
class Log4j2Stack implements Stack {

	@Override
	public void put(String key, String val) {
		ThreadContext.put(key, val);
	}

	@Override
	public String get(String key) {
		return ThreadContext.get(key);
	}

	@Override
	public void remove(String key) {
		ThreadContext.remove(key);
	}

	@Override
	public void clear() {
		ThreadContext.clearAll();
	}

	@Override
	public void push(String msg) {
		ThreadContext.push(msg);
	}

	@Override
	public String pop() {
		return ThreadContext.pop();
	}

	@Override
	public String peek() {
		return ThreadContext.peek();
	}
}
