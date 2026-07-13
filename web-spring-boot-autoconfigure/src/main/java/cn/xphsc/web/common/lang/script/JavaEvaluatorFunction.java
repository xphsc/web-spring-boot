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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  JavaEvaluatorFunction
 * @since 2.0.4
 */
public abstract class JavaEvaluatorFunction {
	protected static final Logger log = LoggerFactory.getLogger(JavaEvaluatorFunction.class);

	public JavaEvaluatorFunction() {
	}

	public Object eval(Object input, Object output, Map<String, Object> bindings) {
		return doEval(input, output, bindings);
	}

	protected Object doEval(Object input, Object output, Map<String, Object> bindings) {
		return null;
	}

}
