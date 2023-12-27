/*
 * Copyright (c) 2021 huipei.x
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
package cn.xphsc.web.log.context;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class OperationLogExpressionEvaluator extends CachedExpressionEvaluator {

    private Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>();
    private Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>();


    public EvaluationContext createEvaluationContext(Class<?> targetClass, Method method, Object[] args,
                                                     Object retObj, String errorMsg, Map<String,Object> optContext) {
        Method targetMethod = getTargetMethod(targetClass, method);
        return new OperationLogEvaluationContext(null, targetMethod, args, getParameterNameDiscoverer(), retObj, errorMsg, optContext);
    }


    public String parseExpression(EvaluationContext evaluationContext, AnnotatedElementKey methodKey, String conditionExpression) {
        return getExpression(this.expressionCache, methodKey, conditionExpression).getValue(evaluationContext, String.class);
    }


    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }
}
