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
package cn.xphsc.web.crypto.encrypt;



import cn.xphsc.web.crypto.encrypt.factory.*;
import cn.xphsc.web.utils.ByteUtils;
import cn.xphsc.web.utils.HexUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class CryptoUtils {

    private static class LazyCryptoHolder {
        /**
         * Aes 加密工厂
         */
        private final static CryptoFactory AES_CRYPTO_FACTORY = new AesCryptoFactory();
        private final static CryptoFactory DES_CRYPTO_FACTORY = new DesCryptoFactory();
        private final static CryptoFactory RSA_CRYPTO_FACTORY = new RsaCryptoFactory();

    }
    private static CryptoFactory getFactory(CryptoType type) {
        switch (type) {
            case AES:
                return LazyCryptoHolder.AES_CRYPTO_FACTORY;
            case DES:
                return LazyCryptoHolder.DES_CRYPTO_FACTORY;
            case RSA:
                return LazyCryptoHolder.RSA_CRYPTO_FACTORY;
            default:
                throw new NullPointerException("未检测到加密");
        }
    }

    public static InputStream encrypt(CryptoType type, String key, InputStream inputStream) throws IOException {
        final CryptoFactory factory = getFactory(type);
        final byte[] bytes = ByteUtils.toBytes(inputStream);
        return new ByteArrayInputStream(factory.encrypt(key, bytes));
    }

    public static InputStream decrypt(CryptoType type, String key, InputStream inputStream) throws IOException {
        final CryptoFactory factory = getFactory(type);
        final byte[] bytes = ByteUtils.toBytes(inputStream);
        final byte[] decrypt = factory.decrypt(key, bytes);
        return new ByteArrayInputStream(decrypt);
    }

    public static byte[] encrypt(CryptoType type, String key, byte[] content) {
        final CryptoFactory factory = getFactory(type);
        return factory.encrypt(key, content);
    }

    public static byte[] decrypt(CryptoType type, String key, byte[] content) {
        final CryptoFactory factory = getFactory(type);
        return factory.decrypt(key, content);
    }


    public static String encrypt(CryptoType type, String key, String content) {
        final CryptoFactory factory = getFactory(type);
        return factory.encrypt(key, content);
    }

    public static String decrypt(CryptoType type, String key, String content) {
        final CryptoFactory factory = getFactory(type);
        return factory.decrypt(key, content);
    }


    public static String encryptToString(CryptoType type, String key, String content) {
        return encrypt(type,key,content);
    }

    public static String encryptHexToString(CryptoType type, String key, String content, Charset charset) {
        return HexUtils.toHexString(encrypt(type, key, content.getBytes(charset)));
    }

    public static String decryptToString(CryptoType type, String key, String content) {
        return decrypt(type,key,content);
    }

    public static String decryptHexToString(CryptoType type, String key, String content, Charset charset) {
        final byte[] bytes = HexUtils.fromHexString(content);
        final byte[] decrypt = decrypt(type, key, bytes);
        return new String(decrypt, charset);
    }


    public static String decryptOf(EncodingType encodingType,CryptoType type, String key, String content,Charset charset) {
        String decryptContent = null;
        switch (encodingType) {
            case BASE64:
                decryptContent = CryptoUtils.decryptToString(type, key, content);
                break;
            case HEX:
                decryptContent = CryptoUtils.decryptHexToString(type, key, content,charset);
                break;
            default:
        }

        return decryptContent;
    }

    public static String encryptOf(EncodingType encodingType,CryptoType type, String key, String content,Charset charset) {
        String decryptContent = null;
        switch (encodingType) {
            case BASE64:
                decryptContent = CryptoUtils.encryptToString(type, key, content);
                break;
            case HEX:
                decryptContent = CryptoUtils.decryptHexToString(type, key, content,charset);
                break;
            default:
        }

        return decryptContent;
    }



}
