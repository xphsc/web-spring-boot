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
package cn.xphsc.web.common.enums;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public enum ExceptionEnum {
    /**
     *
     */
    SQL_KEYWORDS_EXCEPTION(1050, "防御SQL注入，SQL脚本攻击"),
    SQL_EXCEPTION(1051, "SQL异常错误"),
    XSS_EXCEPTION(1052, "防御XSS注入，跨站脚本攻击"),
    CSRF_EXCEPTION(1053, "Sucessfully defended against CSRF attacks");
    private int code;
    private String name;

    ExceptionEnum(int code) {
        this.code = code;
        this.name = this.name();
    }

    ExceptionEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
}
