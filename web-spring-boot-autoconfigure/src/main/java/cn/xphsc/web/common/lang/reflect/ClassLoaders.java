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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: ClassLoader
 * @since 2.0.1
 */
public class ClassLoaders {

    public final static String MANIFEST_FILE = "META-INF/MANIFEST.MF";
    public final static char CLASS_CHAR = '$';

    private final Set<File> processed = new HashSet<>();
    private final List<String> resolvedClassPaths = new ArrayList<>();

    private final ClassLoader classLoader;
    private final Set<String> ignoredPackages;

    public ClassLoaders(final ClassLoader classLoader) {

        this.classLoader = classLoader;
        this.ignoredPackages = new HashSet<>();

    }

    public ClassLoaders(final ClassLoader classLoader, final Set<String> ignoredPackages) {

        this.classLoader = classLoader;
        this.ignoredPackages = ignoredPackages;

    }

    public void addIgnoredPackageName(final String packageName) {

        this.ignoredPackages.add(packageName);

    }

    public void addIgnoredPackageNames(final String... packageNames) {

        this.ignoredPackages.addAll(Arrays.asList(packageNames));

    }

    public boolean resolve() {

        try {
            for (final Map.Entry<File, ClassLoader> entry : this.getClassPathEntries(classLoader).entrySet())
                this.process(entry.getKey(), entry.getValue());
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public List<String> getClassPaths() {

        final List<String> filteredPaths = new ArrayList<>();
        for (final String classPath : resolvedClassPaths) {
            if (classPath.indexOf(CLASS_CHAR) == -1)
                filteredPaths.add(classPath);
        }

        return filteredPaths;

    }

    public List<String> getClassPaths(final String packageName) {

        final List<String> filteredPaths = new ArrayList<>();
        for (final String classPath : getClassPaths()) {
            final String className = ClassUtils.getClassName(classPath);
            final String classPackageName = ClassUtils.getPackageName(className);
            if (classPackageName.equals(packageName))
                filteredPaths.add(classPath);
        }

        return filteredPaths;

    }

    public List<String> getClassPathsRecursive(final String packageName) {

        final List<String> filteredPaths = new ArrayList<>();
        for (final String classPath : getClassPaths()) {
            final String className = ClassUtils.getClassName(classPath);
            if (className.startsWith(packageName) &&
                    !this.ignoredPackages.contains(ClassUtils.getPackageName(className)))
                filteredPaths.add(classPath);
        }

        return filteredPaths;

    }

    private Map<File, ClassLoader> getClassPathEntries(final ClassLoader classLoader) {

        final Map<File, ClassLoader> entries = new HashMap<>();
        final ClassLoader parentClassLoader = classLoader.getParent();
        if (parentClassLoader != null)
            entries.putAll(this.getClassPathEntries(parentClassLoader));

        for (final URL url : this.getClassLoaderUrls(classLoader)) {
            if (!url.getProtocol().equals("file")) continue;
            final File file = new File(url.getFile());
            if (!entries.containsKey(file))
                entries.put(file, classLoader);
        }

        return entries;

    }

    private List<URL> getClassLoaderUrls(final ClassLoader classLoader) {

        if (classLoader instanceof URLClassLoader) {
            return Arrays.asList(((URLClassLoader) classLoader).getURLs());
        } else if (classLoader.equals(ClassLoader.getSystemClassLoader())) {
            return this.parseJavaClassPath();
        } else {
            return Collections.emptyList();
        }

    }

    private List<URL> parseJavaClassPath() {

        final List<URL> urls = new ArrayList<>();
        for (final String entry : System.getProperty("java.class.path").split(File.pathSeparator)) {
            try {
                try {
                    urls.add(new File(entry).toURI().toURL());
                } catch (final SecurityException ex) {
                    urls.add(new URL("file", null, new File(entry).getAbsolutePath()));
                }
            } catch (final MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return urls;

    }

    private void process(final File file, final ClassLoader classLoader) throws IOException {

        if (!processed.add(file.getCanonicalFile()) || !file.exists()) return;

        if (file.isDirectory()) {
            this.processDirectory(file, classLoader);
        } else {
            this.processJarFile(file, classLoader);
        }

    }

    private void processDirectory(final File directory, final ClassLoader classLoader) {

        this.processDirectory(directory, classLoader, "");

    }

    private void processDirectory(final File directory, final ClassLoader classLoader, final String packagePrefix) {

        final File[] files = directory.listFiles();
        if (files == null) return;

        for (final File file : files) {
            if (file.isDirectory()) {
                this.processDirectory(file, classLoader, packagePrefix + file.getName() + "/");
            } else {
                final String resourceName = packagePrefix + file.getName();
                if (resourceName.equals(MANIFEST_FILE)) continue;
                resolvedClassPaths.add(resourceName);
            }
        }

    }

    private void processJarFile(final File file, final ClassLoader classLoader) throws IOException {

        try (JarFile jarFile = new JarFile(file)) {
            for (final File classPath : this.getClassPathsFromManifest(file, jarFile.getManifest()))
                this.process(classPath, classLoader);
            this.scanJarFile(jarFile, classLoader);
        }

    }

    private void scanJarFile(final JarFile jarFile, final ClassLoader classLoader) {

        final Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            final JarEntry entry = enumeration.nextElement();
            if (!entry.isDirectory() && !entry.getName().equals(MANIFEST_FILE))
                resolvedClassPaths.add(entry.getName());
        }

    }

    private Set<File> getClassPathsFromManifest(final File jarFile, final Manifest manifest) throws MalformedURLException {

        final Set<File> classPaths = new HashSet<>();
        if (manifest == null) return classPaths;

        final String classPathAttribute = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString());
        if (classPathAttribute == null) return classPaths;

        for (final String path : classPathAttribute.split(" ")) {
            final URL url = this.getClassPathEntry(jarFile, path);
            if (url.getProtocol().equals("file"))
                classPaths.add(new File(url.getFile()));
        }

        return classPaths;

    }

    private URL getClassPathEntry(final File jarFile, final String path) throws MalformedURLException {

        return new URL(jarFile.toURI().toURL(), path);

    }

}

