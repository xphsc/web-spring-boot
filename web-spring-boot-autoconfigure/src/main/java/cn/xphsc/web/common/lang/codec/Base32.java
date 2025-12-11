package cn.xphsc.web.common.lang.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Base32
 * @since 2.0.3
 */
public class Base32 {
	private static final Encoder encoder = new Base32Encoder();

	public static String encodeToString(byte[] data) {
		return encodeToString(data, 0, data.length);
	}

	public static String encodeToString(byte[] data, int off, int length) {
		byte[] encoded = encode(data, off, length);
		return CodecStrings.fromByteArray(encoded);
	}

	/**
	 * encode the input data producing a base 32 encoded byte array.
	 *
	 * @return a byte array containing the base 32 encoded data.
	 */
	public static byte[] encode(byte[] data) {
		return encode(data, 0, data.length);
	}

	/**
	 * encode the input data producing a base 32 encoded byte array.
	 *
	 * @return a byte array containing the base 32 encoded data.
	 */
	public static byte[] encode(byte[] data, int off, int length) {
		int len = encoder.getEncodedLength(length);
		ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);

		try {
			encoder.encode(data, off, length, bOut);
		} catch (Exception e) {
			throw new CodecException("exception encoding base32 string: " + e.getMessage(), e);
		}

		return bOut.toByteArray();
	}

	/**
	 * Encode the byte data to base 32 writing it to the given output stream.
	 *
	 * @return the number of bytes produced.
	 */
	public static int encode(byte[] data, OutputStream out)
		throws IOException {
		return encoder.encode(data, 0, data.length, out);
	}

	/**
	 * Encode the byte data to base 32 writing it to the given output stream.
	 *
	 * @return the number of bytes produced.
	 */
	public static int encode(byte[] data, int off, int length, OutputStream out)
		throws IOException {
		return encoder.encode(data, off, length, out);
	}

	/**
	 * decode the base 32 encoded input data. It is assumed the input data is valid.
	 *
	 * @return a byte array representing the decoded data.
	 */
	public static byte[] decode(byte[] data) {
		int len = data.length / 8 * 5;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);

		try {
			encoder.decode(data, 0, data.length, bOut);
		} catch (Exception e) {
			throw new CodecException("unable to decode base32 data: " + e.getMessage(), e);
		}

		return bOut.toByteArray();
	}

	/**
	 * decode the base 32 encoded String data - whitespace will be ignored.
	 *
	 * @return a byte array representing the decoded data.
	 */
	public static byte[] decode(String data) {
		int len = data.length() / 8 * 5;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);

		try {
			encoder.decode(data, bOut);
		} catch (Exception e) {
			throw new CodecException("unable to decode base32 string: " + e.getMessage(), e);
		}

		return bOut.toByteArray();
	}

	/**
	 * decode the base 32 encoded String data writing it to the given output stream,
	 * whitespace characters will be ignored.
	 *
	 * @return the number of bytes produced.
	 */
	public static int decode(String data, OutputStream out)
		throws IOException {
		return encoder.decode(data, out);
	}

	/**
	 * Decode to an output stream;
	 *
	 * @param base32Data The source data.
	 * @param start      Start position.
	 * @param length     the length.
	 * @param out        The output stream to write to.
	 */
	public static int decode(byte[] base32Data, int start, int length, OutputStream out) {
		try {
			return encoder.decode(base32Data, start, length, out);
		} catch (Exception e) {
			throw new CodecException("unable to decode base32 data: " + e.getMessage(), e);
		}
	}
}
