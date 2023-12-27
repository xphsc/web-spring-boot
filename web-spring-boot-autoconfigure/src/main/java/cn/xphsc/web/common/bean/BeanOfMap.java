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

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class BeanOfMap {
    /**
     * 目标对象
     */
    private Object target;
    /**
     * 属性集合
     */
    private BeanMap beanMap;

    public BeanOfMap(Class superclass, Map<String, Class> propertyMap) {
        this.target = generateBean(superclass, propertyMap);
        this.beanMap = BeanMap.create(this.target);
    }
    /**
     * bean 添加属性和值
     *
     * @param property
     * @param value
     */
    public void setValue(String property, Object value) {
        beanMap.put(property, value);
    }
    /**
     * 获取属性值
     *
     * @param property
     * @return
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }
    /**
     * 获取对象
     *
     * @return
     */
    public Object getTarget() {
        return this.target;
    }
    /**
     * 根据属性生成对象
     *
     * @param superclass
     * @param propertyMap
     * @return
     */
    private Object generateBean(Class superclass, Map<String, Class> propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        if (null != superclass) {
            generator.setSuperclass(superclass);
        }
        BeanGenerator.addProperties(generator, propertyMap);
        return generator.create();
    }

}
