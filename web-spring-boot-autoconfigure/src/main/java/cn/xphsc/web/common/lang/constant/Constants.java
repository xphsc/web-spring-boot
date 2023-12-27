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
package cn.xphsc.web.common.lang.constant;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: constant
 * @since 1.1.6
 */
public abstract class Constants {

    // -------------------- array --------------------

    /**
     * 英文字母 数组
     */
    public static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};


    // -------------------- letter --------------------

    public static final String CR = "\r";

    public static final String LF = "\n";

    public static final String CR_LF = "\r\n";

    public static final String TAB = "\t";

    public static final String DOT = ".";

    public static final String POUND = "#";

    public static final String DOLLAR = "$";

    public static final String DASHED = "-";

    public static final String SLASH = "/";

    public static final String BACKSLASH = "\\";

    public static final String EMPTY = "";

    public static final String SPACE = " ";

    public static final String SPACE_2 = "  ";

    public static final String SPACE_4 = "    ";

    public static final String OMIT = "...";
    public static final  String DOTDOT = "..";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String QUESTION = "?";
    public static final String QUOTATION = "'";
    public static final String UNDERLINE = "_";
    public static final  String VERTICAL = "|";
    public static final String DOLLARS = "$";
    public static final String RMB = "￥";
    public static final String PERCENT = "%";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TEN = "10";
    public static final String BRACE = "{}";
    public static final String LEFT_BRACE = "{";
    public static final String RIGHT_BRACE = "}";
    public static final String BRACKET = "[]";
    public static final String LEFT_BRACKET = "[";
    public static final String RIGHT_BRACKET = "]";
    public static final String PARENTHESES = "()";
    public static final String LEFT_PARENTHESES = "(";
    public static final  String RIGHT_PARENTHESES = ")";
    public static final String DOUBLE_QUOTE = "\"";
    public static final String SINGLE_QUOTE = "'";
    public static final String EQUALS = "=";
    public static final String HASH = "#";
    public static final String AMPERSAND = "&";
    public static final String AT = "@";
    public static final String ASTERISK = "*";
    public static final String STAR = "*";
    public static final String BACK_SLASH = "\\";
    public static final String DASH = "-";
    public static final String DOT_CLASS = ".class";
    public static final String DOT_JAVA = ".java";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String HAT = "^";
    public static final String LEFT_CHEV = "<";
    public static final String N = "n";
    public static final String NO = "no";
    public static final String OFF = "off";
    public static final  String ON = "on";
    public static final String PIPE = "|";
    public static final String PLUS = "+";
    public static final String QUESTION_MARK = "?";
    public static final String EXCLAMATION_MARK = "!";
    public static final String QUOTE = "\"";
    public static final  String RETURN = "\r";
    public static final  String RIGHT_CHEV = ">";
    public static final  String BACKTICK = "`";
    public static final String LEFT_SQ_BRACKET = "[";
    public static final  String RIGHT_SQ_BRACKET = "]";
    public static final  String UNDERSCORE = "_";
    public static final String Y = "y";
    public static final String YES = "yes";
    public static final String DOLLAR_LEFT_BRACE = "${";
    public static final String   HASH_LEFT_BRACE="#{";
    public static final String CRLF = "\r\n";
    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";

    // -------------------- charset --------------------

    public static final String ASCII = "US-ASCII";

    public static final String GBK = "GBK";

    public static final String GB_2312 = "GB2312";

    public static final String UTF_8 = "UTF-8";

    public static final String UTF_16BE = "UTF-16BE";

    public static final String UTF_16LE = "UTF-16LE";

    public static final String ISO_8859_1 = "ISO-8859-1";

    // -------------------- buffer size --------------------

    public static final int BUFFER_KB_1 = 1024;

    public static final int BUFFER_KB_2 = 1024 * 2;

    public static final int BUFFER_KB_4 = 1024 * 4;

    public static final int BUFFER_KB_8 = 1024 * 8;

    public static final int BUFFER_KB_16 = 1024 * 16;

    public static final int BUFFER_KB_32 = 1024 * 32;

    public static final int MBP = 1024 * 128;

    // -------------------- capacity --------------------

    public static final int CAPACITY_1 = 1;

    public static final int CAPACITY_2 = 2;

    public static final int CAPACITY_4 = 4;

    public static final int CAPACITY_8 = 8;

    public static final int CAPACITY_16 = 16;

    public static final int CAPACITY_32 = 32;

    public static final int CAPACITY_64 = 64;

    public static final int CAPACITY_128 = 128;

    public static final int CAPACITY_256 = 256;

    public static final int CAPACITY_512 = 512;

    public static final int CAPACITY_1024 = 1024;

    // -------------------- ms --------------------

    public static final int MS_100 = 100;

    public static final int MS_300 = 300;

    public static final int MS_500 = 500;

    public static final int MS_S_1 = 1000;

    public static final int MS_S_2 = 1000 * 2;

    public static final int MS_S_3 = 1000 * 3;

    public static final int MS_S_5 = 1000 * 5;

    public static final int MS_S_10 = 1000 * 10;

    public static final int MS_S_15 = 1000 * 15;

    public static final int MS_S_30 = 1000 * 30;

    public static final int MS_S_60 = 1000 * 60;

    // -------------------- file path --------------------

    public static final String ROOT = SLASH;

    public static final String SEPARATOR = SLASH;

    // -------------------- io --------------------

    public static final String STREAM_CLOSE = "Stream closed";

    public static final String ACCESS_R = "r";

    public static final String ACCESS_RW = "rw";

    public static final String ACCESS_RWS = "rws";

    public static final String ACCESS_RWD = "rwd";

    // -------------------- num --------------------

    public static final Integer N_N_1 = -1;

    public static final Integer N_0 = 0;

    public static final Integer N_1 = 1;

    public static final Integer N_2 = 2;

    public static final Integer N_3 = 3;

    public static final Integer N_4 = 4;

    public static final Integer N_5 = 5;

    public static final Integer N_6 = 6;

    public static final Integer N_7 = 7;

    public static final Integer N_8 = 8;

    public static final Integer N_9 = 9;

    public static final Integer N_10 = 10;

    public static final Integer N_100 = 100;

    public static final Integer N_1000 = 1000;

    public static final Integer N_10000 = 10000;

    public static final Integer N_100000 = 100000;

    public static final Long L_N_1 = -1L;

    public static final Long L_0 = 0L;

    public static final Long L_1 = 1L;

    public static final Double D_N_1 = -1D;

    public static final Double D_0 = 0D;

    public static final Double D_1 = 1D;

    // -------------------- suffix --------------------

    public static final String SUFFIX_CSV = "csv";

    public static final String SUFFIX_XLS = "xls";

    public static final String SUFFIX_XLSX = "xlsx";

    public static final String SUFFIX_DOC = "doc";

    public static final String SUFFIX_DOCX = "docx";

    public static final String SUFFIX_PDF = "pdf";

    public static final String SUFFIX_JAVA = "java";

    public static final String SUFFIX_CLASS = "class";

    public static final String SUFFIX_PNG = "png";

    public static final String SUFFIX_JAR = "jar";

    public static final String SUFFIX_WAR = "war";

    public static final String SUFFIX_ZIP = "zip";

    public static final String SUFFIX_7Z = "7z";

    public static final String SUFFIX_TAR = "tar";

    public static final String SUFFIX_GZ = "gz";

    public static final String SUFFIX_BZ2 = "bz2";

    public static final String SUFFIX_TAR_GZ = "tar.gz";

    public static final String SUFFIX_TAR_BZ2 = "tar.bz2";

    public static final String SUFFIX_LOG = "log";

    public static final String SUFFIX_XML = "xml";

    public static final String SUFFIX_JSON = "json";

    public static final String SUFFIX_YML = "yml";

    public static final String SUFFIX_TXT = "txt";

    public static final String SUFFIX_PROPERTIES = "properties";

    public static final String SUFFIX_FILE = "file";

    // -------------------- protocol --------------------

    public static final String PROTOCOL_HTTP = "http";

    public static final String PROTOCOL_HTTPS = "https";

    public static final String PROTOCOL_FTP = "ftp";

    public static final String PROTOCOL_FILE = "file";

    public static final String PROTOCOL_JAR = "jar";

    public static final String PROTOCOL_SSH = "ssh";

    // -------------------- font --------------------

    public static final String FONT_MICROSOFT_ELEGANT_BLACK = "微软雅黑";

    // -------------------- sql --------------------

    public static final String LIMIT = "LIMIT";

    public static final String LIMIT_1 = "LIMIT 1";

    public static final String ENTITY = "entity";

    public static final String UPDATE = "update";

    public static final String LIST = "list";

    public static final String PAGER = "pager";

    // -------------------- http --------------------

    public static final Integer HTTP_OK_CODE = 200;

    public static final Integer HTTP_BAD_REQUEST_CODE = 400;

    public static final Integer HTTP_NOT_FOUND_CODE = 404;

    public static final Integer HTTP_ERROR_CODE = 500;

    // -------------------- others --------------------

    public static final String BR = "<br/>";

    public static final String OK = "ok";

    public static final String ERROR = "error";

    public static final String NULL = "null";

    public static final String UNKNOWN = "unknown";

    public static final String DEFAULT = "default";

    public static final String LOCALHOST = "localhost";

    public static final String LOCALHOST_IP_V4 = "127.0.0.1";

    public static final String LOCALHOST_IP_V6 = "0:0:0:0:0:0:0:1";

    public static final Integer ENABLE = 1;

    public static final Integer DISABLE = 2;

    public static final Integer INCREMENT = 1;

    public static final Integer DECREMENT = 2;

    public static final Integer NOT_DELETED = 1;

    public static final Integer IS_DELETED = 2;

    public static final String EMPTY_OBJECT = "{}";

    public static final String EMPTY_ARRAY = "[]";

}
