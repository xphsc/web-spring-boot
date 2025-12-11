package cn.xphsc.web.common.lang.codec;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Codec Exception
 * @since 2.0.3
 */
public class CodecException extends IllegalStateException {
	private Throwable cause;

	CodecException(String msg, Throwable cause) {
		super(msg);

		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}
}
