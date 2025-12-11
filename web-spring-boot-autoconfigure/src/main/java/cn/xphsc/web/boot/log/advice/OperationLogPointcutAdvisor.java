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
package cn.xphsc.web.boot.log.advice;

import cn.xphsc.web.boot.log.autoconfigure.OperationLogProperties;
import cn.xphsc.web.common.spring.AnnotationPointcut;
import cn.xphsc.web.log.annotation.SysOperationLog;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;

/**
 * {@link AbstractPointcutAdvisor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class OperationLogPointcutAdvisor extends AbstractPointcutAdvisor implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Advice advice;
    private Pointcut pointcut;
    private OperationLogProperties operationLogProperties;

    private Class<? extends Annotation> targetAnnotation;
    public OperationLogPointcutAdvisor(OperationLogProperties operationLogProperties,Class<? extends Annotation> targetAnnotation) {
        this.targetAnnotation = targetAnnotation;
        this.operationLogProperties=operationLogProperties;
    }
    @Override
    public Pointcut getPointcut() {
        if (this.pointcut == null) {
            this.pointcut = buildPointcut(targetAnnotation);
        }
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        if (this.advice == null) {
            this.advice = buildAdvice();
        }
        return this.advice;
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext=applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    private Advice buildAdvice() {
        OperationLogMethodInterceptor interceptor = new OperationLogMethodInterceptor(applicationContext,operationLogProperties,targetAnnotation);
        return interceptor;
    }
    private Pointcut buildPointcut(Class<? extends Annotation> targetAnnotation) {
        return new AnnotationPointcut(targetAnnotation, true);
    }

}
