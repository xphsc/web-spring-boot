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




import cn.xphsc.web.utils.AesUtils;
import cn.xphsc.web.utils.ByteUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */

public class AesCryptoFactory implements CryptoFactory {
    private final Log log = LogFactory.getLog(AesCryptoFactory.class);
    /**
     * 加密
     * @param key  密钥
     * @param content 需要加密的内容
     */
    @Override
    public byte[] encrypt(String key, byte[] content) {
        try {
           return AesUtils.aesEncryptToBytes( ByteUtils.toString(content),key);
        } catch (Exception e) {
            log.error("[AES 加密失败]", e);
        }

        return null;
    }

    /**
     * js example
     * var key = CryptoJS.enc.Utf8.parse(key);
     * var srcs = CryptoJS.enc.Utf8.parse(content);
     * var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
     */
    @Override
    public String encrypt(String key, String content) throws RuntimeException {
        try {
            return AesUtils.aesEncrypt(content,key);
        } catch (Exception e) {
            log.error("[AES 加密失败]", e);
        }
        return null;
    }


    /**
     * 解密
     * @param key     密钥
     * @param content 需要解密的内容
     */
    @Override
    public byte[] decrypt(String key, byte[] content) {
        try {
            return ByteUtils.toBytes(AesUtils.aesDecryptByBytes(content, key));
        } catch (Exception e) {
            log.error("[AES 解密失败]", e);

        }

        return null;
    }

    /**
     *js example
     var key = CryptoJS.enc.Utf8.parse(key);
     var decrypt = CryptoJS.AES.decrypt(content, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
     return CryptoJS.enc.Utf8.stringify(decrypt).toString();
     */
    @Override
    public String decrypt(String key, String content) throws RuntimeException {
        try {
            return AesUtils.aesDecrypt(content,key);
        } catch (Exception e) {
            log.error("[AES 解密失败]", e);
        }
        return null;
    }


}
