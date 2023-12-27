package cn.xphsc.web.common.lang.io;

import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.utils.ByteUtils;
import java.io.*;
import java.util.function.Consumer;
import java.util.function.IntConsumer;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: io 操作
 * @since 1.1.7
 */
@SuppressWarnings("ALL")
public class Streams {

    public Streams() {
    }

    // -------------------- close --------------------

    public static void close(AutoCloseable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception ioe) {
            // ignore
        }
    }

    // -------------------- flush --------------------

    public static void flush(Flushable f) {
        try {
            if (f != null) {
                f.flush();
            }
        } catch (Exception ioe) {
            // ignore
        }
    }

    // -------------------- buffer --------------------

    public static BufferedReader toBufferedReader(Reader reader) {
        return toBufferedReader(reader, Constants.BUFFER_KB_8);
    }

    public static BufferedReader toBufferedReader(Reader reader, int bufferSize) {
        if (reader instanceof BufferedReader) {
            return (BufferedReader) reader;
        } else {
            return new BufferedReader(reader, bufferSize);
        }
    }

    public static BufferedWriter toBufferedWriter(Writer writer) {
        return toBufferedWriter(writer, Constants.BUFFER_KB_8);
    }

    public static BufferedWriter toBufferedWriter(Writer writer, int bufferSize) {
        if (writer instanceof BufferedWriter) {
            return (BufferedWriter) writer;
        } else {
            return new BufferedWriter(writer, bufferSize);
        }
    }

    // -------------------- transfer --------------------

    public static int transfer(RandomAccessFile access, OutputStream output) throws IOException {
        long count = transferLarge(access, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long transferLarge(RandomAccessFile access, OutputStream output) throws IOException {
        byte[] buffer = new byte[Constants.BUFFER_KB_8];
        long count = 0;
        int n = 0;
        while ((n = access.read(buffer)) != -1) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static int transfer(InputStream input, OutputStream output) throws IOException {
        long count = transferLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long transferLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[Constants.BUFFER_KB_8];
        long count = 0;
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static void transfer(InputStream input, Writer output) throws IOException {
        transfer(new InputStreamReader(input), output);
    }

    public static void transfer(InputStream input, Writer output, String charset) throws IOException {
        if (charset == null) {
            transfer(input, output);
        } else {
            transfer(new InputStreamReader(input, charset), output);
        }
    }

    public static int transfer(Reader input, Writer output) throws IOException {
        long count = transferLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long transferLarge(Reader input, Writer output) throws IOException {
        char[] buffer = new char[Constants.BUFFER_KB_8];
        long count = 0;
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static void transfer(Reader input, OutputStream output) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output);
        transfer(input, out);
        out.flush();
    }

    public static void transfer(Reader input, OutputStream output, String charset) throws IOException {
        if (charset == null) {
            transfer(input, output);
        } else {
            OutputStreamWriter out = new OutputStreamWriter(output, charset);
            transfer(input, out);
            out.flush();
        }
    }

    // -------------------- convert byte array --------------------

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        transfer(input, output);
        return output.toByteArray();
    }

    public static byte[] toByteArray(Reader input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        transfer(input, output);
        return output.toByteArray();
    }

    public static byte[] toByteArray(Reader input, String charset) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        transfer(input, output, charset);
        return output.toByteArray();
    }

    public static char[] toCharArray(InputStream is) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        transfer(is, output);
        return output.toCharArray();
    }

    public static char[] toCharArray(InputStream is, String charset) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        transfer(is, output, charset);
        return output.toCharArray();
    }

    public static char[] toCharArray(Reader input) throws IOException {
        CharArrayWriter sw = new CharArrayWriter();
        transfer(input, sw);
        return sw.toCharArray();
    }

    public static String toString(InputStream input) throws IOException {
        StringWriter sw = new StringWriter();
        transfer(input, sw);
        return sw.toString();
    }

    public static String toString(InputStream input, String charset) throws IOException {
        StringWriter sw = new StringWriter();
        transfer(input, sw, charset);
        return sw.toString();
    }

    public static String toString(Reader input) throws IOException {
        StringWriter sw = new StringWriter();
        transfer(input, sw);
        return sw.toString();
    }

    public static String toString(byte[] input) throws IOException {
        return new String(input);
    }

    public static String toString(byte[] input, String charset) throws IOException {
        if (charset == null) {
            return new String(input);
        } else {
            return new String(input, charset);
        }
    }

    // -------------------- convert stream --------------------

    public static InputStream toInputStream(byte[] bs) {
        return new ByteArrayInputStream(bs);
    }

    public static InputStream toInputStream(byte[] bs, int off, int len) {
        return new ByteArrayInputStream(bs, off, len);
    }

    public static InputStream toInputStream(String input) {
        return new ByteArrayInputStream(ByteUtils.toBytes(input));
    }

    public static InputStream toInputStream(String input, String charset) {
        byte[] bytes = charset != null ? ByteUtils.bytes(input, charset) : ByteUtils.toBytes(input);
        return new ByteArrayInputStream(bytes);
    }

    public static OutputStream toOutputStream(byte[] bs) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(bs);
        } catch (Exception e) {
            // ignore
        }
        return out;
    }

    public static OutputStream toOutputStream(byte[] bs, int off, int len) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(bs, off, len);
        } catch (Exception e) {
            // ignore
        }
        return out;
    }

    public static OutputStream toOutputStream(String input) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(ByteUtils.toBytes(input));
        } catch (Exception e) {
            // ignore
        }
        return out;
    }

    public static OutputStream toOutputStream(String input, String charset) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(charset != null ? ByteUtils.bytes(input, charset) : ByteUtils.toBytes(input));
        } catch (Exception e) {
            // ignore
        }
        return out;
    }



    // -------------------- line consumer --------------------

    public static void lineConsumer(InputStream in, Consumer<String> c) throws IOException {
        lineConsumer(new InputStreamReader(in), Constants.BUFFER_KB_8, c);
    }

    public static void lineConsumer(InputStream in, String charset, Consumer<String> c) throws IOException {
        if (charset == null) {
            lineConsumer(in, c);
            return;
        }
        lineConsumer(new InputStreamReader(in, charset), Constants.BUFFER_KB_8, c);
    }

    public static void lineConsumer(Reader reader, Consumer<String> c) throws IOException {
        lineConsumer(reader, Constants.BUFFER_KB_8, c);
    }

    public static void lineConsumer(Reader reader, int bufferSize, Consumer<String> c) throws IOException {
        BufferedReader bufferedReader = toBufferedReader(reader, bufferSize);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            c.accept(line);
        }
    }



    // -------------------- byte array consumer --------------------

    public static void byteArrayConsumer(InputStream input, byte[] buffer, IntConsumer consumer) throws IOException {
        int read;
        while ((read = input.read(buffer)) != -1) {
            consumer.accept(read);
        }
    }


}
