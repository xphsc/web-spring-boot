/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.boot.version.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.common.WebBeanTemplate.APIVERSION_PREFIX;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.0
 */
@ConfigurationProperties(prefix = APIVERSION_PREFIX)
public class ApiVersionProperties {

    private   String header = "API-VERSION";
    /**
     * 版本类型
     */
    private Type type = Type.URI;

    /**
     * URI地址前缀, 例如: /api
     */
    private String uriPrefix;

    /**
     * URI的位置
     */
    private UriLocation uriLocation = UriLocation.BEGIN;

    /**
     * 版本请求参数名
     */
    private String param_name = "api_version";

    public enum Type {
        /**
         * URI路径
         */
        URI,
        /**
         * 请求头
         */
        HEADER,
        /**
         * 请求参数
         */
        PARAM;
    }

    public enum UriLocation {
        BEGIN, END
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUriPrefix() {
        return uriPrefix;
    }

    public void setUriPrefix(String uriPrefix) {
        this.uriPrefix = uriPrefix;
    }

    public UriLocation getUriLocation() {
        return uriLocation;
    }

    public void setUriLocation(UriLocation uriLocation) {
        this.uriLocation = uriLocation;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }
}
