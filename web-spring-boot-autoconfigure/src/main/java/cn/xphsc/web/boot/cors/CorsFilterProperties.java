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
package cn.xphsc.web.boot.cors;


import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.common.WebBeanTemplate.CORS_PREFIX;



/**
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:   Core 跨域相关配置
 * @since 1.0.0
 */

@ConfigurationProperties(CORS_PREFIX)
public class CorsFilterProperties {

    private Boolean enabled;
    private String path;
    private String origin;
    private String allowedHeader;
    private String method;
    private String exposedHeader;

    private Boolean allowCredentials;
    private Long maxAge;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAllowedHeader() {
        return allowedHeader;
    }

    public void setAllowedHeader(String allowedHeader) {
        this.allowedHeader = allowedHeader;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getExposedHeader() {
        return exposedHeader;
    }

    public void setExposedHeader(String exposedHeader) {
        this.exposedHeader = exposedHeader;
    }

    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }
}
