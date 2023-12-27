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
package cn.xphsc.web.common.exception;

import java.lang.reflect.Type;
/**
 * {@link RuntimeException}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class DeserializationException extends RuntimeException {
    public static final int ERROR_CODE = 101;
    private static final long serialVersionUID = -2742350751684273728L;
    private static final String DEFAULT_MSG = " deserialize failed. ";
    private static final String MSG_FOR_SPECIFIED_CLASS = "Nacos deserialize for class [%s] failed. ";
    private Class<?> targetClass;

    public static final String ERROR_MESSAGE_FORMAT = "errCode: %d, errMsg: %s ";
    private int errCode;

    public DeserializationException(int errCode) {
        this.errCode = errCode;
    }

    public DeserializationException(int errCode, String errMsg) {
        super(String.format("errCode: %d, errMsg: %s ", errCode, errMsg));
        this.errCode = errCode;
    }

    public DeserializationException(int errCode, Throwable throwable) {
        super(throwable);
        this.errCode = errCode;
    }

    public DeserializationException(int errCode, String errMsg, Throwable throwable) {
        super(String.format("errCode: %d, errMsg: %s ", errCode, errMsg), throwable);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return this.errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
    public DeserializationException() {
        new DeserializationException(101);
    }

    public DeserializationException(Class<?> targetClass) {
        new DeserializationException(101, String.format("Jackson deserialize for class [%s] failed. ", targetClass.getName()));
        this.targetClass = targetClass;
    }

    public DeserializationException(Type targetType) {
        new DeserializationException(101, String.format("Jackson deserialize for class [%s] failed. ", targetType.toString()));
    }

    public DeserializationException(Throwable throwable) {
        new DeserializationException(101, " deserialize failed. ", throwable);
    }

    public DeserializationException(Class<?> targetClass, Throwable throwable) {
        new DeserializationException(101, String.format("Jackson deserialize for class [%s] failed. ", targetClass.getName()), throwable);
        this.targetClass = targetClass;
    }

    public DeserializationException(Type targetType, Throwable throwable) {
        new DeserializationException(101, String.format("Jackson deserialize for class [%s] failed. ", targetType.toString()), throwable);
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

}
