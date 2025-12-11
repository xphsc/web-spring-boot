/*
 * Copyright (c) 2024   huipei.x
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
import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.common.lang.io.Files;
import cn.xphsc.web.common.lang.io.file.FastOutputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Zip class to provide utils for use
 * @since 2.0.2
 */
public final class ZipUtils {
    /**
     * constructor of self
     */
    private ZipUtils() {

    }

    /**
     * zip the file in the source path to a zip file
     * @param srcPath source path
     * @return {@link File} a zip file
     */
    public static File zip(String srcPath) {
        return zip(Files.newFile(srcPath));
    }

    /**
     * zip the file in the source path to a zip file
     * @param srcFile source path
     * @return {@link File} a zip file
     */
    public static File zip(File srcFile) {
        File zipFile = Files.create(srcFile.getParentFile(), Files.getFileName(srcFile) + ".zip");
        zip(zipFile, false, srcFile);
        return zipFile;
    }

    /**
     * zip the file or file in a folder<br>
     * do not include the root folder
     *srcPath source path, if is a file the path is whole,
     * if is a folder the path is the root folder's path
     * zipPath the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @return {@link File} a zip file
     */
    public static File zip(String srcPath, String zipPath) {
        return zip(srcPath, zipPath, false);
    }

    /**
     * zip the file or file in a folder<br>
     * @param srcPath source path, if is a file the path is whole,
     *if is a folder the path is the root folder's path
     * @param zipPath the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @param withSrcDir if include the root folder, if true include, else false
     * @return {@link File} a zip file
     */
    public static File zip(String srcPath, String zipPath, boolean withSrcDir) {
        File srcFile = Files.newFile(srcPath);
        File zipFile = Files.newFile(zipPath);
        zip(zipFile, withSrcDir, srcFile);
        return zipFile;
    }

    /**
     * zip the file or file in a folder<br>
     *
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @param withSrcDir if include the root folder, if true include, else false
     * @param srcFiles all the files in folder to be zipped
     * @return {@link File} a zip file
     */
    public static File zip(File zipFile, boolean withSrcDir, File... srcFiles) {
        validateFiles(zipFile, srcFiles);
        ZipOutputStream out = null;
        try {
            out = getZipOutputStream(zipFile);
            for (File srcFile : srcFiles) {
                String srcRootDir = srcFile.getCanonicalPath();
                if (srcFile.isFile() || withSrcDir) {
                    srcRootDir = srcFile.getParent();
                }
                zip(out, srcRootDir, srcFile);
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtils.close(out);
        }
        return zipFile;
    }

    /**
     * zip the stream info to a zip file, use the input charset<br>
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @param path the path or the name of stream in zip file
     * @param data data to zip
     * @param charset charset input
     * @return {@link File} a zip file
     */
    public static File zip(File zipFile, String path, String data, Charset charset) {
        return zip(zipFile, path, IoUtils.toInputStream(data, charset));
    }

    /**
     * zip the input stream to a zip file<br>
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @param path the path or the name of stream in zip file
     * @param in input stream to be zipped
     * @return {@link File} a zip file
     */
    public static File zip(File zipFile, String path, InputStream in) {
        ZipOutputStream out = null;
        try {
            out = getZipOutputStream(zipFile);
            zip(out, path, in);
        } finally {
            IoUtils.close(out);
        }
        return zipFile;
    }

    /**
     * get {@link ZipOutputStream}
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @return {@link ZipOutputStream}
     */
    private static ZipOutputStream getZipOutputStream(File zipFile) {
        try {
            return new ZipOutputStream(new CheckedOutputStream(Files.openOutputStream(zipFile, false), new CRC32()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * to zip all the file in the file folder or path
     *
     * @param out zip output stream
     * @param srcRootDir source root directory
     * @param file file object, recursion to get
     */
    private static void zip(ZipOutputStream out, String srcRootDir, File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            final String subPath = Files.getParentPath(srcRootDir, file);
            BufferedInputStream in = null;
            try {
                in = Files.inputStream(file);
                zip(out, subPath, in);
            } finally {
                IoUtils.close(in);
            }
        } else {
            // if a folder, zip file or folder in this
            for (File childFile : file.listFiles()) {
                zip(out, srcRootDir, childFile);
            }
        }
    }

    /**
     * recursion to get file or sub folder from input stream to zip, out in zip output stream
     * @param out ZipOutputStream
     * @param path the path
     * @param in InputStream
     */
    private static void zip(ZipOutputStream out, String path, InputStream in) {
        try {
            out.putNextEntry(new ZipEntry(path));
            IoUtils.copy(in, out);
        } catch (IOException e) {
            throw  Exceptions.runtime(e);
        } finally {
            closeEntry(out);
        }
    }


    /**
     * validate the file or folder in the input file path, the path can be sub folder in recursion
     *
     * @param zipFile a zip file
     * @param srcFiles the source file list
     */
    private static void validateFiles(File zipFile, File... srcFiles) {
        for (File srcFile : srcFiles) {
            if (false == srcFile.exists()) {
                    throw Exceptions.runtime(StringUtils.format("File [{}] not exist!", srcFile.getAbsolutePath()));
            }

            try {
                // the zip file can not be the same path with file to be zipped
                if (srcFile.isDirectory() && zipFile.getParent().contains(srcFile.getCanonicalPath())) {
                    throw Exceptions.runtime("[zipPath] must not be the child directory of [srcPath]!");
                }

                if (false == zipFile.exists()) {
                    Files.create(zipFile);
                }
            } catch (IOException e) {
                throw  Exceptions.runtime(e);
            }
        }
    }

    /**
     * close the present entry, go to next entry
     * @param out ZipOutputStream
     */
    private static void closeEntry(ZipOutputStream out) {
        try {
            out.closeEntry();
        } catch (IOException e) {
        }
    }

    /**
     * unzip a zip file to the path before
     * @param zipFile the zip file's save path, zipPath can not be the srcPath, or sub folders
     * @return File the path is in the zip file when it is be zipped
     */
    public static File unzip(File zipFile) {
        return unzip(zipFile, Files.create(zipFile.getParentFile(), Files.getFileName(zipFile)));
    }

    /**
     * unzip a zip file by the zip path before
     * @param zipFilePath zip file path
     * @return File
     */
    public static File unzip(String zipFilePath) {
        return unzip(Files.create(zipFilePath));
    }

    /**
     * unzip a zip file to the out file directory
     * @param zipFilePath zip file path
     * @param outFileDir out file directory
     * @return File
     */
    public static File unzip(String zipFilePath, String outFileDir) {
        return unzip(Files.create(zipFilePath), Files.createMkdirs(outFileDir));
    }

    /**
     * unzip a zip file to out file
     * @param zipFile zip file
     * @param outFile out file
     * @return File
     */
    public static File unzip(File zipFile, File outFile) {
        ZipFile zipFileObj = null;
        try {
            zipFileObj = new ZipFile(zipFile);
            final Enumeration<ZipEntry> em = (Enumeration<ZipEntry>) zipFileObj.entries();
            ZipEntry zipEntry = null;
            File outItemFile = null;
            while (em.hasMoreElements()) {
                zipEntry = em.nextElement();
                outItemFile = new File(outFile, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    outItemFile.mkdirs();
                } else {
                    Files.create(outItemFile);
                    copy(zipFileObj, zipEntry, outItemFile);
                }
            }
        } catch (IOException e) {
            throw  Exceptions.runtime(e);
        } finally {
            IoUtils.close(zipFileObj);

        }
        return outFile;
    }


    /**
     * copy from a zip file in stream mode
     * @param zipFile a zip file
     * @param zipEntry zip entry
     * @param outItemFile out item file
     * @throws IOException IO exception
     */
    private static void copy(ZipFile zipFile, ZipEntry zipEntry, File outItemFile) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = zipFile.getInputStream(zipEntry);
            out = Files.openOutputStreamFast(outItemFile, false);
            IoUtils.copy(in, out);
        } finally {
            IoUtils.close(out);
            IoUtils.close(in);
        }
    }

    /**
     * gzip to use gzip utils, gzip source content
     * @param source the source to be zipped
     * @param charset Charset
     * @return byte[] zipped
     */
    public static byte[] gzip(String source, Charset charset) {
        return gzip(ByteUtils.bytes(source, charset));
    }

    /**
     * Gzip
     * @param source the source byte to be zipped
     * @return byte[] zipped
     */
    public static byte[] gzip(byte[] source) {
        FastOutputStream bos = new FastOutputStream(source.length);
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(bos);
            gos.write(source, 0, source.length);
            gos.finish();
            gos.flush();
            source = bos.toByte();
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        } finally {
            IoUtils.close(gos);
        }
        return source;
    }

    /**
     * Gzip a zip file to byte
     * @param file the file to be zipped
     * @return byte[] zipped
     */
    public static byte[] gzip(File file) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        GZIPOutputStream gos = null;
        BufferedInputStream in;
        try {
            gos = new GZIPOutputStream(bos);
            in = Files.inputStream(file);
            IoUtils.copy(in, gos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        } finally {
            IoUtils.close(gos);
        }
    }

    /**
     * unGzip source byte to normal byte with input charset
     * @param source the zipped byte stream
     * @param charset Charset
     * @return String unzip to normal byte stream
     */
    public static String unGzip(byte[] source, Charset charset){
        return StringUtils.utf8Str(unGzip(source), charset);
    }

    /**
     * unGzip zipped byte to normal byte
     * @param source source byte buffer
     * @return getBytes
     */
    public static byte[] unGzip(byte[] source) {
        GZIPInputStream gzi = null;
        ByteArrayOutputStream bos = null;
        try {
            gzi = new GZIPInputStream(new ByteArrayInputStream(source));
            bos = new ByteArrayOutputStream(source.length);
            IoUtils.copy(gzi, bos);
            source = bos.toByteArray();
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        } finally {
            IoUtils.close(gzi);
        }
        return source;
    }

}