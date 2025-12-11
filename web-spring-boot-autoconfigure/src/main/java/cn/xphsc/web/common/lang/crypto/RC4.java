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


import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.Base64Utils;
import cn.xphsc.web.utils.ByteUtils;
import cn.xphsc.web.utils.StringUtils;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  RC4 实现
 * @since 2.0.2
 */
public class RC4 {

    private static final int BOX_LENGTH = 256;

    private static final int KEY_MIN_LENGTH = 5;

    private int[] box;

    public RC4(String key) {
        this(ByteUtils.toBytes(key));
    }

    public RC4(byte[] key) {
        this.initKey(key);
    }

    public String encrypt(String s) {
        return Base64Utils.encodeToString(this.encryptOrDecrypt(ByteUtils.toBytes(s)));
    }

    /**
     * 加密
     *
     * @param bs 明文
     * @return 密文
     */
    public byte[] encrypt(byte[] bs) {
        return this.encryptOrDecrypt(bs);
    }

    public String decrypt(String s) {
        try {
            return new String(this.encryptOrDecrypt(Base64Utils.base64Decode(s)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param bs 密文
     * @return 明文
     */
    public byte[] decrypt(byte[] bs) {
        return this.encryptOrDecrypt(bs);
    }

    /**
     * 加密或解密
     *
     * @param bs 要加密或解密的值
     * @return 加密或解密后的值
     */
    public byte[] encryptOrDecrypt(byte[] bs) {
        byte[] code;
        int[] box = this.box.clone();
        code = new byte[bs.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < bs.length; n++) {
            i = (i + 1) % BOX_LENGTH;
            j = (j + box[i]) % BOX_LENGTH;
            ArrayUtils.swap(box, i, j);
            int rand = box[(box[i] + box[j]) % BOX_LENGTH];
            code[n] = (byte) (rand ^ bs[n]);
        }
        return code;
    }

    public void initKey(String key) {
        this.initKey(ByteUtils.toBytes(key));
    }

    /**
     * 初始化密钥
     *
     * @param key 密钥
     */
    public void initKey(byte[] key) {
        int length = key.length;
        if (length < KEY_MIN_LENGTH || length >= BOX_LENGTH) {
            throw Exceptions.runtime(StringUtils.format("key length has to be between {} and {}", KEY_MIN_LENGTH, (BOX_LENGTH - 1)));
        }
        this.box = initBox(key);
    }

    private static int[] initBox(byte[] key) {
        int[] box = new int[BOX_LENGTH];
        int j = 0;
        for (int i = 0; i < BOX_LENGTH; i++) {
            box[i] = i;
        }
        for (int i = 0; i < BOX_LENGTH; i++) {
            j = (j + box[i] + (key[i % key.length]) & 0xFF) % BOX_LENGTH;
            ArrayUtils.swap(box, i, j);
        }
        return box;
    }

}