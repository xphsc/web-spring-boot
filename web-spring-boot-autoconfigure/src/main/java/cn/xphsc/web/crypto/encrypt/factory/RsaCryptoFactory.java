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

import cn.xphsc.web.common.lang.base64.BASE64Decoder;
import cn.xphsc.web.utils.Base64Utils;
import cn.xphsc.web.utils.ByteUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: RSA 加密解密算法
 * @since 1.0.0
 */
public class RsaCryptoFactory implements CryptoFactory {
    private final Log log = LogFactory.getLog(RsaCryptoFactory.class);
    /**
     * 加密
     *
     * @param key     密钥
     * @param content 需要加密的内容
     * @return 加密结果
     * @throws RuntimeException RuntimeException
     */
    @Override
    public byte[] encrypt(String key, byte[] content) throws RuntimeException {
        try {
            return encryptCipher(key).doFinal(content);
        } catch (InvalidKeySpecException | IOException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            log.error("[RSA 加密失败]", e);
        }
        return null;
    }

    @Override
    public String encrypt(String key, String content) throws RuntimeException {
        return Base64Utils.base64Encode(encrypt(key,ByteUtils.toBytes(content)));
    }

    /**
     * 解密
     *
     * @param key     密钥
     * @param content 需要解密的内容
     * @return 解密结果
     * @throws RuntimeException RuntimeException
     */
    @Override
    public byte[] decrypt(String key, byte[] content) throws RuntimeException {
        try {
            return decryptCipher(key).doFinal(content);
        } catch (InvalidKeySpecException | IOException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            log.error("[RSA 解密失败]", e);
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
            log.error("[RSA 解密失败]", e);
        }
        return ByteUtils.toString(bytes);
    }


    private volatile Cipher encryptCipher = null;
    private volatile Cipher decryptCipher = null;

    private static final String RSA = "RSA";

    private PublicKey getPublicKey(String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    private PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    private Cipher encryptCipher(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        if (encryptCipher == null) {
            synchronized (RsaCryptoFactory.class) {
                if (encryptCipher == null) {
                    Cipher cipher = Cipher.getInstance(RSA);
                    cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(key));
                    this.encryptCipher = cipher;
                }
            }
        }
        return encryptCipher;
    }

    private Cipher decryptCipher(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        if (decryptCipher == null) {
            synchronized (RsaCryptoFactory.class) {
                if (decryptCipher == null) {
                    Cipher cipher = Cipher.getInstance(RSA);
                    cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(key));
                    this.decryptCipher = cipher;
                }
            }
        }
        return decryptCipher;
    }


}
