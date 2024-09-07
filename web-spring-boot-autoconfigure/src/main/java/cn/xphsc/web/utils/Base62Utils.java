/*
 * Copyright (c) 2023 huipei.x
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
import java.util.Arrays;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.0
 */
public class Base62Utils {

    private Base62Utils() {}

    /**
     * Returns a {@link Encoder} that encodes using the alphabet in
     * <a href="#alphabet">Base62 Table</a>.
     *
     * @return A Base62 encoder.
     */
    public static Encoder getEncoder() {
        return Encoder.DFT_ENCODER;
    }

    /**
     * Returns a {@link Decoder} that decodes using the alphabet in
     * <a href="#alphabet">Base62 Table</a>.
     *
     * @return A Base62 decoder.
     */
    public static Decoder getDecoder() {
        return Decoder.DFT_DECODER;
    }

    /**
     * This class implements an encoder for encoding data using the
     * Base62 encoding scheme.
     *
     * Instances of {@link Encoder} class are save for use by multiple
     * concurrent threads.
     *
     * Unless otherwise noted, passing a {@code null} argument to a
     * method of this class will cause a
     * {@link java.lang.NullPointerException NullPointerException} to
     * be thrown.
     *
     * @see Decoder
     */
    public static class Encoder {

        private final char[] toBase62;
        private final int padToWidth;

        private static final byte BASE62 = 62;
        private static final char[] DFT_ALPHABET = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };

        static final Encoder DFT_ENCODER = new Encoder(DFT_ALPHABET, 0);

        private Encoder(char[] alphabet, int padToWidth) {
            if (padToWidth < 0) {throw new IllegalArgumentException("padToWidth is negative");}

            this.toBase62 = Arrays.copyOf(alphabet, alphabet.length);
            this.padToWidth = padToWidth;
        }

        private byte[] toBytes(int number) {
            byte[] res = new byte[Integer.BYTES];
            for (int i = res.length - 1; i >= 0; i--) {
                res[i] = (byte) (number & 0xff);
                number >>>= 8;
            }
            return res;
        }

        private byte[] toBytes(long number) {
            byte[] res = new byte[Long.BYTES];
            for (int i = res.length - 1; i >= 0; i--) {
                res[i] = (byte) (number & 0xff);
                number >>>= 8;
            }
            return res;
        }

        /**
         * Encodes all bytes from the specified byte array into a newly-allocated
         * byte array using the {@link Base62Utils} encoding scheme.
         *
         * @param   data
         *          the byte array to encode
         * @return  A newly-allocated byte array containing the resulting
         *          encoded bytes.
         */
        public byte[] encode(byte[] data) {
            if (data.length == 0) {
                return new byte[0];
            }

            int len = data.length;
            byte[] rev = new byte[len]; // reversed b62 array
            int rLen = 0;
            int dividend = 0;
            data = Arrays.copyOf(data, len);

            while (len > 0) {
                int nLen = 0;
                for (int i = 0; i < len; i++) {
                    // convert data[i] to unsigned int and then compute
                    dividend = (dividend << 8) | ((int) data[i]) & 0xff;
                    if (dividend >= BASE62) {
                        data[nLen++] = (byte) (dividend / BASE62);
                        dividend %= BASE62;
                    }
                    else if (nLen > 0) {
                        data[nLen++] = 0;
                    }
                }

                if (rLen == rev.length) {
                    rev = Arrays.copyOf(rev, rev.length + (rev.length >> 1));
                }
                rev[rLen++] = (byte) dividend;
                dividend = 0;
                len = nLen;
            }

            byte[] res = new byte[Math.max(rLen, padToWidth)];
            for (int i = res.length-1, j = 0; j < rLen; i--) {
                res[i] = (byte) toBase62[rev[j++]];
            }
            for (int i = res.length - rLen - 1; i >=0; i--) {
                res[i] = (byte) toBase62[0];
            }
            return res;
        }

        /**
         * Converts the specified integer to a byte array and encodes all bytes
         * from the specified number into a newly-allocated byte array using
         * the {@link Base62Utils} encoding scheme.
         *
         * @param   number
         *          the number to encode
         * @return  A newly-allocated byte array containing the resulting
         *          encoded bytes.
         */
        public byte[] encode(int number) {
            return encode(toBytes(number));
        }

        /**
         * Converts the specified long to a byte array and encodes all bytes
         * from the specified number into a newly-allocated byte array using
         * the {@link Base62Utils} encoding scheme.
         *
         * @param   number
         *          the number to encode
         * @return  A newly-allocated byte array containing the resulting
         *          encoded bytes.
         */
        public byte[] encode(long number) {
            return encode(toBytes(number));
        }

        /**
         * Encodes this string into a sequence of bytes using
         * {@link java.nio.charset.StandardCharsets StandardCharsets.UTF_8}
         * and Encodes this byte array into a newly-allocated byte array using
         * the {@link Base62Utils} encoding scheme.
         *
         * @param   str
         *          the string to encode
         * @return  A newly-allocated byte array containing the resulting
         *          encoded bytes.
         */
        public byte[] encode(String str) {
            return encode(str.getBytes(StandardCharsets.UTF_8));
        }

        /**
         * Encodes this string into a sequence of bytes using the given
         * {@link java.nio.charset.Charset charset} and Encodes this
         * byte array into a newly-allocated byte array using the
         * {@link Base62Utils} encoding scheme.
         *
         * @param   str
         *          the string to encode
         * @param   charset
         *          The Charset to be used to encode the String
         * @return  A newly-allocated byte array containing the resulting
         *          encoded bytes.
         */
        public byte[] encode(String str, Charset charset) {
            return encode(str.getBytes(charset));
        }

        /**
         * Returns an encoder instance that encodes equivalently to this one,
         * but with a specified encoding alphabet.
         *
         * The padToWidth is unaffected by this invocation.
         *
         * @param   alphabet
         *          The alphabet to be used to encode the data
         *
         * @return an equivalent encoder that encodes with specified alphabet
         */
        public Encoder withAlphabet(char[] alphabet) {
            return new Encoder(alphabet, 0);
        }

        /**
         * Returns an encoder instance that encodes equivalently to this one,
         * but with a specified padToWidth value.
         *
         * The default encoder has padToWidth set to 0, i.e. no padding will
         * be added to the encoded result.
         *
         * If specified and the result byte array has a smaller length than
         * padToWidth, the result will be padded from the beginning of the
         * array with the character corresponding to index 0 to meet the
         * width requirement.
         *
         * The alphabet is unaffected by this invocation.
         *
         * @param   padToWidth
         *          The min length of result byte array after encoding
         *
         * @return an equivalent encoder that encodes with specified padToWidth
         *
         * @throws IllegalArgumentException if {@code padToWidth < 0}
         */
        public Encoder withPadToWidth(int padToWidth) {
            if (padToWidth < 0){ throw new IllegalArgumentException("padToWidth is negative");}
            return new Encoder(this.toBase62, padToWidth);
        }
    }

    /**
     * This class implements a decoder for decoding byte data using the Base62 encoding
     * scheme as specified in {@link Encoder}.
     *
     * Instances of {@link Decoder} class are safe for use by
     * multiple concurrent threads.
     *
     * <p> Unless otherwise noted, passing a {@code null} argument to a method of this
     * class will cause a {@link java.lang.NullPointerException NullPointerException}
     * to be thrown.
     *
     * @see Encoder
     */
    public static class Decoder {

        private static final int BASE256 = 256;

        private final int[] fromBase62;
        private final int padToWidth;

        static final Decoder DFT_DECODER = new Decoder(Encoder.DFT_ALPHABET, 0);

        private Decoder(int[] fromBase62, int padToWidth) {
            if (padToWidth < 0) {throw new IllegalArgumentException("padToWidth is negative");}

            this.fromBase62 = Arrays.copyOf(fromBase62, fromBase62.length);
            this.padToWidth = padToWidth;
        }

        private Decoder(char[] alphabet, int padToWidth) {
            if (padToWidth < 0) {throw new IllegalArgumentException("padToWidth is negative");}

            fromBase62 = new int[256];
            Arrays.fill(fromBase62, -1);

            for (int i = 0; i < alphabet.length; i++) {
                fromBase62[alphabet[i]] = i;
            }

            this.padToWidth = padToWidth;
        }

        /**
         * Decodes all bytes from the input byte array using the {@link Base62Utils}
         * encoding scheme, writing the results into a newly-allocated output
         * byte array.
         *
         * @param   src
         *          the byte array to decode
         *
         * @return  A newly-allocated byte array containing the decoded bytes.
         *
         * @throws  IllegalArgumentException
         *          if {@code src} is not in valid Base64 scheme
         */
        public byte[] decode(byte[] src) {
            int len = src.length;
            byte[] rev = new byte[len]; // reversed b256 array
            int rLen = 0;
            int dividend = 0;
            src = Arrays.copyOf(src, len);

            while (len > 0) {
                int nLen = 0;
                for (int i = 0; i < len; i++) {
                    // convert data[i] to unsigned int and then compute
                    dividend = (dividend * 62) + (((int) src[i]) & 0xff);
                    if (dividend >= BASE256) {
                        src[nLen++] = (byte) (dividend / BASE256);
                        dividend %= BASE256;
                    }
                    else if (nLen > 0) {
                        src[nLen++] = 0;
                    }
                }

                if (rLen == rev.length) {
                    rev = Arrays.copyOf(rev, rev.length + (rev.length >> 1));
                }
                rev[rLen++] = (byte) dividend;
                dividend = 0;
                len = nLen;
            }

            byte[] res = new byte[Math.max(rLen, padToWidth)];
            for (int i = res.length-1, j = 0; j < rLen; i--) {
                res[i] = (byte) rev[j++];
            }
            for (int i = res.length - rLen - 1; i >=0; i--) {
                res[i] = 0;
            }
            return res;
        }

        /**
         * Converts each character in the str to a byte array of the corresponding
         * index in alphabet, and then decode all bytes using the {@link Base62Utils}
         * encoding scheme, writing the results into a newly-allocated output
         * byte array.
         *
         * @param   str
         *          the string to decode
         *
         * @return  A newly-allocated byte array containing the decoded bytes.
         *
         * @throws  IllegalArgumentException
         *          if {@code str} is not in valid Base64 scheme
         */
        public byte[] decode(String str) {
            if (str.length() == 0) {return new byte[0];}

            byte[] data = new byte[str.length()];

            for (int i = 0, idx; i < data.length; i++) {
                idx = str.charAt(i);
                if (fromBase62[idx] == -1) {
                    throw new IllegalArgumentException("Unkown character: " + (char) idx);
                }
                data[i] = (byte) fromBase62[idx];
            }

            return decode(data);
        }

        /**
         * Returns an decoder instance that decodes equivalently to this one,
         * but with a specified decoding alphabet.
         *
         * The padToWidth is unaffected by this invocation.
         *
         * @param   alphabet
         *          The alphabet to be used to decode the data
         *
         * @return an equivalent decoder that decodes with specified alphabet
         */
        public Decoder withAlphabet(char[] alphabet) {
            return new Decoder(alphabet, this.padToWidth);
        }

        /**
         * Returns an decoder instance that decodes equivalently to this one,
         * but with a specified padToWidth value.
         *
         * The default decoder has padToWidth set to 0, i.e. no padding will
         * be added to the decoded result.
         *
         * If specified and the result byte array has a smaller length than
         * padToWidth, the result will be padded from the beginning of the
         * array with bytes of 0 value to meet the width requirement.
         *
         * The alphabet is unaffected by this invocation.
         *
         * @param   padToWidth
         *          The min length of result byte array after decoding
         *
         * @return an equivalent decoder that decodes with specified padToWidth
         *
         * @throws IllegalArgumentException if {@code padToWidth < 0}
         */
        public Decoder withPadToWidth(int padToWidth) {
            if (padToWidth < 0) {throw new IllegalArgumentException("padToWidth is negative");}
            return new Decoder(this.fromBase62, padToWidth);
        }
    }

}
