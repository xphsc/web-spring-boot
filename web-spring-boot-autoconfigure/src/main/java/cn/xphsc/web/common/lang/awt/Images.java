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
package cn.xphsc.web.common.lang.awt;

import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.common.lang.io.Files;
import cn.xphsc.web.common.lang.io.Streams;
import cn.xphsc.web.utils.Base64Utils;
import cn.xphsc.web.utils.ByteUtils;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 图片工具类
 * @since 1.2.0
 */
public class Images {

    private Images() {
    }

    /**
     * 复制 BufferedImage
     * @param img BufferedImage
     * @return BufferedImage
     */
    public static BufferedImage copy(BufferedImage img) {
        return copy(img, img.getType());
    }

    /**
     * 复制 BufferedImage
     * @param img       BufferedImage
     * @param imageType 图片类型
     * @return BufferedImage
     */
    public static BufferedImage copy(BufferedImage img, int imageType) {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), imageType);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return newImage;
    }

    /**
     * 重置图片大小
     *
     * @param img    图片
     * @param width  新宽度
     * @param height 新高度
     * @return 新图片
     */
    public static BufferedImage resize(BufferedImage img, int width, int height) {
        return resize(img, width, height, 0, 0);
    }

    /**
     * 重置图片大小
     * @param img    图片
     * @param width  新宽度
     * @param height 新高度
     * @param x      绘制x坐标
     * @param y      绘制y坐标
     * @return 新图片
     */
    public static BufferedImage resize(BufferedImage img, int width, int height, int x, int y) {
        BufferedImage newImage = new BufferedImage(width, height, img.getType());
        Graphics2D g = newImage.createGraphics();
        g.drawImage(img, x, y, null);
        g.dispose();
        return newImage;
    }

    /**
     * 设置抗锯齿
     * @param g2d g2d
     */
    public static void setAntiAliasing(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    /**
     * 创建一个透明背景的图片 需要png格式导出
     *
     * @param width  width
     * @param height height
     * @return BufferedImage
     */
    public static BufferedImage getTransparentImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        return image;
    }

    public static BufferedImage getRadiusImage(BufferedImage image, int radius) {
        return getRadiusImage(image, radius, radius);
    }

    /**
     * 绘制圆角
     * @param image image
     * @param arcw  arcw
     * @param arch  arch
     * @return 圆角image
     */
    public static BufferedImage getRadiusImage(BufferedImage image, float arcw, float arch) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage transparentImage = getTransparentImage(width, height);
        Graphics2D g2d = transparentImage.createGraphics();
        Images.setAntiAliasing(g2d);
        g2d.setClip(new RoundRectangle2D.Float(0, 0, width, height, arcw, arch));
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return transparentImage;
    }

    /**
     * 获取 BufferedImage
     * @param path 文件路径
     * @return BufferedImage
     */
    public static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * 获取BufferedImage
     * @param file 文件
     * @return BufferedImage
     */
    public static BufferedImage getImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * 获取BufferedImage
     *
     * @param bs bytes
     * @return BufferedImage
     */
    public static BufferedImage getImage(byte[] bs) {
        try {
            return ImageIO.read(Streams.toInputStream(bs));
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * 获取BufferedImage
     *
     * @param in inputStream
     * @return BufferedImage
     */
    public static BufferedImage getImage(InputStream in) {
        try {
            return ImageIO.read(in);
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * 获取BufferedImage
     *
     * @param base64 base64 包含头
     * @return BufferedImage
     */
    public static BufferedImage getImageBase64(String base64) {
        try {
            return ImageIO.read(Streams.toInputStream(Base64Utils.img64Decode(base64)));
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * 获取BufferedImage
     *
     * @param bs base64bytes 不包含头
     * @return BufferedImage
     */
    public static BufferedImage getImageBase64(byte[] bs) {
        try {
            return ImageIO.read(Streams.toInputStream(ByteUtils.toBytes(bs)));
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        }
    }

    public static BufferedImage getImageByIcons(byte[] bs) {
        return getImageByIcons(new ImageIcon(bs));
    }

    public static BufferedImage getImageByIcons(InputStream in) throws IOException {
        return getImageByIcons(new ImageIcon(Streams.toByteArray(in)));
    }

    public static BufferedImage getImageByIcons(String base64) {
        return getImageByIcons(new ImageIcon(Base64Utils.img64Decode(base64)));
    }

    /**
     * 通过 ImageIcon 获取图片 解决读取文件是红色遮罩层的问题
     * @param icon icon
     * @return BufferedImage
     */
    public static BufferedImage getImageByIcons(ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
        g.drawImage(icon.getImage(), 0, 0, null);
        g.dispose();
        return bufferedImage;
    }

    /**
     * 获取图片大小
     * @param img 图片
     * @return {width, height}
     */
    public static int[] getSize(BufferedImage img) {
        return new int[]{img.getWidth(null), img.getHeight(null)};
    }

    /**
     * BufferedImage 转 byte[]
     * @param img img
     * @return byte[]
     */
    public static byte[] getBytes(BufferedImage img) {
        return getBytes(img, Constants.SUFFIX_PNG);
    }

    /**
     * BufferedImage 转 byte[]
     * @param img    img
     * @param format 格式
     * @return byte[]
     */
    public static byte[] getBytes(BufferedImage img, String format) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, format, stream);
            return stream.toByteArray();
        } catch (IOException e) {
            throw Exceptions.runtime(e);
        }
    }

    /**
     * BufferedImage 输出到流
     * @param img image
     * @param out 输出流
     * @throws IOException IOException
     */
    public static void write(BufferedImage img, OutputStream out) throws IOException {
        ImageIO.write(img, Constants.SUFFIX_PNG, out);
    }

    /**
     * BufferedImage 输出到流
     * @param img    image
     * @param format 格式
     * @param out    输出流
     * @throws IOException IOException
     */
    public static void write(BufferedImage img, String format, OutputStream out) throws IOException {
        ImageIO.write(img, format, out);
    }

    /**
     * BufferedImage 输出到文件
     * @param img  image
     * @param path 文件路径
     * @throws IOException IOException
     */
    public static void write(BufferedImage img, String path) throws IOException {
        Files.touch(path);
        ImageIO.write(img, Constants.SUFFIX_PNG, new File(path));
    }

    /**
     * BufferedImage 输出到文件
     * @param img    image
     * @param format 格式
     * @param path   文件路径
     * @throws IOException IOException
     */
    public static void write(BufferedImage img, String format, String path) throws IOException {
        Files.touch(path);
        ImageIO.write(img, format, new File(path));
    }

    /**
     * BufferedImage 输出到文件
     * @param img  image
     * @param file 文件
     * @throws IOException IOException
     */
    public static void write(BufferedImage img, File file) throws IOException {
        Files.touch(file);
        ImageIO.write(img, Constants.SUFFIX_PNG, file);
    }

    /**
     * BufferedImage 输出到文件
     * @param img    image
     * @param format 格式
     * @param file   文件
     * @throws IOException IOException
     */
    public static void write(BufferedImage img, String format, File file) throws IOException {
        Files.touch(file);
        ImageIO.write(img, format, file);
    }

    /**
     * BufferedImage 转 base64
     * @param img img
     * @return base64
     */
    public static String base64Encode(BufferedImage img) {
        return base64Encode(img, Constants.SUFFIX_PNG);
    }

    /**
     * BufferedImage 转 base64
     * @param img    img
     * @param format 格式
     * @return base64
     */
    public static String base64Encode(BufferedImage img, String format) {
        return Base64Utils.img64Encode(getBytes(img, format), format);
    }

    /**
     * 图片base64编码
     * @param bs bs
     * @return base64
     */
    public static String base64Encode(byte[] bs) {
        return Base64Utils.img64Encode(bs);
    }

    /**
     * 图片base64编码
     * @param bs   bs
     * @param type jpg, jepg, png
     * @return base64
     */
    public static String base64Encode(byte[] bs, String type) {
        return Base64Utils.img64Encode(bs, type);
    }

    /**
     * 图片base64解码
     * @param s base64
     * @return 图片
     */
    public static byte[] base64Decode(String s) {
        return Base64Utils.img64Decode(s);
    }

}
