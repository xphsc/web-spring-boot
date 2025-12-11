/*
 * Copyright (c) 20225 huipei.x
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
package cn.xphsc.web.common.lang.function.Lambda;

import java.io.*;
import java.lang.invoke.MethodHandleInfo;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: SerializedLambda
 * @since 2.0.3
 */
@SuppressWarnings("unused")
public class SerializedLambda implements Serializable {

    private static final long serialVersionUID = 8025925345765570181L;

    private Class<?> capturingClass;
    private String functionalInterfaceClass;
    private String functionalInterfaceMethodName;
    private String functionalInterfaceMethodSignature;
    private String implClass;
    private String implMethodName;
    private String implMethodSignature;
    private int implMethodKind;
    private String instantiatedMethodType;
    private Object[] capturedArgs;

    public static SerializedLambda resolve(Function<?, ?> lambda) {
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(serialize(lambda))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException("This is impossible to happen", e);
        }
    }


    /**
     * 获取捕获此lambda的 class
     *
     * @return 返回 class
     */
    public Class<?> getCapturingClass() {
        return capturingClass;
    }

    /**
     * 获取捕获此lambda的类的名称
     *
     * @return 返回 class 名称
     */
    public String getCapturingClassName() {
        return normalName(capturingClass.getName());
    }


    /**
     * Get the name of the primary method for the functional interface
     * to which this lambda has been converted.
     *
     * @return
     */
    public String getFunctionalInterfaceMethodName() {
        return functionalInterfaceMethodName;
    }

    /**
     * Get the signature of the primary method for the functional
     * interface to which this lambda has been converted.
     *
     * @return the signature of the primary method of the functional
     * interface
     */
    public String getFunctionalInterfaceMethodSignature() {
        return functionalInterfaceMethodSignature;
    }

    /**
     * Get the signature of the implementation method.
     *
     * @return the signature of the implementation method
     */
    public String getImplMethodSignature() {
        return implMethodSignature;
    }

    /**
     * 获取实现方法的方法句柄类型 （see {@link MethodHandleInfo}）
     *
     * @return int值
     */
    public int getImplMethodKind() {
        return implMethodKind;
    }

    /**
     * Get the count of dynamic arguments to the lambda capture site.
     *
     * @return the count of dynamic arguments to the lambda capture site
     */
    public int getCapturedArgCount() {
        return capturedArgs.length;
    }

    /**
     * 获取接口 class名称
     *
     * @return 返回 class 名称
     */
    public String getFunctionalInterfaceClassName() {
        return normalName(functionalInterfaceClass);
    }

    /**
     * 获取接口 class
     *
     * @return 返回 class
     */
    public Class<?> getFunctionalInterfaceClass() {
        return forName(getFunctionalInterfaceClassName());
    }

    /**
     * 获取实现的 class
     *
     * @return 实现类
     */
    public Class<?> getImplClass() {
        return forName(getImplClassName());
    }

    /**
     * 获取实现的 class 的名称
     *
     * @return 类名
     */
    public String getImplClassName() {
        return normalName(implClass);
    }

    /**
     * 获取实现者的方法名称
     *
     * @return 方法名称
     */
    public String getImplMethodName() {
        return implMethodName;
    }

    /**
     * 正常化类名称，将类名称中的 / 替换为 .
     *
     * @param name 名称
     * @return 正常的类名
     */
    private String normalName(String name) {
        return name.replace('/', '.');
    }

    private static final Pattern INSTANTIATED_METHOD_TYPE = Pattern.compile("\\(L(?<instantiatedMethodType>[\\S&&[^;)]]+);\\)L[\\S]+;");

    /**
     * Get the signature of the primary functional interface method
     * after type variables are substituted with their instantiation
     * from the capture site.
     *
     * @return the signature of the primary functional interface method
     * after type variable processing
     */
    public String getInstantiatedMethodType() {
        return instantiatedMethodType;
    }

    public Class<?> getInstantiatedMethodTypeClass() {
        Matcher matcher = INSTANTIATED_METHOD_TYPE.matcher(instantiatedMethodType);
        if (matcher.find()) {
            return forName(normalName(matcher.group("instantiatedMethodType")));
        }
        throw new RuntimeException(String.format("无法从 %s 解析调用实例。。。", instantiatedMethodType));
    }

    private Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else {
            throw new RuntimeException("Error parsing property name '" + name + "' not start with 'is', 'get' or 'set'.");
        }
        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }
        return name;
    }
    // ------------------ 其他工具方法 ------------------

    public static <T> byte[] serialize(T obj) {
        if (obj == null) return null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("serialize failed.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) {
        if (bytes == null) return null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize failed.", e);
        }
    }

    /**
     * @return 字符串形式
     */
    @Override
    public String toString() {
        return String.format("%s -> %s::%s", getFunctionalInterfaceClassName(), getImplClass().getSimpleName(),
                implMethodName);
    }

}
