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

import org.slf4j.MDC;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Slf4jStack
 * @since 2.0.4
 */
class Slf4jStack implements Stack {
	public static final String DELIMITER = "|";
	public static final String STACK_KEY = "MSG";

	@Override
	public void put(String key, String val) {
		MDC.put(key, val);
	}

	@Override
	public String get(String key) {
		return MDC.get(key);
	}

	@Override
	public void remove(String key) {
		MDC.remove(key);
	}

	@Override
	public void clear() {
		MDC.clear();
	}

	@Override
	public void push(String msg) {
		String old = MDC.get(STACK_KEY);
		if (old == null || old.trim().length() == 0) {
			MDC.put(STACK_KEY, msg);
		} else {
			if (!old.contains(msg)) {
				MDC.put(STACK_KEY, old + DELIMITER + msg);
			}
		}
	}

	@Override
	public String pop() {
		String old = MDC.get(STACK_KEY);
		if (old == null || old.trim().length() == 0) {
			return "";
		}
		old = old.trim();
		int i = old.lastIndexOf(DELIMITER);
		if (i >= 0) {
			String pop = old.substring(i + 1).trim();
			String msg = old.substring(0, i).trim();
			if (msg == null || msg.trim().length() == 0) {
				MDC.remove(STACK_KEY);
			} else {
				MDC.put(STACK_KEY, msg);
			}
			return pop;
		}
		MDC.remove(STACK_KEY);
		return old;
	}

	@Override
	public String peek() {
		String old = MDC.get(STACK_KEY);
		if (old == null || old.trim().length() == 0) {
			return "";
		}
		old = old.trim();
		int i = old.lastIndexOf(DELIMITER);
		if (i >= 0) {
			String pop = old.substring(i + 1).trim();
			return pop;
		}
		return old;
	}
}
