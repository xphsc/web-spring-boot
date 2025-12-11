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
package cn.xphsc.web.boot.log.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.common.WebBeanTemplate.*;
import static cn.xphsc.web.common.WebBeanTemplate.LOG_PREFIX;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @date:
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = LOG_PREFIX)
public class OperationLogProperties {
    /**
     * Log Switch
     */
    private boolean enabled;
    private int order=LOG_PREFIX_ORDER;
    /**
     *  is async
     */
    private  boolean async;
    /**
     *  Whether to fill in abnormal content
     */
    private boolean fillFailContent;
    /**
     *  Log content delimiter
     */
    private String contrastSeparator="|";
    /**
     * Custom Exception Class
     */
    private String[]  exceptionClassName;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isFillFailContent() {
        return fillFailContent;
    }

    public void setFillFailContent(boolean fillFailContent) {
        this.fillFailContent = fillFailContent;
    }

    public String getContrastSeparator() {
        return contrastSeparator;
    }

    public void setContrastSeparator(String contrastSeparator) {
        this.contrastSeparator = contrastSeparator;
    }

    public String[] getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String[] exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }
}
