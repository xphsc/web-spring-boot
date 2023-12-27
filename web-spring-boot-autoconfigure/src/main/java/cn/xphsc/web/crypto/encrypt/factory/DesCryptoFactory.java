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
package cn.xphsc.web.crypto.encrypt.factory;


import cn.xphsc.web.utils.Base64Utils;
import cn.xphsc.web.utils.ByteUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class DesCryptoFactory implements CryptoFactory {
    private final Log log = LogFactory.getLog(DesCryptoFactory.class);
    /**
     * 加密
     * @param key     密钥
     * @param content 需要加密的内容
     * @return 加密结果
     */
    @Override
    public byte[] encrypt(String key, byte[] content) {
        try {
            return encryptCipher(key).doFinal(content);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            log.error("[DES 加密失败", e);
        } catch (InvalidKeyException e) {
            log.error("[DES 加密失败，请检查 key 的长度是否符合8的倍数]", e);
        }
        return null;
    }

    @Override
    public String encrypt(String key, String content) throws RuntimeException {
            return Base64Utils.base64Encode(encrypt(key,ByteUtils.toBytes(content)));


    }

    /**
     * 解密
     * @param key  密钥
     * @param content 需要解密的内容
     */
    @Override
    public byte[] decrypt(String key, byte[] content) throws RuntimeException {
        try {
            return decryptCipher(key).doFinal(content);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            log.error("[DES 解密失败", e);
        } catch (InvalidKeyException e) {
            log.error("[DES 解密失败，请检查 key 的长度是否符合8的倍数]", e);
        }
        return null;
    }

    @Override
    public String decrypt(String key, String content) throws RuntimeException {
            byte[] bytes= new byte[0];
            try {
                bytes = decryptCipher(key).doFinal(Base64Utils.base64Decode(content));
            } catch (Exception e) {
                e.printStackTrace();
            }
                return ByteUtils.toString(bytes);

    }

    private volatile Cipher encryptCipher = null;
    private volatile Cipher decryptCipher = null;
    private static final Charset DEFAULT_KEY_CHARSET = Charset.forName("UTF-8");
    private static final String DES = "DES";

    private Cipher encryptCipher(String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        if (encryptCipher == null) {
            synchronized (DesCryptoFactory.class) {
                if (encryptCipher == null) {
                    SecureRandom random = new SecureRandom();
                    DESKeySpec desKey = new DESKeySpec(key.getBytes(DEFAULT_KEY_CHARSET));
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
                    Cipher cipher = Cipher.getInstance(DES);
                    cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generateSecret(desKey), random);
                    this.encryptCipher = cipher;
                }
            }
        }
        return encryptCipher;
    }

    private Cipher decryptCipher(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        if (decryptCipher == null) {
            synchronized (DesCryptoFactory.class) {
                if (decryptCipher == null) {
                    SecureRandom random = new SecureRandom();
                    DESKeySpec desKey = new DESKeySpec(key.getBytes(DEFAULT_KEY_CHARSET));
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
                    Cipher cipher = Cipher.getInstance(DES);
                    cipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(desKey), random);
                    this.decryptCipher = cipher;
                }
            }
        }
        return decryptCipher;
    }


}
