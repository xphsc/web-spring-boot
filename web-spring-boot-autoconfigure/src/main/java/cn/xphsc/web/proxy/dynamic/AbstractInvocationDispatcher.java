/*
 * Copyright (c) 2023 huipei.x
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
package cn.xphsc.web.proxy.dynamic;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.8
 */
public abstract class AbstractInvocationDispatcher<ANNOTATION_TYPE extends Annotation, ATTACHMENT> {
    private volatile Map<Method, ATTACHMENT> methodAttachments;

    protected final ATTACHMENT getAttachmentOrNull( Method key) {
        return getAttachment(key).orElse(null);
    }

    protected final ATTACHMENT getAttachmentOrErr( Method key) {
        return getAttachment(key).orElseThrow(NullPointerException::new);
    }

    protected final Optional<ATTACHMENT> getAttachment( Method key) {
        return Optional.ofNullable(getMethodAttachments().get(key));
    }

    protected final ATTACHMENT getAttachmentOrCompute(Method key, Function<Method, ATTACHMENT> function) {
        return getMethodAttachments().computeIfAbsent(key, function);
    }

    private Map<Method, ATTACHMENT> getMethodAttachments() {
        if (methodAttachments == null) {
            synchronized (this) {
                if (methodAttachments == null) {
                    methodAttachments = new ConcurrentHashMap<>();
                }
            }
        }
        return methodAttachments;
    }

    @SuppressWarnings("unchecked")
    Class<ANNOTATION_TYPE> getAnnotationType() {
        return (Class<ANNOTATION_TYPE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Object invoke(DynamicProxyContext<ANNOTATION_TYPE> dynamicProxyContext, Object proxy, Method method, Object[] args) throws Throwable {
        return invoke(proxy, method, args);
    }

    protected Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        throw new UnsupportedOperationException();
    }


    protected static class DynamicProxyContext<T extends Annotation> {
        private final T annotation;
        private final Class<?>  dynamicType;

        public T getAnnotation() {
            return annotation;
        }

        public Class<?> getDynamicType() {
            return dynamicType;
        }


        private DynamicProxyContext( Class<?> dynamicType, T annotation) {
            this.annotation = annotation;
            this.dynamicType = dynamicType;
        }

        public static <T extends Annotation> DynamicProxyContext<T> valueOf(Class<?> dynamicType, T annotation) {
            return new DynamicProxyContext<>(dynamicType, annotation);
        }

        @Override
        public String toString() {
            return "DynamicProxyContext{" +
                    "annotation=" + annotation +
                    ", dynamicType=" + dynamicType +
                    '}';
        }
    }


}

