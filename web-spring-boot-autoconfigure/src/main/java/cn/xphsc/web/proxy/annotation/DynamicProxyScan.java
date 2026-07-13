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
package cn.xphsc.web.proxy.annotation;



import cn.xphsc.web.proxy.DynamicBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 动态代理扫描注解，用于启用动态Bean定义注册功能
 *   <p>该注解通过导入DynamicBeanDefinitionRegistrar来实现动态Bean的注册，
 *  允许在运行时动态创建和注册Bean定义到Spring容器中。</p>
 *  @Import 注解用于导入DynamicBeanDefinitionRegistra
 * @since 1.1.8
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DynamicBeanDefinitionRegistrar.class)
public @interface DynamicProxyScan {
    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    /**
     * 标记注解 用于限定扫描的接口
     */
    Class<? extends Annotation> annotationClass() default Annotation.class;
}
