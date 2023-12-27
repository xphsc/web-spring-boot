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
package cn.xphsc.web.dicttraslate.entity;

import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class DictTranslationEntity {
    private DictTransType dictTargetType;
    /**
     * 原字段
     */
    private String sourceField;
    /**
     * 当前字典转义使用的字典
     */
    private Map<String, String> dictDetail;
    /**
     * 目标字段，字典转义的字段name的字段名称
     */
    private String targetField;
    /**
     * 是否是多个字典值拼接的形式
     */
    private Boolean multiple;
    /**
     * 原字段是null时的缺省值
     */
    private String nullValue;
    /**
     * 找不到字典对应时的缺省值
     */
    private String undefinedValue;

    /**
     * 当字典字段是实体类或者集合的时候，将每个字段的信息放到这个里面
     */
    private List<DictTranslationEntity> collectionDictInfo;


    public DictTranslationEntity(String sourceField, Map<String, String> dictDetail, String targetField, boolean multiple, String nullValue, String undefinedValue) {
        this.dictTargetType= DictTransType.FIELD;
        this.sourceField = sourceField;
        this.dictDetail = dictDetail;
        this.targetField = targetField;
        this.multiple = multiple;
        this.nullValue = nullValue;
        this.undefinedValue = undefinedValue;
    }

    public DictTranslationEntity(DictTransType dictTargetType, String sourceField, List<DictTranslationEntity> collectionDictInfo) {
        this.dictTargetType = dictTargetType;
        this.sourceField = sourceField;
        this.collectionDictInfo = collectionDictInfo;
    }

    public DictTransType getDictTargetType() {
        return dictTargetType;
    }

    public void setDictTargetType(DictTransType dictTargetType) {
        this.dictTargetType = dictTargetType;
    }

    public String getSourceField() {
        return sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public Map<String, String> getDictDetail() {
        return dictDetail;
    }

    public void setDictDetail(Map<String, String> dictDetail) {
        this.dictDetail = dictDetail;
    }

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public Boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public String getUndefinedValue() {
        return undefinedValue;
    }

    public void setUndefinedValue(String undefinedValue) {
        this.undefinedValue = undefinedValue;
    }

    public List<DictTranslationEntity> getCollectionDictInfo() {
        return collectionDictInfo;
    }

    public void setCollectionDictInfo(List<DictTranslationEntity> collectionDictInfo) {
        this.collectionDictInfo = collectionDictInfo;
    }

    @Override
    public String toString() {
        return "DictTranslationEntity{" +
                "dictTargetType=" + dictTargetType +
                ", sourceField='" + sourceField + '\'' +
                ", dictDetail=" + dictDetail +
                ", targetField='" + targetField + '\'' +
                ", multiple=" + multiple +
                ", nullValue='" + nullValue + '\'' +
                ", undefinedValue='" + undefinedValue + '\'' +
                ", collectionDictInfo=" + collectionDictInfo +
                '}';
    }
}
