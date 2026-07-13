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
package cn.xphsc.web.boot.response.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static cn.xphsc.web.common.WebBeanTemplate.RESPONSE_RESULT_PREFIX;

/**
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 响应结果配置属性
  * <p>配置优先级说明：方法或类上的 {@link cn.xphsc.web.response.annotation.ResponseResult} 注解配置
 * 优先于本配置文件中的全局配置</p>
 * @since 2.0.4
 */
@ConfigurationProperties(RESPONSE_RESULT_PREFIX)
public class ResponseResultProperties {

    /**
     * 默认响应类全限定名
     */
    private String defaultClass;

    /**
     * 默认响应成功方法名
     */
    private String defaultOkMethod = "ok";

    /**
     * 是否启用响应结果包装
     */
    private Boolean enabled ;

    public String getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(String defaultClass) {
        this.defaultClass = defaultClass;
    }

    public String getDefaultOkMethod() {
        return defaultOkMethod;
    }

    public void setDefaultOkMethod(String defaultOkMethod) {
        this.defaultOkMethod = defaultOkMethod;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
