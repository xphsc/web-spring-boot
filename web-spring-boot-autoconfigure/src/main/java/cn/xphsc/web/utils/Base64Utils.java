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

import cn.xphsc.web.common.lang.base64.BASE64Decoder;
import cn.xphsc.web.common.lang.constant.Constants;
import org.apache.tomcat.util.codec.binary.Base64;
import java.nio.charset.Charset;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Base64Utils {
    private static final Base64Delegate delegate = null;
    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    public static byte[] base64Decode(byte[] bytes){
        return Base64.decodeBase64(bytes.toString());
    }

    public static String encodeToString(byte[] src) {
        if (src == null) {
            return null;
        } else if (src.length == 0) {
            return "";
        } else {
            return ByteUtils.toString(src);
        }
    }

    public static String encode(String value) {
        return Base64Utils.encode(value, Charsets.UTF_8);
    }
    public static String encode(String value, Charset charset) {
        byte[] val = new byte[0];
        val = value.getBytes(charset);
        return base64Encode(val);
    }
    public static byte[] decodeFromString(String src) {
        if (src == null) {
            return null;
        } else if (src.isEmpty()) {
            return new byte[0];
        } else {
            return ByteUtils.toBytes(src);
        }
    }
    /**
     * 图片base64编码
     * @param bs bs
     * @return base64
     */
    public static String img64Encode(byte[] bs) {
        return img64Encode(bs, Constants.SUFFIX_PNG);
    }

    /**
     * 图片base64编码
     *
     * @param bs   bs
     * @param type jpg, jpeg, png
     * @return base64
     */
    public static String img64Encode(byte[] bs, String type) {
        return "data:image/" + type + ";base64," + new String(new String(java.util.Base64.getDecoder().decode(bs)));
    }

    /**
     * 图片base64解码
     *
     * @param s base64
     * @return 图片
     */
    public static byte[] img64Decode(String s) {
        String[] b = s.split(",");
        return ByteUtils.toBytes(new String(java.util.Base64.getDecoder().decode(ByteUtils.toBytes(b[b.length - 1]))));
    }

    /**
     * 获取base64图片类型
     * @param s base64
     * @return 图片类型
     */
    public static String img64Type(String s) {
        String[] b = s.split(",");
        if (b.length == 0) {
            return cn.xphsc.web.utils.StringUtils.EMPTY;
        }
        String dataImage = b[0].split(";")[0];
        String[] dataType = dataImage.split("/");
        if (dataType.length == 0) {
            return cn.xphsc.web.utils.StringUtils.EMPTY;
        }
        return dataType[1];
    }
    interface Base64Delegate {
        byte[] encode(byte[] var1);

        byte[] decode(byte[] var1);

        byte[] encodeUrlSafe(byte[] var1);

        byte[] decodeUrlSafe(byte[] var1);
    }

}
