/*
 * Copyright (c) 2025 huipei.x
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
package cn.xphsc.web.common.lang.io;

import java.io.*;
import java.nio.charset.Charset;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: IOFiles
 * @since 2.0.4
 */
public class IOFiles {
    public static void writeBytes(File file, byte[] bytes) throws IOException {
        mkdirParent(file);
        try (BufferedOutputStream out = IOFiles.toOutputStream(file);) {
            out.write(bytes);
            out.flush();
        }
    }
    public static void writeString(File file, Charset charset, String content) throws IOException {
        mkdirParent(file);
        try (BufferedOutputStream out = IOFiles.toOutputStream(file);) {
            out.write(content.getBytes(charset));
            out.flush();
        }
    }
    public static BufferedOutputStream toOutputStream(File file) throws IOException {
        return IOFiles.toBuffered(new FileOutputStream(file));
    }

    public static BufferedOutputStream toBuffered( OutputStream out) {
        return (out instanceof BufferedOutputStream) ? (BufferedOutputStream) out : new BufferedOutputStream(out);
    }
    public static void mkdirParent(File file) {
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

}
