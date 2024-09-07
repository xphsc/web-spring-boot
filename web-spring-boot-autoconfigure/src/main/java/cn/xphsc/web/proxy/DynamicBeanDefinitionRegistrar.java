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
package cn.xphsc.web.proxy;



import cn.xphsc.web.proxy.annotation.DynamicProxy;
import cn.xphsc.web.proxy.annotation.DynamicProxyScan;
import cn.xphsc.web.proxy.support.ClassPathDynamicBeanDefinitionScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.8
 */
public class DynamicBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(DynamicProxyScan.class.getName(), true);
        AnnotationAttributes attrs = AnnotationAttributes.fromMap(annotationAttributes);
        if (attrs == null) {
            return;
        }
        ArrayList<String> pkgList = new ArrayList<>(2);
        String[] pkg1 = attrs.getStringArray("basePackages");
        String[] pkg2 = attrs.getStringArray("basePackageClasses");
        if (pkg1.length > 0) {
            pkgList.addAll(Arrays.asList(pkg1));
        }
        if (pkg2.length > 0) {
            pkgList.addAll(Arrays.asList(pkg2));
        }
        if (pkgList.isEmpty()) {
            pkgList.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        ClassPathBeanDefinitionScanner scanner = new ClassPathDynamicBeanDefinitionScanner(registry, environment);
        String annotationClass = attrs.getString("annotationClass");
        if (!StringUtils.isEmpty(annotationClass)) {
            scanner.resetFilters(false);
            AnnotationTypeFilter proxyStubFilter = new AnnotationTypeFilter(DynamicProxy.class, true, false);
            @SuppressWarnings("unchecked")
            AnnotationTypeFilter annotationClassFilter = null;
            try {
                annotationClassFilter = new AnnotationTypeFilter((Class<? extends Annotation>) ClassUtils.forName(annotationClass, null), true, false);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            AnnotationTypeFilter finalMarkAnnotationFilter = annotationClassFilter;
            scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> proxyStubFilter.match(metadataReader, metadataReaderFactory) && finalMarkAnnotationFilter.match(metadataReader, metadataReaderFactory));
        }
        scanner.scan(StringUtils.toStringArray(pkgList));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
