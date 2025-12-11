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
package cn.xphsc.web.common.lang.crypto.symmetric;


import cn.xphsc.web.common.lang.crypto.Keys;
import cn.xphsc.web.common.lang.crypto.enums.CipherAlgorithm;
import cn.xphsc.web.common.lang.crypto.enums.PaddingMode;
import cn.xphsc.web.common.lang.crypto.enums.WorkingMode;
import cn.xphsc.web.utils.ByteUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.AlgorithmParameterSpec;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 非对称加密构造器
 * @since 2.0.2
 */
public class SymmetricBuilder {

    /**
     * 加密算法
     */
    protected CipherAlgorithm algorithm;

    /**
     * 工作模式
     */
    protected WorkingMode workingMode;

    /**
     * 填充模式
     */
    protected PaddingMode paddingMode;

    /**
     * 密钥
     */
    protected SecretKey secretKey;

    /**
     * 参数规格
     */
    protected AlgorithmParameterSpec paramSpec;

    /**
     * aad
     */
    private byte[] aad;

    public SymmetricBuilder() {
        this.workingMode = WorkingMode.ECB;
        this.paddingMode = PaddingMode.PKCS5_PADDING;
    }

    /**
     * 创建构造器
     *
     * @return 构造器
     */
    public static SymmetricBuilder create() {
        return new SymmetricBuilder();
    }

    /**
     * 创建 AES 构造器
     *
     * @return 构造器
     */
    public static SymmetricBuilder aes() {
        return new SymmetricBuilder().algorithm(CipherAlgorithm.AES);
    }

    /**
     * 创建 DES 构造器
     *
     * @return 构造器
     */
    public static SymmetricBuilder des() {
        return new SymmetricBuilder().algorithm(CipherAlgorithm.DES);
    }

    /**
     * 创建 DES3 构造器
     *
     * @return 构造器
     */
    public static SymmetricBuilder des3() {
        return new SymmetricBuilder().algorithm(CipherAlgorithm.DES3);
    }


    /**
     * 设置加密算法
     *
     * @param algorithm algorithm
     * @return this
     */
    public SymmetricBuilder algorithm(CipherAlgorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    /**
     * 设置工作模式
     *
     * @param workingMode 工作模式
     * @return this
     */
    public SymmetricBuilder workingMode(WorkingMode workingMode) {
        this.workingMode = workingMode;
        return this;
    }

    /**
     * 设置填充模式
     *
     * @param paddingMode 填充模式
     * @return this
     */
    public SymmetricBuilder paddingMode(PaddingMode paddingMode) {
        this.paddingMode = paddingMode;
        return this;
    }

    /**
     * 设置密钥
     *
     * @param secretKey secretKey
     * @return this
     */
    public SymmetricBuilder secretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    /**
     * 设置密钥
     *
     * @param secretKey secretKey
     * @return this
     */
    public SymmetricBuilder secretKey(byte[] secretKey) {
        this.secretKey = Keys.getSecretKey(secretKey, algorithm);
        return this;
    }

    /**
     * 生成密钥
     *
     * @param secretKey secretKey
     * @return this
     */
    public SymmetricBuilder generatorSecretKey(byte[] secretKey) {
        this.secretKey = Keys.generatorKey(secretKey, algorithm);
        return this;
    }

    /**
     * 生成密钥
     *
     * @param secretKey secretKey
     * @return this
     */
    public SymmetricBuilder generatorSecretKey(String secretKey) {
        this.secretKey = Keys.generatorKey(secretKey, algorithm);
        return this;
    }

    /**
     * 生成密钥
     *
     * @param secretKey secretKey
     * @param keySize   keySize
     * @return this
     */
    public SymmetricBuilder generatorSecretKey(byte[] secretKey, int keySize) {
        this.secretKey = Keys.generatorKey(secretKey, keySize, algorithm);
        return this;
    }

    /**
     * 生成密钥
     *
     * @param secretKey secretKey
     * @param keySize   keySize
     * @return this
     */
    public SymmetricBuilder generatorSecretKey(String secretKey, int keySize) {
        this.secretKey = Keys.generatorKey(secretKey, keySize, algorithm);
        return this;
    }

    /**
     * 设置向量
     *
     * @param ivSpec ivSpec
     * @return this
     */
    public SymmetricBuilder ivSpec(IvParameterSpec ivSpec) {
        this.paramSpec = ivSpec;
        return this;
    }

    /**
     * 设置向量
     * @param iv iv
     * @return this
     */
    public SymmetricBuilder ivSpec(String iv) {
        return this.ivSpec(ByteUtils.toBytes(iv), false);
    }

    /**
     * 设置向量
     * @param iv iv
     * @return this
     */
    public SymmetricBuilder ivSpec(byte[] iv) {
        return this.ivSpec(iv, false);
    }

    /**
     * 设置向量
     * @param iv      iv
     * @param specLen specLen
     * @return this
     */
    public SymmetricBuilder ivSpec(String iv, int specLen) {
        return this.ivSpec(ByteUtils.toBytes(iv), specLen);
    }

    /**
     * 设置向量
     * @param iv      iv
     * @param specLen specLen
     * @return this
     */
    public SymmetricBuilder ivSpec(byte[] iv, int specLen) {
        this.paramSpec = Keys.getIvSpec(iv, specLen);
        return this;
    }

    /**
     * 设置向量
     * @param iv   iv
     * @param fill 是否填充长度
     * @return this
     */
    public SymmetricBuilder ivSpec(String iv, boolean fill) {
        return this.ivSpec(ByteUtils.toBytes(iv), fill);
    }

    /**
     * 设置向量
     * @param iv   iv
     * @param fill 是否填充长度
     * @return this
     */
    public SymmetricBuilder ivSpec(byte[] iv, boolean fill) {
        if (fill) {
            this.paramSpec = Keys.getIvSpec(algorithm, iv);
        } else {
            this.paramSpec = Keys.getIvSpec(iv);
        }
        return this;
    }

    /**
     * 设置 gcm规格
     *
     * @param gcmSpec gcm规格
     * @return this
     */
    public SymmetricBuilder gcmSpec(GCMParameterSpec gcmSpec) {
        this.paramSpec = gcmSpec;
        return this;
    }

    /**
     * 设置 GCM 规格
     *
     * @param gcm gcm
     * @return this
     */
    public SymmetricBuilder gcmSpec(String gcm) {
        return this.gcmSpec(ByteUtils.toBytes(gcm), false);
    }

    /**
     * 设置 GCM 规格
     *
     * @param gcm gcm
     * @return this
     */
    public SymmetricBuilder gcmSpec(byte[] gcm) {
        return this.gcmSpec(gcm, false);
    }

    /**
     * 设置 GCM 规格
     *
     * @param gcm     gcm
     * @param specLen specLen
     * @return this
     */
    public SymmetricBuilder gcmSpec(String gcm, int specLen) {
        return this.gcmSpec(ByteUtils.toBytes(gcm), specLen);
    }

    /**
     * 设置 GCM 规格
     *
     * @param gcm     gcm
     * @param specLen specLen
     * @return this
     */
    public SymmetricBuilder gcmSpec(byte[] gcm, int specLen) {
        this.paramSpec = Keys.getGcmSpec(gcm, specLen);
        return this;
    }

    /**
     * 设置 GCM 规格
     *
     * @param gcm  gcm
     * @param fill 是否填充长度
     * @return this
     */
    public SymmetricBuilder gcmSpec(String gcm, boolean fill) {
        return this.gcmSpec(ByteUtils.toBytes(gcm), fill);
    }

    /**
     * 设置 GCM 规格
     *
     * @param gcm  gcm
     * @param fill 是否填充长度
     * @return this
     */
    public SymmetricBuilder gcmSpec(byte[] gcm, boolean fill) {
        if (fill) {
            this.paramSpec = Keys.getGcmSpec(algorithm, gcm);
        } else {
            this.paramSpec = Keys.getGcmSpec(gcm);
        }
        return this;
    }

    /**
     * 设置参数规格
     *
     * @param paramSpec paramSpec
     * @return this
     */
    public SymmetricBuilder paramSpec(AlgorithmParameterSpec paramSpec) {
        this.paramSpec = paramSpec;
        return this;
    }

    /**
     * 设置 aad
     *
     * @param aad aad
     * @return this
     */
    public SymmetricBuilder aad(String aad) {
        return this.aad(ByteUtils.toBytes(aad));
    }

    /**
     * 设置 aad
     *
     * @param aad aad
     * @return this
     */
    public SymmetricBuilder aad(byte[] aad) {
        this.aad = aad;
        return this;
    }

    /**
     * 构建 ECB
     *
     * @return EcbSymmetric
     */
    public EcbSymmetric buildEcb() {
        return new EcbSymmetric(algorithm, paddingMode, secretKey);
    }

    /**
     * 构建 PARAM
     *
     * @return ParamSymmetric
     */
    public CipherSymmetric buildParam() {
        CipherSymmetric symmetric = new CipherSymmetric(algorithm, workingMode, paddingMode, secretKey, paramSpec);
        symmetric.setAad(aad);
        return symmetric;
    }

}
