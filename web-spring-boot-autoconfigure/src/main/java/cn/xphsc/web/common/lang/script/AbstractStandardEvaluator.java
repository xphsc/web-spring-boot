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


import cn.xphsc.web.common.lang.crypto.Digests;
import cn.xphsc.web.logger.Logger;
import cn.xphsc.web.logger.Loggers;

import javax.script.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  AbstractStandardEvaluator
 * @since 2.0.4
 */
public abstract class AbstractStandardEvaluator implements Evaluator {
	private static final Logger log = Loggers.of(AbstractStandardEvaluator.class);

	public static final String OUT = "out";
	public static final String ERR = "err";

	protected final ScriptEngineManager manager = new ScriptEngineManager();
	protected final ScriptEngine scriptEngine;
	private boolean compilable;
	private Map<String, CompiledScript> cache;

	public AbstractStandardEvaluator() {
		String engineName = getEngineName();
		scriptEngine = manager.getEngineByName(engineName);
		if (scriptEngine != null) {
			initGlobalScope(scriptEngine);
			if ((scriptEngine instanceof Compilable)) {
				compilable = true;
				Map<String, CompiledScript> cache = createCache();
				if (cache == null) {
					cache = new HashMap<>(0x1000);
				}
				this.cache = cache;
			}
		} else {
			log.error("脚本引擎不支持:{}", engineName);
		}
	}

	protected abstract String getEngineName();

	protected Map<String, CompiledScript> createCache() {
		return null;
	}

	protected void initGlobalScope(ScriptEngine scriptEngine) {
		Bindings globalBindings = scriptEngine.createBindings();
		globalBindings.put(OUT, System.out);
		globalBindings.put(ERR, System.err);
		scriptEngine.setBindings(globalBindings, ScriptContext.GLOBAL_SCOPE);
	}


	protected ScriptEngine getScriptEngine() {
		return scriptEngine;
	}


	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Object eval(String scriptContent, Object input, Object output, Map<String, Object> mergeBindings) throws ScriptEvalException {
		try {
			if (scriptEngine == null) {
				throw new ScriptEvalException("脚本引擎不支持:" + getEngineName());
			}
			Bindings bindings = scriptEngine.createBindings();
			if (mergeBindings != null) {
				bindings.putAll(mergeBindings);
				bindings.put(BINDINGS, mergeBindings);
			} else {
				if (input instanceof Map) {
					bindings.putAll((Map) input);
				}
			}
			bindings.put(INPUT, input);
			if (output == null) {
				bindings.put(OUTPUT, new LinkedHashMap<>());
			} else {
				bindings.put(OUTPUT, output);
			}

			Object rs;
			if (compilable) {
				String sourceId = Base64.getEncoder().encodeToString(Digests.sha1(scriptContent));
				CompiledScript compiledScript = cache.get(sourceId);
				if (compiledScript == null) {
					synchronized (this) {
						compiledScript = cache.get(sourceId);
						if (compiledScript == null) {
							compiledScript = ((Compilable) scriptEngine).compile(scriptContent);
							cache.put(sourceId, compiledScript);
						}
					}
				}
				rs = compiledScript.eval(bindings);
			} else {
				rs = scriptEngine.eval(scriptContent, bindings);
			}
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
