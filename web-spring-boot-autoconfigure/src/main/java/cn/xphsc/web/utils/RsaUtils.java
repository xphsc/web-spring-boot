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
package cn.xphsc.web.utils;




import javax.crypto.Cipher;
import java.math.BigInteger;
import cn.xphsc.web.common.lang.tuple.KeyPair;
import java.security.*;
import java.security.spec.*;
import java.util.Objects;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: RSA加、解密工具
 * 1. 公钥负责加密，私钥负责解密；
 * 2. 私钥负责签名，公钥负责验证。
 * @since 1.0.0
 */
public class RsaUtils {
	/**
	 * 数字签名，密钥算法
	 */
	public static final String RSA_ALGORITHM = "RSA";
	public static final String RSA_PADDING = "RSA/ECB/PKCS1Padding";

	/**
	 * 获取 KeyPair
	 */
	public static KeyPair genKeyPair() {
		return genKeyPair(1024);
	}

	/**
	 * 获取 KeyPair
	 */
	public static KeyPair genKeyPair(int keySize) {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
			// 密钥位数
			keyPairGen.initialize(keySize);
			// 密钥对
			return new KeyPair(keyPairGen.generateKeyPair());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成RSA私钥
	 */
	public static PrivateKey generatePrivateKey(String modulus, String exponent) {
		return generatePrivateKey(new BigInteger(modulus), new BigInteger(exponent));
	}

	/**
	 * 生成RSA私钥
	 */
	public static PrivateKey generatePrivateKey(BigInteger modulus, BigInteger exponent) {
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			return keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成RSA公钥
	 */
	public static PublicKey generatePublicKey(String modulus, String exponent) {
		return generatePublicKey(new BigInteger(modulus), new BigInteger(exponent));
	}

	/**
	 * 生成RSA公钥
	 */
	public static PublicKey generatePublicKey(BigInteger modulus, BigInteger exponent) {
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			return keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到公钥
	 */
	public static PublicKey getPublicKey(String base64PubKey) {
		Objects.requireNonNull(base64PubKey, "base64 public key is null.");
		byte[] keyBytes = Base64Utils.decodeFromString(base64PubKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			return keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到公钥字符串
	 *
	 * @param base64PubKey 密钥字符串（经过base64编码）
	 * @return PublicKey String
	 */
	public static String getPublicKeyToBase64(String base64PubKey) {
		PublicKey publicKey = getPublicKey(base64PubKey);
		return getKeyString(publicKey);
	}

	/**
	 * 得到私钥
	 * @param base64PriKey 密钥字符串（经过base64编码）
	 * @return PrivateKey
	 */
	public static PrivateKey getPrivateKey(String base64PriKey) {
		Objects.requireNonNull(base64PriKey, "base64 private key is null.");
		byte[] keyBytes = Base64Utils.decodeFromString(base64PriKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			return keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 *
	 * @param key key
	 * @return base 64 编码后的 key
	 */
	public static String getKeyString(Key key) {
		return Base64Utils.encodeToString(key.getEncoded());
	}

	/**
	 * 得到私钥 base64
	 *
	 * @param base64PriKey 密钥字符串（经过base64编码）
	 * @return PrivateKey String
	 */
	public static String getPrivateKeyToBase64(String base64PriKey) {
		PrivateKey privateKey = getPrivateKey(base64PriKey);
		return getKeyString(privateKey);
	}

	/**
	 * 共要加密
	 *
	 * @param base64PublicKey base64 的公钥
	 * @param data            待加密的内容
	 * @return 加密后的内容
	 */
	public static byte[] encrypt(String base64PublicKey, byte[] data) {
		return encrypt(getPublicKey(base64PublicKey), data);
	}

	/**
	 * 共要加密
	 *
	 * @param publicKey 公钥
	 * @param data      待加密的内容
	 * @return 加密后的内容
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data) {
		return rsa(publicKey, data, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 私钥加密，用于 qpp 内，公钥解密
	 *
	 * @param base64PrivateKey base64 的私钥
	 * @param data             待加密的内容
	 * @return 加密后的内容
	 */
	public static byte[] encryptByPrivateKey(String base64PrivateKey, byte[] data) {
		return encryptByPrivateKey(getPrivateKey(base64PrivateKey), data);
	}

	/**
	 * 私钥加密，加密成 base64 字符串，用于 qpp 内，公钥解密
	 * @param base64PrivateKey base64 的私钥
	 * @param data             待加密的内容
	 * @return 加密后的内容
	 */
	public static String encryptByPrivateKeyToBase64(String base64PrivateKey, byte[] data) {
		return Base64Utils.encodeToString(encryptByPrivateKey(base64PrivateKey, data));
	}

	/**
	 * 私钥加密，用于 qpp 内，公钥解密
	 * privateKey 私钥
	 *data 待加密的内容
	 */
	public static byte[] encryptByPrivateKey(PrivateKey privateKey, byte[] data) {
		return rsa(privateKey, data, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 公钥加密
	 * base64PublicKey base64 公钥
	 * data 待加密的内容
	 */
	public static String encryptToBase64(String base64PublicKey, String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		return Base64Utils.encodeToString(encrypt(base64PublicKey, ByteUtils.toBytes(data)));
	}

	/**
	 * 解密
	 *base64PrivateKey base64 私钥
	 * data   数据
	 */
	public static byte[] decrypt(String base64PrivateKey, byte[] data) {
		return decrypt(getPrivateKey(base64PrivateKey), data);
	}

	/**
	 * 解密
	 * base64publicKey base64公钥
	 *  data     数据
	 */
	public static byte[] decryptByPublicKey(String base64publicKey, byte[] data) {
		return decryptByPublicKey(getPublicKey(base64publicKey), data);
	}

	/**
	 * 解密
	 *privateKey privateKey
	 * data       数据
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		return rsa(privateKey, data, Cipher.DECRYPT_MODE);
	}

	/**
	 * 解密
	 * publicKey PublicKey
	 *data      数据
	 */
	public static byte[] decryptByPublicKey(PublicKey publicKey, byte[] data) {
		return rsa(publicKey, data, Cipher.DECRYPT_MODE);
	}

	/**
	 * rsa 加、解密
	 * key  key
	 * data 数据
	 * mode 模式
	 */
	private static byte[] rsa(Key key, byte[] data, int mode) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_PADDING);
			cipher.init(mode, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * base64 数据解密
	 *  base64PublicKey base64 公钥
	 *  base64Data   base64数据
	 */
	public static byte[] decryptByPublicKeyFromBase64(String base64PublicKey, byte[] base64Data) {
		return decryptByPublicKey(getPublicKey(base64PublicKey), base64Data);
	}

	/**
	 * base64 数据解密
	 * base64PrivateKey base64 私钥
	 * base64Data       base64数据
	 */
	public static String decryptFromBase64(String base64PrivateKey,  String base64Data) {
		if (StringUtils.isBlank(base64Data)) {
			return null;
		}
		return new String(decrypt(base64PrivateKey, Base64Utils.decodeFromString(base64Data)), Charsets.UTF_8);

	}

	/**
	 * base64 数据解密
	 * base64PrivateKey base64 私钥
	 *base64Data       base64数据
	 */
	public static byte[] decryptFromBase64(String base64PrivateKey, byte[] base64Data) {
		return decrypt(base64PrivateKey, Base64Utils.base64Decode(base64Data));
	}

	/**
	 * base64 数据解密
	 * base64PublicKey  base64 公钥
	 * base64Data      base64数据
	 */
	public static String decryptByPublicKeyFromBase64(String base64PublicKey,  String base64Data) {
		if (StringUtils.isBlank(base64Data)) {
			return null;
		}
		return new String(decryptByPublicKeyFromBase64(base64PublicKey, Base64Utils.decodeFromString(base64Data)), Charsets.UTF_8);
	}

}
