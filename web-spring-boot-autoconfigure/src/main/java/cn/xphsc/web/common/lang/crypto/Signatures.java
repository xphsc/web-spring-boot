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

import cn.xphsc.web.common.lang.crypto.enums.HashDigest;
import cn.xphsc.web.common.lang.crypto.enums.SecretKeySpecMode;
import cn.xphsc.web.utils.ByteUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  签名工具类
 * @since 2.0.2
 */
public class Signatures {

    private Signatures() {
    }

    /**
     * MD5签名
     *  s 明文
     */
    public static String md5(String s) {
        return hashSign(ByteUtils.toBytes(s), HashDigest.MD5.getMessageDigest());
    }

    /**
     * MD5签名
     *  bs 明文
     */
    public static String md5(byte[] bs) {
        return hashSign(bs, HashDigest.MD5.getMessageDigest());
    }

    /**
     * MD5签名
     *  s    明文
     *  salt 盐
     */
    public static String md5(String s, String salt) {
        return md5(s, salt, 1);
    }

    /**
     * MD5签名
     *  bs   明文
     *  salt 盐
     */
    public static String md5(byte[] bs, byte[] salt) {
        return md5(bs, salt, 1);
    }

    /**
     * MD5签名
     *  s     明文
     *  salt  盐
     *  times 签名次数
     */
    public static String md5(String s, String salt, int times) {
        return md5(ByteUtils.toBytes(s), ByteUtils.toBytes(salt), times);
    }

    /**
     * MD5签名
     *  bs    明文
     *  salt  盐
     *  times 签名次数
     */
    public static String md5(byte[] bs, byte[] salt, int times) {
        try {
            MessageDigest digest = HashDigest.MD5.getMessageDigest();
            digest.update(bs);
            for (int i = 0; i < times; i++) {
                digest.update(salt);
            }
            return toHex(digest.digest(bs));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * SHA1签名
     *  s 明文
     */
    public static String sha1(String s) {
        return hashSign(ByteUtils.toBytes(s), HashDigest.SHA1.getMessageDigest());
    }

    /**
     * SHA1签名
     *  bs 明文
     */
    public static String sha1(byte[] bs) {
        return hashSign(bs, HashDigest.SHA1.getMessageDigest());
    }

    /**
     * SHA224签名
     *  s 明文
     */
    public static String sha224(String s) {
        return hashSign(ByteUtils.toBytes(s), HashDigest.SHA224.getMessageDigest());
    }

    /**
     * SHA224签名
     *  bs 明文
     */
    public static String sha224(byte[] bs) {
        return hashSign(bs, HashDigest.SHA224.getMessageDigest());
    }

    /**
     * SHA256签名
     *  s 明文
     */
    public static String sha256(String s) {
        return hashSign(ByteUtils.toBytes(s), HashDigest.SHA256.getMessageDigest());
    }

    /**
     * SHA256签名
     *  bs 明文
     *  签名
     */
    public static String sha256(byte[] bs) {
        return hashSign(bs, HashDigest.SHA256.getMessageDigest());
    }

    /**
     * SHA384签名
     * @param s 明文
     */
    public static String sha384(String s) {
        return hashSign(ByteUtils.toBytes(s), HashDigest.SHA384.getMessageDigest());
    }

    /**
     * SHA384签名
     *  bs 明文
     */
    public static String sha384(byte[] bs) {
        return hashSign(bs, HashDigest.SHA384.getMessageDigest());
    }

    /**
     * SHA512签名
     *  s 明文
     */
    public static String sha512(String s) {
        return hashSign(ByteUtils.toBytes(s), HashDigest.SHA512.getMessageDigest());
    }

    /**
     * SHA512签名
     *  bs 明文
     */
    public static String sha512(byte[] bs) {
        return hashSign(bs, HashDigest.SHA512.getMessageDigest());
    }

    /**
     * hash签名
     *  s    明文
     *  type 签名类型 MD5 SHA-1 SHA-224 SHA-256 SHA-384 SHA-512
     */
    public static String sign(String s, String type) {
        return hashSign(ByteUtils.toBytes(s), HashDigest.getMessageDigest(type));
    }

    /**
     * hash签名
     *  bs   明文
     *  type 签名类型 MD5 SHA-1 SHA-224 SHA-256 SHA-384 SHA-512
     */
    public static String sign(byte[] bs, String type) {
        return hashSign(bs, HashDigest.getMessageDigest(type));
    }

    /**
     * 散列签名的方法
     *  bs     明文
     *  digest MessageDigest
     */
    private static String hashSign(byte[] bs, MessageDigest digest) {
        try {
            return toHex(digest.digest(bs));
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * hmac + md5签名
     *  s   明文
     *  key key
     */
    public static String hmacMd5(String s, String key) {
        return hmacHashSign(ByteUtils.toBytes(s), ByteUtils.toBytes(key), SecretKeySpecMode.HMAC_MD5);
    }

    /**
     * hmac + md5签名
     *  bs  明文
     *  key key
     */
    public static String hmacMd5(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_MD5);
    }

    /**
     * hmac + sha1签名
     *  s   明文
     *  key key
     */
    public static String hmacSha1(String s, String key) {
        return hmacHashSign(ByteUtils.toBytes(s), ByteUtils.toBytes(key), SecretKeySpecMode.HMAC_SHA1);
    }

    /**
     * hmac + sha1签名
     *  bs  明文
     *  key key
     */
    public static String hmacSha1(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA1);
    }

    /**
     * hmac + sha224签名
     *  s   明文
     *  key key
     */
    public static String hmacSha224(String s, String key) {
        return hmacHashSign(ByteUtils.toBytes(s), ByteUtils.toBytes(key), SecretKeySpecMode.HMAC_SHA224);
    }

    /**
     * hmac + sha224签名
     *  bs  明文
     *  key key
     */
    public static String hmacSha224(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA224);
    }

    /**
     * hmac + sha256签名
     *  s   明文
     *  key key
     */
    public static String hmacSha256(String s, String key) {
        return hmacHashSign(ByteUtils.toBytes(s),ByteUtils.toBytes(key), SecretKeySpecMode.HMAC_SHA256);
    }

    /**
     * hmac + sha256签名
     *  bs  明文
     *  key key
     */
    public static String hmacSha256(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA256);
    }

    /**
     * hmac + sha384签名
     *  s   明文
     *  key key
     *  密文
     */
    public static String hmacSha384(String s, String key) {
        return hmacHashSign(ByteUtils.toBytes(s), ByteUtils.toBytes(key), SecretKeySpecMode.HMAC_SHA384);
    }

    /**
     * hmac + sha384签名
     *  bs  明文
     *  key key
     *  密文
     */
    public static String hmacSha384(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA384);
    }

    /**
     * hmac + sha512签名
     *  s   明文
     *  key key
     *  密文
     */
    public static String hmacSha512(String s, String key) {
        return hmacHashSign(ByteUtils.toBytes(s), ByteUtils.toBytes(key), SecretKeySpecMode.HMAC_SHA512);
    }

    /**
     * hmac + sha512签名
     *  bs  明文
     *  key key
     */
    public static String hmacSha512(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA512);
    }

    /**
     * hmac + hash签名
     *  s    明文
     *  key  key
     *  mode SecretKeySpecMode
     */
    public static String hmacSign(String s, String key, SecretKeySpecMode mode) {
        return hmacHashSign(ByteUtils.toBytes(s), ByteUtils.toBytes(key), mode);
    }

    /**
     * hmac + hash签名
     *  bs   明文
     *  key  key
     * mode SecretKeySpecMode
     */
    public static String hmacSign(byte[] bs, byte[] key, SecretKeySpecMode mode) {
        return hmacHashSign(bs, key, mode);
    }

    /**
     * mac + hash签名
     *  bs   明文
     *  key  key
     *  mode SecretKeySpecMode
     */
    public static String hmacHashSign(byte[] bs, byte[] key, SecretKeySpecMode mode) {
        try {
            SecretKeySpec secretKey = mode.getSecretKeySpec(key);
            Mac mac = Mac.getInstance(mode.getMode());
            mac.init(secretKey);
            return toHex(mac.doFinal(bs));
        } catch (Exception e) {
            return null;
        }
    }

    public static String toHex(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bs) {
            sb.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
        }
        return sb.toString();
    }

}
