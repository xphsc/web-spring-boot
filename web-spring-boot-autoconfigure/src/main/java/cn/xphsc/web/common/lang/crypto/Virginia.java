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


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  维吉尼亚密码实现
 * @since 2.0.2
 */
public class Virginia {

    private Virginia() {
    }

    /**
     * 加密
     * cipherKey 密钥
     */
    public static String encrypt(String data, String cipherKey) {
        int dataLen = data.length();
        int cipherKeyLen = cipherKey.length();
        char[] cipherArray = new char[dataLen];
        for (int i = 0; i < dataLen / cipherKeyLen + 1; i++) {
            for (int t = 0; t < cipherKeyLen; t++) {
                if (t + i * cipherKeyLen < dataLen) {
                    char dataChar = data.charAt(t + i * cipherKeyLen);
                    char cipherKeyChar = cipherKey.charAt(t);
                    cipherArray[t + i * cipherKeyLen] = (char) ((dataChar + cipherKeyChar - 64) % 95 + 32);
                }
            }
        }
        return new String(cipherArray);
    }

    /**
     * 解密
     * data   密文
     * cipherKey 密钥
     */
    public static String decrypt(String data, String cipherKey) {
        int dataLen = data.length();
        int cipherKeyLen = cipherKey.length();
        char[] clearArray = new char[dataLen];
        for (int i = 0; i < dataLen; i++) {
            for (int t = 0; t < cipherKeyLen; t++) {
                if (t + i * cipherKeyLen < dataLen) {
                    char dataChar = data.charAt(t + i * cipherKeyLen);
                    char cipherKeyChar = cipherKey.charAt(t);
                    if (dataChar - cipherKeyChar >= 0) {
                        clearArray[t + i * cipherKeyLen] = (char) ((dataChar - cipherKeyChar) % 95 + 32);
                    } else {
                        clearArray[t + i * cipherKeyLen] = (char) ((dataChar - cipherKeyChar + 95) % 95 + 32);
                    }
                }
            }
        }
        return new String(clearArray);
    }

}
