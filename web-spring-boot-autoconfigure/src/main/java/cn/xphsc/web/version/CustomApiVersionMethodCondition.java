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
package cn.xphsc.web.version;

import cn.xphsc.web.boot.version.autoconfigure.ApiVersionProperties;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.0
 */
public class CustomApiVersionMethodCondition implements RequestCondition<CustomApiVersionMethodCondition> {

    private final String apiVersion;
    private final ApiVersionProperties apiVersionProperties;

    public CustomApiVersionMethodCondition(String apiVersion, ApiVersionProperties apiVersionProperties) {
        this.apiVersion = apiVersion.trim();
        this.apiVersionProperties = apiVersionProperties;
    }

    @Override
    public CustomApiVersionMethodCondition combine(CustomApiVersionMethodCondition other) {
        return new CustomApiVersionMethodCondition(other.getApiVersion(), other.apiVersionProperties);
    }

    @Override
    public int compareTo(CustomApiVersionMethodCondition other, HttpServletRequest request) {
        return other.getApiVersion().compareTo(getApiVersion());
    }

    @Override
    public CustomApiVersionMethodCondition getMatchingCondition(HttpServletRequest request) {
        ApiVersionProperties.Type type = apiVersionProperties.getType();
        String version = null;
        switch (type) {
            case HEADER:
                version = request.getHeader(apiVersionProperties.getHeader());
                break;
            case PARAM:
                version = request.getParameter(apiVersionProperties.getParam_name());
                break;
        }

        boolean match = version != null && version.length() > 0 && version.trim().equals(apiVersion);
        if (match) {
            return this;
        }
        return null;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public String toString() {
        return "@ApiVersion(" + apiVersion + ")";
    }



}
