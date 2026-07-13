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



import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Evaluator
 * @since 2.0.4
 */
public interface Evaluator  {
	String INPUT = "input";
	String OUTPUT = "output";
	String BINDINGS = "bindings";
	String RESULT = "result";

	/**
	 * 执行脚本
	 *
	 * @param scriptContent 脚本内容
	 * @param input         输入参数
	 * @param output        输出参数
	 * @param mergeBindings 其他绑定变量
	 * @return
	 */
	Object eval(String scriptContent, Object input, Object output, Map<String, Object> mergeBindings) throws ScriptEvalException;

	default Object eval(String scriptContent, Object input, Object output) throws ScriptEvalException {
		return eval(scriptContent, input, output, null);
	}

	default Object eval(String scriptContent, Object input) throws ScriptEvalException {
		return eval(scriptContent, input, null, null);
	}

	default Object eval(String scriptContent) throws ScriptEvalException {
		return eval(scriptContent, null, null, null);
	}


}
