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
import cn.xphsc.web.utils.Asserts;
import cn.xphsc.web.utils.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.spec.AlgorithmParameterSpec;
import static org.springframework.util.Base64Utils.decode;
import static org.springframework.util.Base64Utils.encode;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: CBC CFB OFB FTP GCM 模式非对称加密 AES DES 3DES
 * @since 2.0.2
 */
public class CipherSymmetric extends AbstractSymmetric {

    /**
     * 参数规格
     */
    private final AlgorithmParameterSpec paramSpec;

    /**
     * aad
     */
    private byte[] aad;

    public CipherSymmetric(CipherAlgorithm cipherAlgorithm, WorkingMode workingMode, SecretKey secretKey, AlgorithmParameterSpec paramSpec) {
        this(cipherAlgorithm, workingMode, PaddingMode.PKCS5_PADDING, secretKey, paramSpec);
    }

    public CipherSymmetric(CipherAlgorithm cipherAlgorithm, WorkingMode workingMode, PaddingMode paddingMode, SecretKey secretKey, AlgorithmParameterSpec paramSpec) {
        super(cipherAlgorithm, workingMode, paddingMode, secretKey);
        this.paramSpec = Asserts.notNull(paramSpec, "paramSpec is null");
    }

    public void setAad(String aad) {
            this.aad = ByteUtils.toBytes(aad);
    }

    public void setAad(byte[] aad) {
        this.aad = aad;
    }

    @Override
    public byte[] encrypt(byte[] plain) {
        try {
            Cipher cipher = super.getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            if (aad != null) {
                cipher.updateAAD(aad);
            }
            return encode(cipher.doFinal(this.zeroPadding(plain, cipher.getBlockSize())));
        } catch (Exception e) {
            throw Exceptions.runtime("encrypt data error", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] text) {
        try {
            Cipher cipher = super.getCipher();
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            if (aad != null) {
                cipher.updateAAD(aad);
            }
            return this.clearZeroPadding(cipher.doFinal(decode(text)));
        } catch (Exception e) {
            throw Exceptions.runtime("decrypt data error", e);
        }
    }

}
