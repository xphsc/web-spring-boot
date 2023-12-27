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

import cn.xphsc.web.common.lang.io.Streams;

import java.io.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: IO related tool methods.
 * @since 1.0.0
 */
public class IoUtils extends Streams {

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
    }
    /**
     * To string from stream.
     */
    public static String toString(InputStream input, String encoding) throws IOException {
        if (input == null) {
            return "";
        } else {
            return null == encoding ? toString(new InputStreamReader(input, "UTF-8")) : toString(new InputStreamReader(input, encoding));
        }
    }
    /**
     * To string from stream.
     */
    public static String toString(InputStream input) throws IOException {
        if (input == null) {
            return "";
        } else {
            return toString(new InputStreamReader(input, "UTF-8"));
        }
    }
    /**
     * To string from reader.
     */
    public static String toString(Reader reader) throws IOException {
        CharArrayWriter sw = new CharArrayWriter();
        copy((Reader)reader, (Writer)sw);
        return sw.toString();
    }

    public static String toString(byte[] input) throws IOException {
        return ByteUtils.toString(input);
    }

    public static long copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[4096];
        long count = 0L;
        int n;
        for(boolean var = false; (n = input.read(buffer)) >= 0; count += (long)n) {
            output.write(buffer, 0, n);
        }
        return count;
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        int totalBytes;
        for(totalBytes = 0; (bytesRead = input.read(buffer)) != -1; totalBytes += bytesRead) {
            output.write(buffer, 0, bytesRead);
        }

        return (long)totalBytes;
    }
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Throwable throwable = null;
        byte[] bytes;
        try {
            copy((InputStream)input, (OutputStream)output);
            bytes = output.toByteArray();
        } catch (Throwable throwable1) {
            throwable = throwable1;
            throw throwable1;
        } finally {
            if (output != null) {
                if (throwable != null) {
                    try {
                        output.close();
                    } catch (Throwable var11) {
                        throwable.addSuppressed(var11);
                    }
                } else {
                    output.close();
                }
            }

        }

        return bytes;
    }

    public static byte[] toByteArray(InputStream input, long size) throws IOException {
        if (size > 2147483647L) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
        } else {
            return toByteArray(input, (int)size);
        }
    }

    public static byte[] toByteArray(InputStream input, int size) throws IOException {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        } else if (size == 0) {
            return new byte[0];
        } else {
            byte[] data = new byte[size];

            int offset;
            int read;
            for(offset = 0; offset < size && (read = input.read(data, offset, size - offset)) != -1; offset += read) {
            }

            if (offset != size) {
                throw new IOException("Unexpected read size. current: " + offset + ", expected: " + size);
            } else {
                return data;
            }
        }
    }




}
