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
package cn.xphsc.web.boot.validation;

import cn.xphsc.web.common.enums.ValidationErrors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static cn.xphsc.web.common.WebBeanTemplate.VALIDATION_PREFIX;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: onfiguration properties
 * @since 1.1.4
 */
@ConfigurationProperties(prefix = VALIDATION_PREFIX)
public class ValidationProperties {

    private Integer code= ValidationErrors.INVALID_PARAMS.getCode();
    private String message=ValidationErrors.INVALID_PARAMS.getMsg();
    private Integer status=400;
    private int order=-9999;
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
