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
package cn.xphsc.web.boot.envencrypt;


import cn.xphsc.web.crypto.encrypt.factory.AesCryptoFactory;
import cn.xphsc.web.crypto.encrypt.factory.CryptoFactory;
import cn.xphsc.web.crypto.encrypt.factory.DesCryptoFactory;
import cn.xphsc.web.crypto.encrypt.factory.RsaCryptoFactory;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: configuration environment encryption tools
 * @since 1.1.0
 */
public class EncryptorUtils {

    private static class LazyCryptoHolder {

        private final static CryptoFactory AES_CRYPTO_FACTORY = new AesCryptoFactory();
        private final static CryptoFactory DES_CRYPTO_FACTORY = new DesCryptoFactory();
        private final static CryptoFactory RSA_CRYPTO_FACTORY = new RsaCryptoFactory();

    }
    private static CryptoFactory getFactory(EncryptType type) {
        switch (type) {
            case AES:
                return EncryptorUtils.LazyCryptoHolder.AES_CRYPTO_FACTORY;
            case DES:
                return EncryptorUtils.LazyCryptoHolder.DES_CRYPTO_FACTORY;
            case RSA:
                return EncryptorUtils.LazyCryptoHolder.RSA_CRYPTO_FACTORY;
            default:
                throw new NullPointerException("未检测到加密");
        }
    }

    public static String encrypt(EncryptType type, String key, String content) {
        final CryptoFactory factory = getFactory(type);
        return factory.encrypt(key, content);
    }

    public static String decrypt(EncryptType type, String key, String content) {
        final CryptoFactory factory = getFactory(type);
        return factory.decrypt(key, content);
    }

}
