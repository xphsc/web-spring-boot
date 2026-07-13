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


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: LogStack
 * @since 2.0.4
 */
public class LogStack {

	private static Stack stack;

	static {
		try {
			org.apache.logging.log4j.ThreadContext.peek(); // 执行log4j2的方法, 确定可用
			stack = new Log4j2Stack();
		} catch (Throwable ignored) {
		}
		if (stack == null) {
			try{
				// noinspection ResultOfMethodCallIgnored
				org.slf4j.MDC.getMDCAdapter(); // 执行slf4j的方法, 确定可用
				stack = new Slf4jStack();
			}catch (Throwable ignored){
			}
		}
		if (stack == null) {
			stack = new NoopStack();
		}
	}

	public static void put(String key, String val) {
		stack.put(key, val);
	}

	public static String get(String key) {
		return stack.get(key);
	}

	public static void remove(String key) {
		stack.remove(key);
	}

	public static void clear() {
		stack.clear();
	}

	public static void push(String msg) {
		stack.push(msg);
	}

	public static void pushIfAbsent(String msg) {
		String last = stack.peek();
		if (!Objects.equals(last, msg)) {
			stack.push(msg);
		}
	}

	public static void pop(String msg) {
		Deque<String> queue = new ArrayDeque<>();
		while (true) {
			String pop = pop();
			if (pop == null || pop.trim().length() == 0 || pop.equals(msg)) {
				break;
			} else {
				queue.addLast(pop);
			}
		}
		for (String s : queue) {
			push(s);
		}
	}

	public static String pop() {
		return stack.pop();
	}

	public static String peek() {
		return stack.peek();
	}

}
