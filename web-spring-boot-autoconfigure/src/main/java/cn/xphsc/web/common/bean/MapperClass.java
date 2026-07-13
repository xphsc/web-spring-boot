/*
 * Copyright (c) 2020 huipei.x
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
package cn.xphsc.web.common.bean;
import cn.xphsc.web.utils.Collects;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Mapper Class
 * @since 1.0.0
 */
public class MapperClass {

    private Object origClass;
    private Object targetClass;
    private Map<String,String>  mapper;
    private Converter converter;
    private  Set excludeProperties;
    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    protected MapperClass(){}
    public MapperClass getSelf() {
        return this;
    }
    protected <T> T  copyByMapper(){
        BeanCopier copier = null;
        BeanMap beanMap= BeanMap.create(targetClass);
        beanMap.setBean(targetClass);
        Map<String,Object>  beanToMap= BeanMap.create((origClass));
        if(Collects.isNotEmpty(excludeProperties)){
            Iterator excludeIterator = excludeProperties.iterator();
            while(excludeIterator.hasNext()) {
                beanToMap.put(excludeIterator.next().toString(),null);
            }
        }
        if(Collects.isNotEmpty(mapper)){
            for(Map.Entry entity:mapper.entrySet()){
                for(Map.Entry beanEntity:beanToMap.entrySet()){
                    if(entity.getKey().equals(beanEntity.getKey())){
                        beanMap.put(entity.getValue().toString(),beanEntity.getValue());
                    }else{
                        beanMap.put(beanEntity.getKey(),beanEntity.getValue());

                    }
                }

            }
        }
        String key = genKey(origClass.getClass(), targetClass.getClass());
        if(converter!=null){
             copier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                    k -> BeanCopier.create(origClass.getClass(),targetClass.getClass(), true));
            }else{
            copier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                    k -> BeanCopier.create(origClass.getClass(),targetClass.getClass(), false));
            }
        copier.copy(origClass,targetClass,converter!=null?converter:null);
        if(!hasTargetSetter(targetClass.getClass())){
            copyFieldsWithoutSetter(origClass,targetClass);
        }
        return (T) targetClass;
    }


    public static Builder builder(Object origClass,Class<?> targetClass){
        return  new Builder( origClass, targetClass);
    }

    private MapperClass(Builder builder){
      this.converter=builder.converter;
        this.mapper=builder.mapper;
        this.targetClass= builder.targetClass;
        this.origClass= builder.origClass;
        this.excludeProperties= builder.excludeProperties;
    }
    public static class Builder {
        private Map<String,String>  mapper;
        private Converter converter;
        private Object origClass;
        private Object targetClass;
        private  Set excludeProperties;
        public Builder(Object origClass,Class<?> targetClass){
            this.targetClass= targetObject(targetClass);
            this.origClass= origClass;
        }

        /**
         * origProperty mapping  targetPropert
         */
        public Builder  mapping(String origProperty, String targetPropert){
            mapper=new HashMap<>(10);
            mapper.put(origProperty,targetPropert);
            return this;
        }
        /**
         * origProperty mapping  targetPropert
         */
        public Builder  mapping(Map<String,String>  mapper){
            this.mapper=mapper;
            return this;
        }

        /**
         * exclude properties
         */
        public Builder exclude(String... properties) {
            if (this.excludeProperties == null) {
                this.excludeProperties = new HashSet();
            }
            if (properties != null) {
                this.excludeProperties.addAll(Arrays.asList(properties));
            }
            return this;
        }
        public Builder  converter(Converter converter){
            this.converter=converter;
            return this;
        }


        public MapperClass build() {
            return new MapperClass(this);
        }
    }



    protected   static <T> List<T> copyByMapper(List<?> origlist, Class<?> targetClass){
        List<T> result=new ArrayList(10);
        for(Object object:origlist){
            Object instance= targetObject(targetClass);
            BeanCopier beanCopier=beanCopier(object,targetClass,false);
            beanCopier.copy(object,instance,null);
            if(!hasTargetSetter(targetClass)){
                copyFieldsWithoutSetter(object,targetClass);
            }
            result.add((T) instance);
        }
        return result;
    }


    private static Object targetObject(Class<?> targetClass){
        Object instance = null;
        try {
            try {
                instance = targetClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return  instance;
    }

    protected static BeanCopier beanCopier(Object origClass, Class<?> targetClass, boolean converter){
        BeanCopier copier;
        String key = genKey(origClass.getClass(), targetClass);
        if(!converter){
            copier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                    k -> BeanCopier.create(origClass.getClass(),targetClass, false));
        }else{
            copier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                    k -> BeanCopier.create(origClass.getClass(),targetClass, true));
        }
       return copier;
    }

    private static String genKey(Class<?> sourceClass, Class<?> targetClass) {
        return sourceClass.getName() + "->" + targetClass.getName();
    }

    private static void copyFieldsWithoutSetter(Object source, Object target) {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Set<String> targetSetterProps = new HashSet<>();
        for (Method method : targetClass.getMethods()) {
            if (isSetter(method)) {
                String propName = method.getName().substring(3);
                targetSetterProps.add(propName.toLowerCase());
            }
        }
        for (Field sourceField : getAllFields(sourceClass)) {
            sourceField.setAccessible(true);
            String fieldName = sourceField.getName();
            try {
                Object value = sourceField.get(source);
                if(value!=null){
                    if (!targetSetterProps.contains(fieldName.toLowerCase())&&!fieldName.equals("serialVersionUID")) {
                        try {
                            Field targetField = getField(targetClass, fieldName);
                            if (targetField != null) {
                                targetField.setAccessible(true);
                                targetField.set(target, value);
                            }
                        } catch (Exception ignored) {
                            // 忽略异常，继续尝试拷贝其它字段
                        }
                    }
                }

            } catch (IllegalAccessException e) {

            }
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
    private static Field[] getAllFields(Class<?> clazz) {
        Set<Field> fields = new HashSet<>();
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            Field[] declared = current.getDeclaredFields();
            for (Field f : declared) {
                fields.add(f);
            }
            current = current.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }

    private static boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && method.getParameterCount() == 1
                && method.getReturnType() == void.class;
    }
    private static boolean hasTargetSetter( Class<?> targetClass) {
        Method method  = Arrays.stream(targetClass.getMethods()).filter(m->m.getName().startsWith("set")).findFirst().get();
        if(hasSuperclass(targetClass)){
            method  = Arrays.stream(targetClass.getSuperclass().getMethods()).filter(m->m.getName().startsWith("set")).findFirst().get();
        }
        return isSetter(method);
    }
    public static boolean hasSuperclass(Class<?> clazz) {
        return clazz != null && clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class);
    }

}
