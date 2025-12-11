package cn.xphsc.web.common.lang.codec;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Codec
 * @since 2.0.3
 */
public class Codec {

	public static final byte PADDING = (byte) '=';
	private static final byte[] BASE64_ENC_TABLE = CodecStrings.toByteArray("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
	private static final byte[] BASE64_DEC_TABLE = toDecodingTable(BASE64_ENC_TABLE);
	private static final byte[] BASE32_ENC_TABLE = CodecStrings.toByteArray("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567");
	private static final byte[] BASE32_DEC_TABLE = toDecodingTable(BASE32_ENC_TABLE);

	private static final byte[] BASE32_CROCKFORD_ENC_TABLE = CodecStrings.toByteArray("0123456789ABCDEFGHJKMNPQRSTVWXYZ");
	private static final byte[] BASE32_CROCKFORD_DEC_TABLE = toDecodingTable(BASE32_CROCKFORD_ENC_TABLE);

	private static final byte[] BASE16_ENC_TABLE = CodecStrings.toByteArray("0123456789ABCDEF");
	private static final byte[] BASE16_DEC_TABLE = toDecodingTable(BASE16_ENC_TABLE);


	public static byte[] encodeBase64(byte[] data) {
		return innerEncode(data, BASE64_ENC_TABLE, 6);
	}

	public static char[] encodeBase64AsCharArray(byte[] data) {
		return innerEncodeAsCharArray(data, BASE64_ENC_TABLE, 6);
	}

	public static String encodeBase64AsString(byte[] data) {
		return new String(innerEncode(data, BASE64_ENC_TABLE, 6), StandardCharsets.ISO_8859_1);
	}

	public static byte[] decodeBase64(byte[] data) {
		return innerDecode(data, BASE64_DEC_TABLE, 6, PADDING);
	}

	public static byte[] decodeBase64(char[] data) {
		return innerDecode(data, BASE64_DEC_TABLE, 6, PADDING);
	}

	public static byte[] decodeBase64(String data) {
		return innerDecode(data.getBytes(StandardCharsets.ISO_8859_1), BASE64_DEC_TABLE, 6, PADDING);
	}

	public static byte[] decodeBase64(byte[] data, byte padding) {
		return innerDecode(data, BASE64_DEC_TABLE, 6, padding);
	}

	public static byte[] decodeBase64(char[] data, byte padding) {
		return innerDecode(data, BASE64_DEC_TABLE, 6, padding);
	}

	public static byte[] decodeBase64(String data, byte padding) {
		return innerDecode(data.getBytes(StandardCharsets.ISO_8859_1), BASE64_DEC_TABLE, 6, padding);
	}


	public static byte[] encodeBase32(byte[] data) {
		return innerEncode(data, BASE32_ENC_TABLE, 5);
	}

	public static char[] encodeBase32AsCharArray(byte[] data) {
		return innerEncodeAsCharArray(data, BASE32_ENC_TABLE, 5);
	}

	public static String encodeBase32AsString(byte[] data) {
		return new String(innerEncode(data, BASE32_ENC_TABLE, 5), StandardCharsets.ISO_8859_1);
	}

	public static byte[] decodeBase32(byte[] data) {
		return innerDecode(data, BASE32_DEC_TABLE, 5, PADDING);
	}

	public static byte[] decodeBase32(char[] data) {
		return innerDecode(data, BASE32_DEC_TABLE, 5, PADDING);
	}

	public static byte[] decodeBase32(String data) {
		return innerDecode(data.getBytes(StandardCharsets.ISO_8859_1), BASE32_DEC_TABLE, 5, PADDING);
	}

	public static byte[] decodeBase32(byte[] data, byte padding) {
		return innerDecode(data, BASE32_DEC_TABLE, 5, padding);
	}

	public static byte[] decodeBase32(char[] data, byte padding) {
		return innerDecode(data, BASE32_DEC_TABLE, 5, padding);
	}

	public static byte[] decodeBase32(String data, byte padding) {
		return innerDecode(data.getBytes(StandardCharsets.ISO_8859_1), BASE32_DEC_TABLE, 5, padding);
	}


	public static byte[] encodeCrockfordBase32(byte[] data) {
		return encode(data, BASE32_CROCKFORD_ENC_TABLE, 5);
	}

	public static char[] encodeCrockfordBase32AsCharArray(byte[] data) {
		return innerEncodeAsCharArray(data, BASE32_CROCKFORD_ENC_TABLE, 5);
	}

	public static String encodeCrockfordBase32AsString(byte[] data) {
		return new String(innerEncode(data, BASE32_CROCKFORD_ENC_TABLE, 5), StandardCharsets.ISO_8859_1);
	}

	public static byte[] decodeCrockfordBase32(byte[] data) {
		return innerDecode(data, BASE32_CROCKFORD_DEC_TABLE, 5, PADDING);
	}

	public static byte[] decodeCrockfordBase32(char[] data) {
		return innerDecode(data, BASE32_CROCKFORD_DEC_TABLE, 5, PADDING);
	}

	public static byte[] decodeCrockfordBase32(String data) {
		return innerDecode(data.getBytes(StandardCharsets.ISO_8859_1), BASE32_CROCKFORD_DEC_TABLE, 5, PADDING);
	}

	public static byte[] decodeCrockfordBase32(byte[] data, byte padding) {
		return innerDecode(data, BASE32_CROCKFORD_DEC_TABLE, 5, padding);
	}

	public static byte[] decodeCrockfordBase32(char[] data, byte padding) {
		return innerDecode(data, BASE32_CROCKFORD_DEC_TABLE, 5, padding);
	}

	public static byte[] decodeCrockfordBase32(String data, byte padding) {
		return innerDecode(data.getBytes(StandardCharsets.ISO_8859_1), BASE32_CROCKFORD_DEC_TABLE, 5, padding);
	}


	public static byte[] encodeBase16(byte[] data) {
		return innerEncode(data, BASE16_ENC_TABLE, 4);
	}

	public static char[] encodeBase16AsCharArray(byte[] data) {
		return innerEncodeAsCharArray(data, BASE16_ENC_TABLE, 4);
	}

	public static String encodeBase16AsString(byte[] data) {
		return new String(innerEncode(data, BASE16_ENC_TABLE, 4), StandardCharsets.ISO_8859_1);
	}

	public static byte[] decodeBase16(byte[] data) {
		return innerDecode(data, BASE16_DEC_TABLE, 4, (byte) 0);
	}

	public static byte[] decodeBase16(char[] data) {
		return innerDecode(data, BASE16_DEC_TABLE, 4, (byte) 0);
	}

	public static byte[] decodeBase16(String data) {
		return innerDecode(data.getBytes(StandardCharsets.ISO_8859_1), BASE16_DEC_TABLE, 4, (byte) 0);
	}


	public static byte[] decode(byte[] data, byte[] encodingTable, int bits) {
		return decode(data, encodingTable, bits, PADDING);
	}

	public static byte[] decode(byte[] data, byte[] encodingTable, int bits, byte padding) {
		// 检查bits值是否在有效范围内
		if (bits < 1 || bits > 8) {
			throw new IllegalArgumentException("bits must be in [1,8]");
		}
		// 检查编码字符集长度是否符合预期
		if ((1 << bits) != encodingTable.length) {
			throw new IllegalArgumentException("encodingTable's length must be 2^bits");
		}
		return innerDecode(data, toDecodingTable(encodingTable), bits, padding);
	}


	public static byte[] decode(char[] data, byte[] encodingTable, int bits) {
		return decode(data, encodingTable, bits, PADDING);
	}

	public static byte[] decode(char[] data, byte[] encodingTable, int bits, byte padding) {
		// 检查bits值是否在有效范围内
		if (bits < 1 || bits > 8) {
			throw new IllegalArgumentException("bits must be in [1,8]");
		}
		// 检查编码字符集长度是否符合预期
		if ((1 << bits) != encodingTable.length) {
			throw new IllegalArgumentException("encodingTable's length must be 2^bits");
		}
		return innerDecode(data, toDecodingTable(encodingTable), bits, padding);
	}

	public static byte[] encode(byte[] data, byte[] encodingTable, int bits) {
		// 检查bits值是否在有效范围内
		if (bits < 1 || bits > 8) {
			throw new IllegalArgumentException("bits must be in [1,8]");
		}
		// 检查编码字符集长度是否符合预期
		if ((1 << bits) != encodingTable.length) {
			throw new IllegalArgumentException("encodingTable's length must be 2^bits");
		}
		return innerEncode(data, encodingTable, bits);
	}

	public static char[] encodeAsCharArray(byte[] data, byte[] encodingTable, int bits) {
		// 检查bits值是否在有效范围内
		if (bits < 1 || bits > 8) {
			throw new IllegalArgumentException("bits must be in [1,8]");
		}
		// 检查编码字符集长度是否符合预期
		if ((1 << bits) != encodingTable.length) {
			throw new IllegalArgumentException("encodingTable's length must be 2^bits");
		}
		return innerEncodeAsCharArray(data, encodingTable, bits);
	}

	private static byte[] innerDecode(byte[] data, byte[] decodingTable, int bits, byte padding) {
		// 如果数据为空，直接返回空的字符数组
		if (data.length == 0) {
			return new byte[0];
		}
		int end = data.length - 1;
		if (padding != 0) {
			for (int i = end; i >= 0; i--) {
				if ((byte) data[i] != padding) {
					end = i;
					break;
				}
			}
			if (end == 0) {
				return new byte[0];
			}
		}
		// 根据数据长度和编码位数计算结果数组的长度
		byte[] rs = new byte[data.length * bits / 8];
		int decIdx = 0;
		int curBits = 0;
		byte cur = 0;
		for (int i = 0; i < data.length; i++) {
			byte datum = data[i];
			int b = decodingTable[datum];
			if (b == -1) {
				throw new IllegalArgumentException("Invalid data: " + i + " - " + datum);
			}
			b &= 0xFF;

			int extraBits = 8 - curBits;
			if (bits < extraBits) {
				cur = (byte) (cur << bits | b);
				curBits += bits;
			} else {
				cur = (byte) (cur << extraBits | (b >>> (bits - extraBits)));
				rs[decIdx++] = cur;
				curBits = bits - extraBits;
				cur = (byte) (b & ((1 << curBits) - 1));
			}
		}
		if (decIdx < rs.length - 1 && curBits > 0) {
			rs[decIdx++] = cur;
		}
		return rs;
	}

	private static byte[] innerDecode(char[] data, byte[] decodingTable, int bits, byte padding) {
		// 如果数据为空，直接返回空的字符数组
		if (data.length == 0) {
			return new byte[0];
		}
		int end = data.length - 1;
		if (padding != 0) {
			for (int i = end; i >= 0; i--) {
				if ((byte) data[i] != padding) {
					end = i;
					break;
				}
			}
			if (end == 0) {
				return new byte[0];
			}
		}

		// 根据数据长度和编码位数计算结果数组的长度
		byte[] rs = new byte[(end + 1) * bits / 8];
		int decIdx = 0;
		int curBits = 0;
		byte cur = 0;
		for (int i = 0; i <= end; i++) {
			char datum = data[i];
			int b = decodingTable[datum];
			if (b == -1) {
				throw new IllegalArgumentException("Invalid data: " + i + " - " + datum);
			}
			b &= 0xFF;

			int extraBits = 8 - curBits;
			if (bits < extraBits) {
				cur = (byte) (cur << bits | b);
				curBits += bits;
			} else {
				cur = (byte) (cur << extraBits | (b >>> (bits - extraBits)));
				rs[decIdx++] = cur;
				curBits = bits - extraBits;
				cur = (byte) (b & ((1 << curBits) - 1));
			}
		}
		if (decIdx < rs.length - 1 && curBits > 0) {
			rs[decIdx++] = cur;
		}
		return rs;
	}

	private static byte[] innerEncode(byte[] data, byte[] encodingTable, int bits) {
		// 如果数据为空，直接返回空的字符数组
		if (data.length == 0) {
			return new byte[0];
		}
		// 根据数据长度和编码位数计算结果数组的长度
		byte[] rs = new byte[data.length * 8 % bits > 0 ? data.length * 8 / bits + 1 : data.length * 8 / bits];

		// 初始化编码索引和剩余位数变量
		int encIdx = 0;
		int remainBits = 0;
		int remain = 0;
		// 遍历每个字节数据
		for (int datum : data) {
			datum &= 0xFF;
			int datumBits = 8;
			// 如果有剩余位数，处理跨字节的编码
			if (remainBits > 0) {
				int extraBits = bits - remainBits;
				int index = (remain << extraBits) | (datum >>> (datumBits - extraBits));
				rs[encIdx++] = encodingTable[index];
				datumBits -= extraBits;
				datum &= (byte) ((1 << datumBits) - 1);
			}
			// 处理当前字节的完整编码单元
			while (datumBits >= bits) {
				int index = (datum >>> (datumBits - bits));
				rs[encIdx++] = encodingTable[index];
				datumBits -= bits;
				datum &= (byte) ((1 << datumBits) - 1);
			}
			// 更新剩余位数和剩余数据
			remainBits = datumBits;
			remain = remainBits == 0 ? 0 : datum & ((1 << remainBits) - 1);
		}
		// 如果最后有剩余位数，处理最后一个编码单元
		if (remainBits > 0) {
			int extraBits = bits - remainBits;
			int index = (remain << extraBits);
			rs[encIdx++] = encodingTable[index];
		}
		return rs;
	}


	private static char[] innerEncodeAsCharArray(byte[] data, byte[] encodingTable, int bits) {
		// 如果数据为空，直接返回空的字符数组
		if (data.length == 0) {
			return new char[0];
		}

		// 根据数据长度和编码位数计算结果数组的长度
		char[] rs = new char[data.length * 8 % bits > 0 ? data.length * 8 / bits + 1 : data.length * 8 / bits];

		// 初始化编码索引和剩余位数变量
		int encIdx = 0;
		int remainBits = 0;
		int remain = 0;
		// 遍历每个字节数据
		for (int datum : data) {
			datum &= 0xFF;
			int datumBits = 8;
			// 如果有剩余位数，处理跨字节的编码
			if (remainBits > 0) {
				int extraBits = bits - remainBits;
				int index = (remain << extraBits) | (datum >>> (datumBits - extraBits));
				rs[encIdx++] = (char) encodingTable[index];
				datumBits -= extraBits;
				datum &= (byte) ((1 << datumBits) - 1);
			}
			// 处理当前字节的完整编码单元
			while (datumBits >= bits) {
				int index = (datum >>> (datumBits - bits));
				rs[encIdx++] = (char) encodingTable[index];
				datumBits -= bits;
				datum &= (byte) ((1 << datumBits) - 1);
			}
			// 更新剩余位数和剩余数据
			remainBits = datumBits;
			remain = remainBits == 0 ? 0 : datum & ((1 << remainBits) - 1);
		}
		// 如果最后有剩余位数，处理最后一个编码单元
		if (remainBits > 0) {
			int extraBits = bits - remainBits;
			int index = (remain << extraBits);
			rs[encIdx++] = (char) encodingTable[index];
		}
		return rs;
	}

	private static byte[] toDecodingTable(byte[] encodingTable) {
		byte[] decodingTable = new byte[256];
		Arrays.fill(decodingTable, (byte) -1);
		for (int i = 0; i < encodingTable.length; i++) {
			decodingTable[encodingTable[i]] = (byte) i;
		}
		return decodingTable;
	}
}
