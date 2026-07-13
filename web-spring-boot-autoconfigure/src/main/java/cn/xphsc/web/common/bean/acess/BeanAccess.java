/*
 * Copyright (c) 2025 huipei.x
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
package cn.xphsc.web.common.bean.acess;

import cn.xphsc.web.common.lang.function.consumer.SerializableConsumerWithArgs4;
import cn.xphsc.web.common.lang.function.SerializableTriFunction;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.Label;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.springframework.asm.Opcodes.*;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  BeanAccess
 * @since 2.0.4
 */
public abstract class BeanAccess<T> {
    @SuppressWarnings({"rawtypes"})
    private static final AccessClassPool<Class<?>, BeanAccess> pool = new AccessClassPool<>();
    private Map<String, BeanPropertyInfo> properties;
    private Map<String, Integer> setterIndices;
    private Map<String, Integer> getterIndices;
    private Map<String, Integer> fieldIndices;


    public Map<String, BeanPropertyInfo> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, BeanPropertyInfo> properties) {
        this.properties = properties;
    }

    public Map<String, Integer> getSetterIndices() {
        return setterIndices;
    }

    public void setSetterIndices(Map<String, Integer> setterIndices) {
        this.setterIndices = setterIndices;
    }

    public Map<String, Integer> getGetterIndices() {
        return getterIndices;
    }

    public void setGetterIndices(Map<String, Integer> getterIndices) {
        this.getterIndices = getterIndices;
    }

    public Map<String, Integer> getFieldIndices() {
        return fieldIndices;
    }

    public void setFieldIndices(Map<String, Integer> fieldIndices) {
        this.fieldIndices = fieldIndices;
    }

    public static <T> BeanAccess<T> create(Class<T> type) {
        BeanPropertyInfo.Classification classification = BeanPropertyInfo.classify(type);
        String accessClassName = AccessClassLoader.buildAccessClassName(type, BeanAccess.class);
        Class accessClass;
        AccessClassLoader loader = AccessClassLoader.get(type);
        synchronized (loader) {
            accessClass = loader.loadAccessClass(accessClassName);
            if (accessClass == null) {
                accessClass = buildAccessClass(loader, accessClassName, type, classification);
            }
        }
        BeanAccess<T> access;
        try {
            access = (BeanAccess<T>) accessClass.newInstance();
            // 全部设为只读属性
            access.properties = Collections.unmodifiableMap(classification.properties);
            {
                Map<String, Integer> setterIndices = new HashMap<>();
                for (int i = 0; i < classification.setters.size(); i++) {
                    setterIndices.put(classification.setters.get(i).getPropertyName(), i);
                }
                access.setterIndices = Collections.unmodifiableMap(setterIndices);
            }
            {
                Map<String, Integer> getterIndices = new HashMap<>();
                for (int i = 0; i < classification.getters.size(); i++) {
                    getterIndices.put(classification.getters.get(i).getPropertyName(), i);
                }
                access.getterIndices = Collections.unmodifiableMap(getterIndices);
            }
            {
                Map<String, Integer> fieldIndices = new HashMap<>();
                for (int i = 0; i < classification.fields.size(); i++) {
                    fieldIndices.put(classification.fields.get(i).getPropertyName(), i);
                }
                access.fieldIndices = Collections.unmodifiableMap(fieldIndices);
            }
        } catch (Throwable t) {
            throw new IllegalStateException("创建访问类失败: " + accessClassName, t);
        }
        return access;
    }
    public static <T> BeanAccess<T> get(Class<T> type) {
        return pool.computeIfAbsent(type, BeanAccess::create);
    }

    public static String buildAccessClassName(Class<?> type, Class<?> baseAccessClass) {
        if (type.isArray()) {
            throw new IllegalArgumentException("不支持数组类型：" + type.getCanonicalName());
        }
        String className = type.getName();
        String accessClassName = className + "$$" + baseAccessClass.getSimpleName() + "$";
        if (accessClassName.startsWith("java.")) {
            accessClassName = "javax." + accessClassName.substring(5);
        }
        return accessClassName;
    }
    private static <T> Class buildAccessClass(AccessClassLoader loader
            , String accessClassName, Class<T> type
            , BeanPropertyInfo.Classification classification) {
        String accessClassNameInternal = accessClassName.replace('.', '/');
        String classNameInternal = type.getName().replace('.', '/');

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        String superClassNameInternal = BeanAccess.class.getName().replace('.', '/');
        cw.visit(52, 1 + 32, accessClassNameInternal,
                "L" + superClassNameInternal + "<L" + getInternalName(type) + ";>;",
                superClassNameInternal, null);
        cw.visitInnerClass("java/lang/invoke/MethodHandles$Lookup", "java/lang/invoke/MethodHandles", "Lookup", ACC_PUBLIC | ACC_FINAL | ACC_STATIC);

        Asms.insertDefaultConstructor(cw, superClassNameInternal);
        insertSetterInvokers(cw, accessClassNameInternal, type, classification.setters);
        insertGetterInvokers(cw, accessClassNameInternal, type, classification.getters);
        insertFieldInvokers(cw, accessClassNameInternal, type, classification.fields);

        cw.visitEnd();
        byte[] byteArray = cw.toByteArray();

        return loader.defineAccessClass(accessClassName, byteArray);
    }
    public static String getInternalName(Class<?> clazz) {
        return clazz.getName().replace('.', '/');
    }

    private static <T> void insertSetterInvokers(ClassWriter cw, String accessClassNameInternal, Class<T> type, List<BeanPropertyInfo> setters) {
        // ignore
        if (setters.isEmpty()) {
            return;
        }

        SerializableConsumerWithArgs4<BeanAccess<?>, Object, Integer, Object> setIndexProperty = BeanAccess::setIndexProperty;
        // 生成各方法的索引式调用方法
        {
            MethodVisitor methodVisitor = cw.visitMethod(ACC_PROTECTED | ACC_VARARGS, setIndexProperty.serialized().getImplMethodName(), "(Ljava/lang/Object;ILjava/lang/Object;)V", null, null);
            methodVisitor.visitCode();
            // switch 方法名分支
            {
                methodVisitor.visitVarInsn(ILOAD, 2);

                Label[] labels = Asms.newLabels(setters.size());
                Label labelDefault = new Label();
                Label labelBreak = new Label();
                methodVisitor.visitTableSwitchInsn(0, setters.size() - 1, labelDefault, labels);
                // case
                for (int idxName = 0; idxName < setters.size(); idxName++) {
                    methodVisitor.visitLabel(labels[idxName]);
                    BeanPropertyInfo info = setters.get(idxName);
                    Method method = info.getWriteMethod();
                    String methodName = method.getName();
                    boolean hasThrows = method.getExceptionTypes().length > 0;
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    // 方法调用
                    {
                        Label labelStart = new Label();
                        Label labelEnd = new Label();
                        Label labelCatch = new Label();
                        if (hasThrows) {
                            methodVisitor.visitTryCatchBlock(labelStart, labelEnd, labelCatch, "java/lang/Throwable");
                            methodVisitor.visitLabel(labelStart);
                        }
                        // try
                        methodVisitor.visitVarInsn(ALOAD, 1);
                        methodVisitor.visitTypeInsn(CHECKCAST, getInternalName(type));
                        if (Modifier.isStatic(method.getModifiers())) {
                            methodVisitor.visitInsn(POP);
                        }

                        methodVisitor.visitVarInsn(ALOAD, 3);
                        Class<?> parameterType = parameterTypes[0];
                        Type paramType = Type.getType(parameterType);
                        Asms.autoUnBoxing(methodVisitor, paramType);

                        Class<?> declaringClass = method.getDeclaringClass();
                        boolean isInterface = declaringClass.isInterface();
                        int invokeOpcode;
                        if (isInterface) {
                            invokeOpcode = INVOKEINTERFACE;
                        } else if (Modifier.isStatic(method.getModifiers())) {
                            invokeOpcode = INVOKESTATIC;
                        } else {
                            invokeOpcode = INVOKEVIRTUAL;
                        }
                        methodVisitor.visitMethodInsn(invokeOpcode, Type.getInternalName(declaringClass), methodName, Type.getMethodDescriptor(method), isInterface);
                        Class<?> returnType = method.getReturnType();
                        Asms.autoBoxing(methodVisitor, returnType);
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, labelBreak);

                        if (hasThrows) {
                            methodVisitor.visitLabel(labelEnd);
                        }

                        // catch
                        if (hasThrows) {
                            methodVisitor.visitLabel(labelCatch);
                            methodVisitor.visitVarInsn(ASTORE, 4);
                            methodVisitor.visitTypeInsn(NEW, Type.getInternalName(IllegalArgumentException.class));
                            methodVisitor.visitInsn(DUP);
                            methodVisitor.visitVarInsn(ALOAD, 4);
                            methodVisitor.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(IllegalArgumentException.class), "<init>", "(Ljava/lang/Throwable;)V", false);
                            methodVisitor.visitInsn(ATHROW);
                        }

                    }
                }
                // default
                methodVisitor.visitLabel(labelDefault);
                methodVisitor.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
                methodVisitor.visitInsn(DUP);
                methodVisitor.visitLdcInsn("Method not found");
                methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                methodVisitor.visitInsn(ATHROW);

                // break
                methodVisitor.visitLabel(labelBreak);
            }

            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(0, 0);
            methodVisitor.visitEnd();
        }
    }

    public void setIndexProperty(Object o, int methodIndex, Object val) {
        throw new IllegalArgumentException("Method not found");
    }
    private static <T> void insertGetterInvokers(ClassWriter cw, String accessClassNameInternal, Class<T> type, List<BeanPropertyInfo> getters) {
        // ignore
        if (getters.isEmpty()) {
            return;
        }
        SerializableTriFunction<BeanAccess<?>, Object, Integer, Object> getIndexProperty = BeanAccess::getIndexProperty;

        // 生成各方法的索引式调用方法
        {
            MethodVisitor methodVisitor = cw.visitMethod(ACC_PROTECTED | ACC_VARARGS, getIndexProperty.serialized().getImplMethodName(), "(Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
            methodVisitor.visitCode();
            // switch 方法名分支
            {
                methodVisitor.visitVarInsn(ILOAD, 2);
                Label[] labels = Asms.newLabels(getters.size());
                Label labelDefault = new Label();
                Label labelBreak = new Label();
                methodVisitor.visitTableSwitchInsn(0, getters.size() - 1, labelDefault, labels);

                // case
                for (int idxName = 0; idxName < getters.size(); idxName++) {
                    methodVisitor.visitLabel(labels[idxName]);
                    BeanPropertyInfo info = getters.get(idxName);
                    Method method = info.getReadMethod();
                    String methodName = method.getName();
                    boolean hasThrows = method.getExceptionTypes().length > 0;

                    // 方法调用
                    {
                        Label labelStart = new Label();
                        Label labelEnd = new Label();
                        Label labelCatch = new Label();
                        if (hasThrows) {
                            methodVisitor.visitTryCatchBlock(labelStart, labelEnd, labelCatch, "java/lang/Throwable");
                            methodVisitor.visitLabel(labelStart);
                        }
                        // try
                        methodVisitor.visitVarInsn(ALOAD, 1);
                        methodVisitor.visitTypeInsn(CHECKCAST, Type.getInternalName(type));
                        if (Modifier.isStatic(method.getModifiers())) {
                            methodVisitor.visitInsn(POP);
                        }
                        Class<?> declaringClass = method.getDeclaringClass();
                        boolean isInterface = declaringClass.isInterface();
                        int invokeOpcode;
                        if (isInterface) {
                            invokeOpcode = INVOKEINTERFACE;
                        } else if (Modifier.isStatic(method.getModifiers())) {
                            invokeOpcode = INVOKESTATIC;
                        } else {
                            invokeOpcode = INVOKEVIRTUAL;
                        }
                        methodVisitor.visitMethodInsn(invokeOpcode, Type.getInternalName(declaringClass), methodName, Type.getMethodDescriptor(method), isInterface);
                        Class<?> returnType = method.getReturnType();
                        if (returnType.equals(Void.TYPE)) {
                            methodVisitor.visitInsn(ACONST_NULL);
                        } else {
                            Asms.autoBoxing(methodVisitor, returnType);
                        }
                        if (hasThrows) {
                            methodVisitor.visitLabel(labelEnd);
                        }
                        methodVisitor.visitInsn(ARETURN);
                        // catch
                        if (hasThrows) {
                            methodVisitor.visitLabel(labelCatch);
                            methodVisitor.visitVarInsn(ASTORE, 3);
                            methodVisitor.visitTypeInsn(NEW, Type.getInternalName(IllegalArgumentException.class));
                            methodVisitor.visitInsn(DUP);
                            methodVisitor.visitVarInsn(ALOAD, 3);
                            methodVisitor.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(IllegalArgumentException.class), "<init>", "(Ljava/lang/Throwable;)V", false);
                            methodVisitor.visitInsn(ATHROW);
                        }
                    }
                }
                // default
                methodVisitor.visitLabel(labelDefault);
                methodVisitor.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
                methodVisitor.visitInsn(DUP);
                methodVisitor.visitLdcInsn("Method not found");
                methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                methodVisitor.visitInsn(ATHROW);
            }
            methodVisitor.visitMaxs(0, 0);
            methodVisitor.visitEnd();
        }
    }
    private static <T> void insertFieldInvokers(ClassWriter cw, String accessClassNameInternal, Class<T> type, List<BeanPropertyInfo> fields) {
        // ignore
        if (fields.isEmpty()) {
            return;
        }
        SerializableTriFunction<BeanAccess<?>, Object, Integer, Object> getIndexField = BeanAccess::getIndexField;
        SerializableConsumerWithArgs4<BeanAccess<?>, Object, Integer, Object> setIndexField = BeanAccess::setIndexField;
        insertStdFieldInvoker(cw, type, fields, getIndexField, setIndexField);
    }
    private static <T> void insertStdFieldInvoker(ClassWriter cw, Class<T> type, List<BeanPropertyInfo> fields, SerializableTriFunction<BeanAccess<?>, Object, Integer, Object> getter, SerializableConsumerWithArgs4<BeanAccess<?>, Object, Integer, Object> setter) {
        // getter
        {
            MethodVisitor methodVisitor = cw.visitMethod(ACC_PROTECTED, getter.serialized().getImplMethodName(), "(Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ILOAD, 2);

            Label[] labels = Asms.newLabels(fields.size());
            Label labelDefault = new Label();
            methodVisitor.visitTableSwitchInsn(0, fields.size() - 1, labelDefault, labels);

            for (int idxField = 0; idxField < fields.size(); idxField++) {
                methodVisitor.visitLabel(labels[idxField]);

                BeanPropertyInfo info = fields.get(idxField);
                Field field = info.getField();
                String fieldName = field.getName();
                Type fieldType = Type.getType(field.getType());

                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitTypeInsn(CHECKCAST, Type.getInternalName(type));
                if (Modifier.isStatic(field.getModifiers())) {
                    methodVisitor.visitInsn(POP);
                    methodVisitor.visitFieldInsn(GETSTATIC, Type.getInternalName(field.getDeclaringClass()), fieldName, Type.getDescriptor(field.getType()));
                } else {
                    methodVisitor.visitFieldInsn(GETFIELD, Type.getInternalName(type), fieldName, Type.getDescriptor(field.getType()));
                }
                Asms.autoBoxing(methodVisitor, fieldType);
                methodVisitor.visitInsn(ARETURN);
            }

            // default
            methodVisitor.visitLabel(labelDefault);
            methodVisitor.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitLdcInsn("Field not found");
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
            methodVisitor.visitInsn(ATHROW);

            methodVisitor.visitMaxs(0, 0);
            methodVisitor.visitEnd();

        }
        // setter
        {
            MethodVisitor methodVisitor = cw.visitMethod(ACC_PROTECTED, setter.serialized().getImplMethodName(), "(Ljava/lang/Object;ILjava/lang/Object;)V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ILOAD, 2);

            Label[] labels = Asms.newLabels(fields.size());

            Label labelDefault = new Label();
            methodVisitor.visitTableSwitchInsn(0, fields.size() - 1, labelDefault, labels);
            for (int idxField = 0; idxField < fields.size(); idxField++) {
                methodVisitor.visitLabel(labels[idxField]);

                BeanPropertyInfo info = fields.get(idxField);
                Field field = info.getField();
                String fieldName = field.getName();
                Type fieldType = Type.getType(field.getType());

                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitTypeInsn(CHECKCAST, Type.getInternalName(type));
                if (Modifier.isStatic(field.getModifiers())) {
                    methodVisitor.visitInsn(POP);
                }
                methodVisitor.visitVarInsn(ALOAD, 3);
                Asms.autoUnBoxing(methodVisitor, fieldType);
                if (Modifier.isStatic(field.getModifiers())) {
                    methodVisitor.visitFieldInsn(PUTSTATIC, Type.getInternalName(field.getDeclaringClass()), fieldName, fieldType.getDescriptor());
                } else {
                    methodVisitor.visitFieldInsn(PUTFIELD, Type.getInternalName(field.getDeclaringClass()), fieldName, fieldType.getDescriptor());
                }
                methodVisitor.visitInsn(RETURN);
            }
            // default
            methodVisitor.visitLabel(labelDefault);
            methodVisitor.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitLdcInsn("Field not found");
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
            methodVisitor.visitInsn(ATHROW);
            methodVisitor.visitMaxs(0, 0);
            methodVisitor.visitEnd();
        }
    }
    public Object getIndexProperty(Object o, int methodIndex) {
        throw new IllegalArgumentException("Method not found");
    }

    public Object getIndexField(Object o, int fieldIndex) {
        throw new IllegalArgumentException("Field not found");
    }

    public void setIndexField(Object o, int methodIndex, Object val) {
        throw new IllegalArgumentException("Field not found");
    }
}

