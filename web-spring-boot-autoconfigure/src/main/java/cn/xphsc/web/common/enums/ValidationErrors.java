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
public enum ValidationErrors {
    /**
     *
     */
    UNKNOW_EXCEPTION(0, "unknow exception"),
    UN_AUTHORIZATION(10001, "un authorization"),
    PARAMS_ERROR(10002, "invalid params"),
    INVALID_TOKEN(10003, "invaild token"),
    USERNAME_OR_PASSWORD_ERROR(10004, "username or password error"),
    PAGE_NO_FOUND(10005),
    SINGLE_USER_ERROR(10006, "has user, can't add more"),
    USER_ACCESS_NOT_INIT(10007, "user access not init"),
    NO_ENABLE_EMAIL_CONFIG(10008, "no enable email-config"),
    SEND_EMAIL_FAILD(10009, "send email faild"),
    USER_INIT_API_REFUSE(10010, "this api user init use only"),
    INVALID_EMAIL_CODE(10011, "invalid email code"),
    INVALID_PARAMS(10012, "invalid params"),
    AUTH2_CODE_ERROR(10013, "get token failed auth2 code error message: %s"),
    ;

    private int code;
    private String msg;

    ValidationErrors(int code) {
        this.code = code;
        this.msg = this.name();
    }

    ValidationErrors(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
