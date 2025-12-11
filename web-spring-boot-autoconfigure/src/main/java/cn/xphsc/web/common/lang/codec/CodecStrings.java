package cn.xphsc.web.common.lang.codec;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Codec Strings
 * @since 2.0.3
 */
class CodecStrings {

	public static byte[] toByteArray(char[] chars) {
		byte[] bytes = new byte[chars.length];
		for (int i = 0; i != bytes.length; i++) {
			bytes[i] = (byte) chars[i];
		}
		return bytes;
	}


	public static byte[] toByteArray(String string) {
		byte[] bytes = new byte[string.length()];
		for (int i = 0; i != bytes.length; i++) {
			char ch = string.charAt(i);
			bytes[i] = (byte) ch;
		}
		return bytes;
	}

	public static int toByteArray(String s, byte[] buf, int off) {
		int count = s.length();
		for (int i = 0; i < count; ++i) {
			char c = s.charAt(i);
			buf[off + i] = (byte) c;
		}
		return count;
	}

	public static String fromCharArray(char[] chars) {
		return new String(chars);
	}

	public static String fromByteArray(byte[] bytes) {
		return new String(asCharArray(bytes));
	}

	public static char[] asCharArray(byte[] bytes) {
		char[] chars = new char[bytes.length];
		for (int i = 0; i != chars.length; i++) {
			chars[i] = (char) (bytes[i] & 0xff);
		}
		return chars;
	}


}
