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
package cn.xphsc.web.boot.tree.autoconfigure;


import cn.xphsc.web.tree.ObjectTreeNode;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import static cn.xphsc.web.common.WebBeanTemplate.ENABLED;
import static cn.xphsc.web.common.WebBeanTemplate.TRUE;

/**
 * {@link }
 *
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Configurable
@EnableConfigurationProperties(ObjectTreeProperties.class)
@ConditionalOnProperty(prefix = ObjectTreeProperties.TEEE_PREFIX, name = ENABLED,havingValue = TRUE)
public class ObjectTreeConfiguration implements Ordered {

    private ObjectTreeProperties objectTreeProperties;

    public ObjectTreeConfiguration(ObjectTreeProperties objectTreeProperties){
        this.objectTreeProperties=objectTreeProperties;
    }
    @Bean
    public ObjectTreeNode objectTreeNode(){
        return ObjectTreeNode.builder().packages(objectTreeProperties.getPackages()).build();
    }

    @Override
    public int getOrder() {
        return objectTreeProperties.getOrder();
    }
}
