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
package cn.xphsc.web.utils;



import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class ClassUtils {

  private static final Set<Class<?>> CLASSES = new HashSet<>();
  private static final Class<?>[] WRAP_ARRAY_CLASS = new Class<?>[]{Byte[].class, Short[].class, Integer[].class, Long[].class, Float[].class, Double[].class, Boolean[].class, Character[].class};
  /**
   * 基本类型数组的class
   */
  private static final Class<?>[] BASE_ARRAY_CLASS = new Class<?>[]{byte[].class, short[].class, int[].class, long[].class, float[].class, double[].class, boolean[].class, char[].class};
  /**
   * 基本类型的class
   */
  private static final Class<?>[] BASE_CLASS = new Class<?>[]{Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE};
  /**
   * 包装类型的class
   */
  private static final Class<?>[] WRAP_CLASS = new Class<?>[]{Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, Character.class};
  public static List<Class<?>> getAnnotatedClassOfPackages(String packageName,
                                                           Class<? extends Annotation> clazz) {
      List<Class<?>> classList = new ArrayList<>(scanPackage(packageName));
    List<Class<?>> annotatedClassList = new ArrayList<>(10);
      for (Class<?> item : classList) {
        Annotation annotation = item.getAnnotation(clazz);
        if (annotation != null) {
          annotatedClassList.add(item);
        }
      }
    return annotatedClassList;
  }


  public static Set<Class<?>> scanPackage(String packageName) {
    return scanPackage(packageName, true);
  }


  public static Set<Class<?>> scanPackage(String packageName, boolean recursive) {

    String packageDirName = packageName.replace('.', '/');
    Enumeration<URL> urls;
    try {
      urls = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
          String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
          findClassesInPackageByFile(packageName, filePath, recursive);
        } else if ("jar".equals(protocol)) {
          JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
          findClassesInPackageByJar(jar, packageDirName);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return CLASSES;
  }

  private static void findClassesInPackageByFile(String packageName, String packagePath, boolean recursive) {
    File dir = new File(packagePath);
    if (!dir.exists() || !dir.isDirectory()) {
      return;
    }
    File[] files = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));

    if (files == null) {
      return;
    }

    for (File file : files) {
      if (file.isDirectory()) {
        findClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive);
      } else {
        String className = file.getName().substring(0, file.getName().length() - 6);
        try {
          CLASSES.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));

        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    }

  }

  private static void findClassesInPackageByJar(JarFile jar, String packageDirName) {
    Enumeration<JarEntry> entries = jar.entries();
    while (entries.hasMoreElements()) {
      JarEntry entry = entries.nextElement();
      String name = entry.getName();
      if (name.charAt(0) == '/') {
        name = name.substring(1);
      }

      if (entry.isDirectory() || !name.startsWith(packageDirName) || !name.endsWith(".class")) {
        continue;
      }

      String className = name.substring(0, name.lastIndexOf(".")).replace('/', '.');

      try {
        CLASSES.add(Thread.currentThread().getContextClassLoader().loadClass(className));
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

    }
  }
  public static void setStringValue(Object str, Object value) {
    char[] chars = String.valueOf(value).toCharArray();

    try {
      Field valueField = String.class.getDeclaredField("value");
      valueField.setAccessible(true);
      valueField.set(str, chars);
    } catch (Exception var4) {
    }

  }

  public static <T> void swapBaseType(T i, Object j) {
    try {
      Field field = i.getClass().getDeclaredField("value");
      field.setAccessible(true);
      field.set(i, j);
    } catch (Exception var3) {
    }

  }
  /**
   * 判断对象是否是数组
   * @param clazz class
   * @return true数组
   */
  public static boolean isArray(Class<?> clazz) {
    return clazz.isArray();
  }
  public static boolean isWrapClass(Class<?> clz) {
    try {
      return ((Class)clz.getField("TYPE").get((Object)null)).isPrimitive();
    } catch (Exception var2) {
      return false;
    }
  }

  /**
   * 返回包装数组类型的基本数组类型
   * @param clazz class
   * @return class
   */
  public static Class<?> baseArrayClass(Class<?> clazz) {
    for (int i = 0; i < WRAP_ARRAY_CLASS.length; i++) {
      if (clazz.equals(WRAP_ARRAY_CLASS[i])) {
        return BASE_ARRAY_CLASS[i];
      }
    }
    return clazz;
  }

  /**
   * 返回基本类型的包装类型
   * @param clazz class
   * @return class
   */
  public static Class<?> wrapClass(Class<?> clazz) {
    for (int i = 0; i < BASE_CLASS.length; i++) {
      if (clazz.equals(BASE_CLASS[i])) {
        return WRAP_CLASS[i];
      }
    }
    return clazz;
  }

  /**
   * 判断argClass是否为requireClass的实现类
   * @param requireClass requireClass 父
   * @param argClass     argClass 子
   * @return true 是实现类或本类
   */
  public static boolean isImplClass(Class<?> requireClass, Class<?> argClass) {
    if (requireClass.equals(argClass) || requireClass.equals(Object.class)) {
      return true;
    }
    return requireClass.isAssignableFrom(argClass);

  }
  public static boolean isNormalClass(Class<?> clazz) {
    return null != clazz && !clazz.isInterface() && !isAbstract(clazz) && !clazz.isEnum() && !clazz.isArray() && !clazz.isAnnotation() && !clazz.isSynthetic() && !clazz.isPrimitive();
  }

  public static boolean isAbstract(Class<?> clazz) {
    return Modifier.isAbstract(clazz.getModifiers());
  }

  public static boolean isBaseType(Class<?> cls) {
    return cls.equals(Integer.TYPE) || cls.equals(Byte.TYPE) || cls.equals(Long.TYPE) || cls.equals(Double.TYPE) || cls.equals(Float.TYPE) || cls.equals(Character.TYPE) || cls.equals(Short.TYPE) || cls.equals(Boolean.TYPE);
  }


}
