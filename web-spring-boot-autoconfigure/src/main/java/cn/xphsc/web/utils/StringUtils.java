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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class StringUtils {
    public static final String DOT = ".";
    private static final int INDEX_NOT_FOUND = -1;
    public static final String COMMA = ",";
    public static final String EMPTY = "";

    public StringUtils() {
    }

    public static String newStringForUtf8(byte[] bytes) {
        return new String(bytes, Charset.forName("UTF-8"));
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if(cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
    public static boolean isAllBlank(String... strs) {
        String[] var1 = strs;
        int var2 = strs.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String str = var1[var3];
            if (isNotBlank(str)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String defaultIfEmpty(String str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String substringBetween(String str, String open, String close) {
        if (str != null && open != null && close != null) {
            int start = str.indexOf(open);
            if (start != -1) {
                int end = str.indexOf(close, start + open.length());
                if (end != -1) {
                    return str.substring(start + open.length(), end);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String join(Collection collection, String separator) {
        if (collection == null) {
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            Object[] objects = collection.toArray();

            for(int i = 0; i < collection.size() - 1; ++i) {
                stringBuilder.append(objects[i].toString()).append(separator);
            }

            if (collection.size() > 0) {
                stringBuilder.append(objects[collection.size() - 1]);
            }

            return stringBuilder.toString();
        }
    }
    public static String left(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }
    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }
    public static String removeStart(String str, String remove) {
        if (!isEmpty(str) && !isEmpty(remove)) {
            return str.startsWith(remove) ? str.substring(remove.length()) : str;
        } else {
            return str;
        }
    }
    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : repeat(padChar, pads).concat(str);
            }
        }
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return leftPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }
    public static String right(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(str.length() - len);
        }
    }
    public static int indexOf(CharSequence seq, CharSequence searchSeq) {
        return seq != null && searchSeq != null ? CharSequenceUtils.indexOf(seq, searchSeq, 0) : -1;
    }

    public static String escapeJavaScript(String str) {
        return escapeJavaStyleString(str, true, true);
    }

    private static String escapeJavaStyleString(String str, boolean escapeSingleQuotes, boolean escapeForwardSlash) {
        if (str == null) {
            return null;
        } else {
            try {
                StringWriter writer = new StringWriter(str.length() * 2);
                escapeJavaStyleString(writer, str, escapeSingleQuotes, escapeForwardSlash);
                return writer.toString();
            } catch (IOException var4) {
                return null;
            }
        }
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? rightPad(str, size, String.valueOf(padChar)) : str.concat(repeat(padChar, pads));
            }
        }
    }
    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return rightPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return str.concat(padStr);
            } else if (pads < padLen) {
                return str.concat(padStr.substring(0, pads));
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return str.concat(new String(padding));
            }
        }
    }
    public static String repeat(char ch, int repeat) {
        if (repeat <= 0) {
            return "";
        } else {
            char[] buf = new char[repeat];

            for(int i = repeat - 1; i >= 0; --i) {
                buf[i] = ch;
            }

            return new String(buf);
        }
    }
    private static void escapeJavaStyleString(Writer out, String str, boolean escapeSingleQuote, boolean escapeForwardSlash) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        } else if (str != null) {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                char ch = str.charAt(i);
                if (ch > 4095) {
                    out.write("\\u" + hex(ch));
                } else if (ch > 255) {
                    out.write("\\u0" + hex(ch));
                } else if (ch > 127) {
                    out.write("\\u00" + hex(ch));
                } else if (ch < ' ') {
                    switch(ch) {
                        case '\b':
                            out.write(92);
                            out.write(98);
                            break;
                        case '\t':
                            out.write(92);
                            out.write(116);
                            break;
                        case '\n':
                            out.write(92);
                            out.write(110);
                            break;
                        case '\u000b':
                        default:
                            if (ch > 15) {
                                out.write("\\u00" + hex(ch));
                            } else {
                                out.write("\\u000" + hex(ch));
                            }
                            break;
                        case '\f':
                            out.write(92);
                            out.write(102);
                            break;
                        case '\r':
                            out.write(92);
                            out.write(114);
                    }
                } else {
                    switch(ch) {
                        case '"':
                            out.write(92);
                            out.write(34);
                            break;
                        case '\'':
                            if (escapeSingleQuote) {
                                out.write(92);
                            }

                            out.write(39);
                            break;
                        case '/':
                            if (escapeForwardSlash) {
                                out.write(92);
                            }

                            out.write(47);
                            break;
                        case '\\':
                            out.write(92);
                            out.write(92);
                            break;
                        default:
                            out.write(ch);
                    }
                }
            }

        }
    }

    private static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase(Locale.ENGLISH);
    }

    public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
        if (str != null && searchStr != null) {
            int len = searchStr.length();
            int max = str.length() - len;

            for(int i = 0; i <= max; ++i) {
                if (regionMatches(str, true, i, searchStr, 0, len)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String)cs).regionMatches(ignoreCase, thisStart, (String)substring, start, length);
        } else {
            int index1 = thisStart;
            int index2 = start;
            int var8 = length;

            while(var8-- > 0) {
                char c1 = cs.charAt(index1++);
                char c2 = substring.charAt(index2++);
                if (c1 != c2) {
                    if (!ignoreCase) {
                        return false;
                    }

                    if (Character.toUpperCase(c1) != Character.toUpperCase(c2) && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        if (str1 != null && str2 != null) {
            if (str1 == str2) {
                return true;
            } else {
                return str1.length() != str2.length() ? false : StringUtils.CharSequenceUtils.regionMatches(str1, true, 0, str2, 0, str1.length());
            }
        } else {
            return str1 == str2;
        }
    }
    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        } else if (len >= 0 && pos <= str.length()) {
            if (pos < 0) {
                pos = 0;
            }

            return str.length() <= pos + len ? str.substring(pos) : str.substring(pos, pos + len);
        } else {
            return "";
        }
    }
    static class CharSequenceUtils {
        CharSequenceUtils() {
        }

        static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
            if (cs instanceof String && substring instanceof String) {
                return ((String)cs).regionMatches(ignoreCase, thisStart, (String)substring, start, length);
            } else {
                int index1 = thisStart;
                int index2 = start;
                int var8 = length;

                while(var8-- > 0) {
                    char c1 = cs.charAt(index1++);
                    char c2 = substring.charAt(index2++);
                    if (c1 != c2) {
                        if (!ignoreCase) {
                            return false;
                        }

                        if (Character.toUpperCase(c1) != Character.toUpperCase(c2) && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }

        static int indexOf(CharSequence cs, CharSequence searchChar, int start) {
            return cs.toString().indexOf(searchChar.toString(), start);
        }
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if(!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if(end == -1) {
                return text;
            } else {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = increase < 0?0:increase;
                increase *= max < 0?16:(max > 64?64:max);

                StringBuilder buf;
                for(buf = new StringBuilder(text.length() + increase); end != -1; end = text.indexOf(searchString, start)) {
                    buf.append(text.substring(start, end)).append(replacement);
                    start = end + replLength;
                    --max;
                    if(max == 0) {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }


    public static String removeEnd(final String str, final String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    public static String upperCaseFirst(String source) {
        return source.substring(0, 1).toUpperCase().concat(source.substring(1));
    }

    public static String substring(String str, int start) {
        if(str == null) {
            return null;
        } else {
            if(start < 0) {
                start += str.length();
            }

            if(start < 0) {
                start = 0;
            }

            return start > str.length()?"":str.substring(start);
        }
    }

    public static String substring(String str, int start, int end) {
        if(str == null) {
            return null;
        } else {
            if(end < 0) {
                end += str.length();
            }

            if(start < 0) {
                start += str.length();
            }

            if(end > str.length()) {
                end = str.length();
            }

            if(start > end) {
                return "";
            } else {
                if(start < 0) {
                    start = 0;
                }

                if(end < 0) {
                    end = 0;
                }

                return str.substring(start, end);
            }
        }
    }

    public static String substringBefore(String str, String separator) {
        if (!isEmpty(str) && separator != null) {
            if (separator.isEmpty()) {
                return "";
            } else {
                int pos = str.indexOf(separator);
                return pos == -1 ? str : str.substring(0, pos);
            }
        } else {
            return str;
        }
    }

    public static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        } else if (separator == null) {
            return "";
        } else {
            int pos = str.indexOf(separator);
            return pos == -1 ? "" : str.substring(pos + separator.length());
        }
    }

    public static String substringBeforeLast(String str, String separator) {
        if (!isEmpty(str) && !isEmpty(separator)) {
            int pos = str.lastIndexOf(separator);
            return pos == -1 ? str : str.substring(0, pos);
        } else {
            return str;
        }
    }

    public static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        } else if (isEmpty(separator)) {
            return "";
        } else {
            int pos = str.lastIndexOf(separator);
            return pos != -1 && pos != str.length() - separator.length() ? str.substring(pos + separator.length()) : "";
        }
    }

    public static String upperCaseOfStart(String charsetName){
        if(isNotBlank(charsetName)){
            charsetName =  substring(charsetName, 0, 1).toUpperCase() +substring(charsetName,1);
        } else {
            throw new IllegalArgumentException("Strings must not be null");
        }
        return charsetName;
    }

    public static String lowerCaseOfStart(String charsetName){
        if(isNotBlank(charsetName)){
            charsetName = substring(charsetName, 0, 1).toLowerCase() + substring(charsetName,1);
        } else {
            throw new IllegalArgumentException("Strings must not be null");
        }
        return charsetName;
    }

    public static boolean isNotBlankAndZero(String value){
        if(isNotBlank(value)&&!"0".equals(value)){
            return true;
        }else{
            return false;
        }
    }


    public static String remove(String str, String remove) {
        return !isEmpty(str) && !isEmpty(remove)?replace(str, remove, "", -1):str;
    }

    public static String remove(String str, char remove) {
        if(!isEmpty(str) && str.indexOf(remove) != -1) {
            char[] chars = str.toCharArray();
            int pos = 0;

            for(int i = 0; i < chars.length; ++i) {
                if(chars[i] != remove) {
                    chars[pos++] = chars[i];
                }
            }

            return new String(chars, 0, pos);
        } else {
            return str;
        }
    }


    public static String[] split(String str) {
        return split(str, (String)null, -1);
    }

    public static String[] split(String str, char separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    public static String[] split(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, false);
    }

    public static String[] split(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, false);
    }

    public static String[] splitByWholeSeparator(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, false);
    }

    public static String[] splitByWholeSeparator(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, true);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, true);
    }

    private static String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens) {
        if(str == null) {
            return null;
        } else {
            int len = str.length();
            if(len == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else if(separator != null && !"".equals(separator)) {
                int separatorLength = separator.length();
                ArrayList substrings = new ArrayList();
                int numberOfSubstrings = 0;
                int beg = 0;
                int end = 0;

                while(end < len) {
                    end = str.indexOf(separator, beg);
                    if(end > -1) {
                        if(end > beg) {
                            ++numberOfSubstrings;
                            if(numberOfSubstrings == max) {
                                end = len;
                                substrings.add(str.substring(beg));
                            } else {
                                substrings.add(str.substring(beg, end));
                                beg = end + separatorLength;
                            }
                        } else {
                            if(preserveAllTokens) {
                                ++numberOfSubstrings;
                                if(numberOfSubstrings == max) {
                                    end = len;
                                    substrings.add(str.substring(beg));
                                } else {
                                    substrings.add("");
                                }
                            }

                            beg = end + separatorLength;
                        }
                    } else {
                        substrings.add(str.substring(beg));
                        end = len;
                    }
                }

                return (String[])substrings.toArray(new String[substrings.size()]);
            } else {
                return splitWorker(str, (String)null, max, preserveAllTokens);
            }
        }
    }

    public static String[] splitPreserveAllTokens(String str) {
        return splitWorker(str, (String) null, -1, true);
    }

    public static String[] splitPreserveAllTokens(String str, char separatorChar) {
        return splitWorker(str, separatorChar, true);
    }

    private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
        if(str == null) {
            return null;
        } else {
            int len = str.length();
            if(len == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                ArrayList list = new ArrayList();
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;

                while(true) {
                    while(i < len) {
                        if(str.charAt(i) == separatorChar) {
                            if(match || preserveAllTokens) {
                                list.add(str.substring(start, i));
                                match = false;
                                lastMatch = true;
                            }

                            ++i;
                            start = i;
                        } else {
                            lastMatch = false;
                            match = true;
                            ++i;
                        }
                    }

                    if(match || preserveAllTokens && lastMatch) {
                        list.add(str.substring(start, i));
                    }

                    return (String[])list.toArray(new String[list.size()]);
                }
            }
        }
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, true);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, true);
    }

    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        if(str == null) {
            return null;
        } else {
            int len = str.length();
            if(len == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                ArrayList list = new ArrayList();
                int sizePlus1 = 1;
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;
                if(separatorChars != null) {
                    if(separatorChars.length() != 1) {
                        label:
                        while(true) {
                            while(true) {
                                if(i >= len) {
                                    break label;
                                }

                                if(separatorChars.indexOf(str.charAt(i)) >= 0) {
                                    if(match || preserveAllTokens) {
                                        lastMatch = true;
                                        if(sizePlus1++ == max) {
                                            i = len;
                                            lastMatch = false;
                                        }

                                        list.add(str.substring(start, i));
                                        match = false;
                                    }

                                    ++i;
                                    start = i;
                                } else {
                                    lastMatch = false;
                                    match = true;
                                    ++i;
                                }
                            }
                        }
                    } else {
                        char sep = separatorChars.charAt(0);

                        label71:
                        while(true) {
                            while(true) {
                                if(i >= len) {
                                    break label71;
                                }

                                if(str.charAt(i) == sep) {
                                    if(match || preserveAllTokens) {
                                        lastMatch = true;
                                        if(sizePlus1++ == max) {
                                            i = len;
                                            lastMatch = false;
                                        }

                                        list.add(str.substring(start, i));
                                        match = false;
                                    }

                                    ++i;
                                    start = i;
                                } else {
                                    lastMatch = false;
                                    match = true;
                                    ++i;
                                }
                            }
                        }
                    }
                } else {
                    label103:
                    while(true) {
                        while(true) {
                            if(i >= len) {
                                break label103;
                            }

                            if(Character.isWhitespace(str.charAt(i))) {
                                if(match || preserveAllTokens) {
                                    lastMatch = true;
                                    if(sizePlus1++ == max) {
                                        i = len;
                                        lastMatch = false;
                                    }

                                    list.add(str.substring(start, i));
                                    match = false;
                                }

                                ++i;
                                start = i;
                            } else {
                                lastMatch = false;
                                match = true;
                                ++i;
                            }
                        }
                    }
                }

                if(match || preserveAllTokens && lastMatch) {
                    list.add(str.substring(start, i));
                }

                return (String[])list.toArray(new String[list.size()]);
            }
        }
    }

    public static String[] splitByCharacterType(String str) {
        return splitByCharacterType(str, false);
    }

    public static String[] splitByCharacterTypeCamelCase(String str) {
        return splitByCharacterType(str, true);
    }

    private static String[] splitByCharacterType(String str, boolean camelCase) {
        if(str == null) {
            return null;
        } else if(str.isEmpty()) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        } else {
            char[] c = str.toCharArray();
            ArrayList list = new ArrayList();
            int tokenStart = 0;
            int currentType = Character.getType(c[tokenStart]);

            for(int pos = tokenStart + 1; pos < c.length; ++pos) {
                int type = Character.getType(c[pos]);
                if(type != currentType) {
                    if(camelCase && type == 2 && currentType == 1) {
                        int newTokenStart = pos - 1;
                        if(newTokenStart != tokenStart) {
                            list.add(new String(c, tokenStart, newTokenStart - tokenStart));
                            tokenStart = newTokenStart;
                        }
                    } else {
                        list.add(new String(c, tokenStart, pos - tokenStart));
                        tokenStart = pos;
                    }

                    currentType = type;
                }
            }

            list.add(new String(c, tokenStart, c.length - tokenStart));
            return (String[])list.toArray(new String[list.size()]);
        }
    }

    public static <T> String join(T... elements) {
        return join((Object[]) elements, (String) null);
    }

    public static String join(Object[] array, char separator) {
        return array == null?null:join((Object[])array, separator, 0, array.length);
    }

    public static String join(long[] array, char separator) {
        return array == null?null:join((long[])array, separator, 0, array.length);
    }

    public static String join(int[] array, char separator) {
        return array == null?null:join((int[])array, separator, 0, array.length);
    }

    public static String join(short[] array, char separator) {
        return array == null?null:join((short[])array, separator, 0, array.length);
    }

    public static String join(byte[] array, char separator) {
        return array == null?null:join((byte[])array, separator, 0, array.length);
    }

    public static String join(char[] array, char separator) {
        return array == null?null:join((char[])array, separator, 0, array.length);
    }

    public static String join(float[] array, char separator) {
        return array == null?null:join((float[])array, separator, 0, array.length);
    }

    public static String join(double[] array, char separator) {
        return array == null?null:join((double[])array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    if(array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(long[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(int[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(byte[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(short[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(char[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(double[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(float[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(Object[] array, String separator) {
        return array == null?null:join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            if(separator == null) {
                separator = "";
            }

            int noOfItems = endIndex - startIndex;
            if(noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    if(array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String deleteWhitespace(String str) {
        if(isEmpty(str)) {
            return str;
        } else {
            int sz = str.length();
            char[] chs = new char[sz];
            int count = 0;

            for(int i = 0; i < sz; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    chs[count++] = str.charAt(i);
                }
            }

            if(count == sz) {
                return str;
            } else {
                return new String(chs, 0, count);
            }
        }
    }

    public static String format(String strPattern, Object... argArray) {
        if(!StringUtils.isBlank(strPattern) && !ArrayUtils.isEmpty(argArray)) {
            int strPatternLength = strPattern.length();
            StringBuilder sbuf = new StringBuilder(strPatternLength + 50);
            int handledPosition = 0;

            for(int argIndex = 0; argIndex < argArray.length; ++argIndex) {
                int delimIndex = strPattern.indexOf("{}", handledPosition);
                if(delimIndex == -1) {
                    if(handledPosition == 0) {
                        return strPattern;
                    }

                    sbuf.append(strPattern, handledPosition, strPatternLength);
                    return sbuf.toString();
                }

                if(delimIndex > 0 && strPattern.charAt(delimIndex - 1) == 92) {
                    if(delimIndex > 1 && strPattern.charAt(delimIndex - 2) == 92) {
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(StringUtils.utf8Str(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    } else {
                        --argIndex;
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append('{');
                        handledPosition = delimIndex + 1;
                    }
                } else {
                    sbuf.append(strPattern, handledPosition, delimIndex);
                    sbuf.append(StringUtils.utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }

            sbuf.append(strPattern, handledPosition, strPattern.length());
            return sbuf.toString();
        } else {
            return strPattern;
        }
    }

    /**
     * 获取字符串码点
     * @param s s
     * @return ignore
     */
    public static int[] codePoints(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return new int[0];
        }

        int[] result = new int[s.codePointCount(0, s.length())];
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            result[i] = s.codePointAt(index);
            index += Character.charCount(result[i]);
        }
        return result;
    }
    /**
     * 比较字符串是否相等(不区分大小写)
     * @param o1 对象1
     * @param o2 对象2
     * @return 相同true
     */
    public static boolean ignoreEquals(Object o1, Object o2) {
        String str1 = utf8Str(o1);
        String str2 = utf8Str(o2);
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 != null) {
            return str1.equalsIgnoreCase(str2);
        }
        return false;
    }
    public static boolean defaultEquals(String str1, String str2) {
        return (str1 == str2) || (str1 != null && str1.equals(str2));
    }
    public static String utf8Str(Object obj, Charset charset) {
        return null == obj?null:(obj instanceof String?(String)obj:(obj instanceof byte[]?utf8Str((byte[])((byte[])obj), charset):(obj instanceof Byte[]?utf8Str((Byte[])((Byte[])obj), charset):(obj instanceof ByteBuffer ?utf8Str((ByteBuffer)obj, charset):(ArrayUtils.isArray(obj)?ArrayUtils.toString(obj):obj.toString())))));
    }
    public static String utf8Str(Object obj) {
        return utf8Str(obj, StandardCharsets.UTF_8);
    }

    public static String defaultString(String str) {
        return defaultString(str, "");
    }

    public static String defaultString(String str, String defaultStr) {
        return str == null ? defaultStr : str;
    }
}
