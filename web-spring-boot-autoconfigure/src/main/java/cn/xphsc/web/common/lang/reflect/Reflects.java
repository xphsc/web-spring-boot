/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.common.lang.reflect;

import cn.xphsc.web.utils.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: ReflectTemplate
 * @since 2.0.1
 */
public class Reflects {

    private final List<Class<?>> classes = new ArrayList<>();
    private final Set<String> ignoredPackages = new HashSet<>();

    public Reflects() {

    }

    public Reflects(final ClassLoader classLoader,
                    final List<Class<?>> preResolvedClasses,
                    final List<String> ignoredPackages,
                    final List<String> targetPackages) {

        this.classes.addAll(preResolvedClasses);
        this.ignoredPackages.addAll(ignoredPackages);
        for (final String targetPackage : targetPackages)
            this.resolvePackage(classLoader, targetPackage);
    }

    private Reflects(final Reflects.Builder builder) {
        final ClassLoader classLoader = Optional.ofNullable(builder.classLoader).orElse(ClassLoader.getSystemClassLoader());
        this.classes.addAll(builder.preResolvedClasses);
        this.ignoredPackages.addAll(builder.ignoredPackages);

        for (final String targetPackage : builder.targetPackages)
            this.resolvePackage(classLoader, targetPackage);

    }

    /**
     * Used to find all classes in all resolved classes of the current instance that are annotated with the provided
     * annotation.
     * @param annotationClass The annotation class.
     * @param <T>             The annotation type.
     * @return A map containing the classes mapped to the annotations.
     */

    public <T extends Annotation> Map<Class<?>, T> findAnnotatedClasses(final Class<? extends Annotation> annotationClass) {
        final Map<Class<?>, T> foundClasses = new HashMap<>();
        for (final Class<?> clazz : this.classes) {
            if (clazz.isAnnotationPresent(annotationClass))
                foundClasses.put(clazz, (T) clazz.getAnnotation(annotationClass));
        }

        return foundClasses;

    }

    /**
     * Used to find all declared methods in all resolved classes of the current instance that are annotated with the
     * provided annotation.
     * @param annotationClass The annotation class.
     * @param <T>             The annotation type.
     * @return A map containing the methods mapped to the annotations.
     */

    public <T extends Annotation> Map<Method, T> findAnnotatedMethods(final Class<? extends Annotation> annotationClass) {
        final Map<Method, T> foundMethods = new HashMap<>();
        for (final Class<?> clazz : this.classes) {
            for (final Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotationClass))
                    foundMethods.put(method, (T) method.getAnnotation(annotationClass));
            }
        }

        return foundMethods;

    }

    /**
     * Used to find all declared methods in the provided class that are annotated with the provided annotation.
     * @param clazz           The target class.
     * @param annotationClass The annotation class.
     * @param <T>             The annotation type.
     * @return A map containing the methods mapped to the annotations.
     */

    public <T extends Annotation> Map<Method, T> findAnnotatedMethods(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {

        final Map<Method, T> foundMethods = new HashMap<>();
        for (final Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass))
                foundMethods.put(method, (T) method.getAnnotation(annotationClass));
        }
        return foundMethods;
    }

    /**
     * Used to find all declared fields in all resolved classes of the current instance that are annotated with the
     * provided annotation.
     * @param annotationClass The annotation class.
     * @param <T>             The annotation type.
     * @return A map containing the fields mapped to the annotations.
     */

    public <T extends Annotation> Map<Field, T> findAnnotatedFields(final Class<? extends Annotation> annotationClass) {
        final Map<Field, T> foundFields = new HashMap<>();
        for (final Class<?> clazz : this.classes) {
            for (final Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(annotationClass))
                    foundFields.put(field, (T) field.getAnnotation(annotationClass));
            }
        }

        return foundFields;

    }

    /**
     * Used to find all declared fields in the provided class that are annotated with the provided annotation.
     * @param clazz           The target class.
     * @param annotationClass The annotation class.
     * @param <T>             The annotation type.
     * @return A map containing the fields mapped to the annotations.
     */

    public <T extends Annotation> Map<Field, T> findAnnotatedFields(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        final Map<Field, T> foundFields = new HashMap<>();
        for (final Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass))
                foundFields.put(field, (T) field.getAnnotation(annotationClass));
        }
        return foundFields;
    }

    /**
     * Used to ignore the provided package.
     * @param ignoredPackage The name of the package.
     */

    public void ignorePackage(final String ignoredPackage) {
        this.ignoredPackages.add(ignoredPackage);

    }

    /**
     * Used to find all classes in the provided package and store them in the current instance.
     *
     * @param classLoader The class loader.
     * @param packageName The name of the package.
     */

    public void resolvePackage(final ClassLoader classLoader, final String packageName) {
        this.classes.addAll(ClassUtils.getPackageClassesRecursive(classLoader, packageName, this.ignoredPackages));
    }

    /**
     * Used to get a copy of the resolved classes list.
     *
     * @return The classes list copy.
     */

    public List<Class<?>> getClasses() {
        return new ArrayList<>(classes);

    }

    public static Reflects.Builder builder() {
        return new Reflects.Builder();
    }

    public static Reflects owned(final Class<?> owner) {
        return new Reflects(
                owner.getClassLoader(),
                Collections.emptyList(),
                Collections.singletonList(owner.getPackage().getName() + ".external"),
                Collections.singletonList(owner.getPackage().getName())
        );

    }

    public static class Builder {
        private final List<String> targetPackages = new ArrayList<>();
        private final List<String> ignoredPackages = new ArrayList<>();
        private final List<Class<?>> preResolvedClasses = new ArrayList<>();

        private ClassLoader classLoader;

        public Reflects.Builder classLoader(final ClassLoader classLoader) {

            this.classLoader = classLoader;
            return this;

        }

        public Reflects.Builder resolvePackage(final String packageName) {
            this.targetPackages.add(packageName);
            return this;

        }

        public Reflects.Builder ignoredPackages(final String... ignoredPackages) {
            this.ignoredPackages.addAll(Arrays.asList(ignoredPackages));
            return this;

        }

        public Reflects.Builder ignoredPackages(final Package... ignoredPackages) {
            for (final Package ignoredPackage : ignoredPackages)
                this.ignoredPackages.add(ignoredPackage.getName());
            return this;

        }

        public Reflects.Builder ignoredPackage(final String ignoredPackage) {
            this.ignoredPackages.add(ignoredPackage);
            return this;

        }

        public Reflects.Builder ignoredPackage(final Package ignoredPackage) {
            this.ignoredPackages.add(ignoredPackage.getName());
            return this;

        }

        public Reflects.Builder preResolvedClass(final Class<?> clazz) {
            this.preResolvedClasses.add(clazz);
            return this;

        }

        public Reflects.Builder preResolvedClasses(final List<Class<?>> classes) {
            this.preResolvedClasses.addAll(classes);
            return this;

        }

        public Reflects build() {

            return new Reflects(this);

        }

    }
}
