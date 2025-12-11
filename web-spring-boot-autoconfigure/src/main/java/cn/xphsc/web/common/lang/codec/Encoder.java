package cn.xphsc.web.common.lang.codec;

import java.io.IOException;
import java.io.OutputStream;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Encoder
 * @since 2.0.3
 */
public interface Encoder {
	int getEncodedLength(int inputLength);

	int getMaxDecodedLength(int inputLength);

	int encode(byte[] data, int off, int length, OutputStream out) throws IOException;

	int decode(byte[] data, int off, int length, OutputStream out) throws IOException;

	int decode(String data, OutputStream out) throws IOException;
}
