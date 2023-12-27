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



import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Charsets {

	/**
	 * 字符集ISO-8859-1
	 */
	public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
	public static final String ISO_8859_1_NAME = ISO_8859_1.name();

	/**
	 * 字符集GBK
	 */
	public static final Charset GBK = Charset.forName("GBK");
	public static final String GBK_NAME = GBK.name();

	/**
	 * 字符集utf-8
	 */
	public static final Charset UTF_8 = StandardCharsets.UTF_8;
	public static final String UTF_8_NAME = UTF_8.name();
	public static Charset of(String charset) {
		return Charset.forName(charset);
	}
	/**
	 * 转换为Charset对象
	 */
	public static Charset charset(String charsetName) throws UnsupportedCharsetException {
		return StringUtils.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
	}

	/**
	 * 是否是支持的编码集
	 */
	public static boolean isSupported(String charset) {
		if (StringUtils.isBlank(charset)) {
			return false;
		}
		try {
			return Charset.isSupported(charset);
		} catch (Exception e) {
			return false;
		}
	}

}
