/*
 * Copyright (c) 2021 huipei.x
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
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class ByteUtils {
    public static final byte[] EMPTY = new byte[0];

    public ByteUtils() {
    }

    public static byte[] toBytes(String input) {
        return input == null ? EMPTY : input.getBytes(Charset.forName("UTF-8"));
    }

    public static byte[] toBytes(Object obj) {
        return obj == null ? EMPTY : toBytes(String.valueOf(obj));
    }

    public static String toString(byte[] bytes) {
        return bytes == null ? "" : new String(bytes, Charset.forName("UTF-8"));
    }
    /**
     * 获取byte[]
     * @param s       s
     * @param charset charset
     * @return byte[]
     */
    public static byte[] bytes(String s, Charset charset) {
        if (charset != null) {
            return s.getBytes(charset);
        }
        return s.getBytes(Charsets.of(Systems.FILE_ENCODING));
    }

    /**
     * 获取byte[]
     * @param s       s
     * @param charset charset
     * @return byte[]
     */
    public static byte[] bytes(String s, String charset) {
        if (charset != null) {
            return s.getBytes(Charsets.of(charset));
        }
        return s.getBytes(Charsets.of(Systems.FILE_ENCODING));
    }
    public static boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;
    }

    public static boolean isNotEmpty(byte[] data) {
        return !isEmpty(data);
    }
}
