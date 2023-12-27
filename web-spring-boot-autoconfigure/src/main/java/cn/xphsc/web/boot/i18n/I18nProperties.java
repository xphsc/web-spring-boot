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
package cn.xphsc.web.boot.i18n;


import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.common.WebBeanTemplate.I18N_PREFIX;

/**
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 国际化资源配置属性
 * @since 1.0.0
 */

@ConfigurationProperties(prefix = I18N_PREFIX)
public class I18nProperties {

    private boolean enabled = true;
    /**
     * 默认 UTF-8
     */
    private String defaultEncoding;
    private String[] baseNames;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public String[] getBaseNames() {
        return baseNames;
    }

    public void setBaseNames(String[] baseNames) {
        this.baseNames = baseNames;
    }
}
