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
import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.common.lang.constant.CryptoConstant;
import cn.xphsc.web.common.lang.crypto.enums.CipherAlgorithm;
import cn.xphsc.web.common.lang.crypto.enums.SecretKeySpecMode;
import cn.xphsc.web.common.lang.io.Files;
import cn.xphsc.web.common.lang.io.Streams;
import cn.xphsc.web.common.lang.tuple.Pair;
import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.ByteUtils;
import cn.xphsc.web.utils.StringUtils;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.*;
import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import static cn.xphsc.web.common.lang.base64.Base64Encoder.encode;
import static org.springframework.util.Base64Utils.decode;



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  key 工具类
 * @since 2.0.2
 */
public class Keys {

    private Keys() {
    }

    // -------------------- CER --------------------

    public static PublicKey getCerPublicKey(File file) {
        return getCerPublicKey(Files.openInputStreamSafe(file), true);
    }

    public static PublicKey getCerPublicKey(String file) {
        return getCerPublicKey(Files.openInputStreamSafe(file), true);
    }

    public static PublicKey getCerPublicKey(InputStream in) {
        return getCerPublicKey(in, false);
    }

    /**
     * 读取cer公钥文件
     *
     * @param in    流
     * @param close 是否关闭流
     * @return PublicKey
     */
    public static PublicKey getCerPublicKey(InputStream in, boolean close) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance(CryptoConstant.X_509);
            X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
            return cert.getPublicKey();
        } catch (Exception e) {
            return null;
        } finally {
            if (close) {
                Streams.close(in);
            }
        }
    }

    // -------------------- PFX --------------------

    public static Pair<PublicKey, PrivateKey> getPfxKeys(File file, String password) {
        return getPfxKeys(Files.openInputStreamSafe(file), password, true);
    }

    public static Pair<PublicKey, PrivateKey> getPfxKeys(String file, String password) {
        return getPfxKeys(Files.openInputStreamSafe(file), password, true);
    }

    public static Pair<PublicKey, PrivateKey> getPfxKeys(InputStream in, String password) {
        return getPfxKeys(in, password, false);
    }

    /**
     * 读取PFX私钥文件
     *
     * @param in       in
     * @param password 密码
     * @param close    是否关闭流
     * @return PublicKey, PrivateKey
     */
    public static Pair<PublicKey, PrivateKey> getPfxKeys(InputStream in, String password, boolean close) {
        try {
            char[] ps = password == null ? null : password.toCharArray();
            KeyStore ks = KeyStore.getInstance(CryptoConstant.PKCS12);
            ks.load(in, ps);
            Enumeration<String> aliases = ks.aliases();
            String keyAlias = null;
            if (aliases.hasMoreElements()) {
                keyAlias = aliases.nextElement();
            }
            Certificate cert = ks.getCertificate(keyAlias);
            return Pair.of(cert.getPublicKey(), (PrivateKey) ks.getKey(keyAlias, ps));
        } catch (Exception e) {
            return null;
        } finally {
            if (close) {
                Streams.close(in);
            }
        }
    }

    public static KeyStore getKeyStore(File file, String password) {
        return getKeyStore(Files.openInputStreamSafe(file), password);
    }

    /**
     * 加载 KeyStore
     *
     * @param in       in
     * @param password password
     * @return KeyStore
     */
    public static KeyStore getKeyStore(InputStream in, String password) {
        char[] ps = password == null ? null : password.toCharArray();
        try {
            KeyStore ks = KeyStore.getInstance(CryptoConstant.PKCS12);
            ks.load(in, ps);
            return ks;
        } catch (Exception e) {
            throw Exceptions.runtime("could not be loaded key");
        }
    }

    // -------------------- KEY --------------------

    /**
     * PublicKey -> StringKey
     *
     * @param key PublicKey
     * @return StringKey
     */
    public static String getPublicKey(PublicKey key) {
        return new String(encode(key.getEncoded()));
    }

    /**
     * PrivateKey -> StringKey
     *
     * @param key PrivateKey
     * @return StringKey
     */
    public static String getPrivateKey(PrivateKey key) {
        return new String(encode(key.getEncoded()));
    }

    /**
     * SecretKey -> StringKey
     *
     * @param key SecretKey
     * @return StringKey
     */
    public static String getSecretKey(SecretKey key) {
        return new String(encode(key.getEncoded()));
    }

    public static String getKey(File file) {
        return getKey(new InputStreamReader(Files.openInputStreamSafe(file)), true);
    }

    public static String getKey(String file) {
        return getKey(new InputStreamReader(Files.openInputStreamSafe(file)), true);
    }

    public static String getKey(InputStream in) {
        return getKey(new InputStreamReader(in), false);
    }

    public static String getKey(Reader reader) {
        return getKey(reader, false);
    }

    public static String getKey(Reader reader, boolean close) {
        StringBuilder key = new StringBuilder();
        int c = 0;
        BufferedReader r = new BufferedReader(reader);
        try {
            String s = r.readLine();
            while (s != null) {
                if (s.contains("--")) {
                    if (++c == 2) {
                        break;
                    }
                } else {
                    key.append(s);
                }
                s = r.readLine();
            }
            return key.toString()
                    .replaceAll(Constants.LF, StringUtils.EMPTY)
                    .replaceAll(Constants.CR, StringUtils.EMPTY);
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        } finally {
            if (close) {
                Streams.close(reader);
            }
        }
    }

    // -------------------- AES DES 3DES KEY --------------------

    /**
     * 获取 key 规格长度
     *
     * @param mode mode
     * @return key 长度
     */
    public static int getKeySpecLength(CipherAlgorithm mode) {
        switch (mode) {
            case AES:
                return CryptoConstant.AES_KEY_LENGTH;
            case DES:
                return CryptoConstant.DES_KEY_LENGTH;
            case DES3:
                return CryptoConstant.DES3_KEY_LENGTH;
            case RSA:
                return CryptoConstant.RSA_KEY_LENGTH;
            default:
                throw Exceptions.unsupported("unsupported get " + mode + " key spec length");
        }
    }

    /**
     * 获取 IV 规格长度
     *
     * @param mode mode
     * @return IV 长度
     */
    public static int getIvSpecLength(CipherAlgorithm mode) {
        switch (mode) {
            case AES:
                return CryptoConstant.AES_IV_LENGTH;
            case DES:
                return CryptoConstant.DES_IV_LENGTH;
            case DES3:
                return CryptoConstant.DES3_IV_LENGTH;
            default:
                throw Exceptions.unsupported("unsupported get " + mode + "iv spec length");
        }
    }

    /**
     * 获取 GCM 规格长度
     *
     * @param mode mode
     * @return GCM 长度
     */
    public static int getGcmSpecLength(CipherAlgorithm mode) {
        switch (mode) {
            case AES:
                return CryptoConstant.GCM_SPEC_LENGTH;
            default:
                throw Exceptions.unsupported("unsupported get " + mode + "gcm spec length");
        }
    }

    public static IvParameterSpec getIvSpec(byte[] iv) {
        return new IvParameterSpec(iv);
    }

    public static IvParameterSpec getIvSpec(CipherAlgorithm mode, byte[] iv) {
        return getIvSpec(iv, getIvSpecLength(mode));
    }

    /**
     * 获取向量
     *
     * @param iv        向量
     * @param ivSpecLen 向量长度
     * @return 填充后的向量
     */
    public static IvParameterSpec getIvSpec(byte[] iv, int ivSpecLen) {
        return new IvParameterSpec(ArrayUtils.resize(iv, ivSpecLen));
    }

    public static GCMParameterSpec getGcmSpec(byte[] gcm) {
        return new GCMParameterSpec(gcm.length, gcm);
    }

    public static GCMParameterSpec getGcmSpec(CipherAlgorithm mode, byte[] gcm) {
        return new GCMParameterSpec(getGcmSpecLength(mode), gcm);
    }

    /**
     * 生成 GCM 规范参数
     *
     * @param gcm        gcm
     * @param gcmSpecLen 长度
     * @return GCMParameterSpec
     */
    public static GCMParameterSpec getGcmSpec(byte[] gcm, int gcmSpecLen) {
        return new GCMParameterSpec(gcmSpecLen, gcm);
    }

    /**
     * StringKey -> SecretKey
     *
     * @param key  StringKey
     * @param mode CipherAlgorithm
     * @return SecretKey
     */
    public static SecretKey getSecretKey(byte[] key, CipherAlgorithm mode) {
        return new SecretKeySpec(decode(key), mode.getMode());
    }

    /**
     * StringKey -> SecretKey
     *
     * @param key  StringKey
     * @param mode CipherAlgorithm
     * @return SecretKey
     */
    public static SecretKey getSecretKey(String key, CipherAlgorithm mode) {
        return new SecretKeySpec(decode(ByteUtils.toBytes(key)), mode.getMode());
    }

    public static SecretKey generatorKey(String key, CipherAlgorithm mode) {
        return generatorKey(ByteUtils.toBytes(key), getKeySpecLength(mode), mode);
    }

    public static SecretKey generatorKey(byte[] key, CipherAlgorithm mode) {
        return generatorKey(key, getKeySpecLength(mode), mode);
    }

    public static SecretKey generatorKey(String key, int keySize, CipherAlgorithm mode) {
        return generatorKey(ByteUtils.toBytes(key), keySize, mode);
    }

    public static SecretKey generatorKey(byte[] key, int keySize, CipherAlgorithm mode) {
        return generatorKey(key, keySize, mode, CryptoConstant.AES_ALGORITHM, CryptoConstant.AES_PROVIDER);
    }

    /**
     * String -> SecretKey
     *
     * @param key     key
     * @param keySize key 位数
     *                AES 128 192 256  {@link CryptoConstant#AES_KEY_LENGTH}
     *                DES 8            {@link CryptoConstant#DES_KEY_LENGTH}
     *                3DES 24          {@link CryptoConstant#DES3_KEY_LENGTH}
     *                SM4  16          {@link CryptoConstant#SM4_KEY_LENGTH}
     * @param mode    CipherAlgorithm
     * @return SecretKey
     */
    public static SecretKey generatorKey(byte[] key, int keySize, CipherAlgorithm mode, String algorithm, String provider) {
        try {
            switch (mode) {
                case AES:
                    KeyGenerator keyGenerator = KeyGenerator.getInstance(mode.getMode());
                    SecureRandom random = SecureRandom.getInstance(algorithm, provider);
                    random.setSeed(key);
                    keyGenerator.init(keySize, random);
                    return SecretKeySpecMode.AES.getSecretKeySpec(keyGenerator.generateKey().getEncoded());
                case DES:
                    if (key.length != keySize) {
                        key = ArrayUtils.resize(key, keySize);
                    }
                    return SecretKeyFactory.getInstance(mode.getMode()).generateSecret(new DESKeySpec(key));
                case DES3:
                    if (key.length != keySize) {
                        key = ArrayUtils.resize(key, keySize);
                    }
                    return SecretKeyFactory.getInstance(mode.getMode()).generateSecret(new DESedeKeySpec(key));
                default:
                    throw Exceptions.unsupported("unsupported generator " + mode + " key");
            }
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

}
