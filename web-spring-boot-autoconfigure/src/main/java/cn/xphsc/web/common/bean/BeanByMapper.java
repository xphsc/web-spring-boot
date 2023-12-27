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




import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import java.util.*;


/**
 * {@link MapperClass}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Object Copy
 * @since 1.0.0
 */
public final class BeanByMapper {

    public static void  copyByMapper(Object origClass,Object target){
        BeanCopier beanCopier= MapperClass.beanCopier(origClass,target.getClass(),false);
        beanCopier.copy(origClass,target,null);
    }


    public static <T> T   copyByMapper(Object origClass,Class<?> targetClass){
       return (T) MapperClass.builder(origClass,targetClass).build().copyByMapper();
    }

    public static <T> T  copyByMapper(Object origClass,Class<?> targetClass,Converter converter){
         return MapperClass.builder(origClass,targetClass).converter(converter).build().copyByMapper();
    }

    public static <T> T   copyByMapper(Object origClass,Class<?> targetClass,Map<String,String> map){
        return MapperClass.builder(origClass,targetClass).mapping(map).build().copyByMapper();
    }

    public static  <T> T copyByMapper(MapperClass mapperClass){
        return  mapperClass.copyByMapper();
    }

    public  static <T> List<T> copyByMapper(List<?> origlist, Class<?> targetClass){
        return  MapperClass.copyByMapper(origlist,targetClass);
    }



}
