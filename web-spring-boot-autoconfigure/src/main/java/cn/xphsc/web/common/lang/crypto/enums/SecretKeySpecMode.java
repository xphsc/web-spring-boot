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

import javax.crypto.spec.SecretKeySpec;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 密钥规格
 * @since 2.0.2
 */
public enum SecretKeySpecMode {

    /**
     * AES
     */
    AES("AES"),

    /**
     * DES
     */
    DES("DES"),

    /**
     * 3DES
     */
    DES3("DESEDE"),

    /**
     * SM4
     */
    SM4("SM4"),

    /**
     * HmacMD5
     */
    HMAC_MD5("HmacMD5"),

    /**
     * HmacSHA1
     */
    HMAC_SHA1("HmacSHA1"),

    /**
     * HmacSHA224
     */
    HMAC_SHA224("HmacSHA224"),

    /**
     * HmacSHA256
     */
    HMAC_SHA256("HmacSHA256"),

    /**
     * HmacSHA384
     */
    HMAC_SHA384("HmacSHA384"),

    /**
     * HmacSHA512
     */
    HMAC_SHA512("HmacSHA512"),

    ;

    private final String mode;

    SecretKeySpecMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public SecretKeySpec getSecretKeySpec(byte[] bs) {
        return new SecretKeySpec(bs, mode);
    }

}
