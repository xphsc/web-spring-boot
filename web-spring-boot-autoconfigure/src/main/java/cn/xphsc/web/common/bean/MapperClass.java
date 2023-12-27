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

import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class MapperClass {

    private Object origClass;
    private Object targetClass;
    private Map<String,String>  mapper;
    private Converter converter;
    private  Set excludeProperties;
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
        if(converter!=null){
                copier = BeanCopier.create(origClass.getClass(), targetClass.getClass(), true);
            }else{
                copier = BeanCopier.create(origClass.getClass(), targetClass.getClass(), false);
            }
        copier.copy(origClass,targetClass,converter!=null?converter:null);
        return (T) targetClass;
    }


    public static MapperClass.Builder builder(Object origClass,Class<?> targetClass){
        return  new MapperClass.Builder( origClass, targetClass);
    }

    private MapperClass(MapperClass.Builder builder){
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
        if(!converter){
            copier = BeanCopier.create(origClass.getClass(), targetClass, false);
        }else{
            copier = BeanCopier.create(origClass.getClass(), targetClass, true);
        }
       return copier;
    }


}
