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
package cn.xphsc.web.common.lang.crypto;

import cn.xphsc.web.common.lang.crypto.enums.WorkingMode;
import cn.xphsc.web.common.lang.crypto.symmetric.SymmetricBuilder;
import javax.crypto.SecretKey;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: AES 工具类
 * @since 2.0.2
 */
public class AES {

    private AES() {
    }

    // -------------------- ENC --------------------

    public static String encrypt(String s, String key) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.ECB)
                .generatorSecretKey(key)
                .buildEcb()
                .encryptAsString(s);
    }

    public static String encrypt(String s, SecretKey key) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.ECB)
                .secretKey(key)
                .buildEcb()
                .encryptAsString(s);
    }

    public static byte[] encrypt(byte[] s, byte[] key) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.ECB)
                .secretKey(key)
                .buildEcb()
                .encrypt(s);
    }

    public static byte[] encrypt(byte[] s, SecretKey key) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.ECB)
                .secretKey(key)
                .buildEcb()
                .encrypt(s);
    }

    public static String encrypt(String s, String key, String iv) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.CBC)
                .generatorSecretKey(key)
                .ivSpec(iv)
                .buildParam()
                .encryptAsString(s);
    }

    public static String encrypt(String s, SecretKey key, String iv) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.CBC)
                .secretKey(key)
                .ivSpec(iv)
                .buildParam()
                .encryptAsString(s);
    }

    public static byte[] encrypt(byte[] s, byte[] key, byte[] iv) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.CBC)
                .secretKey(key)
                .ivSpec(iv)
                .buildParam()
                .encrypt(s);
    }

    public static byte[] encrypt(byte[] s, SecretKey key, byte[] iv) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.CBC)
                .secretKey(key)
                .ivSpec(iv)
                .buildParam()
                .encrypt(s);
    }

    public static String encrypt(String s, String key, String gcm, String aad) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.GCM)
                .generatorSecretKey(key)
                .gcmSpec(gcm)
                .aad(aad)
                .buildParam()
                .encryptAsString(s);
    }

    public static String encrypt(String s, SecretKey key, String gcm, String aad) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.GCM)
                .secretKey(key)
                .gcmSpec(gcm)
                .aad(aad)
                .buildParam()
                .encryptAsString(s);
    }

    public static byte[] encrypt(byte[] s, byte[] key, byte[] gcm, byte[] aad) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.GCM)
                .secretKey(key)
                .gcmSpec(gcm)
                .aad(aad)
                .buildParam()
                .encrypt(s);
    }

    public static byte[] encrypt(byte[] s, SecretKey key, byte[] gcm, byte[] aad) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.GCM)
                .secretKey(key)
                .gcmSpec(gcm)
                .aad(aad)
                .buildParam()
                .encrypt(s);
    }

    // -------------------- DEC --------------------

    public static String decrypt(String s, String key) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.ECB)
                .generatorSecretKey(key)
                .buildEcb()
                .decryptAsString(s);
    }

    public static String decrypt(String s, SecretKey key) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.ECB)
                .secretKey(key)
                .buildEcb()
                .decryptAsString(s);
    }

    public static byte[] decrypt(byte[] s, byte[] key) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.ECB)
                .secretKey(key)
                .buildEcb()
                .decrypt(s);
    }

    public static byte[] decrypt(byte[] s, SecretKey key) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.ECB)
                .secretKey(key)
                .buildEcb()
                .decrypt(s);
    }

    public static String decrypt(String s, String key, String iv) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.CBC)
                .generatorSecretKey(key)
                .ivSpec(iv)
                .buildParam()
                .decryptAsString(s);
    }

    public static String decrypt(String s, SecretKey key, String iv) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.CBC)
                .secretKey(key)
                .ivSpec(iv)
                .buildParam()
                .decryptAsString(s);
    }

    public static byte[] decrypt(byte[] s, byte[] key, byte[] iv) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.CBC)
                .secretKey(key)
                .ivSpec(iv)
                .buildParam()
                .decrypt(s);
    }

    public static byte[] decrypt(byte[] s, SecretKey key, byte[] iv) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.CBC)
                .secretKey(key)
                .ivSpec(iv)
                .buildParam()
                .decrypt(s);
    }

    public static String decrypt(String s, String key, String gcm, String aad) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.GCM)
                .generatorSecretKey(key)
                .gcmSpec(gcm)
                .aad(aad)
                .buildParam()
                .decryptAsString(s);
    }

    public static String decrypt(String s, SecretKey key, String gcm, String aad) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.GCM)
                .secretKey(key)
                .gcmSpec(gcm)
                .aad(aad)
                .buildParam()
                .decryptAsString(s);
    }

    public static byte[] decrypt(byte[] s, byte[] key, byte[] gcm, byte[] aad) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.GCM)
                .secretKey(key)
                .gcmSpec(gcm)
                .aad(aad)
                .buildParam()
                .decrypt(s);
    }

    public static byte[] decrypt(byte[] s, SecretKey key, byte[] gcm, byte[] aad) {
        return SymmetricBuilder.aes()
                .workingMode(WorkingMode.GCM)
                .secretKey(key)
                .gcmSpec(gcm)
                .aad(aad)
                .buildParam()
                .decrypt(s);
    }

}
