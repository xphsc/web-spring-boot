package cn.xphsc.web.common.lang.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Base16
 * @since 2.0.3
 */
public class Base16 {
	private static final Base16Encoder encoder = new Base16Encoder();

	public static String encodeToString(byte[] data) {
		return encodeToString(data, 0, data.length);
	}

	public static String encodeToString(byte[] data, int off, int length) {
		byte[] encoded = encode(data, off, length);
		return CodecStrings.fromByteArray(encoded);
	}

	/**
	 * encode the input data producing a Base16 encoded byte array.
	 *
	 * @return a byte array containing the Base16 encoded data.
	 */
	public static byte[] encode(byte[] data) {
		return encode(data, 0, data.length);
	}

	/**
	 * encode the input data producing a Base16 encoded byte array.
	 *
	 * @return a byte array containing the Base16 encoded data.
	 */
	public static byte[] encode(byte[] data, int off, int length) {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		try {
			encoder.encode(data, off, length, bOut);
		} catch (Exception e) {
			throw new CodecException("exception encoding Base16 string: " + e.getMessage(), e);
		}

		return bOut.toByteArray();
	}

	/**
	 * Base16 encode the byte data writing it to the given output stream.
	 *
	 * @return the number of bytes produced.
	 */
	public static int encode(byte[] data, OutputStream out) throws IOException {
		return encoder.encode(data, 0, data.length, out);
	}

	/**
	 * Base16 encode the byte data writing it to the given output stream.
	 *
	 * @return the number of bytes produced.
	 */
	public static int encode(byte[] data, int off, int length, OutputStream out)
		throws IOException {
		return encoder.encode(data, off, length, out);
	}

	/**
	 * decode the Base16 encoded input data. It is assumed the input data is valid.
	 *
	 * @return a byte array representing the decoded data.
	 */
	public static byte[] decode(byte[] data) {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();

		try {
			encoder.decode(data, 0, data.length, bOut);
		} catch (Exception e) {
			throw new CodecException("exception decoding Base16 data: " + e.getMessage(), e);
		}

		return bOut.toByteArray();
	}

	/**
	 * decode the Base16 encoded String data - whitespace will be ignored.
	 *
	 * @return a byte array representing the decoded data.
	 */
	public static byte[] decode(String data) {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();

		try {
			encoder.decode(data, bOut);
		} catch (Exception e) {
			throw new CodecException("exception decoding Base16 string: " + e.getMessage(), e);
		}

		return bOut.toByteArray();
	}

	/**
	 * decode the Base16 encoded String data writing it to the given output stream,
	 * whitespace characters will be ignored.
	 *
	 * @return the number of bytes produced.
	 */
	public static int decode(String data, OutputStream out)
		throws IOException {
		return encoder.decode(data, out);
	}

	/**
	 * Decode the hexadecimal-encoded string strictly i.e. any non-hexadecimal characters will be
	 * considered an error.
	 *
	 * @return a byte array representing the decoded data.
	 */
	public static byte[] decodeStrict(String str) {
		try {
			return encoder.decodeStrict(str, 0, str.length());
		} catch (Exception e) {
			throw new CodecException("exception decoding Base16 string: " + e.getMessage(), e);
		}
	}

	/**
	 * Decode the hexadecimal-encoded string strictly i.e. any non-hexadecimal characters will be
	 * considered an error.
	 *
	 * @return a byte array representing the decoded data.
	 */
	public static byte[] decodeStrict(String str, int off, int len) {
		try {
			return encoder.decodeStrict(str, off, len);
		} catch (Exception e) {
			throw new CodecException("exception decoding Base16 string: " + e.getMessage(), e);
		}
	}
}
