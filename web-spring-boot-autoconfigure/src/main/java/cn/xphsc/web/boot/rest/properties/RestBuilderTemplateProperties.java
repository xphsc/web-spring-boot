/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.boot.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.common.WebBeanTemplate.REST_PREFIX;
/**8c5e6935138f
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = REST_PREFIX)
public class RestBuilderTemplateProperties {

    private int readTimeout=-1;
    private int connectionTimeout=-1;
    private boolean   ignoreCertificate;

    private String charset = "UTF-8";

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public boolean isIgnoreCertificate() {
        return ignoreCertificate;
    }

    public void setIgnoreCertificate(boolean ignoreCertificate) {
        this.ignoreCertificate = ignoreCertificate;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
