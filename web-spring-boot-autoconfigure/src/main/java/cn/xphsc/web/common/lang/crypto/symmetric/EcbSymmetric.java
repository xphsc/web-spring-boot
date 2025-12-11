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

import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.common.lang.crypto.enums.CipherAlgorithm;
import cn.xphsc.web.common.lang.crypto.enums.PaddingMode;
import cn.xphsc.web.common.lang.crypto.enums.WorkingMode;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import static org.springframework.util.Base64Utils.decode;
import static org.springframework.util.Base64Utils.encode;



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: ECB 模式非对称加密 AES DES 3DES
 * @since 2.0.2
 */
public class EcbSymmetric extends AbstractSymmetric {

    public EcbSymmetric(CipherAlgorithm algorithm, SecretKey secretKey) {
        this(algorithm, PaddingMode.PKCS5_PADDING, secretKey);
    }

    public EcbSymmetric(CipherAlgorithm algorithm, PaddingMode paddingMode, SecretKey secretKey) {
        super(algorithm, WorkingMode.ECB, paddingMode, secretKey);
    }

    @Override
    public byte[] encrypt(byte[] plain) {
        try {
            Cipher cipher = super.getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return encode(cipher.doFinal(this.zeroPadding(plain, cipher.getBlockSize())));
        } catch (Exception e) {
            throw Exceptions.runtime("encrypt data error", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] text) {
        try {
            Cipher cipher = super.getCipher();
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return this.clearZeroPadding(cipher.doFinal(decode(text)));
        } catch (Exception e) {
            throw Exceptions.runtime(
                    "decrypt data error", e);
        }
    }

}

