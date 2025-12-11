package cn.xphsc.web.common.lang.codec;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Crock ford Base32 Encoder
 * @since 2.0.3
 */
public class CrockfordBase32Encoder extends Base32Encoder {
	private static final byte[] DEFAULT_ENCODING_TABLE =
		{
			(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
			(byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',
			(byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E',
			(byte) 'F', (byte) 'G', (byte) 'H', (byte) 'J', (byte) 'K',
			(byte) 'M', (byte) 'N', (byte) 'P', (byte) 'Q', (byte) 'R',
			(byte) 'S', (byte) 'T', (byte) 'V', (byte) 'W', (byte) 'X',
			(byte) 'Y', (byte) 'Z'
		};
	private static final byte DEFAULT_PADDING = (byte) '=';

	public CrockfordBase32Encoder() {
		super(DEFAULT_ENCODING_TABLE, DEFAULT_PADDING);
	}

	public CrockfordBase32Encoder(byte padding) {
		super(DEFAULT_ENCODING_TABLE, padding);
	}

}
