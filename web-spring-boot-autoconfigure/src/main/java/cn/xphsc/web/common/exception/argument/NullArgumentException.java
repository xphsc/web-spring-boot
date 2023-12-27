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
package cn.xphsc.web.common.exception.argument;


/**
 * {@link RuntimeException}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 验证对象为空异常
 * @since 1.1.7
 */
public class NullArgumentException extends InvalidArgumentException {

    public NullArgumentException() {
    }

    public NullArgumentException(String message) {
        super(message);
    }

    public NullArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullArgumentException(Throwable cause) {
        super(cause);
    }

}
