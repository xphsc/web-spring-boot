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
package cn.xphsc.web.log.entity;



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class ExtendFiledEntity {
    /**
     * 字段名称
     */
    private      String fieldName;
    /**
     * 字段说明
     */
   private      String fieldDescription;
    /**
     * 字段值
     */
    private      Object fieldValue;

    /**
     * 脱敏值
     */
    private      Object desensitizationValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Object getDesensitizationValue() {
        return desensitizationValue;
    }

    public void setDesensitizationValue(Object desensitizationValue) {
        this.desensitizationValue = desensitizationValue;
    }

    @Override
    public String toString() {
        return "ExtendFiledEntity{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldDescription='" + fieldDescription + '\'' +
                ", fieldValue=" + fieldValue +
                ", desensitizationValue=" + desensitizationValue +
                '}';
    }
}
