
package cn.xphsc.web.common.lang.io.file;


public class FastByteBuffer {

    /**
     * default buffer size
     */
    public static final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * buffer to define
     */
    private byte[][] buffers = new byte[16][];

    /**
     * buffer count
     */
    private int count;
    /**
     * index of buffer
     */
    private int index = -1;
    /**
     * current buffer
     */
    private byte[] currentBuffer;
    /**
     * offset of buffer
     */
    private int offset;
    /**
     * size of buffer
     */
    private int size;

    /**
     * the minimal byte of one buffer
     */
    private final int minLength;

    /**
     * constructor of self to set minLength with default size
     */
    public FastByteBuffer() {
        this.minLength = DEFAULT_BUFFER_SIZE;
    }

    /**
     * constructor of self to set minLength with input size
     */
    public FastByteBuffer(int size) {
        this.minLength = Math.abs(size);
    }

    /**
     * if self is empty
     *
     * @return if empty true, else false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * allocate new next buffer, the buffer can not less than default buffer size
     *
     * @param size new buffer size
     */
    private void newBuffer(int size) {
        int delta = size - this.size;
        int newSize = Math.max(minLength, delta);

        index++;
        currentBuffer = new byte[newSize];
        offset = 0;

        // add buffer
        if (index >= buffers.length) {
            int newLen = buffers.length << 1;
            byte[][] newBuffers = new byte[newLen][];
            System.arraycopy(buffers, 0, newBuffers, 0, buffers.length);
            buffers = newBuffers;
        }
        buffers[index] = currentBuffer;
        count++;
    }

    /**
     * append byte array stream to new FastByteBuffer
     *
     * @param source source array stream data of byte
     * @param off off position
     * @param len the length of append operation
     * @return {@link FastByteBuffer} a new FastByteBuffer object
     */
    public FastByteBuffer append(byte[] source, int off, int len) {
        int end = off + len;
        if ((off < 0) || (len < 0) || (end > source.length)) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return this;
        }
        int newSize = size + len;
        int remaining = len;

        if (currentBuffer != null) {
            // first try to fill current buffer
            int part = Math.min(remaining, currentBuffer.length - offset);
            System.arraycopy(source, end - remaining, currentBuffer, offset, part);
            remaining -= part;
            offset += part;
            size += part;
        }

        if (remaining > 0) {
            // still some data left
            // ask for new buffer
            newBuffer(newSize);

            // then copy remaining
            // but this time we are sure that it will fit
            int part = Math.min(remaining, currentBuffer.length - offset);
            System.arraycopy(source, end - remaining, currentBuffer, offset, part);
            offset += part;
            size += part;
        }

        return this;
    }

    /**
     * append byte array stream to new FastByteBuffer, the default off is 0, len is source length
     *
     * @param source source array stream data of byte
     * @return {@link FastByteBuffer} a new FastByteBuffer object
     */
    public FastByteBuffer append(byte[] source) {
        return append(source, 0, source.length);
    }

    /**
     * append ont byte element to new FastByteBuffer
     *
     * @param source one element
     * @return {@link FastByteBuffer} a new FastByteBuffer object
     */
    public FastByteBuffer append(byte source) {
        if ((currentBuffer == null) || (offset == currentBuffer.length)) {
            newBuffer(size + 1);
        }

        currentBuffer[offset] = source;
        offset++;
        size++;

        return this;
    }

    /**
     * append a new source FastByteBuffer to self
     *
     * @param source a source FastByteBuffer object
     * @return {@link FastByteBuffer} a new FastByteBuffer object
     */
    public FastByteBuffer append(FastByteBuffer source) {
        if (source.size == 0) {
            return this;
        }
        for (int i = 0; i < source.index; i++) {
            append(source.buffers[i]);
        }
        append(source.currentBuffer, 0, source.offset);
        return this;
    }


    /**
     * to get byte stream of buffer with whole self stream
     *
     * @return the byte in buffer
     */
    public byte[] toByte() {
        int pos = 0;
        byte[] array = new byte[size];

        if (index == -1) {
            return array;
        }

        for (int i = 0; i < index; i++) {
            int len = buffers[i].length;
            System.arraycopy(buffers[i], 0, array, pos, len);
            pos += len;
        }

        System.arraycopy(buffers[index], 0, array, pos, offset);

        return array;
    }

    /**
     * to get byte stream of buffer with input start position and length
     *
     * @param start start position
     * @param len the length of buffer to get
     * @return the byte in buffer
     */
    public byte[] toByte(int start, int len) {
        int remaining = len;
        int pos = 0;
        byte[] array = new byte[len];

        if (len == 0) {
            return array;
        }

        int i = 0;
        while (start >= buffers[i].length) {
            start -= buffers[i].length;
            i++;
        }

        while (i < count) {
            byte[] buf = buffers[i];
            int c = Math.min(buf.length - start, remaining);
            System.arraycopy(buf, start, array, pos, c);
            pos += c;
            remaining -= c;
            if (remaining == 0) {
                break;
            }
            start = 0;
            i++;
        }
        return array;
    }

    /**
     * get the one byte of buffer with input index
     *
     * @param index input index
     * @return one byte
     */
    public byte get(int index) {
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException();
        }
        int ndx = 0;
        while (true) {
            byte[] b = buffers[ndx];
            if (index < b.length) {
                return b[index];
            }
            ndx++;
            index -= b.length;
        }
    }

    /**
     * the size of self
     * @return {@link #size}
     */
    public int size() {
        return size;
    }

    /**
     * the index of self
     *
     * @return {@link #index}
     */
    public int index() {
        return index;
    }

    /**
     * the offset of self
     *
     * @return {@link #offset}
     */
    public int offset() {
        return offset;
    }

    /**
     * get the byte of buffer from index
     *
     * @param index index
     * @return the byte in buffer
     */
    public byte[] array(int index) {
        return buffers[index];
    }

    /**
     * reset self to initial
     */
    public void reset() {
        size = 0;
        offset = 0;
        index = -1;
        currentBuffer = null;
        count = 0;
    }

}