package cn.xphsc.web.common.lang.crypto;



import cn.xphsc.web.common.lang.crypto.enums.DigestAlgorithm;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Digests
 * @since 2.0.4
 */
@SuppressWarnings("ALL")
public class Digests {
	private static final int STREAM_BUFFER_LENGTH = 1024;

	public static boolean isAvailable(String messageDigestAlgorithm) {
		return getDigest(messageDigestAlgorithm, (MessageDigest) null) != null;
	}

	public static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static MessageDigest getDigest(String algorithm, MessageDigest defaultMessageDigest) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			return defaultMessageDigest;
		}
	}

	public static byte[] digest(MessageDigest messageDigest, byte[] data) {
		return messageDigest.digest(data);
	}

	public static byte[] digest(MessageDigest messageDigest, ByteBuffer data) {
		messageDigest.update(data);
		return messageDigest.digest();
	}

	public static byte[] digest(MessageDigest messageDigest, InputStream data) throws IOException {
		return updateDigest(messageDigest, data).digest();
	}


	public static byte[] digest(MessageDigest messageDigest, File data) throws IOException {
		return updateDigest(messageDigest, data).digest();
	}

	public static byte[] digest(MessageDigest messageDigest, Path data, OpenOption... options) throws IOException {
		return updateDigest(messageDigest, data, options).digest();
	}

	public static byte[] digest(MessageDigest messageDigest, RandomAccessFile data) throws IOException {
		return updateDigest(messageDigest, data).digest();
	}


	public static MessageDigest updateDigest(MessageDigest digest, File data) throws IOException {
		try (FileInputStream fis = new FileInputStream(data);
				 BufferedInputStream in = new BufferedInputStream(fis);) {
			return updateDigest(digest, in);
		}
	}

	public static MessageDigest updateDigest(MessageDigest digest, InputStream in) throws IOException {
		byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
		for (int read = in.read(buffer, 0, STREAM_BUFFER_LENGTH); read > -1; read = in.read(buffer, 0, STREAM_BUFFER_LENGTH)) {
			digest.update(buffer, 0, read);
		}
		return digest;
	}


	public static MessageDigest updateDigest(MessageDigest digest, Path path, OpenOption... options) throws IOException {
		try (
			InputStream fis = Files.newInputStream(path, options);
			BufferedInputStream in = new BufferedInputStream(fis);
		) {
			return updateDigest(digest, in);
		}
	}

	public static MessageDigest updateDigest(MessageDigest digest, RandomAccessFile data) throws IOException {
		return updateDigest(digest, data.getChannel());
	}

	private static MessageDigest updateDigest(MessageDigest digest, FileChannel data) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(STREAM_BUFFER_LENGTH);
		while (data.read(buffer) > 0) {
			buffer.flip();
			digest.update(buffer);
			buffer.clear();
		}
		return digest;
	}

	public static MessageDigest updateDigest(MessageDigest messageDigest, String data) {
		messageDigest.update(data.getBytes(StandardCharsets.UTF_8));
		return messageDigest;
	}


	public static MessageDigest getMd2Digest() {
		return getDigest(DigestAlgorithm.MD2.code());
	}

	public static MessageDigest getMd5Digest() {
		return getDigest(DigestAlgorithm.MD5.code());
	}


	public static MessageDigest getSha1Digest() {
		return getDigest(DigestAlgorithm.SHA1.code());
	}

	public static MessageDigest getSha256Digest() {
		return getDigest(DigestAlgorithm.SHA256.code());
	}

	public static MessageDigest getSha3_224Digest() {
		return getDigest(DigestAlgorithm.SHA3_224.code());
	}

	public static MessageDigest getSha3_256Digest() {
		return getDigest(DigestAlgorithm.SHA3_256.code());
	}

	public static MessageDigest getSha3_384Digest() {
		return getDigest(DigestAlgorithm.SHA3_384.code());
	}

	public static MessageDigest getSha3_512Digest() {
		return getDigest(DigestAlgorithm.SHA3_512.code());
	}

	public static MessageDigest getSha384Digest() {
		return getDigest(DigestAlgorithm.SHA384.code());
	}

	public static MessageDigest getSha512Digest() {
		return getDigest(DigestAlgorithm.SHA512.code());
	}


	public static MessageDigest getSha512_224Digest() {
		return getDigest(DigestAlgorithm.SHA512_224.code());
	}

	public static MessageDigest getSha512_256Digest() {
		return getDigest(DigestAlgorithm.SHA512_256.code());
	}


	public static byte[] md2(byte[] data) {
		return getMd2Digest().digest(data);
	}

	public static byte[] md5(byte[] data) {
		return getMd5Digest().digest(data);
	}

	public static byte[] sha1(byte[] data) {
		return getSha1Digest().digest(data);
	}

	public static byte[] sha256(byte[] data) {
		return getSha256Digest().digest(data);
	}

	public static byte[] sha3_224(byte[] data) {
		return getSha3_224Digest().digest(data);
	}

	public static byte[] sha3_256(byte[] data) {
		return getSha3_256Digest().digest(data);
	}

	public static byte[] sha3_384(byte[] data) {
		return getSha3_384Digest().digest(data);
	}

	public static byte[] sha3_512(byte[] data) {
		return getSha3_512Digest().digest(data);
	}

	public static byte[] sha384(byte[] data) {
		return getSha384Digest().digest(data);
	}

	public static byte[] sha512(byte[] data) {
		return getSha512Digest().digest(data);
	}

	public static byte[] sha512_224(byte[] data) {
		return getSha512_224Digest().digest(data);
	}

	public static byte[] sha512_256(byte[] data) {
		return getSha512_256Digest().digest(data);
	}


	public static byte[] md2(String data) {
		return md2(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] md5(String data) {
		return md5(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha1(String data) {
		return sha1(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha256(String data) {
		return sha256(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha3_224(String data) {
		return sha3_224(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha3_256(String data) {
		return sha3_256(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha3_384(String data) {
		return sha3_384(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha3_512(String data) {
		return sha3_512(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha384(String data) {
		return sha384(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha512(String data) {
		return sha512(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha512_224(String data) {
		return sha512_224(data.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] sha512_256(String data) {
		return sha512_256(data.getBytes(StandardCharsets.UTF_8));
	}


	public static byte[] md2(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getMd2Digest(), data);
	}

	public static byte[] md5(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getMd5Digest(), data);
	}

	public static byte[] sha1(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha1Digest(), data);
	}

	public static byte[] sha256(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha256Digest(), data);
	}

	public static byte[] sha3_224(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha3_224Digest(), data);
	}

	public static byte[] sha3_256(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha3_256Digest(), data);
	}

	public static byte[] sha3_384(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha3_384Digest(), data);
	}

	public static byte[] sha3_512(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha3_512Digest(), data);
	}

	public static byte[] sha384(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha384Digest(), data);
	}

	public static byte[] sha512(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha512Digest(), data);
	}

	public static byte[] sha512_224(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha512_224Digest(), data);
	}

	public static byte[] sha512_256(InputStream data) throws IOException, NoSuchAlgorithmException {
		return digest(getSha512_256Digest(), data);
	}


}
