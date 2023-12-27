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
package cn.xphsc.web.boot.dicttraslate.builder;

import cn.xphsc.web.common.context.SpringContextHolder;
import cn.xphsc.web.dicttraslate.annotation.*;
import cn.xphsc.web.dicttraslate.entity.DictTransType;
import cn.xphsc.web.dicttraslate.entity.DictTranslationEntity;
import cn.xphsc.web.dicttraslate.handler.DictTransHandler;
import cn.xphsc.web.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ObjectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class DictTranslationBuilder {

    private DictTransHandler dictTransHandler;
    private final Log log = LogFactory.getLog(DictTranslationBuilder.class);

    public Object dictTransResult(Object result, Method method, boolean dictMap) {
        List<DictTranslationEntity> dictMapping;
        DictTranslation dictTranslation;
        DictTransMap dictTransMap = null;
       if(!dictMap) {
            if(method.getAnnotation(DictTranslation.class)!=null){
                dictTranslation=(method.getAnnotation(DictTranslation.class));
                dictTransHandler = SpringContextHolder.getBean(dictTranslation.dictTransHandler());
            }else if(method.getDeclaringClass().isAnnotationPresent(DictTranslation.class)){
                dictTranslation=method.getDeclaringClass().getDeclaredAnnotation(DictTranslation.class);
                dictTransHandler = SpringContextHolder.getBean(dictTranslation.dictTransHandler());

            }
       }else{
            if(method.getAnnotation(DictTransMap.class)!=null){
                dictTransMap=method.getAnnotation(DictTransMap.class);
                dictTransHandler = SpringContextHolder.getBean(dictTransMap.dictTransHandler());
            }else if(method.getDeclaringClass().isAnnotationPresent(DictTransMap.class)){
                dictTransMap=method.getDeclaringClass().getDeclaredAnnotation(DictTransMap.class);
                dictTransHandler = SpringContextHolder.getBean(dictTransMap.dictTransHandler());

            }
        }
        Object obj = null;;
        if (result != null) {
            //判断拦截的方法的返回值类型是否合法
            if (result instanceof List) {
                List olist = (List) result;
                if (olist.size() == 0) {
                    return result;
                }
                if(!dictMap){
                    obj = olist.get(0);
                }else {
                    obj = ((List) result).get(0);
                }
            } else {
                if(dictMap) {
                    if (obj != null && !(obj instanceof Map)) {
                        return result;
                    }
                }else{
                    obj = result;
                }

            }
            if(dictMap) {

                dictMapping = getDictTransMapping(null,dictTransMap.value());
            }else{
               dictMapping = getDictTransMapping(obj.getClass(),null);
            }
            //获取字典转义目录

            if (dictMapping.size() == 0) {
                return result;
            }

            if (result instanceof List) {
                if (dictMap) {
                for (Map entity : (List<Map>) result) {
                    getObjectForMap(entity, dictMapping);
                   }
                } else{
                    for (Object entity : (List) result) {
                        getObject(entity, dictMapping);
                    }
             }
            } else {
                if (dictMap) {
                    getObjectForMap((Map) result, dictMapping);
                }else{
                 getObject(result, dictMapping);
                }
            }
        }
        return result;
    }

    private void getObject(Object entity, List<DictTranslationEntity> dictMapping) {
        try {
            for (DictTranslationEntity dictTranslationEntity : dictMapping) {
                if (dictTranslationEntity.getDictTargetType() == DictTransType.FIELD) {
                    String dictName = dictTranslationEntity.getSourceField();
                    String transField = dictTranslationEntity.getTargetField();
                    if (StringUtils.isBlank(transField)) {
                        transField = dictName + "Name";
                    }
                    String nullValue = dictTranslationEntity.getNullValue();
                    String undefinedValue = dictTranslationEntity.getUndefinedValue();
                    Map<String, String> dict = dictTranslationEntity.getDictDetail();

                    Class entityClass = entity.getClass();
                    if (entityClass != null) {
                        Field dictField = entityClass.getDeclaredField(dictName);
                        dictField.setAccessible(true);
                        Field targetValue = entityClass.getDeclaredField(transField);
                        targetValue.setAccessible(true);
                        if (targetValue.getType() != String.class) {
                            log.error("dict Translation  error Field " + transField + " typeis not String");
                            continue;
                        }
                        Object preValue = dictField.get(entity);
                        if (!ObjectUtils.isEmpty(preValue)) {
                            String preValueStr = String.valueOf(preValue);
                            // 需要赋值的字段
                            if (dictTranslationEntity.isMultiple()) {
                                StringBuffer buffer = new StringBuffer();
                                String[] strings = preValueStr.split(",");
                                for (String string : strings) {
                                    String name = dict.get(string);
                                    buffer.append(name == null ? undefinedValue : name).append(",");
                                }
                                targetValue.set(entity, buffer.deleteCharAt(buffer.length() - 1).toString());
                            } else {
                                String name = dict.get(preValueStr);
                                targetValue.set(entity, name == null ? undefinedValue : name);
                            }
                        } else {
                            targetValue.set(entity, nullValue);
                        }
                    }
                } else {
                    Field fvalue = entity.getClass().getDeclaredField(dictTranslationEntity.getSourceField());
                    fvalue.setAccessible(true);
                    Object preValue = fvalue.get(entity);
                    if (!ObjectUtils.isEmpty(preValue)) {
                        if (dictTranslationEntity.getDictTargetType() == DictTransType.COLLECTION) {
                            for (Object o : (List) preValue) {
                                getObject(o, dictTranslationEntity.getCollectionDictInfo());
                            }
                        } else {
                            getObject(preValue, dictTranslationEntity.getCollectionDictInfo());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("dict Translation having an error. " + e.toString());
        }

    }


    private void getObjectForMap(Map entity, List<DictTranslationEntity> dictMapping) {
        for (DictTranslationEntity dictTranslationEntity : dictMapping) {
            String dictName = dictTranslationEntity.getSourceField();
            String transField = dictTranslationEntity.getTargetField();
            if (StringUtils.isBlank(transField)) {
                transField = dictName + "Name";
            }
            String nullValue = dictTranslationEntity.getNullValue();
            String undefinedValue = dictTranslationEntity.getUndefinedValue();
            Map<String, String> dict = dictTranslationEntity.getDictDetail();
            String preValue = null;
            if(StringUtils.isNotBlank(dictTranslationEntity.getSourceField())){
                preValue = entity.get(dictTranslationEntity.getSourceField()).toString();  
            }
            if (StringUtils.isNotEmpty(preValue)) {
                if (dictTranslationEntity.isMultiple()) {
                    StringBuffer buffer = new StringBuffer();
                    String[] strings = preValue.split(",");
                    for (String string : strings) {
                        String name = dict.get(string);
                        buffer.append(name == null ? undefinedValue : name).append(",");
                    }
                    entity.put(transField, buffer.deleteCharAt(buffer.length() - 1).toString());
                } else {
                    String name = dict.get(preValue);
                    entity.put(transField, name == null ? undefinedValue : name);
                }
            } else {
                entity.put(entity, nullValue);
            }

        }

    }


    private List<DictTranslationEntity> getDictTransMapping(Class clazz,DictTransMapper[] mappers) {
        List<DictTranslationEntity> list = new ArrayList<>();
        DictTranslationEntity dictTranslationEntity;
        DictField dict;
        if(mappers==null){
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(DictField.class)) {
                    dict = field.getAnnotation(DictField.class);
                    //获取字典转义参数信息
                    dictTranslationEntity = new DictTranslationEntity(field.getName(), dictTransHandler.dictTransByName(dict.dictName()), dict.dictTransField(), dict.multiple(), dict.nullValueName(), dict.undefinedValue());
                    list.add(dictTranslationEntity);
                }
                else if (field.isAnnotationPresent(DictEntity.class)) {
                    if (clazz != field.getClass()) {
                        dictTranslationEntity = new DictTranslationEntity(DictTransType.ENTITY, field.getName(), getDictTransMapping(field.getType(),null));
                        list.add(dictTranslationEntity);
                    } else {
                        log.error(field.getName() + "'s class type equals with parent is true");
                    }
                }
            }
        }else{
            for (DictTransMapper mapper : mappers) {
                //获取字典转义参数信息
                dictTranslationEntity= new DictTranslationEntity(mapper.dictName(), dictTransHandler.dictTransByName(mapper.dictName()), mapper.dictTransField(), mapper.multiple(), mapper.nullValueName(), mapper.undefinedValue());
                list.add(dictTranslationEntity);
            }
        }
        return list;
    }

}
