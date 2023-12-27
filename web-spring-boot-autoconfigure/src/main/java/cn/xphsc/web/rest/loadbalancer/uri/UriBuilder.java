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
package cn.xphsc.web.rest.loadbalancer.uri;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class UriBuilder {
    private static final String HEADER_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
    private static final ThreadLocal<CharsetEncoder> UTF8ENCODER = ThreadLocal.withInitial(StandardCharsets.UTF_8::newEncoder);
    public static <T> String getFirstAsString(Map<String, List<T>> multiValueMap, String key) {
        return getFirstAsString(multiValueMap, key, false);
    }

    public static <T> String getFirstAsString(Map<String, List<T>> multiValueMap, String key, boolean stripQuotes) {
        List<T> values = multiValueMap.get(key);
        if (values == null || values.isEmpty()) return null;
        Object value = values.get(0);
        if (value == null) return null;
        return stripQuotes ? stripQuotes(value.toString()) : value.toString();
    }

    public static String stripQuotes(String value) {
        if (value == null) return null;
        int start = 0, end = value.length();
        if (value.charAt(0) == '"') start = 1;
        if (value.charAt(value.length() - 1) == '"') end = value.length() - 1;
        return value.substring(start, end);
    }

    public static void putSingle(Map<String, List<Object>> multiValueMap, String key, Object value) {
        put(multiValueMap, key, value, true);
    }

    public static void add(Map<String, List<Object>> multiValueMap, String key, Object value) {
        put(multiValueMap, key, value, false);
    }

    private static void put(Map<String, List<Object>> multiValueMap, String key, Object value, boolean single) {
        synchronized (multiValueMap) {
            // save calling code some headaches
            if (value == null) {
                if (single) multiValueMap.remove(key);
                return;
            }
            List<Object> values = multiValueMap.get(key);
            if (values == null) {
                values = new ArrayList<>();
                multiValueMap.put(key, values);
            } else if (single)
                values.clear();
            values.add(value);
        }
    }

    /**
     * URL-decodes names and values
     */
    public static Map<String, String> getQueryParameterMap(String queryString) {
        Map<String, String> parameters = new HashMap<>();
        if (queryString != null && queryString.trim().length() > 0) {
            for (String pair : queryString.split("&")) {
                int equals = pair.indexOf('=');
                if (equals == 0) throw new IllegalArgumentException("invalid query parameter: " + pair);

                String key = equals > 0 ? pair.substring(0, equals) : pair;
                String value = equals > 0 ? pair.substring(equals + 1) : null;

                if (key.trim().length() == 0) throw new IllegalArgumentException("query parameters must have a name");

                parameters.put(urlDecode(key), urlDecode(value));
            }
        }
        return parameters;
    }

    /**
     * @deprecated (2.0.4) use {@link #generateRawQueryString(Map)} instead
     */
    public static String generateQueryString(Map<String, String> parameterMap) {
        return generateRawQueryString(parameterMap);
    }

    /**
     * URL-encodes names and values
     */
    public static String generateRawQueryString(Map<String, String> parameterMap) {
        StringBuilder query = new StringBuilder();
        if (parameterMap != null && !parameterMap.isEmpty()) {
            Iterator<String> paramI = parameterMap.keySet().iterator();
            while (paramI.hasNext()) {
                String name = paramI.next();
                query.append(urlEncode(name));
                if (parameterMap.get(name) != null) {
                    query.append("=").append(urlEncode(parameterMap.get(name)));
                }
                if (paramI.hasNext()) query.append("&");
            }
        }
        return query.toString();
    }







    public static String getEncodedPath(URI uri) {

        // this is the only way I've found to get the true encoded path
        String rawUri = uri.toASCIIString();
        String path = rawUri.substring(rawUri.indexOf("/", 9));
        if (path.contains("?")) path = path.substring(0, path.indexOf("?"));
        if (path.contains("#")) path = path.substring(0, path.indexOf("#"));
        return path;
    }

    public static String urlEncode(String value) {
        if (value == null) return null;
        // Use %20, not +
        try {
            return URLEncoder.encode(value, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding isn't supported on this system", e); // unrecoverable
        }
    }

    public static String urlDecode(String value) {
        return urlDecode(value, true);
    }

    public static String urlDecode(String value, boolean preservePlus) {
        if (value == null) return null;
        try {
            // don't want '+' decoded to a space
            if (preservePlus) value = value.replace("+", "%2B");
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding isn't supported on this system", e); // unrecoverable
        }
    }

    /**
     * Note the rawQuery and rawFragment must already be encoded.  No URL-encoding will be done for parameters here.
     * This is the only way ampersands (&amp;) can be encoded into a parameter value.
     */
    public static URI buildUri(String scheme, String host, int port, String path, String rawQuery, String rawFragment)
            throws URISyntaxException {
        URI uri = new URI(scheme, null, host, port, path, null, null);

        String uriString = toASCIIString(uri);
        if (rawQuery != null) uriString += "?" + rawQuery;
        if (rawFragment != null) uriString += "#" + rawFragment;

        // workaround for https://bugs.openjdk.java.net/browse/JDK-8037396
        uriString = uriString.replace("[", "%5B").replace("]", "%5D");

        // replace double-slash with /%2f (workaround for apache client)
        if (path != null && path.length() > 2 && path.charAt(0) == '/' && path.charAt(1) == '/') {
            int doubleSlashIndex = uriString.indexOf("//");
            if (scheme != null) doubleSlashIndex = uriString.indexOf("//", doubleSlashIndex + 2);
            uriString = uriString.substring(0, doubleSlashIndex) + "/%2F" + uriString.substring(doubleSlashIndex + 2);
        }

        // Special case to handle "+" characters that URI doesn't handle well.
        uriString = uriString.replace("+", "%2B");

        return new URI(uriString);
    }

    /**
     * Returns the content of this URI as a US-ASCII string.
     *
     * <p><b>Note:</b> this starts our customized version of URI's toASCIIString.  We differ in only one aspect: we do
     * NOT normalize Unicode characters.  This is because certain Unicode characters may have different compositions
     * and normalization may change the UTF-8 sequence represented by a character.  We must maintain the same UTF-8
     * sequence in and out and therefore we cannot normalize the sequences.</p>
     *
     * <p> If this URI does not contain any characters in the <i>other</i>
     * category then an invocation of this method will return the same value as
     * an invocation of the {@link #toString() toString} method.  Otherwise
     * this method works as if by invoking that method and then
     * <a href="#encode">encoding</a> the result.  </p>
     *
     * @return  The string form of this URI, encoded as needed
     *          so that it only contains characters in the US-ASCII
     *          charset
     */
    public static String toASCIIString(URI u) {
        String s = defineString(u);
        return encode(s);
    }

    /**
     * Defines a URI string.  Provided for our special URI encoder.
     * @param u URI to encode
     * @return String for the URI
     */
    private static String defineString(URI u) {

        StringBuilder sb = new StringBuilder();
        if (u.getScheme() != null) {
            sb.append(u.getScheme());
            sb.append(':');
        }
        if (u.isOpaque()) {
            sb.append(u.getRawSchemeSpecificPart());
        } else {
            if (u.getHost() != null) {
                sb.append("//");
                if (u.getUserInfo() != null) {
                    sb.append(u.getUserInfo());
                    sb.append('@');
                }
                boolean needBrackets = ((u.getHost().indexOf(':') >= 0)
                        && !u.getHost().startsWith("[")
                        && !u.getHost().endsWith("]"));
                if (needBrackets) sb.append('[');
                sb.append(u.getHost());
                if (needBrackets) sb.append(']');
                if (u.getPort() != -1) {
                    sb.append(':');
                    sb.append(u.getPort());
                }
            } else if (u.getRawAuthority() != null) {
                sb.append("//");
                sb.append(u.getRawAuthority());
            }
            if (u.getRawPath() != null)
                sb.append(u.getRawPath());
            if (u.getRawQuery() != null) {
                sb.append('?');
                sb.append(u.getRawQuery());
            }
        }
        if (u.getRawFragment() != null) {
            sb.append('#');
            sb.append(u.getRawFragment());
        }
        return sb.toString();
    }

    /**
     * Encodes all characters >= \u0080 into escaped, <strikethrough>normalized</strikethrough> UTF-8 octets,
     * assuming that s is otherwise legal
     */
    private static String encode(String s) {
        int n = s.length();
        if (n == 0)
            return s;

        // First check whether we actually need to encode
        for (int i = 0;;) {
            if (s.charAt(i) >= '\u0080')
                break;
            if (++i >= n)
                return s;
        }

        ByteBuffer bb = null;
        try {
            bb = UTF8ENCODER.get().encode(CharBuffer.wrap(s));
        } catch (CharacterCodingException x) {
            assert false;
        }finally {
            UTF8ENCODER.remove();
        }

        StringBuffer sb = new StringBuffer();
        while (bb.hasRemaining()) {
            int b = bb.get() & 0xff;
            if (b >= 0x80)
                appendEscape(sb, (byte)b);
            else
                sb.append((char)b);
        }
        return sb.toString();
    }

    private final static char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    private static void appendEscape(StringBuffer sb, byte b) {
        sb.append('%');
        sb.append(hexDigits[(b >> 4) & 0x0f]);
        sb.append(hexDigits[(b >> 0) & 0x0f]);
    }


    public static URI replaceHost(URI uri, String host) throws URISyntaxException {
        return buildUri(uri.getScheme(), host, uri.getPort(), uri.getPath(), uri.getRawQuery(), uri.getRawFragment());
    }

    public static URI replacePath(URI uri, String path) throws URISyntaxException {
        return buildUri(uri.getScheme(), uri.getHost(), uri.getPort(), path, uri.getRawQuery(), uri.getRawFragment());
    }


    public static String join(String separator, Iterable<String> items) {
        if(separator == null) throw new IllegalArgumentException("separator argument is null");
        if(items == null) throw new IllegalArgumentException("items argument is null");
        StringBuilder sb = new StringBuilder();
        for(String item : items) {
            if(sb.length() > 0) sb.append(separator);
            sb.append(item);
        }
        return sb.toString();
    }
}
