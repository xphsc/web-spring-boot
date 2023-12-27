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
package cn.xphsc.web.common.spring;



import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class AnnotationPointcut implements Pointcut {
    private final ClassFilter classFilter;
    private final MethodMatcher methodMatcher;

    public AnnotationPointcut(Class<? extends Annotation> annotationType) {
        this(annotationType, false);
    }

    public AnnotationPointcut(Class<? extends Annotation> annotationType, boolean checkInherited) {
        this.classFilter = new AnnotationClassFilter(annotationType, checkInherited);
        //PS: AnnotationMethodMatcher
        this.methodMatcher = new DynamicMethodMatcher(annotationType, checkInherited);
    }

    @Override
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this.methodMatcher;
    }

    static class DynamicMethodMatcher extends StaticMethodMatcher {
        private final Class<? extends Annotation> annotationType;

        private final boolean checkInherited;

        public DynamicMethodMatcher(Class<? extends Annotation> annotationType) {
            this(annotationType, false);
        }

        public DynamicMethodMatcher(Class<? extends Annotation> annotationType, boolean checkInherited) {
            this.annotationType = annotationType;
            this.checkInherited = checkInherited;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return AnnotationUtils.matchesMethod(method, annotationType, checkInherited) ||
                    AnnotationUtils.matchesClass(targetClass, annotationType, checkInherited);
        }

    }
}
