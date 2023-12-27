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
package cn.xphsc.web.boot.xss.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.common.WebBeanTemplate.XSS_PREFIX;
import static cn.xphsc.web.common.WebBeanTemplate.XSS_PREFIX_ORDER;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = XSS_PREFIX )
public class XssFilterProperties {
    /**
     *
     */
    private boolean parameterEabled;
    private String   excludes="";
    private String urlPatterns="/*";
    private int order=XSS_PREFIX_ORDER;

    public boolean isParameterEabled() {
        return parameterEabled;
    }

    public void setParameterEabled(boolean parameterEabled) {
        this.parameterEabled = parameterEabled;
    }

    public String getExcludes() {
        return excludes;
    }

    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }

    public String getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
