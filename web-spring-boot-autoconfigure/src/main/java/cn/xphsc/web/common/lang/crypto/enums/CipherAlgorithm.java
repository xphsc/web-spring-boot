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
package cn.xphsc.web.common.lang.crypto.enums;


import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.utils.StringUtils;
import javax.crypto.Cipher;
import java.security.Provider;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 加密算法
 * @since 2.0.2
 */
public enum CipherAlgorithm {

    /**
     * RSA
     */
    RSA("RSA"),

    /**
     * AES
     */
    AES("AES"),

    /**
     * DES
     */
    DES("DES"),

    /**
     * 3DES
     */
    DES3("DESEDE");




    private final String mode;

    CipherAlgorithm(String model) {
        this.mode = model;
    }

    public String getMode() {
        return mode;
    }

    public Cipher getCipher() {
        try {
            return Cipher.getInstance(mode);
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

    public Cipher getCipher(String work, String padding) {
        try {
            return Cipher.getInstance(this.getCipherMode(work, padding));
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

    public Cipher getCipher(WorkingMode work, PaddingMode padding) {
        try {
            return Cipher.getInstance(this.getCipherMode(work, padding));
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

    public Cipher getCipher(String work, String padding, String provider) {
        try {
            return Cipher.getInstance(this.getCipherMode(work, padding), provider);
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

    public Cipher getCipher(WorkingMode work, PaddingMode padding, String provider) {
        try {
            return Cipher.getInstance(this.getCipherMode(work, padding), provider);
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

    public Cipher getCipher(String work, String padding, Provider provider) {
        try {
            return Cipher.getInstance(this.getCipherMode(work, padding), provider);
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * 解密算法/工作模式/填充方式
     *
     * @param work    工作模式
     * @param padding 填充方式
     * @return Cipher
     */
    public Cipher getCipher(WorkingMode work, PaddingMode padding, Provider provider) {
        try {
            return Cipher.getInstance(this.getCipherMode(work, padding), provider);
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

    protected String getCipherMode(WorkingMode work, PaddingMode padding) {
        return this.getCipherMode(work.getMode(), padding.getMode());
    }

    /**
     * 获取算法 解密算法/工作模式/填充方式
     *
     * @param work    工作模式
     * @param padding 填充方式
     * @return mode
     */
    protected String getCipherMode(String work, String padding) {
        return mode + "/" + work + "/" + padding;
    }

    public static Cipher getCipher(String model) {
        if (StringUtils.isBlank(model)) {
            return null;
        }
        CipherAlgorithm[] values = values();
        for (CipherAlgorithm value : values) {
            if (value.getMode().equalsIgnoreCase(model.trim())) {
                return value.getCipher();
            }
        }
        return null;
    }

}
