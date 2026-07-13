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



import cn.xphsc.web.common.lang.compiler.MemoryCompiler;
import cn.xphsc.web.common.lang.crypto.Digests;
import cn.xphsc.web.common.lang.function.SerializableFunctionWithArgs4;
import cn.xphsc.web.logger.Logger;
import cn.xphsc.web.logger.Loggers;
import cn.xphsc.web.utils.ReflectUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  JavaEvaluator
 * @since 2.0.4
 */
public class JavaEvaluator implements Evaluator {
	public static final String ENGINE_NAME = "java";
	private static final Logger log = Loggers.of(JavaEvaluator.class);
	private static final AtomicLong CLASS_NO = new AtomicLong(0);
	private static final Pattern importPattern = Pattern.compile("\\s*\\bimport\\s+(static\\s+)?[\\w\\.\\*]+;\\s*+");

	private Map<String, JavaEvaluatorFunction> cache = new HashMap<>(0x1000);

	static String nextClassName() {
		return JavaEvaluatorFunction.class.getName() + "Impl" + CLASS_NO.incrementAndGet();
	}

	static String asClassContent(String className, String classBody, String inputType, String outputType) {
		StringBuilder sb = new StringBuilder();
		String simpleName = className;
		{
			int i = className.lastIndexOf(".");
			if (i > 0) {
				sb.append("package ").append(className, 0, i).append(";\n");
				simpleName = className.substring(i + 1);
			}
		}
		sb.append("import java.math.*;").append("\n");
		sb.append("import java.util.*;").append("\n");
		sb.append("import java.util.function.*;").append("\n");
		sb.append("import java.sql.*;").append("\n");

		StringBuffer body = new StringBuffer();
		{
			Matcher matcher = importPattern.matcher(classBody);
			while (matcher.find()) {
				sb.append(matcher.group()).append("\n");
				matcher.appendReplacement(body, "");
			}
			matcher.appendTail(body);
			classBody = body.toString();
			body.setLength(0);
		}

		sb.append("public class ").append(simpleName).append(" extends ").append(JavaEvaluatorFunction.class.getName());
		sb.append("{");
		sb.append("\n");

		sb.append("public ").append(simpleName).append("(){");
		sb.append("super();");
		sb.append("\n");
		sb.append("}");
		sb.append("\n");

		// region override
		sb.append("public Object ")
			.append(ReflectUtils.getLambdaMethodName(
				(SerializableFunctionWithArgs4<JavaEvaluatorFunction, Object, Object, Map<String, Object>, Object>) JavaEvaluatorFunction::doEval))
			.append("(Object _input, Object _output, Map<String, Object> ").append(BINDINGS).append("){\n");
		sb.append(inputType).append(" ").append(INPUT).append(" = (").append(inputType).append(")_input;").append("\n");
		sb.append(outputType).append(" ").append(OUTPUT).append(" = (").append(outputType).append(")_output;").append("\n");
		sb.append(classBody).append("\n");
		sb.append("}");
		sb.append("\n");
		// endregion

		sb.append("}");
		sb.append("\n");
		return sb.toString();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Object eval(String scriptContent, Object input, Object output, Map<String, Object> mergeBindings) throws ScriptEvalException {
		try {
			String inputType = input == null ? Map.class.getName() : input.getClass().getName();
			String outputType = output == null ? Map.class.getName() : output.getClass().getName();
			String sha1 = Base64.getEncoder().encodeToString(
				Digests.sha1(scriptContent + "\n" + inputType + "\n" + outputType)
			);
			JavaEvaluatorFunction bean = cache.get(sha1);
			if (bean == null) {
				synchronized (this) {
					bean = cache.get(sha1);
					if (bean == null) {
						String className = nextClassName();
						String content = asClassContent(className, scriptContent, inputType, outputType);
						Class<?> clazz = MemoryCompiler.getInstance().compile(className, content);
						bean = (JavaEvaluatorFunction) clazz.newInstance();
						cache.put(sha1, bean);
					}
				}
			}

			if (input == null) {
				input = new LinkedHashMap<>();
			}
			if (output == null) {
				output = new LinkedHashMap<>();
			}
			Map<String, Object> bindings = new HashMap<>();
			if (mergeBindings != null) {
				bindings.putAll(mergeBindings);
			} else {
				if (input instanceof Map) {
					bindings.putAll((Map) input);
				}
			}
			bindings.put(INPUT, input);
			bindings.put(OUTPUT, output);
			Object rs = bean.eval(input, output, bindings);
			if (output instanceof Map) {
				((Map) output).putIfAbsent(RESULT, rs);
			}
			return rs;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ScriptEvalException(e.getMessage(), e);
		}
	}
}
