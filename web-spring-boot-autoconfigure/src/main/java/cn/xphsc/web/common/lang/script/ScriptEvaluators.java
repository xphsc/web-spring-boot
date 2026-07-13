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
package cn.xphsc.web.common.lang.script;

import cn.xphsc.web.common.lang.io.Streams;
import cn.xphsc.web.logger.Logger;
import cn.xphsc.web.logger.Loggers;
import cn.xphsc.web.utils.ObjectUtils;
import cn.xphsc.web.utils.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  ScriptEvaluators
 * @since 2.0.4
 */
public class ScriptEvaluators {
	private static final Logger log = Loggers.of(ScriptEvaluators.class);
	private static final String FILE_PREFIX = "file:";
	private static final String CLASSPATH_PREFIX = "classpath:";
	private static final Map<String, Evaluator> engineMap = new ConcurrentHashMap<>();
	private static Evaluator defaultEngine;

	static {
			try {;
				Evaluator evaluator = new JavaEvaluator();
				String engineName=null;
				if (ObjectUtils.isEmpty(evaluator)) {
					String simpleName = evaluator.getClass().getSimpleName();
					engineName = simpleName.replaceFirst(Evaluator.class.getSimpleName() + "$", "");
				}
				if (!hasEvaluator(engineName)) {
					register(engineName, evaluator);
					register(engineName.toUpperCase(), evaluator);
					register(engineName.toLowerCase(), evaluator);
					if (defaultEngine == null) {
						defaultEngine = evaluator;
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		if (defaultEngine == null) {
			defaultEngine = new JavaScriptEvaluator();
		}
	}

	public static boolean hasEvaluator(String engineName) {
		return engineMap.containsKey(engineName);
	}

	public static Evaluator getEvaluator(String engineName) {
		return engineMap.get(engineName);
	}

	public static void register(String engineName, Evaluator evaluator) {
		engineMap.put(engineName, evaluator);
	}


	public static Object eval(String content, Map<String, Object> input, Map<String, Object> output, Map<String, Object> mergeBindings)
		throws ScriptEvalException {
		return defaultEngine.eval(content, input, output, mergeBindings);
	}

	public static Object evalFile(String file, Map<String, Object> input, Map<String, Object> output, Map<String, Object> mergeBindings)
		throws IOException, ScriptEvalException {
		return defaultEngine.eval(getContent(file), input, output, mergeBindings);
	}

	public static Object eval(String engineName, String content, Map<String, Object> input, Map<String, Object> output, Map<String, Object> mergeBindings)
		throws ScriptEvalException {
		Evaluator evaluator = engineMap.get(engineName);
		return evaluator.eval(content, input, output, mergeBindings);
	}

	public static Object evalFile(String engineName, String file, Map<String, Object> input, Map<String, Object> output, Map<String, Object> mergeBindings)
		throws IOException, ScriptEvalException {
		Evaluator evaluator = engineMap.get(engineName);
		return evaluator.eval(getContent(file), input, output, mergeBindings);
	}

	private static String getContent(String path) throws IOException {
		try (InputStream in = getInputStream(path)) {
			return Streams.toString(in, Charset.defaultCharset());
		}
	}

	private static InputStream getInputStream(String path) throws FileNotFoundException {
		InputStream in = null;
		boolean isClasspath = path.startsWith(CLASSPATH_PREFIX);
		String resource;
		if (isClasspath) {
			resource = path.substring(CLASSPATH_PREFIX.length());
		} else {
			if (path.startsWith(FILE_PREFIX)) {
				resource = path.substring(FILE_PREFIX.length());
			} else {
				resource = path;
			}
		}
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (isClasspath) {
			in = classLoader.getResourceAsStream(resource);
		}
		if (in == null) {
			try {
				in = new FileInputStream(resource);
			} catch (FileNotFoundException e) {
				in = classLoader.getResourceAsStream(resource);
				if (in == null) {
					in = ClassLoader.getSystemResourceAsStream(resource);
				}
				if (in == null) {
					throw new FileNotFoundException(resource);
				}
			}
		}
		return in;
	}
}
