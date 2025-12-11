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
 * @description:  凯撒密码实现
 * @since 2.0.2
 */
public class Caesars {

    public static final String TABLE = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    private final int keys;

    public Caesars() {
        this(3);
    }

    public Caesars(int keys) {
        this.keys = keys;
    }

    /**
     * 加密
     *
     * @param s 明文
     * @return 密文
     */
    public String encrypt(String s) {
        char[] plain = s.toCharArray();
        for (int i = 0; i < plain.length; i++) {
            if (!Character.isLetter(plain[i])) {
                continue;
            }
            plain[i] = cipher(plain[i]);
        }
        return new String(plain);
    }

    /**
     * 解密
     *
     * @param s 密文
     * @return 明文
     */
    public String decrypt(String s) {
        char[] plain = s.toCharArray();
        for (int i = 0; i < plain.length; i++) {
            if (!Character.isLetter(plain[i])) {
                continue;
            }
            plain[i] = decCipher(plain[i]);
        }
        return new String(plain);
    }

    /**
     * 加密轮盘
     *
     * @param str str
     * @return 密文
     */
    private char cipher(char str) {
        int position = (TABLE.indexOf(str) + keys) % 52;
        return TABLE.charAt(position);
    }

    /**
     * 解密轮盘
     *
     * @param str str
     * @return 明文
     */
    private char decCipher(char str) {
        int position = (TABLE.indexOf(str) - keys) % 52;
        position = position < 0 ? 52 + position : position;
        return TABLE.charAt(position);
    }

}
