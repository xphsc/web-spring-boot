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
 * @description: 填充模式
 * @since 2.0.2
 */
public enum PaddingMode {

    /**
     * PKCS1
     */
    PKCS1_PADDING("PKCS1Padding"),

    /**
     * PKCS5
     */
    PKCS5_PADDING("PKCS5Padding"),

    /**
     * PKCS7
     */
    PKCS7_PADDING("PKCS7Padding"),

    /**
     * ISO10126
     */
    ISO_10126_PADDING("ISO10126Padding"),

    /**
     * ANSI_X_923_PADDING
     */
    ANSI_X_923_PADDING("X9.23PADDING"),

    /**
     * SSL3Padding
     */
    SSL3_PADDING("SSL3Padding"),

    /**
     * 不填充
     */
    NO_PADDING("NoPadding"),

    /**
     * 0填充 需要自己实现
     */
    ZERO_PADDING("NoPadding");

    private final String mode;

    PaddingMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

}
