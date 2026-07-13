package cn.xphsc.web.common.lang.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  MemoryJavaFileObject
 * @since 2.0.4
 */
public class MemoryJavaFileObject extends SimpleJavaFileObject {

	private final CharSequence source;
	private final byte[] bytes;

	public MemoryJavaFileObject(final String classFullName, final byte[] bytes) {
		super(URI.create(classFullName.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
		this.source = null;
		this.bytes = bytes;
	}


	protected MemoryJavaFileObject(final String classFullName, final CharSequence source) {
		super(URI.create(classFullName.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.source = source;
		this.bytes = null;
	}

	protected MemoryJavaFileObject(final URI uri, final CharSequence source) {
		super(uri, Kind.SOURCE);
		this.source = source;
		this.bytes = null;
	}

	protected MemoryJavaFileObject(final URI uri, final Kind kind) {
		super(uri, kind);
		this.source = null;
		this.bytes = null;
	}


	@Override
	public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws UnsupportedOperationException {
		if (source == null) {
			throw new UnsupportedOperationException();
		}
		return source;
	}

	@Override
	public InputStream openInputStream() {
		return new ByteArrayInputStream(bytes);
	}

	public byte[] getByteCode() {
		return bytes;
	}
}
