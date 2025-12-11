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
package cn.xphsc.web.common.lang.crypto.enums;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 工作模式
 * @since 2.0.2
 */
public enum WorkingMode {

    /**
     * None
     */
    NONE("None"),

    /**
     * 电子密码本模式 AES DES 3DES
     */
    ECB("ECB"),

    /**
     * 密码分组连接模式 AES DES 3DES
     */
    CBC("CBC"),

    /**
     * 密文反馈模式 AES DES 3DES
     */
    CFB("CFB"),

    /**
     * 输出反馈模式 AES DES 3DES
     */
    OFB("OFB"),

    /**
     * 计数器模式 AES DES 3DES
     */
    FTP("FTP"),

    /**
     * 加密认证模式 AES
     */
    GCM("GCM");

    private final String mode;

    WorkingMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

}
