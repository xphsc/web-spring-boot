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
package cn.xphsc.web.common.lang.crypto.symmetric;

import cn.xphsc.web.utils.ByteUtils;
import java.util.Arrays;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 对称加密
 * @since 2.0.2
 */
public interface SymmetricCrypto {

    /**
     * 加密
     * plain 明文
     */
    byte[] encrypt(byte[] plain);

    /**
     * 加密
     * plain 明文
     */
    default byte[] encrypt(String plain) {
        return this.encrypt(ByteUtils.toBytes(plain));
    }

    /**
     * 加密
     *  plain 明文
     */
    default String encryptAsString(String plain) {
        return new String(this.encrypt(ByteUtils.toBytes(plain)));
    }

    /**
     * 加密
     * plain 明文
     */
    default String encryptAsString(byte[] plain) {
        return new String(this.encrypt(plain));
    }

    /**
     * 解密
     *  text 密文
     */
    byte[] decrypt(byte[] text);

    /**
     * 解密
     *  text 密文
     */
    default byte[] decrypt(String text) {
        return this.decrypt(ByteUtils.toBytes(text));
    }

    /**
     * 解密
     *  text 密文
     */
    default String decryptAsString(String text) {
        return new String(this.decrypt(ByteUtils.toBytes(text)));
    }

    /**
     * 解密
     * text 密文
     */
    default String decryptAsString(byte[] text) {
        return new String(this.decrypt(text));
    }

    /**
     * 验证加密结果
     *  plain 明文
     *  text  密文
     */
    default boolean verify(String plain, String text) {
        try {
            return plain.equals(this.decryptAsString(text));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证加密结果
     *  plain 明文
     *  text  密文
     */
    default boolean verify(byte[] plain, byte[] text) {
        try {
            return Arrays.equals(plain, this.decrypt(text));
        } catch (Exception e) {
            return false;
        }
    }

}
